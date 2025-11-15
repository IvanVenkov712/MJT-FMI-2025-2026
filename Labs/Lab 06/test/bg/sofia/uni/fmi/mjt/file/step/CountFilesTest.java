package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountFilesTest {
    @Test
    void testProcessNullCollection() {
        assertThrows(IllegalArgumentException.class, () -> new CountFiles().process(null),
            "CountFiles should throw InvalidArgumentException for null collection!"
        );
    }

    @Test
    void testProcessZeroSize() {
        assertEquals(0, new CountFiles().process(List.of()),
            "CountFiles.process should return 0 for empty collection!"
        );
    }

    @Test
    void testProcessNonZeroSize() {
        Collection<File> nonEmpty = mock(Collection.class);
        when(nonEmpty.size()).thenReturn(20);

        assertEquals(20, new CountFiles().process(nonEmpty),
            "Expected result: 20"
        );

        verify(nonEmpty).size();
    }
}