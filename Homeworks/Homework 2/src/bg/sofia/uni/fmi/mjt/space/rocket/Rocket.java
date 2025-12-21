package bg.sofia.uni.fmi.mjt.space.rocket;

import bg.sofia.uni.fmi.mjt.space.CSVHelper;

import java.util.List;
import java.util.Optional;

public record Rocket(String id, String name, Optional<String> wiki, Optional<Double> height) {
    private static final int ID_INDEX = 0;
    private static final int NAME_INDEX = 1;
    private static final int WIKI_INDEX = 2;
    private static final int HEIGHT_INDEX = 3;
    private static final int LINE_SIZE = 4;

    public static Rocket of(String line) {
        if (line == null) {
            throw new IllegalArgumentException("line is null");
        }

        List<String> fields = CSVHelper.parseCsvLine(line);
        if (fields.size() < LINE_SIZE) {
            throw new IllegalArgumentException("Invalid CSV line: " + line);
        }
        String id = fields.get(ID_INDEX);
        String name = fields.get(NAME_INDEX);
        String wikiPart = fields.get(WIKI_INDEX);
        Optional<String> wiki = wikiPart.isEmpty() ? Optional.empty() : Optional.of(wikiPart);
        Optional<Double> height = readHeight(fields.get(HEIGHT_INDEX));

        return new Rocket(id, name, wiki, height);
    }

    private static Optional<Double> readHeight(String str) {
        if (str.isEmpty()) {
            return Optional.empty();
        }
        String cleaned = str.replaceAll("[^0-9.\\-]", "");
        if (cleaned.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(Double.parseDouble(cleaned));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Rocket rocket = (Rocket) o;
        return Objects.equals(id, rocket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
     */
}
