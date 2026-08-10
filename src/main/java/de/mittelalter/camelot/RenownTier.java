package de.mittelalter.camelot;

public enum RenownTier {
    UNKNOWN(0), RECOGNIZED(25), HONORED(75), ROUND_TABLE(150);
    private final int threshold;
    RenownTier(int threshold) { this.threshold = threshold; }
    public int threshold() { return threshold; }
    public static RenownTier forPoints(int points) {
        RenownTier result = UNKNOWN;
        for (RenownTier tier : values()) if (points >= tier.threshold) result = tier;
        return result;
    }
}
