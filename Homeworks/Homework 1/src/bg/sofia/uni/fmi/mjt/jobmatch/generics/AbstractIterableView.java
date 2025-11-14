package bg.sofia.uni.fmi.mjt.jobmatch.generics;

import bg.sofia.uni.fmi.mjt.jobmatch.Predicate;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AbstractIterableView<T1, T2> implements IterableView<T1, T2> {
    private final Iterable<T1> original;
    private final Converter<? super T1, ? extends T2> converter;

    public AbstractIterableView(Iterable<T1> original, Converter<? super T1, ? extends T2> converter) {
        if (original == null || converter == null) {
            throw new IllegalArgumentException("Null arguments are illegal!");
        }
        this.original = original;
        this.converter = converter;
    }

    @Override
    public List<T2> findTopN(Comparator<? super T2> comparator, Predicate<? super T2> predicate, int limit) {
        if (limit <= 0) {
            return List.of();
        }

        Queue<T2> topItems = new PriorityQueue<>(comparator);
        for (T2 item : this) {
            if (predicate.test(item)) {
                topItems.add(item);
                if (topItems.size() > limit) {
                    topItems.poll();
                }
            }
        }

        List<T2> res = new ArrayList<>();
        while (!topItems.isEmpty()) {
            res.add(topItems.poll());
        }

        return List.copyOf(res.reversed());
    }

    @Override
    public @NotNull Iterator<T2> iterator() {
        return new IterableViewIterator<>(original.iterator(), converter);
    }
}
