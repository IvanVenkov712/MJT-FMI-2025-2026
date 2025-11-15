package bg.sofia.uni.fmi.mjt.file;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileTest {
    @Test
    void testConstructorEmptyContent() {
        assertThrows(
            IllegalArgumentException.class, () -> new File(null),
            "Constructor should throw IllegalArgumentException with null content"
        );
    }

}