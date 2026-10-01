# Status Log

Append-only project status log for deterministic session handover.

## 2026-07-09 07:40:24
- Change: bootstrap-mittelalter-mod
- Status: implemented
- Summary: Bootstrapped the hybrid app.dev-template workflow (governance, workflow scripts, three tool adapters, CI gates) and a NeoForge 26.1.2 Gradle mod skeleton (mod id mittelalter) as the repository foundation.
- Evidence: openspec/changes/bootstrap-mittelalter-mod/verification.md, workflow/state/reports/2026-07-09-bootstrap-mittelalter-mod.md
- Next: Add verification.md and workflow evidence

## 2026-07-09 07:43:35
- Change: bootstrap-mittelalter-mod
- Status: implemented
- Summary: Fixed a pipefail bug in post-impl-check.sh --staged that would abort on commits touching no openspec/changes/ path
- Completed: All tracked tasks are currently marked complete.
- Evidence: openspec/changes/bootstrap-mittelalter-mod/verification.md, workflow/state/reports/2026-07-09-bootstrap-mittelalter-mod.md
- Next: Archive the change into openspec/changes/archive/

## 2026-07-09 10:25:09
- Change: github-project-sync
- Status: implemented
- Summary: Lean GitHub Projects (v2) board wired as a one-directional, mechanically-derived visualization of openspec/changes/ state; openspec remains SSOT. Added workflow/scripts/github-sync.sh (sync/archive), .github/project-sync.json, issue template, and adapter pointers across Claude/Gemini/Codex.
- Completed: All tracked tasks are currently marked complete.
- Evidence: openspec/changes/github-project-sync/verification.md, workflow/state/reports/2026-07-09-github-project-sync.md
- Next: Add verification.md and workflow evidence

## 2026-08-10 19:06:12
- Change: v1-medieval-equipment-resources
- Status: checkpointed
- Summary: Implemented the V1 medieval equipment and silver/ruby resource vertical slice with registries, assets, data, tests, worldgen, and client smoke evidence.
- Evidence: openspec/changes/v1-medieval-equipment-resources/verification.md, workflow/state/reports/2026-08-10-v1-medieval-equipment-resources.md
- Next: Complete remaining tasks

## 2026-08-10 19:06:13
- Change: v1-medieval-equipment-resources
- Status: checkpointed
- Summary: V1 equipment and resource implementation verified through build, data initialization, deterministic manifest tests, and bounded client loading.
- Completed: Registries, creative tab, recipes, loot, tags, ore worldgen, models, textures, localization, JUnit coverage, runData, and client smoke.
- Remaining: Run the final change-done gate and archive after it passes.
- Evidence: openspec/changes/v1-medieval-equipment-resources/verification.md, workflow/state/reports/2026-08-10-v1-medieval-equipment-resources.md
- Next: Run workflow/scripts/change-done.sh --change v1-medieval-equipment-resources.

## 2026-08-10 19:06:36
- Change: v1-medieval-equipment-resources
- Status: implemented
- Summary: All eleven scoped tasks now have corresponding code, resource, test, smoke, or workflow evidence.
- Completed: 11/11 tasks with verification.md and workflow report present.
- Remaining: Final deterministic completion gate only.
- Evidence: openspec/changes/v1-medieval-equipment-resources/verification.md, workflow/state/reports/2026-08-10-v1-medieval-equipment-resources.md
- Next: Execute workflow/scripts/change-done.sh for this change.

## 2026-08-10 19:18:31
- Change: v1-soldier-command-mvp
- Status: implemented
- Summary: Implemented the V1 server-authoritative soldier command system with foot and archer roles, recruitment, ownership, bounded commands, persistence, configuration, assets, and tests.
- Evidence: openspec/changes/v1-soldier-command-mvp/verification.md, workflow/state/reports/2026-08-10-v1-soldier-command-mvp.md
- Next: Add verification.md and workflow evidence

## 2026-08-10 19:18:32
- Change: v1-soldier-command-mvp
- Status: implemented
- Summary: All soldier command MVP tasks have code, resource, test, review, and runtime initialization evidence.
- Completed: 10/10 tasks; AI construction-order defect corrected during review; verification artifact present.
- Remaining: Final deterministic completion gate only.
- Evidence: openspec/changes/v1-soldier-command-mvp/verification.md, workflow/state/reports/2026-08-10-v1-soldier-command-mvp.md
- Next: Execute workflow/scripts/change-done.sh for this change.

## 2026-08-10 19:22:33
- Change: medieval-warfare-vision
- Status: implemented
- Summary: Validated the canonical medieval warfare vision and applied it through dedicated equipment, soldier-command, and fortification changes.
- Evidence: openspec/changes/medieval-warfare-vision/verification.md, workflow/state/reports/2026-08-10-medieval-warfare-vision.md
- Next: Add verification.md and workflow evidence

## 2026-08-10 19:22:34
- Change: medieval-warfare-vision
- Status: implemented
- Summary: All thirteen vision and planning tasks are complete with two archived self-application samples and a third active implementation sample.
- Completed: 13/13 tasks, verification artifact, product decisions, architecture defaults, and follow-up change mapping.
- Remaining: Final deterministic completion gate only.
- Evidence: openspec/changes/medieval-warfare-vision/verification.md, workflow/state/reports/2026-08-10-medieval-warfare-vision.md
- Next: Execute workflow/scripts/change-done.sh for medieval-warfare-vision.

## 2026-08-10 19:40:58
- Change: v1-fortifications
- Status: implemented
- Summary: Implemented V1 palisade, reinforced-stone progression, and a directional functional arrow-slit block with complete resources and deterministic geometry tests.
- Evidence: openspec/changes/v1-fortifications/verification.md, workflow/state/reports/2026-08-10-v1-fortifications.md
- Next: Add verification.md and workflow evidence

## 2026-08-10 19:40:59
- Change: v1-fortifications
- Status: implemented
- Summary: All nine fortification tasks have code, data, geometry tests, runtime initialization, and verification evidence.
- Completed: 9/9 tasks including directional model/collision parity and scoped iron-tier progression.
- Remaining: Final deterministic completion gate only.
- Evidence: openspec/changes/v1-fortifications/verification.md, workflow/state/reports/2026-08-10-v1-fortifications.md
- Next: Execute workflow/scripts/change-done.sh for v1-fortifications.

## 2026-08-10 19:51:39
- Change: arthurian-heraldry-rewards
- Status: implemented
- Summary: Implemented optional Arthurian heraldry with four iron-equivalent surcoats, gated acquisition/presentation, stable tournament rewards, original item art, and tests.
- Evidence: openspec/changes/arthurian-heraldry-rewards/verification.md, workflow/state/reports/2026-08-10-arthurian-heraldry-rewards.md
- Next: Add verification.md and workflow evidence

## 2026-08-10 19:51:39
- Change: arthurian-heraldry-rewards
- Status: implemented
- Summary: All nine heraldry and tournament-reward tasks have code, assets, gating, tests, runtime, and workflow evidence.
- Completed: 9/9 tasks with exact iron armor parity and distinct worn colors.
- Remaining: Final deterministic completion gate only.
- Evidence: openspec/changes/arthurian-heraldry-rewards/verification.md, workflow/state/reports/2026-08-10-arthurian-heraldry-rewards.md
- Next: Execute workflow/scripts/change-done.sh for arthurian-heraldry-rewards.

## 2026-08-10 20:14:34
- Change: arthurian-tournaments
- Status: implemented
- Summary: Implemented Arthurian melee and archery tournaments with a deterministic physical venue, server-authoritative sessions, eligibility rules, cooldowns, rewards, messages, and tests.
- Evidence: openspec/changes/arthurian-tournaments/verification.md, workflow/state/reports/2026-08-10-arthurian-tournaments.md
- Next: Add verification.md and workflow evidence

## 2026-08-10 20:14:34
- Change: arthurian-tournaments
- Status: implemented
- Summary: All ten tournament tasks have venue, event, reward, test, runtime, and workflow evidence.
- Completed: 10/10 tasks; melee and archery supported without cavalry.
- Remaining: Final deterministic completion gate only.
- Evidence: openspec/changes/arthurian-tournaments/verification.md, workflow/state/reports/2026-08-10-arthurian-tournaments.md
- Next: Execute workflow/scripts/change-done.sh for arthurian-tournaments.

## 2026-08-10 20:35:28
- Change: camelot-round-table-renown
- Status: implemented
- Summary: Implemented singular Camelot construction, named Round Table knight archetypes, persistent renown tiers, and tournament renown integration with tests.
- Evidence: openspec/changes/camelot-round-table-renown/verification.md, workflow/state/reports/2026-08-10-camelot-round-table-renown.md
- Next: Add verification.md and workflow evidence

## 2026-08-10 20:35:28
- Change: camelot-round-table-renown
- Status: implemented
- Summary: All ten Camelot, court, and renown tasks have plan, persistence, integration, test, runtime, and workflow evidence.
- Completed: 10/10 tasks with unique-world location and persistent four-tier renown.
- Remaining: Final deterministic completion gate only.
- Evidence: openspec/changes/camelot-round-table-renown/verification.md, workflow/state/reports/2026-08-10-camelot-round-table-renown.md
- Next: Execute workflow/scripts/change-done.sh for camelot-round-table-renown.

## 2026-08-10 20:43:39
- Change: arthurian-role-paths
- Status: implemented
- Summary: Implemented persistent Arthurian role paths with Young Knight default, gated Arthur/Merlin transitions, and shared renown, command, and mythic hooks.
- Evidence: openspec/changes/arthurian-role-paths/verification.md, workflow/state/reports/2026-08-10-arthurian-role-paths.md
- Next: Add verification.md and workflow evidence

## 2026-08-10 20:43:40
- Change: arthurian-role-paths
- Status: implemented
- Summary: All eleven Arthurian role tasks have persistence, transition, integration, presentation, test, runtime, and workflow evidence.
- Completed: 11/11 tasks; three role benefits wired to owning systems.
- Remaining: Final deterministic completion gate only.
- Evidence: openspec/changes/arthurian-role-paths/verification.md, workflow/state/reports/2026-08-10-arthurian-role-paths.md
- Next: Execute workflow/scripts/change-done.sh for arthurian-role-paths.

## 2026-08-10 20:44:22
- Change: arthurian-legends-layer
- Status: implemented
- Summary: Validated and self-applied the optional Arthurian layer through heraldry, tournaments, Camelot/renown, and persistent role-path implementations.
- Evidence: openspec/changes/arthurian-legends-layer/verification.md, workflow/state/reports/2026-08-10-arthurian-legends-layer.md
- Next: Add verification.md and workflow evidence

## 2026-08-10 20:49:21
- Change: arthurian-legends-layer
- Status: implemented
- Summary: All twenty-two Arthurian concept and follow-up planning tasks are backed by four implemented, verified, and archived feature changes.
- Completed: 22/22 tasks and four self-applicability samples.
- Remaining: Final deterministic completion gate only.
- Evidence: openspec/changes/arthurian-legends-layer/verification.md, workflow/state/reports/2026-08-10-arthurian-legends-layer.md
- Next: Execute workflow/scripts/change-done.sh for arthurian-legends-layer.

## 2026-08-10 20:56:24
- Summary: Alle elf Changes implementiert, verifiziert und archiviert; GitHub-Projektaufgaben geschlossen. Gesamt-Build, Tests, Datagen, JSON-, Registry- und Diff-Prüfungen sind grün. Es gibt keinen aktiven Change; nächster Schritt ist bei Bedarf ein neuer OpenSpec-Change.
- Change: none
- State: no_change

## 2026-08-10 21:33:31
- Change: v1-integration-release-candidate
- Status: checkpointed
- Summary: Created v1-integration-release-candidate with GameTest, packaged-JAR audit, deterministic verification, and honest manual acceptance boundaries; GitHub issue #12 synced.
- Remaining: Complete the remaining tracked tasks.
- Evidence: openspec/changes/v1-integration-release-candidate/verification.md, workflow/state/reports/2026-08-10-v1-integration-release-candidate.md
- Next: Complete remaining tasks

## 2026-08-10 21:40:53
- Change: v1-integration-release-candidate
- Status: checkpointed
- Summary: V1 RC hardened with four dedicated GameTests, direct JAR audit, corrected packaging exclusions, RC metadata, checksum evidence, bounded client smoke, and explicit manual acceptance boundary.
- Evidence: openspec/changes/v1-integration-release-candidate/verification.md, workflow/state/reports/2026-08-10-v1-integration-release-candidate.md
- Next: Complete remaining tasks

## 2026-08-10 21:41:04
- Change: v1-integration-release-candidate
- Status: implemented
- Summary: All 13 RC tasks have implementation or evidence; deterministic pipeline, GameTests, artifact audit, and client smoke are complete, with manual visual checks explicitly deferred to release promotion.
- Evidence: openspec/changes/v1-integration-release-candidate/verification.md, workflow/state/reports/2026-08-10-v1-integration-release-candidate.md
- Next: Archive the change into openspec/changes/archive/

## 2026-08-10 21:41:45
- Summary: V1 integration release candidate 0.1.0-rc.1 completed and archived. Four NeoForge GameTests, direct JAR audit, packaging hygiene, checksum evidence, runData and bounded client smoke pass. GitHub issue #12 is closed. Manual visual/feel checklist remains for release promotion; next feature change can target V2 siege engines.
- Change: none
- State: no_change

## 2026-08-14 08:46:24
- Summary: Shared focus and orchestration baseline synchronized; portable routing and worker-handoff checks pass. Existing product changes remain untouched and are the next-session context.
- Change: none
- State: no_change

## 2026-08-14 10:15:32
- Summary: Completed visible Codex task cleanup lifecycle adopted: exact destination identity, fail-closed archival gate, independent worktree release gate, canonical handoff metadata, and focused tests are in place. No task, branch, or worktree was deleted.
- Change: adopt-completed-task-cleanup
- State: ready_for_verify
- Next: Add verification.md and workflow evidence

## 2026-08-14 10:20:38
- Summary: Completed visible Codex task lifecycle adoption is implemented and locally verified. Exact destination identity, fail-closed task archival, separate worktree-release eligibility, OpenSpec evidence, and worker-handoff metadata were added. Codex created no commit, staged no files, pushed nothing, and deleted no task, branch, or worktree. The repository main session must review inherited local changes, stage only this change's owned files, rerun focused checks, and create the checkpoint/commit if appropriate.
- Change: adopt-completed-task-cleanup
- State: ready_for_verify
- Next: Add verification.md and workflow evidence

## 2026-08-16 09:07:28
- Change: 2026-08-16-outcome-guard
- Status: implemented
- Summary: Added an outcome guard that parks unsupported tooling escalation.
- Evidence: openspec/changes/2026-08-16-outcome-guard/verification.md, workflow/state/reports/2026-08-16-2026-08-16-outcome-guard.md
- Next: Complete proposal and delta specs

## 2026-08-16 09:10:03
- Change: 2026-08-16-outcome-guard
- Status: implemented
- Summary: Outcome guard verified; unsupported evidence escalation now resets to the product critical path.
- Completed: All tracked tasks are currently marked complete.
- Evidence: openspec/changes/2026-08-16-outcome-guard/verification.md, workflow/state/reports/2026-08-16-2026-08-16-outcome-guard.md
- Next: Complete proposal and delta specs

## 2026-08-16 09:52:13
- Change: 2026-08-16-outcome-guard
- Status: implemented
- Summary: Adopted the reviewed deterministic JSON graph router and negative smoke matrix.
- Completed: All tracked tasks are currently marked complete.
- Evidence: openspec/changes/2026-08-16-outcome-guard/verification.md, workflow/state/reports/2026-08-16-2026-08-16-outcome-guard.md
- Next: Complete proposal and delta specs

## 2026-10-01 — local Minecraft verification
- Change: fix-gametest-world-entry
- Orchestration: user_action_pending
- Status: checkpointed
- Summary: GameTest encoding regression and dedicated tests pass; client started, but desktop control cannot select the Java window.
- Evidence: openspec/changes/fix-gametest-world-entry/verification.md
- Next: User creates a new Creative test world; Main checks login and basic interactions.
