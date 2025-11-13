package bg.sofia.uni.fmi.mjt.jobmatch.model;

import java.util.Set;

public class Validation {
    private Validation() {
    }

    public static boolean isValid(String s) {
        return !(s == null || s.isBlank());
    }

    public static boolean isValid(Set<?> set) {
        return !(set == null || set.isEmpty());
    }

    public static <T extends Enum<?>> boolean isValid(T arg) {
        return arg != null;
    }

    public static boolean isValid(double number) {
        return number >= 0;
    }

    public static <T> boolean isValid(T arg) {
        return arg != null;
    }

    public static boolean isValidSimilarityScore(double score) {
        return score >= 0 && score <= 1;
    }

    public static boolean isValidImprovementScore(double score) {
        return score >= 0;
    }

    public static void validate(String s, String argName) {
        if (!isValid(s)) {
            throw new IllegalArgumentException(argName + " is not a valid string!");
        }
    }

    public static void validate(Set<?> set, String argName) {
        if (!isValid(set)) {
            throw new IllegalArgumentException(argName + " is not a valid set!");
        }
    }

    public static void validate(double number, String argName) {
        if (!isValid(number)) {
            throw new IllegalArgumentException(argName + " is not a valid number!");
        }
    }

    public static <T extends Enum<?>> void validate(T arg, String argName) {
        if (!isValid(arg)) {
            throw new IllegalArgumentException(argName + " is not a valid enum instance");
        }
    }

    public static <T> void validate(T arg, String argName) {
        if (!isValid(arg)) {
            throw new IllegalArgumentException(argName + " is not a valid object!");
        }
    }

    public static void validateSimilarityScore(double score) {
        if (!isValidSimilarityScore(score)) {
            throw new IllegalArgumentException("score is not a valid similarity score!");
        }
    }

    public static void validateImprovementScore(double score) {
        if (!isValidImprovementScore(score)) {
            throw new IllegalArgumentException("score is not a valid improvement score!");
        }
    }
}
