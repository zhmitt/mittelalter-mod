package de.mittelalter.role;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/** Minecraft-independent UUID role storage and safe deserialization. */
public final class RoleLedger {
    private final Map<UUID, ArthurianRole> roles = new HashMap<>();

    public RoleLedger() {}

    public RoleLedger(Map<String, String> serialized) {
        serialized.forEach((id, role) -> {
            try {
                roles.put(UUID.fromString(id), ArthurianRole.parseOrDefault(role));
            } catch (IllegalArgumentException ignored) {
                // A malformed UUID is an unusable record, not a world-load failure.
            }
        });
    }

    public Optional<ArthurianRole> find(UUID player) { return Optional.ofNullable(roles.get(player)); }
    public ArthurianRole getOrAssignDefault(UUID player) { return roles.computeIfAbsent(player, ignored -> ArthurianRole.YOUNG_KNIGHT); }
    public void set(UUID player, ArthurianRole role) { roles.put(player, role); }
    public Map<String, String> serialized() {
        Map<String, String> result = new HashMap<>();
        roles.forEach((id, role) -> result.put(id.toString(), role.name()));
        return result;
    }
}
