package lukeisbrecht.notrampling.config;

public enum TrampleProtection {
    FULL(3, "Full"),
    MOBS_ONLY(2, "Mobs Only"),
    PLAYERS_ONLY(1, "Players Only"),
    NONE(0, "None");

    private final int value;
    private final String displayName;

    TrampleProtection(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static TrampleProtection fromInt(int value) {
        for (TrampleProtection d : values()) {
            if (d.value == value) {
                return d;
            }
        }
        return FULL;
    }

    public TrampleProtection next() {
        int nextIndex = (this.ordinal() + 1) % values().length;
        return values()[nextIndex];
    }

    public TrampleProtection previous() {
        int prevIndex = (this.ordinal() - 1 + values().length) % values().length;
        return values()[prevIndex];
    }
}
