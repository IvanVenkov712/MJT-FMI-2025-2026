package bg.sofia.uni.fmi.mjt.space;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVHelperTest {
    @Test
    void testParseCsvLineNormalFields() {
        String line = "4,Vanguard,https://en.wikipedia.org/wiki/Vanguard_(rocket),23.0 m";
        List<String> res = CSVHelper.parseCsvLine(line);
        List<String> expected = List.of("4", "Vanguard", "https://en.wikipedia.org/wiki/Vanguard_(rocket)", "23.0 m");
        assertEquals(expected, res, "Not what was expected");
    }

    @Test
    void testParseCsvLineSpacePaddedFields() {
        String line = "4, Vanguard, https://en.wikipedia.org/wiki/Vanguard_(rocket), 23.0 m";
        List<String> res = CSVHelper.parseCsvLine(line);
        List<String> expected = List.of("4", "Vanguard", "https://en.wikipedia.org/wiki/Vanguard_(rocket)", "23.0 m");
        assertEquals(expected, res, "Not what was expected");
    }

    @Test
    void testParseCsvLineSpaceQuotedFields() {
        String line = "4, Vanguard, \"https://e,,,, n.wikipedia.org/wiki/Vang uard_(rocket)\", \"23.0 m\"";
        List<String> res = CSVHelper.parseCsvLine(line);
        List<String> expected = List.of("4", "Vanguard", "https://e,,,, n.wikipedia.org/wiki/Vang uard_(rocket)", "23.0 m");
        assertEquals(expected, res, "Not what was expected");
    }

    @Test
    void testParseCsvLineSpaceEmptyFields() {
        String line = "4,  , \"https://e,,,, n.wikipedia.org/wiki/Vang uard_(rocket)\",   ";
        List<String> res = CSVHelper.parseCsvLine(line);
        List<String> expected = List.of("4", "", "https://e,,,, n.wikipedia.org/wiki/Vang uard_(rocket)", "");
        assertEquals(expected, res, "Not what was expected");
    }
}