# Verification: Arthurian legends layer

## Scope verified

- Optional courtly Arthurian layer independent from core and separately composable with mythic/fantasy content
- Young Knight default plus gated Arthur and Merlin paths
- Early-to-mid repeatable tournaments, singular Camelot, mixed knight dispositions, and lightweight renown
- Mythic relic boundaries, sandbox-compatible narrative delivery, and shared-system architecture
- Concrete follow-up changes for heraldry/rewards, tournaments/venues, Camelot/court/renown, and role paths

## Cross-reference evidence

- Heraldry and stable rewards: `openspec/changes/archive/arthurian-heraldry-rewards/`
- Melee/archery events and 9×9 venue: `openspec/changes/archive/arthurian-tournaments/`
- Singular Camelot, named knights, persistent renown: `openspec/changes/archive/camelot-round-table-renown/`
- Young Knight/Arthur/Merlin selection and shared-system hooks: `openspec/changes/archive/arthurian-role-paths/`
- `rg` finds all declared item, entity, configuration, tournament, renown, and role integration identifiers in `src/main/java` and `src/main/resources`

## Self-applicability evidence

All four implementation samples passed `workflow/scripts/change-done.sh --change <id>` with exit 0 before archival:

- `arthurian-heraldry-rewards`
- `arthurian-tournaments`
- `camelot-round-table-renown`
- `arthurian-role-paths`

Each contains `verification.md`; matching status/report/drift artifacts exist in `workflow/state/`.

## Architecture evidence

- Arthurian soldiers and knights extend `SoldierEntity`
- Tournaments reuse standard combat events and registered reward items
- Camelot uses shared blocks/entities plus overworld SavedData rather than a second world pipeline
- Roles query the existing tournament, renown, and command owners
- Mythic eligibility is a gate only; no parallel magic implementation was introduced

## Known limitations

- V1 Camelot and tournament grounds are player-triggered deterministic structures rather than automatic world generation.
- Jousting remains correctly deferred until shared cavalry exists.
- Excalibur, Grail, Lady of the Lake, and full mythic encounters remain V3 scope.

## 2026-08-10 20:44:22

- Summary: Validated and self-applied the optional Arthurian layer through heraldry, tournaments, Camelot/renown, and persistent role-path implementations.
- Phase state: ready_for_verify
- Tasks complete: 22/22
- Evidence: Four archived Arthurian implementation changes each passed change-done exit 0; cross-reference grep finds all declared integration surfaces.

## 2026-08-10 20:49:21

- Summary: All twenty-two Arthurian concept and follow-up planning tasks are backed by four implemented, verified, and archived feature changes.
- Phase state: ready_for_archive
- Tasks complete: 22/22
- Completed: 22/22 tasks and four self-applicability samples.
- Remaining: Final deterministic completion gate only.
- Evidence: openspec/changes/arthurian-legends-layer/verification.md and four archived child verification artifacts.
- Next: Execute workflow/scripts/change-done.sh for arthurian-legends-layer.
