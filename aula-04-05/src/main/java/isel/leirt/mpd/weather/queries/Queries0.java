package isel.leirt.mpd.weather.queries;

import isel.leirt.mpd.weather.dto.WeatherInfo;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Queries0 {
    public static Iterable<WeatherInfo> filter(
        Iterable<WeatherInfo> wiSeq, Predicate<WeatherInfo> pred) {

        List<WeatherInfo> result = new ArrayList<>();

        for(var wi : wiSeq) {
            if (pred.test(wi))
                result.add(wi);
        }
        return result;
    }

    public Iterable<Double> map(Iterable<WeatherInfo> seq,
                                Function<WeatherInfo, Double> mapper) {
        List<Double> doubleSeq = new ArrayList<>();
        for(var wi : seq)
            doubleSeq.add(mapper.apply(wi));
        return doubleSeq;
    }

    public static Iterable<WeatherInfo> get_sunny_locals(
        List<WeatherInfo> wiSeq) {

        return filter(wiSeq, wi -> wi.description().contains("sky"));
    }

    public static Iterable<WeatherInfo> get_rainy_locals(
        List<WeatherInfo> wiSeq) {

        return filter(wiSeq, wi -> wi.description().contains("rain"));
    }

    public static Iterable<WeatherInfo> getLisbonWeatherFromList(Iterable<WeatherInfo> seq) {
        return
            filter(seq, wi -> wi.local().equalsIgnoreCase("Lisboa"));
    }

    public Iterable<Double> getTemperaturesInInterval(
                    Iterable<WeatherInfo> seq, double min, double max) {
        List<Double> temps = new ArrayList<>();

        for(var wi : seq) {
            if (wi.temp() >= min && wi.temp() <= max)
                temps.add(wi.temp());
        }
        return temps;
    }


    public Iterable<Double> getTemperaturesInInterval2(
        Iterable<WeatherInfo> seq, double min, double max) {

        return map(
            filter(seq, wi -> wi.temp() >= min && wi.temp() <= max),
            wi -> wi.temp()
        );
    }



}
