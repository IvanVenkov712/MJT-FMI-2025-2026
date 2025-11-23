package bg.sofia.uni.fmi.mjt.jobmatch.generics;

import java.util.Collection;
import java.util.List;

public interface CollectionConverter<InT, OutT> {
    List<OutT> convert(Collection<InT> inputCollection);
}
