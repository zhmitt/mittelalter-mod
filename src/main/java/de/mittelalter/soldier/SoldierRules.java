package de.mittelalter.soldier;

import java.util.Objects;
import java.util.UUID;

public final class SoldierRules {
    private SoldierRules() {
    }

    public static boolean isCommandEligible(UUID issuer, UUID owner, double distanceSquared, double radius) {
        return Objects.equals(issuer, owner) && radius >= 0.0 && distanceSquared <= radius * radius;
    }

    public static boolean canRecruit(int currentOwned, int cap) {
        return cap > 0 && currentOwned >= 0 && currentOwned < cap;
    }
}
