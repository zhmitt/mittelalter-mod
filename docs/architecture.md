# Architecture Overview

## Intent

This repository combines a NeoForge Minecraft mod with a reusable hybrid workflow base inherited from `app.dev-template`:

- OpenSpec for change and specification management
- deterministic shell scripts for execution-state management
- thin adapters for Codex, Claude Code, and Gemini CLI

The goal is a consistent, multi-contributor workflow on top of a concrete stack (NeoForge + Gradle + Java), not a generic starter.

## Architectural layers

### 1. Governance layer

`AGENTS.md` defines the operating contract.

It answers:

- what is canonical
- when changes need specs
- which evidence is required before completion
- how tool-specific adapters are allowed to behave

### 2. Specification layer

`openspec/` is the source of truth for system behavior and changes.

- `openspec/specs/` contains current, agreed behavior — both inherited workflow specs and mod-specific specs (blocks, items, mobs, mechanics)
- `openspec/changes/` contains active work
- `openspec/changes/archive/` contains historical context
- `openspec/config.yaml` injects project context and artifact-specific rules

### 3. Execution layer

`workflow/` contains operational behavior that should remain stable across tools.

- `workflow/scripts/` is the machine-facing API
- `workflow/scripts/milestone-sync.sh` records meaningful implementation checkpoints in canonical state
- `workflow/scripts/milestone-check.sh` detects implementation drift against canonical milestone artifacts
- `workflow/scripts/post-impl-prepare.sh` scaffolds canonical evidence before final verification
- `workflow/state/` stores operational state and evidence

This layer provides:

- status detection
- task aggregation
- milestone checkpointing
- post-implementation gating
- session handover

### 4. Tool adapter layer

Adapters provide native ergonomics without owning the process.

- `.claude/` offers commands, subagents, and optional hooks
- `.gemini/` offers commands, skills, and subagents
- `.codex/` offers skills and optional prompt sources

Adapter responsibilities:

- help a tool discover the canonical workflow
- improve ergonomics for a specific CLI
- preserve native strengths such as Claude subagent delegation
- preserve native strengths such as Gemini subagent delegation

Adapter non-responsibilities:

- define exclusive process rules
- store the only copy of governance logic
- maintain hidden state that other tools cannot interpret

### 5. Mod stack

- **Modloader:** NeoForge
- **Language/Build:** Java, Gradle
- **Source layout:** standard Gradle mod layout under `src/main/java` and `src/main/resources`

## Key design decisions

### OpenSpec-first, not ad-hoc feature branches

OpenSpec provides a cleaner split between:

- current behavior
- proposed changes
- archived history

That structure scales better than branch- or feature-folder-driven spec trees, especially with multiple contributors adding content concurrently (items, blocks, mobs, mechanics).

### Workflow scripts remain first-class

OpenSpec is strong at specs and change history. It is weaker at deterministic operational state.

The `workflow/` layer fills that gap with scripts that any tool can execute.

### Claude remains powerful, but not authoritative

Claude Code can still use:

- slash commands
- subagents
- hooks

Those remain useful because they preserve main-agent context and reduce orchestration overhead. They are intentionally kept as accelerators, not the law.

### Codex global prompts are optional only

Codex prompt files are user-global, not repo-local. That makes them unsuitable as canonical repo state.

This repository versions prompt sources and provides an installer script, but the canonical behavior remains in repo-local skills and shared scripts.

## Known limits

- Some native tool features cannot be represented perfectly across all three CLIs.
- Teams still need discipline to prevent process drift back into tool-specific files.
