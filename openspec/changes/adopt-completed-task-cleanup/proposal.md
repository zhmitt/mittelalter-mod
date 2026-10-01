# Proposal: adopt-completed-task-cleanup

## Outcome

Completed visible Codex change tasks can be archived automatically from an exact,
verified final handoff while unsafe or ambiguous task/worktree cleanup fails closed.

## Acceptance

- The canonical policy and handoff distinguish exact thread identity from queued client identity.
- A pure repository helper covers task archival and independent worktree-release eligibility.
- Focused tests cover the positive path and every required fail-closed case.

## Non-goals

- Calling Codex app APIs from repository code.
- Deleting branches, dirty/orphan worktrees, or unrelated task state.
- Archiving OpenSpec changes automatically.

## Budget

Only canonical workflow policy, handoff metadata, one pure shell helper, one
focused test, and this OpenSpec change are in scope.

## Stop condition

Stop without claiming cleanup success if exact task identity, final handoff,
checkpoint preservation, integration, ownership, cleanliness, or diagnostic
release status is missing or ambiguous.

