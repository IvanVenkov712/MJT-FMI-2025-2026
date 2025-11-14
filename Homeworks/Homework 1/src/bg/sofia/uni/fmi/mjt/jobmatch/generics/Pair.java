package bg.sofia.uni.fmi.mjt.jobmatch.generics;

public record Pair<T1, T2>(T1 first, T2 second) {
    public Pair {
        if (first == null || second == null) {
            throw new IllegalArgumentException("null elements in a pair are illegal!");
        }
    }
}
