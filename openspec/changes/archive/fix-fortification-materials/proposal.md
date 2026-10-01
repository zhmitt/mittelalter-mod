# Restore fortification materials

Outcome: Reinforced stone and oak palisades use matching building materials,
not copied weapon sprites, in canonical Minecraft resource models.

Acceptance: (1) Reinforced stone uses stone bricks. (2) Oak palisades use
oak-log sides/end grain. (3) Resource regression, build and artifact audit pass.
Non-goals: custom art, geometry, mechanics, recipes or other blocks.
Owned: two block models, FortificationContentTest and focused workflow evidence.
Decision: reuse vanilla materials, preserving gameplay. Stop at acceptance.
Next: record regression evidence and run final completion gate.
