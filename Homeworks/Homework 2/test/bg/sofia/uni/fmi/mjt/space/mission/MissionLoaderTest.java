package bg.sofia.uni.fmi.mjt.space.mission;

import static org.junit.jupiter.api.Assertions.*;

class MissionLoaderTest {
    void testLoadNullReader() {
        assertThrows(IllegalArgumentException.class, () -> MissionLoader.load(null), "IllegalArgumentException expected");
    }

    void testLoadDataset1() {

    }
}