package isel.leirt.mpd.weather.dto;

import com.google.gson.annotations.SerializedName;

public class ForecastInfo {

    private WeatherInfoForecast[] list;

    @SerializedName("city")
    private Local local;

    public WeatherInfoForecast[] getForecast() { return list; }
    public Local getLocal() { return local; }
}
