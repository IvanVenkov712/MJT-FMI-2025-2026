package bg.sofia.uni.fmi.mjt.file;

import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static <T> boolean areSetsEqual(Set<T> set1, Set<T> set2) {
        if(set1 == null || set2 == null) {
            throw new IllegalArgumentException("null sets are not permitted!");
        }
        Set<T> diff1 = new HashSet<>(set1);
        diff1.removeAll(set2);
        Set<T> diff2 = new HashSet<>(set2);
        diff2.removeAll(set1);
        return diff1.isEmpty() && diff2.isEmpty();
    }
}
