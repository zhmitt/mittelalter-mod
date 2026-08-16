# Worker handoff

## Outcome Guard

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
escalation, stop mutation and run the outcome reset.

## Capability preflight and approval budget

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

+## Inherited Authority Contract

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
+
## Visible task lifecycle metadata

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


- Change id: `<change-id|none>`
- Worker kind: `<change-task|subagent|local>`
- Outcome: `<one observable result>`
- Acceptance: `<up to three criteria>`
- Owned scope: `<files or components>`
- Excluded scope: `<files, components, or none>`
- Branch and baseline: `<branch>@<commit>`
- Commit or stash: `<commit-hash|stash-reference|none>`
- Workspace mode: `<tool-managed workspace|manual git worktree|primary workspace>`
- Tests: `<commands and results>`
- Open blockers: `<none|concise blockers>`
- Scope deviations: `<none|explicitly approved deviations>`
- Integration order: `<before/after dependency or independent>`

## Material out-of-scope findings

Record no more than three. Reporting does not authorize more investigation,
implementation, a new task, or permanent backlog state.

1. Evidence: `<concrete observation>`
   Impact: `<impact>`
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
