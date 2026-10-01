# Development GameTest world entry

## Purpose
Allow local development worlds to load while preserving dedicated GameTests.

## Requirements
### Requirement: Development GameTests support registry synchronization
The mod SHALL register GameTests with codecs matching their runtime types and SHALL preserve dedicated test execution.

#### Scenario: A player enters a local development world
- **WHEN** Minecraft encodes the GameTest registry during login
- **THEN** all Mittelalter GameTests encode without type errors
- **AND** the player can enter the world.
