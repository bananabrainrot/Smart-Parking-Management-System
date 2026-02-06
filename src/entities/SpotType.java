package entities;

public enum SpotType {
    STANDARD,
    DISABLED,
    ELECTRIC;

    public static SpotType fromSpotNumber(String spotNumber) {
        if (spotNumber == null || spotNumber.isBlank()) {
            return STANDARD;
        }
        String normalized = spotNumber.trim().toUpperCase();
        if (normalized.startsWith("D")) {
            return DISABLED;
        }
        if (normalized.startsWith("E")) {
            return ELECTRIC;
        }
        return STANDARD;
    }
}
