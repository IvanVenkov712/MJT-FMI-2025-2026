package bg.sofia.uni.fmi.mjt.jobmatch.generics;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class IterableConverter<T1, T2> implements Iterable<T2> {
    private final Iterable<T1> original;
    private final Converter<? super T1, ? extends T2> converter;

    public IterableConverter(Iterable<T1> original, Converter<? super T1, ? extends T2> converter) {
        if (original == null || converter == null) {
            throw new IllegalArgumentException("Null arguments are illegal!");
        }
        this.original = original;
        this.converter = converter;
    }

    @Override
    public @NotNull Iterator<T2> iterator() {
        return new IterableConverterIterator<>(original.iterator(), converter);
    }
}
