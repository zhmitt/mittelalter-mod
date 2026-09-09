---
name: openspec-verify
description: Verify that a change has enough evidence to be treated as complete.
---

Verify the changed slice and its applicable high-risk controls. Do not add
unrelated full evidence or independent review. Reuse valid decisions and
evidence unless an attested dependency changed.

Inspect existing evidence first. Only for missing final evidence skeletons, run:

```bash
workflow/scripts/post-impl-prepare.sh --change <id> --summary "<what changed>"
```

Only when a handoff, writing-claim release, material rescope, or external wait
needs durable Claim Release resume state, use:

```bash
workflow/scripts/milestone-sync.sh --summary "<what changed>"
```

An ordinary local breadcrumb does not require `milestone-sync.sh`.

Then confirm:

- tasks are complete
- `verification.md` exists
- `workflow/state/status.md` contains a matching entry
- `workflow/state/reports/` contains a matching report

For final completion run `workflow/scripts/change-done.sh --change <id>` once;
it includes post-impl-check. Do not run both separately. Scaffolding and regression
tests are not acceptance evidence. For journeys follow AGENTS.md: reuse valid
candidate-bound results and rerun only affected steps and required negative cases.
