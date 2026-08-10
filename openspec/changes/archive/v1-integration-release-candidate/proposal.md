# Proposal: Harden the V1 integration and produce a release candidate

## Why

The first gameplay wave is implemented and its individual changes are archived, but the remaining evidence gaps are cross-system world behavior: soldier save/load and ranged combat, complete tournament-to-renown-to-role progression, Camelot uniqueness, and the contents of the distributable JAR. A dedicated stabilization change turns those known gaps into repeatable release evidence before V2 siege work begins.

## What changes

- Add server-side integration/GameTests for the highest-risk world and persistence paths
- Add a deterministic release-artifact audit that checks registrations, data, client assets, metadata, and accidental development-only files in the built JAR
- Exercise clean test, build, data generation, dedicated GameTest server, and bounded client startup from one documented release-candidate workflow
- Record a concise manual in-world acceptance checklist for visual and feel checks that cannot be proven headlessly
- Fix only defects exposed by this stabilization pass; new V2 gameplay remains out of scope

## Impact

Production behavior should remain unchanged unless an integration test exposes a defect. The change adds test infrastructure and release validation around existing public IDs and saved-state formats. It lives in canonical OpenSpec/workflow layers so the release claim is portable across Codex, Claude Code, Gemini CLI, and local developer runs.

## Portability and drift risks

GameTest APIs are tied to the current Minecraft/NeoForge version and can drift on upgrades. Assertions therefore target observable mod behavior and stable registry IDs rather than internal engine implementation details. The release audit inspects the actual JAR instead of assuming source resources were packaged.

## Workspace ownership

`primary workspace`; no worktree or branch handoff is planned.
