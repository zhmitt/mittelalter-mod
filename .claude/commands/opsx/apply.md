---
name: opsx-apply
description: Implement work from an active OpenSpec change using canonical workflow artifacts.
category: workflow
tags: [openspec, implementation, tasks]
---

Read `AGENTS.md`, the active change under `openspec/changes/`, and `workflow/state/task-registry.md` first.

Then:

1. treat the change as compact working memory and reuse recorded in-scope
   decisions unless an attested dependency changed, scope was exceeded, or a
   genuine human-only judgment remains
2. select risk, evidence, and delegation for the changed slice; the Main may
   implement a bounded slice directly, and delegates only for elapsed-time,
   specialized-capability, or independent-judgment benefit
3. implement tasks from `tasks.md` and update canonical artifacts as
   understanding changes
4. refresh derived state with `workflow/scripts/tasks-sync.sh` when task or
   orchestration facts change
5. use an ordinary breadcrumb with `Change-Id` and focused `Test` for coherent
   local progress; use `workflow/scripts/milestone-sync.sh --summary "<what
   changed>"` only for durable Claim Release when a handoff, writing-claim
   release, material rescope, or external wait needs resume state
