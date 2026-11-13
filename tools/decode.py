#!/usr/bin/env python3
"""Decoder for the string layer in DonutDuper+ / FunRat.

Encoded strings look like fable.stream("mmgapcga-ijhepagi-...", key): letters a-p are
nibbles, and each byte is xored against a cycling 4 byte key. The same encoding carries
the hidden invokedynamic targets as class, method and descriptor, separated by NUL bytes.
"""
import re, sys, json

def decode_stream(s: str, key: int) -> bytes:
    if s and ord(s[0]) >= 256:
        out = bytearray()
        for i, ch in enumerate(s):
            out.append(((ord(ch) - 256) ^ ((key >> (8 * (i & 3))) & 0xFF)) & 0xFF)
        return bytes(out)
    out = bytearray()
    hi = -1
    for ch in s:
        if ch == '-':
            continue
        lo = ord(ch) - ord('a')
        if hi < 0:
            hi = lo
        else:
            out.append((((hi << 4) | lo) ^ ((key >> (8 * (len(out) & 3))) & 0xFF)) & 0xFF)
            hi = -1
    return bytes(out)

def parse_key(expr: str) -> int:
    e = expr.strip()
    m = re.fullmatch(r'(\d+)\s*<<\s*16\s*\|\s*\(char\)\s*(-?\d+)', e)
    if m:
        return ((int(m.group(1)) << 16) | (int(m.group(2)) & 0xFFFF)) & 0xFFFFFFFF
    m = re.fullmatch(r'\(char\)\s*(-?\d+)', e)
    if m:
        return int(m.group(1)) & 0xFFFF
    m = re.fullmatch(r'(-?\d+)', e)
    if m:
        return int(m.group(1)) & 0xFFFFFFFF
    raise ValueError('unparsed key: %r' % expr)

def split_args(s: str):
    """split a java arg-list string on top-level commas (angle brackets tracked,
    but '<<', '>>', '<=', '>=', '->' are not treated as bracket delimiters)"""
    args, depth, cur, i = [], 0, [], 0
    in_str = in_char = False
    while i < len(s):
        c = s[i]
        if in_str:
            cur.append(c)
            if c == '\\':
                if i + 1 < len(s):
                    cur.append(s[i+1]); i += 2; continue
            if c == '"':
                in_str = False
            i += 1; continue
        if in_char:
            cur.append(c)
            if c == '\\':
                if i + 1 < len(s):
                    cur.append(s[i+1]); i += 2; continue
            if c == "'":
                in_char = False
            i += 1; continue
        if c == '"':
            in_str = True; cur.append(c); i += 1; continue
        if c == "'":
            in_char = True; cur.append(c); i += 1; continue
        if c in '([{':
            depth += 1; cur.append(c); i += 1; continue
        if c in ')]}':
            depth -= 1; cur.append(c); i += 1; continue
        if c == '<':
            nxt = s[i+1] if i + 1 < len(s) else ''
            if nxt in '<=':
                cur.append(c); cur.append(nxt); i += 2; continue
            depth += 1; cur.append(c); i += 1; continue
        if c == '>':
            prev = s[i-1] if i else ''
            nxt = s[i+1] if i + 1 < len(s) else ''
            if nxt == '>' or prev in '>=-':
                cur.append(c); i += 1; continue
            if depth > 0:
                depth -= 1
            cur.append(c); i += 1; continue
        if c == ',' and depth == 0:
            args.append(''.join(cur).strip()); cur = []; i += 1; continue
        cur.append(c); i += 1
    if ''.join(cur).strip():
        args.append(''.join(cur).strip())
    return args

def show(b: bytes) -> str:
    try:
        t = b.decode('utf-8')
    except UnicodeDecodeError:
        return 'b' + repr(b)
    return json.dumps(t)

if __name__ == '__main__':
    pass
