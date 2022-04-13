package isel.leirt.mpd.queries.lazy2.iterators;

import java.util.Iterator;
import java.util.function.Predicate;

public class FilterIterator<T> implements Iterator<T> {
    private final Iterator<T> srcIt;
    private final  Predicate<T> pred;
    private T current;
    private boolean hasValue;

    public FilterIterator(Iterable<T> src,
                          Predicate<T> pred) {
        this.srcIt = src.iterator();
        this.pred = pred;
        current = null;
        hasValue = false;
    }

    @Override
    public boolean hasNext() {
        if (hasValue) return true;
        while (srcIt.hasNext()) {
            T srcNext = srcIt.next();
            if (pred.test(srcNext)) {
                current = srcNext;
                hasValue = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new IllegalStateException();
        hasValue = false;
        return current;
    }
}
