public enum Difficulty {
    MUDAH("mudah", 1),
    MENENGAH("menengah", 6),
    SULIT("sulit", 16);

    private final String displayName;
    private final int minWandererLevel;

    Difficulty(String displayName, int minWandererLevel) {
        this.displayName = displayName;
        this.minWandererLevel = minWandererLevel;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMinWandererLevel() {
        return minWandererLevel;
    }

    public static Difficulty fromString(String raw) {
        if (raw == null) return null;
        for (Difficulty difficulty : Difficulty.values()) {
            if (difficulty.displayName.equalsIgnoreCase(raw.trim())) {
                return difficulty;
            }
        }
        return null;
    }
}