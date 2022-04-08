package isel.leirt.mpd.weather.dto;

import isel.leirt.mpd.weather.utils.TimeUtils;

import java.time.LocalDateTime;

import static isel.leirt.mpd.weather.utils.PrintUtils.EOL;

public class Local {
    private String name;
    private String country;
    private int population;
    private int timezone;
    private long sunrise;
    private long sunset;

    public String getName() { return name; }
    public String getCountry() { return country; }
    public int getPopulation() { return population; }
    public int getTimezone() { return timezone; }
    public LocalDateTime getSunRise() { return TimeUtils.fromUnixTime(sunrise); }
    public LocalDateTime getSunSet() { return TimeUtils.fromUnixTime(sunset); }

    public String toString() {
        return "{" + EOL +
                "\tname = " + getName() + EOL +
                "\tcountry = " + getCountry() + EOL +
                "\tpopulation = " + getPopulation() + EOL +
                "\ttimezone = " + getTimezone() + EOL +
                "\tsunrise = " + getSunRise() + EOL +
                "\tsunset = " + getSunSet() + EOL +
            "}";
    }
}
