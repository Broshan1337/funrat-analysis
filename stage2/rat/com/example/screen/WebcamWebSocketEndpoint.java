package com.example.screen;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
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

@ServerEndpoint("/webcam")
@Component
public class WebcamWebSocketEndpoint {
   private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
   private static final Map<Session, AtomicBoolean> binaryInFlight = new ConcurrentHashMap<>();
   private static final long ASYNC_SEND_TIMEOUT_MS = 2000L;
   private static final ObjectMapper mapper = new ObjectMapper();
   private static final String VIEWER_KEY_PROP = "viewerKey";
   private static final String VIEWER_TARGET_PROP = "viewerTargetId";

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
   }

   @OnError
   public void onError(Session session, Throwable throwable) {
      if (session != null) {
         sessions.remove(session);
         binaryInFlight.remove(session);
      }
   }

   public static int cleanupStaleSessions() {
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
         }
      }

      for (Entry<Session, AtomicBoolean> entry : binaryInFlight.entrySet()) {
         Session session = entry.getKey();
         if (session != null && !session.isOpen() && binaryInFlight.remove(session) != null) {
            removed++;
         }
      }

      return removed;
   }

   @OnMessage
   public void onMessage(Session session, String message) {
      JsonNode root = this.readJson(message);
      if (root != null) {
         String type = root.path("type").asText("");
         if ("hello".equals(type) || "identify".equals(type)) {
            this.handleIdentify(session, root);
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
      String key = root.path("key").asText("");
      String targetId = root.path("clientId").asText("");
      if (targetId.isEmpty()) {
         targetId = root.path("targetId").asText("");
      }

      if (!key.isEmpty()) {
         session.getUserProperties().put("viewerKey", WebcamSenderRegistry.resolveOwnerKeyAlias(key));
      }

      if (!targetId.isEmpty()) {
         session.getUserProperties().put("viewerTargetId", normalize(targetId));
      }
   }

   private void hydrateViewerFromQuery(Session session) {
      String key = this.firstParam(session, "key");
      String targetId = this.firstParam(session, "clientId");
      if (targetId.isEmpty()) {
         targetId = this.firstParam(session, "targetId");
      }

      if (!key.isEmpty()) {
         session.getUserProperties().put("viewerKey", WebcamSenderRegistry.resolveOwnerKeyAlias(key));
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

   public static void broadcastToViewers(String accessKey, String senderId, byte[] data) {
      String normalizedKey = normalize(accessKey);
      String normalizedSender = normalize(senderId);
      if (!normalizedKey.isEmpty() && !normalizedSender.isEmpty()) {
         synchronized (sessions) {
            for (Session s : sessions) {
               String viewerKey = getViewerProp(s, "viewerKey");
               String targetId = getViewerProp(s, "viewerTargetId");
               if (normalizedKey.equals(viewerKey) && normalizedSender.equals(targetId)) {
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

   private static String getViewerProp(Session session, String name) {
      try {
         Object value = session.getUserProperties().get(name);
         return value instanceof String ? normalize((String)value) : "";
      } catch (IllegalStateException ignored) {
         return "";
      }
   }

   private static String normalize(String value) {
      if (value == null) {
         return "";
      }

      String trimmed = value.trim();
      return trimmed.isEmpty() ? "" : trimmed;
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
                        closeQuietly(session);
                     }
                  });
               } catch (Exception ignored) {
                  inFlight.set(false);
                  closeQuietly(session);
               }
            }
         }
      }
   }

   private static void closeQuietly(Session session) {
      if (session != null && session.isOpen()) {
         try {
            session.close(new CloseReason(CloseCodes.UNEXPECTED_CONDITION, "binary send failed"));
         } catch (Exception var2) {
         }
      }
   }

   private static void sendTextSafely(Session session, String message) {
      if (session != null && message != null && !message.trim().isEmpty()) {
         if (session.isOpen()) {
            try {
               session.getAsyncRemote().sendText(message, result -> {
                  if (result != null && !result.isOK()) {
                     closeQuietly(session);
                  }
               });
            } catch (Exception ignored) {
               closeQuietly(session);
            }
         }
      }
   }
}
