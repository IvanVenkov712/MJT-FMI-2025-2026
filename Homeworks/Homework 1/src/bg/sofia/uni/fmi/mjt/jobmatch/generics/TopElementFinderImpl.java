package bg.sofia.uni.fmi.mjt.jobmatch.generics;

import bg.sofia.uni.fmi.mjt.jobmatch.MyPredicate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class TopElementFinderImpl<T> implements TopElementFinder<T> {
    private final Iterable<T> iterable;
    private final Comparator<T> defaultComparator;
    private final MyPredicate<T> defaultPredicate;

    public TopElementFinderImpl(Iterable<T> iterable, Comparator<T> defaultComparator,
                                MyPredicate<T> defaultPredicate) {

        if (iterable == null || defaultComparator == null || defaultPredicate == null) {
            throw new IllegalArgumentException("Null arguments are illegal!");
        }
        this.iterable = iterable;
        this.defaultComparator = defaultComparator;
        this.defaultPredicate = defaultPredicate;
    }

    @Override
    public List<T> findTopN(Comparator<? super T> comparator, MyPredicate<? super T> predicate, int limit) {
        if (limit <= 0) {
            return List.of();
        }

        Queue<T> topItems = new PriorityQueue<>(comparator);
        for (T item : iterable) {
            if (predicate.test(item)) {
                topItems.add(item);
                if (topItems.size() > limit) {
                    topItems.poll();
                }
            }
        }

        List<T> res = new ArrayList<>();
        while (!topItems.isEmpty()) {
            res.add(topItems.poll());
        }

        return List.copyOf(res.reversed());
    }

    @Override
    public List<T> findTopN(int limit) {
        return findTopN(defaultComparator, defaultPredicate, limit);
    }
}
