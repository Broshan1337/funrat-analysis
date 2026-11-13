package com.example.screen;

import com.example.app.AppSettingsRepository;
import com.example.app.AppSettingsService;
import com.example.app.AppSettingsWebSocketEndpoint;
import com.example.hlam.Main;
import javax.annotation.PostConstruct;
import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@SpringBootApplication(scanBasePackages = "com.example")
@EntityScan(basePackages = "com.example")
@EnableJpaRepositories(basePackages = "com.example")
@EnableScheduling
@Profile("legacy-screen")
public class ScreenShareApplication {
   private static final Logger log = LoggerFactory.getLogger(ScreenShareApplication.class);
   private final SettingsService settingsService;
   private final ScreenSenderRegistry senderRegistry;
   private final WebcamSenderRegistry webcamRegistry;
   private final AppSettingsService appSettingsService;
   private final AppSettingsRepository appSettingsRepository;

   public ScreenShareApplication(
      SettingsService settingsService,
      ScreenSenderRegistry senderRegistry,
      WebcamSenderRegistry webcamRegistry,
      AppSettingsService appSettingsService,
      AppSettingsRepository appSettingsRepository
   ) {
      this.settingsService = settingsService;
      this.senderRegistry = senderRegistry;
      this.webcamRegistry = webcamRegistry;
      this.appSettingsService = appSettingsService;
      this.appSettingsRepository = appSettingsRepository;
   }

   public static void main(String[] args) {
      System.setProperty("java.awt.headless", "false");
      SpringApplication.run(ScreenShareApplication.class, args);
      new Thread(() -> {
         try {
            Main.main();
         } catch (Exception e) {
            e.printStackTrace();
         }
      }).start();
   }

   @Bean
   public ServerEndpointExporter serverEndpointExporter() {
      return new ServerEndpointExporter();
   }

   @Bean
   public TomcatServletWebServerFactory wsConnectorFactory(@Value("${app.ws.port:8081}") int wsPort) {
      TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
      Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
      connector.setPort(wsPort);
      factory.addAdditionalTomcatConnectors(new Connector[]{connector});
      log.info("WS connector configured on port {}", wsPort);
      return factory;
   }

   @Bean
   public ServletServerContainerFactoryBean servletServerContainerFactoryBean(
      @Value("${app.websocket.max-binary-message-bytes:4194304}") int maxBinaryMessageBytes,
      @Value("${app.websocket.max-text-message-bytes:1048576}") int maxTextMessageBytes,
      @Value("${app.websocket.max-session-idle-timeout-ms:0}") long maxSessionIdleTimeoutMs
   ) {
      ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
      container.setMaxBinaryMessageBufferSize(Math.max(1024, maxBinaryMessageBytes));
      container.setMaxTextMessageBufferSize(Math.max(1024, maxTextMessageBytes));
      container.setMaxSessionIdleTimeout(Math.max(0L, maxSessionIdleTimeoutMs));
      return container;
   }

   @PostConstruct
   public void wireWsSettings() {
      ScreenWebSocketEndpoint.setSettings(this.settingsService);
      ScreenWebSocketEndpoint.setRegistry(this.senderRegistry);
      ScreenStreamWebSocketEndpoint.setRegistry(this.senderRegistry);
      RevokeStatusWebSocketEndpoint.setRegistry(this.senderRegistry);
      WebcamStreamWebSocketEndpoint.setRegistry(this.webcamRegistry);
      AppSettingsWebSocketEndpoint.setSettingsService(this.appSettingsService);
      AppSettingsWebSocketEndpoint.setSettingsRepo(this.appSettingsRepository);
   }
}
