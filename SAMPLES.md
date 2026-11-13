# Samples

Hashes only. The binaries are deliberately not in this repo.

## Stage 1 — the mod

| | |
|---|---|
| file | `DonutDuper+.jar` |
| size | 75,355 B |
| SHA-256 | `e1d18e83771c5ffce9b4c6914d598f5b567e28834c97838e124ad594fc45eeac` |
| MD5 | `b86837bb67c517c25ca71cfc821f8723` |
| type | Java archive, Fabric mod |
| manifest | Fabric-Loader 0.19.3, Minecraft 26.2, Main-Class none |
| fabric.mod.json | id `hello-world-fabric`, name "Fun mod", author "FunMaster", entrypoint `aurora.orchid`, MC >= 1.21, Java 21 |
| classes | `aurora/orchid, rattle, pillar, sardine, nutmeg, fable, medal` |

## Stage 2 payloads (fetched from the live `/config` on 2026-11-13, config version 47)

| slot | role | size | SHA-256 |
|---|---|---|---|
| 0 | stage 2a launcher, `core.core` | 11,913 | `f54ae16bf251a55dce52f0fe509ab445e1da616e67f0f0623844b540194bcbff` |
| 1 | `back.jar` implant, `aurora.prawn` | 11,699,654 | `3dbf58b855f40e92da31e88fe535fc768bc61dfed6a05b5da98e2821d9854934` |
| 2 | `dis.jar` Discord spammer, `org.example.Main` | 5,727,727 | `cf55438b34472a67d08d071294bb3d5c3688e7c5ddfc77eff2308c196f1f43cd` |
| 7 | RAT, `com.example.rat.RatSender` | 7,052,532 | `a74299e956848fdefbed8b0203464fcf08114806a8020b8d8b2c5165fb85e8cd` |

Download URLs at the time of writing:

```
0  https://relay-3f9c71.kartohaantoha36-6a9.workers.dev/file/1c7afe74291530de8b03
1  https://relay-3f9c71.kartohaantoha36-6a9.workers.dev/file/abfaed33f1789420be6b
2  https://relay-3f9c71.kartohaantoha36-6a9.workers.dev/file/spe4b5469f67894bf5bc
7  https://relay-3f9c71.kartohaantoha36-6a9.workers.dev/file/sp46208b0cb8514d8a88
3  https://funrat.mom
8  https://relay-3f9c71.kartohaantoha36-6a9.workers.dev
9  https://funrat.mom
```

## Native components (inside the jars, not reproduced here)

| file | where | size | SHA-256 |
|---|---|---|---|
| `nyx.bin` | root of slot 1 and slot 7 jars, identical | 211,456 | `42fb35024d3324569c01f8a1c7dd9d7eb4eb0146116bc5cc60e20e45251bd631` |
| `CookiveV2.dll` | root of slot 1 jar | 64,512 | `99d6881553218afeef61ea5408c16598d699c958e36f56effc8827638c44d4eb` |
| `dll/padlock.dll` | slot 7 jar | 23,552 | `9880b3df045f7a4a6b8a8e57bdea196f642db11f9e4b27fee45d36968fa8b686` |

`nyx.bin` is a PE32+ x86-64 executable stored XORed with `0x5a`.

## Bundled libraries

slot 1 and slot 7 both ship Jackson, Gson, Java-WebSocket, JNA (with the win32 `jnidispatch`
DLLs), sqlite-jdbc and sarxos webcam-capture. slot 2 ships Jackson. None of that is the malware
itself, but it is what gives the payloads DPAPI access, SQLite readers, WebSocket clients and
webcam capture without needing anything installed on the victim machine.
