package de.mittelalter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import de.mittelalter.tournament.VenuePlan;

class TournamentVenuePlanTest {
    @Test
    void deterministicPlanContainsCompactVenueElements() {
        var plan = VenuePlan.placements();
        assertEquals(33, plan.size());
        assertEquals(1, count(plan, VenuePlan.Element.STANDARD));
        assertEquals(4, count(plan, VenuePlan.Element.GATE));
        assertEquals(2, count(plan, VenuePlan.Element.TARGET));
        assertEquals(4, count(plan, VenuePlan.Element.SPECTATOR_MARKER));
        assertEquals(22, count(plan, VenuePlan.Element.FENCE));
        assertEquals(plan, VenuePlan.placements());
    }

    @Test
    void validationRequiresEveryFloorAndBothClearLayers() {
        assertTrue(VenuePlan.isFlatAndClear(new TestArea(null, null)));
        assertFalse(VenuePlan.isFlatAndClear(new TestArea(new Cell(3, -2, 0), null)));
        assertFalse(VenuePlan.isFlatAndClear(new TestArea(null, new Cell(-4, 4, 1))));
    }

    private static long count(java.util.List<VenuePlan.Placement> plan, VenuePlan.Element element) {
        return plan.stream().filter(p -> p.element() == element).count();
    }

    private record Cell(int x, int z, int y) { }
    private record TestArea(Cell missingFloor, Cell obstruction) implements VenuePlan.Area {
        @Override public boolean hasSolidFloor(int x, int z) {
            return missingFloor == null || missingFloor.x != x || missingFloor.z != z;
        }
        @Override public boolean isClear(int x, int z, int y) {
            return obstruction == null || obstruction.x != x || obstruction.z != z || obstruction.y != y;
        }
    }
}
