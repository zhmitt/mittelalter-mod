## ADDED Requirements

### Requirement: Camelot is singular and persistent
The mod SHALL construct at most one primary Camelot landmark per world by default and SHALL persist its location across restart.

#### Scenario: Second charter is used
- **WHEN** Camelot already has a recorded location
- **THEN** the server refuses to construct another primary Camelot
- **AND** communicates the existing location to the player

### Requirement: Camelot provides an interactive faction hub
The V1 Camelot landmark SHALL include a great hall, central Round Table area, and named knight positions rather than existing only as a decorative marker.

#### Scenario: Camelot is constructed
- **WHEN** a valid Arthurian-enabled charter completes
- **THEN** the deterministic landmark and its named knight archetypes are created

### Requirement: Round Table knights reuse core unit architecture
Named Arthurian knights SHALL reuse shared soldier navigation, equipment, ownership/faction, and rendering foundations while exposing distinct ally/mentor/rival dispositions.

#### Scenario: Player encounters the court
- **WHEN** the three V1 named knights are present
- **THEN** at least one mentor, ally, and rival disposition is represented

### Requirement: Renown is lightweight and persistent
The mod SHALL maintain persistent per-player Arthurian renown with four documented tiers and SHALL avoid a dense hidden reputation matrix.

#### Scenario: Player completes tournaments
- **WHEN** a player completes normal or champion tournament sessions
- **THEN** renown increases by the documented amount
- **AND** the corresponding tier can be queried for later access gates

### Requirement: Camelot systems respect Arthurian gating
Charter use, court presentation, named knight creation, and renown awards SHALL be inactive when Arthurian content is disabled.

#### Scenario: Arthurian layer is disabled
- **WHEN** a player attempts to use a Camelot charter
- **THEN** no landmark or court NPC is created
