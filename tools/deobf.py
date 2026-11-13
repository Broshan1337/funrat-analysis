#!/usr/bin/env python3
"""Turns the vineflower dump of the FunRat jars back into readable Java.

Resolves the hidden calls and encoded strings in place. The bootstrap and decoder get
renamed between builds, so the names are flags.
"""
import re, sys, json, os
from decode import decode_stream, parse_key, split_args

KINDS = {0:'static',1:'virtual',2:'ctor',3:'special',4:'static-get',5:'static-set',6:'get',7:'set'}

SIMPLIFY = {
 'java.lang.String':'String','java.lang.Object':'Object','java.lang.Integer':'Integer',
 'java.lang.Long':'Long','java.lang.Boolean':'Boolean','java.lang.Thread':'Thread',
 'java.net.HttpURLConnection':'HttpURLConnection','java.net.URL':'URL',
 'java.lang.reflect.Method':'Method','java.lang.StringBuilder':'StringBuilder',
 'java.lang.System':'System','java.lang.Runtime':'Runtime','java.lang.ProcessBuilder':'ProcessBuilder',
 'java.io.File':'File','java.lang.Exception':'Exception','java.lang.Throwable':'Throwable',
 'java.util.concurrent.atomic.AtomicInteger':'AtomicInteger',
}

def skip_string(s, i):
    q = s[i]; i += 1
    while i < len(s):
        if s[i] == '\\': i += 2; continue
        if s[i] == q: return i + 1
        i += 1
    return i

def find_angle_end(s, i):
    """i points just after the opening '<' of the indy static-arg list."""
    depth = 0
    while i < len(s):
        c = s[i]
        if c in '"\'':
            i = skip_string(s, i); continue
        if c in '([{': depth += 1; i += 1; continue
        if c in ')]}': depth -= 1; i += 1; continue
        if c == '<':
            if i+1 < len(s) and s[i+1] == '<': i += 2; continue
            depth += 1; i += 1; continue
        if c == '>':
            if i+1 < len(s) and s[i+1] == '>': i += 2; continue
            if depth == 0: return i
            depth -= 1; i += 1; continue
        i += 1
    raise ValueError('no angle end')

def find_paren_end(s, i):
    """i points at '(' -> return index of matching ')'."""
    assert s[i] == '('
    depth = 0
    while i < len(s):
        c = s[i]
        if c in '"\'':
            i = skip_string(s, i); continue
        if c == '(': depth += 1
        elif c == ')':
            depth -= 1
            if depth == 0: return i
        i += 1
    raise ValueError('no paren end')

def unescape_java(lit):
    """turn a java string literal body into the actual code points (keeps non-ASCII intact)"""
    out, i = [], 0
    esc = {'n':'\n','t':'\t','r':'\r','b':'\b','f':'\f','0':'\0','\\':'\\','"':'"',"'":"'"}
    while i < len(lit):
        c = lit[i]
        if c == '\\':
            n = lit[i+1] if i+1 < len(lit) else ''
            if n == 'u':
                out.append(chr(int(lit[i+2:i+6], 16))); i += 6; continue
            out.append(esc.get(n, n)); i += 2; continue
        out.append(c); i += 1
    return ''.join(out)

def split_top(s, sep):
    """split on a top-level single char, respecting strings and brackets"""
    parts, cur, depth, i = [], [], 0, 0
    while i < len(s):
        c = s[i]
        if c in '"\'':
            j = skip_string(s, i); cur.append(s[i:j]); i = j; continue
        if c in '([{': depth += 1
        if c in ')]}': depth -= 1
        if c == sep and depth == 0:
            parts.append(''.join(cur)); cur = []; i += 1; continue
        cur.append(c); i += 1
    parts.append(''.join(cur))
    return parts

def try_const(e):
    """constant-fold a java expression to a python str, or None if not statically known"""
    e = e.strip()
    m = re.fullmatch(r'"((?:[^"\\]|\\.)*)"', e, re.S)
    if m: return unescape_java(m.group(1))
    m = re.fullmatch(r'String\.valueOf\((.*)\)', e, re.S)
    if m: return try_const(m.group(1))
    parts = split_top(e, '+')
    if len(parts) > 1:
        vals = [try_const(p) for p in parts]
        if all(v is not None for v in vals): return ''.join(vals)
    # chained .concat(): peel the outermost call whose closing paren ends the expression
    i, last = 0, -1
    depth = 0
    while i < len(e):
        c = e[i]
        if c in '"\'':
            i = skip_string(e, i); continue
        if c in '([{': depth += 1
        elif c in ')]}': depth -= 1
        elif depth == 0 and e.startswith('.concat(', i):
            last = i
            i += 8; depth += 1; continue
        i += 1
    if last >= 0:
        try:
            end = find_paren_end(e, last + 7)
        except ValueError:
            return None
        if end == len(e) - 1:
            a = try_const(e[:last])
            b = try_const(e[last+8:end])
            if a is not None and b is not None:
                return a + b
    return None

def short(cls):
    return SIMPLIFY.get(cls, cls)

def parse_blob(b):
    parts = b.split('\0')
    if len(parts) != 3: return None
    return parts[0].replace('/', '.'), parts[1], parts[2]

def render(cls, name, kind, args):
    cls = short(cls)
    if kind == 0:  return '%s.%s(%s)' % (cls, name, ', '.join(args))
    if kind == 1:  return '%s.%s(%s)' % (args[0], name, ', '.join(args[1:])) if args else '?.%s()' % name
    if kind == 2:  return 'new %s(%s)' % (cls, ', '.join(args))
    if kind == 3:  return '%s.%s(%s)' % (args[0], name, ', '.join(args[1:])) if args else 'super.%s()' % name
    if kind == 4:  return '%s.%s' % (cls, name)
    if kind == 5:  return '%s.%s = %s' % (cls, name, args[0] if args else '?')
    if kind == 6:  return '%s.%s' % (args[0], name) if args else '?.%s' % name
    if kind == 7:  return '%s.%s = %s' % (args[0], name, args[1] if len(args) > 1 else '?') if args else '?.%s = ?' % name
    return 'INDY(kind=%s,%s.%s)(%s)' % (kind, cls, name, ', '.join(args))

STATS = {'indy':0,'str':0,'fail':0}

# names of the obfuscated helpers; overridable with --indy= / --str=
INDY_FN = 'nutmeg.fresco'
STR_FN  = 'fable.stream'
TABLE = []

def transform(src):
    out = []
    i = 0
    n = len(src)
    while i < n:
        if src.startswith(INDY_FN + '<', i):
            j = i + len(INDY_FN) + 1
            try:
                aend = find_angle_end(src, j)
                sargs = split_args(src[j:aend])
                k = aend + 1
                while k < n and src[k] in ' \t': k += 1
                pend = find_paren_end(src, k)
                dargs = split_args(src[k+1:pend])
                blit = re.fullmatch(r'"((?:[^"\\]|\\.)*)"', sargs[1], re.S)
                braw = unescape_java(blit.group(1)) if blit else sargs[1].strip('"')
                blob = decode_stream(braw, parse_key(sargs[2]))
                kind = int(sargs[3].strip())
                tgt = parse_blob(blob.decode('latin-1'))
                dargs = [transform(a) for a in dargs]
                if tgt is None:
                    STATS['fail'] += 1
                    out.append('INDY<%s>(%s)' % (sargs[1], ', '.join(dargs)))
                else:
                    cls, name, desc = tgt
                    STATS['indy'] += 1
                    TABLE.append((KINDS.get(kind,kind), cls, name, desc))
                    out.append(render(cls, name, kind, dargs))
                i = pend + 1
                continue
            except Exception as e:
                STATS['fail'] += 1
                STATS.setdefault('errors', []).append(str(e))
                out.append(src[i:i+16]); i += 16; continue
        if src.startswith(STR_FN + '(', i):
            j = i + len(STR_FN) + 1
            pend = find_paren_end(src, j - 1)
            args = [transform(a) for a in split_args(src[j:pend])]
            try:
                raw = try_const(args[0])
                if raw is None:
                    raise ValueError('non-constant encoded string: %s' % args[0][:60])
                key = parse_key(args[1])
                val = decode_stream(raw, key)
                STATS['str'] += 1
                out.append(json.dumps(val.decode('utf-8', 'replace')))
            except Exception:
                STATS['fail'] += 1
                out.append('ENC(%s)' % ','.join(args))
            i = pend + 1
            continue
        out.append(src[i]); i += 1
    return ''.join(out)

NOISE = re.compile(r'\n\s*if \(medal\.prawn\(\) != 0\) \{\s*\n\s*medal\.coyote\(\);\s*\n\s*\}\n')

def clean(src):
    prev = None
    while prev != src:
        prev = src
        src = NOISE.sub('\n', src)
    return src

if __name__ == '__main__':
    argv = sys.argv[1:]
    files = []
    for a in argv:
        if a.startswith('--indy='): INDY_FN = a[7:]
        elif a.startswith('--str='):  STR_FN  = a[6:]
        else: files.append(a)
    for path in files:
        src = open(path, encoding='utf-8').read()
        res = clean(transform(src))
        out = path.replace('.java', '.deobf.java')
        open(out, 'w', encoding='utf-8').write(res)
        print('wrote', out)
    print('stats', STATS)
    seen = {}
    for k, c, m, d in TABLE:
        seen[(k, c, m, d)] = seen.get((k, c, m, d), 0) + 1
    print('\n--- resolved call table (%d unique / %d total) ---' % (len(seen), sum(seen.values())))
    for (k, c, m, d), cnt in sorted(seen.items(), key=lambda x: (-x[1], x[0][1])):
        print('%-11s %-55s %-28s %-45s x%d' % (k, c, m, d, cnt))
