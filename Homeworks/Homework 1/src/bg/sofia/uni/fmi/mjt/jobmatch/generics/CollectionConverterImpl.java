package bg.sofia.uni.fmi.mjt.jobmatch.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionConverterImpl<InT, OutT> implements CollectionConverter<InT, OutT> {
    private final Converter<? super InT, ? extends OutT> converter;

    public CollectionConverterImpl(Converter<? super InT, ? extends OutT> converter) {
        if (converter == null) {
            throw new IllegalArgumentException("converter is null");
        }
        this.converter = converter;
    }

    @Override
    public List<OutT> convert(Collection<InT> inputCollection) {
        if (inputCollection == null) {
            throw new IllegalArgumentException("inputCollection is null");
        }
        List<OutT> res = new ArrayList<>();
        for (InT elem : inputCollection) {
            res.add(converter.convert(elem));
        }
        return res;
    }
}
