package de.mittelalter.soldier;

public enum SoldierOrder {
    FOLLOW,
    HOLD,
    ATTACK;

    public static SoldierOrder parse(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException | NullPointerException ignored) {
            return FOLLOW;
        }
    }
}
