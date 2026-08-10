package de.mittelalter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.mittelalter.block.ArrowSlitGeometry;
import java.util.List;
import org.junit.jupiter.api.Test;

class ArrowSlitGeometryTest {
    @Test
    void northSouthFrameLeavesCentralProjectileChannelOpen() {
        assertOpen(ArrowSlitGeometry.NORTH_SOUTH_FRAME, 8, 8, 0);
        assertOpen(ArrowSlitGeometry.NORTH_SOUTH_FRAME, 8, 8, 8);
        assertOpen(ArrowSlitGeometry.NORTH_SOUTH_FRAME, 8, 8, 16);
        assertCovered(ArrowSlitGeometry.NORTH_SOUTH_FRAME, 3, 8, 8);
        assertCovered(ArrowSlitGeometry.NORTH_SOUTH_FRAME, 8, 3, 8);
    }

    @Test
    void eastWestFrameLeavesRotatedProjectileChannelOpen() {
        assertOpen(ArrowSlitGeometry.EAST_WEST_FRAME, 0, 8, 8);
        assertOpen(ArrowSlitGeometry.EAST_WEST_FRAME, 8, 8, 8);
        assertOpen(ArrowSlitGeometry.EAST_WEST_FRAME, 16, 8, 8);
        assertCovered(ArrowSlitGeometry.EAST_WEST_FRAME, 8, 8, 3);
        assertCovered(ArrowSlitGeometry.EAST_WEST_FRAME, 8, 3, 8);
    }

    private static void assertOpen(List<ArrowSlitGeometry.Box> boxes, double x, double y, double z) {
        assertFalse(boxes.stream().anyMatch(box -> box.contains(x, y, z)));
    }

    private static void assertCovered(List<ArrowSlitGeometry.Box> boxes, double x, double y, double z) {
        assertTrue(boxes.stream().anyMatch(box -> box.contains(x, y, z)));
    }
}
