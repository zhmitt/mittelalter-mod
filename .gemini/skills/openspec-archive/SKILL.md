---
name: openspec-archive
description: Archive a verified change into OpenSpec history.
---

Archive only after the canonical completion gate passes.

Prefer `openspec archive <change-id>` when available, then refresh workflow state.

If `.github/project-sync.json` exists, run `workflow/scripts/github-sync.sh archive <change-id>` to close the tracking issue and mark the board card Done.

