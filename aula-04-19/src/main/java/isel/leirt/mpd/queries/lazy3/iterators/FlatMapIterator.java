package isel.leirt.mpd.queries.lazy3.iterators;

import jdk.jshell.spi.ExecutionControl;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

public class FlatMapIterator<T,U> implements Iterator<U> {
    private Iterator<T> itSrc;
    private Function<T,Iterable<U>>  mapper;

    private Iterator<U> itCurr;

    private U curr;
    private boolean hasValue;

    public FlatMapIterator(Iterable<T> src, Function<T,Iterable<U>> mapper) {
        this.itSrc = src.iterator();
        this.mapper = mapper;
        itCurr = null;
        hasValue = false;
    }

    @Override
    public boolean hasNext() {
        if (hasValue) return true;
        while(itCurr == null || !itCurr.hasNext()) {
            if (!itSrc.hasNext()) return false;
            itCurr = mapper.apply(itSrc.next()).iterator();
        }
        curr = itCurr.next();
        hasValue = true;
        return true;
    }

    @Override
    public U next() {
        if (!hasNext())
            throw new NoSuchElementException();
        hasValue = false;
        return curr;
    }
}

