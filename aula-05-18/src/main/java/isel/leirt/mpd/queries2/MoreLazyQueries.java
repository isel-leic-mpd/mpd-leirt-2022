package isel.leirt.mpd.queries2;

import isel.leirt.mpd.queries2.iterators.IteratorCached;
import isel.leirt.mpd.queries2.iterators.IteratorGenerator;
import isel.leirt.mpd.queries2.iterators.IteratorLimit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class MoreLazyQueries {

    // factory operations
    public static <T> Iterable<T> generate(Supplier<T> next){

        return () -> new IteratorGenerator<>(next);
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

    public static <T> Iterable<T> limit(Iterable<T> src, int nr){
        return () -> new IteratorLimit<>(src,nr);
    }

    public static <T> Iterable<T> cache(Iterable<T> src) {
        ArrayList<T> cached = new ArrayList<>();
        Iterator<T> sharedIt = src.iterator();
        return () -> new IteratorCached(sharedIt, cached);
    }


    // terminal operations
    public static <T> List<T> toList(Iterable<T> src) {
        List<T> res = new ArrayList<>();
        for(T val:src) {
            res.add(val);
        }
        return res;
    }
}
