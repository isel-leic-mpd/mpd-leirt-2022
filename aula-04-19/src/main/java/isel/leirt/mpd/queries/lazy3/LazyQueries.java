package isel.leirt.mpd.queries.lazy3;

import isel.leirt.mpd.queries.lazy3.iterators.FilterIterator;
import isel.leirt.mpd.queries.lazy3.iterators.FlatMapIterator;
import isel.leirt.mpd.queries.lazy3.iterators.TakeWhileIterator;
import isel.leirt.mpd.queries.lazy3.iterators.MapIterator;


import java.util.*;
import java.util.function.BiFunction;
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
    iterate(T seed, UnaryOperator<T> oper) {
        return () -> new Iterator<T>() {
            private T curr = seed;
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                T val = curr;
                curr = oper.apply(curr);
                return val;
            }
        };
    }

    // intermediate operations

    public static <T,U> Iterable<U> flatMap(
        Iterable<T> src,
        Function<T, Iterable<U>> mapper) {
        return () -> new FlatMapIterator<>(src, mapper);
    }

    public static <T,U> Iterable<U> map(Iterable<T> src, Function<T,U> mapper) {
        return () -> new MapIterator(src, mapper);
    }


    public static <T> Iterable<T> filter(
        Iterable<T> src, Predicate<T> pred) {
        return () -> new FilterIterator<T>(src, pred);
    }

    public static <T> Iterable<T> takeWhile(
        Iterable<T> src, Predicate<T> pred) {
        return () -> new TakeWhileIterator<T>(src, pred);
    }

    public static <T> Iterable<T> limit( Iterable<T> src, int n) {
        return () -> new Iterator<T>() {
            Iterator<T> itSrc = src.iterator();
            int remaining = n;
            @Override
            public boolean hasNext() {
                return remaining > 0 && itSrc.hasNext();
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                remaining--;
                return itSrc.next();
            }
        };
    }


    // terminal operations

    public static <T> List<T> toList(Iterable<T> src) {
        List<T> res = new ArrayList<>();
        for(T val : src) {
            res.add(val);
        }
        return res;
    }
    public static <T> T max(Iterable<T> src, Comparator<T> cmp) {
        T maxVal = null;
        for(var elem : src ) {
            if (maxVal == null || cmp.compare(elem, maxVal) > 0)
                maxVal = elem;
        }
        return maxVal;
    }

    public static <T,R> R reduce(Iterable<T> src, R initial, BiFunction<R,T, R> accum) {
        R result = initial;
        for(T t : src) {
            result = accum.apply(result, t);
        }
        return result;
    }

    public static <T,Long> long count2(Iterable<T> src) {
        return reduce(src, 0L, (a, t) -> a+1);
    }

    public static <T>  Optional<T> first(Iterable<T> src) {
        Iterator<T> itSrc = src.iterator();
        if (!itSrc.hasNext()) return Optional.empty();
        return Optional.of(itSrc.next());
    }

    public static <T>  Optional<T> last(Iterable<T> src) {
        T l = reduce(src, null, (a,t) -> t);
        if (l == null) return Optional.empty();
        else return Optional.of(l);
    }

    public static <T> long count(Iterable<T> src) {
        long count = 0;
        for(T t : src) count++;
        return count;
    }


}
