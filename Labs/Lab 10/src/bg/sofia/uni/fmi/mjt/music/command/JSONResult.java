package bg.sofia.uni.fmi.mjt.music.command;

import bg.sofia.uni.fmi.mjt.music.server.model.Song;

import java.util.Collection;
import java.util.Iterator;

public class JSONResult {
    public static String formatAsJSON(Status status, String message) {
        return formatAsJSON(status, "message", message);
    }

    public static String formatAsJSON(Status status, String message, Object... args) {
        return formatAsJSON(status, String.format(message, args));
    }

    public static String formatAsJSON(Status status, String msgName, String message) {
        return String.format("{\"status\":\"%s\",\"%s\":\"%s\"}", status, msgName, message);
    }

    public static String asJSONArray(String[] elems) {
        if (elems.length == 0) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < elems.length - 1; ++i) {
            builder.append("\"" + elems[i] + "\",");
        }
        builder.append( "\"" + elems[elems.length - 1] + "\"]");
        return builder.toString();
    }

    public static String asJSONArray(Collection<?> elems) {
        if (elems.isEmpty()) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        Iterator<?> iter = elems.iterator();
        for (int i = 0; i < elems.size() - 1; ++i) {
            Object o = iter.next();
            builder.append(String.format("\"%s\",", o));
        }
        builder.append(String.format("\"%s\"", iter.next()));
        return builder.toString();
    }

    public static String getJSONFromSong(Song song, int likes) {
        return String.format(
            "{\"title\":\"%s\",\"artist\":\"%s\",\"duration\":\"%d\",\"likes\":\"%d\"}", song.title(), song.artist(),
            song.duration(), likes);
    }

}