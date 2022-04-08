package isel.leirt.mpd.weather.queries;

import isel.leirt.mpd.weather.dto.WeatherInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Queries {
    public static <T> Iterable<T> filter(
        Iterable<T> wiSeq, Predicate<T> pred) {

        List<T> result = new ArrayList<>();

        for(var wi : wiSeq) {
            if (pred.test(wi))
                result.add(wi);
        }
        return result;
    }

    public static <T, U> Iterable<U> map(Iterable<T> seq,
                                Function<T, U> mapper) {
        List<U> doubleSeq = new ArrayList<>();
        for(var wi : seq)
            doubleSeq.add(mapper.apply(wi));
        return doubleSeq;
    }

}
