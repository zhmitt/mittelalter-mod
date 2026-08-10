# Design: Arthurian tournaments

## Venue

The tournament grounds deed validates a flat 9×9 area and constructs a fenced yard with gates, archery targets, spectator markers, and a central tournament standard. The standard is the venue anchor; trials can only start near one.

## Trials

- melee trial: defeat three hostile mobs within the venue radius before timeout
- archery trial: defeat three hostile mobs with player-fired projectiles before timeout

The player selects a trial through interaction with the standard. Only one active trial exists per player. Progress is server-authoritative and bounded to the venue.

## Rewards

Completion awards tournament tokens. Completing within a champion threshold additionally awards one champion laurel. Trials have cooldowns to avoid trivial reward farming while remaining repeatable.

## Persistence and announcements

Active session state is held per server and safely cleared on logout/server stop; V1 events are short enough that they do not persist through restarts. Start, progress, completion, failure, and cooldown are announced through concise action messages. Longer scheduled tournaments remain a later extension.

## Cavalry boundary

No jousting is implemented. The event and venue abstractions reserve a future trial identifier for mounted competition after shared cavalry support exists.
