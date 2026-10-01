## ADDED Requirements
### Requirement: Fortifications use matching building materials
Reinforced stone SHALL use stone-brick faces and oak palisades SHALL use oak-log sides and end grain, without changing collision or gameplay.

#### Scenario: Client resolves block models
- **WHEN** resources load
- **THEN** the two models resolve building-material textures rather than weapon sprites.
