# AGENTS.md - Hybrid Workflow Contract

This repository is the Mittelalter-Mod, a Minecraft Java Edition content mod built on NeoForge. It uses one development workflow across Codex, Claude Code, and Gemini CLI so multiple contributors stay consistent regardless of which tool they use.

This workflow is inherited from the `app.dev-template` baseline. Shared workflow policy changes should generally be proposed upstream first; this file may add product-specific constraints for the mod itself.

## Canonical sources

The only canonical sources of process truth are:

1. `AGENTS.md`
2. `openspec/`
3. `workflow/`

The following are adapters only and must never become the only place where a rule exists:

- `.claude/`
- `.gemini/`
- `.codex/`
- `CLAUDE.md`
- `GEMINI.md`

## Core rules

1. Non-trivial changes are spec-first.
2. Current system behavior lives in `openspec/specs/`.
3. Proposed work lives in `openspec/changes/<change-id>/`.
4. Operational state lives in `workflow/state/`.
5. Deterministic checks live in `workflow/scripts/`.
6. Do not treat tool-specific files as canonical governance.

## Spec-first policy

Skip formal change scaffolding only for:

- typo-only changes
- comment-only changes
- tiny bug fixes that restore intended behavior without changing system design

Everything else (new items, blocks, mobs, mechanics, world-gen, etc.) should create or update an OpenSpec change.

## Delegation policy

Main agents should keep orchestration context clean and actively evaluate delegation for every non-trivial block.

- Use specialized agents or subagents for implementation, review, testing, verification, documentation, and bounded diagnosis when the task can be isolated cleanly.
- In Codex, prefer `explorer` agents for read-only codebase research, root-cause analysis, log/doc inspection, and spec reconciliation.
- In Codex, prefer `worker` agents for bounded implementation, targeted test runs, verification, and documentation updates with explicit ownership and disjoint write sets.
- Run agents in parallel when the next local step is not blocked and the scopes do not overlap; keep urgent blocking work local.
- The main agent remains the tech lead/orchestration layer and owns architecture decisions, canonical workflow updates, and final integration unless explicitly delegated.

All workers must:

1. read canonical artifacts first
2. treat legacy layers as reference only
3. report back into canonical artifacts or concise summaries

## Workspace ownership and branch handoff

Every active line of work must use one explicit workspace mode at a time:

- `tool-managed workspace`
  - The chat or app owns the branch/worktree lifecycle.
  - Hidden tool worktrees or detached thread state may exist.
  - Prefer this mode when an existing chat should continue.
- `manual git worktree`
  - A human or script owns `git worktree add` and the branch checkout.
  - Open a new chat in that exact folder instead of rebinding an older chat that still points somewhere else.
- `primary workspace`
  - The top-level checkout is the default stable repo entrypoint.
  - Do not treat it as a surprise branch-rebind target when other workspace modes are active.

Rules:

1. One branch, one ownership mode at a time.
2. If an existing chat should continue, let the tool manage that branch and do not create a parallel manual worktree for it.
3. If a manual git worktree is created, continue the work in a new chat opened in that exact workspace path.
4. Before freeing a branch from a manual worktree back to a tool-managed workspace, create a checkpoint commit or an explicit stash first. Prefer a pushed checkpoint when the work is important or the app behavior is uncertain.
5. Handoffs between tools or workspaces must record the change id when present, branch name, commit hash or stash reference, current workspace mode, and intended target workspace or tool.
6. Run `workflow/scripts/workspace-status.sh` before branch/worktree handoffs when there is any doubt about current ownership.

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

## Canonical evidence requirements

Before a change is considered ready to archive:

- all tasks in `tasks.md` are complete
- a `verification.md` note exists in the change folder
- `workflow/state/status.md` contains an entry for the change
- a report exists in `workflow/state/reports/`

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

## Definition of Done

**Done = `workflow/scripts/change-done.sh --change <id>` exit 0. Nichts anderes
zaehlt als Done.**

`./gradlew build` + `./gradlew test` ist ausschliesslich Beweis fuer
"kompiliert + bestehende Tests gruen" -- kein Beweis fuer "Spec erfuellt"
oder "Implementation korrekt".

### Beweis-Typen pro Change-Typ

| Change-Typ | Pflicht-Beweis |
|---|---|
| **Bug-Fix** | Failing-Test (vor Fix) -> Passing-Test (nach Fix). Test-Name muss in Commit-Message oder Report stehen. |
| **Feature** | Smoke-Test (`./gradlew runClient`/`runData` starten, Item/Block im Spiel zeigen, Test-Output zeigen) + Test-Datei oder Recording. |
| **Refactor** | Vor/Nach-Inventur identisch. Public-API (Registries, Events, Capabilities) gleich. Test-Snapshot-Diff = leer. |
| **Migration** | Sample-Vergleich alt vs neu (z. B. Datapack/Recipe-Vergleich). Beweis: Diff-Output. |
| **Security-Hardening** | Penetrations-Versuch (curl/script gegen Server-Endpunkt) muss failen. Beweis: failing-Output protokolliert. |
| **Performance-Optimierung** | Vor/Nach-Messung (z. B. TPS/MSPT) mit definiertem Threshold. Beweis: Zahlen vor/nach. |
| **Doc-Update** | Cross-Reference-Check (Doc claimt X exists -> grep findet X). Beweis: grep-Output. |
| **Spec/Workflow-Change** | Selbst-Anwendbarkeit (das neue Workflow auf einen Sample-Change angewendet -> gruen). |

Verification-Pipeline: `workflow/scripts/change-done.sh`

### Historischer Cutoff

Die `change-done.sh`-Pflicht gilt ab dem ersten Change dieses Repositories
(`bootstrap-mittelalter-mod`). Kein `done`-Claim ohne
`change-done.sh --change <id>` exit 0.

## Parallel Work Limit

**Max 3 unverifizierte Claims gleichzeitig in Flight.**

"Unverifiziert" bedeutet: noch kein `workflow/scripts/change-done.sh --change
<id>` exit 0.

Vor dem 4. parallelen Claim muessen fuer die ersten 3 `change-done.sh`-Gates
abgeschlossen sein.

**Dokumentierter Failure-Mode:** Audit-Sweep mit vielen parallelen Changes
ohne Pro-Change-Gate fuehrt zu falsch-als-done gemeldeten Implementations.
Drift wird erst spaeter entdeckt. Beispiele: Util geschrieben aber nicht
importiert; Registry-Eintrag erstellt aber nicht registriert; Loot-Table
geaendert aber nicht referenziert; re-exports vergessen. Typecheck + Tests
blieben durchgehend gruen.

Diese Regel gilt unabhaengig vom Tool-Author -- Claude, Codex, Gemini. Die
Volume-Falle ist tool-unabhaengig.

## Sub-Agent Output Format

Jede Sub-Agent-Antwort, die Claim-Verben nutzt ("implemented", "fixed",
"done", "complete", "tested", "deployed", "verified", "ready"), **muss** einen
Evidence-Block am Ende der Antwort enthalten:

```
Implemented:
- <file:lines> -- <was konkret>
Skipped/Out-of-Scope:
- <reason>
Verified:
- <command> exit <code>
- <test-name>: result
NOT verified:
- <was wurde NICHT geprueft, woran koennte gelogen worden sein>
Drift-risk:
- <Util geschrieben aber nicht importiert? Test geschrieben aber nicht
   ausgefuehrt? Refactor angekuendigt aber Caller nicht migriert?>
```

Tech-Lead-Regel: Sub-Agent-Antwort ohne diesen Block = nicht akzeptiert.

## Sub-agent failsafe

### Hook-Failsafe-System

Der Sub-Agent- und Workflow-Einsatz wird durch ein dreistufiges
Hook-System hart erzwungen (Implementierung: `.claude/hooks/` und
`workflow/scripts/agent-evidence-check.sh`):

1. **Gate 1 — PreToolUse-Block auf Edit/Write/MultiEdit/NotebookEdit**
   (`.claude/hooks/gate-edit.sh`). Blockiert Edits an Code-Dateien
   (u. a. `.java/.py/.sh/.rs/.go/…`), wenn in den letzten 4 h kein
   `Plan`- oder `Explore`-Sub-Agent gelaufen ist. Whitelist (keine
   Sperre): `*.md`, `*.json/yml/yaml/toml`, `openspec/`, `workflow/`,
   `docs/`, `.claude/`, `.git-hooks/`, `.workflow-evidence/`, Dotfiles.
2. **Gate 2 — PreToolUse-Block auf Bash**
   (`.claude/hooks/gate-bash.sh`). Blockiert `git commit --no-verify`,
   `git commit -n`, `git push --no-verify`, `git -c core.hooksPath=…`
   und `--no-gpg-sign`.
3. **Gate 3 — Pre-Commit-Advisory**
   (`workflow/scripts/agent-evidence-check.sh`). Warnt (nicht-blockierend)
   wenn beim Commit von Code-Dateien kein `test-runner`-Sub-Agent in den
   letzten 30 min lief, und zeigt `CLAUDE_HOOKS_OFF`-Bypasses der
   letzten 24 h.

**Telemetrie**: Jeder Sub-Agent-Spawn wird per PostToolUse-Hook
(`.claude/hooks/log-agent.sh`) als JSONL nach
`.workflow-evidence/agents.jsonl` geloggt. Bypasses landen in
`.workflow-evidence/overrides.jsonl`. Beide Logs sind `.gitignore`d.

**Override**: `CLAUDE_HOOKS_OFF=1 <command>` deaktiviert Gate 1 + Gate 2
für genau einen Aufruf, schreibt aber einen Bypass-Record. Nur mit
expliziter User-Autorisierung verwenden. Bei fehlschlagendem Hook erst
die Root-Cause beheben, nicht bypassen.
