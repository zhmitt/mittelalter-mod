# Outcome Guard

The repository keeps one stable product or operational outcome on the critical
path. Supporting tooling is non-blocking unless a concrete causal chain connects
it to an acceptance criterion or a high-risk security, privacy, data-integrity,
payment, safety, or Production concern.

## Robust graph reset

`workflow/scripts/outcome-reset.sh --input <graph.json>` validates an inert,
bounded schema and emits a deterministic Keep/Park/Defer decision. Use
`--format json` for machine-readable output. A flat graph is valid for simple
repositories; dependent Changes use `parent_id` edges.

The router rejects duplicate or unknown keys, invalid UTF-8, control characters,
placeholders, cycles, broken references, oversized input, symlinks and causal
claims without an exact acceptance criterion. It never executes input content or
uses the network. Direct product, evidenced safety, and mandatory evidence work
remain eligible only through an entirely kept ancestor chain. Supporting work is
parked after two failed approaches, a Supporting-to-Supporting escalation, or an
exceeded complexity budget.

Run `workflow/tests/outcome-guard-smoke.sh` after modifying the router or its
contract. The test covers the analyzer-to-supervisor spiral, deterministic
output, input inertness, dependency closure and the negative validation matrix.
