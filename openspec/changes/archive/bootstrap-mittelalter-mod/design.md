# Design: Mittelalter-Mod repository bootstrap

## Context

Two concerns need to be resolved together for the first commit: the collaboration workflow (multiple people, multiple AI tools) and the concrete mod stack (NeoForge/Gradle/Java). Neither is useful alone — a workflow without a real stack has nothing to govern, and a mod skeleton without shared process rules will drift once more than one contributor touches it.

## Decisions

### Decision 1: Inherit the workflow wholesale from `app.dev-template`, not reinvent it
`AGENTS.md`, `openspec/` mechanics, and `workflow/scripts/` are copied from the already-proven `app.dev-template` baseline rather than designed from scratch. Only the Definition-of-Done evidence table is adapted (Gradle commands instead of `pnpm`), since the scripts themselves are already tool/stack-neutral (bash + python3).

### Decision 2: Reset operational state, keep specification state
`workflow/state/*` (status log, task registry, next-session) starts empty — it is the template's own operational history, not ours. `openspec/specs/*` (session-handover, task-registry, workflow-governance, multi-tool-adapters, milestone-sync, launch-readiness, post-implementation-verification, workspace-governance) is kept as-is, because those describe the workflow system itself, which this repository uses unchanged.

### Decision 3: Include all three tool adapters
Contributors may use Claude Code, Gemini CLI, or Codex. Keeping all three adapters avoids a two-tier contributor experience where only one tool has first-class support.

### Decision 4: NeoForge over Fabric/Forge, version pinned to the latest stable Maven release
NeoForge is the dominant ecosystem for content-heavy mods in 2026 (large mod compatibility, capability system, JEI integration). The exact version (`26.1.2`, NeoForge build `26.1.2.78`) was taken from `https://maven.neoforged.net/releases/net/neoforged/neoforge/maven-metadata.xml` (`<release>` tag) rather than assumed, since blog sources disagreed with the actual latest stable Maven artifact.

### Decision 5: Minimal placeholder content, not real game design
The mod skeleton includes one placeholder block/item (mirroring the official NeoForge MDK) purely to prove the registry/creative-tab/build pipeline works end to end. Actual medieval content (blocks, items, mobs, mechanics) is out of scope for this change and belongs in dedicated follow-up OpenSpec changes.

## Trade-offs

- The workflow's hook-gate system (`.claude/hooks/gate-edit.sh`) requires a `Plan`/`Explore` subagent to have run before editing code files; this is inherited as-is and applies to this repository too.
- NeoForge 26.1.2 requires JDK 25; contributors need that toolchain installed (or use Gradle's toolchain auto-provisioning where available).
- No license was chosen yet (`mod_license=All Rights Reserved` placeholder in `gradle.properties`) — a follow-up decision, not blocking the skeleton.

## Workspace ownership

`primary workspace` — this is the initial commit to the top-level checkout, no worktree or branch handoff involved.
