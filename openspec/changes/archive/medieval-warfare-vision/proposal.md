# Proposal: Establish the Mittelalter-Mod gameplay vision and phased scope

## Why

The repository has a working NeoForge skeleton and workflow baseline, but it does not yet have a canonical product direction for the actual mod. Without that layer, future implementation changes for weapons, blocks, mobs, world generation, or AI-controlled soldiers would be driven by scattered chat notes instead of a shared specification, and different contributors could optimize for different interpretations of "the medieval mod."

This change turns Henri's free-form gameplay description into a canonical OpenSpec baseline: a Vanilla+ medieval warfare sandbox with an optional fantasy layer, centered on player-commanded soldiers, fortifications, and period-flavored equipment. Capturing that direction in `openspec/` keeps the design portable across tools and reduces drift before real content implementation begins.

## What changes

- Add a canonical gameplay-direction change for the mod instead of leaving the concept in tool-local conversation history
- Define the product identity as a Minecraft-preserving medieval warfare sandbox, not a linear story campaign or a total conversion
- Split the concept into a grounded default profile and an optional fantasy profile
- Define the major capability pillars future implementation changes should align to:
  - medieval equipment and resources
  - player-commanded soldiers
  - fortifications and siege gameplay
  - curated world atmosphere and biome/worldgen direction
- Record a phased delivery model (`V1` / `V2` / `V3`) so implementation work can start with a realistic MVP instead of attempting every idea at once
- Explicitly flag ambiguous source terms and unresolved product questions that must be validated with Henri before implementation locks in names or mechanics

## Impact

- Future gameplay changes can be scoped against one canonical design brief instead of ad hoc recollection
- Reduces portability risk across Claude/Gemini/Codex by keeping the mod vision in OpenSpec rather than in tool-local memory
- Introduces a deliberate interpretation layer: some spoken terms from the source brief were ambiguous and are normalized here as best-fit medieval gameplay concepts until validated
- No player-facing behavior changes land yet; this is a planning and specification anchor for follow-up implementation changes
