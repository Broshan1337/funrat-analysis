package com.example.screen;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.EOFException;
import java.nio.channels.ClosedChannelException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

@ServerEndpoint("/screen-stream")
@Component
public class ScreenStreamWebSocketEndpoint {
   private static final ObjectMapper mapper = new ObjectMapper();
   private static final Set<Session> disconnectEventsSent = ConcurrentHashMap.newKeySet();
   private static final Map<Session, Long> lastFrameAcceptedAt = new ConcurrentHashMap<>();
   private static final int MAX_FRAME_BYTES = 4194304;
   private static final long MIN_FRAME_INTERVAL_MS = 50L;
   private static final long ASYNC_SEND_TIMEOUT_MS = 2000L;
   private static volatile ScreenSenderRegistry registry;

   public static void setRegistry(ScreenSenderRegistry senderRegistry) {
      registry = senderRegistry;
   }

   @OnOpen
   public void onOpen(Session session) {
      session.getAsyncRemote().setSendTimeout(2000L);
      ScreenSenderRegistry current = registry;
      if (current != null) {
         current.register(session);
         this.hydrateIdentityFromQuery(session);
      }

      RevokeStatusWebSocketEndpoint.broadcastStatus();
   }

   @OnClose
   public void onClose(Session session, CloseReason reason) {
      ScreenSenderRegistry current = registry;

      try {
         String closeText = reason == null ? "unknown" : reason.getCloseCode() + " " + reason.getReasonPhrase();
         System.out.println("[screen-stream] close " + this.describeSession(current, session) + " reason=" + closeText);
      } catch (Exception var5) {
      }

      if (current != null) {
         ScreenSenderRegistry.ClientInfo info = current.infoFor(session);
         if (info != null) {
            ScreenWebSocketEndpoint.clearControlApproval(info.getAccessKey(), info.getClientId());
         }

         this.broadcastDisconnectEvent(session, current, this.buildCloseReason(reason));
         current.unregister(session);
         this.cleanupSessionState(session);
      }

      RevokeStatusWebSocketEndpoint.broadcastStatus();
   }

   @OnError
   public void onError(Session session, Throwable throwable) {
      if (session != null) {
         ScreenSenderRegistry current = registry;

         try {
            Throwable root = this.rootCause(throwable);
            String msg = throwable == null ? "null" : throwable.getClass().getName() + ": " + this.safe(throwable.getMessage());
            String rootMsg = root == null ? "" : " | root=" + root.getClass().getName() + ": " + this.safe(root.getMessage());
            if (!this.isExpectedDisconnect(throwable)) {
               System.out.println("[screen-stream] error " + this.describeSession(current, session) + " " + msg + rootMsg);
            }

            if (throwable != null && !this.isExpectedDisconnect(throwable)) {
               throwable.printStackTrace();
            }
         } catch (Exception var7) {
         }

         if (current != null) {
            ScreenSenderRegistry.ClientInfo info = current.infoFor(session);
            if (info != null) {
               ScreenWebSocketEndpoint.clearControlApproval(info.getAccessKey(), info.getClientId());
            }

            String reason = throwable == null ? "" : throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
            this.broadcastDisconnectEvent(session, current, reason);
            current.unregister(session);
            this.cleanupSessionState(session);
         }
      }

      RevokeStatusWebSocketEndpoint.broadcastStatus();
   }

   @OnMessage
   public void onMessage(Session session, String message) {
      JsonNode root = this.readJson(message);
      if (root != null) {
         String type = root.path("type").asText("");
         if ("latencyPing".equals(type)) {
            this.handleLatencyPing(session, root);
         } else if ("senderLatency".equals(type)) {
            this.handleSenderLatency(session, root);
         } else if ("hello".equals(type) || "identify".equals(type)) {
            this.handleIdentify(session, root);
         } else if ("fileResult".equals(type) || "fileResponse".equals(type)) {
            this.handleFileResponse(session, root, message);
         } else if ("shellResult".equals(type) || "shellResponse".equals(type)) {
            this.handleShellResponse(session, root, message);
         } else if ("shellChunk".equals(type)) {
            this.handleShellChunk(session, root, message);
         } else if ("monitors".equals(type)) {
            this.handleMonitors(session, message);
         } else if ("controlStatus".equals(type)) {
            this.handleControlStatus(session, root, message);
         } else if ("pauseStatus".equals(type)) {
            this.handlePauseStatus(session, message);
         } else if ("clientActionResult".equals(type)) {
            this.handleClientActionResult(session, root, message);
         } else if ("keylog".equals(type)) {
            this.handleKeylog(session, message);
         }
      }
   }

   private void handleKeylog(Session session, String message) {
      ScreenSenderRegistry current = registry;
      if (current != null) {
         ScreenSenderRegistry.ClientInfo info = current.infoFor(session);
         if (info != null) {
            ScreenWebSocketEndpoint.broadcastTextToViewers(info.getAccessKey(), info.getClientId(), message);
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

         ScreenWebSocketEndpoint.sendTextToSession(session, payload.toString());
      } catch (Exception var5) {
      }
   }

   private void handleSenderLatency(Session session, JsonNode root) {
      ScreenSenderRegistry current = registry;
      if (current != null) {
         ScreenSenderRegistry.ClientInfo info = current.infoFor(session);
         if (info != null && !info.getAccessKey().isEmpty() && !info.getClientId().isEmpty()) {
            try {
               ObjectNode payload = mapper.createObjectNode()
                  .put("type", "senderLatency")
                  .put("offsetMs", root.path("offsetMs").asDouble(0.0))
                  .put("rttMs", root.path("rttMs").asDouble(0.0))
                  .put("serverTs", System.currentTimeMillis());
               ScreenWebSocketEndpoint.broadcastTextToViewers(info.getAccessKey(), info.getClientId(), payload.toString());
            } catch (Exception var6) {
            }
         }
      }
   }

   @OnMessage
   public void onBinary(Session session, byte[] data) {
      ScreenSenderRegistry current = registry;
      if (current != null) {
         if (data != null && data.length != 0) {
            if (data.length > 4194304) {
               this.closeQuietly(session, "screen frame too large");
            } else {
               long now = System.currentTimeMillis();
               Long previous = lastFrameAcceptedAt.get(session);
               if (previous == null || now - previous >= 50L) {
                  lastFrameAcceptedAt.put(session, now);
                  ScreenSenderRegistry.ClientInfo info = current.infoFor(session);
                  if (info != null && !info.getAccessKey().isEmpty()) {
                     ScreenWebSocketEndpoint.broadcastToViewers(info.getAccessKey(), info.getClientId(), data);
                  }
               }
            }
         }
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
      ScreenSenderRegistry current = registry;
      if (current != null) {
         String key = root.path("key").asText("");
         String deviceName = root.path("deviceName").asText("");
         if (deviceName.isEmpty()) {
            deviceName = root.path("pcName").asText("");
         }

         if (deviceName.isEmpty()) {
            deviceName = root.path("name").asText("");
         }

         String ipAddress = root.path("ipAddress").asText("");
         if (ipAddress.isEmpty()) {
            ipAddress = root.path("ip").asText("");
         }

         if (ipAddress.isEmpty()) {
            ipAddress = root.path("sourceIp").asText("");
         }

         String clientId = current.resolveServerClientId(key, deviceName, ipAddress);
         if (clientId.isEmpty()) {
            clientId = this.generateClientId();
         }

         boolean admin = root.path("admin").asBoolean(false) || root.path("isAdmin").asBoolean(false);
         current.updateIdentity(session, key, deviceName, clientId, admin, ipAddress);
         ScreenSenderRegistry.ClientInfo info = current.infoFor(session);
         this.sendAck(session, "identified", info != null ? info.getClientId() : clientId);
         if (info != null && !info.getAccessKey().isEmpty()) {
            ScreenWebSocketEndpoint.updateControlApproval(info.getAccessKey(), info.getClientId(), false);
            ScreenWebSocketEndpoint.requestViewerState(info.getAccessKey(), info.getClientId());
            RevokeStatusWebSocketEndpoint.broadcastClientEvent(info.getAccessKey(), info.getClientId(), info.getDeviceName(), "connected", "", Instant.now());
         }

         RevokeStatusWebSocketEndpoint.broadcastStatus();
      }
   }

   private void handleFileResponse(Session session, JsonNode root, String message) {
      ScreenSenderRegistry current = registry;
      if (current != null) {
         String requestId = extractRequestId(root);
         Session viewer = ScreenWebSocketEndpoint.consumeFileRequest(requestId);
         if (viewer != null && viewer.isOpen()) {
            ScreenWebSocketEndpoint.sendTextToSession(viewer, message);
         } else {
            ScreenSenderRegistry.ClientInfo info = current.infoFor(session);
            if (info != null) {
               ScreenWebSocketEndpoint.broadcastTextToViewers(info.getAccessKey(), info.getClientId(), message);
            }
         }
      }
   }

   private void handleShellResponse(Session session, JsonNode root, String message) {
      ScreenSenderRegistry current = registry;
      if (current != null) {
         String requestId = extractRequestId(root);
         Session viewer = ScreenWebSocketEndpoint.consumeShellRequest(requestId);
         if (viewer != null && viewer.isOpen()) {
            ScreenWebSocketEndpoint.sendTextToSession(viewer, message);
         } else {
            ScreenSenderRegistry.ClientInfo info = current.infoFor(session);
            if (info != null) {
               ScreenWebSocketEndpoint.broadcastTextToViewers(info.getAccessKey(), info.getClientId(), message);
            }
         }
      }
   }

   private void handleShellChunk(Session session, JsonNode root, String message) {
      ScreenSenderRegistry current = registry;
      if (current != null) {
         String requestId = extractRequestId(root);
         Session viewer = ScreenWebSocketEndpoint.peekShellRequest(requestId);
         if (viewer != null && viewer.isOpen()) {
            ScreenWebSocketEndpoint.sendTextToSession(viewer, message);
         } else {
            ScreenSenderRegistry.ClientInfo info = current.infoFor(session);
            if (info != null) {
               ScreenWebSocketEndpoint.broadcastTextToViewers(info.getAccessKey(), info.getClientId(), message);
            }
         }
      }
   }

   private void handleControlStatus(Session session, JsonNode root, String message) {
      ScreenSenderRegistry current = registry;
      if (current != null) {
         ScreenSenderRegistry.ClientInfo info = current.infoFor(session);
         if (info != null) {
            boolean allowed = root.path("allowed").asBoolean(false);
            String note = root.path("note").asText("");
            ScreenWebSocketEndpoint.updateControlApproval(info.getAccessKey(), info.getClientId(), allowed);
            String event = this.resolveControlEvent(allowed, note);
            if (!event.isEmpty()) {
               RevokeStatusWebSocketEndpoint.broadcastClientEvent(info.getAccessKey(), info.getClientId(), info.getDeviceName(), event, note, Instant.now());
            }

            ScreenWebSocketEndpoint.broadcastTextToViewers(info.getAccessKey(), info.getClientId(), message);
         }
      }
   }

   private void handleClientActionResult(Session session, JsonNode root, String message) {
      ScreenSenderRegistry current = registry;
      if (current != null) {
         ScreenSenderRegistry.ClientInfo info = current.infoFor(session);
         if (info != null) {
            String action = root == null ? "" : root.path("action").asText("");
            String note = root == null ? "" : root.path("note").asText("");
            if ("padlock".equalsIgnoreCase(action)) {
               String dllReason = this.extractPadlockDllReason(note);
               if (!dllReason.isEmpty()) {
                  RevokeStatusWebSocketEndpoint.broadcastClientEvent(
                     info.getAccessKey(), info.getClientId(), info.getDeviceName(), "padlockDllUnavailable", dllReason, Instant.now()
                  );
               }
            }

            ScreenWebSocketEndpoint.broadcastTextToViewers(info.getAccessKey(), info.getClientId(), message);
         }
      }
   }

   private String extractPadlockDllReason(String note) {
      if (note != null && !note.trim().isEmpty()) {
         String marker = "dll_unavailable:";
         int idx = note.toLowerCase().indexOf(marker);
         if (idx < 0) {
            return "";
         }

         int from = idx + marker.length();
         int to = note.indexOf(44, from);
         String raw = to >= 0 ? note.substring(from, to) : note.substring(from);
         String cleaned = raw == null ? "" : raw.trim();
         return cleaned.isEmpty() ? "" : cleaned;
      } else {
         return "";
      }
   }

   private void handleMonitors(Session session, String message) {
      ScreenSenderRegistry current = registry;
      if (current != null) {
         ScreenSenderRegistry.ClientInfo info = current.infoFor(session);
         if (info != null) {
            ScreenWebSocketEndpoint.broadcastTextToViewers(info.getAccessKey(), info.getClientId(), message);
         }
      }
   }

   private void handlePauseStatus(Session session, String message) {
      ScreenSenderRegistry current = registry;
      if (current != null) {
         ScreenSenderRegistry.ClientInfo info = current.infoFor(session);
         if (info != null) {
            ScreenWebSocketEndpoint.broadcastTextToViewers(info.getAccessKey(), info.getClientId(), message);
         }
      }
   }

   private void sendAck(Session session, String what, String clientId) {
      try {
         ObjectNode payload = mapper.createObjectNode().put("type", "ack").put("event", what);
         if (clientId != null && !clientId.isEmpty()) {
            payload.put("clientId", clientId);
         }

         ScreenWebSocketEndpoint.sendTextToSession(session, payload.toString());
      } catch (Exception var5) {
      }
   }

   private void hydrateIdentityFromQuery(Session session) {
      String key = this.firstParam(session, "key");
      String deviceName = this.firstParam(session, "deviceName");
      if (deviceName.isEmpty()) {
         deviceName = this.firstParam(session, "pcName");
      }

      if (deviceName.isEmpty()) {
         deviceName = this.firstParam(session, "name");
      }

      String ipAddress = this.firstParam(session, "ipAddress");
      if (ipAddress.isEmpty()) {
         ipAddress = this.firstParam(session, "ip");
      }

      if (ipAddress.isEmpty()) {
         ipAddress = this.firstParam(session, "sourceIp");
      }

      if (!key.isEmpty() || !deviceName.isEmpty()) {
         ScreenSenderRegistry current = registry;
         if (current != null) {
            String clientId = current.resolveServerClientId(key, deviceName, ipAddress);
            current.updateIdentity(session, key, deviceName, clientId, false, ipAddress);
         }
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

      return requestId == null ? "" : requestId.trim();
   }

   private void broadcastDisconnectEvent(Session session, ScreenSenderRegistry current, String reason) {
      if (session != null && current != null) {
         if (!this.isDisconnectEventSent(session)) {
            ScreenSenderRegistry.ClientInfo info = current.infoFor(session);
            if (info != null && !info.getAccessKey().isEmpty()) {
               this.markDisconnectEventSent(session);
               RevokeStatusWebSocketEndpoint.broadcastClientEvent(
                  info.getAccessKey(), info.getClientId(), info.getDeviceName(), "disconnected", reason, Instant.now()
               );
            }
         }
      }
   }

   private boolean isDisconnectEventSent(Session session) {
      return disconnectEventsSent.contains(session);
   }

   private void markDisconnectEventSent(Session session) {
      disconnectEventsSent.add(session);
   }

   private void cleanupSessionState(Session session) {
      disconnectEventsSent.remove(session);
      lastFrameAcceptedAt.remove(session);
      ScreenWebSocketEndpoint.clearSendState(session);
   }

   private void closeQuietly(Session session, String reason) {
      if (session != null && session.isOpen()) {
         try {
            session.close(new CloseReason(CloseCodes.TOO_BIG, reason == null ? "closed" : reason));
         } catch (Exception var4) {
         }
      }
   }

   private String generateClientId() {
      return UUID.randomUUID().toString();
   }

   private String buildCloseReason(CloseReason reason) {
      if (reason == null) {
         return "";
      } else {
         String phrase = reason.getReasonPhrase();
         int code = reason.getCloseCode() != null ? reason.getCloseCode().getCode() : 0;
         if (phrase != null && !phrase.trim().isEmpty()) {
            return code > 0 ? phrase.trim() + " (code " + code + ")" : phrase.trim();
         } else {
            return code > 0 ? "close code " + code : "";
         }
      }
   }

   private String describeSession(ScreenSenderRegistry current, Session session) {
      if (session == null) {
         return "session=null";
      }

      String id = this.safe(session.getId());
      ScreenSenderRegistry.ClientInfo info = current == null ? null : current.infoFor(session);
      return info == null
         ? "id=" + id + " sender=unknown"
         : "id=" + id + " key=" + this.safe(info.getAccessKey()) + " clientId=" + this.safe(info.getClientId()) + " device=" + this.safe(info.getDeviceName());
   }

   private Throwable rootCause(Throwable throwable) {
      if (throwable == null) {
         return null;
      }

      Throwable current = throwable;

      for (int guard = 0; current.getCause() != null && current.getCause() != current && guard < 16; guard++) {
         current = current.getCause();
      }

      return current;
   }

   private boolean isExpectedDisconnect(Throwable throwable) {
      Throwable root = this.rootCause(throwable);
      if (!(root instanceof EOFException) && !(root instanceof ClosedChannelException)) {
         String msg = root == null ? "" : this.safe(root.getMessage()).toLowerCase();
         return msg.contains("connection reset") || msg.contains("broken pipe") || msg.contains("forcibly closed") || msg.contains("socket closed");
      } else {
         return true;
      }
   }

   private String safe(String value) {
      return value == null ? "" : value.replace('\n', ' ').replace('\r', ' ').trim();
   }

   private String resolveControlEvent(boolean allowed, String note) {
      if (allowed) {
         return "controlApproved";
      }

      String normalized = note == null ? "" : note.trim().toLowerCase();
      return "disabled".equals(normalized) ? "controlDisabled" : "controlDenied";
   }
}
