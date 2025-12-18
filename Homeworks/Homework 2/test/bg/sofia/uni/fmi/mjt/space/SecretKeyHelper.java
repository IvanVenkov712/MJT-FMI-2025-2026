package bg.sofia.uni.fmi.mjt.space;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SecretKeyHelper {
    private static final int KILOBYTE = 1024;
    private static final String ENCRYPTION_ALGORITHM = "AES"; // //  Advanced Encryption Standard
    private static final String KEY_FILE_PATH = "secret.key";
    private static final int KEY_SIZE_IN_BITS = 128; // Key sizes like 192 or 256 might not be available on all systems

    public static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ENCRYPTION_ALGORITHM);
        keyGenerator.init(KEY_SIZE_IN_BITS);
        SecretKey secretKey = keyGenerator.generateKey();

        // In order to view the key in some text representation, we'll convert it to Base64 format
        // Comment the next two lines if that's not needed
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println("Generated Secret Key (Base64-encoded): " + base64Key);

        return secretKey;
    }

    public static void persistSecretKey(SecretKey secretKey, Path keyFilePath) throws IOException {
        byte[] keyBytes = secretKey.getEncoded();

        // Write key bytes to file
        Files.write(keyFilePath, keyBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        // (Optional) Set file permissions to restrict access
        Files.setPosixFilePermissions(keyFilePath, PosixFilePermissions.fromString("rw-------"));
    }

    public static SecretKey loadSecretKey(Path keyFilePath) throws IOException {
        byte[] keyBytes = Files.readAllBytes(keyFilePath);
        return new SecretKeySpec(keyBytes, ENCRYPTION_ALGORITHM);
    }
}
