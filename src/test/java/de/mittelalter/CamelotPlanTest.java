package de.mittelalter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import de.mittelalter.camelot.CamelotPlan;

class CamelotPlanTest {
    @Test void acceptsExactlyFlatClearFifteenByFifteenSite() {
        assertTrue(CamelotPlan.isValidSite(new Area(true, true)));
        assertEquals(15, CamelotPlan.SIZE);
        assertTrue(CamelotPlan.placements().stream().anyMatch(p -> p.element() == CamelotPlan.Element.TABLE));
        assertEquals(3, CamelotPlan.knightPositions().size());
    }

    @Test void rejectsMissingFloorOrBlockedVolume() {
        assertFalse(CamelotPlan.isValidSite(new Area(false, true)));
        assertFalse(CamelotPlan.isValidSite(new Area(true, false)));
    }

    private record Area(boolean floor, boolean clear) implements CamelotPlan.Area {
        public boolean hasSolidFloor(int x, int z) { return floor; }
        public boolean isClear(int x, int y, int z) { return clear; }
    }
}
