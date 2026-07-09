---
description: Create or refine an OpenSpec change before non-trivial implementation.
argument-hint: change summary or change id
---

Read `AGENTS.md` and `openspec/config.yaml` first.

Create or refine the relevant OpenSpec change in `openspec/changes/`. Keep canonical truth in `openspec/` and `workflow/`.

If `.github/project-sync.json` exists, run `workflow/scripts/github-sync.sh sync` after creating the change so the GitHub Projects board picks up the new tracking issue.

