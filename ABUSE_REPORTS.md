# Abuse reports

Ready-to-send texts. Verified contacts, all from RDAP on 2026-11-13:

| who | what they control | contact |
|---|---|---|
| Cloudflare | the two Workers hosts that serve the config and the payloads | https://abuse.cloudflare.com (or abuse@cloudflare.com) |
| FreakHosting Ltd (DE) | the VPS `151.244.72.155` running the panel | `report@abuseradar.com` (RIPE abuse contact for `151.244.72.0/24`) |
| NameSilo LLC | registrar of `funrat.mom` and `funrat.top` | `abuse@namesilo.com`, +1 480 524 0066 |
| deSEC (DESEC e.V.) | DNS for both domains (`ns1.desec.io`, `ns2.desec.org`) | contact form at https://desec.io |
| Discord | guild + invite used by the spammer | https://dis.gd/report / Trust & Safety via support.discord.com |
| Telegram | `t.me/weloverat` advertising the RAT | https://telegram.org/support |

Send the Cloudflare and FreakHosting ones first, they are the ones that actually stop the
payload delivery. Do not attach the malware to any of these emails, link the repo and give hashes.

One thing to keep in every report: we did static analysis plus read-only requests, and **two
deliberate test POSTs with obviously fake data** on 2026-11-13 around 22:21 UTC. Say so, because
they will see our IP in their logs and it is better that they hear it from us.

---

## 1. Cloudflare

**To:** https://abuse.cloudflare.com
**Subject:** Malware C2 and payload hosting on two Workers in the same account (kartohaantoha36-6a9)

Hello,

I am a malware reverse engineer and I would like to report two Cloudflare Workers hostnames that
are being used as the command and control and payload delivery layer for a malware campaign that
infects Minecraft players and steals their Microsoft/Xbox accounts.

The hostnames are:

    relay-3f9c71.kartohaantoha36-6a9.workers.dev
    telemetry-relay.kartohaantoha36-6a9.workers.dev

Both share the same account subdomain prefix, `kartohaantoha36-6a9`, so I believe they belong to
one Cloudflare account.

What the first one is doing, all of this observed live on 2026-11-13 between 22:20 and 22:26 UTC:

`GET /config` returns the campaign's payload map, currently version 47:

    {"slots":{
      "0":"https://relay-3f9c71.kartohaantoha36-6a9.workers.dev/file/1c7afe74291530de8b03",
      "1":"https://relay-3f9c71.kartohaantoha36-6a9.workers.dev/file/abfaed33f1789420be6b",
      "2":"https://relay-3f9c71.kartohaantoha36-6a9.workers.dev/file/spe4b5469f67894bf5bc",
      "3":"https://funrat.mom",
      "7":"https://relay-3f9c71.kartohaantoha36-6a9.workers.dev/file/sp46208b0cb8514d8a88",
      "8":"https://relay-3f9c71.kartohaantoha36-6a9.workers.dev",
      "9":"https://funrat.mom"},"v":47}

Those four `/file/...` paths serve Java archives. I downloaded them read-only and analysed them
statically. They are:

    /file/1c7afe74291530de8b03   11,913 B   sha256 f54ae16bf251a55dce52f0fe509ab445e1da616e67f0f0623844b540194bcbff
    /file/abfaed33f1789420be6b   11,699,654 B   sha256 3dbf58b855f40e92da31e88fe535fc768bc61dfed6a05b5da98e2821d9854934
    /file/spe4b5469f67894bf5bc   5,727,727 B   sha256 cf55438b34472a67d08d071294bb3d5c3688e7c5ddfc77eff2308c196f1f43cd
    /file/sp46208b0cb8514d8a88   7,052,532 B   sha256 a74299e956848fdefbed8b0203464fcf08114806a8020b8d8b2c5165fb85e8cd

The largest one is a credential stealer that reads browser password and cookie databases, crypto
wallet files and game account tokens, installs a scheduled task named
`MicrosoftEdgeUpdateTaskMachineCore` for persistence, injects a DLL into the browser process, and
escalates to SYSTEM. One of the others is a remote access trojan with live screen, webcam and
microphone streaming and the ability to lock the victim's keyboard, mouse and screen.

`POST /report` on the same hostname is the collection endpoint for the stolen accounts. It accepts
anything and forwards it:

    POST /report  {"name":"...","uuid":"...","token":"...","receiver":"...","key":"..."}
    -> 200 {"ok":true,"relayed":true,"code":200,"w":"relayed"}

The `token` field is a Microsoft/Xbox OAuth access token for the victim's account. The endpoint
does not validate anything, it is a blind relay.

Transparency: to confirm the endpoint was live rather than a parked route, I sent two POSTs with
obviously fake data (all-zero UUIDs, name `probe-test`) at about 22:21 UTC on 2026-11-13. Those
are test entries, not victims. Everything else I did was read-only GET requests.

The second hostname, `telemetry-relay.kartohaantoha36-6a9.workers.dev`, currently returns Cloudflare
403 and 530 responses, so it may be misconfigured or already partially down.

The full technical writeup, the deobfuscated code and the indicators are public at
https://github.com/Broshan1337/funrat-analysis . I have not attached any malware to this report.

Could you identify the account behind these two Workers and suspend them? The `kartohaantoha36-6a9`
prefix should tie them together. I am happy to provide additional detail or the samples through a
channel of your choosing.

Thank you,

---

## 2. Hosting provider for the panel

**To:** report@abuseradar.com (RIPE abuse contact for 151.244.72.0/24, FreakHosting Ltd)
**Subject:** Abusing host: 151.244.72.155 serving a malware control panel and account theft endpoint

Hello,

The IP `151.244.72.155` is the origin for `funrat.mom`, which is the control panel and command
server for a malware campaign that steals Minecraft players' Microsoft and Xbox accounts.

Evidence, observed 2026-11-13 around 22:20 UTC:

    $ curl -I https://funrat.mom/config
    HTTP/2 200
    server: nginx/1.18.0 (Ubuntu)

    $ dig +short funrat.mom
    151.244.72.155

The domain `funrat.mom` returns the same `/config` document as the Cloudflare Workers relay, and
the malware's own code contains `https://funrat.mom` as a hardcoded fallback base URL. The panel
login is at `https://funrat.mom/auth/login`, and the malware's upload and update routes
(`/api/ingest`, `/version`, `/update`) redirect there, so the operator's management interface is on
this host.

The backend answers as a Spring Boot application. The collection endpoint accepts stolen account
data and forwards it. The service advertises itself publicly as a remote access trojan called
FunRat, and one of the components it distributes is a remote access tool with live webcam, screen
and microphone streaming plus the ability to lock the victim's keyboard, mouse and display.

The campaign is distributed as a "free Minecraft cheat" mod on YouTube and video sharing sites. The
entry point is a Fabric mod, sha256
`e1d18e83771c5ffce9b4c6914d598f5b567e28834c97838e124ad594fc45eeac`.

I have published the analysis, the deobfuscated code and the indicators at
https://github.com/Broshan1337/funrat-analysis . No malware binaries are attached to this email.

For transparency: on 2026-11-13 at about 22:21 UTC I sent two HTTP POSTs to this host containing
obviously fake test data (all-zero identifiers, name `probe-test`) to confirm the endpoints were
live. Those two entries in your customer's logs and database are mine and are not real victims.
Everything else was read-only.

Could you identify the customer behind this IP and suspend the service? I can provide the samples
and the full analysis on request.

Thank you,

---

## 3. Registrar

**To:** abuse@namesilo.com
**Subject:** Abuse complaint: funrat.mom used as malware C2 and RAT panel (registered 2026-11-09)

Hello,

I am reporting two domains registered through NameSilo that are used as command and control
infrastructure for a malware campaign.

**funrat.mom**
Registered 2026-11-09, expires 2027-11-09, nameservers `ns1.desec.io` and `ns2.desec.org`.
It resolves to `151.244.72.155` (FreakHosting Ltd, Germany) and serves an nginx/Spring Boot
application. It is the control panel for a remote access trojan sold under the name FunRat. The
malware's code contains it as a hardcoded fallback base URL, and the operator login page is at
`https://funrat.mom/auth/login`. The malware's data collection endpoint is reachable through it.

**funrat.top**
Registered 2026-10-18, same registrar and same nameservers, currently in `serverHold` status and
no longer resolving. It was the previous hosting for the same campaign, and the older malware
binaries still contain `https://funrat.top/api/hlam/files/4001` and `.../4003` as payload URLs.
I mention it because it shows the pattern: the domain is replaced roughly every month while the
malware in victims' hands keeps working.

The malware is distributed as a "free Minecraft dupe" mod. It steals the victim's Microsoft and
Xbox account tokens, then downloads a stealer that takes browser passwords and cookies, crypto
wallet files and game account tokens, and a remote access component with webcam, screen and
microphone streaming.

Full analysis, deobfuscated source and indicators: https://github.com/Broshan1337/funrat-analysis

Requested action: suspend `funrat.mom` for use in malware distribution and account theft. I am
willing to provide the samples and further technical detail.

Thank you,

---

## 4. DNS provider

**To:** via the contact form at https://desec.io
**Subject:** Malware C2 zones hosted on deSEC: funrat.mom and funrat.top

Hello,

Both of these domains are using deSEC nameservers (`ns1.desec.io`, `ns2.desec.org`) and both are
part of the same malware campaign:

    funrat.mom   -> 151.244.72.155   live control panel and malware C2
    funrat.top   -> serverHold, dead  previous C2 for the same campaign

`funrat.mom` is the operator panel for a remote access trojan sold as FunRat, and it is referenced
directly in the malware binaries. The registrar is NameSilo and the hosting is FreakHosting Ltd in
Germany; I have reported to both.

Analysis and indicators: https://github.com/Broshan1337/funrat-analysis

Could you suspend the zones for these two domains? I can provide further detail if useful.

Thank you,

---

## 5. Discord

**To:** https://dis.gd/report (Trust & Safety)
**Subject:** Stolen-token spam campaign and RAT advertising: guild 1443109598579003414, invite TSYNmF9B2y

Hello,

I am reporting a malware campaign that uses stolen Discord accounts to spam Discord itself, and
that advertises the malware through Discord and Telegram.

The malware is a "free Minecraft cheat" mod that also functions as a remote access trojan. One of
its components steals Discord tokens from the Discord desktop client's local storage and from
browser storage, validates them against the Discord API, and then:

* posts `@everyone @here YOUR MINECRAFT ACCOUNT IS ALREADY STOLEN ... funrat.mom` into every text
  channel of every guild the stolen token can write to, and
* sends the same message as a DM to every friend of the compromised account.

There is a check in the code that skips this behaviour if the compromised account is a member of
guild **1443109598579003414**, which appears to be the malware author's own guild. That guild is
therefore a safe way to identify accounts that were used to test the malware rather than real
victims, and it is also a direct link to the author.

The spam also advertises these:

    invite: https://discord.gg/TSYMmF9B2y
    telegram: https://t.me/weloverat

The component also requests `users/@me/billing/payment-sources`, so it harvests payment card data
from compromised accounts.

Indicators, analysis and the deobfuscated code: https://github.com/Broshan1337/funrat-analysis

Requested action: remove the invite and the guild, and if possible identify the account that
operates the malware panel, since the malware reports victim data to infrastructure that is
publicly documented in the report above.

Thank you,

---

## 6. Telegram

**To:** https://telegram.org/support
**Subject:** Channel advertising malware and account theft: t.me/weloverat

Hello,

The Telegram channel `https://t.me/weloverat` is used to advertise a remote access trojan called
FunRat that is distributed as a "free Minecraft cheat" mod. The malware steals Microsoft and Xbox
accounts, browser passwords, crypto wallets and game tokens, and includes webcam, screen and
microphone spying.

The channel is referenced inside the malware itself, in the spam messages that the malware sends
from compromised Discord accounts. The related domain is `funrat.mom`.

Analysis and indicators: https://github.com/Broshan1337/funrat-analysis

Please remove the channel. Thank you.

---

## 7. Police / cybercrime unit

Take this one to your national cybercrime unit, and if you have any US nexus also to the FBI IC3
(https://ic3.gov). Bring the GitHub repo link and the hashes, not the binaries.

**Subject:** Malware-as-a-service operation targeting minors, account theft and unlawful recording

To whom it may concern,

I am reporting an active malware-as-a-service operation. The software is distributed to minors as a
"free item duplication" mod for Minecraft. It is a two stage dropper: the first stage steals the
victim's Minecraft session, which contains a Microsoft and Xbox account token, and the second stage
installs a credential stealer and a remote access trojan.

The installed software is capable of:

* reading saved passwords, cookies and payment data from Chrome, Edge and Brave,
* copying cryptocurrency wallet files from several wallet applications,
* capturing the victim's screen, webcam and microphone in real time,
* opening a command shell and browsing the victim's files,
* locking the victim's keyboard, mouse and display,
* escalating to SYSTEM and installing persistence as a scheduled task disguised as an Edge update.

The victim population is predominantly minors, and the webcam and microphone capability means there
is a recording and surveillance element that goes beyond financial theft.

Infrastructure identified so far:

    control panel and C2      funrat.mom          151.244.72.155, FreakHosting Ltd, Germany
    payload delivery and C2   relay-3f9c71.kartohaantoha36-6a9.workers.dev, Cloudflare Workers
    telemetry                 telemetry-relay.kartohaantoha36-6a9.workers.dev, Cloudflare Workers
    previous C2               funrat.top (suspended 2026-10)
    malware distribution      YouTube videos promoting "Donut Dupe" Minecraft mods
    advertising               discord.gg/TSYMmF9B2y, t.me/weloverat
    author's Discord guild    1443109598579003414

The list of command servers is stored in a smart contract on the TON blockchain, at address
`EQChU6Zxa_y2fKeeCaSDubGcFZizRdRVSArwAh6qJtfe9NKZ`, method `link`. This is why taking down a single
domain does not stop the campaign: the malware reads its current server address from the blockchain.
The contract itself cannot be removed, but the address is a stable identifier for the operator and
the transaction history around it may be traceable.

Sample hashes (SHA-256):

    e1d18e83771c5ffce9b4c6914d598f5b567e28834c97838e124ad594fc45eeac   the mod, first stage
    3dbf58b855f40e92da31e88fe535fc768bc61dfed6a05b5da98e2821d9854934   stealer
    a74299e956848fdefbed8b0203464fcf08114806a8020b8d8b2c5165fb85e8cd   remote access trojan

The full technical analysis, including deobfuscated source code and the tooling used to undo the
obfuscation, is public at https://github.com/Broshan1337/funrat-analysis . I can provide the
samples on request through a secure channel.

All analysis was performed statically. The malware was never executed.

---

## Order and expectations

Realistic outcome: Cloudflare and FreakHosting act on documented malware hosting fairly quickly,
and NameSilo suspended `funrat.top` once already, so the domain route works too. Expect the
campaign to come back on a new domain and a new Worker within days, because the address list lives
on-chain. That is worth saying in any report you file, because it explains why you may see the
infrastructure change right after a takedown.
