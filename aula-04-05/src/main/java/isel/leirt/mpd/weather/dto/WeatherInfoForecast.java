package isel.leirt.mpd.weather.dto;

import static isel.leirt.mpd.weather.utils.PrintUtils.EOL;

public class WeatherInfoForecast extends WeatherInfoBase {
    private String dt_txt;

    @Override
    public String toString() {
        return "{" + EOL
            + "\tdateTime = " + dateTime() + EOL
            + "\tdescription = " + description() + EOL
            + "\tweather = " + weather() + EOL
            + "\tdt_txt = " + dt_txt + EOL
            + "}";
    }
}
