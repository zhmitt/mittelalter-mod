# Workflow Governance

## Requirements

### Requirement: Canonical workflow contract
The repository SHALL define its workflow contract in `AGENTS.md`, `openspec/`, and `workflow/`.

#### Scenario: Adapter file exists
- **WHEN** a tool-specific file such as `CLAUDE.md`, `GEMINI.md`, `.claude/`, `.gemini/`, or `.codex/` is present
- **THEN** it SHALL behave as an adapter to the canonical layers
- **AND** it SHALL NOT be the only source of a mandatory workflow rule

### Requirement: Spec-first for non-trivial work
The repository SHALL require an OpenSpec change for non-trivial work.

#### Scenario: Non-trivial change is proposed
- **WHEN** a change introduces new behavior, design changes, or cross-cutting updates
- **THEN** it SHALL create or update an OpenSpec change before implementation begins

#### Scenario: Trivial maintenance is proposed
- **WHEN** a change is a typo-only fix, comment-only fix, or tiny bug restoration
- **THEN** the repository MAY skip formal change scaffolding

