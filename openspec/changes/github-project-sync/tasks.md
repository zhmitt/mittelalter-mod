# Tasks

## 1. GitHub Projects (v2) board setup
- [x] 1.1 Create the project: `gh project create --owner zhmitt --title "Mittelalter-Mod"`
- [x] 1.2 Link the project to the repository: `gh project link <number> --owner zhmitt --repo mittelalter-mod`
- [x] 1.3 Add the `Change ID` text field to the project
- [x] 1.4 Write `.github/project-sync.json` with `{"owner": "zhmitt", "project_number": <number>}`

## 2. Sync script
- [x] 2.1 Add `workflow/scripts/github-sync.sh` (`sync` command: scan active changes, create/update issues + project items, set `Status`/`Change ID` fields; `archive` command: close issue + set `Done` for an archived change), chmod +x
- [x] 2.2 Idempotent issue lookup via hidden `<!-- openspec-change: <id> -->` body marker (`gh issue list --search`)
- [x] 2.3 Map `workflow/scripts/phase-status.sh --change <id> --json` state onto the three-bucket `Status` table from `design.md`

## 3. Contributor-facing surfaces
- [x] 3.1 Add `.github/ISSUE_TEMPLATE/change-proposal.md`
- [x] 3.2 Add a pointer to `workflow/scripts/github-sync.sh sync` in `.claude/commands/opsx/propose.md` and `archive.md`
- [x] 3.3 Mirror the same pointer in `.gemini/commands/opsx/propose.toml`, `.gemini/commands/opsx/archive.toml`, `.gemini/skills/openspec-propose/SKILL.md`, `.gemini/skills/openspec-archive/SKILL.md`, `.codex/prompts-src/opsx-propose.md`, `.codex/prompts-src/opsx-archive.md`, `.codex/skills/openspec-propose/SKILL.md`, `.codex/skills/openspec-archive/SKILL.md`

## 4. Verification
- [x] 4.1 Run `workflow/scripts/github-sync.sh sync` and confirm it creates exactly one issue for `bootstrap-mittelalter-mod` and one for `github-project-sync`, both visible on the project board (evidence in `verification.md`)
- [x] 4.2 Run `workflow/scripts/github-sync.sh sync` a second time and confirm no duplicate issues are created
- [x] 4.3 Run `workflow/scripts/change-done.sh --change github-project-sync` and confirm exit 0
