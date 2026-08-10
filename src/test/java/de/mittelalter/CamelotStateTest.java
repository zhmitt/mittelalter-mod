package de.mittelalter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import de.mittelalter.camelot.*;

class CamelotStateTest {
    @Test void camelotLocationCanOnlyBeClaimedOnce() {
        CamelotUniqueness state = new CamelotUniqueness();
        var first = new CamelotUniqueness.Location(10, 64, 20);
        assertTrue(state.claim(first));
        assertFalse(state.claim(new CamelotUniqueness.Location(30, 64, 40)));
        assertEquals(first, state.location().orElseThrow());
    }

    @Test void namedCourtCoversMentorAllyAndRival() {
        assertEquals(KnightDisposition.MENTOR, KnightArchetype.BEDIVERE.disposition());
        assertEquals(KnightDisposition.ALLY, KnightArchetype.GAWAIN.disposition());
        assertEquals(KnightDisposition.RIVAL, KnightArchetype.LANCELOT.disposition());
    }

    @Test void renownHasPublicTiersAndTournamentAwards() {
        assertEquals(RenownTier.UNKNOWN, RenownTier.forPoints(24));
        assertEquals(RenownTier.RECOGNIZED, RenownTier.forPoints(25));
        assertEquals(RenownTier.HONORED, RenownTier.forPoints(75));
        assertEquals(RenownTier.ROUND_TABLE, RenownTier.forPoints(150));
        assertEquals(10, RenownLedger.tournamentAward(false));
        assertEquals(20, RenownLedger.tournamentAward(true));
        RenownLedger data = new RenownLedger();
        UUID id = UUID.randomUUID();
        assertEquals(25, data.award(id, 25));
        assertEquals(RenownTier.RECOGNIZED, data.tier(id));
    }
}
