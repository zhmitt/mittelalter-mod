# Verification: fix-arrow-slit-stone-texture

- Completed: Arrow-slit model faces and particles use vanilla stone bricks; element geometry and state rotations are unchanged. Compiled client resources contain the corrected model.
- Focused checks: arrowSlitUsesMasonryForFacesAndParticles exits 1 before fix and passes after fix; FortificationContentTest and ArrowSlitGeometryTest plus build exit 0. release-artifact-audit.sh exits 0.
- Open gates: None; user confirmed the corrected appearance on 2026-10-01: "ja so ist gut" after the resource-reload instructions.
- Final gate: change-done.sh --change fix-arrow-slit-stone-texture exit 0 on 2026-10-01; evidence: `/tmp/mittelalter-arrow-slit-gate.log`.
- Next: Ready for archive; no implementation or acceptance gate remains.

Evidence: `/tmp/mittelalter-arrow-slit-before.log` and `/tmp/mittelalter-arrow-slit-after.log`. The previous `arrow_slit.png` is visibly a sword icon. The final model avoids that incorrect bitmap. Production JAR SHA-256: `6d1fecc9fa4e078bbaf497f920a394a6435e893e7d1219ed21f148bb52f3dfb1`.

Native UI control remains unable to select the Java app. Existing live-world shot/walking checks were user-confirmed before this material-only fix. No new asset generation or geometry changes were needed.

Follow-up observation (not in this fix): reinforced_stone and oak_palisade PNGs have the same checksum as the incorrect sword bitmap. Their presentation needs a separate bounded correction if requested.
