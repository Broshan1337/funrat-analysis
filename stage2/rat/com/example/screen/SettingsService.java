package com.example.screen;

import org.springframework.stereotype.Component;

@Component
public class SettingsService {
   private volatile int fps = 20;
   private volatile float jpegQuality = 0.8F;
   private volatile int monitorIndex = 0;
   private volatile boolean showCursor = true;
   private volatile boolean controlEnabled = false;
   private volatile boolean localCaptureEnabled = false;
   private volatile float scale = 1.0F;

   public int getFps() {
      return this.fps;
   }

   public void setFps(int fps) {
      if (fps < 1) {
         fps = 1;
      }

      if (fps > 60) {
         fps = 60;
      }

      this.fps = fps;
   }

   public float getJpegQuality() {
      return this.jpegQuality;
   }

   public void setJpegQuality(float jpegQuality) {
      if (jpegQuality < 0.1F) {
         jpegQuality = 0.1F;
      }

      if (jpegQuality > 1.0F) {
         jpegQuality = 1.0F;
      }

      this.jpegQuality = jpegQuality;
   }

   public int getMonitorIndex() {
      return this.monitorIndex;
   }

   public void setMonitorIndex(int monitorIndex) {
      if (monitorIndex < 0) {
         monitorIndex = 0;
      }

      this.monitorIndex = monitorIndex;
   }

   public boolean isShowCursor() {
      return this.showCursor;
   }

   public void setShowCursor(boolean showCursor) {
      this.showCursor = showCursor;
   }

   public boolean isControlEnabled() {
      return this.controlEnabled;
   }

   public void setControlEnabled(boolean controlEnabled) {
      this.controlEnabled = controlEnabled;
   }

   public boolean isLocalCaptureEnabled() {
      return this.localCaptureEnabled;
   }

   public void setLocalCaptureEnabled(boolean localCaptureEnabled) {
      this.localCaptureEnabled = localCaptureEnabled;
   }

   public float getScale() {
      return this.scale;
   }

   public void setScale(float scale) {
      if (scale < 0.25F) {
         scale = 0.25F;
      }

      if (scale > 1.0F) {
         scale = 1.0F;
      }

      this.scale = scale;
   }
}
