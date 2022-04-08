package isel.leirt.mpd.weather.dto;

import com.google.gson.annotations.SerializedName;
import isel.leirt.mpd.weather.utils.TimeUtils;

import java.time.LocalDateTime;

public class WeatherInfoBase {
    public static class Weather {
        public  double temp;
        public  double feels_like;
        public  double temp_min;
        public  double temp_max;
        public  int humidity;

        /*
        public Weather(double temp, double feels_like, double temp_min, double temp_max, int humidity) {
            this.temp = temp;
            this.feels_like = feels_like;
            this.temp_min = temp_min;
            this.temp_max = temp_max;
            this.humidity = humidity;
        }
        */

        @Override
        public String toString() {
            return  "{ "
                +"temp = " + temp
                + ", feels_like = " + feels_like
                + ", temp_min = " + temp_min
                + ". temp_max = " + temp_max
                + ", humidity = " + humidity
                + " }";
        }
    }

    private static class WeatherDescription {
        public final int id;
        public final String description;

        public WeatherDescription(int id, String description) {
            this.id = id;
            this.description = description;
        }

        @Override
        public String toString() {
            return  "{ "
                +"id = " + id + ", "
                + "description = " + "'" + description  + "'"
                + " }";
        }
    }

    @SerializedName("weather")
    protected  WeatherDescription[] descriptors;

    @SerializedName("main")
    protected  Weather weather;

    protected  long dt;

    public LocalDateTime dateTime() {
        return TimeUtils.fromUnixTime(dt);
    }

    protected Weather weather() { return weather; }

    public double feelsLike() { return weather.feels_like; }

    public double temp() { return weather.temp; }

    public double minTemp() { return weather.temp_min; }

    public double maxTemp() { return weather.temp_max; }

    public int humidity() { return weather.humidity; }

    public String description() { return descriptors[0].description; }

    public int descriptionId() { return descriptors[0].id; }

}
