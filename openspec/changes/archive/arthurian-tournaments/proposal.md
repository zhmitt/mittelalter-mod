# Proposal: Implement Arthurian tournament gameplay and venues

## Why

Tournaments are the V1 anchor of courtly Arthurian play. A compact melee/archery event loop and a physical player-built venue prove repeatable prestige progression without depending on the later cavalry system.

## What changes

- Add a tournament standard as the required venue anchor
- Add a grounds deed that constructs a compact, validated tournament yard
- Add repeatable melee and archery trials with server-authoritative progress and timeout
- Award registered tournament tokens and a champion laurel for exceptional completion
- Gate venue construction and events behind `arthurian.enabled`
- Add tests and runtime evidence

## Impact

This introduces the first event-state system. It reuses registered heraldry rewards and normal combat events. Jousting remains deferred until the common cavalry foundation exists.

## Workspace ownership

`primary workspace`; no branch handoff is planned.
