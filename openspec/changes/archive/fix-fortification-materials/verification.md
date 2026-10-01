# Verification
- Completed: Both block models use matching vanilla materials; geometry and gameplay unchanged.
- Focused checks: remainingFortificationsUseMatchingBuildingMaterials failed before correction, exit 1 (/tmp/mittelalter-materials-before.log); full Gradle test/build and release-artifact-audit.sh passed exit 0 (/tmp/mittelalter-materials-after.log, /tmp/mittelalter-materials-audit.log).
- Open gates: None for resource-reference acceptance. Live visual appearance of these two blocks is not claimed.
- Final gate: change-done.sh --change fix-fortification-materials exit 0; /tmp/mittelalter-materials-gate.log.
- Next: Archive and push.
