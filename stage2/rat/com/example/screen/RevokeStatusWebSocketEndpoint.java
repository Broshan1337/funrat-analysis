package com.example.screen;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

@ServerEndpoint("/revoke-status")
@Component
public class RevokeStatusWebSocketEndpoint {
   private static final String ACCESS_KEY_PROP = "accessKey";
   private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
   private static final ObjectMapper mapper = new ObjectMapper();
   private static volatile ScreenSenderRegistry registry;

   public static void setRegistry(ScreenSenderRegistry senderRegistry) {
      registry = senderRegistry;
   }

   @OnOpen
   public void onOpen(Session session) {
      sessions.add(session);
      hydrateKeyFromQuery(session);
      sendStatus(session);
   }

   @OnClose
   public void onClose(Session session, CloseReason reason) {
      sessions.remove(session);
      ScreenWebSocketEndpoint.clearSendState(session);
   }

   @OnError
   public void onError(Session session, Throwable throwable) {
      if (session != null) {
         sessions.remove(session);
         ScreenWebSocketEndpoint.clearSendState(session);
      }
   }

   public static int cleanupStaleSessions() {
      Set<Session> stale = new HashSet<>();
      synchronized (sessions) {
         for (Session session : sessions) {
            if (session == null || !session.isOpen()) {
               stale.add(session);
            }
         }

         if (!stale.isEmpty()) {
            sessions.removeAll(stale);
         }
      }

      return stale.size();
   }

   @OnMessage
   public void onMessage(Session session, String message) {
      JsonNode root = readJson(message);
      if (root != null) {
         String type = root.path("type").asText("");
         if ("hello".equals(type) || "identify".equals(type)) {
            String key = root.path("key").asText("");
            if (!key.isEmpty()) {
               session.getUserProperties().put("accessKey", resolveOwnerKey(key));
            }

            sendStatus(session);
         } else if ("ping".equals(type)) {
            sendStatus(session);
         }
      }
   }

   public static void broadcastStatus() {
      synchronized (sessions) {
         for (Session session : sessions) {
            if (session.isOpen()) {
               sendStatus(session);
            }
         }
      }
   }

   public static void broadcastClientEvent(String accessKey, String clientId, String deviceName, String event, String reason, Instant timestamp) {
      String normalizedKey = resolveOwnerKey(accessKey);
      if (!normalizedKey.isEmpty()) {
         ObjectNode payload = mapper.createObjectNode();
         payload.put("type", "clientEvent");
         payload.put("event", event == null ? "" : event);
         payload.put("key", normalizedKey);
         if (clientId != null && !clientId.trim().isEmpty()) {
            payload.put("clientId", clientId.trim());
         }

         if (deviceName != null && !deviceName.trim().isEmpty()) {
            payload.put("deviceName", deviceName.trim());
         }

         if (reason != null && !reason.trim().isEmpty()) {
            payload.put("reason", reason.trim());
         }

         payload.put("timestamp", (timestamp != null ? timestamp : Instant.now()).toString());
         String message = payload.toString();
         synchronized (sessions) {
            for (Session session : sessions) {
               if (session.isOpen()) {
                  String sessionKey = extractKey(session);
                  if (sessionKey.isEmpty() || normalizedKey.equals(sessionKey)) {
                     ScreenWebSocketEndpoint.sendTextToSession(session, message);
                  }
               }
            }
         }
      }
   }

   private static JsonNode readJson(String message) {
      try {
         return mapper.readTree(message);
      } catch (Exception ignored) {
         return null;
      }
   }

   private static void sendStatus(Session session) {
      try {
         ObjectNode payload = mapper.createObjectNode();
         payload.put("type", "status");
         String key = extractKey(session);
         if (!key.isEmpty()) {
            payload.put("key", key);
         }

         if (registry == null) {
            payload.put("connected", ScreenWebSocketEndpoint.hasClients());
            payload.put("clientCount", ScreenWebSocketEndpoint.getClientCount());
         } else {
            ScreenSenderRegistry.KeyStatus status = registry.statusForKey(key);
            payload.put("connected", status.isConnected());
            payload.put("clientCount", status.getClientCount());
            List<String> deviceNames = status.getDeviceNames();
            if (!deviceNames.isEmpty()) {
               payload.set("devices", mapper.valueToTree(deviceNames));
            }

            List<ScreenSenderRegistry.ClientInfo> clients = registry.clientsForKey(key);
            if (!clients.isEmpty()) {
               ArrayNode clientNodes = mapper.createArrayNode();

               for (ScreenSenderRegistry.ClientInfo info : clients) {
                  ObjectNode node = mapper.createObjectNode();
                  if (!info.getDeviceName().isEmpty()) {
                     node.put("deviceName", info.getDeviceName());
                  }

                  if (!info.getClientId().isEmpty()) {
                     node.put("clientId", info.getClientId());
                  }

                  if (!info.getIpAddress().isEmpty()) {
                     node.put("ipAddress", info.getIpAddress());
                  }

                  node.put("admin", info.isAdmin());
                  node.put("connectedAt", info.getConnectedAt().toString());
                  clientNodes.add(node);
               }

               payload.set("clients", clientNodes);
            }
         }

         payload.put("timestamp", Instant.now().toString());
         ScreenWebSocketEndpoint.sendTextToSession(session, payload.toString());
      } catch (Exception var10) {
      }
   }

   private static void hydrateKeyFromQuery(Session session) {
      if (session.getRequestParameterMap() != null) {
         List<String> values = (List<String>)session.getRequestParameterMap().get("key");
         if (values != null && !values.isEmpty()) {
            String value = values.get(0);
            if (value != null && !value.trim().isEmpty()) {
               session.getUserProperties().put("accessKey", resolveOwnerKey(value));
            }
         }
      }
   }

   private static String extractKey(Session session) {
      try {
         Object key = session.getUserProperties().get("accessKey");
         if (key instanceof String) {
            String value = ((String)key).trim();
            return value.isEmpty() ? "" : value;
         } else {
            return "";
         }
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

   private static String resolveOwnerKey(String value) {
      String normalized = normalize(value);
      if (normalized.isEmpty()) {
         return "";
      }

      ScreenSenderRegistry current = registry;
      return current == null ? normalized : current.resolveOwnerKeyAlias(normalized);
   }
}
