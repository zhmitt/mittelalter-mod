package de.mittelalter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.mittelalter.tournament.TournamentMode;
import de.mittelalter.tournament.TournamentSession;

class TournamentSessionTest {
    @Test
    void onlyEligibleMeleeKillsProgressAndFastCompletionIsChampion() {
        TournamentSession session = new TournamentSession(TournamentMode.MELEE, 100, 0, 64, 0);
        assertEquals(TournamentSession.Outcome.IGNORED, session.recordKill(TournamentMode.ARCHERY, true, 0, 64, 0, 110));
        assertEquals(TournamentSession.Outcome.IGNORED, session.recordKill(TournamentMode.MELEE, false, 0, 64, 0, 110));
        assertEquals(TournamentSession.Outcome.IGNORED, session.recordKill(TournamentMode.MELEE, true, 20, 64, 0, 110));
        assertEquals(TournamentSession.Outcome.PROGRESSED, session.recordKill(TournamentMode.MELEE, true, 0, 64, 0, 120));
        assertEquals(TournamentSession.Outcome.PROGRESSED, session.recordKill(TournamentMode.MELEE, true, 0, 64, 0, 130));
        assertEquals(TournamentSession.Outcome.CHAMPION, session.recordKill(TournamentMode.MELEE, true, 0, 64, 0, 140));
        assertEquals(3, session.kills());
    }

    @Test
    void slowCompletionIsNormalAndExpiredKillsAreRejected() {
        TournamentSession normal = new TournamentSession(TournamentMode.ARCHERY, 0, 0, 0, 0);
        normal.recordKill(TournamentMode.ARCHERY, true, 0, 0, 0, TournamentSession.CHAMPION_TICKS + 1);
        normal.recordKill(TournamentMode.ARCHERY, true, 0, 0, 0, TournamentSession.CHAMPION_TICKS + 2);
        assertEquals(TournamentSession.Outcome.COMPLETED,
                normal.recordKill(TournamentMode.ARCHERY, true, 0, 0, 0, TournamentSession.CHAMPION_TICKS + 3));

        TournamentSession expired = new TournamentSession(TournamentMode.MELEE, 0, 0, 0, 0);
        assertTrue(expired.isExpired(TournamentSession.DURATION_TICKS));
        assertEquals(TournamentSession.Outcome.IGNORED,
                expired.recordKill(TournamentMode.MELEE, true, 0, 0, 0, TournamentSession.DURATION_TICKS));
    }
}
