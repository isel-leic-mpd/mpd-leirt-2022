package isel.leirt.mpd.queries2.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorLimit<T> implements Iterator<T> {
    private final Iterator<T> src;
    private int remaining;


    public IteratorLimit(Iterable<T> src, int limit) {
        this.src = src.iterator();
        this.remaining = limit;

    }

    @Override
    public boolean hasNext() {
        return remaining > 0 && src.hasNext();
    }

    @Override
    public T next() {
        if (!hasNext())  throw new NoSuchElementException();
        T item = src.next();
        remaining--;
        return item;
    }
}
