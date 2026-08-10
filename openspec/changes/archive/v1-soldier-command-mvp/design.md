# Design: V1 soldier command MVP

## Domain model

Both soldier roles share owner UUID, `FOLLOW`/`HOLD`/`ATTACK` order, hold position, and an optional attack target. Foot soldiers use melee equipment and close distance; archers use bows and ranged attacks.

## Recruitment

Craftable recruitment contracts spawn one owned soldier only when the player remains below the configured cap. This represents hiring/recruitment rather than crafting a person: the item is a contract consumed by the recruitment action. Spawn eggs may exist only for development, not survival progression.

## Commands

The officer's command baton cycles `FOLLOW` and `HOLD` when used in the air and applies targeted `ATTACK` when used on a valid hostile entity. Only soldiers owned by the issuing player and within the configured radius receive the order. Out-of-range and foreign soldiers remain unchanged.

## Authority and persistence

Commands run on the server. Owner, order, hold position, and target state are saved to entity data. Client rendering reflects vanilla-compatible humanoid roles; no speech-recognition dependency exists.

## Defaults

- command radius: 32 blocks, configurable from 4 to 128
- maximum soldiers per player: 16, configurable from 1 to 64
- V1 roles: foot soldier and archer; cavalry remains V2
