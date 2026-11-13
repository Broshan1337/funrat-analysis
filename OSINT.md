# OSINT: the FunRAT operation

Everything here comes from public sources: the operator's own Telegram channel, public
certificate and registration records, the public TON blockchain, the panel's public web pages,
and build artefacts inside the samples. No logins, no scraping behind authentication, no
attempts to access the operator's accounts.

## The product

The landing page at `https://funrat.mom/` is public and calls itself **FunRAT**, "The Ultimate
Minecraft RAT", "Industry-Leading Tool". It lists the capability set in its own words:

* Data collection: Minecraft session tokens, IP and geolocation, real-time screenshots, Discord
  **and Telegram** credentials
* Remote access: file system explorer, live screen and webcam stream, remote mouse and keyboard
* Tools: **sale of Hypixel and DonutSMP items**, live token upgrader/refresher, Discord mass DM
  and spam tools
* Supported launchers it targets: Lunar, Feather, Essential, IAS, Prism, MultiMC, Modrinth,
  CurseForge

The last group is confirmed in the code, which is the first time I have seen a stealer treat the
launcher as a first-class target:

```
"Lunar"  "Feather"  "essential\microsoft_accounts.json"  "Ias"
"CurseForge.json"  "CurseForge_msa.json"  "CurseForge accounts exported to: "
"Modrinth data exported to: "
```

Telegram theft is also in the code and matches the marketing: `tdata`, `Telegram Desktop`,
`\Telegram`, plus DPAPI calls.

The business model is freemium. The operator's own channel post from 2026-03-03 says it plainly:

> Spam will also be returned to the free version soon (we need advertising xD), and a premium
> feature will be added that will disable it.

That is exactly the `isPremium()` gate and the `dis.jar` spam component we found in stage 2a. The
spam is not a bug, it is the customer acquisition channel for the free tier.

The channel display name is **FunServer**. Support is "website ticket or channel message". The
panel offers Google and Discord login for customers.

## Timeline, with the correlations that matter

The interesting part is that three independent record types line up: the operator's Telegram
posts, domain registration records, and writes to the TON contract.

| date | Telegram channel | domain records | on-chain | sample artefacts |
|---|---|---|---|---|
| 2026-02-25 | channel `@weloverat` created | | | |
| 2026-02-26 | "We are now open!", pinned link `https://funrat.lol/` | | | |
| 2026-02-27 | "my Discord account is banned. That's why I don't want to make a server in ds" | | | |
| 2026-03-03 | free tier gets the spam, premium removes it | | | |
| 2026-03-04 | "Updated Telegram reception", more Telegram clients | | | |
| 2026-06-03 | "We have removed 'Suspected Malware warning'" (browser flagged the site) | | | |
| 2026-07-27 | mod supports MC 1.21 and "26+" | | | our sample targets MC 26.2 |
| 2026-09-24 | "RAT is temporarily not working... Some idiots sent report to all possible services" | | | |
| 2026-10-12 21:04 | "We've rebuilt our delivery pipeline" | | contract `EQChU6Zx...` created 12:06, admin wallet first seen 11:07 | |
| 2026-10-17/18 | outage, "the rest is on the hoster" | `funrat.top` put in `serverHold` | | |
| 2026-10-18 | "new builds come through clean on both VirusTotal and Ratter Scanner" | | | |
| 2026-11-04 | | | contract writes 03:07, 03:08, 03:10, 03:15, 03:17 | jar entries stamped 11-04-2026 **03:36** |
| 2026-11-06 10:18 | "New address: https://funrat.lol" | `funrat.lol` registered 07:00 | contract writes 07:06, 07:12, 09:58 | |
| 2026-11-09 06:39 | "Again new domain https://funrat.mom" | `funrat.mom` registered 06:17, LE wildcard cert 05:35 | contract writes 06:29, 06:29, 06:43 | |
| 2026-11-10 | | `funrat.lol` suspended (`serverHold` + `clientHold`) | | |
| 2026-11-13 22:59 | "mod won't work until lunch" | | | our two test POSTs were 22:21 |

The Nov 4 sequence is the tightest: five contract writes between 03:07 and 03:17 UTC, then the
payload jars are stamped 03:36 the same morning. Whoever updated the on-chain config and built the
jars was working in the same sitting.

On the last row: I want to be careful. Their "mod won't work until lunch" post appeared 38 minutes
after our two test POSTs. I do not know whether it is related. I stopped active probing after
that, because if it was related then a couple of junk requests were enough to disturb their
service, and I would rather not be the reason a victim's machine suddenly stops reporting while
everyone is mid-investigation.

## Domain rotation

Four domains, one pattern, all registered at NameSilo, all on `ns1.desec.io` / `ns2.desec.org`:

| domain | registered | status today |
|---|---|---|
| `funrat.top` | 2026-10-18 | `serverHold`, dead |
| `funrat.lol` | originally pinned from Feb 2026, current registration 2026-11-06 | `serverHold` + `clientHold`, dead |
| `funrat.mom` | 2026-11-09 | live |
| `funrat.co` | not registered | referenced in `back.jar` as the upload base, does not resolve |

`funrat.lol` being re-registered in November after being the pinned link since February means it
was lost and bought back. The registrar has suspended two of the four, so the registrar route
works, and the operator replaces a domain within about 24 hours of losing one.

## On-chain infrastructure

The C2 list lives in TON. Reading `link` on the live contract right now:

```
arg 0 -> https://relay-3f9c71.kartohaantoha36-6a9.workers.dev/file/1c7afe74291530de8b03   stage 2a
arg 1 -> https://relay-3f9c71.kartohaantoha36-6a9.workers.dev/file/abfaed33f1789420be6b   back.jar
arg 2 -> https://relay-3f9c71.kartohaantoha36-6a9.workers.dev/file/spe4b5469f67894bf5bc   dis.jar
arg 3 -> https://funrat.mom
arg 7 -> https://relay-3f9c71.kartohaantoha36-6a9.workers.dev/file/7b247818da28e273f071
arg 8 -> https://relay-3f9c71.kartohaantoha36-6a9.workers.dev
arg 9 -> https://funrat.mom
```

Note arg 7: the contract serves `7b247818da28e273f071` while the `/config` relay serves
`sp46208b0cb8514d8a88` for the same slot. The two sources are out of sync, so a victim that falls
back to the chain gets a different payload than one that reads `/config`. Worth knowing when you
compare samples in the wild.

Wallet cluster, all from public chain data:

```
admin wallet   0:7fbd655627967cbaa3b4b6137fb264cf1b6d4c37667ad574c143f282f7a402d4
               EQ-9ZVYnlny6o7S2E3-yZM8bbUw3ZnrVdMFD8oL3pALU
               158/158 writes to the live contract, first seen 2026-10-12 11:07, balance 0.45 TON

second address 0:412f898b932b4a7489f2aa0f9c6f8ba7902d5f51e215e2faf72c59a37eab431b
               27 txs from 2025-12-17, balance 1.97 TON, writes to both fallback contracts

fallback 1     EQCsNH1LY2PQeqG2HKSk2myObNTgCuREdq1IVvZFL4k_5iia   (0:ac347d4b...)  active since 2026-02-18
fallback 2     EQBaz-F9o6nHrQ16Z_jTGZShLyRNEMRtS0OWMYs0GNQK_PHA   (0:5acfe17d...)  active since 2026-02-19
```

Both fallback contracts still exist and still hold balance, but `link` now returns nothing for
them, so they are retired storage rather than live config. The `0:412f898b...` address is the
oldest thing in the whole cluster, active since **December 2025**, and it touches the February
2026 fallback contracts. That is the address to watch: it predates the current campaign by ten
months and probably predates the product.

The chain is not the weak point for takedown, but it is the strongest attribution asset the case
has, because the same wallet has been signing config updates continuously since October and the
older one since December.

## Attribution leads

What I have, ranked by how much I trust it:

1. **`maksv`** — both native DLLs carry PDB paths from the same Windows account:
   `C:\Users\maksv\source\repos\CookiveV2\x64\Release\CookiveV2.pdb` and
   `C:\Users\maksv\source\repos\padlock\x64\Release\padlock_dll.pdb`. Same machine built the
   browser injector and the lock screen. This is a build artefact, not an identity, and I am not
   going to guess at who is behind it. It is the single best lead and it belongs in a police
   report, not in a blog post.
2. **Language profile.** The English throughout is consistent and non-native with a Slavic
   pattern: "Now all work", "If you bought it earlier, write here, I'll give it to him now",
   "Alredy fold inject in discord", "Stop testing rat yourself". The stage 2a entry points are
   named `mais`, `mains` and `vov`. Suggestive, not evidence.
3. **Handles.** `FunServer` (Telegram display name), `@weloverat`, `FunMaster` (the mod's author
   field), and the Cloudflare account subdomain `kartohaantoha36-6a9`. No cross-platform username
   match found for any of them.
4. **`@virusfinder` on TikTok** ("Finding viruses in every file", 103 followers, 7 videos) posted
   the link the malware's channel shared. That looks like a small content account covering the
   sample, not the operator. I am noting it because it is part of how this spreads, not because
   it is a lead on the author.
5. The operator says their Discord account was banned in Feb 2026, which explains why the guild
   `1443109598579003414` is small and why the invite `TSYMmF9B2y` now returns `Unknown Invite`.

I did not find a real name, and I do not think publishing one from these fragments would be
responsible or correct. The `maksv` path, the wallet cluster and the hosting and registrar records
are enough for a law enforcement request to the relevant providers, which is the only route that
actually resolves a name.

## What I would do next with this

- Give the wallet cluster and the `maksv` path to whoever files the police report. That is the
  part only a provider can answer.
- Report `funrat.lol` to NameSilo as well, it is suspended but the registration record still
  exists and links the three domains to one buyer.
- Report `@weloverat` to Telegram with the channel's own posts as evidence. The changelog is a
  confession written in the first person, which is unusually good.
- Keep watching the contract. A new write to `link` means new infrastructure before any of it is
  public.
