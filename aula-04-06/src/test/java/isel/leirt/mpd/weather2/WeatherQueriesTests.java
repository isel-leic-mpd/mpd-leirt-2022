package isel.leirt.mpd.weather2;

import isel.leirt.mpd.weather.OpenWeatherWebApi;
import isel.leirt.mpd.weather.dto.PollutionInfo;
import org.junit.jupiter.api.Test;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import static isel.leirt.mpd.weather2.queries.GenericQueries.*;



import static java.util.stream.Collectors.groupingBy;

public class WeatherQueriesTests {
    private final static double LISBON_LAT  =  38.7071;
    private final static double LISBON_LONG = -9.1359;

    record CoDatePair(double co, LocalDateTime dateTime) {}

    private  static boolean isWeekEnd(LocalDateTime date) {
        DayOfWeek wd =  date.getDayOfWeek();
        return wd == DayOfWeek.SUNDAY || wd == DayOfWeek.SATURDAY;
    }

    private  static boolean isLabourHour(LocalDateTime dt) {

        return dt.getHour() >= 7 && dt.getHour() <= 21;
    }

    /**
     * this code is just to show a preview on what we can do
     * already in java with java sequences (through interface Stream<T>)
     */
    @Test
    public void get_air_pollution_average_on_different_days() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        LocalDate start = LocalDate.of(2022, 03, 03);
        LocalDate end = LocalDate.of(2022, 04, 03);
        List<PollutionInfo> pinfo =
            webApi.pollutionHistoryAt(LISBON_LAT, LISBON_LONG,start,end);


        var weekendCoAverage =
            pinfo.stream()
            .filter(pi -> isWeekEnd(pi.dateTime()))
            .mapToDouble(PollutionInfo::getCo)
            .average()
            .getAsDouble();

        System.out.println("Average CO on weekends = " + weekendCoAverage);

        var NonWeekendCoAverage =
            pinfo.stream()
            .filter(pi -> !isWeekEnd(pi.dateTime()))
            .mapToDouble(PollutionInfo::getCo)
            .average()
            .getAsDouble();
        System.out.println("Average CO on labour days = " + NonWeekendCoAverage);


        var averageByDayOnLaborHours =
                pinfo.stream()
                .filter(pi -> isLabourHour(pi.dateTime()))
                .collect(groupingBy(pi -> pi.dateTime().getDayOfWeek(),
                    Collectors.averagingDouble(PollutionInfo::getCo)));

        System.out.println("Average CO by day on labour hours:");

        for(var entry : averageByDayOnLaborHours.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        var averageByDayOnNightHours =
            pinfo.stream()
                .filter(pi -> !isLabourHour(pi.dateTime()))
                .collect(groupingBy(pi -> pi.dateTime().getDayOfWeek(),
                    Collectors.averagingDouble(PollutionInfo::getCo)));

        System.out.println("Average CO by day on night hours:");

        for(var entry : averageByDayOnNightHours.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }


    @Test
    public void get_co_max_with_datetime_at_weekend_on_lisbon() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        LocalDate start = LocalDate.of(2022, 03, 03);
        LocalDate end = LocalDate.of(2022, 04, 03);
        List<PollutionInfo> pinfo =
            webApi.pollutionHistoryAt(LISBON_LAT, LISBON_LONG,start,end);

        var maxCoDate =
            pinfo.stream()
                .filter(pi -> !isWeekEnd(pi.dateTime()))
                .map(pi -> new CoDatePair(pi.getCo(), pi.dateTime()))
                .max(Comparator.comparing(CoDatePair::co))
                .get();

        System.out.println("Max co date = " + maxCoDate);
    }

    @Test
    public void get_co_max_on_lisbon_at_weekend_by_period_imperative() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        LocalDate start = LocalDate.of(2022, 03, 03);
        LocalDate end = LocalDate.of(2022, 04, 03);
        List<PollutionInfo> pinfo =
            webApi.pollutionHistoryAt(LISBON_LAT, LISBON_LONG,start,end);


        CoDatePair maxVal = new CoDatePair(0, null);

        for(var pi : pinfo) {
            if (isWeekEnd(pi.dateTime()) && pi.getCo() > maxVal.co)
                maxVal = new CoDatePair(pi.getCo(), pi.dateTime());
        }


        System.out.println("weekend co max = " + maxVal);
    }

    @Test
    public void get_co_max_on_lisbon_at_weekend_by_period_declarative() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi();
        LocalDate start = LocalDate.of(2022, 03, 03);
        LocalDate end = LocalDate.of(2022, 04, 03);
        List<PollutionInfo> pinfo =
            webApi.pollutionHistoryAt(LISBON_LAT, LISBON_LONG,start,end);

        var maxVal =
            max(
                map(
                    filter(pinfo, pi -> isWeekEnd(pi.dateTime())),
                    pi -> new CoDatePair(pi.getCo(), pi.dateTime())
                ),
                Comparator.comparing(CoDatePair::co)
            );

        System.out.println("weekend co max = " + maxVal);
    }


}
