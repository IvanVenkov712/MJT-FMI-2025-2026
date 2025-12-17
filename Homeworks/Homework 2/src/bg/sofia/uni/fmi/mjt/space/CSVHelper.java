package bg.sofia.uni.fmi.mjt.space;

import java.util.ArrayList;
import java.util.List;

public class CSVHelper {

    public static List<String> parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        if (line == null || line.isEmpty()) return result;

        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(stripSurroundingQuotes(sb.toString()));
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        result.add(stripSurroundingQuotes(sb.toString()));
        return result;
    }

    private static String stripSurroundingQuotes(String s) {
        String t = s.strip();
        if (t.length() >= 2 && t.startsWith("\"") && t.endsWith("\"")) {
            return t.substring(1, t.length() - 1).strip();
        }
        return t;
    }
}
