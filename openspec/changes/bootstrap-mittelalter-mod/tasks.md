# Tasks

## 1. Repository bootstrap
- [x] 1.1 `git init`, create public GitHub remote `zhmitt/mittelalter-mod`
- [x] 1.2 Add `.gitignore` covering the hybrid-workflow base plus Java/Gradle/IDE artifacts

## 2. Canonical layers
- [x] 2.1 Add `AGENTS.md` adapted from `app.dev-template` (Gradle-based Definition-of-Done evidence table)
- [x] 2.2 Add `openspec/config.yaml` with mod-project context
- [x] 2.3 Copy inherited workflow specs into `openspec/specs/`
- [x] 2.4 Add `workflow/scripts/` and fresh `workflow/state/` (no template history)
- [x] 2.5 Add `README.md`, `CLAUDE.md`, `GEMINI.md`, `docs/architecture.md`

## 3. Tool adapters
- [x] 3.1 Add Claude adapter (`.claude/agents/`, `.claude/commands/opsx/`, `.claude/hooks/`, `.claude/settings.json`)
- [x] 3.2 Add Gemini adapter (`.gemini/agents/`, `.gemini/commands/opsx/`, `.gemini/skills/`, `.gemini/settings.json`)
- [x] 3.3 Add Codex adapter (`.codex/skills/`, `.codex/prompts-src/`) and `scripts/install-codex-prompts.sh`
- [x] 3.4 Add `.git-hooks/pre-commit` and `.github/workflows/openspec-gate.yml`

## 4. NeoForge mod skeleton
- [x] 4.1 Determine current NeoForge version from Maven metadata (`26.1.2` / `26.1.2.78`)
- [x] 4.2 Add `build.gradle`, `settings.gradle`, `gradle.properties` (`mod_id=mittelalter`), Gradle wrapper
- [x] 4.3 Add mod entrypoint (`MittelalterMod`, `MittelalterModClient`, `Config`) with placeholder block/item and creative tab
- [x] 4.4 Add `src/main/templates/META-INF/neoforge.mods.toml` template and `src/main/resources/assets/mittelalter/lang/en_us.json` lang file
- [x] 4.5 Add `.github/workflows/gradle-build.yml` CI build

## 5. Verification
- [x] 5.1 Install a working JDK 25 toolchain locally (Temurin, downloaded directly since Homebrew does not yet support this macOS version)
- [x] 5.2 Run `./gradlew build` and confirm it succeeds (evidence in `verification.md`)
- [x] 5.3 Run `workflow/scripts/change-done.sh --change bootstrap-mittelalter-mod` and confirm exit 0
