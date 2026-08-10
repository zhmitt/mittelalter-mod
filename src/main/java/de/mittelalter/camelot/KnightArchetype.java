package de.mittelalter.camelot;

public enum KnightArchetype {
    BEDIVERE("Sir Bedivere", KnightDisposition.MENTOR),
    GAWAIN("Sir Gawain", KnightDisposition.ALLY),
    LANCELOT("Sir Lancelot", KnightDisposition.RIVAL);
    private final String displayName;
    private final KnightDisposition disposition;
    KnightArchetype(String displayName, KnightDisposition disposition) {
        this.displayName = displayName;
        this.disposition = disposition;
    }
    public String displayName() { return displayName; }
    public KnightDisposition disposition() { return disposition; }
}
