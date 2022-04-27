package isel.leirt.mpd.weather3;

import isel.leirt.mpd.weather3.model.DayInfo;
import isel.leirt.mpd.weather3.model.Location;
import isel.leirt.mpd.weather3.model.WeatherInfo;
import isel.leirt.mpd.weather3.requests.HttpRequest;
import isel.leirt.mpd.weather3.requests.Request;
import isel.leirt.mpd.weather3.requests.MockRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import static isel.leirt.mpd.queries.lazy3.LazyQueries.*;


public class WeatherServiceTests {
	@Test
	public void get_locations_named_lisbon() {
		Request httpReq = new HttpRequest();
		final int[] count = {0};

		Request counterReq =
			path -> {
				count[0]++;
				return httpReq.get(path);
			};


		OpenWeatherService service =
			new OpenWeatherService(
				new OpenWeatherWebApi(counterReq)
			);

		Iterable<Location> locations =
			service.search("Lisbon");

		assertEquals(0,  count[0]);
		locations.forEach(System.out::println);
		assertEquals(1,  count[0]);
	}


	@Test
	public void getForecastForLisbonTest() {
		Request httpReq = new HttpRequest();
		final int[] count = {0};

		Request counterReq =
			path -> {
				count[0]++;
				return httpReq.get(path);
			};
		OpenWeatherService service =
			new OpenWeatherService(
				new OpenWeatherWebApi(counterReq));

		Iterable<DayInfo> forecastWeather =
				flatMap(
					filter(
						service.search("Lisbon"),
						l -> l.getCountry().equals("PT")
					),
					l ->  l.forecast()
				);

		List<DayInfo>  forecastList = toList(forecastWeather);

		assertEquals(2, count[0]);
		long nDays = count(forecastList);
		assertEquals(2, count[0]);
		System.out.println("DayInfo list size: " + nDays);
		assertEquals(8, nDays);
		forecastList.forEach(System.out::println);
		assertEquals(2, count[0]);
	}

	@Test
	public void getForecastDetailForLisbonTest() {
		OpenWeatherService serv =
			new OpenWeatherService(new OpenWeatherWebApi(new HttpRequest()));

		// to implement
		Iterable<WeatherInfo> tomorrowTemps  = null;




		System.out.println("WeatherInfo list size: " + count(tomorrowTemps));
		tomorrowTemps.forEach(System.out::println);

	}

	@Test
	public void checkTheLocationCreationDoesntInvokeTheDataSourceTest() {
		Request request = new HttpRequest();

		int[] count = {0};

		Request counterReq =
			 p -> {
				count[0]++;
				return request.get(p);
			 };

		OpenWeatherService service =
			new OpenWeatherService(new OpenWeatherWebApi(counterReq));

		System.out.println("Initial counterReq= " + count[0]);
		assertEquals(0, count[0]);
		Iterable<Location> locations = service.search("Lisbon");

		System.out.println("counterReq after search locations  = " + count[0]);
		assertEquals(0, count[0]);
		Location loc =  first(locations).get();

		System.out.println("counterReq after get first location=" + count[0]);
		assertEquals(1, count[0]);
		Iterable<DayInfo> forecast = loc.forecast();


		System.out.println("counterReq after loc.forecast call = " + count[0]);
		assertEquals(1, count[0]);
		forecast.forEach(System.out::println);
		assertEquals(2, count[0]);
		System.out.println("counterReq after forecast foreach = " + count[0]);

	}


}
