---
name: openspec-apply
description: Implement active OpenSpec tasks while keeping canonical state current.
---

Read the active change and `workflow/state/task-registry.md` first.

Treat the change as compact working memory and reuse recorded in-scope
decisions unless an attested dependency changed, scope was exceeded, or a
genuine human-only judgment remains. Select risk, evidence, and delegation for
the changed slice. Main may implement a bounded slice directly; use
`@opsx-implementer` only for elapsed-time, specialized-capability, or
independent-judgment benefit.

Implement from `tasks.md` and run `workflow/scripts/tasks-sync.sh` when task or
orchestration facts change. Use an ordinary `Change-Id` plus focused `Test`
breadcrumb for coherent local progress; reserve
`workflow/scripts/milestone-sync.sh` for durable Claim Release.
