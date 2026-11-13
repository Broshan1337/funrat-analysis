package com.example.screen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WebSocketStateCleanupTask {
   private static final Logger log = LoggerFactory.getLogger(WebSocketStateCleanupTask.class);
   private final ScreenSenderRegistry screenSenderRegistry;
   private final WebcamSenderRegistry webcamSenderRegistry;

   public WebSocketStateCleanupTask(ScreenSenderRegistry screenSenderRegistry, WebcamSenderRegistry webcamSenderRegistry) {
      this.screenSenderRegistry = screenSenderRegistry;
      this.webcamSenderRegistry = webcamSenderRegistry;
   }

   @Scheduled(fixedDelayString = "${app.websocket.cleanup-interval-ms:3600000}", initialDelayString = "${app.websocket.cleanup-initial-delay-ms:60000}")
   public void cleanup() {
      int screenWsRemoved = ScreenWebSocketEndpoint.cleanupStaleState();
      int revokeWsRemoved = RevokeStatusWebSocketEndpoint.cleanupStaleSessions();
      int webcamWsRemoved = WebcamWebSocketEndpoint.cleanupStaleSessions();
      int screenSendersRemoved = this.screenSenderRegistry.pruneClosedSessions();
      int webcamSendersRemoved = this.webcamSenderRegistry.pruneClosedSessions();
      int total = screenWsRemoved + revokeWsRemoved + webcamWsRemoved + screenSendersRemoved + webcamSendersRemoved;
      if (total > 0 && log.isDebugEnabled()) {
         log.debug(
            "websocket cleanup removed total={} (screenWs={}, revokeWs={}, webcamWs={}, screenSenders={}, webcamSenders={})",
            new Object[]{total, screenWsRemoved, revokeWsRemoved, webcamWsRemoved, screenSendersRemoved, webcamSendersRemoved}
         );
      }
   }
}
