# Proposal: Lean GitHub Projects board as a visual projection of OpenSpec state

## Why

Multiple contributors need a visual, at-a-glance view of what's being worked on (a Kanban board), but `openspec/changes/` must remain the single source of truth for "is this change actually done" — that's the entire point of the `workflow/scripts/change-done.sh` gate this repository already enforces. If GitHub Issues/Projects became a second, independently-edited task tracker, the two would drift apart (the exact "Volume-Falle" failure mode `CLAUDE.md` warns about: state claimed in one place, not reflected in the other).

This change adds a thin, scripted, one-directional sync from `openspec/changes/` to a GitHub Projects (v2) board, so the board is always a derived view, never an independent source of truth.

## What changes

- Add `workflow/scripts/github-sync.sh`, a deterministic, idempotent script that:
  - creates or updates one GitHub Issue per active (non-archived) change in `openspec/changes/`, linked back to the change folder
  - adds that issue to a GitHub Projects (v2) board and sets its `Status` (mapped from `workflow/scripts/phase-status.sh` output) and `Change ID` fields
  - on change archival, closes the issue and marks it `Done`
- Add `.github/project-sync.json`, a small pointer file (owner + project number only — field/option IDs are looked up live on each run, so there is nothing stale to drift)
- Create the GitHub Projects (v2) board itself (`Mittelalter-Mod`, owned by `zhmitt`, linked to this repo), with one additional custom field (`Change ID`, text); the board reuses the project's default `Status` field (`Todo` / `In Progress` / `Done`) rather than introducing new status vocabulary
- Add a one-line pointer to `workflow/scripts/github-sync.sh` in the `opsx:propose` and `opsx:archive` adapter instructions (`.claude/`, `.gemini/`, `.codex/`), so the sync runs as a natural side effect of the existing propose/archive flow rather than as a separately-remembered step
- Add an issue template (`.github/ISSUE_TEMPLATE/change-proposal.md`) so external contributors can propose changes in GitHub-native form; a maintainer turns accepted proposals into a real `openspec/changes/<id>/` folder (the issue is an intake surface, never a source of truth)

## Impact

- `openspec/changes/` remains the sole source of truth for change status; the board is a read-derived visualization that can be deleted and regenerated at any time without losing information
- Adds a `project` scope dependency on the `gh` CLI token (already present on this machine)
- No change to the Definition-of-Done gate (`workflow/scripts/change-done.sh`) — this change does not touch verification logic, only adds a visualization layer on top of it
