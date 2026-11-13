# tools

Three small scripts, all offline except `c2_recon.py`.

**decode.py** — the string layer on its own. Useful for poking at one encoded literal.

```bash
python3 -c 'from decode import decode_stream, show; print(show(decode_stream("edgejdga-cjgejhgc-gbdpmf", 485 << 16 | -19995)))'
```

**deobf.py** — the source rewriter. Feed it the vineflower output; it resolves the hidden
invokedynamic calls and the encoded strings in place and writes `<name>.deobf.java` next to
the input, plus a resolved call table on stdout.

```bash
java -jar vineflower-1.12.0.jar -fdi=1 -dgs=1 sample.jar out_src
python3 deobf.py out_src/aurora/*.java
```

The obfuscated names change between builds, so they are flags. Stage 1 uses the defaults:

```bash
python3 deobf.py --indy=nutmeg.fresco --str=fable.stream stage1_file.java          # the mod
python3 deobf.py --indy=hwefggxaqr.eqbybn --str=ixuofesnss.klxfqq prawn.java       # back.jar
```

**c2_recon.py** — replays the C2 lookup without running the malware. Asks the TON contract
what the current base URL is, reads `/config`, and prints the slot map and payload URLs. It
also ports the TON message cell parser so you can read the fallback addresses out of the
blockchain.

```bash
python3 c2_recon.py
```

It hits toncenter.com and tonapi.io, then does plain GETs on the relay. If you would rather not
touch the attacker's host at all, stop after the first function.
