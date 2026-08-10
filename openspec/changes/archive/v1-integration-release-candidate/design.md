# Design: V1 integration release candidate

## Test layers

The stabilization pass uses three complementary layers:

1. Plain JUnit retains fast domain and resource-contract coverage.
2. NeoForge GameTests create real server worlds and entities for persistence, combat, authority, construction, and progression paths.
3. A release-artifact audit opens the produced JAR and validates the shipped manifest of classes, assets, data, and metadata.

Bounded `runClient` remains a startup/resource smoke test. It is not represented as proof of player interaction. A manual acceptance checklist explicitly records visual, balance, and usability checks separately from deterministic completion evidence.

## Integration scenarios

The suite prioritizes previously documented risk boundaries:

- owned soldier state survives serialization and load, and invalid targets clear safely
- foot soldiers and archers retain distinct combat behavior in a real server world
- command ownership/radius/cap rejection remains server-authoritative
- tournament completion awards renown and feeds role-transition eligibility
- Camelot construction is unique per world and retains its claimed location
- the packaged mod contains every V1/Arthurian public content surface without source atlases or development-only artifacts

Tests may use focused public adapters where direct Minecraft automation would be timing-sensitive, but each adapter must be called by production code or assert the same serialized/runtime boundary. Test-only utilities must not become a second gameplay implementation.

## Release candidate output

The canonical artifact is the versioned JAR produced by `./gradlew clean build`. Verification records its filename, size, and SHA-256 checksum. No GitHub release or public version publication is implied by this change.

## Failure handling

Any defect found during integration receives a regression test that fails before the fix and passes afterward. Broader feature requests are recorded for a later V2 OpenSpec change instead of expanding this stabilization scope.
