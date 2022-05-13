package isel.leirt.mpd.queries2.iterators;

import java.util.Iterator;
import java.util.List;

public class IteratorCached<T> implements Iterator<T> {
    private final List<T> cache;
    private final Iterator<T> itSrc;

    private int index;


    public IteratorCached(Iterator<T> itSrc, List<T> cache) {
        this.itSrc = itSrc;
        this.cache = cache;
    }

    @Override
    public boolean hasNext() {
        if (index < cache.size()) return true;
        if (!itSrc.hasNext()) return false;
        cache.add(itSrc.next());
        return true;
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new IllegalStateException();
        return cache.get(index++);
    }
}