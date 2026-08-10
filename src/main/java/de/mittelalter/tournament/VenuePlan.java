package de.mittelalter.tournament;

import java.util.ArrayList;
import java.util.List;

/** Deterministic, Minecraft-independent description of the compact tournament yard. */
public final class VenuePlan {
    public static final int RADIUS = 4;
    public static final int SIZE = RADIUS * 2 + 1;

    public enum Element { STANDARD, FENCE, GATE, TARGET, SPECTATOR_MARKER }
    public record Placement(int x, int z, Element element) { }

    private VenuePlan() { }

    public static boolean isFlatAndClear(Area area) {
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int z = -RADIUS; z <= RADIUS; z++) {
                if (!area.hasSolidFloor(x, z) || !area.isClear(x, z, 0) || !area.isClear(x, z, 1)) return false;
            }
        }
        return true;
    }

    public static List<Placement> placements() {
        List<Placement> result = new ArrayList<>();
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int z = -RADIUS; z <= RADIUS; z++) {
                if (x == 0 && z == 0) result.add(new Placement(x, z, Element.STANDARD));
                else if (z == -RADIUS && Math.abs(x) == 2) result.add(new Placement(x, z, Element.TARGET));
                else if ((x == -RADIUS || x == RADIUS) && Math.abs(z) == 2) result.add(new Placement(x, z, Element.SPECTATOR_MARKER));
                else if ((x == 0 && Math.abs(z) == RADIUS) || (z == 0 && Math.abs(x) == RADIUS))
                    result.add(new Placement(x, z, Element.GATE));
                else if (Math.abs(x) == RADIUS || Math.abs(z) == RADIUS)
                    result.add(new Placement(x, z, Element.FENCE));
            }
        }
        return List.copyOf(result);
    }

    public interface Area {
        boolean hasSolidFloor(int x, int z);
        boolean isClear(int x, int z, int y);
    }
}
