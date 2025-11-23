package bg.sofia.uni.fmi.mjt.jobmatch.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class TopElementFinderImpl<T> implements TopElementFinder<T> {
    private final Collection<T> collection;
    private final Comparator<T> defaultComparator;
    private final Filter<T> defaultFilter;

    public TopElementFinderImpl(Collection<T> collection, Comparator<T> defaultComparator,
                                Filter<T> defaultFilter) {

        if (collection == null || defaultComparator == null || defaultFilter == null) {
            throw new IllegalArgumentException("Null arguments are illegal!");
        }
        this.collection = collection;
        this.defaultComparator = defaultComparator;
        this.defaultFilter = defaultFilter;
    }

    @Override
    public List<T> findTopN(Comparator<? super T> comparator, Filter<? super T> filter, int limit) {
        if (limit <= 0) {
            return List.of();
        }

        Queue<T> topItems = new PriorityQueue<>(comparator);
        for (T item : collection) {
            if (filter.matches(item)) {
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
        return findTopN(defaultComparator, defaultFilter, limit);
    }
}
