## ADDED Requirements

### Requirement: Tournaments require a physical venue
The mod SHALL provide a tournament grounds construction path and a tournament standard venue anchor. Trials SHALL only start for a player near a valid anchor.

#### Scenario: Player establishes tournament grounds
- **WHEN** an Arthurian-enabled player uses a grounds deed on a valid flat area
- **THEN** a compact fenced tournament yard with a standard and trial elements is constructed

### Requirement: V1 supports melee and archery trials
The tournament system SHALL support repeatable melee and archery trials without requiring cavalry.

#### Scenario: Player completes a selected trial
- **WHEN** the player defeats the required hostile targets using the trial's combat mode within venue and time bounds
- **THEN** server-authoritative progress reaches completion
- **AND** the player receives tournament rewards

### Requirement: Tournament state rejects invalid progress
Progress SHALL count only for the enrolled player, selected combat mode, active time window, and venue radius.

#### Scenario: Kill is ineligible
- **WHEN** a kill occurs outside the venue, after timeout, by another player, or with the wrong combat mode
- **THEN** it does not advance the trial

### Requirement: Tournaments award reusable prestige items
Successful trials SHALL award tournament tokens and MAY award a champion laurel for exceptional completion. A cooldown SHALL prevent immediate reward farming.

#### Scenario: Player completes quickly
- **WHEN** completion occurs inside the champion threshold
- **THEN** the normal token reward and one champion laurel are granted

### Requirement: Tournament gameplay respects Arthurian gating
Venue construction, trial start, and reward progression SHALL remain unavailable when `arthurian.enabled` is false.

#### Scenario: Arthurian content is disabled
- **WHEN** a player tries to construct or activate tournament content
- **THEN** the server refuses the action without altering core gameplay
