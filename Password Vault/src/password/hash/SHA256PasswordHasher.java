package password.hash;

import java.security.MessageDigest;

public class SHA256PasswordHasher implements PasswordHasher {
    private static final String ALGORITHM_NAME = "SHA-256";
    private static final String ENCODING = "UTF-8";

    @Override
    public String getHash(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM_NAME);
            messageDigest.update(password.getBytes(ENCODING));
            return new String(messageDigest.digest(), ENCODING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
