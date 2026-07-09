# Proposal: Bootstrap the Mittelalter-Mod repository

## Why

The Mittelalter-Mod needs a consistent development workflow from day one because multiple people will contribute across different AI tools (Claude Code, Gemini CLI, Codex), and a concrete NeoForge project skeleton to build content on top of. Building this ad hoc, change by change, would risk process drift (canonical rules ending up only in tool-specific files) and an inconsistent Gradle/mod setup.

This repository adopts the hybrid workflow baseline from `app.dev-template` (governance in `AGENTS.md`, specs in `openspec/`, deterministic checks in `workflow/`, thin tool adapters) and pairs it with a NeoForge 26.1.2 Gradle mod skeleton, so the first real content change (a block, an item, a mob, ...) can start from a working, spec-governed baseline instead of an empty repository.

## What changes

- Add the canonical governance layer: `AGENTS.md`, `openspec/config.yaml`, inherited workflow specs under `openspec/specs/`
- Add the canonical execution layer: `workflow/scripts/` (deterministic checks) and fresh `workflow/state/` (no template history carried over)
- Add thin tool adapters: `.claude/`, `.gemini/`, `.codex/` (all three, since contributors may use different tools)
- Add a NeoForge 26.1.2 Gradle mod skeleton: `build.gradle`, `settings.gradle`, `gradle.properties` (mod id `mittelalter`), Gradle wrapper, a minimal mod entrypoint (`MittelalterMod`, `MittelalterModClient`, `Config`) with one placeholder block/item exercising the registry and creative-tab wiring end to end
- Add a `spec-drift` + `tasks-consistency` GitHub Actions gate (`.github/workflows/openspec-gate.yml`) and a Gradle build CI workflow (`.github/workflows/gradle-build.yml`)
- Initialize the git repository and a public GitHub remote (`zhmitt/mittelalter-mod`)

## Impact

- Establishes the repository structure every future change (new blocks, items, mobs, mechanics, world-gen, ...) builds on
- All non-trivial future changes must go through `openspec/changes/<change-id>/` per `AGENTS.md`
- No prior application code or specs exist yet, so this change has no migration/compatibility concerns
