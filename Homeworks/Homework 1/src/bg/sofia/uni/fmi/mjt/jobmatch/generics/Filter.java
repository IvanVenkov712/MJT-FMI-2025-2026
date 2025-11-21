package bg.sofia.uni.fmi.mjt.jobmatch.generics;

public interface Filter<T> {
    boolean matches(T t);
}
