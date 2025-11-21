package bg.sofia.uni.fmi.mjt.jobmatch.generics;

import java.util.Comparator;
import java.util.List;

public interface TopElementFinder<T> {
    List<T> findTopN(Comparator<? super T> comparator, Filter<? super T> predicate, int limit);

    List<T> findTopN(int limit);
}
