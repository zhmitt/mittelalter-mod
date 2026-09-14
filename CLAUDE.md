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

Die in `AGENTS.md` ausgelösten Verfahren unter `workflow/procedures/`
sind verbindlich: verification (Done), preservation (Writing lane admission)
und delegation (Sub-Agent Output Format).

**Vor einem finalen `done`/`fertig`-Claim:**
`workflow/scripts/change-done.sh --change <id>` exit 0 prüfen.

Implementierungs- und Deploy-Zwischenstände nennen ihre konkrete Evidence,
sind aber keine Done-Claims. Normale Breadcrumbs benötigen keine Abschluss-
zeremonie. Arbeitskontext und Scheduling folgen ausschließlich AGENTS.md.
