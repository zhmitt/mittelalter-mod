## ADDED Requirements

### Requirement: Young Knight is the default Arthurian role
The mod SHALL assign `YOUNG_KNIGHT` to an Arthurian-enabled player who has no stored role. Non-Arthurian players SHALL receive no forced role.

#### Scenario: Player joins an Arthurian world
- **WHEN** Arthurian content is enabled and the player has no role state
- **THEN** Young Knight is persisted as the default role

### Requirement: Arthur and Merlin are gated special roles
Arthur SHALL require explicit special-role configuration and Round Table renown. Merlin SHALL additionally require mythic configuration and at least Honored renown.

#### Scenario: Ineligible role transition is requested
- **WHEN** configuration or renown requirements are not met
- **THEN** the server rejects the transition and preserves the current role

### Requirement: Role state persists safely
The mod SHALL persist role state by player UUID and SHALL safely fall back to Young Knight for unknown serialized role names.

#### Scenario: Player reconnects
- **WHEN** stored role data is loaded
- **THEN** the same valid role and its shared-system hooks are restored

### Requirement: Roles extend shared systems
Role benefits SHALL be expressed through shared renown, soldier-command, and mythic-event hooks without changing core survival, inventory, building, or base combat rules.

#### Scenario: Role benefit is queried
- **WHEN** a tournament completes, command radius is calculated, or mythic eligibility is checked
- **THEN** the active role supplies its documented small modifier
- **AND** normal non-role mechanics remain owned by their existing systems

### Requirement: Role selection has complete presentation
Special role items and all role/status messages SHALL have English/German localization, client models/textures, gated creative visibility, and documented non-craftable or configured acquisition.

#### Scenario: Special roles are enabled
- **WHEN** an administrator presents role tokens to eligible players
- **THEN** Arthur and Merlin transitions can be requested through localized items
