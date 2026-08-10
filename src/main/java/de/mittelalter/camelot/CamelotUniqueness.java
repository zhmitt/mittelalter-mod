package de.mittelalter.camelot;

import java.util.Optional;

/** Minecraft-independent uniqueness rule used by the world saved-data adapter. */
public final class CamelotUniqueness {
    public record Location(int x, int y, int z) {}
    private Optional<Location> location;
    public CamelotUniqueness() { this(Optional.empty()); }
    public CamelotUniqueness(Optional<Location> location) { this.location = location; }
    public Optional<Location> location() { return location; }
    public boolean claim(Location proposed) {
        if (location.isPresent()) return false;
        location = Optional.of(proposed);
        return true;
    }
}
