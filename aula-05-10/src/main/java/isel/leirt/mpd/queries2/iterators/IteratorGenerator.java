package isel.leirt.mpd.queries2.iterators;

import java.util.Iterator;
import java.util.function.Supplier;

public class IteratorGenerator<T> implements Iterator<T> {
    private final Supplier<T> next;

    public IteratorGenerator(Supplier<T> next) {
        this.next = next;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public T next() {
        return next.get();
    }
}
