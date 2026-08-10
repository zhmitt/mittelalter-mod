# Design: V1 medieval equipment and resources

## Decisions

### Equipment baseline

The longsword is the general-purpose two-handed-style weapon, the poleaxe favors damage and armor pressure at slower speed, and the halberd favors reach/control. Minecraft does not provide a universal two-hand lock, so V1 communicates role through attributes and recipes without inventing a brittle offhand restriction.

### Materials

Silver and ruby receive ore, raw/gem, block, loot, recipe, model, texture, and localization coverage. Silver ingots are produced through normal ore processing. Ruby remains a gem used by the ceremonial officer weapon variant or officer component rather than becoming a full armor/tool tier.

### Compatibility

All content uses stable `mittelalter:*` identifiers. Recipes use standard tags where appropriate. Values are centralized so later balancing does not require registry rewrites.

### Verification

Automated tests cover declared content identifiers and resource completeness. `runData` validates data loading/generation, and `runClient` provides the required feature smoke evidence when the environment supports launching the client.
