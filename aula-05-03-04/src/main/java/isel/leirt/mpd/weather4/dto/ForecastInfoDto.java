package isel.leirt.mpd.weather4.dto;

import isel.leirt.mpd.weather4.utils.TimeUtils;

import java.time.LocalDate;
import java.time.LocalTime;


import static isel.leirt.mpd.weather4.utils.TimeUtils.*;


public class ForecastInfoDto {

    private long dt;
    private long sunrise;
    private long sunset;
    private double moon_phase;
    private long moonrise;
    private long moonset;
    private TempDto temp;
    private WeatherDescriptionDto[] weather;

    @Override
    public String toString() {
        return "date= " + obsDate()
            + ", sunrise = " + TimeUtils.timeFromUnixTime(sunrise)
            + ", sunset = " + TimeUtils.timeFromUnixTime(sunset)
            + ", moon_phase = " + moon_phase
            + ", moonrise = " + TimeUtils.timeFromUnixTime( moonrise)
            + ", moonset = " + TimeUtils.timeFromUnixTime(moonset)
            + ", maxTemp = " + temp.max
            + ", minTemp = " + temp.min
            + ", weather condition = " + weather[0].toString();
    }

    public LocalDate obsDate() {
        return  TimeUtils.dateFromUnixTime(dt);
    }

    public LocalTime sunRise() {
        return TimeUtils.timeFromUnixTime(sunrise);
    }

    public LocalTime sunSet() {
        return TimeUtils.timeFromUnixTime(sunset);
    }

    public LocalTime moonRise() {
        return TimeUtils.timeFromUnixTime(moonrise);
    }

    public LocalTime moonSet() {
        return TimeUtils.timeFromUnixTime(moonset);
    }

    public double maxTemp() { return temp.max; }

    public double minTemp() { return temp.min; }

    public double moonPhase() {
        return moon_phase;
    }

    public String getDescription() {
        return weather[0].description;
    }
}
