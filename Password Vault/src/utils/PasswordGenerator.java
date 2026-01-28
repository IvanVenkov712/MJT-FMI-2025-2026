package utils;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String CHARACTERS =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789" +
            "!@#$%^&*()-_=+[]{}|;:,.<>?";

    private static final int MIN_LENGTH = 12;
    private static final int DEFAULT_LENGTH = 16;

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generatePassword(int length) {
        if (length < MIN_LENGTH) {
            throw new IllegalArgumentException("Password length should be at least 12 characters");
        }

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }

    public static String generatePassword() {
        return generatePassword(DEFAULT_LENGTH);
    }
}