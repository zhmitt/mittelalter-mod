package de.mittelalter.camelot;

import java.util.ArrayList;
import java.util.List;

/** Pure deterministic description of Camelot's compact 15 by 15 great hall. */
public final class CamelotPlan {
    public static final int RADIUS = 7;
    public static final int SIZE = 15;

    public enum Element { FLOOR, WALL, GATE, TABLE, BANNER }
    public record Placement(int x, int y, int z, Element element) {}
    public record KnightPosition(int x, int y, int z, String archetype) {}
    public interface Area {
        boolean hasSolidFloor(int x, int z);
        boolean isClear(int x, int y, int z);
    }

    private CamelotPlan() {}

    public static boolean isValidSite(Area area) {
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int z = -RADIUS; z <= RADIUS; z++) {
                if (!area.hasSolidFloor(x, z)) return false;
                for (int y = 0; y <= 5; y++) if (!area.isClear(x, y, z)) return false;
            }
        }
        return true;
    }

    public static List<Placement> placements() {
        List<Placement> result = new ArrayList<>();
        for (int x = -RADIUS; x <= RADIUS; x++) for (int z = -RADIUS; z <= RADIUS; z++)
            result.add(new Placement(x, 0, z, Element.FLOOR));
        for (int y = 1; y <= 4; y++) for (int i = -RADIUS; i <= RADIUS; i++) {
            result.add(new Placement(i, y, -RADIUS, Element.WALL));
            result.add(new Placement(i, y, RADIUS, Element.WALL));
            result.add(new Placement(-RADIUS, y, i, Element.WALL));
            result.add(new Placement(RADIUS, y, i, Element.WALL));
        }
        result.removeIf(p -> p.z() == -RADIUS && p.x() == 0 && p.y() <= 3);
        result.add(new Placement(0, 1, -RADIUS, Element.GATE));
        for (int x = -2; x <= 2; x++) for (int z = -2; z <= 2; z++)
            if (Math.abs(x) == 2 || Math.abs(z) == 2) result.add(new Placement(x, 1, z, Element.TABLE));
        result.add(new Placement(-5, 1, 0, Element.BANNER));
        result.add(new Placement(5, 1, 0, Element.BANNER));
        return List.copyOf(result);
    }

    public static List<KnightPosition> knightPositions() {
        return List.of(new KnightPosition(-3, 1, 2, "bedivere"),
                new KnightPosition(3, 1, 2, "gawain"), new KnightPosition(0, 1, 4, "lancelot"));
    }
}
