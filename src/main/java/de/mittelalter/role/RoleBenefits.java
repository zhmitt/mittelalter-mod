package de.mittelalter.role;

/** Pure role modifiers queried by the systems which own their mechanics. */
public final class RoleBenefits {
    public static final int YOUNG_KNIGHT_TOURNAMENT_BONUS = 2;
    public static final int ARTHUR_COMMAND_RADIUS_BONUS = 8;

    private RoleBenefits() {}

    public static int tournamentRenownBonus(ArthurianRole role) {
        return role == ArthurianRole.YOUNG_KNIGHT ? YOUNG_KNIGHT_TOURNAMENT_BONUS : 0;
    }

    public static double effectiveCommandRadius(double baseRadius, ArthurianRole role) {
        return baseRadius + (role == ArthurianRole.ARTHUR ? ARTHUR_COMMAND_RADIUS_BONUS : 0);
    }

    public static boolean mythicEligible(ArthurianRole role, boolean mythicEnabled) {
        return mythicEnabled && role == ArthurianRole.MERLIN;
    }
}
