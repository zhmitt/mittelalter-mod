# Workflow Governance

## Requirements

### Requirement: Canonical workflow contract
The repository SHALL define its workflow contract in `AGENTS.md`, `openspec/`, and `workflow/`.

#### Scenario: Adapter file exists
- **WHEN** a tool-specific file such as `CLAUDE.md`, `GEMINI.md`, `.claude/`, `.gemini/`, or `.codex/` is present
- **THEN** it SHALL behave as an adapter to the canonical layers
- **AND** it SHALL NOT be the only source of a mandatory workflow rule

### Requirement: Spec-first for non-trivial work
The repository SHALL require an OpenSpec change for non-trivial work.

#### Scenario: Non-trivial change is proposed
- **WHEN** a change introduces new behavior, design changes, or cross-cutting updates
- **THEN** it SHALL create or update an OpenSpec change before implementation begins

#### Scenario: Trivial maintenance is proposed
- **WHEN** a change is a typo-only fix, comment-only fix, or tiny bug restoration
- **THEN** the repository MAY skip formal change scaffolding

### Requirement: Bounded non-production chains use environment-scoped authority
The workflow SHALL allow one environment-scoped authorization envelope for a
bounded Sandbox, Dev or Staging operation chain. The envelope SHALL name the
authoritative target identity, allowed chain, candidate-resolution rule,
test-data bound, invariants, exclusions, and stop condition. A source branch or
provider UI label alone SHALL NOT identify the target environment. An envelope
SHALL NOT authorize Production, IAM/secret or permission changes, real payment,
public communication, irreversible work, new or unbounded cost, or material
scope expansion; it SHALL NOT expand native platform approval boundaries.

#### Scenario: Derived Staging prerequisite
- **GIVEN** a valid envelope for one named Staging target
- **AND** a canonical push, CI result, configuration repair, deploy and smoke
  test are listed as dependent steps
- **WHEN** one listed prerequisite becomes necessary
- **THEN** the orchestrator SHALL execute it without conversational re-approval
- **AND** SHALL preserve Production, IAM/secret, payment, public, irreversible,
  cost and scope-expansion exclusions

#### Scenario: Native external-write boundary rejects a listed step
- **GIVEN** a listed non-production write is rejected by the runtime or provider
- **WHEN** the target and effect remain unchanged
- **THEN** the orchestrator SHALL report at most one consolidated capability
  mismatch
- **AND** SHALL NOT issue serial conversational confirmations for that step

### Requirement: Human gates and external waits stay minimal
The workflow SHALL reserve `user_action_pending` for a genuine human-only input
or judgment and SHALL keep an `external_wait` monitor terminally quiet. A
scope/over-engineering audit SHALL be read-only and SHALL NOT create work or
mutate workflow state by itself.

#### Scenario: Inbox code is required for a bounded smoke test
- **GIVEN** a valid non-production envelope permits the smoke test
- **AND** the next value exists only in a human-controlled inbox
- **WHEN** the code is needed
- **THEN** the state SHALL become `user_action_pending`
- **AND** the request SHALL name only that human input

#### Scenario: CI is pending
- **GIVEN** a monitor has an authoritative CI operation identifier
- **WHEN** the operation remains pending
- **THEN** it SHALL not emit periodic narrative updates
- **AND** it SHALL notify its owner only at a terminal state, timeout, or
  required genuine human input

#### Scenario: Scope audit finds non-critical supporting work
- **GIVEN** the audit can classify the work as `park`
- **WHEN** it completes its read-only assessment
- **THEN** it SHALL report the classification without creating a task or
  changing a change state

### Requirement: Incomplete work SHALL use an explicit checkpoint gate
The workflow SHALL distinguish an incomplete checkpoint commit from a Done
claim. A meaningful verified slice MAY be preserved before handoff, external
wait, writing-claim release, or material rescope without time-, line-, or
commit-count quotas. The commit SHALL declare `Workflow-Checkpoint:
<change-id>` and stage matching tasks, verification with completed work,
focused checks, open gates and exact resume step, checkpointed status, report,
NEXT-SESSION state and fresh task registry.

#### Scenario: Verified incomplete slice is handed off
- **GIVEN** an active change has unfinished tasks and staged matching evidence
- **WHEN** its commit declares the exact checkpoint trailer
- **THEN** the pre-commit gate SHALL accept it without requiring all tasks
- **AND** the change SHALL remain incomplete

#### Scenario: Checkpoint evidence is absent or ambiguous
- **GIVEN** an active incomplete change seeks a checkpoint
- **WHEN** its trailer, staged evidence, or exact resume step is absent or
  belongs to another change
- **THEN** the pre-commit gate SHALL reject the commit

### Requirement: Checkpoints SHALL NOT establish Done
`workflow/scripts/change-done.sh --change <id>` SHALL remain the only Done
authority. The checkpoint validator SHALL reject an all-complete change from
the checkpoint path, and incomplete work SHALL fail the final completion gate.
Proposal-only documentation commits SHALL retain normal registry and spec-drift
validation without being inferred as completion claims.

## Lean delivery requirements

### Requirement: Outcome precedence and compact change memory

The workflow SHALL treat observable acceptance as primary and keep a change
compact: one outcome, at most three acceptance criteria, non-goals, short tasks,
recorded operative decisions, and one next action.

#### Scenario: Supporting ceremony is not causally required
- **WHEN** a report, registry field, review, delegation, or evidence artifact has
  no causal relation to acceptance or a concrete high-risk gate
- **THEN** it is derived, deferred, parked, or omitted rather than blocking the
  outcome

### Requirement: Decisions invalidate by dependency

An explicit in-scope user decision SHALL remain reusable for dependent
reversible work until an attested dependency changes, scope is exceeded, or a
genuine human-only judgment remains.

#### Scenario: Unrelated metadata is missing
- **WHEN** metadata required only for a later Production control is absent
- **THEN** unrelated implementation and non-production verification continue

### Requirement: Slice-proportional risk and delegation

Risk, evidence, and delegation SHALL be selected for the changed vertical slice.
The Main MAY deliver a bounded slice directly and SHALL delegate only for useful
parallel ownership, specialized capability, or required independent judgment.

#### Scenario: Small reversible slice
- **WHEN** a slice is isolated, reversible, and carries no concrete high-risk gate
- **THEN** one targeted check and a coherent breadcrumb are sufficient

### Requirement: Breadcrumb and Claim Release separation

An ordinary incomplete progress commit SHALL use `Change-Id` and a focused
`Test: ... exit 0`; full checkpoint evidence and
`Workflow-Checkpoint` SHALL be reserved for handoff, external wait needing
resume state, writing-claim release, or material rescope.

#### Scenario: Final completion
- **WHEN** a change is claimed Done
- **THEN** `workflow/scripts/change-done.sh --change <id>` MUST exit 0

### Requirement: Mutable ownership schedules work

Every active change SHALL have exactly one of `active_write`,
`external_wait`, `user_action_pending`, `parked`, `ready_for_verify`,
`ready_for_archive`, or `done`; only `active_write` consumes writing
capacity.

#### Scenario: Preserved unfinished work
- **WHEN** incomplete work is parked or waiting without an active writer
- **THEN** it does not consume a writing lane

### Requirement: Scope reset replaces the critical path

After the bounded failed-approach or unplanned-fix threshold, the workflow SHALL
replace the superseded active critical path with the smallest acceptance path
and park historical/supporting work.

#### Scenario: Supporting work spawns supporting work
- **WHEN** the next supporting step does not materially move acceptance closer
- **THEN** the active scope is reset rather than expanded

### Requirement: Worker handoffs are conditional

A worker handoff SHALL require only the compact delivery core and SHALL add
capability, authority, environment, Claim Release, visible-task, outcome-guard,
or findings extensions only when their named trigger applies.

#### Scenario: Local bounded handoff
- **WHEN** no external capability, rescope, checkpoint, or visible task is involved
- **THEN** omitted extension fields are treated as not applicable
