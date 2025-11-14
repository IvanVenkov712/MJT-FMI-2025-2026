package bg.sofia.uni.fmi.mjt.jobmatch.generics;

import bg.sofia.uni.fmi.mjt.jobmatch.MyPredicate;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class IterableViewImpl<T1, T2> implements IterableView<T1, T2> {
    private final Iterable<T1> original;
    private final Converter<? super T1, ? extends T2> converter;
    private final Comparator<T2> defaultComparator;
    private final MyPredicate<T2> defaultPredicate;

    public IterableViewImpl(Iterable<T1> original, Converter<? super T1, ? extends T2> converter,
                            Comparator<T2> defaultComparator, MyPredicate<T2> defaultPredicate) {

        if (original == null || converter == null || defaultComparator == null || defaultPredicate == null) {
            throw new IllegalArgumentException("Null arguments are illegal!");
        }
        this.original = original;
        this.converter = converter;
        this.defaultComparator = defaultComparator;
        this.defaultPredicate = defaultPredicate;
    }

    @Override
    public List<T2> findTopN(Comparator<? super T2> comparator, MyPredicate<? super T2> predicate, int limit) {
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
    public List<T2> findTopN(int limit) {
        return findTopN(defaultComparator, defaultPredicate, limit);
    }

    @Override
    public Iterable<T1> getOriginal() {
        return original;
    }

    @Override
    public @NotNull Iterator<T2> iterator() {
        return new IterableViewIterator<>(original.iterator(), converter);
    }
}
