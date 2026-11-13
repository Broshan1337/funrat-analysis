package aurora;

import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public final class nutmeg {
   private nutmeg() {
   }

   public static CallSite fresco(Object var0, String var1, Object var2, String var3, int var4, int var5) {
      Lookup var6 = (Lookup)var0;
      MethodType var7 = (MethodType)var2;

      try {
         String var8 = ENC(var3,var4);
         int var9 = var8.indexOf(0);
         int var10 = var8.indexOf(0, var9 + 1);
         String var11 = var8.substring(0, var9).replace('/', '.');
         String var12 = var8.substring(var9 + 1, var10);
         String var13 = var8.substring(var10 + 1);
         Class var14 = var6.findClass(var11);
         MethodType var15 = descriptor(var6, var13);
         MethodHandle var16;
         if (var5 == 0) {
            var16 = var6.findStatic(var14, var12, var15);
         } else if (var5 == 1) {
            var16 = var6.findVirtual(var14, var12, var15);
         } else if (var5 == 2) {
            var16 = var6.findConstructor(var14, var15);
         } else if (var5 == 3) {
            var16 = var6.findSpecial(var14, var12, var15, var6.lookupClass());
         } else if (var5 == 4) {
            var16 = var6.findStaticGetter(var14, var12, var15.returnType());
         } else if (var5 == 5) {
            var16 = var6.findStaticSetter(var14, var12, var15.parameterType(0));
         } else if (var5 == 6) {
            var16 = var6.findGetter(var14, var12, var15.returnType());
         } else {
            var16 = var6.findSetter(var14, var12, var15.parameterType(1));
         }

         return new ConstantCallSite(var16.asType(var7));
      } catch (Throwable var17) {
         throw new RuntimeException(var1 + var7, var17);
      }
   }

   private static MethodType descriptor(Lookup var0, String var1) throws Throwable {
      ArrayList var2 = new ArrayList();
      int var3 = 1;

      while (var1.charAt(var3) != ')') {
         var3 = parse(var0, var1, var3, var2);
      }

      ArrayList var4 = new ArrayList(1);
      parse(var0, var1, var3 + 1, var4);
      return MethodType.methodType((Class<?>)var4.get(0), var2);
   }

   private static int parse(Lookup var0, String var1, int var2, List<Class<?>> var3) throws Throwable {
      int var4 = 0;

      while (var1.charAt(var2) == '[') {
         var4++;
         var2++;
      }

      char var5 = var1.charAt(var2);
      Class var6;
      int var7;
      switch (var5) {
         case 'B':
            var6 = byte.class;
            var7 = var2 + 1;
            break;
         case 'C':
            var6 = char.class;
            var7 = var2 + 1;
            break;
         case 'D':
            var6 = double.class;
            var7 = var2 + 1;
            break;
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'L':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'W':
         case 'X':
         case 'Y':
         default:
            int var8 = var1.indexOf(59, var2);
            var6 = var0.findClass(var1.substring(var2 + 1, var8).replace('/', '.'));
            var7 = var8 + 1;
            break;
         case 'F':
            var6 = float.class;
            var7 = var2 + 1;
            break;
         case 'I':
            var6 = int.class;
            var7 = var2 + 1;
            break;
         case 'J':
            var6 = long.class;
            var7 = var2 + 1;
            break;
         case 'S':
            var6 = short.class;
            var7 = var2 + 1;
            break;
         case 'V':
            var6 = void.class;
            var7 = var2 + 1;
            break;
         case 'Z':
            var6 = boolean.class;
            var7 = var2 + 1;
      }

      if (var4 > 0) {
         var6 = Array.newInstance(var6).getClass();
      }

      var3.add(var6);
      return var7;
   }
}
