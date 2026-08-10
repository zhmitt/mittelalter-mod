# Verification: V1 fortifications

## Scope verified

- Oak palisade with wood mining behavior and early recipe
- Reinforced stone with iron-tier drop requirement, higher hardness/blast resistance, and no global vanilla-stone mutation
- Horizontal arrow-slit placement with north/south and east/west frame geometry
- Matching directional blockstate/model rotations
- Creative-tab, localization, item model, texture, recipe, loot, and mining-tag coverage

## Automated evidence

- `JAVA_HOME=/Users/mschmitt/.codex/runtimes/jdk-21.0.11+10/Contents/Home ./gradlew build` — exit 0
- `ArrowSlitGeometryTest` — central projectile channel open along both axes and surrounding frame covered
- `FortificationContentTest` — registry/resource/data manifest coverage passed
- `find src/main/resources -name '*.json' -print0 | xargs -0 -n1 jq empty` — exit 0

## Runtime evidence

- `./gradlew runData` — exit 0 after correcting an invalid oak-log property-copy attempt to an oak-planks baseline
- Bounded `./gradlew runClient` — complete resource reload and block/item atlas construction, no missing Mittelalter resource errors; intentionally stopped after successful startup
- Runtime log: `run/logs/latest.log`

## Geometry evidence

The 16×16 frame leaves an open channel from x=6..10 and y=5..11 for north/south orientation, with a rotated equivalent for east/west orientation. Four surrounding collision boxes provide cover without filling the firing channel.

## Known limitations

- Automated geometry proves the projectile channel; a manual in-world projectile screenshot/recording remains a future visual-polish check rather than a blocker to deterministic V1 behavior.

## 2026-08-10 19:40:58

- Summary: Implemented V1 palisade, reinforced-stone progression, and a directional functional arrow-slit block with complete resources and deterministic geometry tests.
- Phase state: ready_for_verify
- Tasks complete: 9/9
- Evidence: ArrowSlitGeometryTest and FortificationContentTest pass; build and runData exit 0; bounded client resource reload succeeded.

## 2026-08-10 19:40:59

- Summary: All nine fortification tasks have code, data, geometry tests, runtime initialization, and verification evidence.
- Phase state: ready_for_archive
- Tasks complete: 9/9
- Completed: 9/9 tasks including directional model/collision parity and scoped iron-tier progression.
- Remaining: Final deterministic completion gate only.
- Evidence: openspec/changes/v1-fortifications/verification.md; ArrowSlitGeometryTest; FortificationContentTest.
- Next: Execute workflow/scripts/change-done.sh for v1-fortifications.
