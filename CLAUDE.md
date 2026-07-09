# Claude Code Adapter

Use this file only as a thin entrypoint.

Canonical project rules live in:

- `AGENTS.md`
- `openspec/`
- `workflow/`

When working in Claude Code:

1. read `AGENTS.md`
2. inspect relevant artifacts in `openspec/`
3. use `.claude/commands/opsx/` or `.claude/agents/` only as adapters
4. use `workflow/scripts/` for deterministic checks and state updates

Do not treat this file as the source of process truth.

## Done-Disziplin und Volume-Falle

Die "Volume-Falle": Viele Changes parallel implementiert, einige fälschlich
als done gemeldet (Util geschrieben, nirgends importiert; Schema verschärft,
nicht aktiviert; re-exports vergessen; Script fehlt komplett). Typecheck +
Test blieben grün, weil sie den ungetesteten Pfad nicht testen.

Die Sections "Definition of Done", "Parallel Work Limit" und "Sub-Agent
Output Format" in `AGENTS.md` sind verbindlich, nicht optional.

**Vor jedem `done`/`fertig`/`implementiert`/`deployed`-Claim:**
`workflow/scripts/change-done.sh --change <id>` exit 0 prüfen.

**Bei Mass-Sweeps (>3 parallele Changes):** nach jeweils 3 Changes
`change-done.sh`-Gate, bevor der nächste Batch startet.
