package de.mittelalter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import de.mittelalter.soldier.SoldierOrder;
import de.mittelalter.soldier.SoldierRole;
import de.mittelalter.soldier.SoldierRules;

class SoldierRulesTest {
    private final UUID owner = UUID.randomUUID();

    @Test void commandRequiresOwnershipAndInclusiveRadius() {
        assertTrue(SoldierRules.isCommandEligible(owner, owner, 32 * 32, 32));
        assertFalse(SoldierRules.isCommandEligible(owner, UUID.randomUUID(), 1, 32));
        assertFalse(SoldierRules.isCommandEligible(owner, owner, 32 * 32 + 0.01, 32));
    }

    @Test void recruitmentCapIsServerCheckable() {
        assertTrue(SoldierRules.canRecruit(15, 16));
        assertFalse(SoldierRules.canRecruit(16, 16));
        assertFalse(SoldierRules.canRecruit(17, 16));
    }

    @Test void rolesRemainDistinct() {
        assertNotEquals(SoldierRole.FOOT, SoldierRole.ARCHER);
    }

    @Test void persistedOrderNamesRoundTripAndInvalidInputIsSafe() {
        for (SoldierOrder order : SoldierOrder.values()) assertEquals(order, SoldierOrder.parse(order.name()));
        assertEquals(SoldierOrder.FOLLOW, SoldierOrder.parse("removed-future-order"));
    }
}
