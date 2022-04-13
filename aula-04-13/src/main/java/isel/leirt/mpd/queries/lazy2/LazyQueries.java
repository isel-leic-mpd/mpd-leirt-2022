package isel.leirt.mpd.queries.lazy2;

import isel.leirt.mpd.queries.lazy2.iterators.FilterIterator;
import isel.leirt.mpd.queries.lazy2.iterators.TakeWhileIterator;
import isel.leirt.mpd.weather2.queries.lazy.iterators.MapIterator;
import isel.leirt.mpd.weather2.queries.lazy.iterators.PrimesIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

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


    public static <T,U> Iterable<U> map(Iterable<T> src, Function<T,U> mapper) {
        return () -> new MapIterator(src, mapper);
    }

    /**
     * A filter for a generic sequence
     * produces a new sequence of the same type
     * but potentially with less (or none) elements
     * Just those who are accepted by the filter
     *
     * @param seq
     * @param pred
     * @param <T>
     * @return
     */
    public static <T> Iterable<T> filter0(
        Iterable<T> seq, Predicate<T> pred) {

        List<T> result = new ArrayList<>();

        for(var wi : seq) {
            if (pred.test(wi))
                result.add(wi);
        }
        return result;
    }

    public static <T> Iterable<T> filter(
        Iterable<T> src, Predicate<T> pred) {
        return () -> new FilterIterator<T>(src, pred);
    }

    public static <T> Iterable<T> takeWhile(
        Iterable<T> src, Predicate<T> pred) {
        return () -> new TakeWhileIterator<T>(src, pred);
    }

}
