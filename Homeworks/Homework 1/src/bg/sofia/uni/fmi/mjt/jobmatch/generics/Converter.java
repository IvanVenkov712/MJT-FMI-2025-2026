package bg.sofia.uni.fmi.mjt.jobmatch.generics;

public interface Converter<T1, T2> {
    T2 convert(T1 arg);
}
