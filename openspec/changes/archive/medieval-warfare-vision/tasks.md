# Tasks

## 1. Canonical design baseline
- [x] 1.1 Capture Henri's free-form brief in canonical OpenSpec artifacts (`proposal.md`, `design.md`, delta spec)
- [x] 1.2 Normalize ambiguous spoken terms and record them as explicit validation items instead of silently hard-coding them

## 2. Product-owner validation
- [x] 2.1 Confirm in-game order commands with range limits; exclude real microphone input
- [x] 2.2 Confirm longsword, poleaxe / long axe, and halberd for `V1`; defer concrete recipes and balance to the equipment implementation change
- [x] 2.3 Confirm the defensive stone block as a `Schiessschartenblock` / arrow-slit block
- [x] 2.4 Keep vanilla TNT available but outside the intended medieval progression
- [x] 2.5 Use ruby for decoration and officer/prestige equipment rather than a generic combat tier

## 3. Follow-up implementation planning
- [x] 3.1 Create a dedicated implementation change for `V1` medieval equipment and new resource lines (`v1-medieval-equipment-resources`)
- [x] 3.2 Create a dedicated implementation change for the soldier command MVP (`v1-soldier-command-mvp`)
- [x] 3.3 Create a dedicated implementation change for fortification blocks and harsher stone progression (`v1-fortifications`)

## 4. Later-phase planning
- [x] 4.1 Define siege engines as assembled entities with optional stationary deployment state
- [x] 4.2 Preserve vanilla villages and add rare defended sites with lightweight faction state
- [x] 4.3 Use a grounded default and explicit world-creation/pre-play server gating for fantasy content
