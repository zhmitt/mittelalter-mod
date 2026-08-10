package de.mittelalter.camelot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Minecraft-independent renown rules, persisted by RenownSavedData. */
public final class RenownLedger {
    private final Map<UUID, Integer> points = new HashMap<>();
    public RenownLedger() {}
    public RenownLedger(Map<UUID, Integer> initial) { initial.forEach((id, value) -> points.put(id, Math.max(0, value))); }
    public int points(UUID player) { return points.getOrDefault(player, 0); }
    public RenownTier tier(UUID player) { return RenownTier.forPoints(points(player)); }
    public int award(UUID player, int amount) {
        if (amount < 0) throw new IllegalArgumentException("Renown award cannot be negative");
        return points.merge(player, amount, Integer::sum);
    }
    public Map<UUID, Integer> snapshot() { return Map.copyOf(points); }
    public static int tournamentAward(boolean champion) { return champion ? 20 : 10; }
}
