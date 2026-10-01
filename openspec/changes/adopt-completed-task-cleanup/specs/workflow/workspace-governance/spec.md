# Delta: Workflow Workspace Governance

## ADDED Requirements

### Requirement: Completed visible change tasks SHALL be archived only from exact final handoffs

A visible Codex change task SHALL be archived only after its final output,
acceptance/tests, checkpoint, integration or preservation, absence of blockers,
and exact destination thread identity are confirmed. Client-only identity SHALL
fail closed.

### Requirement: Safe visible-task spawning SHALL establish canonical destination identity

A visible change task SHALL begin from a real same-directory thread identity,
then be uniquely named and handed into a tool-managed worktree. The successful
handoff's `destinationThreadId` SHALL become canonical before work is sent or
cleanup can be considered.

### Requirement: Subagent completion SHALL remain distinct from visible task archival

Subagent final state SHALL be consumed without emitting visible-task archival or
inventing thread identity.

### Requirement: Worktree release SHALL be separate and fail closed

Worktree release SHALL require exact ownership, a clean worktree, integrated or
preserved checkpoint, no diagnostic need, and no active owner. It SHALL NOT
authorize branch deletion or removal of dirty, orphan, or ambiguous worktrees.

