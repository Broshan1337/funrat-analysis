package com.example.screen;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

@ServerEndpoint("/webcam-stream")
@Component
public class WebcamStreamWebSocketEndpoint {
   private static final ObjectMapper mapper = new ObjectMapper();
   private static final int MAX_FRAME_BYTES = 1048576;
   private static final long ASYNC_SEND_TIMEOUT_MS = 2000L;
   private static volatile WebcamSenderRegistry registry;

   public static void setRegistry(WebcamSenderRegistry senderRegistry) {
      registry = senderRegistry;
   }

   @OnOpen
   public void onOpen(Session session) {
      session.getAsyncRemote().setSendTimeout(2000L);
      WebcamSenderRegistry current = registry;
      if (current != null) {
         current.register(session);
         this.hydrateIdentityFromQuery(session);
      }
   }

   @OnClose
   public void onClose(Session session, CloseReason reason) {
      WebcamSenderRegistry current = registry;
      if (current != null) {
         current.unregister(session);
      }
   }

   @OnError
   public void onError(Session session, Throwable throwable) {
      if (session != null) {
         WebcamSenderRegistry current = registry;
         if (current != null) {
            current.unregister(session);
         }
      }
   }

   @OnMessage
   public void onMessage(Session session, String message) {
      JsonNode root = this.readJson(message);
      if (root != null) {
         String type = root.path("type").asText("");
         if ("hello".equals(type) || "identify".equals(type)) {
            this.handleIdentify(session, root);
         } else if ("devices".equals(type)) {
            this.handleDevices(session, root);
         }
      }
   }

   @OnMessage
   public void onBinary(Session session, byte[] data) {
      WebcamSenderRegistry current = registry;
      if (current != null) {
         if (data != null && data.length != 0) {
            if (data.length > 1048576) {
               this.closeQuietly(session, "webcam frame too large");
            } else {
               WebcamSenderRegistry.ClientInfo info = current.infoFor(session);
               if (info != null && !info.getAccessKey().isEmpty() && !info.getClientId().isEmpty()) {
                  WebcamWebSocketEndpoint.broadcastToViewers(info.getAccessKey(), info.getClientId(), data);
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
      WebcamSenderRegistry current = registry;
      if (current != null) {
         String key = root.path("key").asText("");
         String deviceName = root.path("deviceName").asText("");
         if (deviceName.isEmpty()) {
            deviceName = root.path("pcName").asText("");
         }

         if (deviceName.isEmpty()) {
            deviceName = root.path("name").asText("");
         }

         String clientId = root.path("clientId").asText("");
         if (clientId.isEmpty()) {
            clientId = root.path("targetId").asText("");
         }

         current.updateIdentity(session, key, deviceName, clientId);
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

      String clientId = this.firstParam(session, "clientId");
      if (clientId.isEmpty()) {
         clientId = this.firstParam(session, "targetId");
      }

      if (!key.isEmpty() || !deviceName.isEmpty() || !clientId.isEmpty()) {
         WebcamSenderRegistry current = registry;
         if (current != null) {
            current.updateIdentity(session, key, deviceName, clientId);
         }
      }
   }

   private void handleDevices(Session session, JsonNode root) {
      WebcamSenderRegistry current = registry;
      if (current != null) {
         WebcamSenderRegistry.ClientInfo info = current.infoFor(session);
         String key = WebcamSenderRegistry.resolveOwnerKeyAlias(root.path("key").asText(info != null ? info.getAccessKey() : ""));
         String clientId = root.path("clientId").asText(info != null ? info.getClientId() : "");
         if (!key.isEmpty() && !clientId.isEmpty()) {
            ObjectNode payload = mapper.createObjectNode();
            payload.put("type", "devices");
            payload.put("key", key);
            payload.put("clientId", clientId);
            if (root.has("devices")) {
               payload.set("devices", root.get("devices"));
            } else {
               payload.set("devices", mapper.createArrayNode());
            }

            WebcamWebSocketEndpoint.broadcastTextToViewers(key, clientId, payload.toString());
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

   private void closeQuietly(Session session, String reason) {
      if (session != null && session.isOpen()) {
         try {
            session.close(new CloseReason(CloseCodes.TOO_BIG, reason == null ? "closed" : reason));
         } catch (Exception var4) {
         }
      }
   }
}
