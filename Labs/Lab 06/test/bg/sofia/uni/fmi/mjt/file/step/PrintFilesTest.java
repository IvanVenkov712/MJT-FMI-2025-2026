package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PrintFilesTest {
    @Test
    void testProcessNullCollection() {
        assertThrows(IllegalArgumentException.class, () -> new PrintFiles().process(null),
            "PrintFiles().process called with null should throw IllegalArgumentException!"
        );
    }

    @Test
    void testProcessEmptyCollection() {
        Collection<File> empty = List.of();
        assertEquals(empty, new PrintFiles().process(empty),
            "For empty collection PrintFiles().process should return the same collection!"
        );
    }

    @Test
    void testProcessNonEmptyCollection() {
        List<File> nonEmpty = List.of(new File("File1"), new File("File2"));
        assertEquals(nonEmpty, new PrintFiles().process(nonEmpty),
            "For non-empty collection PrintFiles().process should return the same collection!"
        );

    }
}