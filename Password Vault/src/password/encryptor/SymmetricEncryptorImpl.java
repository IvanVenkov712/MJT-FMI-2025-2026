package password.encryptor;

import password.algorithm.SymmetricBlockCipher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class SymmetricEncryptorImpl implements SymmetricEncryptor {
    private static final String ENCODING = "UTF-8";

    private final SymmetricBlockCipher cipher;

    public SymmetricEncryptorImpl(SymmetricBlockCipher cipher) {
        this.cipher = cipher;
    }

    @Override
    public byte[] encrypt(String password) {
        try (var input = new ByteArrayInputStream(password.getBytes(ENCODING));
             var output = new ByteArrayOutputStream()) {

            cipher.encrypt(input, output);
            return output.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decrypt(byte[] encryptedPassword) {
        try (var input = new ByteArrayInputStream(encryptedPassword);
             var output = new ByteArrayOutputStream()) {

            cipher.decrypt(input, output);
            return output.toString(ENCODING);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
