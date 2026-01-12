package newsfeed.request;

public enum Country {
    US("us");

    private final String str;
    Country(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }
}
