## ADDED Requirements

### Requirement: Players can build an early palisade
The mod SHALL provide a craftable `mittelalter:oak_palisade` block with wood-appropriate mining and loot behavior.

#### Scenario: Player establishes an early defense
- **WHEN** a player has common wood and a small iron investment
- **THEN** they can craft and place oak palisade blocks

### Requirement: Reinforced stone provides scoped harsher progression
The mod SHALL provide `mittelalter:reinforced_stone` with greater hardness and blast resistance than ordinary stone bricks and SHALL require an iron-level tool for correct drops. The mod SHALL NOT globally alter vanilla stone mining.

#### Scenario: Player upgrades a fortification
- **WHEN** a player combines stone-brick and iron progression
- **THEN** they can craft reinforced stone for more durable defenses
- **AND** ordinary vanilla stone retains its existing behavior

### Requirement: Arrow slits provide functional cover
The mod SHALL provide a directional `mittelalter:arrow_slit` stone block whose collision geometry leaves a central firing opening.

#### Scenario: Defender uses an arrow slit
- **WHEN** the block faces an attacker
- **THEN** the defender can shoot through the central opening
- **AND** the surrounding frame provides collision cover

### Requirement: Fortification blocks are complete content
All V1 fortification blocks SHALL have recipes, correct loot/mining tags, localization, creative-tab visibility, blockstates, models, and textures.

#### Scenario: Client loads fortification content
- **WHEN** the Mittelalter content is available
- **THEN** the three blocks appear with complete survival and client-resource coverage
