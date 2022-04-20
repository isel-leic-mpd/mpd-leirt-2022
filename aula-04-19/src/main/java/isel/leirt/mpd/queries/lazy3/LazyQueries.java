package isel.leirt.mpd.queries.lazy3;

import isel.leirt.mpd.queries.lazy3.iterators.FilterIterator;
import isel.leirt.mpd.queries.lazy3.iterators.FlatMapIterator;
import isel.leirt.mpd.queries.lazy3.iterators.TakeWhileIterator;
import isel.leirt.mpd.queries.lazy3.iterators.MapIterator;


import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class LazyQueries {

    // factory operations

    public static <T> Iterable<T> of(T ... src) {
        // eager solution
        // return List.of(src);

        // lazy solution
        return () -> new Iterator<T>() {
            private int currIndex = 0;

            @Override
            public boolean hasNext() {
                return currIndex < src.length;
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return src[currIndex++];
            }
        };
    }

    public static Iterable<Long> range(long start, long end) {
        return () -> new Iterator<Long>() {
            private long curr = start;
            @Override
            public boolean hasNext() {
                return curr <= end;
            }

            @Override
            public Long next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return curr++;
            }
        };

    }


    public static <T> Iterable<T>
    iterate(T seed, UnaryOperator<T> f) {

         return null;
    }

    // intermediary operations


    public static <T,U> Iterable<U> flatMap(
        Iterable<T> src,
        Function<T, Iterable<U>> mapper) {
        return () -> new FlatMapIterator<>(src, mapper);
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


    // terminal operations

    public static <T> T max(Iterable<T> src, Comparator<T> cmp) {
        T maxVal = null;
        for(var elem : src ) {
            if (maxVal == null || cmp.compare(elem, maxVal) > 0)
                maxVal = elem;
        }
        return maxVal;
    }
}
