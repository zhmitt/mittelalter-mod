# Change: adopt outcome guard

## Outcome

Keep delivery on the smallest critical path when optional tooling fails.

## Acceptance criteria

- Workers carry a stable outcome anchor and blocker classification.
- Tooling blocks only with an exact criterion and causal evidence.
- Two failed approaches or a second supporting layer emits `reset_required`.

## Non-goals

No product mutation, deployment, push, telemetry platform, or weakened safety gate.
