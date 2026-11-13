package com.example.screen;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
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
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.zip.CRC32;
import javax.annotation.PostConstruct;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScreenCaptureScheduler {
   private static final Logger log = LoggerFactory.getLogger(ScreenCaptureScheduler.class);
   private Robot robot;
   private volatile long lastCrc = -1L;
   private volatile long lastSentNanos = 0L;
   private final SettingsService settings;

   public ScreenCaptureScheduler(SettingsService settings) {
      this.settings = settings;
   }

   @PostConstruct
   public void init() throws AWTException {
      if (GraphicsEnvironment.isHeadless()) {
         log.warn("Screen capture disabled: headless environment.");
      } else {
         this.robot = new Robot();
      }
   }

   private Rectangle computeCaptureRect() {
      try {
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         GraphicsDevice[] devices = ge.getScreenDevices();
         int idx = this.settings.getMonitorIndex();
         if (devices.length == 0) {
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            return new Rectangle(size);
         }

         if (idx >= devices.length) {
            idx = 0;
         }

         return devices[idx].getDefaultConfiguration().getBounds();
      } catch (Exception e) {
         Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
         return new Rectangle(size);
      }
   }

   private void drawCursorIfNeeded(BufferedImage img, Rectangle rect) {
      if (this.settings.isShowCursor()) {
         try {
            PointerInfo pi = MouseInfo.getPointerInfo();
            if (pi == null) {
               return;
            }

            Point p = pi.getLocation();
            int x = p.x - rect.x;
            int y = p.y - rect.y;
            if (x < 0 || y < 0 || x >= rect.width || y >= rect.height) {
               return;
            }

            Graphics2D g = img.createGraphics();

            try {
               g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
               g.setColor(new Color(0, 0, 0, 128));
               g.fillOval(x - 8, y - 8, 16, 16);
               g.setColor(new Color(255, 255, 255, 220));
               g.drawOval(x - 8, y - 8, 16, 16);
               g.drawLine(x - 10, y, x + 10, y);
               g.drawLine(x, y - 10, x, y + 10);
            } finally {
               g.dispose();
            }
         } catch (Exception var12) {
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

   @Scheduled(fixedRate = 10L)
   public void captureAndSend() {
      try {
         if (this.robot == null) {
            return;
         }

         if (!this.settings.isLocalCaptureEnabled()) {
            return;
         }

         if (!ScreenWebSocketEndpoint.hasClients()) {
            return;
         }

         int fps = this.settings.getFps();
         long intervalNanos = 1000000000L / Math.max(1, fps);
         long now = System.nanoTime();
         if (now - this.lastSentNanos < intervalNanos) {
            return;
         }

         Rectangle rect = this.computeCaptureRect();
         BufferedImage image = this.robot.createScreenCapture(rect);
         this.drawCursorIfNeeded(image, rect);
         float scale = this.settings.getScale();
         if (scale < 0.999F) {
            image = this.scaleImage(image, scale);
         }

         byte[] bytes = this.encodeJpeg(image, this.settings.getJpegQuality());
         CRC32 crc32 = new CRC32();
         crc32.update(bytes);
         long crc = crc32.getValue();
         if (crc == this.lastCrc) {
            return;
         }

         this.lastCrc = crc;
         this.lastSentNanos = now;
         ScreenWebSocketEndpoint.broadcast(bytes);
      } catch (Exception e) {
         e.printStackTrace();
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
}
