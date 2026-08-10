## ADDED Requirements

### Requirement: High-risk V1 world behavior is integration-tested

The project SHALL provide repeatable server-side integration tests for soldier persistence and role behavior, command authority boundaries, Camelot uniqueness, and tournament-to-renown progression.

#### Scenario: Existing gameplay systems run together
- **WHEN** the dedicated integration test suite executes in a clean generated world
- **THEN** soldiers retain valid state and role behavior
- **AND** invalid ownership, range, cap, or target operations are rejected safely
- **AND** Camelot and Arthurian progression state remain internally consistent

### Requirement: The distributable JAR is audited directly

The release workflow SHALL validate the actual built mod JAR for required metadata, production classes, assets, data files, and translations, and SHALL reject development-only source artwork or cache files from the distributable.

#### Scenario: Release artifact is assembled
- **WHEN** the clean build produces the candidate JAR
- **THEN** the artifact audit passes against the JAR entries
- **AND** a SHA-256 checksum and byte size are recorded as evidence

### Requirement: Release verification distinguishes deterministic and manual evidence

The release record SHALL include clean unit tests, data generation, dedicated GameTest execution, bounded client startup, and a separate manual in-world acceptance checklist.

#### Scenario: Automated verification is complete
- **WHEN** all deterministic commands pass and client resource initialization succeeds
- **THEN** the candidate may pass the repository completion gate
- **AND** unperformed visual, balance, or feel checks remain explicitly marked rather than being implied by startup evidence

### Requirement: Stabilization does not silently expand V2 scope

The change SHALL fix regression-backed integration defects only. Cavalry, siege engines, factions, defended world structures, and fantasy mobs SHALL remain separate future changes.

#### Scenario: A new feature idea appears during playtest
- **WHEN** the idea is not required to satisfy an existing V1 contract
- **THEN** it is recorded as follow-up work and is not added to the release-candidate implementation
