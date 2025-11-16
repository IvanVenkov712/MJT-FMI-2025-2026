package bg.sofia.uni.fmi.mjt.pipeline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {
    @Test
    void testCacheValueNullKey() {
        Cache cache = new Cache();
        assertThrows( IllegalArgumentException.class, () -> cache.cacheValue(null, new Object()),
            "Null key or value should throw IllegalArgumentException"
        );
    }

    @Test
    void testCacheValueNullValue() {
        Cache cache = new Cache();
        assertThrows( IllegalArgumentException.class, () -> cache.cacheValue(new Object(), null),
            "Null key or value should throw IllegalArgumentException"
        );
    }

    @Test
    void testGetCashedValueNullKey() {
        Cache cache = new Cache();
        assertThrows( IllegalArgumentException.class, () -> cache.getCachedValue(null),
            "Null key or value should throw IllegalArgumentException"
        );
    }

    @Test
    void testContainsKeyNullKey() {
        Cache cache = new Cache();
        assertThrows( IllegalArgumentException.class, () -> cache.containsKey(null),
            "Null key or value should throw IllegalArgumentException"
        );
    }

}