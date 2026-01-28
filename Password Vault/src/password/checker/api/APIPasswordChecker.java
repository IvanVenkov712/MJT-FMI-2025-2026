package password.checker.api;

import password.checker.PasswordChecker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class APIPasswordChecker implements PasswordChecker {
    private static final String API_KEY_PATH = "ApiKey";
    private static final String API_SECRET_PATH = "ApiSecret";

    private final String apiKey;
    private final String apiSecret;

    public APIPasswordChecker(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public APIPasswordChecker(Path apiKeyPath, Path apiSecretPath) {
        String apiKey;
        String apiSecret;
        try {
            apiKey = Files.readString(apiKeyPath);
            apiSecret = Files.readString(apiSecretPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this(apiKey, apiSecret);
    }

    public APIPasswordChecker() {
        this(Path.of(API_KEY_PATH), Path.of(API_SECRET_PATH));
    }

    @Override
    public boolean isPasswordSecure(String password) {

    }
}
