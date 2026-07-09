---
name: opsx-propose
description: Create or refine an OpenSpec change before non-trivial implementation.
category: workflow
tags: [openspec, planning, governance]
---

Read `AGENTS.md` and `openspec/config.yaml` first.

Then:

1. identify whether the request is non-trivial
2. create or refine an active change in `openspec/changes/<change-id>/`
3. ensure `proposal.md`, delta specs, `design.md`, and `tasks.md` are coherent
4. keep tool-specific details out of the canonical artifacts unless the change is adapter-specific

Prefer the canonical OpenSpec artifacts over any tool-local memory or conventions.

If `.github/project-sync.json` exists, run `workflow/scripts/github-sync.sh sync` after creating the change so the GitHub Projects board picks up the new tracking issue.

