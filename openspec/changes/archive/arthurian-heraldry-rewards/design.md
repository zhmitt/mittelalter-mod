# Design: Arthurian heraldry and rewards

## Heraldic set

V1 provides lion (red/gold), stag (green/silver), raven (black/silver), and grail (blue/gold) surcoats. Each is chest equipment at an iron-equivalent defensive level. They communicate allegiance and prestige, not a new best-in-slot tier.

## Acquisition

Base surcoats are crafted from an iron chestplate, wool/dye, and an officer signet. The officer signet connects core prestige resources to courtly equipment. Recipes are enabled only when Arthurian content is active where recipe-condition support permits; runtime use/creative visibility also respects the common configuration.

## Rewards

Tournament tokens are stackable event currency for later reward tables. Champion laurels are non-craftable prestige trophies intended for first-place or exceptional outcomes. This change registers and presents them; the tournament change owns awarding logic.

## Configuration

`arthurian.enabled` defaults false. Content remains registered for save compatibility, while creative presentation and gameplay acquisition are gated. Casual mid-save configuration changes are not a supported progression path.
