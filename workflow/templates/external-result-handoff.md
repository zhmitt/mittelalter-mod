# External result handoff

Outcome / existing authority: <original outcome and authorized continuation scope>
Continuation owner: <Main/task identity and actual workspace>
Operation / candidate: <exact run ID; source SHA and relevant runtime/config identity>
Status check: <authoritative read-only command or API target>
Observer: <one worker/monitor ID; reuse it, no duplicate Main polling>
Polling / deadline: <bounded cadence, backoff cap, timeout timestamp>
Pending behavior: <quiet observation; genuine human-input trigger if any>
Durable wakeup: <supported monitor ID and owner if Main yields; or exact limitation>
Terminal success: <exact state; authorized next command/action and owner>
Terminal failure: <exact state; authorized diagnosis/repair/checks and owner>
Timeout: <authorized diagnostic action; continued observation owner or real blocker>
Preserved: <commit/artifact references; dirty/index ownership; resume context>
Result / evidence: <candidate-matched state, check timestamp and evidence reference>
Continuation started: <actual action/worker ID and scope; never just a recommendation>
Real blocker if not started: <exact human/capability gate, owner and resume trigger>
Observer shutdown: <when transferred; observer ends, delivery responsibility continues>
Restrictions: <remaining gates; no-push does not prohibit authorized local repair>

On terminal result, start/dispatch the recorded authorized action or name the exact
real blocker before yielding. Do not claim delivery complete from observer shutdown.
Use [execution ownership](../procedures/execution-ownership.md) and
[preservation](../procedures/preservation.md) for binding continuation/resume rules.
