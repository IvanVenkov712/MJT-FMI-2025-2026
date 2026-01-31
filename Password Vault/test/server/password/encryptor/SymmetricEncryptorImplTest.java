package server.password.encryptor;

import org.junit.jupiter.api.Test;
import server.password.algorithm.Rijndael;

import javax.crypto.SecretKey;

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

    @Test
    void testEncryptEmpty() throws Exception {
        SecretKey key = generateSecretKey();
        Rijndael cipher = new Rijndael(key);
        SymmetricEncryptorImpl encryptor = new SymmetricEncryptorImpl(cipher);

        String original = "";
        assertEquals(original, encryptor.decrypt(encryptor.encrypt(original)), "Empty string should remain empty");
    }

    @Test
    void testEncryptMultiplePasswords() throws Exception {
        SecretKey key = generateSecretKey();
        Rijndael cipher = new Rijndael(key);
        SymmetricEncryptorImpl encryptor = new SymmetricEncryptorImpl(cipher);

        String[] passwords = {"password1", "pass@123", "P@ss!23", "secure_pwd_2024"};
        for (String password : passwords) {
            assertEquals(password, encryptor.decrypt(encryptor.encrypt(password)),
                "Password " + password + " should be preserved");
        }
    }

    @Test
    void testEncryptLongPassword() throws Exception {
        SecretKey key = generateSecretKey();
        Rijndael cipher = new Rijndael(key);
        SymmetricEncryptorImpl encryptor = new SymmetricEncryptorImpl(cipher);

        String original = "A".repeat(500);
        assertEquals(original, encryptor.decrypt(encryptor.encrypt(original)), "Long password should be preserved");
    }

    @Test
    void testEncryptAndDecryptReturnsDifferentBytes() throws Exception {
        SecretKey key = generateSecretKey();
        Rijndael cipher = new Rijndael(key);
        SymmetricEncryptorImpl encryptor = new SymmetricEncryptorImpl(cipher);

        String original = "Secret";
        byte[] encrypted = encryptor.encrypt(original);

        assertNotEquals(original, new String(encrypted, "UTF-8"), "Encrypted bytes should differ from original");
    }

    @Test
    void testMultipleEncryptionSamePassword() throws Exception {
        SecretKey key = generateSecretKey();
        Rijndael cipher = new Rijndael(key);
        SymmetricEncryptorImpl encryptor = new SymmetricEncryptorImpl(cipher);

        String password = "TestPassword";
        byte[] encrypted1 = encryptor.encrypt(password);
        byte[] encrypted2 = encryptor.encrypt(password);

        assertArrayEquals(encrypted1, encrypted2, "Same plaintext should encrypt consistently");
    }

}