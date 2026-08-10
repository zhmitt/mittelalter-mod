# Verification: V1 soldier command MVP

## Scope verified

- Foot and archer soldier entity registrations with distinct melee/ranged goals and equipment
- Persistent owner UUID, command order, hold position, and attack-target UUID
- Server-side recruitment contract cap enforcement across loaded server entities
- Server-side command radius and ownership filtering
- `FOLLOW`, `HOLD`, and hostile targeted `ATTACK` command paths
- Common configuration defaults: radius 32, cap 16, with bounded ranges
- Client renderer registration, items, recipes, models, and English/German localization

## Automated evidence

- `JAVA_HOME=/Users/mschmitt/.codex/runtimes/jdk-21.0.11+10/Contents/Home ./gradlew clean test build --no-daemon` — exit 0
- `SoldierRulesTest` — 4 tests cover ownership/range boundary, cap rejection, role distinction, and safe order-name round trips
- Existing `ContentManifestTest` — 3 tests remain green
- `find src/main/resources -name '*.json' -print0 | xargs -0 -n1 jq empty` — exit 0
- Scoped `git diff --check -- src/main/java src/main/resources src/test/java build.gradle` — exit 0

## Runtime evidence

- `./gradlew runData` after final AI review — exit 0; entity registries, config, renderer subscriber discovery, and mod initialization completed
- Bounded `./gradlew runClient` — full Mittelalter resource reload reached the title screen with both soldier renderers registered; intentionally stopped after successful startup
- Runtime log: `run/logs/latest.log`

## Review correction

Review found that Mob goal registration can occur before the `SoldierEntity` role field is assigned. Archer goal selection now uses the concrete `Archer` subtype during construction, preventing accidental melee-goal registration. A clean build and data run passed after the correction.

## Known limitations

- The renderer intentionally reuses the vanilla zombie model/texture as development-safe presentation.
- Automated domain tests and runtime initialization cover the V1 contract; a future GameTest suite should add full world save/reload and projectile-hit scenarios.

## 2026-08-10 19:18:31

- Summary: Implemented the V1 server-authoritative soldier command system with foot and archer roles, recruitment, ownership, bounded commands, persistence, configuration, assets, and tests.
- Phase state: ready_for_verify
- Tasks complete: 10/10
- Evidence: SoldierRulesTest 4/4 and ContentManifestTest 3/3; clean build exit 0; runData exit 0; bounded runClient reached successful resource reload.

## 2026-08-10 19:18:32

- Summary: All soldier command MVP tasks have code, resource, test, review, and runtime initialization evidence.
- Phase state: ready_for_archive
- Tasks complete: 10/10
- Completed: 10/10 tasks; AI construction-order defect corrected during review; verification artifact present.
- Remaining: Final deterministic completion gate only.
- Evidence: openspec/changes/v1-soldier-command-mvp/verification.md; SoldierRulesTest; clean build and runData exit 0.
- Next: Execute workflow/scripts/change-done.sh for this change.
