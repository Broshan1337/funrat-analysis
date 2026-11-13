package core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.Proxy.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class TorBridge {
   private static TorBridge instance;
   private Process torProcess;
   private int socksPort = 9050;
   private Path dataDir;
   private File torBinary;
   private boolean started;

   public static synchronized TorBridge get() {
      if (instance == null) {
         instance = new TorBridge();
      }

      return instance;
   }

   public void start() throws Exception {
      if (!this.started) {
         if (isPortOpen("127.0.0.1", 9050, 500)) {
            this.socksPort = 9050;
            this.started = true;
            System.out.println("[TorBridge] Using existing Tor on 127.0.0.1:9050");
         } else {
            this.startInternal(0);
         }
      }
   }

   private static boolean isPortOpen(String var0, int var1, int var2) {
      try (Socket var3 = new Socket()) {
         var3.connect(new InetSocketAddress(var0, var1), var2);
         return true;
      } catch (Exception var17) {
         return false;
      }
   }

   private void startInternal(int var1) throws Exception {
      if (this.torProcess != null) {
         this.stop();
      }

      this.torBinary = this.findTorBinary();
      if (this.torBinary == null) {
         throw new IOException("Tor not found in JAR resources or system PATH.");
      }

      this.socksPort = this.findFreePort(9050);
      if (this.socksPort < 0) {
         throw new IOException("No free port in range 9050-9060");
      }

      this.startTorProcess();
      if (!this.waitForPort(60000L)) {
         this.stop();
         if (var1 < 1) {
            System.out.println("[TorBridge] Retrying...");
            this.startInternal(var1 + 1);
         } else {
            throw new Exception("Tor did not start within 60s");
         }
      } else {
         this.started = true;
         System.out.println("[TorBridge] Ready on 127.0.0.1:" + this.socksPort);
      }
   }

   public void stop() {
      this.started = false;
      if (this.torProcess != null) {
         this.torProcess.destroyForcibly();
         this.torProcess = null;
      }

      if (this.dataDir != null) {
         this.deleteDir(this.dataDir.toFile());
         this.dataDir = null;
      }
   }

   public Proxy socks() {
      return new Proxy(Type.SOCKS, new InetSocketAddress("127.0.0.1", this.socksPort));
   }

   private File findTorBinary() {
      File var1 = this.extractFromJar();
      if (var1 != null) {
         return var1;
      }

      var1 = this.findSystemTor();
      return var1 != null ? var1 : null;
   }

   private File extractFromJar() {
      try (InputStream var1 = this.getClass().getResourceAsStream("/tor/win/tor.exe")) {
         if (var1 == null) {
            return null;
         }

         Path var3 = Files.createTempFile("tor", ".exe");
         Files.copy(var1, var3, StandardCopyOption.REPLACE_EXISTING);
         File var4 = var3.toFile();
         var4.deleteOnExit();
         var4.setExecutable(true);
         System.out.println("[TorBridge] Extracted tor.exe from JAR");
         return var4;
      } catch (Exception var18) {
         return null;
      }
   }

   private File findSystemTor() {
      for (String var4 : System.getenv("PATH").split(File.pathSeparator)) {
         File var5 = new File(var4, "tor.exe");
         if (var5.exists()) {
            return var5;
         }
      }

      for (String var9 : new String[]{System.getenv("LOCALAPPDATA"), System.getenv("PROGRAMFILES"), System.getenv("PROGRAMFILES(X86)")}) {
         if (var9 != null) {
            File var10 = new File(var9, "Tor Browser/Browser/TorBrowser/Tor/tor.exe");
            if (var10.exists()) {
               return var10;
            }
         }
      }

      return null;
   }

   private int findFreePort(int var1) {
      for (int var2 = var1; var2 < var1 + 10; var2++) {
         try (Socket var3 = new Socket()) {
            var3.connect(new InetSocketAddress("127.0.0.1", var2), 500);
         } catch (IOException var16) {
            return var2;
         }
      }

      return -1;
   }

   private void startTorProcess() throws Exception {
      this.dataDir = Files.createTempDirectory("torbridge");
      Path var1 = this.dataDir.resolve("torrc");
      Files.write(
         var1,
         Arrays.asList("SocksPort " + this.socksPort, "DataDirectory " + this.dataDir, "Log notice stdout", "GeoIPFile /dev/null", "GeoIPv6File /dev/null")
      );
      this.torProcess = new ProcessBuilder(this.torBinary.getAbsolutePath(), "-f", var1.toString()).redirectErrorStream(true).start();
      Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
   }

   private boolean waitForPort(long var1) {
      long var3 = System.currentTimeMillis() + var1;

      while (System.currentTimeMillis() < var3) {
         if (!this.torProcess.isAlive()) {
            System.out.println("[TorBridge] Process died");
            return false;
         }

         try (Socket var5 = new Socket()) {
            var5.connect(new InetSocketAddress("127.0.0.1", this.socksPort), 1000);

            try {
               Thread.sleep(8000L);
            } catch (InterruptedException var18) {
               Thread.currentThread().interrupt();
            }

            return true;
         } catch (Exception var23) {
            try {
               Thread.sleep(1000L);
            } catch (InterruptedException var20) {
               Thread.currentThread().interrupt();
            }
         }
      }

      return false;
   }

   private void deleteDir(File var1) {
      if (var1 != null && var1.exists()) {
         for (File var5 : var1.listFiles()) {
            if (var5.isDirectory()) {
               this.deleteDir(var5);
            }

            var5.delete();
         }

         var1.delete();
      }
   }
}
