package de.mittelalter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import de.mittelalter.role.ArthurianRole;
import de.mittelalter.role.RoleBenefits;
import de.mittelalter.role.RoleLedger;
import de.mittelalter.role.RoleTransitions;

class ArthurianRoleTest {
    @Test void optionalRoleFeaturesDefaultToDisabled() {
        assertFalse(Config.DEFAULT_SPECIAL_ROLES_ENABLED);
        assertFalse(Config.DEFAULT_MYTHIC_ENABLED);
    }

    @Test void roleLedgerAssignsDefaultAndSafelyParsesPersistence() {
        UUID valid = UUID.randomUUID();
        RoleLedger ledger = new RoleLedger(Map.of(
                valid.toString(), "REMOVED_FUTURE_ROLE",
                "not-a-uuid", "ARTHUR"));
        assertEquals(ArthurianRole.YOUNG_KNIGHT, ledger.find(valid).orElseThrow());
        UUID newPlayer = UUID.randomUUID();
        assertTrue(ledger.find(newPlayer).isEmpty());
        assertEquals(ArthurianRole.YOUNG_KNIGHT, ledger.getOrAssignDefault(newPlayer));
        assertEquals("YOUNG_KNIGHT", ledger.serialized().get(newPlayer.toString()));
    }

    @Test void specialRoleTransitionsEnforceConfigAndRenown() {
        assertFalse(RoleTransitions.canTransition(ArthurianRole.ARTHUR, false, true, 150));
        assertFalse(RoleTransitions.canTransition(ArthurianRole.ARTHUR, true, false, 149));
        assertTrue(RoleTransitions.canTransition(ArthurianRole.ARTHUR, true, false, 150));
        assertFalse(RoleTransitions.canTransition(ArthurianRole.MERLIN, true, false, 75));
        assertFalse(RoleTransitions.canTransition(ArthurianRole.MERLIN, true, true, 74));
        assertTrue(RoleTransitions.canTransition(ArthurianRole.MERLIN, true, true, 75));
    }

    @Test void allThreeSharedSystemHooksAreSmallAndPure() {
        assertEquals(2, RoleBenefits.tournamentRenownBonus(ArthurianRole.YOUNG_KNIGHT));
        assertEquals(0, RoleBenefits.tournamentRenownBonus(ArthurianRole.ARTHUR));
        assertEquals(40.0, RoleBenefits.effectiveCommandRadius(32, ArthurianRole.ARTHUR));
        assertEquals(32.0, RoleBenefits.effectiveCommandRadius(32, ArthurianRole.MERLIN));
        assertTrue(RoleBenefits.mythicEligible(ArthurianRole.MERLIN, true));
        assertFalse(RoleBenefits.mythicEligible(ArthurianRole.MERLIN, false));
        assertFalse(RoleBenefits.mythicEligible(ArthurianRole.YOUNG_KNIGHT, true));
    }
}
