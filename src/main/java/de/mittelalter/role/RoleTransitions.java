package de.mittelalter.role;

import de.mittelalter.camelot.RenownTier;

/** Server-independent transition policy. */
public final class RoleTransitions {
    private RoleTransitions() {}

    public static boolean canTransition(ArthurianRole target, boolean specialRolesEnabled,
                                        boolean mythicEnabled, int renown) {
        if (!specialRolesEnabled) return false;
        return switch (target) {
            case ARTHUR -> renown >= RenownTier.ROUND_TABLE.threshold();
            case MERLIN -> mythicEnabled && renown >= RenownTier.HONORED.threshold();
            case YOUNG_KNIGHT -> false;
        };
    }
}
