package de.mittelalter.block;

import java.util.List;

/** Pixel-space geometry shared by the arrow-slit block and deterministic tests. */
public final class ArrowSlitGeometry {
    public record Box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        public boolean contains(double x, double y, double z) {
            return x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ;
        }
    }

    public static final List<Box> NORTH_SOUTH_FRAME = List.of(
            new Box(0, 0, 0, 6, 16, 16),
            new Box(10, 0, 0, 16, 16, 16),
            new Box(6, 0, 0, 10, 5, 16),
            new Box(6, 11, 0, 10, 16, 16));

    public static final List<Box> EAST_WEST_FRAME = List.of(
            new Box(0, 0, 0, 16, 16, 6),
            new Box(0, 0, 10, 16, 16, 16),
            new Box(0, 0, 6, 16, 5, 10),
            new Box(0, 11, 6, 16, 16, 10));

    private ArrowSlitGeometry() {
    }
}
