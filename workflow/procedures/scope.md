# Scope procedure

Read only when the core names this trigger. Retained repository-specific details.

## Outcome guard and scope reset

Every change has one stable **Outcome Anchor**: the observable product or
operational result, its user value, no more than three required acceptance
criteria, proportional release evidence, and explicitly non-blocking supporting
evidence. Supporting tooling does not become a hard dependency merely because
its own verification fails.

Classify every blocking claim as exactly one of `product`, `safety-security`,
`release-evidence`, `tooling`, or `external-authority`. A tooling finding may
block the outcome only with a concrete causal chain to an acceptance criterion
or to security, privacy, data integrity, payment, safety, or Production. Missing
release evidence first triggers a search for the smallest proportional
alternative evidence. External-authority uncertainty and isolatable risk use a
safe conservative default; disable or defer the affected feature while the
independent outcome continues.

Before creating or escalating supporting work, record its critical-path
relation, exact blocked criterion, causal evidence, safe default, deferral route,
and complexity delta. Supporting work defaults to non-blocking follow-up or
parked research. It may become active only when it directly closes acceptance,
prevents a concrete high-risk harm, removes more complexity than it adds, or has
at least two current consumers.

The scope-escalation budget is at most two supporting follow-ups, one new
abstraction without a second current consumer, and two layers between product
code and release evidence. After two failed approaches, when supporting work
would spawn supporting work, or when the next step does not move the Outcome
Anchor materially closer to delivery, run `workflow/scripts/outcome-reset.sh`.
The reset keeps the smallest product critical path active, selects proportional
alternative evidence, applies safe defaults, and parks or closes the rest. It
does not create a new blocking Change by itself.

## Decision-minimal gates and authoritative evidence

For customer journeys, retain one compact candidate-bound evidence record:
source/runtime/frontend identity, relevant non-secret configuration identity,
tested steps, expected outcomes and applicable negative-contract cases. Reuse
results while their attested dependencies are unchanged; rerun affected steps,
not every journey for documentation or unrelated code edits. Provider acceptance
alone is not proof of usable customer capability. Prefer existing deterministic
tests for repeated mechanics and a bounded browser smoke on the actual candidate;
do not create a new test platform. Adjacent or cosmetic findings do not extend
agreed acceptance automatically. Stop when that acceptance passes.

For every approval, publication, release or migration gate, minimize manual
input to decisions only a responsible human can make. Identity, role, time,
candidate or release identity, checksums, test results and other facts already
available from an authenticated or authoritative system source **MUST** be
derived by the system, shown for confirmation when material, and atomically
bound to the decision. They must not be re-entered or maintained as a second
source of truth.

Evidence and prior approvals are invalidated only by a change to a dependency
they attest. Formatting, report relocation or regeneration of derivable
metadata must not restart unrelated review, UAT or cooling-off. A procedural
artifact may block delivery only when a mandatory safety, legal, privacy,
data-integrity, payment or release control depends on it and no smaller
equivalent evidence exists.

This rule does not remove substantive professional judgment, fresh
authentication, cooling-off, immutable audit, fail-closed mismatch handling or
required independent review. Systems must not derive or pre-author professional
judgment, exception decisions, finding dispositions or risk acceptance.
