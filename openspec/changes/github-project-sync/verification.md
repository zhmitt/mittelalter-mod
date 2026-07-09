# Verification: github-project-sync

## Board setup

Project: https://github.com/users/zhmitt/projects/5 ("Mittelalter-Mod"), public, linked to `zhmitt/mittelalter-mod`. Fields: default `Status` (Todo/In Progress/Done) + custom `Change ID` (text). Pointer stored in `.github/project-sync.json`.

## Sync script

Command: `workflow/scripts/github-sync.sh sync`

First run:
```
Created issue #1 for bootstrap-mittelalter-mod
Synced bootstrap-mittelalter-mod -> issue #1, state=ready_for_archive
Created issue #2 for github-project-sync
Synced github-project-sync -> issue #2, state=in_progress
```

Second run (idempotency check):
```
Synced bootstrap-mittelalter-mod -> issue #1, state=ready_for_archive
Synced github-project-sync -> issue #2, state=in_progress
```
No new issues created; `gh issue list --repo zhmitt/mittelalter-mod --state all` still shows exactly 2 issues (#1, #2).

`gh project item-list 5 --owner zhmitt --format json` confirms both items carry the correct `status` (`Done` for `bootstrap-mittelalter-mod`, `In Progress` for `github-project-sync`) and `change ID` field values, matching `workflow/scripts/phase-status.sh` output for each change.

## Definition of Done

Command: `workflow/scripts/change-done.sh --change github-project-sync`

Result: exit 0 — "Change github-project-sync is verifiably done." All phases pass (Phase 5 Claim-Evidence is a non-blocking WARN on the auto-scaffolded report bullets, consistent with the same pattern accepted for `bootstrap-mittelalter-mod`).

## 2026-07-09 10:25:09

- Summary: Lean GitHub Projects (v2) board wired as a one-directional, mechanically-derived visualization of openspec/changes/ state; openspec remains SSOT. Added workflow/scripts/github-sync.sh (sync/archive), .github/project-sync.json, issue template, and adapter pointers across Claude/Gemini/Codex.
- Phase state: ready_for_verify
- Tasks complete: 13/13
- Completed: All tracked tasks are currently marked complete.
- Next: Add verification.md and workflow evidence
