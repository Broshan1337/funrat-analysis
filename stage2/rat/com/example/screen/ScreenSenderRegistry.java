package com.example.screen;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.Session;
import org.springframework.stereotype.Component;

@Component
public class ScreenSenderRegistry {
   private static final int MAX_DEVICE_NAME_LENGTH = 64;
   private static final int MAX_IP_LENGTH = 64;
   private static final String OLD_OWNER_KEY = "30e3267e-d80c-4a8f-84e2-bb475930a37c";
   private static final String NEW_OWNER_KEY = "8c19e15f-8d7d-46b6-ae4d-26f33f45b8ff";
   private final Map<Session, ScreenSenderRegistry.ClientInfo> clients = new ConcurrentHashMap<>();
   private final Map<String, String> stickyClientIds = new ConcurrentHashMap<>();
   private final Map<String, String> ownerKeyAliases = new ConcurrentHashMap<>();

   public ScreenSenderRegistry() {
      this.putOwnerKeyAlias("30e3267e-d80c-4a8f-84e2-bb475930a37c", "8c19e15f-8d7d-46b6-ae4d-26f33f45b8ff");
   }

   public void register(Session session) {
      this.clients.putIfAbsent(session, new ScreenSenderRegistry.ClientInfo(session.getId(), "", "", "", Instant.now(), false));
   }

   public void unregister(Session session) {
      this.clients.remove(session);
   }

   public int pruneClosedSessions() {
      int removed = 0;

      for (Entry<Session, ScreenSenderRegistry.ClientInfo> entry : this.clients.entrySet()) {
         Session session = entry.getKey();
         if (session != null && !session.isOpen() && this.clients.remove(session) != null) {
            removed++;
         }
      }

      return removed;
   }

   public void updateIdentity(Session session, String accessKey, String deviceName, String clientId, boolean admin) {
      this.updateIdentity(session, accessKey, deviceName, clientId, admin, "");
   }

   public String resolveServerClientId(String accessKey, String deviceName, String ipAddress) {
      String normalizedKey = this.resolveOwnerKeyAlias(accessKey);
      if (normalizedKey.isEmpty()) {
         return "";
      }

      String normalizedDevice = normalizeDeviceName(deviceName);
      String normalizedIp = normalizeIp(ipAddress);
      String fingerprint = buildFingerprint(normalizedKey, normalizedDevice, normalizedIp);
      return fingerprint.isEmpty() ? "" : this.stickyClientIds.computeIfAbsent(fingerprint, k -> generateClientId());
   }

   public void updateIdentity(Session session, String accessKey, String deviceName, String clientId, boolean admin, String ipAddress) {
      String normalizedKey = this.resolveOwnerKeyAlias(accessKey);
      String normalizedDevice = normalizeDeviceName(deviceName);
      String normalizedIp = normalizeIp(ipAddress);
      ScreenSenderRegistry.ClientInfo existing = this.clients.get(session);
      Instant connectedAt = existing != null ? existing.getConnectedAt() : Instant.now();
      String effectiveClientId = normalize(clientId);
      if (effectiveClientId.isEmpty()) {
         effectiveClientId = existing != null ? existing.getClientId() : session.getId();
      }

      if (normalizedIp.isEmpty() && existing != null) {
         normalizedIp = existing.getIpAddress();
      }

      this.clients.put(session, new ScreenSenderRegistry.ClientInfo(effectiveClientId, normalizedKey, normalizedDevice, normalizedIp, connectedAt, admin));
      if (!normalizedKey.isEmpty() && !effectiveClientId.isEmpty()) {
         this.removeDuplicateIdentities(session, normalizedKey, effectiveClientId);
      }
   }

   public ScreenSenderRegistry.ClientInfo infoFor(Session session) {
      return this.clients.get(session);
   }

   public Session sessionFor(String accessKey, String clientId) {
      String normalizedKey = this.resolveOwnerKeyAlias(accessKey);
      String normalizedId = normalize(clientId);
      if (!normalizedKey.isEmpty() && !normalizedId.isEmpty()) {
         Session bestSession = null;
         Instant bestConnectedAt = null;

         for (Entry<Session, ScreenSenderRegistry.ClientInfo> entry : this.clients.entrySet()) {
            ScreenSenderRegistry.ClientInfo info = entry.getValue();
            if (normalizedKey.equals(info.getAccessKey()) && normalizedId.equals(info.getClientId())) {
               Session candidate = entry.getKey();
               if (candidate != null
                  && candidate.isOpen()
                  && (bestSession == null || bestConnectedAt == null || info.getConnectedAt().isAfter(bestConnectedAt))) {
                  bestSession = candidate;
                  bestConnectedAt = info.getConnectedAt();
               }
            }
         }

         return bestSession;
      } else {
         return null;
      }
   }

   public boolean hasClients() {
      return !this.clients.isEmpty();
   }

   public int getClientCount() {
      return this.clients.size();
   }

   public ScreenSenderRegistry.KeyStatus statusForKey(String accessKey) {
      String normalizedKey = this.resolveOwnerKeyAlias(accessKey);
      if (normalizedKey.isEmpty()) {
         return new ScreenSenderRegistry.KeyStatus(false, 0, Collections.emptyList());
      }

      int count = 0;
      Set<String> names = new LinkedHashSet<>();

      for (Entry<Session, ScreenSenderRegistry.ClientInfo> entry : this.clients.entrySet()) {
         Session session = entry.getKey();
         if (session != null) {
            if (!session.isOpen()) {
               this.clients.remove(session);
            } else {
               ScreenSenderRegistry.ClientInfo info = entry.getValue();
               if (normalizedKey.equals(info.getAccessKey())) {
                  count++;
                  if (!info.getDeviceName().isEmpty()) {
                     names.add(info.getDeviceName());
                  }
               }
            }
         }
      }

      List<String> deviceNames = names.isEmpty() ? Collections.emptyList() : new ArrayList<>(names);
      return new ScreenSenderRegistry.KeyStatus(count > 0, count, deviceNames);
   }

   public List<ScreenSenderRegistry.ClientInfo> clientsForKey(String accessKey) {
      String normalizedKey = this.resolveOwnerKeyAlias(accessKey);
      if (normalizedKey.isEmpty()) {
         return Collections.emptyList();
      }

      List<ScreenSenderRegistry.ClientInfo> matches = new ArrayList<>();

      for (Entry<Session, ScreenSenderRegistry.ClientInfo> entry : this.clients.entrySet()) {
         Session session = entry.getKey();
         if (session != null) {
            if (!session.isOpen()) {
               this.clients.remove(session);
            } else {
               ScreenSenderRegistry.ClientInfo info = entry.getValue();
               if (normalizedKey.equals(info.getAccessKey())) {
                  matches.add(info);
               }
            }
         }
      }

      if (matches.size() > 1) {
         matches.sort(Comparator.comparing(ScreenSenderRegistry.ClientInfo::getConnectedAt));
      }

      return matches.isEmpty() ? Collections.emptyList() : matches;
   }

   public List<ScreenSenderRegistry.ClientInfo> allClients() {
      if (this.clients.isEmpty()) {
         return Collections.emptyList();
      }

      List<ScreenSenderRegistry.ClientInfo> list = new ArrayList<>();

      for (Entry<Session, ScreenSenderRegistry.ClientInfo> entry : this.clients.entrySet()) {
         Session session = entry.getKey();
         if (session != null) {
            if (!session.isOpen()) {
               this.clients.remove(session);
            } else {
               list.add(entry.getValue());
            }
         }
      }

      if (list.isEmpty()) {
         return Collections.emptyList();
      }

      if (list.size() > 1) {
         list.sort(Comparator.comparing(ScreenSenderRegistry.ClientInfo::getConnectedAt));
      }

      return list;
   }

   public void putOwnerKeyAlias(String oldOwnerKey, String newOwnerKey) {
      String oldKey = normalize(oldOwnerKey);
      String newKey = normalize(newOwnerKey);
      if (!oldKey.isEmpty() && !newKey.isEmpty() && !oldKey.equals(newKey)) {
         this.ownerKeyAliases.put(oldKey, newKey);
      }
   }

   public void removeOwnerKeyAlias(String oldOwnerKey) {
      String oldKey = normalize(oldOwnerKey);
      if (!oldKey.isEmpty()) {
         this.ownerKeyAliases.remove(oldKey);
      }
   }

   public Map<String, String> ownerKeyAliases() {
      return this.ownerKeyAliases.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(new ConcurrentHashMap<>(this.ownerKeyAliases));
   }

   public String resolveOwnerKeyAlias(String ownerKey) {
      String resolved = normalize(ownerKey);
      if (resolved.isEmpty()) {
         return "";
      }

      Set<String> seen = new LinkedHashSet<>();

      while (!resolved.isEmpty() && seen.add(resolved)) {
         String next = normalize(this.ownerKeyAliases.get(resolved));
         if (next.isEmpty()) {
            return resolved;
         }

         resolved = next;
      }

      return resolved;
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

   private static String normalizeIp(String value) {
      String normalized = normalize(value);
      return normalized.length() > 64 ? normalized.substring(0, 64) : normalized;
   }

   private static String buildFingerprint(String accessKey, String deviceName, String ipAddress) {
      if (accessKey == null || accessKey.isEmpty()) {
         return "";
      } else if (deviceName != null && !deviceName.isEmpty()) {
         return accessKey + "|device|" + deviceName.toLowerCase(Locale.ROOT);
      } else {
         return ipAddress != null && !ipAddress.isEmpty() ? accessKey + "|ip|" + ipAddress : "";
      }
   }

   private static String generateClientId() {
      return UUID.randomUUID().toString();
   }

   private void removeDuplicateIdentities(Session currentSession, String accessKey, String clientId) {
      for (Entry<Session, ScreenSenderRegistry.ClientInfo> entry : this.clients.entrySet()) {
         Session session = entry.getKey();
         if (session != null && session != currentSession) {
            ScreenSenderRegistry.ClientInfo info = entry.getValue();
            if (info != null && accessKey.equals(info.getAccessKey()) && clientId.equals(info.getClientId())) {
               this.clients.remove(session);
            }
         }
      }
   }

   public static final class ClientInfo {
      private final String clientId;
      private final String accessKey;
      private final String deviceName;
      private final String ipAddress;
      private final Instant connectedAt;
      private final boolean admin;

      public ClientInfo(String clientId, String accessKey, String deviceName, String ipAddress, Instant connectedAt, boolean admin) {
         this.clientId = clientId;
         this.accessKey = accessKey;
         this.deviceName = deviceName;
         this.ipAddress = ipAddress;
         this.connectedAt = connectedAt;
         this.admin = admin;
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

      public String getIpAddress() {
         return this.ipAddress;
      }

      public Instant getConnectedAt() {
         return this.connectedAt;
      }

      public boolean isAdmin() {
         return this.admin;
      }
   }

   public static final class KeyStatus {
      private final boolean connected;
      private final int clientCount;
      private final List<String> deviceNames;

      public KeyStatus(boolean connected, int clientCount, List<String> deviceNames) {
         this.connected = connected;
         this.clientCount = clientCount;
         this.deviceNames = deviceNames;
      }

      public boolean isConnected() {
         return this.connected;
      }

      public int getClientCount() {
         return this.clientCount;
      }

      public List<String> getDeviceNames() {
         return this.deviceNames;
      }
   }
}
