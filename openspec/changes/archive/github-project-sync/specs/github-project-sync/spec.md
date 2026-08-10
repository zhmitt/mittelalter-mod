## ADDED Requirements

### Requirement: OpenSpec remains the sole source of truth for change status
The GitHub Projects board SHALL be a mechanically-derived view of `openspec/changes/`. No script or process SHALL write GitHub issue/project state back into `openspec/changes/` or `workflow/state/`.

#### Scenario: Board and repo disagree
- **WHEN** a contributor manually drags a card to a different column on the GitHub board without updating the corresponding change folder
- **THEN** the next `github-sync.sh` run overwrites the card's `Status` field back to whatever `phase-status.sh` derives from `openspec/changes/`, silently correcting the drift

### Requirement: One GitHub issue per active change, idempotently synced
`workflow/scripts/github-sync.sh` SHALL create at most one GitHub issue per active (non-archived) change directory, and MUST NOT create a duplicate issue on repeated runs.

#### Scenario: Running sync twice
- **WHEN** `github-sync.sh` is run twice in a row with no changes to `openspec/changes/` in between
- **THEN** no new issues are created on the second run; existing issues and project fields are left unchanged

#### Scenario: New active change appears
- **WHEN** a new directory is created under `openspec/changes/` (not `archive/`)
- **THEN** the next `github-sync.sh` run creates exactly one new GitHub issue for it, added to the project board with `Status` derived from `phase-status.sh`

### Requirement: Status field is derived from phase-status.sh, not hand-set
The GitHub Projects board's `Status` field for a change's issue SHALL be set by mapping the change's `phase-status.sh` state through a fixed table (`draft`/`ready_for_design`/`ready_for_tasks` → `Todo`; `in_progress`/`ready_for_verify` → `In Progress`; `ready_for_archive`/archived → `Done`).

#### Scenario: Change moves from proposal to implementation
- **WHEN** a change's `tasks.md` starts accumulating checked items such that `phase-status.sh` reports `in_progress`
- **THEN** the next `github-sync.sh` run moves the corresponding board card to `In Progress`

### Requirement: Archiving a change closes and completes its issue
When a change directory is moved to `openspec/changes/archive/`, `github-sync.sh` SHALL close the corresponding GitHub issue and set its board `Status` to `Done`.

#### Scenario: Change is archived
- **WHEN** a change folder is moved from `openspec/changes/<id>/` to `openspec/changes/archive/<id>/`
- **THEN** the next `github-sync.sh` run closes the linked issue and sets its `Status` field to `Done`
