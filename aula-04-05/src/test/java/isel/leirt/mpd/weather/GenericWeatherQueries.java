package isel.leirt.mpd.weather;

import isel.leirt.mpd.weather.dto.WeatherInfo;
import static isel.leirt.mpd.weather.queries.Queries.*;

import isel.leirt.mpd.weather.queries.Queries;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GenericWeatherQueries {
    @Test
    public void get_sunny_temperatures_in_portugal_now() {
        double lati = 36.945, longi = -9.522;
        double latf = 42.149, longf = -6.187;

        OpenWeatherWebApi webApi = new OpenWeatherWebApi();

        List<WeatherInfo> result =
            webApi.weatherAtArea(longi, lati, longf, latf);

        Iterable<Double> sunnyTemps =
              map(
                  filter(
                      result,
                      wi -> wi.description().contains("sky")
                  ),
                  wi -> wi.temp()
              );

         sunnyTemps.forEach(System.out::println);
    }
}
