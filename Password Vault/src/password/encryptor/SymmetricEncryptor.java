package password.encryptor;

public interface SymmetricEncryptor {
    String encrypt(String password);

    String decrypt(String encryptedPassword);
}
