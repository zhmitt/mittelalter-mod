# Mittelalter-Mod

Ein Content-Mod für Minecraft Java Edition, gebaut auf [NeoForge](https://neoforged.net/). Themenschwerpunkt: mittelalterliche Blöcke, Items, Waffen, Mobs und Gameplay-Mechaniken.

Dieses Repository nutzt den Hybrid-Workflow aus `app.dev-template` mit einer konsistenten Entwicklungs-Pipeline über Codex, Claude Code und Gemini CLI, damit mehrere Mitwirkende konsistent zusammenarbeiten können. Es bringt:

- eine kanonische Governance-Ebene in `AGENTS.md`
- eine kanonische Spezifikations-Ebene in `openspec/`
- eine kanonische Ausführungsebene in `workflow/`
- dünne Tool-Adapter in `.claude/`, `.gemini/` und `.codex/`

## Stack

- **Modloader:** NeoForge
- **Minecraft-Version:** siehe `gradle.properties` (`minecraft_version`)
- **Sprache/Build:** Java, Gradle

## Repository-Struktur

```text
mittelalter-mod/
├── AGENTS.md
├── README.md
├── CLAUDE.md
├── GEMINI.md
├── docs/
├── openspec/
├── workflow/
├── .claude/
├── .gemini/
├── .codex/
├── build.gradle
├── gradle.properties
├── src/
└── tests/
```

## Voraussetzungen

- Git
- JDK (siehe `gradle.properties` für die geforderte Java-Version)
- Bash, `python3` (für Workflow-Skripte)
- OpenSpec-Workflow-Skripte in `workflow/scripts/`

Optional:

- Claude Code, Gemini CLI oder Codex als Entwicklungs-Assistent

## Loslegen

```bash
./gradlew build      # baut das Mod-Jar
./gradlew runClient  # startet einen Test-Client mit dem Mod geladen
./gradlew runData    # generiert Daten (Recipes, Loot Tables, ...)
```

## Kanonischer Workflow

### Planung und Change-Definition

- Aktuelles Verhalten liegt in `openspec/specs/`
- Vorgeschlagene Arbeit liegt in `openspec/changes/<change-id>/`
- Archivierte Historie liegt in `openspec/changes/archive/`

### Ausführung und operationaler Zustand

- `workflow/scripts/phase-status.sh`
- `workflow/scripts/tasks-sync.sh`
- `workflow/scripts/tasks-sync.sh --check`
- `workflow/scripts/milestone-sync.sh --summary "..."`
- `workflow/scripts/milestone-check.sh --staged --mode warn|enforce`
- `workflow/scripts/post-impl-check.sh`
- `workflow/scripts/session-close.sh --summary "..."`

An bedeutsamen Implementierungs-Checkpoints `milestone-sync.sh` bevorzugen, damit `verification.md`, `workflow/state/status.md`, `workflow/state/NEXT-SESSION.md`, Reports und die Task-Registry synchron bleiben.

Bevor ein Change als "done" gilt: `workflow/scripts/change-done.sh --change <id>` muss mit Exit-Code 0 durchlaufen (siehe `AGENTS.md`, Abschnitt "Definition of Done").

### Tool-Adapter

- Claude Code: `.claude/commands/opsx/` und `.claude/agents/`
- Gemini CLI: `.gemini/commands/opsx/`, `.gemini/skills/` und `.gemini/agents/`
- Codex: `.codex/skills/` und optionale globale Prompt-Installation via `scripts/install-codex-prompts.sh`
