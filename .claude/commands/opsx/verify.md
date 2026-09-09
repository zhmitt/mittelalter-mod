---
name: opsx-verify
description: Verify that an active change has enough evidence to be considered complete.
category: workflow
tags: [verification, post-implementation, quality]
---

Read the active change, its `tasks.md`, and related workflow state.

Then:

1. verify the changed slice and its applicable high-risk controls; do not add
   unrelated full evidence or independent review
2. reuse valid decisions and evidence unless an attested dependency changed
3. inspect existing evidence; use post-impl-prepare with --change only for missing
   final evidence skeletons
4. review the generated `verification.md` and report, ensure tasks are
   complete, and run `workflow/scripts/change-done.sh --change <id>` once;
   it includes post-impl-check, so do not run that separately
5. use `workflow/scripts/milestone-sync.sh --summary "<what changed>"` only for
   durable Claim Release, not for an ordinary local breadcrumb
