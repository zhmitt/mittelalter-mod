# Design: Arthurian legends as an optional module-layer

## Context

The core gameplay vision already positions the Mittelalter-Mod as a sandbox medieval warfare mod, not a mandatory story game. Arthurian material can enrich that world, but only if it behaves like a self-contained legend layer players can opt into. Otherwise Camelot, named knights, and mythic relics would overwhelm the broader medieval sandbox and make every world feel like one specific literary adaptation.

The design goal is therefore not "replace the Mittelalter-Mod with the Artus-Saga." It is "add an Arthurian module that can sit on top of the base warfare systems and, when enabled, provide tournaments, heraldry, elite knight identities, and optional legendary questlines."

## Decisions

### Decision 1: Arthurian content is an explicit add-on layer, not the default world identity
The base mod remains:

- open-ended sandbox
- medieval gear and fortifications
- recruitable/commandable soldiers
- sieges and factional fighting

The Arthurian layer adds a specific courtly-legendaric flavor on top of that. Worlds that do not enable the layer must still feel complete and coherent.

### Decision 2: The Arthurian layer is parallel to the fantasy layer, not a synonym for it
The repository should treat world/theme configuration as composable layers rather than a single mode switch. The intended combinations are:

- `core medieval sandbox`
- `core + fantasy`
- `core + Arthurian`
- `core + Arthurian + mythic/fantasy`

This matters because a player may want Camelot, tournaments, heraldry, and Round Table politics without dragons or overt magic. Arthurian content should therefore sit beside the fantasy layer and optionally connect to it, not collapse into it.

### Decision 3: Split the Arthurian layer into courtly and mythic sublayers
The source request mixes two compatible but distinct tones:

- `courtly Arthurian`
  - tournaments
  - Camelot
  - heraldry
  - named knights
  - rivalries, honor, renown
- `mythic Arthurian`
  - Excalibur
  - Lady of the Lake
  - Grail-inspired questing
  - Avalon-like spaces
  - prophetic or magical encounters

The courtly layer can work even in a mostly grounded world. The mythic layer should align with the mod's broader fantasy-mode philosophy and stay optional.

### Decision 4: Offer Arthurian play as optional start profiles or scenario roles
The idea that a player can be Arthur, Merlin, or a new knight is strong, but it should not replace the normal Minecraft avatar globally. It fits better as an optional Arthurian start-path system inside the layer.

Recommended role paths:

- `Young Knight`
  - recommended default Arthurian path
  - starts closest to ordinary Minecraft progression
  - rises through tournaments, service, renown, and Round Table access
- `Arthur`
  - prestige or scenario-specific ruler path
  - emphasizes leadership, alliances, command authority, and faction decisions
  - should feel less like a low-level survival start and more like a special campaign/sandbox variant
- `Merlin`
  - mythic-only path
  - emphasizes prophecy, knowledge, magical guidance, and rare event hooks
  - should only appear when the mythic subset is enabled

This keeps the core sandbox intact while still letting the Arthurian layer offer stronger character identity.

### Decision 5: Tournaments are the `V1` anchor because they fit both gameplay and theme
Paul's tournament request is the cleanest early Arthurian feature because it:

- uses combat systems the mod already wants
- gives cavalry/lance gameplay a natural home
- creates a reason for ceremonial armor and heraldry
- works without forcing a heavy story campaign

Recommended tournament event types:

- jousting lane duel
- melee bracket in an enclosed arena
- archery trial
- team skirmish tournament for retinues

Recommended rewards:

- renown
- heraldic cosmetics
- horse gear
- elite knight invitations
- access tokens to Camelot-aligned structures or events

Tournaments also provide the cleanest progression loop for the `Young Knight` path.

### Decision 6: Arthurian armor should be identity-rich, not just stronger stat ladders
Alternative armor in this layer should emphasize:

- heraldic surcoats and cloaks
- colored crests and shield devices
- distinct helmets for tourney, battle, and ceremonial use
- named knight sets tied to roles or prestige

This armor may have situational bonuses, but it should not flatten the rest of the equipment system by simply becoming "the best armor because it is famous."

Good bonus directions:

- mounted combat handling
- command aura / morale support
- tournament performance
- renown gain

### Decision 7: The Round Table should function as a social-faction hub, not only as lore decoration
The Knights of the Round Table are best represented as a high-prestige faction or elite companion layer.

Possible roles:

- quest-givers
- unlockable knight allies
- rival champions in tournaments
- exemplars of combat archetypes
- political actors during later scenario conflicts

This keeps them mechanically relevant to the soldier/warfare core of the mod.

For role-path integration:

- `Young Knight` should seek entry into this hub
- `Arthur` may begin with direct access or partial ownership of it
- `Merlin` should interact through prophecy/advisory privileges rather than ordinary knight progression

### Decision 8: Arthurian story content should be scenario-driven, never mandatory for normal play
The user explicitly wants this to stay a separate story. Therefore:

- no player must complete an Arthurian questline to access the core mod
- Camelot should be a rare destination or enabled structure set, not the center of every world
- legendary arcs should be optional event chains, scenario starts, or prestige questlines

This preserves compatibility with the core sandbox and with grounded non-Arthurian playthroughs.

### Decision 9: Stage the Arthurian layer by complexity

#### `V1` - Courtly warfare flavor

- heraldry and knightly color identity
- tournament grounds structure
- jousting and melee tournaments
- tournament armor variants
- named champion NPCs without deep quest chains
- `Young Knight` as the default Arthurian start path

#### `V2` - Camelot and Round Table systems

- Camelot or Camelot-aligned fortress/city structure
- Round Table hall as a faction hub
- notable knights with combat archetypes and reputations
- renown/honor progression tied to tournaments and service
- invitations, alliances, or rivalries
- `Arthur` as a scenario/prestige start path

#### `V3` - Mythic Arthurian legend

- Excalibur questline or relic event
- Lady of the Lake encounter
- Grail-inspired multi-step quest arc
- traitor/conflict scenario such as a Mordred-style split
- late-game legendary battle sequence
- `Merlin` as a mythic scenario/start path

### Decision 10: Adopt concrete default product choices for activation and role selection
To keep the Arthurian layer implementable and understandable, the current recommended defaults are:

- `Arthurian layer activation`
  - selected primarily at world creation
  - also controllable through server/config for curated multiplayer worlds
  - not treated as a casual mid-save toggle
- `Default Arthurian role`
  - `Young Knight`
  - this is the standard Arthurian start because it fits survival progression best
- `Special Arthurian roles`
  - `Arthur` and `Merlin` are not standard freeform starts in every Arthurian world
  - they should be scenario, prestige, or explicitly enabled role starts

This keeps the normal experience coherent, preserves sandbox progression, and avoids turning the Arthurian layer into a class picker detached from Minecraft survival.

### Decision 11: Treat tournaments as an early-to-mid progression pillar, not only endgame prestige
Tournaments should appear early enough to define the feel of the Arthurian layer, but not so early that a brand-new player with no gear immediately enters elite courtly competition.

Recommended default:

- first meaningful access in early-to-mid progression
- repeatable across the full playthrough
- useful both for growth (`Young Knight`) and prestige (`Arthurian veterans`)

This makes tournaments a signature loop of the layer rather than a late decorative side activity.

### Decision 12: Make Camelot unique-per-world by default
Camelot should feel singular and aspirational, not like one more repeated village structure.

Recommended default:

- one primary Camelot-aligned great structure per world
- optionally surrounded by smaller Arthurian outposts, tourney grounds, or knightly sites
- scenario variants may override this, but the baseline expectation is uniqueness

This preserves mythic weight and gives players a clear long-term destination.

### Decision 13: Round Table knights should be both allies and rivals by default
If every named knight is only a recruitable ally, the layer becomes flat fan service. If every named knight is only a hostile rival, Camelot loses warmth and courtly identity.

Recommended default:

- some knights function as allies, mentors, or recruitable companions
- some act as rivals, challengers, or political opponents
- some may shift depending on renown, choices, or scenario state

This creates stronger replay value and lets the Arthurian layer support both belonging and tension.

### Decision 14: Excalibur, Grail, and comparable relic arcs belong to the mythic Arthurian subset
The cleanest default is to keep overt legendary relics and miracle-coded questlines out of grounded Arthurian play. Camelot, tournaments, heraldry, and named knights can all function without them.

Recommended default:

- `Excalibur`, `Holy Grail`-style arcs, and `Lady of the Lake` encounters live in the mythic Arthurian subset
- grounded Arthurian worlds can still reference legend and prestige without manifest magical relics
- scenario overrides may surface relic lore earlier, but the default product boundary stays intact

This preserves the separation between courtly Arthurian play and full mythic/fantasy escalation.

### Decision 15: Use lightweight event-and-quest presentation, not a text-heavy RPG campaign
Arthurian story delivery should support atmosphere and progression without overwhelming Minecraft's sandbox rhythm.

Recommended default:

- short quest chains
- event-driven encounters
- tournament invitations, court summons, relic rumors, and faction incidents
- concise NPC text with clear action hooks

Avoid as the baseline:

- long dialogue trees
- mandatory visual-novel style exposition
- a single linear campaign that replaces free play

This keeps the Arthurian layer readable, flavorful, and compatible with survival sessions.

### Decision 16: Treat legendary relics as singular prestige objects with configurable scenario overrides
Relics such as Excalibur should feel world-defining, not farmable.

Recommended default:

- one canonical Excalibur-style relic outcome per world
- obtained through a mythic event, prestige quest, or scenario resolution
- server/scenario config may override this for special maps or replay-focused worlds
- Grail-style content should behave more like a culminating arc or blessing outcome than like ordinary equipment crafting

This gives relics proper weight while still leaving room for curated multiplayer or custom scenarios.

### Decision 17: Tournament access should support both world events and structure-based entry
Restricting tournaments to only one trigger model would leave too much value on the table.

Recommended default:

- tournament grounds and Arthurian venues are the primary physical access points
- scheduled or announced world events can activate major tournaments at those sites
- curated scenarios or servers may also trigger tournaments directly

This keeps tournaments discoverable in the world while still allowing pacing, anticipation, and multiplayer coordination.

### Decision 18: Keep a lightweight renown/honor system
The Arthurian layer benefits from a small social-prestige loop, but not from a heavy simulation of court politics.

Recommended default:

- use a lightweight `renown/honor` value or tier
- let it gate invitations, rival reactions, Round Table access, tournament eligibility, and some rewards
- avoid turning it into a dense reputation matrix with dozens of hidden modifiers

This gives the layer a coherent progression language without overwhelming the sandbox.

### Decision 19: Integrate the Arthurian layer by extending base systems, not duplicating them
The Arthurian layer should not introduce parallel combat, follower, or faction frameworks.

Recommended default integration:

- `soldier system`
  - squires, retainers, knight companions, and elite household guards are Arthurian skins/roles on top of the core soldier framework
- `fantasy / mythic systems`
  - Merlin, Excalibur, Grail arcs, and mystical encounters reuse the same mythic gates used by the broader fantasy profile
- `faction / world structure systems`
  - Camelot, knightly outposts, rival courts, and tournament grounds hook into the broader structure and faction model rather than inventing a separate world pipeline
- `equipment systems`
  - heraldic armor, lances, and ceremonial items extend the main equipment progression instead of replacing it

This keeps implementation complexity lower and prevents the Arthurian layer from fragmenting the overall mod architecture.

### Decision 20: Keep role paths asymmetric in privileges, not in core survival rules
All Arthurian role paths retain the same base survival, inventory, combat, and building systems. Their asymmetry is limited to starting context, access gates, quest/event hooks, and progression emphasis:

- `Young Knight` starts with ordinary survival-compatible access and earns prestige
- `Arthur` may begin with court access and leadership obligations, but does not bypass core resource or combat rules
- `Merlin` receives mythic knowledge/event hooks only when mythic gating is active, but does not become a separate magic-only game

This preserves shared implementation surfaces and multiplayer compatibility while still making the roles meaningfully different.

### Decision 21: Launch tournaments without requiring cavalry
The first tournament implementation uses melee brackets and archery trials. The venue/event architecture leaves room for jousting, but jousting remains disabled until the V2 cavalry and mounted-combat foundation exists. This prevents the Arthurian V1 from inventing a one-off horse system that would later diverge from the core mod.

## Arthurian Scenes That Fit The Mod

- `Sword in the Stone`
  - world event or prestige trial
  - likely best in mythic mode or a special scenario
- `Camelot`
  - rare structure with great hall, tournament yard, barracks, stables, chapel, and elite court NPCs
- `The Round Table`
  - knight council and mission hub
- `Knightly Tournaments`
  - recurring prestige combat events
- `A Young Knight's Rise`
  - survival-compatible progression into prestige
- `Questing Knights`
  - roaming elite NPCs with allegiances, favors, or duels
- `Lady of the Lake`
  - mystical lake shrine encounter
- `Camlann-style Final Battle`
  - optional scenario or endgame battle arc, not standard survival progression

## Integration With The Rest Of The Mod

- soldier systems provide the base for squires, retainers, and elite knights
- cavalry systems support jousting and mounted knight roles
- heraldry extends shields, banners, surcoats, and faction colors
- fortifications and castles provide natural homes for Camelot-adjacent structures
- fantasy mode provides the right gate for mythic relics and magical encounters
- optional role paths provide stronger identity without forcing a hard class system onto non-Arthurian worlds

The Arthurian layer should therefore reuse the base mod's systems rather than invent a disconnected parallel game.

## Open Questions

No product-owner question currently blocks follow-up implementation planning. Exact balance values and content counts remain implementation-level decisions.

## Trade-offs

- Keeping Arthurian content optional preserves the core identity, but it means some players may never see high-effort legend content.
- Start-role variety adds strong fantasy and replay value, but it risks pulling the layer toward scenario RPG design if the role paths diverge too far.
- Tournaments are an efficient early feature, but they depend on cavalry, arenas, and reward loops being satisfying.
- Named knights add strong flavor, but they require restraint so the sandbox does not become a lore dump.
- Mythic Arthurian content can be memorable, but it should remain aligned with the broader fantasy gate to avoid contaminating grounded worlds.

## Workspace ownership

`primary workspace` - this is a canonical concept-layer change authored in the top-level checkout with no worktree handoff.
