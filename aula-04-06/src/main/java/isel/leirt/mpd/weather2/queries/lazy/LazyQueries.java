package isel.leirt.mpd.weather2.queries.lazy;

import isel.leirt.mpd.weather2.queries.lazy.iterators.PrimesIterator;

import java.util.Iterator;
import java.util.function.Function;

public class LazyQueries {

    private static class PrimesIterable implements Iterable<Long> {

        @Override
        public Iterator<Long> iterator() {
            return new PrimesIterator();
        }
    }

    public static Iterable<Long> primes0() {
        return new PrimesIterable();
    }

    public static Iterable<Long> primes() {
        return () -> new PrimesIterator();
    }


}
