package com.example.screen;

import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

final class PadlockNative {
   static final int TARGET_KEYBOARD = 1;
   static final int TARGET_MOUSE = 2;
   static final int TARGET_SCREEN = 4;
   private static volatile PadlockNative.Api api;
   private static volatile String loadedDllPath;
   private static volatile String lastError;

   private PadlockNative() {
   }

   static boolean isAvailable() {
      return api != null;
   }

   static String getLoadedDllPath() {
      return loadedDllPath;
   }

   static String getLastError() {
      return lastError;
   }

   static synchronized boolean loadIfPresent() {
      if (api != null) {
         return true;
      }

      if (!isWindows()) {
         lastError = "not_windows";
         return false;
      }

      String path = resolveDllPath();
      if (path != null && !path.isEmpty()) {
         return load(path);
      }

      lastError = "dll_not_found";
      return false;
   }

   static synchronized boolean load(String dllPath) {
      if (!isWindows()) {
         lastError = "not_windows";
         return false;
      }

      if (dllPath != null && !dllPath.trim().isEmpty()) {
         try {
            Path p = Paths.get(dllPath).toAbsolutePath().normalize();
            if (!Files.exists(p)) {
               lastError = "dll_missing:" + p;
               return false;
            }

            String normalized = p.toString();
            if (api != null && normalized.equalsIgnoreCase(loadedDllPath)) {
               return true;
            }

            api = (PadlockNative.Api)Native.load(normalized, PadlockNative.Api.class, W32APIOptions.DEFAULT_OPTIONS);
            loadedDllPath = normalized;
            lastError = null;
            return true;
         } catch (Throwable t) {
            api = null;
            loadedDllPath = null;
            lastError = "dll_load_failed:" + t.getClass().getSimpleName() + ":" + safeMsg(t.getMessage());
            return false;
         }
      } else {
         lastError = "dll_path_empty";
         return false;
      }
   }

   static boolean startTargets(int targets) {
      PadlockNative.Api a = api;
      if (a == null) {
         return false;
      }

      try {
         return a.PadlockStart(targets);
      } catch (Throwable t) {
         lastError = "start_failed:" + t.getClass().getSimpleName() + ":" + safeMsg(t.getMessage());
         return false;
      }
   }

   static boolean setTargets(int targets) {
      PadlockNative.Api a = api;
      if (a == null) {
         return false;
      }

      try {
         return a.PadlockSetTargets(targets);
      } catch (Throwable t) {
         lastError = "set_failed:" + t.getClass().getSimpleName() + ":" + safeMsg(t.getMessage());
         return false;
      }
   }

   static void stopAll() {
      PadlockNative.Api a = api;
      if (a != null) {
         try {
            a.PadlockStop();
         } catch (Throwable t) {
            lastError = "stop_failed:" + t.getClass().getSimpleName() + ":" + safeMsg(t.getMessage());
         }
      }
   }

   static boolean isRunning() {
      PadlockNative.Api a = api;
      if (a == null) {
         return false;
      }

      try {
         return a.PadlockIsRunning();
      } catch (Throwable t) {
         lastError = "is_running_failed:" + t.getClass().getSimpleName() + ":" + safeMsg(t.getMessage());
         return false;
      }
   }

   static int getTargets() {
      PadlockNative.Api a = api;
      if (a == null) {
         return 0;
      }

      try {
         return a.PadlockGetTargets();
      } catch (Throwable t) {
         lastError = "get_targets_failed:" + t.getClass().getSimpleName() + ":" + safeMsg(t.getMessage());
         return 0;
      }
   }

   static boolean setDisplayPower(int state) {
      PadlockNative.Api a = api;
      if (a == null) {
         return false;
      }

      try {
         return a.PadlockSetDisplayPower(state);
      } catch (Throwable t) {
         lastError = "display_power_failed:" + t.getClass().getSimpleName() + ":" + safeMsg(t.getMessage());
         return false;
      }
   }

   private static String resolveDllPath() {
      String fromProp = firstNonEmpty(System.getProperty("padlock.dll"), System.getProperty("padlock.dll.path"), System.getProperty("screen.padlock.dll"));
      if (exists(fromProp)) {
         return normalize(fromProp);
      }

      String fromEnv = firstNonEmpty(System.getenv("PADLOCK_DLL_PATH"), System.getenv("PADLOCK_DLL"));
      if (exists(fromEnv)) {
         return normalize(fromEnv);
      }

      String[] candidates = new String[]{
         "resources\\dll\\padlock.dll",
         "resources\\dll\\padlock_dll.dll",
         "dll\\padlock.dll",
         "dll\\padlock_dll.dll",
         "data\\padlock_dll.dll",
         "data\\padlock.dll",
         "build\\resources\\main\\dll\\padlock.dll",
         "src\\main\\resources\\dll\\padlock.dll",
         "padlock_dll.dll"
      };

      for (String candidate : candidates) {
         if (exists(candidate)) {
            return normalize(candidate);
         }
      }

      String extracted = extractBundledDll();
      return !extracted.isEmpty() ? extracted : "";
   }

   private static String extractBundledDll() {
      try {
         InputStream in = PadlockNative.class.getResourceAsStream("/dll/padlock.dll");

         String var7;
         label55: {
            String var2;
            try {
               if (in == null) {
                  var7 = "";
                  break label55;
               }

               Path temp = Files.createTempFile("padlock_dll_", ".dll");
               Files.copy(in, temp, StandardCopyOption.REPLACE_EXISTING);

               try {
                  temp.toFile().deleteOnExit();
               } catch (Exception var4) {
               }

               var2 = temp.toAbsolutePath().normalize().toString();
            } catch (Throwable var5) {
               if (in != null) {
                  try {
                     in.close();
                  } catch (Throwable var3) {
                     var5.addSuppressed(var3);
                  }
               }

               throw var5;
            }

            if (in != null) {
               in.close();
            }

            return var2;
         }

         if (in != null) {
            in.close();
         }

         return var7;
      } catch (Exception e) {
         lastError = "dll_extract_failed:" + e.getClass().getSimpleName() + ":" + safeMsg(e.getMessage());
         return "";
      }
   }

   private static boolean exists(String path) {
      if (path != null && !path.trim().isEmpty()) {
         try {
            return Files.exists(Paths.get(path).toAbsolutePath().normalize());
         } catch (Exception ignored) {
            return false;
         }
      } else {
         return false;
      }
   }

   private static String normalize(String path) {
      try {
         return Paths.get(path).toAbsolutePath().normalize().toString();
      } catch (Exception ignored) {
         return path == null ? "" : path;
      }
   }

   private static String firstNonEmpty(String... values) {
      if (values == null) {
         return "";
      }

      for (String v : values) {
         if (v != null && !v.trim().isEmpty()) {
            return v.trim();
         }
      }

      return "";
   }

   private static boolean isWindows() {
      String os = System.getProperty("os.name");
      return os != null && os.toLowerCase().contains("win");
   }

   private static String safeMsg(String msg) {
      if (msg == null) {
         return "";
      }

      String trimmed = msg.trim();
      return trimmed.length() > 200 ? trimmed.substring(0, 200) : trimmed;
   }

   interface Api extends StdCallLibrary {
      boolean PadlockStart(int targets);

      boolean PadlockSetTargets(int targets);

      void PadlockStop();

      int PadlockGetTargets();

      boolean PadlockIsRunning();

      boolean PadlockSetDisplayPower(int state);
   }
}
