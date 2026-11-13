# nyx.bin — the privilege escalation helper

`nyx.bin` sits at the root of both big payloads (`back.jar` and the slot-7 RAT jar), identical in both:

```
size    211456
sha256  42fb35024d3324569c01f8a1c7dd9d7eb4eb0146116bc5cc60e20e45251bd631
```

It is not a PE as stored. The first bytes are `17 00 ca 5a 59 5a 5a 5a` and the file is mostly `0x5a` filler — XOR the whole blob with `0x5a` and it starts with `4d 5a 90 00` (`MZ`).

```bash
python3 -c 'd=open("nyx.bin","rb").read(); open("nyx_dec.bin","wb").write(bytes(b^0x5a for b in d))'
file nyx_dec.bin
# PE32+ executable for MS Windows 6.00 (console), x86-64, 7 sections
```

It is a standalone Windows privilege escalation tool. Its own step strings say what it does:

```
step 1: masquerade PEB / loader
step 2: elevation (task first, ladder only if the task is not armed)
step 3: getsystem (token theft)
step 4: opening console in the interactive session
```

and the token-theft path is visible in the imports and messages:

```
SeDebugPrivilege / SeAssignPrimaryTokenPrivilege
OpenProcess -> OpenProcessToken -> DuplicateTokenEx
CreateProcessWithTokenW / CreateProcessAsUserW / WTSQueryUserToken / AdjustTokenPrivileges
getsystem: spawned as SYSTEM from pid %lu (child exit code after wait: %lu, wf=%lu)
getsystem: no SYSTEM process found
getsystem: SeDebugPrivilege not available
RESULT: elevated instance spawned, waiting
RESULT: FAIL - not elevated, UAC bypass did not work
ADMIN RIGHTS: %s   --elevated   stage=elevated   RESULT: install/uninstall needs an elevated process (one time only)
```

Its embedded manifest requests `asInvoker`, so the elevation comes from the scheduled task the Java side creates, not from the binary's own manifest.

How the Java side uses it (`stage2/implant/aurora_prawn.deobf.java`):

* line 558 — `prawn.class.getResourceAsStream("/nyx.bin")`, written out to `%TEMP%\upd_<random hex>.exe`
* line 766 — builds `%WINDIR%\Temp\nyx_<random hex>.cmd`:

  ```bat
  @echo off
  echo 1> "C:\Windows\Temp\nyx.back.system"
  "%JAVA_HOME%\bin\java.exe" -jar "<path to back.jar>" <args>
  ```

* line 776 — registers it as a one-shot scheduled task named **`MicrosoftEdgeUpdateTaskMachineCore`**
  (`schtasks /create /tn MicrosoftEdgeUpdateTaskMachineCore /tr <cmd> /sc once /st 00:00`)
* lines 849-850 — reads back the markers `C:\Windows\Temp\nyx.back.system` and `nyx.back.high` to decide which integrity level it is currently running at

So the chain is: Java writes a .cmd wrapper -> scheduled task runs it elevated -> the helper escalates to SYSTEM by stealing a token -> the same JAR is relaunched with higher rights, and marker files in `C:\Windows\Temp` record what was achieved.
