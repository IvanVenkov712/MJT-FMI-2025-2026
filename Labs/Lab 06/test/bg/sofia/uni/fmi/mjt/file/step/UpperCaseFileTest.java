package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpperCaseFileTest {
    @Test
    void testProcessNullFile() {
        assertThrows(
            IllegalArgumentException.class, () -> new UpperCaseFile().process(null),
            "Called with null file CheckEmptyFile.process should throw IllegalArgumentException"
        );
    }

    @Test
    void testProcessNullContent() {
        assertThrows(
            IllegalArgumentException.class, () -> new UpperCaseFile().process(new File(null)),
            "Called with null file CheckEmptyFile.process should throw IllegalArgumentException"
        );
    }

    @Test
    void testProcessNotEmpty() {
        assertEquals(
            "HELLO, WORLD!", new UpperCaseFile().process(new File("Hello, world!")).getContent(),
            "expected HELLO, WORLD!"
        );
    }
}