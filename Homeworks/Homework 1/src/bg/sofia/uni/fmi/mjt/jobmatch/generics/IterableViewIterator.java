package bg.sofia.uni.fmi.mjt.jobmatch.generics;

import java.util.Iterator;

public class IterableViewIterator<T1, T2> implements Iterator<T2> {
    private final Iterator<T1> iterator;
    private final Converter<? super T1, ? extends T2> converter;

    public IterableViewIterator(Iterator<T1> iterator, Converter<? super T1, ? extends T2> converter) {
        this.iterator = iterator;
        this.converter = converter;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public T2 next() {
        T1 element = iterator.next();
        return converter.convert(element);
    }
}
