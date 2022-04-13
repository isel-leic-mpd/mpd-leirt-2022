package isel.leirt.mpd.queries.lazy2.iterators;

import java.util.Iterator;
import java.util.function.Predicate;

public class TakeWhileIterator<T> implements Iterator<T>  {
    private final Iterator<T> srcIt;
    private final Predicate<T> pred;
    private T current;
    private boolean hasValue;
    private boolean done;

    public TakeWhileIterator(Iterable<T> src,
                          Predicate<T> pred) {
        this.srcIt = src.iterator();
        this.pred = pred;
        current = null;
        done = false;
        hasValue = false;
    }

    @Override
    public boolean hasNext() {
        if (done) return false;
        if (hasValue) return true;

        if (!srcIt.hasNext() || !pred.test(current = srcIt.next())) {
            done = true;
            return false;
        }
        hasValue = true;
        return true;
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new IllegalStateException();
        hasValue = false;
        return current;
    }
}
