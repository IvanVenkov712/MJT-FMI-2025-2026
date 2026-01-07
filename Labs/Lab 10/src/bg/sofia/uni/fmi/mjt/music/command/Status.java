package bg.sofia.uni.fmi.mjt.music.command;

public enum Status {
    OK("OK"),
    ERROR("ERROR");

    private final String statusStr;
    Status(String statusStr) {
        this.statusStr = statusStr;
    }

    @Override
    public String toString() {
        return statusStr;
    }

}
