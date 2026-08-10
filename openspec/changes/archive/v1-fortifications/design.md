# Design: V1 fortifications

## Progression

The palisade is an early wood defense crafted from logs and iron nuggets. Reinforced stone is a mid-game fortification block crafted from stone bricks and iron, requires an iron-level pickaxe, and has higher hardness/blast resistance than ordinary stone bricks. This makes fortification progression harsher without modifying vanilla blocks globally.

## Arrow slit

The arrow-slit block is horizontally directional. Its collision/occlusion geometry forms a thick stone frame with a central vertical opening, allowing arrows and line of sight through the slit while still providing meaningful cover. It uses reinforced stone in its recipe.

## Compatibility

All blocks use standard loot/tags and stable `mittelalter` identifiers. Redstone, tile entities, and global mining hooks are intentionally unnecessary.

## Verification

Tests cover geometry openness, material/tag/resource coverage, and recipes. A bounded client run validates blockstate/model/texture loading.
