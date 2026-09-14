# Delegation procedure

Read only when the core names this trigger. Retained repository-specific details.

## Orchestrator and delegation

The Main Session is the Tech Lead and Integration Owner. It owns outcome, scope,
architecture, ownership, sequencing, integration and closure. It MAY implement,
test, and integrate a bounded slice directly when local execution is the fastest
coherent path.

Delegate when it reduces elapsed delivery time through useful parallel
ownership or specialized capability, or when genuinely independent judgment is
required by a concrete risk or acceptance criterion. Delegation is not a
ceremony for every non-trivial task, and implementation, test, evidence, and
review SHALL NOT fan out into separate workers without one of those benefits.
This repository instruction is standing authorization to spawn scoped workers
when those conditions apply. The user may disable delegation explicitly.

Parallelize only disjoint ownership; otherwise integrate sequentially. Native
runtime limits and genuine user gates—Production, IAM/secrets, irreversible
actions, new cost, public communication and material scope expansion—still
apply.

Every worker receives outcome, acceptance criteria, owned and excluded paths,
baseline, dependencies, authority, stop condition and required evidence. It
reads canonical artifacts first, never widens scope silently, and returns a
focused checkpoint plus concise evidence. Parallel writes require disjoint
files, contracts and mutable resources; the Main Session owns integration order.

## Sub-Agent Output Format

Jede Sub-Agent-Antwort, die Claim-Verben nutzt ("implemented", "fixed",
"done", "complete", "tested", "deployed", "verified", "ready"), **muss** einen
Evidence-Block am Ende der Antwort enthalten:

```
Implemented:
- <file:lines> -- <was konkret>
Skipped/Out-of-Scope:
- <reason>
Verified:
- <command> exit <code>
- <test-name>: result
NOT verified:
- <was wurde NICHT geprueft, woran koennte gelogen worden sein>
Drift-risk:
- <Util geschrieben aber nicht importiert? Test geschrieben aber nicht
   ausgefuehrt? Refactor angekuendigt aber Caller nicht migriert?>
```

Tech-Lead-Regel: Sub-Agent-Antwort ohne diesen Block = nicht akzeptiert.

## Inherited worker authority

Every worker handoff must carry an **Inherited Authority Contract** with these
fields: `Authority source`, `Parent permission profile`, `Inherited safe
actions`, `Repository/worktree scope`, `External systems allowed`, `Explicit
user gates`, `Forbidden actions`, and `Escalation condition`.

Effective worker authority is the intersection of the parent session's actual
runtime authority, the declared worker scope, and repository safety policy. A
worker never gains authority the parent does not have. Within that intersection,
the worker proceeds without micro-approval for in-scope, reversible work:
reading and editing owned files, tests/lint/builds, temporary test artifacts,
read-only Git and external checks, targeted staging/commits, already-authorized
SSH/CI/container access, and monitoring an already-authorized operation.

Escalation is impact-based, not command-name-based. A command such as `ssh`,
`docker`, or `rm` is not by itself a user gate; evaluate its resolved target,
side effects, reversibility, data sensitivity, environment, and declared scope.
Workers must not request elevated execution pre-emptively when the action works
inside their effective profile. If the runtime cannot inherit the parent
profile, record the mismatch once and return the narrowest actionable approval
request instead of serial command-by-command prompts.

Production mutation, push/merge/release without prior authorization,
IAM/permission/secret changes, new or unbounded cost, public communication,
irreversible or broad deletion, scope expansion, and secret disclosure remain
explicit user gates. Repository governance can narrow native authority but can
never bypass platform sandboxing or safety controls.
