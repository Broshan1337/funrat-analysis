# FunRat in a Minecraft mod: reversing DonutDuper+

I was bored one evening and went down the Donut Dupe rabbit hole on YouTube. Those videos
always end with "install this client mod and get free dupes", so I figured I'd grab one of
the jars people were sharing and actually look at it. This is the writeup of the one I
picked up: `DonutDuper+.jar`, a Fabric mod that turns out to be a two stage dropper for
something that calls itself **FunRat**.

Everything here is static analysis. I never ran the mod, never ran a payload, and never
let any of these jars touch a JVM. Java on this machine was only ever used to run
[vineflower](https://github.com/vineflower/vineflower) so it could read the class files.

**TL;DR**

* A Fabric mod that steals your Minecraft account (username, UUID, access token) and posts it to a C2.
* If the token looks real, it downloads stage 2 and runs it from `%APPDATA%\Microsoft\SecurityUpdates`.
* The C2 address is not in the binary. It lives in a **TON blockchain smart contract**, so the
  payload host can change without the mod changing.
* Stage 2 is a launcher that pulls the real implant (`back.jar`, browser/wallet/game credential
  stealer, scheduled-task persistence, UAC + SYSTEM escalation) and a Discord spammer (`dis.jar`).
* A separate slot serves a full RAT: screen, webcam and mic streaming, shell, file access, over
  WebSockets to `funrat.mom` or a hardcoded `.onion`.

---

## How I did it without running anything

The mod is a normal jar, so `unzip` and vineflower get you the code. The interesting part is
that the code is obfuscated in a way that hides *what it calls*, not just what it says, so you
have to undo that before you can even read the control flow. Once it was readable, the network
logic was plain HTTP, which meant I could replay the resolution steps in Python and download
the payloads as files instead of letting the malware fetch them.

The only thing that touches attacker infrastructure is the final file download, and that is a
read-only GET. The C2 address itself comes from public third party services (toncenter, tonapi),
so you can find the current payload URLs without the operator ever seeing you.

`tools/c2_recon.py` is a passive port of that chain. Run it and it prints the live C2 base,
the `/config` slots and the payload URLs.

---

## Stage 1: the mod itself

`fabric.mod.json` says id `hello-world-fabric`, name "Fun mod", author "FunMaster". Seven
classes in a package called `aurora`. Nothing about it screams malware until you look inside.

Four layers of obfuscation, all of them using ordinary lowercase letters, which is what makes
it annoying to grep for:

1. **Encoded strings.** Every string is `fable.stream("mmgapcga-ijhepagi-...", key)`. Letters
   `a`-`p` are hex nibbles separated by dashes, each byte XORed against a cycling 4 byte key
   written as something like `6039 << 16 | (char)-19995`.
2. **Hidden method calls.** Almost every call goes through an `invokedynamic` bootstrap
   (`nutmeg.fresco`) whose arguments carry the target class, method and descriptor, encoded with
   the same string scheme, plus a random human word as camouflage ("decoy", "patio", "walnut").
   Vineflower renders these as pseudo-syntax, so I wrote a small parser for it.
3. **Split strings.** Sensitive literals are built from nested `String.concat()` chains of five
   or six encoded fragments, so no single fragment looks interesting.
4. **Dead code.** `if (medal.prawn() != 0) medal.coyote();` where `flower = flower`. Noise.

After running `tools/deobf.py` over the decompiled output: **552 hidden call sites and 172
strings resolved, 1 left** (the bootstrap's own runtime decode). The result is in
`stage1/deobfuscated/`, and `stage1/DonutDuper_deobfuscated_readable.java` is a hand renamed
version that reads like normal code.

What it actually does on `onInitializeClient()`:

* grabs the Minecraft session through reflection (`class_310.getUser()`, then name, profile id
  and access token via intermediary mappings),
* POSTs `{"name","uuid","token","receiver","key"}` to `<C2>/report` on a thread called
  `thin-report`, retrying 24 times with backoff up to 5 minutes,
* and if `token.length() == 15` is false, i.e. the account looks like a real one, it downloads
  the next stage to `%APPDATA%\Microsoft\JavaSec\stage.jar`, checks the zip contains
  `core/core.class`, and loads it in a `URLClassLoader`.

The C2 lookup is the clever bit. It calls `link` on TON contract
`EQChU6Zxa_y2fKeeCaSDubGcFZizRdRVSArwAh6qJtfe9NKZ` through toncenter, and falls back to reading a
TON message cell through tonapi. Both are public APIs. The contract currently returns a
Cloudflare Workers URL.

---

## Getting stage 2 without running stage 1

This is the part I thought was worth writing up, because it works and it needs no sandbox.

| step | what the malware does | how I replayed it |
|---|---|---|
| 1 | `POST toncenter.com/api/v2/runGetMethod` with the contract address, method `link`, arg `8` | `c2_recon.py apple(8)` |
| 2 | scrape `"b64":"..."`, base64 decode | same |
| 3 | that string is the live C2 base | `GET <base>/config`, read the slots |
| 4 | fallback: `tonapi.io/.../methods/getMessage`, scrape `"cell":"<hex>"`, parse the TON cell | `maroon()` + `stork()` |
| 5 | GET the payload, keep it only if the zip has `core/core.class` | curl + `unzip -l` |

Steps 1 to 4 only talk to TON's public APIs. The `/config` I got back (version 47) maps slots to
files on the Workers relay, and slot 3/9 point at `funrat.mom`, which is where the panel lives.
The TON message cells decode to `funrat.top`, which no longer resolves, so the relay is the live
path today.

Hashes of everything I pulled are in [SAMPLES.md](SAMPLES.md). The binaries themselves are not in
this repo, on purpose.

---

## Stage 2a: the launcher (`core.core`)

Not obfuscated at all, which is a bit funny after stage 1. Real names, `resolveBase`,
`configSlot`, `isPremium`. Decompiled in `stage2/loader/core.java`.

It re-resolves the C2 the same way, sets up `%APPDATA%\Microsoft\SecurityUpdates` as its home
(a second fake Microsoft folder, stage 1 used `Microsoft\JavaSec`), then:

* `GET /api/premium-status?id=<key>` and looks for `"premium":true`,
* downloads slot 1 as `Java.jar` and runs it as its own process: `java -jar Java.jar <token> <uuid> <username> <key>`,
* downloads slot 2 as `dis.jar` and runs it **only if the victim is not premium**.

There is a hardcoded campaign key `30e3267e-d80c-4a8f-84e2-bb475930a37c` that skips the spam
component. So the operator sorts victims: the ones worth the full implant get the stealer, the
rest also get the spammer that advertises the product for him.

---

## back.jar: the real implant

Same `aurora` package and the exact same obfuscation scheme as stage 1, just renamed: the string
decoder is now `ixuofesnss.klxfqq` and the bootstrap is `hwefggxaqr.eqbybn`, and every stage 1
method name became a class name (`apple`, `gadget`, `stork`, `maroon`, `kayak`...). Main class is
`aurora.prawn`, one 29,000 line class.

Because the scheme is identical, the same deobfuscator works with two flags:

```bash
java -jar vineflower.jar back_aurora.jar back_src
python3 tools/deobf.py --indy=hwefggxaqr.eqbybn --str=ixuofesnss.klxfqq stage2/implant/aurora_prawn.deobf.java
```

That resolved **7,679 hidden calls and 2,561 strings** down to 13,471 readable lines
(`stage2/implant/`). What it does:

* **Browsers**: Chrome, Chrome Beta/Dev/SxS, Edge, Brave. Reads `Login Data`, `Local State` and
  `Cookies` straight out of SQLite (sqlite-jdbc is bundled) and unwraps the keys with DPAPI
  through JNA `Crypt32`. Log tags `[decryptKey]`, `[decryptCookies]`, `[loginData]`.
* **Crypto wallets**: Electrum, Exodus, Coinomi, Metamask, Phantom, TrustWallet, Pali, plus
  generic `DesktopCryptoWallets` / `WebCryptoWallets` sweeps.
* **Game tokens**: Steam, Epic, EA, Xbox, Apple, Microsoft account.
* **Discord injection**, and it kills browser processes first so the databases unlock.
* **Persistence**: writes `%WINDIR%\Temp\nyx_<random>.cmd` that re-runs the jar with java.exe,
  then registers it as a one-shot scheduled task called **`MicrosoftEdgeUpdateTaskMachineCore`**.
* **Escalation**: drops `/nyx.bin` to `%TEMP%\upd_<random>.exe` (see below).
* **Live browser sessions**: it relaunches the browser with `--remote-debugging-port=9222
  --user-data-dir=... --profile-directory=... --restore-last-session` and then reads it through
  the DevTools endpoint at `http://localhost:9222/json`. So it is not only copying the cookie
  database, it is driving a running browser.
* **Account token chains**: it walks the real OAuth flows, `login.live.com/oauth20_token.srf`
  for Microsoft accounts and `user.auth.xboxlive.com` then `xsts.auth.xboxlive.com` for Xbox,
  and it also hits the Discord API including `/users/@me/billing/payment-sources`.

Where the loot goes: the collected files are zipped in memory (`ZipOutputStream`) and pushed as
`POST <base>/api/ingest` with `Content-Type: application/zip` and a multipart body, with an
`Authorization` header. There is also a second Cloudflare worker,
`telemetry-relay.kartohaantoha36-6a9.workers.dev`, same account prefix as the payload relay, used
for the crash and status beacons. And a third domain in the family: `funrat.co`.

`nyx.bin` is a PE XORed with `0x5a`. Decoded, it is a privilege escalation tool that announces
its own plan in strings: `step 1: masquerade PEB / loader`, `step 2: elevation (task first,
ladder only if the task is not armed)`, `step 3: getsystem (token theft)`, and then does the
classic `SeDebugPrivilege` -> `OpenProcessToken` -> `DuplicateTokenEx` -> `CreateProcessWithTokenW`
dance to relaunch itself as SYSTEM. Details in [native/nyx.md](native/nyx.md).

---

## dis.jar: the spam component

This one is not Minecraft spam at all, despite the ad copy. `org.example.Main` (in
`stage2/discord_spam/`) is a Discord token stealer with a megaphone attached:

* pulls the token out of `%APPDATA%\discord\Local Storage\leveldb` with the familiar
  `dQw4w9WgXcQ:` regex, DPAPI then AES/GCM, plus about twenty browser leveldb paths,
* checks the token against `discord.com/api/v9/users/@me` and also grabs
  `/billing/payment-sources`,
* if the account is **not** in guild `1443109598579003414`, which is the author's own guild, it
  posts `@everyone @here YOUR MINECRAFT ACCOUNT IS ALREADY STOLEN ... funrat.mom` into every
  text channel of every guild the token can write to, and DMs every friend.

The guild check is also a nice anti-analysis tripwire: if you test the sample with your own
account and you are not in his guild, your account gets used to advertise his RAT. There is a
string in there that reads `Stop testing rat yourself`.

---

## The slot 7 payload: the actual RAT

A different jar, `com.example.rat.RatSender`, and this one is not obfuscated at all. Screen
share, webcam, microphone, clipboard, an interactive shell and file browsing over WebSockets
(`/screen-stream`, `/webcam-stream`, `/mic/*`, `/run`, `/monitors`), plus a UAC `Elevator` and
`padlock.dll`.

The nastiest export in the bundle is `padlock.dll`, which is a lock screen. Its 14 exported
functions are `PadlockStartKeyboard`, `PadlockStartMouse`, `PadlockStartScreen`,
`PadlockEnableKeyboard`, `PadlockGetOverlayWindow`, `PadlockSetDisplayPower` and so on, and the
Java side binds them with JNA. The operator can freeze your keyboard and mouse and paint an
overlay over your display, on demand, while the webcam is running. Details in
[native/README.md](native/README.md).

It dials out to `wss://funrat.mom/screen-stream` and `wss://funrat.mom/webcam-stream`, or to a
hardcoded hidden service `ws://5kiwqdzvhip5yjx5u6ms2hsv3yozpslxocjhyf4cyimrg33fcb7devid.onion/webcam-stream`.
`core/TorBridge` is what makes the onion reachable: it is a Tor *client* helper, not a hidden
service on the victim. It reuses a local Tor on `127.0.0.1:9050` if one is running, otherwise
looks for `tor.exe` in `PATH` or a Tor Browser install, writes a `torrc` into a temp `torbridge`
directory, spawns `tor.exe -f torrc` and hands back a SOCKS proxy. The bundled `/tor/win/tor.exe`
resource is missing from both jars, so in practice it only works if the victim already has Tor.

---

## IOCs

See [IOCS.md](IOCS.md) for the flat list and [SAMPLES.md](SAMPLES.md) for hashes.

I also poked the endpoints with obviously fake data to confirm they are live. The credential
endpoint answers `{"ok":true,"relayed":true}` and does not validate anything, and the worker
returns 200 for paths that do not exist. Details in [PROBES.md](PROBES.md).

The one worth calling out for anyone doing takedown: the C2 list is stored **on chain**. Killing
the Cloudflare Workers relay does not kill the campaign, because the mod will fall back to the
TON contract and to `funrat.mom`. The contract has to be flagged too.

---

## Repo layout

```
tools/         decode.py (string layer), deobf.py (source rewriter), c2_recon.py (passive C2 resolver)
stage1/        deobfuscated mod classes, hand renamed version, resolved call table
stage2/
  loader/      core.core, the stage 2a launcher
  implant/     aurora.prawn, the stealer, deobfuscated
  discord_spam/ Discord token stealer and spammer, TorBridge
  rat/         the slot 7 RAT, screen/webcam/mic/shell
native/        nyx.bin analysis (the binary itself is not here)
ABUSE_REPORTS.md   the abuse reports I sent, ready to copy
OSINT.md       the operation behind it: product, timeline, wallets, leads
IOCS.md        indicators
PROBES.md      what the live endpoints actually return
SAMPLES.md     hashes and metadata, no binaries
```

No malware binaries in this repo. If you want the samples, get them from a sandbox or a malware
sharing platform, not from GitHub.

## The operation behind the malware

Short version, full detail in [OSINT.md](OSINT.md).

This is not a lone script kiddie, it is a product. The landing page at `funrat.mom` is public and
markets "FunRAT, The Ultimate Minecraft RAT", listing session tokens, screenshots, Discord and
Telegram credentials, webcam and screen streaming, remote mouse and keyboard, Discord mass DM, and
the **sale of Hypixel and DonutSMP items** from stolen accounts. Freemium: the free tier spams your
friends as advertising, premium turns the spam off. That is the `isPremium()` gate in the code.

The operator runs a Telegram changelog channel, `@weloverat`, display name **FunServer**, open
since 2026-02-25, where they announce features in the first person, complain about abuse reports
("Some idiots sent report to all possible services"), and announce each new domain. Four domains
since February, all at NameSilo, all on deSEC nameservers, two of them already suspended by the
registrar.

The strongest lead is inside the binaries: both native DLLs were built from
`C:\Users\maksv\source\repos\`, so the injector and the lock screen came off one machine. The
oldest on-chain address in the cluster has been active since December 2025, ten months before the
current campaign. I did not find a name and I am not going to invent one from a username; that
part belongs to whoever takes the police report.

## Notes and limits

* Static only, so anything that only shows up at runtime (decryption keys negotiated on the wire,
  actual exfil volume) I did not observe.
* Status as of 2026-11-13: the Workers relay and `funrat.mom` are live and share one backend,
  `funrat.top` and `funrat.co` do not resolve, and the `telemetry-relay` worker returns Cloudflare
  403/530. The panel login is at `funrat.mom/auth/login`.
* `CookiveV2.dll` and `padlock.dll` were only read through headers, imports, exports and strings,
  not disassembled. The injection story for `CookiveV2.dll` is inferred from the loader strings
  and the fact that the DLL has no export table.
* I filed abuse reports with Cloudflare, the hosting provider, the registrar and the DNS provider.
  The texts are in [ABUSE_REPORTS.md](ABUSE_REPORTS.md). The TON contract cannot be taken down, only
  flagged, which is why the campaign survives domain seizures.
