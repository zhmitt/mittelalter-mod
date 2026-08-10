package de.mittelalter.tournament;

public enum TournamentMode {
    MELEE, ARCHERY;

    public TournamentMode next() { return this == MELEE ? ARCHERY : MELEE; }
    public String translationSuffix() { return name().toLowerCase(); }
}
