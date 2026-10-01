# Restore arrow-slit masonry appearance

Outcome: The functional arrow-slit frame renders as stone masonry, rather than a sword icon.

Acceptance: Its faces and particles use Minecraft stone bricks; its existing opening and facing geometry remain unchanged; a resource reload shows masonry in the current test world.

Non-goals: New textures, new gameplay, and other fortification assets.

Decision: Reuse the vanilla stone-brick texture in the model. The existing arrow_slit PNG was incorrectly copied from a sword sprite; do not edit that bitmap or broaden the asset pass.

Next: Capture a failing material regression, fix the model, build, then reload client resources.
