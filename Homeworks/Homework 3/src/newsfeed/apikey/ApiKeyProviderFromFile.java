package newsfeed.apikey;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ApiKeyProviderFromFile implements ApiKeyProvider {
    private final Path filePath;

    public ApiKeyProviderFromFile(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getApiKey() {
        try {
            return Files.readString(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
