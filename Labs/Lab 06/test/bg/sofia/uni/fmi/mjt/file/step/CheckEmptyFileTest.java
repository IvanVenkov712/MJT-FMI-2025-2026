package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.file.exception.EmptyFileException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckEmptyFileTest {
    @Test
    void testProcessEmptyFile() {
        File empty = new File("");
        assertThrows(EmptyFileException.class, ()->new CheckEmptyFile().process(empty),
            "CheckEmptyFile.process should throw EmptyFileException on empty file!"
        );
    }

    @Test
    void testProcessNonEmptyFile() {
        File nonEmpty = new File("a");
        assertEquals(nonEmpty, new CheckEmptyFile().process(nonEmpty),
            "CheckEmptyFile.process should return the file itself when not empty!"
        );
    }
}