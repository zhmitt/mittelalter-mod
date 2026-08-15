# AGENTS.md - Hybrid Workflow Contract

This repository is the Mittelalter-Mod, a Minecraft Java Edition content mod built on NeoForge. It uses one development workflow across Codex, Claude Code, and Gemini CLI so multiple contributors stay consistent regardless of which tool they use.

This workflow is inherited from the `app.dev-template` baseline. Shared workflow policy changes should generally be proposed upstream first; this file may add product-specific constraints for the mod itself.

## Canonical sources

The only canonical sources of process truth are:

1. `AGENTS.md`
2. `openspec/`
3. `workflow/`

The following are adapters only and must never become the only place where a rule exists:

- `.claude/`
- `.gemini/`
- `.codex/`
- `CLAUDE.md`
- `GEMINI.md`

## Core rules

1. Non-trivial changes are spec-first.
2. Current system behavior lives in `openspec/specs/`.
3. Proposed work lives in `openspec/changes/<change-id>/`.
4. Operational state lives in `workflow/state/`.
5. Deterministic checks live in `workflow/scripts/`.
6. Do not treat tool-specific files as canonical governance.

## Spec-first policy

Skip formal change scaffolding only for:

- typo-only changes
- comment-only changes
- tiny bug fixes that restore intended behavior without changing system design

Everything else (new items, blocks, mobs, mechanics, world-gen, etc.) should create or update an OpenSpec change.

## Delegation policy

Main agents should keep orchestration context clean and actively evaluate delegation for every non-trivial block.

- Use specialized agents or subagents for implementation, review, testing, verification, documentation, and bounded diagnosis when the task can be isolated cleanly.
- In Codex, prefer `explorer` agents for read-only codebase research, root-cause analysis, log/doc inspection, and spec reconciliation.
- In Codex, prefer `worker` agents for bounded implementation, targeted test runs, verification, and documentation updates with explicit ownership and disjoint write sets.
- Run agents in parallel when the next local step is not blocked and the scopes do not overlap; keep urgent blocking work local.
- The main agent remains the tech lead/orchestration layer and owns architecture decisions, canonical workflow updates, and final integration unless explicitly delegated.

All workers must:

1. read canonical artifacts first
2. treat legacy layers as reference only
3. report back into canonical artifacts or concise summaries

## Workspace ownership and branch handoff

Every active line of work must use one explicit workspace mode at a time:

- `tool-managed workspace`
  - The chat or app owns the branch/worktree lifecycle.
  - Hidden tool worktrees or detached thread state may exist.
  - Prefer this mode when an existing chat should continue.
- `manual git worktree`
  - A human or script owns `git worktree add` and the branch checkout.
  - Open a new chat in that exact folder instead of rebinding an older chat that still points somewhere else.
- `primary workspace`
  - The top-level checkout is the default stable repo entrypoint.
  - Do not treat it as a surprise branch-rebind target when other workspace modes are active.

Rules:

1. One branch, one ownership mode at a time.
2. If an existing chat should continue, let the tool manage that branch and do not create a parallel manual worktree for it.
3. If a manual git worktree is created, continue the work in a new chat opened in that exact workspace path.
4. Before freeing a branch from a manual worktree back to a tool-managed workspace, create a checkpoint commit or an explicit stash first. Prefer a pushed checkpoint when the work is important or the app behavior is uncertain.
5. Handoffs between tools or workspaces must record the change id when present, branch name, commit hash or stash reference, current workspace mode, and intended target workspace or tool.
6. Run `workflow/scripts/workspace-status.sh` before branch/worktree handoffs when there is any doubt about current ownership.

## Canonical workflow surface

Use these scripts as the operational API:

```bash
workflow/scripts/phase-status.sh
workflow/scripts/tasks-sync.sh
workflow/scripts/tasks-sync.sh --check
workflow/scripts/milestone-sync.sh --summary "..."
workflow/scripts/milestone-check.sh --staged --mode warn
workflow/scripts/post-impl-prepare.sh --summary "..."
workflow/scripts/post-impl-check.sh
workflow/scripts/session-close.sh --summary "..."
workflow/scripts/worktree-doctor.sh
workflow/scripts/worktree-doctor.sh --branch <branch>
workflow/scripts/worktree-close.sh --summary "..."
workflow/scripts/worktree-close.sh --summary "..." --remove --change <change-id>
```

## Canonical evidence requirements

Before a change is considered ready to archive:

- all tasks in `tasks.md` are complete
- a `verification.md` note exists in the change folder
- `workflow/state/status.md` contains an entry for the change
- a report exists in `workflow/state/reports/`

## Tool adapter policy

### Claude Code

- Slash commands, subagents, and hooks are allowed.
- They are accelerators only.
- They must delegate to `openspec/` and `workflow/scripts/`.

### Gemini CLI

- Commands, skills, and subagents are allowed.
- Workspace-level Gemini subagents may be enabled in `.gemini/settings.json`.
- They must mirror the same workflow surface.
- They must not define exclusive process rules.

### Codex

- Repo-local skills are the primary Codex integration.
- Codex app multi-agent workflows should be used for non-trivial work when the task can be isolated cleanly.
- Prefer `explorer` agents for read-only investigation, spec/doc/codebase search, and root-cause analysis.
- Prefer `worker` agents for bounded implementation, targeted tests, verification, and documentation updates with explicit ownership and disjoint write sets.
- Optional global prompts are convenience only.
- Global prompts must be generated from versioned repo sources.

## Definition of Done

**Done = `workflow/scripts/change-done.sh --change <id>` exit 0. Nichts anderes
zaehlt als Done.**

`./gradlew build` + `./gradlew test` ist ausschliesslich Beweis fuer
"kompiliert + bestehende Tests gruen" -- kein Beweis fuer "Spec erfuellt"
oder "Implementation korrekt".

### Beweis-Typen pro Change-Typ

| Change-Typ | Pflicht-Beweis |
|---|---|
| **Bug-Fix** | Failing-Test (vor Fix) -> Passing-Test (nach Fix). Test-Name muss in Commit-Message oder Report stehen. |
| **Feature** | Smoke-Test (`./gradlew runClient`/`runData` starten, Item/Block im Spiel zeigen, Test-Output zeigen) + Test-Datei oder Recording. |
| **Refactor** | Vor/Nach-Inventur identisch. Public-API (Registries, Events, Capabilities) gleich. Test-Snapshot-Diff = leer. |
| **Migration** | Sample-Vergleich alt vs neu (z. B. Datapack/Recipe-Vergleich). Beweis: Diff-Output. |
| **Security-Hardening** | Penetrations-Versuch (curl/script gegen Server-Endpunkt) muss failen. Beweis: failing-Output protokolliert. |
| **Performance-Optimierung** | Vor/Nach-Messung (z. B. TPS/MSPT) mit definiertem Threshold. Beweis: Zahlen vor/nach. |
| **Doc-Update** | Cross-Reference-Check (Doc claimt X exists -> grep findet X). Beweis: grep-Output. |
| **Spec/Workflow-Change** | Selbst-Anwendbarkeit (das neue Workflow auf einen Sample-Change angewendet -> gruen). |

Verification-Pipeline: `workflow/scripts/change-done.sh`

### Historischer Cutoff

Die `change-done.sh`-Pflicht gilt ab dem ersten Change dieses Repositories
(`bootstrap-mittelalter-mod`). Kein `done`-Claim ohne
`change-done.sh --change <id>` exit 0.

## Parallel Work Limit

**Max 3 unverifizierte Claims gleichzeitig in Flight.**

"Unverifiziert" bedeutet: noch kein `workflow/scripts/change-done.sh --change
<id>` exit 0.

Vor dem 4. parallelen Claim muessen fuer die ersten 3 `change-done.sh`-Gates
abgeschlossen sein.

**Dokumentierter Failure-Mode:** Audit-Sweep mit vielen parallelen Changes
ohne Pro-Change-Gate fuehrt zu falsch-als-done gemeldeten Implementations.
Drift wird erst spaeter entdeckt. Beispiele: Util geschrieben aber nicht
importiert; Registry-Eintrag erstellt aber nicht registriert; Loot-Table
geaendert aber nicht referenziert; re-exports vergessen. Typecheck + Tests
blieben durchgehend gruen.

Diese Regel gilt unabhaengig vom Tool-Author -- Claude, Codex, Gemini. Die
Volume-Falle ist tool-unabhaengig.

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

## Sub-agent failsafe

### Hook-Failsafe-System

Der Sub-Agent- und Workflow-Einsatz wird durch ein dreistufiges
Hook-System hart erzwungen (Implementierung: `.claude/hooks/` und
`workflow/scripts/agent-evidence-check.sh`):

1. **Gate 1 — PreToolUse-Block auf Edit/Write/MultiEdit/NotebookEdit**
   (`.claude/hooks/gate-edit.sh`). Blockiert Edits an Code-Dateien
   (u. a. `.java/.py/.sh/.rs/.go/…`), wenn in den letzten 4 h kein
   `Plan`- oder `Explore`-Sub-Agent gelaufen ist. Whitelist (keine
   Sperre): `*.md`, `*.json/yml/yaml/toml`, `openspec/`, `workflow/`,
   `docs/`, `.claude/`, `.git-hooks/`, `.workflow-evidence/`, Dotfiles.
2. **Gate 2 — PreToolUse-Block auf Bash**
   (`.claude/hooks/gate-bash.sh`). Blockiert `git commit --no-verify`,
   `git commit -n`, `git push --no-verify`, `git -c core.hooksPath=…`
   und `--no-gpg-sign`.
3. **Gate 3 — Pre-Commit-Advisory**
   (`workflow/scripts/agent-evidence-check.sh`). Warnt (nicht-blockierend)
   wenn beim Commit von Code-Dateien kein `test-runner`-Sub-Agent in den
   letzten 30 min lief, und zeigt `CLAUDE_HOOKS_OFF`-Bypasses der
   letzten 24 h.

**Telemetrie**: Jeder Sub-Agent-Spawn wird per PostToolUse-Hook
(`.claude/hooks/log-agent.sh`) als JSONL nach
`.workflow-evidence/agents.jsonl` geloggt. Bypasses landen in
`.workflow-evidence/overrides.jsonl`. Beide Logs sind `.gitignore`d.

**Override**: `CLAUDE_HOOKS_OFF=1 <command>` deaktiviert Gate 1 + Gate 2
für genau einen Aufruf, schreibt aber einen Bypass-Record. Nur mit
expliziter User-Autorisierung verwenden. Bei fehlschlagendem Hook erst
die Root-Cause beheben, nicht bypassen.
+
## Shared focus and orchestration baseline

For every non-trivial implementation, declare one observable outcome, at most
three acceptance criteria, non-goals, owned files/components, and a stop
condition. Prefer the smallest vertical slice that satisfies acceptance. Process
is proportional to risk, not line count.

Unplanned findings default to follow-up. Only work required for acceptance or a
credible security, privacy, data-integrity, payment, legal, or production risk
may trigger a scope checkpoint. Before adding an abstraction, require two
current consumers or immediate net complexity removal. Stop after two failed
approaches, three commits without acceptance progress, or two unplanned fixes
and re-scope instead of building more diagnostic infrastructure.

The primary session is the orchestrator/integration owner: it owns priorities,
shared-contract decisions, canonical handoff state, merge order, deployment,
and closure. Durable change tasks own one branch/worktree; short-lived subagents
own bounded slices. Parallel writing requires disjoint ownership, explicit
contracts/dependencies, external-resource isolation, verification capacity, and
integration order. Ambiguity defaults to sequential execution. Default capacity
is three independent implementation slices and two completed-but-not-integrated
branches; read-only and parked work do not consume implementation capacity.

Workers return `workflow/templates/worker-handoff.md` with at most three
out-of-scope findings. `workflow/scripts/finding-route.sh` only advises routing;
it never authorizes or creates implementation work.
+
## Proactive orchestration v3

Scheduling is based on active mutable ownership, not unfinished-change count.
Use one state per change: `active_write`, `external_wait`,
`user_action_pending`, `parked`, `ready_for_verify`,
`ready_for_archive`, or `done`. Only `active_write` consumes a writing
lane. Where older text limits "unverified claims", this section supersedes
that scheduling rule; the repository's canonical completion gate still remains
the only definition of Done.

Release a writing lane only through a Claim Release checkpoint recording the
baseline and preservation reference, completed work, open gates/risks, exact
resume step, released files/resources, absence of writing workers, and
Integration Owner confirmation. Claim Release never implies completion.

Within the accepted outcome and ownership, execute safe routine actions without
a conversational micro-approval: read-only status/auth checks, required
interactive sign-in initiation, local tests/builds, scoped worktree/worker setup,
focused commits, evidence synchronization, small integration fixes, and
external-wait monitor creation. Starting sign-in does not authorize credentials,
MFA, new consent, or ambiguous account/tenant choices.

Still require explicit authorization for production mutation/deployment,
IAM/secret/permission changes, new or unbounded paid work, public communication,
irreversible deletion, material product decisions, and push/merge unless already
granted for the session. Native sandbox, connector, and approval boundaries and
stricter repository-specific rules always prevail.

The Main/Integration Owner may perform small conflict resolution,
state/evidence synchronization, focused governance and integration patches, Git
operations, and gates directly when delegation would add more coordination than
risk reduction. Independent product slices and broad writes remain worker work.
Verification is delta- and risk-based: reuse valid evidence and rerun what
changed; reserve Full Evidence for high-risk or difficult-to-reverse work.

Before stopping because progress cannot continue, execute any already-authorized
recovery action. Otherwise return:

```text
Recovery Contract
State: <external_wait|user_action_pending|parked>
Blocker: <exact blocker>
Preserved: <commit/stash/artifact and completed work>
Recommendation: <default next path>
Alternatives: <zero to two bounded alternatives>
Authorized next action: <action executable without another decision, or none>
Resume trigger: <observable event/owner>
Next command or action: <exact continuation>
```

A global Codex Stop hook may request one continuation pass when this contract is
missing; `stop_hook_active` prevents loops. The hook is a recovery failsafe,
not a scheduler or permission bypass.

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

## Approval-churn prevention

Before dispatch, the Main/Integration Owner performs a **capability preflight**:
resolve the worker's required filesystem, process, network, connector, remote
runtime and external-write capabilities; choose a runtime that already supports
the authorized work; pre-position exact commits, mirrors or non-secret artifacts
when transfer would otherwise cause repeated elevation; and record an
**approval budget**. The default budget is zero conversational approvals and at
most one consolidated native platform approval for a known, bounded capability
gap.

A worker must not discover predictable permissions one command at a time. At
startup it validates the authority contract and required capabilities
read-only. If a required capability is absent, it reports one consolidated
mismatch containing the blocked capability, resolved target/effect, why the
current runtime cannot proceed, the narrowest safe grant or suitable runtime,
and preserved state. It must not issue serial approval prompts or try command
variants that cross the same boundary.

Prefer designs that eliminate privileged observation: repository-owned
instrumentation, process-local lifecycle evidence, deterministic harnesses, and
pre-provisioned verification runtimes are preferred over OS-wide process
inspection or ad-hoc observer scripts. After one native permission mismatch or
one approval-gated method proves operationally unsuitable, park that method;
resume only with an already-authorized capability, a redesigned bounded method,
or a newly provisioned runtime. Approval denial never authorizes retries,
fallback mutation, broader inspection, or scope expansion.

When the user or parent explicitly authorizes uninterrupted execution until a
real blocker, workers treat every in-scope action inside the effective authority
intersection and approval budget as authorized. They interrupt only for an
explicit user gate, an unavailable required native capability after the
consolidated preflight, or a material ambiguity in target or effect.

## External wait continuity

When the primary orchestrator cannot make useful progress because an already
running external operation is pending (for example CI, a remote test, build,
deploy, import, provider job, or approval), it must establish continuation
before yielding:

- record the exact operation identifier, authoritative read-only status check,
  owner, polling cadence/backoff, timeout, terminal success and failure states,
  and the next action for each terminal state;
- create exactly one thread-bound wake-up monitor with the runtime's supported
  automation or recurring follow-up mechanism; while pending it stays quiet, on
  a terminal result it wakes the owning session, reports evidence, continues the
  recorded next action when authorized, and then disables itself;
- reuse an existing monitor for the same operation instead of creating duplicate
  polling, CI runs, deployments, or notifications;
- never restart, cancel, replace, deploy, merge, or otherwise mutate the external
  operation merely to make monitoring easier unless that mutation is separately
  authorized;
- cancel or disable the monitor when the operation becomes terminal, obsolete,
  superseded, manually stopped, or the owning task closes.

A repository polling script alone is not a wake-up guarantee. If the active
runtime cannot create a durable wake-up monitor, state that limitation, preserve
the continuation contract in the handoff, and use bounded foreground polling
only when practical. Never claim autonomous continuation unless the wake-up
path was actually created and its ownership is known.

## Completed visible Codex task lifecycle

Create visible change tasks from a same-directory fork that returns a real
`threadId`; immediately assign a unique title, hand it into a tool-managed
worktree, and treat the successful handoff's `destinationThreadId` as canonical.
Rename the destination task before sending work. Never treat setup state or a
`clientThreadId` as a completed handoff. Use
`<PROJECT> · Change <change-id> · <short unique purpose>`, never generic titles.

Archive exactly that destination task only after its final output, acceptance
and tests, checkpoint commit or safe preservation, integration/preservation,
absence of blockers, and unique change/worker mapping are confirmed. Any
missing gate or client-only identity is `blocked-manual-cleanup`. Subagents are
not visible tasks and never trigger archival or invented thread ids.

Task archival and worktree release are separate. Release also requires exact
ownership, cleanliness, secured checkpoint, no diagnostic need, and no active
task owner. Never automatically delete branches or remove dirty, orphan,
ambiguous, or diagnostically required worktrees. If app archival removes a
worktree, verify with `git worktree list` that only the expected one disappeared.
The pure `workflow/scripts/completed-task-cleanup-status.sh` checks eligibility
but calls no Codex API and mutates no task, branch, or worktree.
