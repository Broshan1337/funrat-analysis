package com.example.screen;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ScreenSettingsController {
   private final SettingsService settings;
   private final Robot robot;
   private final boolean headless;

   public ScreenSettingsController(SettingsService settings) throws AWTException {
      this.settings = settings;
      this.headless = GraphicsEnvironment.isHeadless();
      this.robot = this.headless ? null : new Robot();
   }

   @GetMapping("/settings")
   public Map<String, Object> getSettings() {
      Map<String, Object> m = new HashMap<>();
      m.put("fps", this.settings.getFps());
      m.put("jpegQuality", this.settings.getJpegQuality());
      m.put("monitorIndex", this.settings.getMonitorIndex());
      m.put("showCursor", this.settings.isShowCursor());
      m.put("controlEnabled", this.settings.isControlEnabled());
      m.put("localCaptureEnabled", this.settings.isLocalCaptureEnabled());
      m.put("scale", this.settings.getScale());
      return m;
   }

   @PostMapping("/settings")
   public ResponseEntity<?> updateSettings(@RequestBody Map<String, Object> body) {
      if (body.containsKey("fps")) {
         Object v = body.get("fps");
         if (v instanceof Number) {
            this.settings.setFps(((Number)v).intValue());
         }
      }

      if (body.containsKey("jpegQuality")) {
         Object v = body.get("jpegQuality");
         if (v instanceof Number) {
            this.settings.setJpegQuality(((Number)v).floatValue());
         }
      }

      if (body.containsKey("monitorIndex")) {
         Object v = body.get("monitorIndex");
         if (v instanceof Number) {
            this.settings.setMonitorIndex(((Number)v).intValue());
         }
      }

      if (body.containsKey("showCursor")) {
         Object v = body.get("showCursor");
         if (v instanceof Boolean) {
            this.settings.setShowCursor((Boolean)v);
         }
      }

      if (body.containsKey("controlEnabled")) {
         Object v = body.get("controlEnabled");
         if (v instanceof Boolean) {
            this.settings.setControlEnabled((Boolean)v);
         }
      }

      if (body.containsKey("localCaptureEnabled")) {
         Object v = body.get("localCaptureEnabled");
         if (v instanceof Boolean) {
            this.settings.setLocalCaptureEnabled((Boolean)v);
         }
      }

      if (body.containsKey("scale")) {
         Object v = body.get("scale");
         if (v instanceof Number) {
            this.settings.setScale(((Number)v).floatValue());
         }
      }

      return ResponseEntity.ok().build();
   }

   @GetMapping("/monitors")
   public List<Map<String, Object>> monitors() {
      List<Map<String, Object>> list = new ArrayList<>();
      if (this.headless) {
         return list;
      }

      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice[] devices = ge.getScreenDevices();

      for (int i = 0; i < devices.length; i++) {
         Rectangle b = devices[i].getDefaultConfiguration().getBounds();
         Map<String, Object> m = new HashMap<>();
         m.put("index", i);
         Map<String, Object> bounds = new HashMap<>();
         bounds.put("x", b.x);
         bounds.put("y", b.y);
         bounds.put("width", b.width);
         bounds.put("height", b.height);
         m.put("bounds", bounds);
         list.add(m);
      }

      return list;
   }

   @PostMapping("/control/mouseMove")
   public ResponseEntity<?> mouseMove(@RequestBody Map<String, Object> body) {
      if (!this.settings.isControlEnabled()) {
         return ResponseEntity.status(403).build();
      }

      if (this.robot == null) {
         return ResponseEntity.status(503).build();
      }

      int x = ((Number)body.getOrDefault("x", 0)).intValue();
      int y = ((Number)body.getOrDefault("y", 0)).intValue();
      this.robot.mouseMove(x, y);
      return ResponseEntity.ok().build();
   }

   @PostMapping("/control/mousePress")
   public ResponseEntity<?> mousePress(@RequestBody Map<String, Object> body) {
      if (!this.settings.isControlEnabled()) {
         return ResponseEntity.status(403).build();
      }

      if (this.robot == null) {
         return ResponseEntity.status(503).build();
      }

      int button = ((Number)body.getOrDefault("button", 1024)).intValue();
      this.robot.mousePress(button);
      return ResponseEntity.ok().build();
   }

   @PostMapping("/control/mouseRelease")
   public ResponseEntity<?> mouseRelease(@RequestBody Map<String, Object> body) {
      if (!this.settings.isControlEnabled()) {
         return ResponseEntity.status(403).build();
      }

      if (this.robot == null) {
         return ResponseEntity.status(503).build();
      }

      int button = ((Number)body.getOrDefault("button", 1024)).intValue();
      this.robot.mouseRelease(button);
      return ResponseEntity.ok().build();
   }

   @PostMapping("/control/keyPress")
   public ResponseEntity<?> keyPress(@RequestBody Map<String, Object> body) {
      if (!this.settings.isControlEnabled()) {
         return ResponseEntity.status(403).build();
      }

      if (this.robot == null) {
         return ResponseEntity.status(503).build();
      }

      int keyCode = ((Number)body.getOrDefault("keyCode", 0)).intValue();
      this.robot.keyPress(keyCode);
      return ResponseEntity.ok().build();
   }

   @PostMapping("/control/keyRelease")
   public ResponseEntity<?> keyRelease(@RequestBody Map<String, Object> body) {
      if (!this.settings.isControlEnabled()) {
         return ResponseEntity.status(403).build();
      }

      if (this.robot == null) {
         return ResponseEntity.status(503).build();
      }

      int keyCode = ((Number)body.getOrDefault("keyCode", 0)).intValue();
      this.robot.keyRelease(keyCode);
      return ResponseEntity.ok().build();
   }
}
