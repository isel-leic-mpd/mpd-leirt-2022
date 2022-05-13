package isel.leirt.mpd.queries2;

import isel.leirt.mpd.queries2.iterators.IteratorCached;
import isel.leirt.mpd.queries2.iterators.IteratorGenerator;
import isel.leirt.mpd.queries2.iterators.IteratorLimit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class MoreLazyQueries {
    public static <T> Iterable<T> generate(Supplier<T> next){
        return () -> new IteratorGenerator<>(next);
    }

    public static <T> List<T> toList(Iterable<T> src) {
        List<T> res = new ArrayList<>();
        for(T val:src) {
            res.add(val);
        }
        return res;
    }

    public static <T> Iterable<T> limit(Iterable<T> src, int nr){

        return () -> new IteratorLimit<>(src,nr);
    }

    public static <T> Iterable<T> cache(Iterable<T> src) {
        final List<T> cache = new ArrayList<>();
        final boolean[] done = {false};
        Iterator<T> it = src.iterator();
        return () -> new IteratorCached<>(src.iterator(), cache);
    }

}
