package isel.leirt.mpd.queries2.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorCached<T> implements Iterator<T> {

    private final Iterator<T> itSrc;
    private final List<T> cachedElements;
    private int index;

    public IteratorCached(Iterator<T> srcIt, List<T> cachedElements) {
        this.itSrc = srcIt;
        this.cachedElements = cachedElements;
        index = 0;
    }

    @Override
    public boolean hasNext() {
        if (index < cachedElements.size()) return true;
        if (!itSrc.hasNext())  return false;
        cachedElements.add(itSrc.next());
        return true;
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new IllegalStateException();
        return cachedElements.get(index++);
    }
}