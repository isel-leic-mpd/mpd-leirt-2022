package isel.leirt.mpd.weather2.queries;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class GenericQueries {

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
    public static <T> Iterable<T> filter(
        Iterable<T> seq, Predicate<T> pred) {

        List<T> result = new ArrayList<>();

        for(var wi : seq) {
            if (pred.test(wi))
                result.add(wi);
        }
        return result;
    }

    /**
     * Given a sequence of an arbitrary type produces
     * a sequence of a potencially diferent type by
     * "mapping" ecah element of the original sequence to the
     * result sequence type using the passed "mapper" function
     * @param seq
     * @param mapper
     * @param <T>
     * @param <U>
     * @return
     */
    public static <T, U> Iterable<U> map(Iterable<T> seq,
                                Function<T, U> mapper) {
        List<U> resultSeq = new ArrayList<>();
        for(var wi : seq)
            resultSeq.add(mapper.apply(wi));
        return resultSeq;
    }

    public static <T> T max(Iterable<T> src, Comparator<T> cmp) {
        // TODO
        T maxVal = null;
        for(var elem : src ) {
            if (maxVal == null || cmp.compare(elem, maxVal) > 0)
                maxVal = elem;
        }
        return maxVal;
    }

}
