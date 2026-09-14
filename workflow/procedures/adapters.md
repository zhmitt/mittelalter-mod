# Adapters procedure

Read only when the core names this trigger. Retained repository-specific details.

## Canonical workflow surface

Use these scripts as the operational API:

```bash
workflow/scripts/phase-status.sh
workflow/scripts/tasks-sync.sh
workflow/scripts/tasks-sync.sh --check
workflow/scripts/milestone-sync.sh --summary "..."
workflow/scripts/milestone-check.sh --staged --mode warn
workflow/scripts/post-impl-prepare.sh --summary "..."
workflow/scripts/post-impl-check.sh
workflow/scripts/session-close.sh --summary "..."
workflow/scripts/worktree-doctor.sh
workflow/scripts/worktree-doctor.sh --branch <branch>
workflow/scripts/worktree-close.sh --summary "..."
workflow/scripts/worktree-close.sh --summary "..." --remove --change <change-id>
```

## Tool adapter policy

### Claude Code

- Slash commands, subagents, and hooks are allowed.
- They are accelerators only.
- They must delegate to `openspec/` and `workflow/scripts/`.

### Gemini CLI

- Commands, skills, and subagents are allowed.
- Workspace-level Gemini subagents may be enabled in `.gemini/settings.json`.
- They must mirror the same workflow surface.
- They must not define exclusive process rules.

### Codex

- Repo-local skills are the primary Codex integration.
- Codex app multi-agent workflows should be used for non-trivial work when the task can be isolated cleanly.
- Prefer `explorer` agents for read-only investigation, spec/doc/codebase search, and root-cause analysis.
- Prefer `worker` agents for bounded implementation, targeted tests, verification, and documentation updates with explicit ownership and disjoint write sets.
- Optional global prompts are convenience only.
- Global prompts must be generated from versioned repo sources.
