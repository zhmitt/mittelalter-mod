# Session Handover

## Requirements

### Requirement: Session close updates canonical state
The repository SHALL provide a deterministic session close workflow.

#### Scenario: Session is closed with a summary
- **WHEN** `workflow/scripts/session-close.sh --summary "..."` runs
- **THEN** it SHALL refresh the task registry
- **AND** append an entry to `workflow/state/status.md`
- **AND** rewrite `workflow/state/NEXT-SESSION.md`

### Requirement: Session state remains tool-neutral
Session handover artifacts SHALL be readable and writable regardless of the active CLI tool.

#### Scenario: Different tool is used next session
- **WHEN** the next session starts in another supported tool
- **THEN** that tool SHALL be able to recover context from `workflow/state/` without relying on tool-local memory

