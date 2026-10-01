# Design
Minecraft synchronizes the GameTest registry during player login. DirectGameTestInstance returned FunctionGameTestInstance.CODEC although it was not that type. Dedicated server execution did not exercise encoding. Use the supported function registration and assert encoding in addition to executing tests.

Owned components: GameTest registration, focused regression coverage, and the local Minecraft test world. Primary main checkout; no branch handoff. Save existing worlds unchanged and create a separately named test world.
