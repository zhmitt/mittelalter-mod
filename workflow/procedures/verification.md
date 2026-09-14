# Verification procedure

Read only when the core names this trigger. Retained repository-specific details.

## Canonical evidence requirements

Before a change is considered ready to archive:

- all tasks in `tasks.md` are complete
- a `verification.md` note exists in the change folder
- `workflow/state/status.md` contains an entry for the change
- a report exists in `workflow/state/reports/`

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

## Sub-agent failsafe

### Hook-Failsafe-System

Das dreistufige Hook-System kombiniert Delegations-Telemetrie mit konkreten
Git-Sicherheits- und Evidence-Checks (Implementierung: `.claude/hooks/` und
`workflow/scripts/agent-evidence-check.sh`):

1. **Gate 1 — PreToolUse-Advisory auf Edit/Write/MultiEdit/NotebookEdit**
   (`.claude/hooks/gate-edit.sh`). Klassifiziert Code-Dateien und weist auf
   fehlende aktuelle Plan-/Explore-Evidence hin, blockiert lokale Edits aber
   nicht allein wegen fehlender Sub-Agent-Telemetrie. Delegation bleibt an
   konkreten Parallelitäts-, Spezialfähigkeits- oder Unabhängigkeitsnutzen
   gebunden.
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

**Override**: `CLAUDE_HOOKS_OFF=1 <command>` bleibt für kompatible Hook-
Bypasses beobachtbar und schreibt einen Record. Es darf den konkreten Git-
Sicherheitsblock aus Gate 2 nur mit expliziter User-Autorisierung umgehen.
Bei fehlschlagendem Sicherheits-Hook erst die Root-Cause beheben.
