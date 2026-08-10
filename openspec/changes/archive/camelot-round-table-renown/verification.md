# Verification: Camelot, Round Table, and renown

## Scope verified

- Deterministic 15×15 great-hall plan with validation, fortified shell, gate, round-table chamber, standards, and NPC positions
- Arthurian-gated Camelot charter and persistent one-location-per-world state
- Bedivere mentor, Gawain ally, and Lancelot rival entity archetypes on shared soldier infrastructure
- Persistent per-player renown with UNKNOWN/RECOGNIZED/HONORED/ROUND_TABLE tiers
- Normal/champion tournament integration awarding 10/20 renown
- Charter resources, conditioned recipe, creative presentation, and English/German messages

## Automated evidence

- `./gradlew test --no-daemon` — exit 0
- `./gradlew clean build --no-daemon` — exit 0
- `CamelotPlanTest` — deterministic plan and invalid-site behavior
- `CamelotStateTest` — one-time location claim, all three dispositions, tier thresholds, and 10/20 award values
- `CamelotContentTest` — registry/resource/recipe/localization coverage
- Scoped `git diff --check` and resource JSON validation — exit 0

## Runtime evidence

- `./gradlew runData --no-daemon` — exit 0; SavedData codecs and entity registrations initialized
- Bounded `./gradlew runClient --no-daemon` reached full common/client setup and resource reload with court renderers registered; intentionally stopped after successful startup
- Runtime log: `run/logs/latest.log`

## Persistence evidence

- `CamelotSavedData` stores an optional `BlockPos` with `BlockPos.CODEC` in overworld data storage
- `RenownSavedData` stores UUID-to-points entries through a validated codec map and marks data dirty on awards
- Pure uniqueness and ledger state transitions have deterministic unit coverage

## Known limitations

- The V1 Camelot is a compact player-triggered landmark, not a full automatically generated city.
- Named knight dispositions are exposed archetypes; deep dialogue and political state changes remain later content.

## 2026-08-10 20:35:28

- Summary: Implemented singular Camelot construction, named Round Table knight archetypes, persistent renown tiers, and tournament renown integration with tests.
- Phase state: ready_for_verify
- Tasks complete: 10/10
- Evidence: Camelot plan/state/content tests pass; clean build and runData exit 0; bounded client initialization succeeded.

## 2026-08-10 20:35:28

- Summary: All ten Camelot, court, and renown tasks have plan, persistence, integration, test, runtime, and workflow evidence.
- Phase state: ready_for_archive
- Tasks complete: 10/10
- Completed: 10/10 tasks with unique-world location and persistent four-tier renown.
- Remaining: Final deterministic completion gate only.
- Evidence: openspec/changes/camelot-round-table-renown/verification.md; three Camelot test classes.
- Next: Execute workflow/scripts/change-done.sh for camelot-round-table-renown.
