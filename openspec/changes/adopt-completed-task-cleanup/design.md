# Design: Completed task cleanup

Repository code evaluates eligibility only. The orchestrator performs app-level
archival for the exact destination thread after a complete handoff. A
`clientThreadId` never substitutes for `threadId` or `destinationThreadId`.
Task archival and worktree release are independent decisions. Every missing or
ambiguous gate returns manual cleanup; no helper deletes tasks, branches, or
worktrees.

