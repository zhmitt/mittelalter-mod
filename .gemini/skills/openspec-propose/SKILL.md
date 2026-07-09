---
name: openspec-propose
description: Create or refine an OpenSpec change before non-trivial implementation.
---

Read `AGENTS.md` and `openspec/config.yaml`.

Use when:

- a change is non-trivial
- a new capability is being defined
- an existing change needs refinement

Keep canonical truth in `openspec/` and `workflow/`.

If `.github/project-sync.json` exists, run `workflow/scripts/github-sync.sh sync` after creating the change so the GitHub Projects board picks up the new tracking issue.

