package bg.sofia.uni.fmi.mjt.jobmatch.generics;

import bg.sofia.uni.fmi.mjt.jobmatch.Predicate;

import java.util.Comparator;
import java.util.List;

public interface IterableView<T1, T2> extends Iterable<T2> {
    List<T2> findTopN(Comparator<? super T2> comparator, Predicate<? super T2> predicate, int limit);
}
