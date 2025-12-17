package bg.sofia.uni.fmi.mjt.space.rocket;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.List;

public class RocketLoader {
    public static List<Rocket> load(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("reader is null");
        }
        var buffReader = new BufferedReader(reader);
        return buffReader.lines().skip(1).map(Rocket::of).toList();
    }
}
