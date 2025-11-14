package bg.sofia.uni.fmi.mjt.jobmatch.generics;

import bg.sofia.uni.fmi.mjt.jobmatch.MyPredicate;

import java.util.Comparator;
import java.util.List;

public interface IterableView<T1, T2> extends Iterable<T2> {
    List<T2> findTopN(Comparator<? super T2> comparator, MyPredicate<? super T2> predicate, int limit);

    List<T2> findTopN(int limit);

    Iterable<T1> getOriginal();
}
