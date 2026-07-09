---
name: openspec-archive
description: Archive a fully verified OpenSpec change.
---

Archive only after `workflow/scripts/post-impl-check.sh` passes.

Prefer `openspec archive <change-id>` when the OpenSpec CLI is installed.

If `.github/project-sync.json` exists, run `workflow/scripts/github-sync.sh archive <change-id>` to close the tracking issue and mark the board card Done.

