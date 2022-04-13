package isel.leirt.mpd.weather2.queries.lazy.iterators;

import java.util.Iterator;
import java.util.function.Function;

public class MapIterator<T,U> implements Iterator<U> {
    private final Iterator<T> srcIt;
    private final Function<T,U> mapper;

    public MapIterator(Iterable<T> src, Function<T,U> mapper) {
        srcIt = src.iterator();
        this.mapper = mapper;
    }
    @Override
    public boolean hasNext() {
        return srcIt.hasNext();
    }

    @Override
    public U next() {
        if (!hasNext())
            throw new IllegalStateException();
        return mapper.apply(srcIt.next());
    }
}
