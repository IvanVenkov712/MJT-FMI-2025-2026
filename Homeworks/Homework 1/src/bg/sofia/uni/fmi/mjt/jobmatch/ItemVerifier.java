package bg.sofia.uni.fmi.mjt.jobmatch;

public interface ItemVerifier<T> {
    boolean isValid(T t);
}
