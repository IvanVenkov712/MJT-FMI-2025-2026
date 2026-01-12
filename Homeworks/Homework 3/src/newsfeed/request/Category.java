package newsfeed.request;

public enum Category {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology");

    private final String str;
    Category(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
