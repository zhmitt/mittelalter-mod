# Verification: V1 integration release candidate

## Scope verified

- NeoForge GameTest registration is discovered by the dedicated server
- Soldier NBT round-trip, invalid-target fallback, melee/ranged distinction, and projectile creation
- Command ownership, radius, and recruitment-cap authority boundaries
- Camelot world uniqueness and tournament-to-renown-to-Arthur progression using real server SavedData
- Release artifact contents, exact RC metadata version, and absence of caches, source atlases, source files, and GameTest classes
- Clean unit tests, build, data generation, dedicated GameTest server, JSON parsing, diff hygiene, and bounded client resource initialization

## Deterministic evidence

- `JAVA_HOME=/Users/mschmitt/.codex/runtimes/jdk-21.0.11+10/Contents/Home ./gradlew clean test build runData runGameTestServer --no-daemon` — exit 0
- GameTest server — all 5 required tests passed: four Mittelalter integration tests plus the vanilla control test
- `workflow/scripts/release-artifact-audit.sh` — exit 0; 26 required entries checked
- `find src/main/resources -name '*.json' -print0 | xargs -0 -n1 jq empty` — exit 0
- `git diff --check` — exit 0

## Release candidate

- Version matrix: Minecraft 26.1.2, NeoForge 26.1.2.78, Java toolchain 25
- Artifact: `build/libs/mittelalter-0.1.0-rc.1.jar`
- Byte size: `163572`
- SHA-256: `dd80681749f96e6b4521dfaa0fc921029ef9a17d7eae7f6f251b55ece311dbc3`
- Production JAR excludes `.cache`, source artwork, `.bbmodel`, source/test files, and `de/mittelalter/gametest/**`

## Runtime smoke

- `./gradlew runClient --no-daemon` loaded Mittelalter `0.1.0-rc.1`, completed common/client setup, reloaded all resources, started OpenAL, and created block/item/GUI atlases.
- The bounded smoke was intentionally stopped with Ctrl-C after successful initialization (exit 130).

## Defects found and corrected

- The first artifact audit failed because `.cache` entries and both generated source atlases were packaged. Resource exclusion patterns were corrected; the final JAR audit passes.
- Release hygiene review found the development GameTest class in the first integrated JAR. JAR assembly now excludes it, and the audit independently rejects future leakage while the development classpath retains it.
- The first dedicated server test run exposed a mock-player coordinate fixture issue. Players are now snapped to the spawned soldier; the unchanged production authority rule passes on rerun.

## Manual acceptance boundary

`manual-acceptance.md` contains the fresh-world visual, balance, and usability checklist. Those boxes remain deliberately unchecked: bounded startup and deterministic server tests do not prove natural ore discovery, rendered presentation, or player-input feel. This artifact is therefore an RC, not a promoted public final release.

## Known limitations

- Entity persistence crosses the engine NBT boundary through `Entity.restoreFrom`; it does not restart a separate server process and reload a region file.
- The checksum was produced on the current macOS/JDK environment and was not compared across machines.
- No GitHub Release was created by this change.

## 2026-08-10 21:33:31

- Summary: Created v1-integration-release-candidate with GameTest, packaged-JAR audit, deterministic verification, and honest manual acceptance boundaries; GitHub issue #12 synced.
- Phase state: in_progress
- Tasks complete: 0/13
- Remaining: Complete the remaining tracked tasks.
- Next: Complete remaining tasks

## 2026-08-10 21:40:53

- Summary: V1 RC hardened with four dedicated GameTests, direct JAR audit, corrected packaging exclusions, RC metadata, checksum evidence, bounded client smoke, and explicit manual acceptance boundary.
- Phase state: in_progress
- Tasks complete: 12/13

## 2026-08-10 21:41:04

- Summary: All 13 RC tasks have implementation or evidence; deterministic pipeline, GameTests, artifact audit, and client smoke are complete, with manual visual checks explicitly deferred to release promotion.
- Phase state: ready_for_archive
- Tasks complete: 13/13
