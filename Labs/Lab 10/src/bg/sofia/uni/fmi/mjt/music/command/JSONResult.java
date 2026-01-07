package bg.sofia.uni.fmi.mjt.music.command;

public class JSONResult {
    public static String formatAsJSON(Status status, String message) {
        return String.format("{\"status\":\"%s\",\"message\":\"%s\"}", status, message);
    }

    public static String formatAsJSON(Status status, String message, Object... args) {
        return formatAsJSON(status, String.format(message, args));
    }
}