## ADDED Requirements

### Requirement: V1 weapons are registered and craftable
The mod SHALL register a longsword, poleaxe, and halberd under stable `mittelalter` identifiers and SHALL provide distinct survival recipes for each.

#### Scenario: Player reviews the medieval weapon roster
- **WHEN** the mod content is loaded
- **THEN** `mittelalter:longsword`, `mittelalter:poleaxe`, and `mittelalter:halberd` exist
- **AND** each weapon has a recipe, localized name, inventory model, and texture

### Requirement: Silver is a complete resource line
The mod SHALL provide silver ore, deepslate silver ore, raw silver, silver ingot, raw silver block, and silver block with mining loot and processing recipes.

#### Scenario: Player mines silver ore
- **WHEN** a player mines silver ore with an appropriate tool
- **THEN** the block produces raw silver subject to normal loot behavior
- **AND** raw silver can be smelted or blasted into a silver ingot

### Requirement: Ruby is a prestige resource line
The mod SHALL provide ruby ore, deepslate ruby ore, ruby, and a ruby block. Ruby SHALL support ceremonial or officer equipment and SHALL NOT define a generic full combat tier.

#### Scenario: Player obtains ruby
- **WHEN** a player mines ruby ore with an appropriate tool
- **THEN** ruby is available for storage and documented prestige crafting
- **AND** the mod does not register a complete ruby armor/tool tier

### Requirement: Implemented content is visible and localized
The mod SHALL expose the V1 equipment and resources through its creative tab and SHALL provide English and German localization.

#### Scenario: Player opens the Mittelalter creative tab
- **WHEN** the creative inventory is available
- **THEN** all V1 weapons, resource items, and resource blocks are visible with localized names
