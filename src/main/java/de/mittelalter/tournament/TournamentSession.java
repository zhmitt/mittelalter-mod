package de.mittelalter.tournament;

public final class TournamentSession {
    public static final int REQUIRED_KILLS = 3;
    public static final long DURATION_TICKS = 20L * 90L;
    public static final long CHAMPION_TICKS = 20L * 30L;
    public static final long COOLDOWN_TICKS = 20L * 60L * 5L;
    public static final double VENUE_RADIUS = 8.0;

    public enum Outcome { IGNORED, PROGRESSED, COMPLETED, CHAMPION }

    private final TournamentMode mode;
    private final long startedAt;
    private final int anchorX;
    private final int anchorY;
    private final int anchorZ;
    private int kills;

    public TournamentSession(TournamentMode mode, long startedAt, int anchorX, int anchorY, int anchorZ) {
        this.mode = mode;
        this.startedAt = startedAt;
        this.anchorX = anchorX;
        this.anchorY = anchorY;
        this.anchorZ = anchorZ;
    }

    public TournamentMode mode() { return mode; }
    public int kills() { return kills; }
    public long startedAt() { return startedAt; }
    public boolean isExpired(long now) { return now - startedAt >= DURATION_TICKS; }
    public boolean isInside(double x, double y, double z) {
        double dx = x - (anchorX + 0.5), dy = y - (anchorY + 0.5), dz = z - (anchorZ + 0.5);
        return dx * dx + dy * dy + dz * dz <= VENUE_RADIUS * VENUE_RADIUS;
    }

    public Outcome recordKill(TournamentMode attackMode, boolean hostile, double x, double y, double z, long now) {
        if (!hostile || attackMode != mode || isExpired(now) || !isInside(x, y, z) || kills >= REQUIRED_KILLS)
            return Outcome.IGNORED;
        kills++;
        if (kills < REQUIRED_KILLS) return Outcome.PROGRESSED;
        return now - startedAt <= CHAMPION_TICKS ? Outcome.CHAMPION : Outcome.COMPLETED;
    }
}
