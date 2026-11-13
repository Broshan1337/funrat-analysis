# Native components

No binaries here, only what I could read out of them statically.

## nyx.bin

See [nyx.md](nyx.md). Short version: a PE32+ x86-64 executable stored XORed with `0x5a`, and
what it does is privilege escalation (scheduled task, then `SeDebugPrivilege` token theft to SYSTEM).

## padlock.dll

```
size    23552
sha256  9880b3df045f7a4a6b8a8e57bdea196f642db11f9e4b27fee45d36968fa8b686
```

PE32+ DLL, imports only `KERNEL32`, `USER32`, `GDI32` and the MSVC runtime. It has a real export
table with 14 functions, and the names say everything:

```
PadlockStart        PadlockStartAll      PadlockStop       PadlockIsRunning
PadlockStartKeyboard  PadlockEnableKeyboard
PadlockStartMouse     PadlockEnableMouse
PadlockStartScreen    PadlockEnableScreen
PadlockGetOverlayWindow   PadlockGetTargets   PadlockSetTargets   PadlockSetDisplayPower
```

So this is not a crypto or anti-cheat helper, it is a **lock screen**. It blocks keyboard input,
blocks mouse input, and takes over the display (an overlay window it owns, plus turning the
display power on and off). `GetAsyncKeyState` is imported, which is how it watches the keys it is
blocking. The Java side binds it with JNA (`Native.load`) in `com/example/screen/PadlockNative.java`
and exposes `PadlockStart(targets)`, `PadlockSetTargets`, `PadlockStop`, `PadlockIsRunning`, so the
operator can lock a victim's machine remotely and pick which of keyboard / mouse / screen get locked.


## CookiveV2.dll

```
size    64512
sha256  99d6881553218afeef61ea5408c16598d699c958e36f56effc8827638c44d4eb
```

PE32+ DLL, imports `KERNEL32`, `SHELL32`, `ole32`, `OLEAUT32`, `MSVCP140` (so it is C++). Notable
imported functions: `CreateFileA`, `ReadFile`, `WriteFile`, `PeekNamedPipe`, `WaitNamedPipeA`,
`CreateThread`, `CoCreateInstance`, `GetModuleFileNameA`. Strings mention `Chrome`, `Google`,
`chrome`, `BraveSoftware`, `Microsoft`, `Browser`.

The interesting detail is that it has **no export table at all**. A DLL with no exports cannot be
called through `LoadLibrary` + `GetProcAddress`, which means it is not meant to be used that way.
It is a payload that gets injected. The Java code extracts it from the jar resources
(`aurora_prawn.deobf.java:6745`) and there is a helper whose error string is

```
LoadLibraryW failed in remote process, lastError=%d
```

i.e. the implant allocates memory in another process, writes the DLL there and gets it loaded
remotely. Once inside, the injected DLL talks back to the Java process over
`\\.\pipe\<name>` (`WaitNamedPipeA` / `PeekNamedPipe` / `ReadFile` / `WriteFile`).

Why inject into a browser instead of just copying the files: the cookie and login databases are
locked while the browser runs, and the encryption key in `Local State` is DPAPI protected. Being
inside the browser process solves both, and it also means the implant can read live sessions
rather than only what is on disk. It matches the other technique in the same binary, which
relaunches the browser with `--remote-debugging-port=9222` and reads it through the DevTools
endpoint on `http://localhost:9222/json`.

The name is a pun on Chrome's cookie encryption version (`v10`/`v20`) and cookie.
