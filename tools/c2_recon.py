#!/usr/bin/env python3
"""Passive replay of the FunRat C2 lookup.

Only talks to TON's public APIs to learn the addresses, then does plain GETs on whatever
the contract points at. Nothing here runs the malware.
"""

import base64
import json
import re
import sys
import urllib.request

UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"

TON_CONFIG_CONTRACT = "EQChU6Zxa_y2fKeeCaSDubGcFZizRdRVSArwAh6qJtfe9NKZ"   # rattle.cipher
TON_FALLBACKS = [                                                          # rattle.gadget
    "EQCsNH1LY2PQeqG2HKSk2myObNTgCuREdq1IVvZFL4k_5iia",
    "EQBaz-F9o6nHrQ16Z_jTGZShLyRNEMRtS0OWMYs0GNQK_PHA",
]
RELAYS = [
    "https://relay-3f9c71.kartohaantoha36-6a9.workers.dev",               # rattle.slipper
    "https://relay-mirror.kartohaantoha36-6a9.workers.dev",               # rattle.ember
]


def http(url, method="GET", body=None, headers=None, timeout=30):
    req = urllib.request.Request(url, method=method, data=body)
    req.add_header("User-Agent", UA)
    for k, v in (headers or {}).items():
        req.add_header(k, v)
    try:
        with urllib.request.urlopen(req, timeout=timeout) as r:
            return r.status, r.read()
    except urllib.error.HTTPError as e:
        return e.code, e.read()
    except Exception as e:
        return None, str(e).encode()


# ---- rattle.apple(int arg) : C2 list stored in a TON contract method result ----
def apple(arg=8):
    body = ('{"address":"%s","method":"link","stack":[["int","%d"]]}'
            % (TON_CONFIG_CONTRACT, arg)).encode()
    code, data = http("https://toncenter.com/api/v2/runGetMethod", "POST", body,
                      {"Content-Type": "application/json"})
    print("[*] runGetMethod -> HTTP %s (%d bytes)" % (code, len(data)))
    m = re.search(rb'"b64":"([A-Za-z0-9+/=]+)"', data)
    if not m:
        print("[!] no b64 field; raw response follows:")
        print(data[:2000].decode("utf-8", "replace"))
        return None
    return base64.b64decode(m.group(1)).decode("utf-8", "replace")


# ---- rattle.maroon(addr) : base URL stored in a TON message cell ----
def maroon(addr):
    url = "https://tonapi.io/v2/blockchain/accounts/%s/methods/getMessage" % addr
    code, data = http(url, headers={"Accept": "application/json"})
    print("[*] tonapi getMessage %s -> HTTP %s (%d bytes)" % (addr[:16] + "..", code, len(data)))
    m = re.search(rb'"cell":"([0-9a-fA-F]+)"', data)
    if not m:
        print("[!] no cell field; raw response follows:")
        print(data[:1500].decode("utf-8", "replace"))
        return None
    return stork(m.group(1).decode())


# ---- rattle.stork(hex) : minimal TON Cell payload extractor ----
def stork(hexstr):
    b = bytes.fromhex(hexstr)
    i = 4
    v3 = b[i]; i += 1
    d = v3 & 7
    v5 = b[i]; i += 1
    be = lambda off, n: int.from_bytes(b[off:off + n], "big") if n else 0
    v6 = be(i, d); i += d
    v7 = be(i, d); i += d
    i += d + v5 + v7 * d
    if v3 & 128:
        i += v6 * v5
    i += 1
    v8 = b[i]; i += 1
    n = (v8 + 1) // 2
    return b[i:i + n].decode("utf-8", "replace")


def main():
    print("=== 1. C2 list from TON contract (%s, method link, arg 8) ===" % TON_CONFIG_CONTRACT)
    cfg = apple(8)
    if cfg:
        print("[+] decoded C2 list: %r" % cfg)

    print("\n=== 2. payload base URL from TON message cells ===")
    for addr in TON_FALLBACKS:
        u = maroon(addr)
        if u:
            print("[+] cell payload -> %r" % u)
            if not u.startswith("http"):
                u = "https://" + u
            if "/api/" not in u:
                u = u.rstrip("/") + "/api/hlam/files/4001"
            print("[+] stage-2 URL: %s" % u)

    print("\n=== 3. hardcoded relays (reachability only, no attacker POST) ===")
    for r in RELAYS:
        code, data = http(r + "/config", timeout=15)
        print("[*] %s/config -> HTTP %s : %s" % (r, code, data[:400].decode("utf-8", "replace")))


if __name__ == "__main__":
    main()
