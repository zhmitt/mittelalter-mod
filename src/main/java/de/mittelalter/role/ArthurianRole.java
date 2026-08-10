package de.mittelalter.role;

public enum ArthurianRole {
    YOUNG_KNIGHT,
    ARTHUR,
    MERLIN;

    public static ArthurianRole parseOrDefault(String serialized) {
        if (serialized == null) return YOUNG_KNIGHT;
        try {
            return valueOf(serialized);
        } catch (IllegalArgumentException ignored) {
            return YOUNG_KNIGHT;
        }
    }
}
