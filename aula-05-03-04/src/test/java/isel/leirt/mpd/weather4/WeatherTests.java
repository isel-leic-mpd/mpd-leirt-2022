package isel.leirt.mpd.weather4;

import isel.leirt.mpd.weather4.dto.*;
import isel.leirt.mpd.weather4.requests.HttpRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeatherTests {

    private final static double LISBON_LAT  =  38.7071;
    private final static double LISBON_LONG = -9.1359;

    @Test
    public void get_weather_at_lisbon_now() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(new HttpRequest());
        WeatherInfoDto winfo = webApi.weatherAt(LISBON_LAT, LISBON_LONG );
        System.out.println(winfo);
    }





    @Test
    public void get_air_pollution_in_lisbon_now() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(new HttpRequest());

        PollutionInfoDto pi = webApi.airPollutionAt(
            LISBON_LAT, LISBON_LONG);

        System.out.println(pi);
    }

    @Test
    public void get_air_pollution_history_by_period() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(new HttpRequest());
        LocalDate start = LocalDate.of(2022, 03, 03);
        LocalDate end = LocalDate.of(2022, 04, 03);
        List<PollutionInfoDto> pinfo =
                webApi.pollutionHistoryAt(LISBON_LAT, LISBON_LONG,start,end);

        for(PollutionInfoDto pi : pinfo) {
            System.out.println(pi);
        }
    }

    @Test
    public void get_location_info_by_name() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(new HttpRequest());
        String localName = "Lisboa";

        List<LocationDto> locations = webApi.search(localName);
        for(var loc : locations)
            System.out.println(loc);
    }

    @Test
    public void getForecastWeatherForLisbonTest() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(new HttpRequest());
        List<LocationDto> locs = webApi.search("Lisbon");
        assertTrue(locs.size() > 0);
        LocationDto loc = locs.get(0);
        List<ForecastInfoDto> winfo =
            webApi.forecastWeatherAt(loc.getLat(), loc.getLon());

        for(ForecastInfoDto wif : winfo) {
            System.out.println(wif);
        }
    }

    @Test
    public void getForecastDetailForLisbonTest() {
        OpenWeatherWebApi webApi = new OpenWeatherWebApi(new HttpRequest());
        List<LocationDto> locs = webApi.search("Lisbon");
        assertTrue(locs.size() > 0);
        LocationDto loc = locs.get(0);
        List<ForecastHourlyDto> winfo = webApi.forecastDetailAt(loc.getLat(), loc.getLon());

        System.out.println("WeatherInfo list size: " + winfo.size());
        for(ForecastHourlyDto fwi : winfo) {
            System.out.println(fwi);
        }
    }

}
