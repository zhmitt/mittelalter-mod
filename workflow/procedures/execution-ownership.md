# Execution ownership

Read for native delegation, external-result continuation or Main workspace
reconciliation. This procedure supersedes conflicting earlier execution defaults
only, including mandatory visible-task/worktree setup. Safety, native runtime
controls, professional/legal/matter boundaries and preservation evidence remain
binding; development instructions do not authorize autonomous matter work.

## Native workers and integration

- Main SHALL default to native subagents for meaningful isolatable analysis,
  implementation, tests, independent review and CI diagnosis when parallelism,
  specialization or context isolation helps. Tiny coherent actions MAY stay local.
- Give each worker the outcome, acceptance, exact owned/excluded paths and mutable
  resources, baseline, scoped context, authority, stop condition and evidence format
  from [delegation.md](delegation.md). Avoid full-history forks without concrete need.
- Native workers require neither a visible task nor a separate worktree. Use a
  worktree only for concrete isolation needs; visible tasks require explicit user
  request and follow the runtime's lifecycle rules.
- Main owns integration order and the shared Git index. Workers edit disjoint owned
  files and return evidence; they do not stage or commit through a shared index.
  An explicitly assigned isolated index/worktree may have its own sole Git owner.
  Serialize overlapping files, shared test environments and external resources.
- Workers proceed within the existing user scope, inherited permission contract
  and runtime capability. Dispatch does not enlarge authority or create new gates;
  use [authority.md](authority.md) for preflight and concrete capability mismatches.

## External result to next action

- Before a wait, record one exact operation/run and resolved candidate (source
  commit plus relevant runtime/configuration identity), authoritative status check,
  continuation owner, success/failure/timeout states and an exact next action for
  each. Use [the compact handoff](../templates/external-result-handoff.md).
- Do not narrow an authorized delivery/repair task to "notify and stop" in the
  observer prompt. Carry forward its permitted next actions and remaining gates.
- Assign exactly one observer. Main consumes its results instead of duplicating
  polling. Set bounded polling cadence/backoff and a deadline; reuse an existing
  observer for that operation. Pending results stay quiet unless user input is due.
- If Main yields while the operation is pending, establish a supported durable
  wakeup tied to the owner and record its identity. An in-memory worker or polling
  script alone is not durable wakeup. If unavailable, record the exact capability
  blocker and preservation, use practical bounded foreground observation, and do
  not claim unattended continuation. Follow [preservation.md](preservation.md)
  when the wait requires durable resume state or releases a writing claim.
- On terminal success or failure, the owner SHALL start or dispatch the authorized
  next action and record the actual action/worker identity, or name the exact real
  blocker, responsible human/capability and resume trigger. A result report,
  recommendation or notification alone does not discharge continuation ownership.
- On timeout, perform the recorded authorized status/diagnostic action; preserve
  the still-pending operation and name its owner and next observation or blocker.
  Never restart, cancel or replace an operation merely to simplify observation.
- CI failure does not end an authorized repair task: diagnose, repair locally and
  run affected checks within its original scope. A push restriction gates push,
  not that local continuation. Ask only at a real remaining authorization or
  capability boundary; retain the original outcome while awaiting it.
- Disable an obsolete/terminal observer only after transferring continuation to
  a started action, acknowledged handoff (actual accepting worker/event ID), or
  concrete blocker. Naming an owner without dispatch is not a transfer. Shutdown ends
  observation, never Main's delivery responsibility. Do not claim completion from
  monitor termination or merely from dispatch; acceptance evidence still governs.

## Stable Main and bounded reconciliation

- Main's default integration anchor is the primary checkout on `main`. An explicit
  temporary isolation lane records its owner, purpose and safe return action;
  never switch a live task's branch/worktree to manufacture that default.
- Before reconciliation, inspect actual command cwd, repository root, branch/HEAD,
  worktree ownership, index and dirty paths, unique commits, and the governance
  commit/HEAD whose instructions the runtime loaded (including known local edits).
  UI project/task metadata does not prove cwd, branch or loaded instruction state.
  If the runtime baseline is unknown, record it as unknown; obtain fresh applicable
  instructions before dependent work without inventing an attestation.
- Compare the actual lane to the current primary governance baseline. Preserve
  staged and dirty contents with exact ownership and references, and secure unique
  commits before handoff or cleanup using [preservation.md](preservation.md).
  An update message does not itself refresh a running session's loaded rules.
- A safe local fast-forward is permitted only with existing integration authority,
  a clean exact target, verified ancestry and no conflicting owner. This grants no
  push authority. Divergent commits require scoped reconciliation of owned work
  under existing authority; never blanket-merge unrelated branches or push them.
- Cleanup requires existing cleanup authority covering the resolved exact targets
  (a bounded cleanup request suffices, not a new approval per branch), identified owners,
  preserved unique commits/index/dirty work, no live task owner or diagnostic need,
  and proof that removal loses nothing. Delete only those proven-safe targets;
  never force-remove, reset, clean, rebind a live task or overwrite foreign work.
- Ambiguous ownership blocks the unsafe integration/cleanup action only. Continue
  independent authorized delivery and assign the unresolved target to its owner.
  Report assignment, preservation, integration and actual cleanup separately;
  do not call an assigned cleanup complete before its result is verified.
