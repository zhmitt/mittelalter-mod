# Report: v1-integration-release-candidate

## Summary

Hardened the integrated V1 gameplay wave with four dedicated NeoForge server GameTests, a direct production-JAR audit, corrected packaging exclusions, an RC version, and an honest manual acceptance boundary.

## Current state

- Phase state: ready_for_verify
- Tasks complete: 12/13

## Evidence

- Clean unit/build/data/GameTest pipeline exits 0; all 5 required GameTests pass.
- Bounded RC client reaches full resource and texture-atlas initialization.
- `release-artifact-audit.sh` exits 0 against 26 required entries and rejects development-only files.
- Candidate `mittelalter-0.1.0-rc.1.jar`: 163572 bytes; SHA-256 `dd80681749f96e6b4521dfaa0fc921029ef9a17d7eae7f6f251b55ece311dbc3`.
- JSON parsing and `git diff --check` exit 0.
- Manual visual/feel checklist exists and remains explicitly unchecked.

## Next step

- Run the deterministic completion gate, then archive the change if it passes.

## 2026-08-10 21:33:31

- Summary: Created v1-integration-release-candidate with GameTest, packaged-JAR audit, deterministic verification, and honest manual acceptance boundaries; GitHub issue #12 synced.
- Change: v1-integration-release-candidate
- Phase state: in_progress
- Tasks complete: 0/13
- Remaining: Complete the remaining tracked tasks.
- Next: Complete remaining tasks

## 2026-08-10 21:40:53

- Summary: V1 RC hardened with four dedicated GameTests, direct JAR audit, corrected packaging exclusions, RC metadata, checksum evidence, bounded client smoke, and explicit manual acceptance boundary.
- Change: v1-integration-release-candidate
- Phase state: in_progress
- Tasks complete: 12/13
- Next: Complete remaining tasks

## 2026-08-10 21:41:04

- Summary: All 13 RC tasks have implementation or evidence; deterministic pipeline, GameTests, artifact audit, and client smoke are complete, with manual visual checks explicitly deferred to release promotion.
- Change: v1-integration-release-candidate
- Phase state: ready_for_archive
- Tasks complete: 13/13
- Next: Archive the change into openspec/changes/archive/
