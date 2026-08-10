# Proposal: Implement V1 medieval equipment and resource lines

## Why

The gameplay vision names medieval equipment as the first player-facing content slice. Implementing one cohesive vertical slice establishes real registry, asset, recipe, data-generation, and verification patterns before soldier AI and world generation add more complexity.

## What changes

- Add craftable longsword, poleaxe, and halberd weapons
- Add silver and ruby items plus mineable ore/storage blocks
- Give ruby a ceremonial/officer role rather than a generic combat tier
- Give silver a distinct foundation for later anti-fantasy interactions
- Replace the bootstrap placeholders in the creative presentation with real content
- Add deterministic tests and a client/data smoke-test record

## Impact

This is the first gameplay implementation. Registry identifiers and recipes become player-facing compatibility surfaces. The implementation remains tool-neutral and lives in normal NeoForge source/resources; OpenSpec and workflow evidence remain canonical.

## Workspace ownership

`primary workspace`; no worktree handoff is planned.
