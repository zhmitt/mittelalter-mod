# Design: Arthurian role paths

## Selection

When Arthurian content is enabled, a player without role state receives `YOUNG_KNIGHT` on login. Role choice is not presented when the layer is disabled.

Special role tokens request transitions:

- `Arthur's Signet`: requires `arthurian.specialRolesEnabled=true` and `ROUND_TABLE` renown (150)
- `Merlin's Grimoire`: requires special roles, `arthurian.mythicEnabled=true`, and `HONORED` renown (75)

Transitions are server-authoritative and never erase inventory or survival progress.

## Progression hooks

- Young Knight: +2 bonus renown on tournament completion, emphasizing ascent
- Arthur: +8 effective soldier command radius, emphasizing leadership
- Merlin: exposes mythic-event eligibility only; no parallel magic system is invented here

Benefits are pure queried functions so existing tournament/soldier systems remain the owners of their mechanics.

## Persistence

Role UUID mappings use overworld SavedData and safe enum parsing. Removing or renaming a future role falls back to Young Knight.

## Scope boundaries

No class-locked inventory, altered crafting base, separate health model, or mandatory story campaign is introduced.
