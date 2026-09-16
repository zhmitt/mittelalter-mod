# AGENTS.md - Hybrid Workflow Contract

This repository is the Mittelalter-Mod, a Minecraft Java Edition content mod built on NeoForge. It uses one development workflow across Codex, Claude Code, and Gemini CLI so multiple contributors stay consistent regardless of which tool they use.

This workflow is inherited from the `app.dev-template` baseline. Shared workflow policy changes should generally be proposed upstream first; this file may add product-specific constraints for the mod itself.

# Shared delivery core

Canonical process lives in AGENTS.md, openspec/ and workflow/. Tool adapters
only reference it. Local product, legal and opt-in rules below remain binding.

## Start and deliver

- Read this core, the current NEXT-SESSION and selected plan once. Query historical
  status only for a specific unresolved question; reread changed dependencies, not
  all context before each mutation. Check branch/HEAD and dirty ownership first.
- Non-trivial development uses a compact OpenSpec change unless the repository
  explicitly uses a native/opt-in plan. Typos, comments and tiny restorative fixes
  need no scaffolding. One outcome, at most three acceptance criteria, non-goals,
  short tasks, decisions and next action suffice; no new platform by default.
- Main owns outcome and integration. Use native subagents by default for meaningful
  isolated analysis, implementation, tests and review when elapsed time, expertise
  or context isolation benefits. Tiny coherent actions may stay local. A worker
  does not require a visible task or separate worktree; use isolation only as needed.
  Implement the smallest slice, check it and commit a breadcrumb; stop at acceptance.
- Keep Main anchored at the primary main checkout; verify actual cwd, HEAD and
  instruction baseline, not just the app's project label. Record temporary lanes.
- A terminal worker/monitor result is a continuation trigger, not task completion.
  Start or dispatch the authorized next action; report a real blocker otherwise.
  Use one quiet observer with bounded backoff/timeout, never duplicate Main polling.
- New findings default to non-blocking follow-up, not automatic tasks. Scope resets
  replace the old critical path. After two failed approaches, two unplanned fixes
  or three commits without acceptance progress, reassess before adding machinery.
- Record each decision/result once in the owning change; other views link it.
  Reuse user decisions and evidence until an attested dependency changes. Missing
  metadata blocks only its dependent action, not independent delivery.

## Context and output discipline

- Search narrowly with rg; select relevant files, lines and fields before emitting
  tool results. Start with a concise result and expand only a concrete failure.
  Do not print whole logs, JSON histories or directories to find one fact.
- Store large diagnostic output in an owned local artifact and return its path,
  exit status and relevant excerpt. Do not hide failures or truncate mandatory
  instructions: read the complete triggered procedure, not unrelated procedures.
- Give workers outcome, owned paths, baseline, needed facts, authority and stop
  condition. Start with scoped context rather than a full conversation fork unless
  the history is genuinely needed. Return a concise diff/evidence result; Main
  should not repeat the same investigation without a concrete discrepancy.
- Keep the current handoff short: outcome, actual preservation/commit reference,
  blocker and next action. Verify commit facts with Git; do not carry stale
  "uncommitted" claims across closure. History stays on disk, not in every prompt.

## Safety, ownership and authority

- Authority is the intersection of user scope, repository rules and actual runtime
  capability. Native controls cannot be bypassed by instructions or worker handoffs.
- Production, IAM/secrets/permissions, real payments, new/unbounded cost, public
  communication, irreversible deletion, material scope expansion and push/merge
  require explicit authority. A bounded non-production envelope authorizes only
  its named targets/operation chain, never these exclusions automatically.
- Inspect exact targets; preserve unrelated dirty files and staged work. Stage
  exact paths/hunks only; no broad add, reset, clean or hook bypass. Do not rebind
  branches, delete worktrees or archive tasks without exact ownership/preservation.
- Parallel writers need disjoint files, contracts and mutable external resources.
  Only active_write consumes a writing lane; parked/wait/verification queues do not.
- Run already-authorized recovery before asking. Ask only for a real human decision
  or required unavailable capability, not derivable facts or repeated approvals.
  A denial is not permission to retry across the same boundary by another method.

## Evidence and completion

- Risk belongs to the changed slice: isolated reversible work gets a focused
  check; normal changes get acceptance evidence; concrete security/privacy/payment/
  legal/data-integrity/Production risks retain relevant independent review and
  environment evidence. Broad product labels alone do not trigger full process.
- Tests prove only what they exercise. For provider journeys, record one resolved
  source/runtime/frontend/configuration candidate and tested positive/negative
  outcomes. Provider acceptance alone does not prove usable customer capability.
  Repeat affected steps only; reuse valid evidence for unchanged dependencies.
- Ordinary breadcrumbs use Change-Id and focused Test where applicable, without
  synchronized status/report/registry/Handoff bundles. Preparation is not verification.
  At final closure run change-done once; do not manually repeat its constituent
  checks. Final Git validation remains a separate check of commit intent/content.
- Where the native OpenSpec runtime applies, only
  `workflow/scripts/change-done.sh --change <id>` exit 0 establishes Done.
  Workflow-Complete requests commit evidence checks, not Done. Checkpoints preserve
  incomplete work and never imply completion. Local opt-in exceptions remain intact.
- Durable Claim Release is only for workspace handoff, external wait needing resume
  state, writing-claim release or material rescope. It still needs the exact trailer
  and atomic staged evidence defined in the preservation procedure.

## Conditional procedures — load only when triggered

The files below contain binding details for their named action, not startup reading.
Read the applicable procedure completely before taking that action.

| Trigger | Procedure |
| --- | --- |
| Main startup, worker selection, external-result continuation or workspace reconciliation | workflow/procedures/execution-ownership.md |
| Delegation or worker evidence | workflow/procedures/delegation.md |
| Worker dispatch, blocked-stop recovery, external action, authority gap, pending operation | workflow/procedures/authority.md |
| Workspace handoff, Claim Release, task/worktree cleanup | workflow/procedures/preservation.md |
| Final verification, archive or hook maintenance | workflow/procedures/verification.md |
| Material rescope, supporting-work escalation, approval/publication/release/migration gate or journey evidence | workflow/procedures/scope.md |
| Tool adapter or starter customization | workflow/procedures/adapters.md |

For ordinary work no extension is needed. Only use procedures present in this
repository; its native instructions win over examples from other repository types.
The execution-ownership defaults supersede older execution-shape preferences,
not repository-specific safety, professional judgment or native mode boundaries.

<!-- ## Proactive orchestration v3: recovery contract is in authority.md. -->

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

## Checkpoint commits

An incomplete active change SHALL create an explicit checkpoint commit after a
meaningful verified slice, before a workspace or branch handoff, an
already-authorized external wait or non-production deploy, a writing-claim
release, or a material rescope. This is not a cadence: time, line count, and
commit count never create a checkpoint requirement by themselves.

The commit message MUST contain `Workflow-Checkpoint: <change-id>`. Its staged
index MUST atomically contain the affected `tasks.md`, checkpoint
`verification.md`, matching `checkpointed` status entry, report,
`NEXT-SESSION.md` resume step, and fresh task registry. The evidence records
completed work, focused checks, open gates, and the exact next step. A
checkpoint preserves incomplete work; it never establishes Done. Only
`workflow/scripts/change-done.sh --change <id>` may do that. Proposal-only
documentation commits remain subject to normal registry and spec-drift checks.

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
## Lean delivery authority

This section supersedes older conflicting delegation, checkpoint, and
unfinished-claim scheduling wording. The observable outcome outranks procedural
completeness. Keep each OpenSpec change as compact working memory: one outcome,
no more than three acceptance criteria, explicit non-goals, a short task list,
recorded decisions, and one next action. Derived reports, registries, adapter
metadata, or formatting do not become acceptance criteria merely by existing.

An explicit user decision inside the declared outcome and scope is operative and
reusable for dependent reversible work. Reopen only the affected decision when
an attested dependency changes, scope is exceeded, or genuine human-only
judgment remains. Missing metadata blocks only the control that depends on it;
Production-only metadata does not block unrelated implementation or
non-production verification.

Classify risk and evidence on the changed vertical slice. Small, isolated,
reversible work gets one targeted check; standard behavior changes get OpenSpec,
relevant acceptance evidence, and one breadcrumb; concrete security, privacy,
payment, legal, data-integrity, safety, difficult-rollback, or Production risk
gets independent review and environment-specific evidence. Procedures block
only with a causal relation to acceptance or one of those gates.

The Main Session may implement, test, and integrate a bounded slice directly.
Delegate only when useful parallel ownership, specialized capability, or
genuinely independent judgment reduces elapsed time or closes a concrete risk.
Do not fan implementation, tests, evidence, and review into separate workers by
default. A scope reset replaces the superseded critical path; it does not append
another active scope.

Use ordinary coherent progress commits with exact trailers
`Change-Id: <change-id>` and `Test: <focused check> exit 0`. Reserve
`Workflow-Checkpoint: <change-id>` and the full Claim Release evidence bundle
for a workspace/branch handoff, external wait needing durable resume state,
writing-claim release, or material rescope. Checkpoints preserve incomplete work
and never establish Done; only
`workflow/scripts/change-done.sh --change <id>` exit 0 does.

Use exactly one orchestration state per change: `active_write`,
`external_wait`, `user_action_pending`, `parked`, `ready_for_verify`,
`ready_for_archive`, or `done`. Only `active_write` consumes writing
capacity. Admit work by disjoint mutable ownership, dependency order, external
resources, verification capacity, and integration capacity—not by counting all
unfinished changes.

A bounded worker handoff needs only change id, outcome, up to three acceptance
criteria, owned and excluded scope, baseline/workspace, operative decisions,
completed work plus focused evidence, open gate, and next action/stop condition.
Add capability, environment, inherited-authority, Claim Release, visible-task,
outcome-guard, or material-finding extensions only when their named trigger
applies.

Concrete Production, IAM, secret, permission, payment, legal, privacy, safety,
irreversible-action, new-cost, public-communication, and material-scope gates
remain fail-closed. Repository product constraints and native runtime boundaries
remain authoritative.
