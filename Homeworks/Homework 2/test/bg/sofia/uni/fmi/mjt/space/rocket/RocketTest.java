package bg.sofia.uni.fmi.mjt.space.rocket;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RocketTest {
    @Test
    void testOfRocketNullLine() {
        assertThrows(IllegalArgumentException.class, () -> Rocket.of(null), "IllegalArgument expected");
    }

    @Test
    void testOfRocketShorterLine() {
        assertThrows(IllegalArgumentException.class, () -> Rocket.of("a,b,c"), "IllegalArgument expected");
    }

    @Test
    void testOfAllFieldsRocket() {
        Rocket expected = new Rocket("23", "Zéphyr", Optional.of("https://fr.wikipedia.org/wiki/Z%C3%A9phyr_(fus%C3%A9e)"), Optional.of(12.3));
        Rocket actual = Rocket.of("23,Zéphyr,https://fr.wikipedia.org/wiki/Z%C3%A9phyr_(fus%C3%A9e),12.3 m");
        assertEquals(expected, actual, "Not what was expected");
    }

    @Test
    void testOfNoWiki() {
        Rocket expected = new Rocket("23", "Zéphyr", Optional.empty(), Optional.of(12.3));
        Rocket actual = Rocket.of("23,Zéphyr,,12.3 m");
        assertEquals(expected, actual, "Not what was expected");
    }

    @Test
    void testOfNoHeight() {
        Rocket expected = new Rocket("23", "Zéphyr", Optional.of("https://fr.wikipedia.org/wiki/Z%C3%A9phyr_(fus%C3%A9e)"), Optional.empty());
        Rocket actual = Rocket.of("23,Zéphyr,https://fr.wikipedia.org/wiki/Z%C3%A9phyr_(fus%C3%A9e),");
        assertEquals(expected, actual, "Not what was expected");
    }

    @Test
    void testOfNoWikiNoHeight1() {
        Rocket expected = new Rocket("23", "Zéphyr", Optional.empty(), Optional.empty());
        Rocket actual = Rocket.of("23,Zéphyr,,");
        assertEquals(expected, actual, "Not what was expected");
    }
}