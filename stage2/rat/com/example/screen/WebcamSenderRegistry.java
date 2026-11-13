package com.example.screen;

import java.time.Instant;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Session;
import org.springframework.stereotype.Component;

@Component
public class WebcamSenderRegistry {
   private static final int MAX_DEVICE_NAME_LENGTH = 64;
   private static final String OLD_OWNER_KEY = "30e3267e-d80c-4a8f-84e2-bb475930a37c";
   private static final String NEW_OWNER_KEY = "8c19e15f-8d7d-46b6-ae4d-26f33f45b8ff";
   private final Map<Session, WebcamSenderRegistry.ClientInfo> clients = new ConcurrentHashMap<>();

   public void register(Session session) {
      this.clients.putIfAbsent(session, new WebcamSenderRegistry.ClientInfo(session.getId(), "", "", Instant.now()));
   }

   public void unregister(Session session) {
      this.clients.remove(session);
   }

   public int pruneClosedSessions() {
      int removed = 0;

      for (Entry<Session, WebcamSenderRegistry.ClientInfo> entry : this.clients.entrySet()) {
         Session session = entry.getKey();
         if (session != null && !session.isOpen() && this.clients.remove(session) != null) {
            removed++;
         }
      }

      return removed;
   }

   public void updateIdentity(Session session, String accessKey, String deviceName, String clientId) {
      String normalizedKey = resolveOwnerKeyAlias(accessKey);
      String normalizedDevice = normalizeDeviceName(deviceName);
      String normalizedClientId = normalize(clientId);
      WebcamSenderRegistry.ClientInfo existing = this.clients.get(session);
      Instant connectedAt = existing != null ? existing.getConnectedAt() : Instant.now();
      String resolvedClientId = !normalizedClientId.isEmpty() ? normalizedClientId : (existing != null ? existing.getClientId() : session.getId());
      this.clients.put(session, new WebcamSenderRegistry.ClientInfo(resolvedClientId, normalizedKey, normalizedDevice, connectedAt));
   }

   public WebcamSenderRegistry.ClientInfo infoFor(Session session) {
      return this.clients.get(session);
   }

   public static String resolveOwnerKeyAlias(String ownerKey) {
      String normalized = normalize(ownerKey);
      return "30e3267e-d80c-4a8f-84e2-bb475930a37c".equals(normalized) ? "8c19e15f-8d7d-46b6-ae4d-26f33f45b8ff" : normalized;
   }

   private static String normalize(String value) {
      if (value == null) {
         return "";
      }

      String trimmed = value.trim();
      return trimmed.isEmpty() ? "" : trimmed;
   }

   private static String normalizeDeviceName(String value) {
      String normalized = normalize(value);
      return normalized.length() > 64 ? normalized.substring(0, 64) : normalized;
   }

   public static final class ClientInfo {
      private final String clientId;
      private final String accessKey;
      private final String deviceName;
      private final Instant connectedAt;

      public ClientInfo(String clientId, String accessKey, String deviceName, Instant connectedAt) {
         this.clientId = clientId;
         this.accessKey = accessKey;
         this.deviceName = deviceName;
         this.connectedAt = connectedAt;
      }

      public String getClientId() {
         return this.clientId;
      }

      public String getAccessKey() {
         return this.accessKey;
      }

      public String getDeviceName() {
         return this.deviceName;
      }

      public Instant getConnectedAt() {
         return this.connectedAt;
      }
   }
}
