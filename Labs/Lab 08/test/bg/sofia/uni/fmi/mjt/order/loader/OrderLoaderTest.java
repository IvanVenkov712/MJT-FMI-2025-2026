package bg.sofia.uni.fmi.mjt.order.loader;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderLoaderTest {
    @Test
    void testLoadNullReader() {
        assertThrows(IllegalArgumentException.class, () -> OrderLoader.load(null), "IllegalArgumentException expected");
    }
}