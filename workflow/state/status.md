# Status Log

Append-only project status log for deterministic session handover.

## 2026-07-09 07:40:24
- Change: bootstrap-mittelalter-mod
- Status: implemented
- Summary: Bootstrapped the hybrid app.dev-template workflow (governance, workflow scripts, three tool adapters, CI gates) and a NeoForge 26.1.2 Gradle mod skeleton (mod id mittelalter) as the repository foundation.
- Evidence: openspec/changes/bootstrap-mittelalter-mod/verification.md, workflow/state/reports/2026-07-09-bootstrap-mittelalter-mod.md
- Next: Add verification.md and workflow evidence

## 2026-07-09 07:43:35
- Change: bootstrap-mittelalter-mod
- Status: implemented
- Summary: Fixed a pipefail bug in post-impl-check.sh --staged that would abort on commits touching no openspec/changes/ path
- Completed: All tracked tasks are currently marked complete.
- Evidence: openspec/changes/bootstrap-mittelalter-mod/verification.md, workflow/state/reports/2026-07-09-bootstrap-mittelalter-mod.md
- Next: Archive the change into openspec/changes/archive/

## 2026-07-09 10:25:09
- Change: github-project-sync
- Status: implemented
- Summary: Lean GitHub Projects (v2) board wired as a one-directional, mechanically-derived visualization of openspec/changes/ state; openspec remains SSOT. Added workflow/scripts/github-sync.sh (sync/archive), .github/project-sync.json, issue template, and adapter pointers across Claude/Gemini/Codex.
- Completed: All tracked tasks are currently marked complete.
- Evidence: openspec/changes/github-project-sync/verification.md, workflow/state/reports/2026-07-09-github-project-sync.md
- Next: Add verification.md and workflow evidence
