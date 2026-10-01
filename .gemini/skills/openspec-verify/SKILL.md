---
name: openspec-verify
description: Verify evidence before a change is considered complete.
---

Verify the changed slice and its applicable high-risk controls; do not add
unrelated full evidence or independent review. Reuse valid decisions and
evidence unless an attested dependency changed. Use `@opsx-verifier` only when
genuinely independent judgment is required.

For final completion, use `workflow/scripts/post-impl-check.sh` and ensure
`verification.md`, status entries, and reports exist. Reserve
`workflow/scripts/milestone-sync.sh` for durable Claim Release, not ordinary
breadcrumbs.
