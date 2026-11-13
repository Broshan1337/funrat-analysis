# Indicators

Collected 2026-11-13, static analysis plus read-only requests to the C2.

## Files and paths

```
DonutDuper+.jar                                   (stage 1, Fabric mod)
%APPDATA%\Microsoft\JavaSec\stage.jar             stage 2 written by the mod
%APPDATA%\Microsoft\SecurityUpdates\              stage 2a working directory
%APPDATA%\Microsoft\SecurityUpdates\Java.jar      back.jar, the implant
%APPDATA%\Microsoft\SecurityUpdates\dis.jar       the Discord spammer
%TEMP%\upd_<random hex>.exe                       nyx.bin after XOR decode
%WINDIR%\Temp\nyx_<random hex>.cmd                wrapper that relaunches the implant
C:\Windows\Temp\nyx.back.system                   integrity marker
C:\Windows\Temp\nyx.back.high                     integrity marker
```

## Persistence

```
schtasks /create /tn MicrosoftEdgeUpdateTaskMachineCore /tr %WINDIR%\Temp\nyx_<hex>.cmd /sc once /st 00:00
```

Also enumerates the Start Menu Startup folders, WMI startup entries and `schtasks` (some of
those sections are read-only recon and report rather than write).

## Network

```
toncenter.com/api/v2/runGetMethod      POST, contract link(8)
tonapi.io/v2/blockchain/accounts/.../methods/getMessage
tonhubapi.com/api/v2/runGetMethod      used by the stage 2 Discord component
EQChU6Zxa_y2fKeeCaSDubGcFZizRdRVSArwAh6qJtfe9NKZ    TON contract holding the C2 list
relay-3f9c71.kartohaantoha36-6a9.workers.dev        live C2 / payload relay
funrat.mom                                          panel, also slot 3 and 9
funrat.top                                          legacy host, currently NXDOMAIN
funrat.co                                           base URL for the zip upload, currently NXDOMAIN
funrat.lol                                          original and Nov 2026 domain, suspended 2026-11-10
funrat.mom/auth/login                               operator panel login, target of the 302s
telemetry-relay.kartohaantoha36-6a9.workers.dev     second worker, crash and status beacons
```

Endpoints seen in the code:

```
GET  /config                                  slot map
POST /report                                  stolen Minecraft account
POST /error                                   crash telemetry, "src" field, max 6
GET  /api/premium-status?id=<key>             premium gate
GET  /api/hlam/files/4001                     back.jar fallback
GET  /api/hlam/files/4003                     dis.jar fallback
POST /api/ingest                                stolen files, zip body, Authorization header
GET  /version  /update  /settings
http://localhost:9222/json                        Chrome DevTools, after relaunching the browser
   flags used: --remote-debugging-port=9222 --user-data-dir= --profile-directory= --restore-last-session --headless
https://login.live.com/oauth20_token.srf          Microsoft account refresh token
https://user.auth.xboxlive.com/user/authenticate  Xbox auth chain
https://xsts.auth.xboxlive.com/xsts/authorize     Xbox token chain
ws://127.0.0.1:9050                               Tor SOCKS port TorBridge looks for
wss://funrat.mom/screen-stream
wss://funrat.mom/webcam-stream
ws://5kiwqdzvhip5yjx5u6ms2hsv3yozpslxocjhyf4cyimrg33fcb7devid.onion/webcam-stream
/screen-stream  /webcam-stream  /mic/*  /run  /monitors  /revoke-status   (websocket paths)
```

## Endpoint behaviour (confirmed 2026-11-13, see PROBES.md)

```
POST /report   -> 200 {"ok":true,"relayed":true,"code":200,"w":"relayed"}   no validation at all
POST /error    -> 200 {"ok":true,"sent":false,"code":0}
GET  /report   -> 405 Spring Boot error, internal path is /api/report
GET  /api/premium-status?id=<any>                  -> 200 {"premium":false}
GET  /api/ingest | /version | /update              -> 302 to /auth/login
any other path                                     -> 200 {"ok":true,"ignored":true}  catch-all
```

The campaign key `30e3267e-d80c-4a8f-84e2-bb475930a37c` returns `premium:false` server side.

## On-chain (TON)

```
EQChU6Zxa_y2fKeeCaSDubGcFZizRdRVSArwAh6qJtfe9NKZ   live config contract, method link
EQCsNH1LY2PQeqG2HKSk2myObNTgCuREdq1IVvZFL4k_5iia   retired fallback, active since 2026-02-18
EQBaz-F9o6nHrQ16Z_jTGZShLyRNEMRtS0OWMYs0GNQK_PHA   retired fallback, active since 2026-02-19

admin wallet   0:7fbd655627967cbaa3b4b6137fb264cf1b6d4c37667ad574c143f282f7a402d4
               EQ-9ZVYnlny6o7S2E3-yZM8bbUw3ZnrVdMFD8oL3pALU   all 158 config writes, since 2026-10-12
older address  0:412f898b932b4a7489f2aa0f9c6f8ba7902d5f51e215e2faf72c59a37eab431b   since 2025-12-17
```

## Handles and identities

```
FunServer            Telegram channel display name
@weloverat           the operator's changelog channel, created 2026-02-25
FunMaster            author field in the mod
kartohaantoha36-6a9  Cloudflare account subdomain used by both Workers
maksv              Windows account in the DLL PDB paths (CookiveV2, padlock)
@virusfinder       TikTok account that shared the sample, not the operator
```

## Targets of the launcher credential theft

Lunar, Feather, Essential (`essential\microsoft_accounts.json`), IAS, Prism, MultiMC, Modrinth,
CurseForge (`CurseForge.json`, `CurseForge_msa.json`). Telegram sessions (`tdata`,
`Telegram Desktop`) are also harvested.

## Discord abuse

```
discord.com/api/v9/users/@me                        token validation
discord.com/api/v6/users/@me                        older path, same purpose
discord.com/api/v9/users/@me/billing/payment-sources   card data
discord.com/api/v9/channels/<id>/messages              mass channel spam
discord.com/api/v9/users/@me/relationships           friend enumeration
discord.com/api/v9/users/@me/channels                DM creation
1443109598579003414                                  author's own guild, used as a skip check
dQw4w9WgXcQ:[^"]*                                    Discord token regex in leveldb
https://t.me/weloverat                               advertised by the spam
https://discord.gg/TSYMmF9B2y                        advertised by the spam
```

Spam text: `@everyone @here YOUR MINECRAFT ACCOUNT IS ALREADY STOLEN ... funrat.mom`

## Other strings

```
FunMaster                       mod author field
Fun mod                         mod display name
hello-world-fabric              mod id
thin-report                     stage 1 exfil thread name
err-report                      crash beacon thread name
30e3267e-d80c-4a8f-84e2-bb475930a37c   hardcoded campaign key, skips the spam component
Stop testing rat yourself       string in the Discord component
nyx.elevate.test                assembly identity inside nyx.bin
CookiveV2.dll                   injected browser helper, no export table, named pipe IPC
padlock.dll                     lock screen, exports PadlockStartKeyboard/Mouse/Screen,
                                PadlockEnable*, PadlockGetOverlayWindow, PadlockSetDisplayPower
thin-report / err-report        beacon thread names
```

## Targets of the credential stealer

Browsers: Chrome, Chrome Beta, Chrome Dev, Chrome SxS, Edge, Brave (`Login Data`, `Local State`, `Cookies`).
Wallets: Electrum, Exodus, ExodusWeb3, Coinomi, Metamask, Phantom, TrustWallet, Pali, plus generic
`DesktopCryptoWallets` / `WebCryptoWallets` / `\Wallets` sweeps.
Game and account tokens: Steam, Epic, EA, Xbox, Apple, Microsoft account, Discord (injection).
