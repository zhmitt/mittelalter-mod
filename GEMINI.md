# Gemini CLI Adapter

Use this file only as a thin entrypoint.

Canonical project rules live in:

- `AGENTS.md`
- `openspec/`
- `workflow/`

When working in Gemini CLI:

1. read `AGENTS.md`
2. inspect relevant artifacts in `openspec/`
3. use `.gemini/commands/opsx/`, `.gemini/skills/`, and `.gemini/agents/` only as adapters
4. prefer Gemini subagents such as `@opsx-implementer` or `@opsx-verifier` for bounded work when that keeps the main agent focused
5. use `workflow/scripts/` for deterministic checks and state updates

This repository explicitly enables Gemini subagents at the workspace level in `.gemini/settings.json`.

Do not treat this file as the source of process truth.
