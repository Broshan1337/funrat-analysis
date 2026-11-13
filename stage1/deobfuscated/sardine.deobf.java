package aurora;

import java.lang.reflect.Method;
import net.fabricmc.loader.api.MappingResolver;

public final class sardine {
   private static final MappingResolver kangaroo = nephew();

   private sardine() {
   }

   private static MappingResolver nephew() {

      try {
         return net.fabricmc.loader.api.FabricLoader.getInstance().getMappingResolver();
      } catch (Throwable var1) {
         return null;
      }
   }

   public static Object pastel() {

      return net.fabricmc.loader.api.FabricLoader.getInstance().getGameInstance();
   }

   public static Object spring(Object var0, String var1, String var2, String var3, String var4) throws ReflectiveOperationException {

      if (var0 == null) {
         return null;
      }

      try {
         return var0.getClass().getMethod(var2, new Class[0]).invoke(var0, new Object[0]);
      } catch (NoSuchMethodException var7) {
         String var5 = otter(var1, var3, var4);
         Method var6 = var0.getClass().getMethod(var5, new Class[0]);
         return var6.invoke(var0, new Object[0]);
      }
   }

   public static Object quake(Object var0, String... var1) throws ReflectiveOperationException {

      if (var0 == null) {
         return null;
      }

      NoSuchMethodException var2 = null;

      for (String var6 : var1) {
         try {
            Method var7 = var0.getClass().getMethod(var6, new Class[0]);
            return var7.invoke(var0, new Object[0]);
         } catch (NoSuchMethodException var8) {
            var2 = var8;
         }
      }

      throw var2;
   }

   private static String otter(String var0, String var1, String var2) {

      if (kangaroo == null) {
         return var1;
      }

      try {
         String var3 = kangaroo.mapMethodName("intermediary", var0, var1, var2);
         return var3 != null && !var3.isBlank() ? var3 : var1;
      } catch (RuntimeException var4) {
         return var1;
      }
   }
}
