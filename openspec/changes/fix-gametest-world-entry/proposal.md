# Fix GameTest registry synchronization on world entry

## Outcome
The development Minecraft client can enter a local test world while the mod's dedicated GameTests remain executable.

## Acceptance
- Registered mod GameTests serialize with the correct codec without a ClassCastException.
- All dedicated mod GameTests pass after the correction.
- A real client enters a fresh local test world and basic mod items/interactions can be exercised.

## Non-goals
New gameplay, public release publication, workflow cleanup, and exhaustive balance certification.

## Decision
Replace the mismatched programmatic test adapter with a supported serializable registration. Keep the existing production-JAR exclusion.

## Next action
Reproduce registry encoding failure, correct registration, then run server tests and client world entry.
