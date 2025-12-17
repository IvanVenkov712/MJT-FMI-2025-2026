package bg.sofia.uni.fmi.mjt.space.mission;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.List;

public class MissionLoader {
    public static List<Mission> load(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("reader is null");
        }
        var buffReader = new BufferedReader(reader);
        return buffReader.lines().skip(1).map(Mission::of).toList();
    }
}
