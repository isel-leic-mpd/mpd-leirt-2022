package isel.leirt.mpd.weather4.dto;

import java.util.Arrays;

public class ForecastWeatherInfoQueryDto {
    private ForecastInfoDto[] daily;
    private ForecastHourlyDto[] hourly;
    public ForecastInfoDto[] getForecastInfo() {
        return Arrays.copyOf(daily, daily.length);
    }
    public ForecastHourlyDto[] getHourlyInfo() {
        return Arrays.copyOf(hourly, hourly.length);
    }
}
