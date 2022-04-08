package isel.leirt.mpd.weather.exceptions;

public class WeatherApiException extends RuntimeException {
    public WeatherApiException(String msg) {
        super(msg);
    }
}
