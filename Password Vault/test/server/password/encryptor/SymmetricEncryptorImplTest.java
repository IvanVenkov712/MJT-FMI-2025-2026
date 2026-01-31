package server.password.encryptor;

import org.junit.jupiter.api.Test;
import server.password.algorithm.Rijndael;

import javax.crypto.SecretKey;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static server.password.encryptor.SymmetricEncryptorImpl.generateSecretKey;

class SymmetricEncryptorImplTest {
    @Test
    void testEncryptMatchesTheOriginalNonEmpty() throws Exception {
        SecretKey key = generateSecretKey();
        Rijndael cipher = new Rijndael(key);
        SymmetricEncryptorImpl encryptor = new SymmetricEncryptorImpl(cipher);

        String original = "Hello, World! We will encrypt and then decrypt this very important message and hope it remains the same!";

        assertEquals(original, encryptor.decrypt(encryptor.encrypt(original)), "The message should remain unchanged!");
    }
}