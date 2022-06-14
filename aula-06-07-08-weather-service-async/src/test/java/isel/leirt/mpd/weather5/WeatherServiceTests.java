package isel.leirt.mpd.weather5;

import isel.leirt.mpd.weatherasync3.OpenWeatherService;
import isel.leirt.mpd.weatherasync3.OpenWeatherWebApi;
import isel.leirt.mpd.weatherasync3.model.DayInfo;
import isel.leirt.mpd.weatherasync3.model.Location;
import isel.leirt.mpd.weatherasync3.model.WeatherInfo;
import isel.leirt.mpd.weatherasync3.requests.CounterRequest;
import isel.leirt.mpd.weatherasync3.requests.AsyncHttpRequest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WeatherServiceTests {


    private static CompletableFuture<Location>
    getPortugalLocation(OpenWeatherService service, String location)  {

        return
                     service.search(location)
                     .thenApply(s ->
                          s.filter(l -> l.getCountry().equals("PT"))
                          .findFirst()
                          .get()
                      );
    }



    @Test
    public void getForecastForLisbonTest() {

        OpenWeatherService service =
            new OpenWeatherService(new OpenWeatherWebApi());

        List<DayInfo> futLisbonForecast= null;

        // TO COMPLETE
        System.out.println("DayInfo list size: " + futLisbonForecast.size());
        futLisbonForecast.forEach(System.out::println);
    }


    @Test
    public void getForecastDetailForLisbonTest() {
        CounterRequest counterReq = new CounterRequest(new AsyncHttpRequest());

        OpenWeatherService service = new OpenWeatherService(new OpenWeatherWebApi(counterReq));
        var startTime = System.currentTimeMillis();

        List<WeatherInfo> forecastDetail = null;

        // TO COMPLETE

        System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms");


        System.out.println("DayInfo list size: " + forecastDetail.size());
        forecastDetail.forEach(System.out::println);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void
    getForecastDetailForLisbonTest2() {
        CounterRequest counterReq = new CounterRequest(new AsyncHttpRequest());
        OpenWeatherService service = new OpenWeatherService(new OpenWeatherWebApi(counterReq));

        var startTime = System.currentTimeMillis();
        List<WeatherInfo> forecastDetail = null;

        // TO COMPLETE

        System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms");
        System.out.println("DayInfo list size: " + forecastDetail.size());
        forecastDetail.forEach(System.out::println);
    }
}
