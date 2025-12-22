package bg.sofia.uni.fmi.mjt.space.mission;

import bg.sofia.uni.fmi.mjt.space.rocket.Rocket;
import bg.sofia.uni.fmi.mjt.space.rocket.RocketStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MissionTest {
    @Test
    void testOfMissionNullLine() {
        assertThrows(IllegalArgumentException.class, () -> Mission.of(null), "IllegalArgument expected");
    }

    @Test
    void testOfMissionShorterLine() {
        assertThrows(IllegalArgumentException.class, () -> Mission.of("a,b,c"), "IllegalArgument expected");
    }

    @Test
    void testOfAllFieldsMission() {
        Mission res = Mission.of("11,ExPace,\"Site 95, Jiuquan Satellite Launch Center, China\",\"Fri Jul 10, 2020\",\"Kuaizhou 11 | Jilin-1 02E, CentiSpace-1 S2\",StatusActive,\"28.3 \",Failure");
        Mission expected = new Mission("11", "ExPace", "Site 95, Jiuquan Satellite Launch Center, China", LocalDate.parse("Fri Jul 10, 2020", DateTimeFormatter.ofPattern("EEE MMM dd, yyyy", Locale.ENGLISH)), new Detail("Kuaizhou 11", "Jilin-1 02E, CentiSpace-1 S2"), RocketStatus.STATUS_ACTIVE, Optional.of(28.3), MissionStatus.FAILURE);
        assertEquals(expected, res, "Not what was expected");
    }

    @Test
    void testOfAllNoCost() {
        Mission res = Mission.of("11,ExPace,\"Site 95, Jiuquan Satellite Launch Center, China\",\"Fri Jul 10, 2020\",\"Kuaizhou 11 | Jilin-1 02E, CentiSpace-1 S2\",StatusActive,,Failure");
        Mission expected = new Mission("11", "ExPace", "Site 95, Jiuquan Satellite Launch Center, China", LocalDate.parse("Fri Jul 10, 2020", DateTimeFormatter.ofPattern("EEE MMM dd, yyyy", Locale.ENGLISH)), new Detail("Kuaizhou 11", "Jilin-1 02E, CentiSpace-1 S2"), RocketStatus.STATUS_ACTIVE, Optional.empty(), MissionStatus.FAILURE);
        assertEquals(expected, res, "Not what was expected");
    }

    @Test
    void testOfAllFieldsUnquotedDetail() {
        Mission res = Mission.of("11,ExPace,\"Site 95, Jiuquan Satellite Launch Center, China\",\"Fri Jul 10, 2020\",Kuaizhou 11 | Jilin-1 02E CentiSpace-1 S2,StatusActive,\"28.3 \",Failure");
        Mission expected = new Mission("11", "ExPace", "Site 95, Jiuquan Satellite Launch Center, China", LocalDate.parse("Fri Jul 10, 2020", DateTimeFormatter.ofPattern("EEE MMM dd, yyyy", Locale.ENGLISH)), new Detail("Kuaizhou 11", "Jilin-1 02E CentiSpace-1 S2"), RocketStatus.STATUS_ACTIVE, Optional.of(28.3), MissionStatus.FAILURE);
        assertEquals(expected, res, "Not what was expected");
    }
    @Test
    void testGetCountryValidDataCommaInLocation() {
        Mission mission = new Mission("11", "ExPace", "Site 95, Jiuquan Satellite Launch Center, China", LocalDate.parse("Fri Jul 10, 2020", DateTimeFormatter.ofPattern("EEE MMM dd, yyyy", Locale.ENGLISH)), new Detail("Kuaizhou 11", "Jilin-1 02E, CentiSpace-1 S2"), RocketStatus.STATUS_ACTIVE, Optional.of(28.3), MissionStatus.FAILURE);
        String country = mission.getCountry();
        assertEquals("China", country, "Not what was expected");
    }

    @Test
    void testGetCountryNoCommaInLocation() {
        Mission mission = new Mission("11", "ExPace", "Jiuquan Satellite Launch Center", LocalDate.parse("Fri Jul 10, 2020", DateTimeFormatter.ofPattern("EEE MMM dd, yyyy", Locale.ENGLISH)), new Detail("Kuaizhou 11", "Jilin-1 02E, CentiSpace-1 S2"), RocketStatus.STATUS_ACTIVE, Optional.of(28.3), MissionStatus.FAILURE);
        String country = mission.getCountry();
        assertEquals("Jiuquan Satellite Launch Center", country, "Not what was expected");
    }
}