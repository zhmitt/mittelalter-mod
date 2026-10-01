# Fortification material restoration

- Change: fix-fortification-materials
- Completed: reinforced stone uses vanilla stone bricks;
oak palisades use oak-log sides and end grain via cube_column. Geometry,
registries, recipes, loot, and collision remain unchanged. No new mechanic or
design is introduced. A compact change records the resource restoration because
the native commit gate requires an active Change-Id.

Regression: remainingFortificationsUseMatchingBuildingMaterials failed before
the fix (exit 1, /tmp/mittelalter-materials-before.log). Full Gradle test/build
passed exit 0 (/tmp/mittelalter-materials-after.log), as did release-artifact-audit.sh
(/tmp/mittelalter-materials-audit.log). Live visual verification of these two
blocks is not claimed. Next: reload client resources with F3+T and view the blocks.

- Open gates: None for resource-reference acceptance.
- Next: Complete the scoped gate, archive and push.
