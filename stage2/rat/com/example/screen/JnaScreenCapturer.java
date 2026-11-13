package com.example.screen;

import com.sun.jna.Memory;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HBITMAP;
import com.sun.jna.platform.win32.WinDef.HDC;
import com.sun.jna.platform.win32.WinGDI.BITMAPINFO;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class JnaScreenCapturer implements ScreenShareSender.ScreenCapturer {
   private final boolean supported = this.isWindows();

   public boolean isSupported() {
      return this.supported;
   }

   @Override
   public BufferedImage capture(Rectangle rect, boolean drawCursor) throws Exception {
      if (!this.supported) {
         throw new IllegalStateException("JNA capture is only supported on Windows");
      }

      int width = Math.max(1, rect.width);
      int height = Math.max(1, rect.height);
      User32 user32 = User32.INSTANCE;
      GDI32 gdi32 = GDI32.INSTANCE;
      HDC screenDC = null;
      HDC memDC = null;
      HBITMAP hBitmap = null;
      HANDLE old = null;

      try {
         screenDC = user32.GetDC(null);
         if (screenDC == null) {
            throw new IllegalStateException("GetDC returned null");
         }

         memDC = gdi32.CreateCompatibleDC(screenDC);
         if (memDC == null) {
            throw new IllegalStateException("CreateCompatibleDC returned null");
         }

         hBitmap = gdi32.CreateCompatibleBitmap(screenDC, width, height);
         if (hBitmap == null) {
            throw new IllegalStateException("CreateCompatibleBitmap returned null");
         }

         old = gdi32.SelectObject(memDC, hBitmap);
         if (old == null) {
            throw new IllegalStateException("SelectObject returned null");
         }

         gdi32.BitBlt(memDC, 0, 0, width, height, screenDC, rect.x, rect.y, 13369376);
         BufferedImage image = new BufferedImage(width, height, 2);
         BITMAPINFO bmi = new BITMAPINFO();
         bmi.bmiHeader.biSize = bmi.bmiHeader.size();
         bmi.bmiHeader.biWidth = width;
         bmi.bmiHeader.biHeight = -height;
         bmi.bmiHeader.biPlanes = 1;
         bmi.bmiHeader.biBitCount = 32;
         bmi.bmiHeader.biCompression = 0;
         int bufferSize = width * height * 4;
         Memory buffer = new Memory(bufferSize);
         int lines = gdi32.GetDIBits(memDC, hBitmap, 0, height, buffer, bmi, 0);
         if (lines == 0) {
            throw new IllegalStateException("GetDIBits returned 0");
         }

         int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
         buffer.read(0L, pixels, 0, pixels.length);

         for (int i = 0; i < pixels.length; i++) {
            pixels[i] |= -16777216;
         }

         return image;
      } finally {
         if (old != null && memDC != null) {
            gdi32.SelectObject(memDC, old);
         }

         if (hBitmap != null) {
            gdi32.DeleteObject(hBitmap);
         }

         if (memDC != null) {
            gdi32.DeleteDC(memDC);
         }

         if (screenDC != null) {
            user32.ReleaseDC(null, screenDC);
         }
      }
   }

   @Override
   public boolean drawsCursor() {
      return false;
   }

   @Override
   public String name() {
      return "jna-bitblt";
   }

   private boolean isWindows() {
      String os = System.getProperty("os.name");
      return os != null && os.toLowerCase().contains("win");
   }
}
