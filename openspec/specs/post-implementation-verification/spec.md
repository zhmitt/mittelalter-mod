# Post-Implementation Verification

## Requirements

### Requirement: Completion requires evidence
The repository SHALL require explicit evidence before a change is ready to archive.

#### Scenario: Change is checked for completion
- **WHEN** `workflow/scripts/post-impl-check.sh` runs against an active change
- **THEN** it SHALL require all tasks to be complete
- **AND** require a `verification.md` note in the change folder
- **AND** require a related entry in `workflow/state/status.md`
- **AND** require a report in `workflow/state/reports/`

### Requirement: Incomplete evidence blocks completion
The completion gate SHALL fail when required evidence is missing.

#### Scenario: Verification note is missing
- **WHEN** a change has completed tasks but no `verification.md`
- **THEN** the post-implementation check SHALL exit non-zero

