# Report: fix-gametest-world-entry

- Change: fix-gametest-world-entry
- Completed: Native GameTest function registration replaces the mismatched adapter; runtime encode/decode regression and server tests pass.
- Focused checks: runGameTestServer exit 1 before correction and exit 0 after correction; build and release JAR audit exit 0. Exact logs and candidate identity are in openspec/changes/fix-gametest-world-entry/verification.md.
- Open gates: None for the scoped fix. User performed GUI actions because native desktop control could not select Java; the client log confirms world login, foot and archer recruitment, and Hold/Follow orders.
- Next: Run change-done once and preserve the correction. Full live combat and other gameplay systems remain a separate acceptance pass.
