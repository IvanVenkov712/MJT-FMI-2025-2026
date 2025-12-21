package bg.sofia.uni.fmi.mjt.space.mission;

import bg.sofia.uni.fmi.mjt.space.dataset.MissionDatasets;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MissionLoaderTest {
    @Test
    void testLoadMissionLoaderNullReader() {
        assertThrows(IllegalArgumentException.class, () -> MissionLoader.load(null), "IllegalArgumentException expected");
    }

    @Test
    void testLoadMissionLoaderDataset1() {
        List<Mission> actual = MissionLoader.load(new StringReader(MissionDatasets.DATASET1_STRING));
        assertEquals(MissionDatasets.DATASET1_PARSED, actual, "Not what was expected");
    }
}