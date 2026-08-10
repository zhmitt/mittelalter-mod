# Design: Lean GitHub Projects board as a visual projection of OpenSpec state

## Context

Two goals are in tension: contributors want a visual board (GitHub's native Projects v2 gives Board/Table/Roadmap views for free from one dataset), but this repository already has a script-verified source of truth for change status (`openspec/changes/*` + `workflow/scripts/phase-status.sh` + `workflow/scripts/change-done.sh`). Any second place that records "what state is this change in" is a drift risk unless it is mechanically derived, never hand-edited.

## Decisions

### Decision 1: One-directional sync, OpenSpec → GitHub, never the reverse
`workflow/scripts/github-sync.sh` only ever reads `openspec/changes/` and writes to GitHub (issues, project item fields). Nothing reads GitHub state back into `openspec/`. This guarantees `openspec/changes/` stays authoritative by construction, not by convention — there is no code path that could pull stale GitHub state back into the spec layer.

### Decision 2: One issue per change, not per task
Task-level detail already lives in `tasks.md` and is enforced by `workflow/scripts/change-done.sh` Phase 2 (tasks.md consistency). Mirroring every task as a separate GitHub issue would create ~19 issues for the bootstrap change alone and a second place for task checkboxes to go stale. One issue per change keeps the board lean and keeps task truth in exactly one place.

### Decision 3: Reuse the Projects v2 default `Status` field instead of inventing new vocabulary
`workflow/scripts/phase-status.sh` emits six internal states (`draft`, `ready_for_design`, `ready_for_tasks`, `in_progress`, `ready_for_verify`, `ready_for_archive`). Mapping all six onto custom board columns would make the board noisier than the internal workflow needs to be for a *visual* overview. Instead the script maps them onto the three columns GitHub already provisions on every new Projects v2 board:

| phase-status.sh state | Projects v2 Status |
|---|---|
| `draft`, `ready_for_design`, `ready_for_tasks` | `Todo` |
| `in_progress`, `ready_for_verify` | `In Progress` |
| `ready_for_archive`, archived | `Done` |

Only one custom field is added: `Change ID` (text) — the folder slug, so a board card can be traced back to `openspec/changes/<id>/` unambiguously (issue titles alone are not guaranteed unique/stable).

### Decision 4: No persisted field/option IDs, only a project-number pointer
`.github/project-sync.json` stores only `{"owner": ..., "project_number": ...}`. Field IDs and single-select option IDs are looked up live via `gh project field-list` on every `github-sync.sh` run. This costs one extra API call per sync but eliminates an entire class of "config file went stale after someone edited the board" bugs — the exact failure mode this change exists to avoid at the openspec layer, so it would be self-defeating to reintroduce it at the GitHub layer.

### Decision 5: Idempotent issue matching via a hidden marker, not title matching
Each created issue body contains an HTML comment `<!-- openspec-change: <change-id> -->`. `github-sync.sh` finds existing issues via `gh issue list --search "openspec-change: <change-id> in:body"` rather than by title, so renaming a change's display title later does not break re-sync.

## Trade-offs

- The three-bucket `Status` mapping loses granularity visible only via `workflow/scripts/phase-status.sh`/`workflow/scripts/change-done.sh` directly (e.g. `ready_for_verify` vs. `ready_for_archive` both show as `Done`... actually `ready_for_verify` shows `In Progress`, `ready_for_archive` shows `Done` — a change can look "Done" on the board slightly before it is archived). This is accepted because the board is explicitly a coarse visual aid, not a verification surface.
- `github-sync.sh` is invoked manually (pointed to from the `opsx:propose`/`opsx:archive` adapter instructions) rather than wired into a GitHub Actions workflow. A CI-triggered sync was considered but rejected for this change: it would need a bot token with `project` scope stored as a repo secret, which is a bigger trust/security surface than this lean-board goal justifies. Can be revisited as a follow-up change if manual invocation proves unreliable in practice.
- Creating the actual GitHub Project (v2) resource is a one-time manual `gh project create` step performed as part of this change's implementation, not something `github-sync.sh` does itself (the script assumes the project already exists and is pointed to by `.github/project-sync.json`).

## Workspace ownership

`primary workspace` — implemented directly on `main`-tracked working tree, no worktree/branch handoff involved.
