# Report: fix-gametest-world-entry

- Change: fix-gametest-world-entry
- Completed: Native GameTest function registration replaces the mismatched adapter; runtime encode/decode regression and server tests pass.
- Focused checks: runGameTestServer exit 1 before correction and exit 0 after correction; build and release JAR audit exit 0. Exact logs and candidate identity are in openspec/changes/archive/fix-gametest-world-entry/verification.md.
- Open gates: None for the scoped fix. User performed GUI actions because native desktop control could not select Java; the client log confirms world login, foot and archer recruitment, and Hold/Follow orders.
- Next: Archived after change-done exit 0. User-reported combat and save/reload evidence are recorded in the archived verification note; this is not a full-product acceptance claim.
