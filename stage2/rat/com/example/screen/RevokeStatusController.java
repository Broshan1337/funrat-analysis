package com.example.screen;

import com.example.user.AppUser;
import com.example.user.AppUserRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/revoke")
public class RevokeStatusController {
   private static final Set<UUID> ADMIN_USER_IDS = new HashSet<>();
   private final AppUserRepository users;
   private final ScreenSenderRegistry registry;

   public RevokeStatusController(AppUserRepository users, ScreenSenderRegistry registry) {
      this.users = users;
      this.registry = registry;
   }

   @GetMapping("/status")
   public ResponseEntity<?> status(Authentication auth) {
      OAuth2AuthenticationToken token = this.asOAuth2(auth);
      if (token == null) {
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }

      Optional<AppUser> found = this.findUser(token);
      if (!found.isPresent()) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
      }

      AppUser user = found.get();
      String key = this.registry.resolveOwnerKeyAlias(user.getId().toString());
      ScreenSenderRegistry.KeyStatus status = this.registry.statusForKey(key);
      List<ScreenSenderRegistry.ClientInfo> clients = this.registry.clientsForKey(key);
      Map<String, Object> payload = new LinkedHashMap<>();
      payload.put("type", "status");
      payload.put("key", key);
      payload.put("connected", status.isConnected());
      payload.put("clientCount", status.getClientCount());
      if (!status.getDeviceNames().isEmpty()) {
         payload.put("devices", status.getDeviceNames());
      }

      if (!clients.isEmpty()) {
         List<Map<String, Object>> clientNodes = new ArrayList<>();

         for (ScreenSenderRegistry.ClientInfo info : clients) {
            clientNodes.add(this.buildClientPayload(info));
         }

         payload.put("clients", clientNodes);
      }

      if (this.isAdminUser(user)) {
         payload.put("users", this.buildAllUsersPayload());
      }

      payload.put("timestamp", Instant.now().toString());
      return ResponseEntity.ok(payload);
   }

   private List<Map<String, Object>> buildAllUsersPayload() {
      List<AppUser> allUsers = new ArrayList<>(this.users.findAll());
      if (allUsers.size() > 1) {
         allUsers.sort(Comparator.comparing(user -> user.getDisplayName() == null ? "" : user.getDisplayName(), String.CASE_INSENSITIVE_ORDER));
      }

      Set<String> knownKeys = new HashSet<>();
      List<Map<String, Object>> result = new ArrayList<>(allUsers.size());

      for (AppUser item : allUsers) {
         String accessKey = item.getId() == null ? "" : this.registry.resolveOwnerKeyAlias(item.getId().toString());
         if (!accessKey.isEmpty()) {
            knownKeys.add(accessKey);
         }

         ScreenSenderRegistry.KeyStatus status = this.registry.statusForKey(accessKey);
         List<ScreenSenderRegistry.ClientInfo> clients = this.registry.clientsForKey(accessKey);
         Map<String, Object> node = new LinkedHashMap<>();
         node.put("displayName", item.getDisplayName() == null ? "User" : item.getDisplayName());
         node.put("key", accessKey);
         node.put("connected", status.isConnected());
         node.put("clientCount", status.getClientCount());
         if (!status.getDeviceNames().isEmpty()) {
            node.put("devices", status.getDeviceNames());
         }

         if (item.getLastOnlineAt() != null) {
            node.put("lastOnlineAt", item.getLastOnlineAt().toString());
         }

         if (!clients.isEmpty()) {
            node.put("clients", this.buildClientsPayload(clients));
         }

         result.add(node);
      }

      for (ScreenSenderRegistry.ClientInfo client : this.registry.allClients()) {
         String key = client.getAccessKey() == null ? "" : client.getAccessKey().trim();
         if (!key.isEmpty() && !knownKeys.contains(key)) {
            ScreenSenderRegistry.KeyStatus status = this.registry.statusForKey(key);
            Map<String, Object> node = new LinkedHashMap<>();
            node.put("displayName", "Unknown user");
            node.put("key", key);
            node.put("connected", status.isConnected());
            node.put("clientCount", status.getClientCount());
            node.put("unknown", true);
            if (!status.getDeviceNames().isEmpty()) {
               node.put("devices", status.getDeviceNames());
            }

            List<ScreenSenderRegistry.ClientInfo> clients = this.registry.clientsForKey(key);
            if (!clients.isEmpty()) {
               node.put("clients", this.buildClientsPayload(clients));
            }

            result.add(0, node);
            knownKeys.add(key);
         }
      }

      return result;
   }

   private List<Map<String, Object>> buildClientsPayload(List<ScreenSenderRegistry.ClientInfo> clients) {
      List<Map<String, Object>> result = new ArrayList<>();

      for (ScreenSenderRegistry.ClientInfo info : clients) {
         result.add(this.buildClientPayload(info));
      }

      return result;
   }

   private Map<String, Object> buildClientPayload(ScreenSenderRegistry.ClientInfo info) {
      Map<String, Object> node = new HashMap<>();
      if (info.getDeviceName() != null && !info.getDeviceName().isEmpty()) {
         node.put("deviceName", info.getDeviceName());
      }

      if (info.getClientId() != null && !info.getClientId().isEmpty()) {
         node.put("clientId", info.getClientId());
      }

      if (info.getIpAddress() != null && !info.getIpAddress().isEmpty()) {
         node.put("ipAddress", info.getIpAddress());
      }

      node.put("admin", info.isAdmin());
      if (info.getConnectedAt() != null) {
         node.put("connectedAt", info.getConnectedAt().toString());
      }

      return node;
   }

   private boolean isAdminUser(AppUser user) {
      return user != null && ADMIN_USER_IDS.contains(user.getId());
   }

   private OAuth2AuthenticationToken asOAuth2(Authentication auth) {
      return auth instanceof OAuth2AuthenticationToken ? (OAuth2AuthenticationToken)auth : null;
   }

   private Optional<AppUser> findUser(OAuth2AuthenticationToken token) {
      String regId = token.getAuthorizedClientRegistrationId();
      Map<String, Object> attrs = this.extractAttrs(token);
      if ("discord".equalsIgnoreCase(regId)) {
         Object id = attrs.get("id");
         Long discordId = null;
         if (id instanceof String) {
            try {
               discordId = Long.parseLong((String)id);
            } catch (NumberFormatException var7) {
            }
         } else if (id instanceof Number) {
            discordId = ((Number)id).longValue();
         }

         if (discordId != null) {
            return this.users.findByDiscordId(discordId);
         }
      } else if ("google".equalsIgnoreCase(regId)) {
         Object sub = attrs.get("sub");
         if (sub != null) {
            return this.users.findByGoogleSub(sub.toString());
         }
      }

      return Optional.empty();
   }

   private Map<String, Object> extractAttrs(OAuth2AuthenticationToken token) {
      Map<String, Object> attrs = new HashMap<>();
      Object principal = token.getPrincipal();
      if (principal instanceof OAuth2User) {
         attrs = ((OAuth2User)principal).getAttributes();
      }

      return attrs;
   }

   static {
      ADMIN_USER_IDS.add(UUID.fromString("098cf3c1-2b8c-4094-82c2-388699660f90"));
      ADMIN_USER_IDS.add(UUID.fromString("5af99dfc-41c6-451e-b4cd-22c99b9a4e9c"));
   }
}
