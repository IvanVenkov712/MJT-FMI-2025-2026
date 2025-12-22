package bg.sofia.uni.fmi.mjt.space.mission;

import bg.sofia.uni.fmi.mjt.space.rocket.RocketStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MissionStatusTest {
    @Test
    void testOfNullString() {
        assertNull(MissionStatus.of(null), "null expected");
    }

    @Test
    void testOfBlankString() {
        assertNull(MissionStatus.of("   "), "Null expected");
    }

    @Test
    void testOfSuccess() {
        assertEquals(MissionStatus.SUCCESS, MissionStatus.of("Success"), "Not what was expected");
    }

    @Test
    void testOfFailure() {
        assertEquals(MissionStatus.FAILURE, MissionStatus.of("Failure"), "Not what was expected");
    }

    @Test
    void testOfPartialFailure() {
        assertEquals(MissionStatus.PARTIAL_FAILURE, MissionStatus.of("Partial Failure"), "Not what was expected");
    }

    @Test
    void testOfPrelaunchFailure() {
        assertEquals(MissionStatus.PRELAUNCH_FAILURE, MissionStatus.of("Prelaunch Failure"), "Not what was expected");
    }

    @Test
    void testOfInvalidString() {
        assertNull(MissionStatus.of("dniubxiusbuydb"), "Null expected");
    }


}