package aurora;

import java.nio.charset.Charset;

public final class fable {
   private static final Charset CS = Charset.forName("UTF-8");

   private fable() {
   }

   public static String stream(String var0, int var1) {
      int var2 = var0.length();
      byte[] var3 = new byte[var2];
      int var4 = 0;
      if (var0.charAt(0) >= 256) {
         for (int var5 = 0; var5 < var2; var5++) {
            var3[var4] = (byte)((var0.charAt(var5) - 256 ^ var1 >>> (var4 & 3) * 8) & 0xFF);
            var4++;
         }
      } else {
         int var9 = -1;

         for (int var6 = 0; var6 < var2; var6++) {
            char var7 = var0.charAt(var6);
            if (var7 != '-') {
               int var8 = var7 - 'a';
               if (var9 < 0) {
                  var9 = var8;
               } else {
                  var3[var4] = (byte)(((var9 << 4 | var8) ^ var1 >>> (var4 & 3) * 8) & 0xFF);
                  var4++;
                  var9 = -1;
               }
            }
         }
      }

      return new String(var3, 0, var4, CS);
   }
}
