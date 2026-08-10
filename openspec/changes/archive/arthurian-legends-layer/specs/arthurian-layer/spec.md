## ADDED Requirements

### Requirement: Arthurian content remains optional to the core sandbox
The Mittelalter-Mod SHALL support Arthurian-themed content only as an optional layer or module above the core medieval sandbox. Core survival, warfare, and progression SHALL remain playable without enabling or completing Arthurian content.

#### Scenario: Standard non-Arthurian world
- **WHEN** a player starts a world without the Arthurian layer enabled
- **THEN** the base medieval warfare sandbox remains fully playable
- **AND** Camelot-specific factions, tournaments, and Arthurian quest hooks are not required for normal progression

#### Scenario: Arthurian layer combines with other world layers
- **WHEN** a player configures a world with Arthurian content enabled
- **THEN** the Arthurian layer can exist independently of the broader fantasy layer
- **AND** mythic Arthurian content can still remain separately gated

### Requirement: Arthurian layer activation is chosen as world configuration
The Arthurian layer SHALL be enabled primarily through world or scenario configuration, and MAY also be controlled through server configuration for curated multiplayer worlds. It SHALL NOT assume casual mid-save toggling as the normal product path.

#### Scenario: Server hosts an Arthurian world
- **WHEN** a server or player creates a world intended to include Arthurian content
- **THEN** Arthurian activation is configured before or during world setup
- **AND** connected players enter a world with a consistent Arthurian ruleset

### Requirement: The Arthurian layer supports tournaments as a core activity
When the Arthurian layer is enabled, the mod SHALL support knightly tournament gameplay as a repeatable activity that fits the mod's combat and cavalry systems.

#### Scenario: Player enters a tournament
- **WHEN** a player reaches an Arthurian tournament venue or event
- **THEN** they can participate in at least one structured competitive activity such as jousting, melee combat, or archery
- **AND** the tournament yields prestige, rewards, or access to further Arthurian content

#### Scenario: Arthurian progression begins
- **WHEN** a player advances through an Arthurian-enabled playthrough
- **THEN** tournament participation becomes available during early-to-mid progression rather than only at endgame
- **AND** tournaments remain useful later as repeatable prestige content

#### Scenario: Tournament activation uses world venues and events
- **WHEN** Arthurian tournament content appears in a world
- **THEN** it is anchored to tournament grounds, Camelot-aligned sites, or comparable Arthurian venues
- **AND** event scheduling or announcements can activate tournaments at those locations

### Requirement: The Arthurian layer supports optional role-based starts or scenario paths
The Arthurian layer SHALL be able to present distinct Arthurian identity paths such as a young knight aspirant, Arthur, or Merlin without making any of those identities mandatory for non-Arthurian play.

#### Scenario: Player chooses a survival-compatible Arthurian path
- **WHEN** a player wants to experience Arthurian progression without skipping ordinary survival growth
- **THEN** the layer offers a `Young Knight`-style path that can rise into tournament, Camelot, and Round Table content through progression

#### Scenario: Default Arthurian role selection
- **WHEN** a player starts a standard Arthurian-enabled playthrough without choosing a special scenario
- **THEN** the recommended/default role path is `Young Knight`
- **AND** the player is not forced into an `Arthur` or `Merlin` identity

#### Scenario: Player chooses a mythic Arthurian path
- **WHEN** a player selects a Merlin-style path
- **THEN** that path is only available when the mythic Arthurian subset is enabled
- **AND** it emphasizes magical or prophetic hooks rather than ordinary knight progression

#### Scenario: Player chooses a ruler-style Arthurian path
- **WHEN** a player selects an `Arthur`-style path
- **THEN** that path is treated as a special scenario, prestige start, or explicitly enabled role start
- **AND** it does not replace the default progression model for ordinary Arthurian worlds

### Requirement: Arthurian armor emphasizes heraldic identity
The Arthurian layer SHALL provide alternate knightly armor or equipment variants that communicate heraldry, prestige, or role identity beyond raw combat stats.

#### Scenario: Player acquires Arthurian knight gear
- **WHEN** a player earns or obtains Arthurian-themed knight equipment
- **THEN** the equipment presents visible factional, heraldic, ceremonial, or prestige identity
- **AND** it is not implemented solely as an unexplained superior stat tier

### Requirement: The Round Table is represented as an interactive factional system
If Camelot and the Round Table are present, they SHALL provide interactive gameplay functions such as allies, rivals, missions, honors, or prestige gates rather than existing only as decorative lore references.

#### Scenario: Player reaches Camelot-aligned content
- **WHEN** a player gains access to the Arthurian elite court or its champions
- **THEN** named Arthurian knights or institutions offer meaningful interactions such as recruitment, rivalry, tournaments, or quests

#### Scenario: Arthurian knight relationships vary
- **WHEN** a player engages with named Round Table knights
- **THEN** the layer presents a mix of allied, rival, and potentially shifting relationships rather than one uniform stance for every knight

### Requirement: Arthurian progression uses lightweight renown or honor
The Arthurian layer SHALL use a lightweight renown/honor progression signal to gate prestige-oriented content such as invitations, rival reactions, eligibility, or access, without requiring a heavyweight social simulation.

#### Scenario: Player earns Arthurian prestige
- **WHEN** a player wins tournaments, completes Arthurian favors, or advances in courtly standing
- **THEN** their renown/honor state can unlock additional invitations, rival responses, or prestige opportunities
- **AND** the system remains lightweight enough to coexist with ordinary survival play

### Requirement: Mythic Arthurian content stays separately gateable
Arthurian mythic content such as Excalibur, the Lady of the Lake, or Grail-inspired arcs SHALL be separately gateable from the grounded courtly layer so that grounded Arthurian play remains possible.

#### Scenario: Grounded Arthurian playthrough
- **WHEN** a player enables Arthurian courtly content without the mythic subset
- **THEN** tournaments, heraldry, Camelot, and named knights can still appear
- **AND** overt mythic relics or magical Arthurian encounters do not have to appear

#### Scenario: Mythic relics remain gated
- **WHEN** a world does not enable the mythic Arthurian subset
- **THEN** Excalibur-style relics, Grail arcs, and Lady-of-the-Lake encounters are not part of normal progression
- **AND** the Arthurian layer still remains mechanically complete as a courtly sandbox extension

### Requirement: Camelot is singular by default
The Arthurian layer SHALL treat Camelot as a unique or primary world landmark by default rather than as a frequently repeated generic settlement, while still allowing smaller Arthurian-adjacent sites elsewhere.

#### Scenario: World generates Arthurian landmarks
- **WHEN** a world includes Arthurian content
- **THEN** one primary Camelot-aligned seat can serve as the central courtly landmark
- **AND** auxiliary sites such as tourney grounds, knight outposts, or shrines may still appear outside Camelot

### Requirement: Arthurian narrative delivery remains sandbox-compatible
The Arthurian layer SHALL present narrative content through lightweight events, concise quest chains, and action-oriented NPC interactions rather than requiring a long, text-heavy linear campaign.

#### Scenario: Player engages Arthurian story content
- **WHEN** a player encounters Arthurian narrative hooks
- **THEN** the content is delivered through brief quests, invitations, incidents, or prestige events that return the player to sandbox play
- **AND** the player is not forced through extended dialogue-heavy sequences as the default mode of play

### Requirement: Legendary relics are singular prestige outcomes by default
Legendary Arthurian relics such as Excalibur SHALL be treated as singular prestige objects or mythic scenario outcomes by default, with optional scenario or server overrides when curated replayability is desired.

#### Scenario: Player resolves a relic arc
- **WHEN** a player completes a mythic Arthurian relic event or questline
- **THEN** the reward behaves as a world-significant prestige outcome rather than ordinary repeatable loot
- **AND** special scenario or server configuration may intentionally override that uniqueness

### Requirement: Arthurian systems extend the core mod architecture
The Arthurian layer SHALL extend the mod's main soldier, fantasy, faction, structure, and equipment systems rather than duplicating them with a separate parallel architecture.

#### Scenario: Arthurian companion or knight appears
- **WHEN** the Arthurian layer introduces squires, knight companions, or elite retainers
- **THEN** those units build on the core soldier framework rather than on a disconnected follower system

#### Scenario: Arthurian mythic content is enabled
- **WHEN** Merlin, Excalibur, or other mythic Arthurian content is active
- **THEN** that content uses the broader mythic/fantasy gating model instead of inventing a second independent magic-mode switch

### Requirement: Arthurian roles share core survival rules
Arthurian role paths SHALL vary through starting context, access, event hooks, and progression emphasis while sharing the core survival, inventory, building, and combat rules.

#### Scenario: Special Arthurian role enters normal play
- **WHEN** an `Arthur` or `Merlin` scenario role enters the sandbox
- **THEN** that player still participates in the common resource, inventory, building, and combat systems
- **AND** role-specific privileges do not require a separate parallel game architecture

### Requirement: Initial tournaments do not depend on cavalry
The first tournament implementation SHALL provide melee and/or archery competition without requiring the later cavalry system. Jousting MAY activate after the shared mounted-combat foundation exists.

#### Scenario: Courtly V1 is enabled before cavalry
- **WHEN** an Arthurian tournament venue activates and cavalry is unavailable
- **THEN** players can complete a structured melee bracket or archery trial
- **AND** no one-off Arthurian-only horse system is required
