# Proposal: Implement the V1 soldier command MVP

## Why

Player-commanded soldiers are the mod's primary differentiator. A bounded server-authoritative MVP proves ownership, recruitment, persistence, role distinction, and nearby orders before formations, cavalry, and siege crews broaden the AI surface.

## What changes

- Add recruitable foot-soldier and archer roles
- Persist recruiter ownership and current order on each soldier
- Add an officer's command baton for `Follow`, `Hold`, and targeted `Attack`
- Enforce a configurable command radius and per-player unit cap
- Keep all command decisions server-authoritative without microphone input
- Add deterministic behavior tests plus a client/server smoke record

## Impact

This introduces entity identifiers and saved command state. Networking remains minimal because item interactions are validated and executed on the logical server. Later UI or voice layers may issue the same domain commands without changing soldier persistence.

## Workspace ownership

`primary workspace`; no branch handoff is planned.
