# Preservation procedure

Read only when the core names this trigger. Retained repository-specific details.

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
