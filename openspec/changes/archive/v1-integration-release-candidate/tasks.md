# Tasks

## 1. Integration harness
- [x] 1.1 Add a NeoForge GameTest source/registration path that runs on the dedicated GameTest server
- [x] 1.2 Cover soldier save/load, invalid-target safety, and distinct melee/ranged behavior
- [x] 1.3 Cover command ownership/range/cap rejection using production authority rules
- [x] 1.4 Cover Camelot uniqueness and tournament-to-renown-to-role progression

## 2. Release artifact
- [x] 2.1 Add a deterministic audit for required classes, metadata, assets, data, and translations inside the built JAR
- [x] 2.2 Reject source atlases, development caches, and other non-release resources from the candidate JAR
- [x] 2.3 Record the release-candidate filename, byte size, and SHA-256 checksum

## 3. Verification and stabilization
- [x] 3.1 Run clean unit tests and fix regression-backed integration defects
- [x] 3.2 Run data generation and the dedicated GameTest server
- [x] 3.3 Run a bounded client smoke through full resource initialization
- [x] 3.4 Create a manual in-world checklist for visual, balance, and usability acceptance without claiming unperformed checks

## 4. Canonical completion
- [x] 4.1 Update verification, status, drift, report, and task-registry artifacts
- [x] 4.2 Pass `workflow/scripts/change-done.sh --change v1-integration-release-candidate`
