# Verification: fix-gametest-world-entry

- State: ready_for_verify
- Completed: Replaced the mismatched codec adapter with native TEST_FUNCTION registration and FunctionGameTestInstance. Added encode/decode assertions over all four actual registry entries to soldier_state_round_trip.
- Focused checks: Before fix, runGameTestServer exit 1 with the historical ClassCastException reproduced; after fix, exit 0 and all four mod tests plus vanilla control pass. Gradle build and release-artifact-audit.sh exit 0. Client completes resources/audio startup.
- Open gates: None for the scoped world-entry fix; full combat, structure, and Arthurian playthrough remain outside this change's acceptance.
- Next: Run the canonical completion gate and preserve the verified correction.

## Evidence

- Before: `/tmp/mittelalter-codec-regression-before.log:21467`; dedicated server exit 1 on DirectGameTestInstance -> FunctionGameTestInstance cast.
- After: `/tmp/mittelalter-codec-regression-after.log:21225`; All 5 required tests passed, exit 0.
- Build: `/tmp/mittelalter-world-entry-build.log`; Gradle build exit 0.
- Client: `/tmp/mittelalter-world-entry-client.log`; successful setup, resource atlas creation and sound initialization. The early-display slow glfwInit warning is not a world-entry result.
- Artifact audit: 26 entries pass; 163572-byte RC JAR SHA-256 `dd80681749f96e6b4521dfaa0fc921029ef9a17d7eae7f6f251b55ece311dbc3`. Unchanged because GameTest classes are deliberately excluded from production packaging.

## Capability and preservation

The desktop inventory contains no selectable Minecraft app. CUA getApp rejects the reported Java app id, java display name, and verified JDK bundle id with Invalid app. No alternative OS input mechanism was used. Existing test worlds remain unchanged. Main started this client through Gradle and leaves it running for the user's world-entry step. All workers have finished; local code is preserved by a checkpoint commit, and no Done claim is made.

## Confirmed world entry — 2026-10-01

The user reports being inside Mittelalter Fix Test. The current client log
independently confirms Dev logged in and joined the game (lines 390 and 392).
No ClassCastException or Failed to handle packet appears in this run. The
EARLYDISPLAY entry is a slow GLFW initialization warning before client setup.
Basic recruitment and command interactions remain the next acceptance step.

## Recruitment and orders — 2026-10-01

The user reports recruitment and Follow/Hold working. Client chat independently
records four Foot Soldier recruits, Hold applied to four units, then Follow
applied to four units. Archer recruitment is not yet confirmed: the log records
the archer contract being given, but no Archer recruited message. Both entity
roles currently share the vanilla zombie texture in SoldierRenderer. Distinct
role recruitment and rendered equipment need the next focused check.

## Confirmed basic interactions — 2026-10-01

The user confirms the focused archer recruitment and bow check works. The
client log independently records Recruited Archer at lines 462, 463, and 469.
Together with the earlier user-confirmed movement/Hold/Follow and matching chat
messages, all scoped acceptance criteria are met. No live combat, regional
save/reload, fortification projectile, or Arthurian full-progression claim is made.

Final completion: `workflow/scripts/change-done.sh --change fix-gametest-world-entry`
exit 0; all phases pass. Log: `/tmp/mittelalter-fix-world-entry-gate.log`.
