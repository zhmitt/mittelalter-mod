# Verification: Arthurian heraldry and rewards

## Scope verified

- Disabled-by-default `arthurian.enabled` common configuration
- Lion, stag, raven, and grail chest equipment with exact iron material values
- Four distinct worn default colors through separate equipment assets
- Config-gated creative presentation and NeoForge-conditioned survival recipes
- Stable tournament token and champion laurel reward identifiers
- Original 16×16 inventory art, English/German localization, client models, and recipes

## Automated evidence

- `./gradlew clean build --no-daemon` — exit 0
- `ArthurianHeraldryTest` — 5/5 tests for default gate, iron parity, resources/conditions, distinct equipment colors, and localization
- `./gradlew runData --no-daemon` — exit 0
- Generated `run/config/mittelalter-common.toml` contains `[arthurian] enabled = false`

## Runtime evidence

- Bounded `./gradlew runClient --no-daemon` reached full resource reload, atlas creation, and main menu without missing Mittelalter assets or exceptions; intentionally stopped after successful startup
- Runtime log: `run/logs/latest.log`

## Visual asset evidence

- Source atlas: `src/main/resources/assets/mittelalter/textures/source/arthurian_heraldry_atlas.png`
- Six derived item textures are 16×16
- Worn presentation reuses Minecraft's supported leather layer with four separate default colors, avoiding copied vanilla texture files

## Known limitations

- Heraldic emblems are strongest in inventory icons; V1 worn models distinguish houses primarily by color. Custom high-resolution worn overlays remain a polish task.

## 2026-08-10 19:51:39

- Summary: Implemented optional Arthurian heraldry with four iron-equivalent surcoats, gated acquisition/presentation, stable tournament rewards, original item art, and tests.
- Phase state: ready_for_verify
- Tasks complete: 9/9
- Evidence: ArthurianHeraldryTest 5/5; build and runData exit 0; bounded client reload succeeded.

## 2026-08-10 19:51:39

- Summary: All nine heraldry and tournament-reward tasks have code, assets, gating, tests, runtime, and workflow evidence.
- Phase state: ready_for_archive
- Tasks complete: 9/9
- Completed: 9/9 tasks with exact iron armor parity and distinct worn colors.
- Remaining: Final deterministic completion gate only.
- Evidence: openspec/changes/arthurian-heraldry-rewards/verification.md; ArthurianHeraldryTest.
- Next: Execute workflow/scripts/change-done.sh for arthurian-heraldry-rewards.
