package bg.sofia.uni.fmi.mjt.space.rocket;

import bg.sofia.uni.fmi.mjt.space.dataset.MissionDatasets;
import bg.sofia.uni.fmi.mjt.space.dataset.RocketDatasets;
import bg.sofia.uni.fmi.mjt.space.mission.Mission;
import bg.sofia.uni.fmi.mjt.space.mission.MissionLoader;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RocketLoaderTest {

    @Test
    void testLoadNullReader() {
        assertThrows(IllegalArgumentException.class, () -> RocketLoader.load(null), "IllegalArgumentException expected");
    }

    @Test
    void testLoadDataset1() {
        List<Rocket> actual = RocketLoader.load(new StringReader(RocketDatasets.DATASET1_STRING));
        assertEquals(RocketDatasets.DATASET1_PARSED, actual, "Not what was expected");
    }

}