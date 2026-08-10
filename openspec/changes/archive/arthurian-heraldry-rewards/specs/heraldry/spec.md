## ADDED Requirements

### Requirement: Arthurian heraldry provides visible armor identity
The mod SHALL provide lion, stag, raven, and grail heraldic surcoats as equippable chest items with distinct visible presentation.

#### Scenario: Player equips a surcoat
- **WHEN** a player equips one of the four heraldic surcoats
- **THEN** its heraldic identity is visually distinguishable
- **AND** its protection does not create a tier above the intended iron-equivalent baseline

### Requirement: Courtly rewards have stable reusable identifiers
The mod SHALL register a stackable tournament token and a champion laurel prestige trophy for later tournament reward logic.

#### Scenario: Tournament system grants a reward
- **WHEN** a later tournament implementation resolves a valid reward
- **THEN** it can grant `mittelalter:tournament_token` or `mittelalter:champion_laurel` without introducing new reward identifiers

### Requirement: Heraldic content remains optional
Arthurian heraldic acquisition and presentation SHALL respect an `arthurian.enabled` configuration that defaults to false.

#### Scenario: Grounded non-Arthurian world loads
- **WHEN** Arthurian content is disabled
- **THEN** normal medieval equipment progression remains complete
- **AND** Arthurian heraldic content is not presented as required core progression

### Requirement: Heraldic content has complete resources
The six new items SHALL have English/German localization, client models, textures, creative presentation when enabled, and documented acquisition or reward ownership.

#### Scenario: Arthurian content is enabled
- **WHEN** the client opens the Mittelalter creative tab
- **THEN** four surcoats and two reward items are presented with localized names and valid models
