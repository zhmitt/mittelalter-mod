---
name: openspec-propose
description: Create or refine an OpenSpec change before non-trivial implementation.
---

Read `AGENTS.md` and `openspec/config.yaml`.

Use when:

- a request changes behavior or architecture
- a new capability needs formalization
- an existing change needs refinement

Update canonical OpenSpec artifacts, not tool-local state.

If `.github/project-sync.json` exists, run `workflow/scripts/github-sync.sh sync` after creating the change so the GitHub Projects board picks up the new tracking issue.

