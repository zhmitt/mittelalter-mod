# Worker handoff

## Always-required compact core

- Change id: `<change-id|none>`
- Outcome: `<one observable result>`
- Acceptance: `<up to three criteria>`
- Owned scope: `<files, components, or resources>`
- Excluded scope: `<explicit exclusions or none>`
- Baseline and workspace: `<branch>@<commit>; workspace mode>`
- Decisions already made: `<operative user/artifact/option decisions or none>`
- Completed and focused evidence: `<work plus commands/results>`
- Open gate: `<concrete acceptance/high-risk/authority gate or none>`
- Next action or stop condition: `<one executable action or satisfied stop>`

This compact core is sufficient for a bounded handoff. Add an extension below
only when its named trigger applies. Omitted extensions mean “not applicable”;
do not fill them with placeholder ceremony.

## Outcome Guard extension — only for a blocker, material rescope, or failed approach

- Outcome anchor: `<stable observable product/operational result>`
- User value: `<who benefits and how>`
- Required release evidence: `<proportional evidence for acceptance>`
- Non-blocking supporting evidence: `<tooling or research evidence>`
- Critical-path relation: `<direct|safety|evidence|supporting>`
- Blocker class: `<product|safety-security|release-evidence|tooling|external-authority|none>`
- Blocks exact criterion: `<criterion or none>`
- Causal evidence: `<reproduction/causal chain or none>`
- Smallest alternative evidence: `<alternative or none>`
- Safe default: `<conservative isolation or none>`
- Deferral route: `<follow-up|parked|none>`
- Failed approaches: `<0|1|2>`
- Complexity delta: `<components/dependencies added and removed>`

A supporting finding cannot become blocking without the exact criterion and
causal evidence. At two failed approaches or a supporting-to-supporting
escalation, stop mutation and run the outcome reset. The reset replaces the
superseded active critical path; it does not append another active scope.

## Capability preflight extension — only for delegated or external-capability work

- Required capabilities: `<filesystem/process/network/connectors/remotes/writes>`
- Preflight result: `<available|one consolidated mismatch>`
- Pre-positioned resources: `<exact commit/mirror/artifact/runtime or none>`
- Approval budget: `<default zero conversational; max native prompts>`
- Privilege-free design: `<process-local evidence/harness or why unavailable>`
- Runtime fallback: `<named suitable runtime or park condition>`
- Re-prompt prohibition: `<boundary/method that must not be retried>`

Validate this block read-only before implementation. Do not discover predictable
permissions command by command. One capability mismatch produces one
consolidated report; it does not authorize retries or scope expansion.

## Environment envelope — only for a bounded Sandbox, Dev, or Staging chain

- Environment class: `<sandbox|dev|staging|none>` (Production is excluded)
- Canonical target identity: `<provider project/account/tenant plus actual environment>`
- Allowed operation chain: `<for example canonical push -> CI -> bounded deploy -> smoke>`
- Candidate-resolution rule: `<exact commit, immutable artifact, or authoritative lookup>`
- Test-data bound: `<named synthetic record/account, maximum count, payment prohibition>`
- Invariants: `<secrets/IAM/Production/data boundaries that remain unchanged>`
- Excluded actions: `<Production, IAM/secrets, real payment, public communication, irreversible work, scope expansion>`
- Expiry or stop condition: `<terminal result, time bound, failure class, or none>`
- Native-boundary state: `<available|one consolidated mismatch|not-required>`
- User-only inputs: `<MFA/inbox code/ambiguous tenant/professional decision|none>`
- Terminal wait notification: `<terminal-only|not-applicable>`
- Scope-audit authority: `<read-only classification only|not-applicable>`

Use this envelope only for a bounded named target and only inside effective
inherited authority. Derived prerequisites inside the allowed chain do not need
conversational re-approval. A provider branch or source ref does not by itself
identify the environment. Native platform boundaries still apply; one rejected
enveloped external write produces one consolidated capability mismatch, not
serial requests for the same human confirmation. `user_action_pending` is
reserved for a real human-only input or judgment, while `external_wait` remains
terminal-only and quiet.

## Inherited authority extension — only for a delegated worker

- Authority source: `<parent session / explicit user authorization>`
- Parent permission profile: `<sandbox / approval policy / relevant connector grants>`
- Inherited safe actions: `<bounded actions executable without micro-approval>`
- Repository/worktree scope: `<exact paths, branch, and worktree>`
- External systems allowed: `<read/write boundary per named system or none>`
- Explicit user gates: `<production, push/merge/release, IAM/secrets, cost, public, irreversible, scope expansion>`
- Forbidden actions: `<secret disclosure and any task-specific prohibitions>`
- Escalation condition: `<impact or authority boundary that requires the user>`

Effective authority is the intersection of the parent runtime authority, this
declared scope, and repository policy. Evaluate resolved impact rather than the
command name. Do not request elevation pre-emptively for actions already inside
that intersection; if inheritance is unavailable, report the mismatch once and
request the narrowest actionable permission.

## Claim Release extension — only for durable incomplete-work preservation

- Commit trailer: `Workflow-Checkpoint: <change-id>`
- Trigger: `<handoff|external wait|claim release|material rescope>`
- Staged evidence: `<tasks.md, verification completed/checks/open-gates/next,
  checkpointed status, report, NEXT-SESSION resume step, fresh task registry>`
- Exact resume step: `<one executable next action>`

Do not use this extension for an ordinary local breadcrumb or a completed
change. It is required only for a workspace/branch handoff, writing-claim
release, material rescope, or external wait that needs durable resume state. It
preserves a bounded incomplete slice and never substitutes for
`change-done.sh --change <id>`.

## Visible task lifecycle extension — only for a visible Codex task

- Task title: `<PROJECT · Change <change-id> · short unique purpose|not-applicable>`
- Source thread id: `<threadId|none>`
- Destination thread id: `<destinationThreadId|none>`
- Client thread id: `<clientThreadId|none>`
- Worker final received: `<yes|no>`
- Acceptance/tests handed off: `<yes|no>`
- Checkpoint commit: `<commit-hash|documented-preservation|none>`
- Integration or preservation confirmed: `<yes|no>`
- Active task still owns worktree: `<yes|no|not-applicable>`
- Task cleanup disposition: `<archive-exact-thread|blocked-manual-cleanup|not-applicable-subagent>`
- Worktree ownership: `<exact-owned|orphan|ambiguous|not-applicable>`
- Worktree state: `<clean|dirty|not-applicable>`
- Needed for diagnosis: `<yes|no|not-applicable>`
- Worktree cleanup disposition: `<eligible|retain|blocked-manual-cleanup|not-applicable>`

## Material findings extension — only for evidenced material findings

Record no more than three. Reporting a finding does not authorize further
investigation, implementation, a new task, or persistent backlog state.

1. Evidence: `<concrete observation>`
   Impact: `<user or system impact>`
   Criticality: `<critical|material|minor>`
   Blocks acceptance: `<yes|no>`
   Likely files: `<paths or unknown>`
   Suggested route: `<scope checkpoint|urgent separate task|bounded follow-up|park|discard>`
2. Evidence: `<optional>`
   Impact: `<optional>`
   Criticality: `<optional>`
   Blocks acceptance: `<optional>`
   Likely files: `<optional>`
   Suggested route: `<optional>`
3. Evidence: `<optional>`
   Impact: `<optional>`
   Criticality: `<optional>`
   Blocks acceptance: `<optional>`
   Likely files: `<optional>`
   Suggested route: `<optional>`
