package bg.sofia.uni.fmi.mjt.space.rocket;

public enum RocketStatus {
    STATUS_RETIRED("StatusRetired"),
    STATUS_ACTIVE("StatusActive");

    private final String value;

    RocketStatus(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public static RocketStatus of(String s) {
        if (s == null || s.isBlank()) {
            throw new IllegalArgumentException("s is not valid!");
        }
        return switch (s) {
            case "StatusActive" -> STATUS_ACTIVE;
            case "StatusRetired" -> STATUS_RETIRED;
            default ->throw new IllegalArgumentException("s is not valid!");
        };
    }
}