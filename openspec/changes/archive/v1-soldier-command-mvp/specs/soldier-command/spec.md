## ADDED Requirements

### Requirement: Players can recruit two distinct soldier roles
The mod SHALL provide foot-soldier and archer recruitment contracts. A recruited soldier SHALL record the recruiting player as owner and SHALL count against the configured unit cap.

#### Scenario: Player recruits both roles
- **WHEN** a player below the unit cap uses the two recruitment contracts
- **THEN** one owned melee soldier and one owned ranged soldier can be created
- **AND** their combat goals and starting equipment are distinct

### Requirement: Owned soldiers accept bounded commands
The mod SHALL provide in-game `FOLLOW`, `HOLD`, and targeted `ATTACK` orders. Commands SHALL affect only soldiers owned by the issuer within the configured command radius.

#### Scenario: Player issues a nearby order
- **WHEN** the player uses the command baton with owned soldiers inside the radius
- **THEN** those soldiers adopt the selected order

#### Scenario: Soldier is not eligible
- **WHEN** a soldier is outside the radius or owned by another player
- **THEN** its current order remains unchanged

### Requirement: Soldier command state persists
Owner, current order, and hold position SHALL survive entity save/load. Attack orders SHALL clear safely when their target is invalid or unavailable.

#### Scenario: World reloads while soldiers hold
- **WHEN** an owned soldier with `HOLD` is saved and loaded
- **THEN** it retains its owner, order, and hold position

### Requirement: Commands are server-authoritative and configurable
Recruitment cap and command radius SHALL be validated on the logical server and SHALL be exposed as common/server configuration values.

#### Scenario: Client requests an invalid recruitment or command
- **WHEN** a player is at the cap or a soldier is outside command range
- **THEN** the server rejects that state change regardless of client presentation
