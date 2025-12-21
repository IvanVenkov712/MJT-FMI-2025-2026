package bg.sofia.uni.fmi.mjt.space.algorithm;

import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static bg.sofia.uni.fmi.mjt.space.SecretKeyHelper.generateSecretKey;
import static org.junit.jupiter.api.Assertions.*;

class RijndaelTest {
    @Test
    void testEncryptMatchesTheOriginalNonEmpty() throws Exception {
        SecretKey key = generateSecretKey();
        Rijndael cipher = new Rijndael(key);

        byte[] original = "Hello, World! We will encrypt and then decrypt this very important message and hope it remains the same!".getBytes();

        try(ByteArrayInputStream input = new ByteArrayInputStream(original);
            ByteArrayOutputStream encrypted = new ByteArrayOutputStream()
        ){
            cipher.encrypt(input, encrypted);
            try(
                ByteArrayInputStream encryptedInput = new ByteArrayInputStream(encrypted.toByteArray());
                ByteArrayOutputStream decrypted = new ByteArrayOutputStream()){
                cipher.decrypt(encryptedInput, decrypted);
                byte[] actual = decrypted.toByteArray();

                assertArrayEquals(original, actual, "Not what was expected!");
            }
        }
        assertTrue(true);
    }

    @Test
    void testEncryptEmpty() throws Exception {
        SecretKey key = generateSecretKey();
        Rijndael cipher = new Rijndael(key);

        byte[] original = new byte[0];

        try(ByteArrayInputStream input = new ByteArrayInputStream(original);
            ByteArrayOutputStream output = new ByteArrayOutputStream()
        ){
            cipher.encrypt(input, output);
            try(
                ByteArrayInputStream encryptedInput = new ByteArrayInputStream(output.toByteArray());
                ByteArrayOutputStream decrypted = new ByteArrayOutputStream()){
                cipher.decrypt(encryptedInput, decrypted);
                byte[] actual = decrypted.toByteArray();

                assertArrayEquals(original, actual, "Not what was expected!");
            }
        }
        assertTrue(true);
    }
}