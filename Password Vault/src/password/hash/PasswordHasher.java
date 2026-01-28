package password.hash;

public interface PasswordHasher {
    String getHash(String password);
}
