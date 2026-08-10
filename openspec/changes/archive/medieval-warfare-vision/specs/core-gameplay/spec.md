## ADDED Requirements

### Requirement: The mod remains a sandbox medieval extension of Minecraft
The Mittelalter-Mod SHALL preserve Minecraft's open-ended survival sandbox as its base play model while adding medieval warfare systems on top. It SHALL NOT require a linear story campaign for normal play.

#### Scenario: Player starts a standard world
- **WHEN** a player creates a world with the mod enabled
- **THEN** they can gather, build, explore, and fight without entering a mandatory scripted story
- **AND** the medieval features extend the sandbox instead of replacing it with a fixed campaign structure

### Requirement: The mod supports grounded and fantasy world profiles
The Mittelalter-Mod SHALL support a grounded medieval baseline and SHALL allow additional fantasy content to be enabled through explicit settings or world configuration.

#### Scenario: Grounded world profile
- **WHEN** a player uses the grounded/default profile
- **THEN** the world includes the medieval warfare systems of the mod
- **AND** fantasy-exclusive content such as dragons, trolls, and wizards is not active by default

#### Scenario: Fantasy world profile
- **WHEN** a player enables the fantasy profile
- **THEN** fantasy creatures and magic-capable opponents can appear in addition to the core medieval warfare systems

### Requirement: The mod adds medieval equipment through craftable progression
The Mittelalter-Mod SHALL add medieval-themed player equipment with craftable progression paths tied to material choices. The `V1` roster SHALL include a longsword, a poleaxe / long axe, and a halberd. Their concrete recipes and balance values SHALL be defined in the dedicated equipment implementation change.

#### Scenario: Player crafts advanced melee gear
- **WHEN** a player unlocks the relevant material tier and crafting ingredients
- **THEN** they can craft the `V1` longsword, poleaxe / long axe, and halberd through distinct documented recipes

### Requirement: The mod expands resources with distinct medieval roles
The Mittelalter-Mod SHALL add new resource lines, including silver and ruby, and those resources SHALL have distinct gameplay roles rather than serving only as cosmetic duplicates of vanilla tiers.

#### Scenario: Player evaluates new materials
- **WHEN** a player obtains silver or ruby resources
- **THEN** each resource unlocks equipment, upgrades, or interactions that are meaningfully different from vanilla iron/diamond progression

#### Scenario: Player obtains ruby
- **WHEN** a player obtains ruby
- **THEN** it supports decorative, ceremonial, or officer/prestige equipment
- **AND** it does not act as a generic combat tier above vanilla materials

### Requirement: The mod provides player-commanded soldier roles
The Mittelalter-Mod SHALL provide player-aligned soldiers in differentiated combat roles that can receive direct in-game player orders within a bounded command range. The command system SHALL NOT require microphone or speech-recognition input.

#### Scenario: Nearby soldiers receive an order
- **WHEN** a player issues a command to soldiers inside the effective command radius
- **THEN** those soldiers respond according to their role and current state
- **AND** soldiers outside the allowed range do not behave as if they heard the order

#### Scenario: Different soldier roles coexist
- **WHEN** a player fields both ranged and melee soldiers
- **THEN** the soldier roles remain behaviorally distinct rather than acting as identical reskins

### Requirement: The mod supports fortification-oriented play
The Mittelalter-Mod SHALL support fortification-oriented building and combat, including a stone arrow-slit / `Schiessschartenblock` and a path toward siege-oriented assaults.

#### Scenario: Player fortifies a settlement
- **WHEN** a player builds a defended base
- **THEN** the mod provides medieval-appropriate construction elements such as palisade-style defenses or other fortification blocks that fit the mod's theme

### Requirement: Vanilla TNT remains outside medieval progression
The Mittelalter-Mod SHALL NOT globally disable vanilla TNT. Its medieval equipment, fortification, and siege progression SHALL NOT require TNT as a central unlock or ingredient.

#### Scenario: Player uses the normal Minecraft sandbox
- **WHEN** a player obtains TNT through otherwise available Minecraft mechanics
- **THEN** the mod does not disable that TNT globally
- **AND** progression through the mod's medieval systems does not depend on it

### Requirement: Siege engines use an assembled-entity architecture
The Mittelalter-Mod SHALL represent completed siege engines as entities assembled from documented parts. An engine MAY enter a stationary deployed state, but SHALL NOT depend on unrestricted multiblock-shape discovery.

#### Scenario: Player deploys a siege engine
- **WHEN** a player completes assembly of a supported siege engine
- **THEN** the completed engine has entity state for ownership, aiming, animation, and collision
- **AND** any stationary deployment requirement is explicit to that engine

### Requirement: Defended sites coexist with vanilla villages
The Mittelalter-Mod SHALL preserve vanilla villages in the initial world-generation model and SHALL add castles, camps, or defended sites alongside them rather than globally replacing them.

#### Scenario: Grounded world generates settlements
- **WHEN** a grounded world generates settlement content
- **THEN** vanilla villages remain eligible to generate
- **AND** enabled medieval defended sites may generate independently with lightweight faction state

### Requirement: Fantasy content is explicitly gated
The Mittelalter-Mod SHALL use the grounded profile by default. Fantasy creatures, magic systems, and fantasy-specific spawning SHALL require explicit world-creation or equivalent pre-play server configuration.

#### Scenario: Player creates a default world
- **WHEN** no fantasy profile is explicitly selected
- **THEN** dragons, trolls, wizards, magic systems, and fantasy-specific spawning remain disabled
