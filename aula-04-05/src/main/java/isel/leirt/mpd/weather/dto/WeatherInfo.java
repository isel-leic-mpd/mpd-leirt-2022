package isel.leirt.mpd.weather.dto;

import static isel.leirt.mpd.weather.utils.PrintUtils.*;

public class WeatherInfo extends WeatherInfoBase {

    private String name;
    private  int timezone;
    public String local() {
        return name;
    }

    @Override
    public String toString() {
        return "{" + EOL
               + "\tdateTime = " + dateTime() + EOL
               + "\tdescription = " + description() + EOL
               + "\tweather = " + weather() + EOL
               + "\tname = " + local() + EOL
               + "\ttimezone = " + timezone + EOL
               + "}";
    }
}
