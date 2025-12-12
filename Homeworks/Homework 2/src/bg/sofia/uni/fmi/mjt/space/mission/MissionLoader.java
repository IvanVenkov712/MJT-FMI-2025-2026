package bg.sofia.uni.fmi.mjt.space.mission;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.Collection;

public class MissionLoader {
    public static Collection<Mission> load(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("reader is null");
        }
        var buffReader = new BufferedReader(reader);
        return buffReader.lines().skip(1).map(Mission::of).toList();
    }
}
