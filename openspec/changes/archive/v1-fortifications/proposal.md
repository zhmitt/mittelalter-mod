# Proposal: Implement V1 fortifications

## Why

The grounded warfare sandbox needs an early defensive building loop. Palisades create accessible first defenses, while reinforced stone and a functional arrow slit establish a later upgrade without globally rewriting vanilla stone behavior.

## What changes

- Add an early oak palisade block
- Add reinforced stone as a deliberately slower, iron-gated fortification material
- Add a directional stone arrow-slit block with a projectile opening
- Add recipes, loot, tags, models, textures, localization, and creative-tab entries
- Add deterministic geometry/resource tests and client smoke evidence

## Impact

These are stable player-facing block identifiers. The harder progression is scoped to new fortification materials; vanilla stone mining remains untouched, reducing compatibility risk with other mods and existing worlds.

## Workspace ownership

`primary workspace`; no branch handoff is planned.
