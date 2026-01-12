package newsfeed.apikey;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ApiKeyProviderFromFileTest {
    ApiKeyProviderFromFile provider;
    @Test
    void testGetKeyFromFile(@TempDir Path tempDir) throws IOException {
        String key = "abcdefghijklmnop";
        Path fPath = tempDir.resolve("apikey.txt");
        Files.writeString(fPath, key);
        provider = new ApiKeyProviderFromFile(fPath);
        assertEquals(key, provider.getApiKey(), "Not what was expected!");
    }
}