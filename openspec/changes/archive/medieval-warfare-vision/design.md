# Design: Mittelalter-Mod gameplay vision and phased scope

## Context

Henri's source description is rich in ideas but informal: it mixes vision, individual recipes, world tone, PvE/PvP goals, and some speech-to-text ambiguity. That is useful as creative input, but not stable enough to guide implementation work directly. The repository needs a normalized design baseline that future changes can reference when adding weapons, blocks, AI, world generation, or fantasy content.

This design deliberately keeps Minecraft recognizable. The mod is not aiming to replace the survival loop with a scripted campaign. Its identity is "Vanilla+ medieval warfare sandbox": build, gather, equip, fortify, command troops, and fight in a more medieval-feeling world.

## Decisions

### Decision 1: Position the mod as Vanilla+ medieval warfare, not a total conversion
Core Minecraft survival remains the base layer. The mod adds medieval texture through weapons, fortifications, troop control, and siege pressure instead of replacing the game with a separate RPG or narrative structure.

There is no mandatory story campaign. Optional scenario content may exist later, but the default expectation is sandbox play.

### Decision 2: Keep a grounded default profile and gate fantasy behind explicit settings
The brief clearly describes two tones:

- a grounded medieval mode with normal Minecraft monsters, castles, villages, and warfare
- an optional fantasy mode with dragons, trolls, wizards, and magic

That split should be explicit in configuration or world creation rather than blended by default. It keeps the first implementation track coherent and prevents dragons/magic from distorting every early systems decision.

### Decision 3: Treat player-commanded soldiers as the primary differentiator
Many mods can add swords or blocks. The strongest distinct identity in the brief is that the player can command soldiers in battle.

The soldier system should therefore be treated as a core pillar, not a side feature. The initial role split from the brief is:

- foot soldiers
- archers
- cavalry

The brief also implies dedicated equipment handling on the soldier side: hand slots, two-hand usage, armor, belt storage, and utility slots. That should be implemented progressively, but the long-term model is a soldier unit with role-based combat behavior and visible equipment state.

### Decision 4: Use bounded in-game commands, not microphone integration
The source brief says soldiers obey the player's speech and that voice has limited range in the world. The confirmed gameplay intent is that nearby soldiers react to in-game commands with distance limits.

`V1` therefore uses an in-game command abstraction with range limits (`Follow`, `Hold`, `Attack`). Real microphone or speech-recognition integration is not part of the planned command system.

### Decision 5: Phase the feature set instead of attempting full medieval + fantasy + siege scope at once
The idea set is too broad for one implementation wave. The repository should sequence the work into phases.

#### `V1` - Core medieval sandbox

- medieval equipment baseline:
  - longsword
  - poleaxe / long axe
  - halberd
- resource expansion:
  - silver ore
  - ruby ore
- harsher early progression around stone extraction
- palisade-style defensive building blocks
- soldier MVP:
  - foot soldier and archer first
  - simple command set with range limits
- no mandatory story
- grounded world profile only

#### `V2` - Warfare and fortification depth

- cavalry
- richer soldier equipment slots and formation behavior
- siege-focused blocks such as an arrow-slit defensive block
- siege machinery:
  - trebuchet / tribock
  - catapult
  - ram
  - siege tower
  - other validated medieval engines from the brief
- PvP/PvE skirmish support
- stronger structure/worldgen support for castles, factions, or defended settlements

#### `V3` - Fantasy expansion

- fantasy world profile
- dragons
- trolls
- wizards
- magic systems or magical combat hooks
- special interactions for silver and other materials against fantasy threats

### Decision 6: Give new materials distinct gameplay roles, not just another tier ladder
The brief introduces silver and ruby. If they are implemented as generic copies of iron/diamond with slightly different numbers, the system will feel arbitrary.

Recommended direction:

- silver: effective against fantasy-aligned enemies or magic users
- ruby: decorative, ceremonial, and officer/prestige equipment rather than a standalone combat tier

This fits the medieval/fantasy split better than treating both as ordinary stat tiers.

### Decision 7: Normalize ambiguous spoken terms explicitly
Several source terms were likely distorted by speech recognition. The current best-fit interpretations are:

- "Triebbock" -> `trebuchet` or `tribock`
- "Rambok" -> `ram` / `battering ram`
- "Skischattenblock" -> likely `Schiessschartenblock` / arrow-slit defensive block
- "lange Axt" -> long axe / poleaxe family weapon

The defensive block has been confirmed as a `Schiessschartenblock` / arrow-slit block. The exact final names for the siege engines and poleaxe family remain subject to later implementation naming.

### Decision 8: Preserve vanilla TNT outside the medieval progression
TNT remains available as part of Minecraft and is not globally disabled. It is not a required ingredient, unlock, or central tool in the intended medieval progression. Dedicated siege systems should not be designed merely as TNT delivery mechanisms.

### Decision 9: Model siege engines as assembled entities
Siege engines should use a hybrid model: construction or assembly consumes documented parts, while the completed engine is represented as an entity for aiming, animation, collision, ownership, and movement where appropriate. Large engines may require a stationary deployed state. They are not ordinary single blocks and do not require full free-form multiblock detection.

### Decision 10: Add defended sites without replacing vanilla settlements
Vanilla villages remain available. The mod should first add rare castles, camps, and defended sites alongside them, with lightweight faction ownership and hostility data. Replacing or globally rewriting vanilla villages is outside the initial world-generation scope.

### Decision 11: Gate fantasy at world creation with server configuration support
The grounded profile is the default. Dragons, trolls, wizards, magic systems, and fantasy-specific spawning remain disabled unless the fantasy profile is explicitly enabled at world creation or by an equivalent server configuration before normal play. Implementations should avoid casual mid-save toggling when it would leave incompatible world or progression state.

## Open Questions

- How are soldiers acquired: crafted, recruited, hired, trained, or spawned through structures?
- How many soldiers can one player command at once before balance or pathfinding breaks down?
- Should cavalry already exist in `V1`, or is foot + archer sufficient for the first release?
- Should silver weapons exist in grounded worlds too, or only matter once fantasy mode exists?
- Which specific defended site is the first world-generation implementation target?
- Which siege engine should validate the assembled-entity architecture first?

## Trade-offs

- Using in-game commands keeps soldier control deterministic and server-friendly, but gives up microphone-driven novelty.
- Splitting grounded and fantasy profiles improves product clarity, but requires discipline so fantasy content does not leak into the baseline prematurely.
- Making soldiers a core pillar raises AI/pathfinding complexity early, but that is where the mod becomes distinct instead of feeling like "Minecraft with a few extra swords."
- Delaying full siege engines until after troop/fortification basics reduces scope risk and makes balance easier to judge.

## Workspace ownership

`primary workspace` - this is a canonical product-direction change created in the top-level checkout, with no worktree handoff involved.
