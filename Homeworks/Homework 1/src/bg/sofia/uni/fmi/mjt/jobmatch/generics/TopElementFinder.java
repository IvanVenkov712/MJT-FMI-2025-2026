package bg.sofia.uni.fmi.mjt.jobmatch.generics;

import bg.sofia.uni.fmi.mjt.jobmatch.MyPredicate;

import java.util.Comparator;
import java.util.List;

public interface TopElementFinder<T> {
    List<T> findTopN(Comparator<? super T> comparator, MyPredicate<? super T> predicate, int limit);

    List<T> findTopN(int limit);
}
