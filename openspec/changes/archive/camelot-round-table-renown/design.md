# Design: Camelot, Round Table, and renown

## Camelot V1 landmark

The Camelot charter validates a flat 15×15 site and constructs a fortified great hall with walls, gate, central round-table chamber, banners/standards, and knight positions. World saved data records the first location and rejects additional charters. This is the unique primary seat; later worldgen may discover/place it automatically using the same state.

## Round Table knights

V1 provides three named archetypes built on the soldier entity foundation:

- Sir Bedivere — mentor/ally
- Sir Gawain — ally/champion
- Sir Lancelot — rival/challenger

They reuse common navigation/combat/rendering and expose disposition/name state. Deep dialogue and branching politics remain later content.

## Renown

Renown is an integer with four public tiers: `UNKNOWN` (0), `RECOGNIZED` (25), `HONORED` (75), `ROUND_TABLE` (150). Tournament completion awards 10 renown; champion completion awards 20. Renown persists per player in server saved data and can gate future invitations/roles.

## Scope boundaries

The landmark is intentionally compact and player-triggered in V1. No vanilla village replacement, mandatory quest chain, or dense reputation matrix is introduced.
