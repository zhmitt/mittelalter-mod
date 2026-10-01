# Verification: fix-gametest-world-entry

- State: user_action_pending
- Completed: Replaced the mismatched codec adapter with native TEST_FUNCTION registration and FunctionGameTestInstance. Added encode/decode assertions over all four actual registry entries to soldier_state_round_trip.
- Focused checks: Before fix, runGameTestServer exit 1 with the historical ClassCastException reproduced; after fix, exit 0 and all four mod tests plus vanilla control pass. Gradle build and release-artifact-audit.sh exit 0. Client completes resources/audio startup.
- Open gates: Actual client world entry and item interactions remain unverified because native desktop control rejects the running Java client as Invalid app.
- Next: In the already-running Minecraft window, create a new Creative world named Mittelalter Fix Test 2026-10-01 with commands enabled. Report whether the terrain and hotbar appear; Main will inspect the login log and continue interaction checks.

## Evidence

- Before: `/tmp/mittelalter-codec-regression-before.log:21467`; dedicated server exit 1 on DirectGameTestInstance -> FunctionGameTestInstance cast.
- After: `/tmp/mittelalter-codec-regression-after.log:21225`; All 5 required tests passed, exit 0.
- Build: `/tmp/mittelalter-world-entry-build.log`; Gradle build exit 0.
- Client: `/tmp/mittelalter-world-entry-client.log`; successful setup, resource atlas creation and sound initialization. The early-display slow glfwInit warning is not a world-entry result.
- Artifact audit: 26 entries pass; 163572-byte RC JAR SHA-256 `dd80681749f96e6b4521dfaa0fc921029ef9a17d7eae7f6f251b55ece311dbc3`. Unchanged because GameTest classes are deliberately excluded from production packaging.

## Capability and preservation

The desktop inventory contains no selectable Minecraft app. CUA getApp rejects the reported Java app id, java display name, and verified JDK bundle id with Invalid app. No alternative OS input mechanism was used. Existing test worlds remain unchanged. Main started this client through Gradle and leaves it running for the user's world-entry step. All workers have finished; local code is preserved by a checkpoint commit, and no Done claim is made.
