package bg.sofia.uni.fmi.mjt.space.rocket;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RocketStatusTest {
    @Test
    void testOfNullString() {
        assertNull(RocketStatus.of(null), "null expected");
    }

    @Test
    void testOfBlankString() {
        assertNull(RocketStatus.of("   "), "Null expected");
    }

    @Test
    void testOfStatusActive() {
        assertEquals(RocketStatus.STATUS_ACTIVE, RocketStatus.of("StatusActive"), "Not what was expected");
    }

    @Test
    void testOfStatusRetired() {
        assertEquals(RocketStatus.STATUS_RETIRED, RocketStatus.of("StatusRetired"), "Not what was expected");
    }

    @Test
    void testOfInvalidString() {
        assertNull(RocketStatus.of("dniubxiusbuydb"), "Null expected");
    }

}