package bg.sofia.uni.fmi.mjt.jobmatch.generics;

public interface ElementConverter<T1, T2> {
    T2 convert(T1 arg);
}
