---
name: workflow-status
description: Inspect canonical workflow state in the repository.
---

Read compact NEXT-SESSION and query `workflow/scripts/phase-status.sh --change <id>`
for the selected change (omit --change for an overview). Inspect the registry only
for cross-change scheduling; use tasks-sync --dry-run only to check freshness.
Query relevant status-log entries on demand, not all history. Do not infer status
from tool-local state or mutate derived state during a read-only status request.
