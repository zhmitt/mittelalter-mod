# Report: github-project-sync

## Summary

Added a lean GitHub Projects (v2) board as a one-directional, mechanically-derived visualization of `openspec/changes/` state; `openspec/changes/` remains the sole source of truth.

## Current state

- Phase state: ready_for_verify
- Tasks complete: 13/13

## Evidence

- `workflow/scripts/github-sync.sh sync` created issue #1 (`bootstrap-mittelalter-mod`) and issue #2 (`github-project-sync`) and a re-run produced no duplicates; see `verification.md`
- `workflow/scripts/change-done.sh --change github-project-sync` exited 0

## Next step

- Commit and push the change artifacts to `origin main`

## 2026-07-09 10:25:09

- Summary: Lean GitHub Projects (v2) board wired as a one-directional, mechanically-derived visualization of openspec/changes/ state; openspec remains SSOT. Added workflow/scripts/github-sync.sh (sync/archive), .github/project-sync.json, issue template, and adapter pointers across Claude/Gemini/Codex.
- Change: github-project-sync
- Phase state: ready_for_verify
- Tasks complete: 13/13
- Completed: All tracked tasks are currently marked complete.
- Next: Add verification.md and workflow evidence
