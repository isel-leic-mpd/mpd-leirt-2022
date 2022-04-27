package isel.leirt.mpd.weather3.dto;

import java.time.LocalDate;
import java.time.LocalTime;


import static isel.leirt.mpd.weather3.utils.TimeUtils.*;


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
            + ", " + weather[0].description
            + ", sunrise = " + timeFromUnixTime(sunrise)
            + ", sunset = " + timeFromUnixTime(sunset)
            + ", moon_phase = " + moon_phase
            + ", moonrise = " +timeFromUnixTime( moonrise)
            + ", moonset = " + timeFromUnixTime(moonset)
            + ", maxTemp = " + temp.max
            + ", minTemp = " + temp.min;
    }

    public LocalDate obsDate() {
        return  dateFromUnixTime(dt);
    }

    public LocalTime sunRise() {
        return timeFromUnixTime(sunrise);
    }

    public LocalTime sunSet() {
        return timeFromUnixTime(sunset);
    }

    public LocalTime moonRise() {
        return timeFromUnixTime(moonrise);
    }

    public LocalTime moonSet() {
        return timeFromUnixTime(moonset);
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
