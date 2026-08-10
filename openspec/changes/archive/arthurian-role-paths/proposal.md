# Proposal: Implement Arthurian role paths

## Why

The Arthurian layer now has tournaments, renown, Camelot, and named knights. Persistent role paths can connect those systems without replacing Minecraft survival or forcing every player into Arthur/Merlin identities.

## What changes

- Persist one Arthurian role per player: Young Knight, Arthur, or Merlin
- Assign Young Knight as the default on first eligible login
- Gate Arthur behind explicit special-role configuration and Round Table renown
- Gate Merlin behind special-role plus mythic configuration and honored renown
- Add small shared-system progression hooks rather than parallel rules
- Add selection items/messages, resources, tests, and runtime evidence

## Impact

This adds persistent per-player role state and two optional configuration switches. Roles affect renown/command/mythic eligibility only; core inventory, combat, building, and survival remain unchanged.

## Workspace ownership

`primary workspace`; no branch handoff is planned.
