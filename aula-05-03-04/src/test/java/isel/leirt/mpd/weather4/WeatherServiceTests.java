package isel.leirt.mpd.weather4;

import isel.leirt.mpd.weather4.model.DayInfo;
import isel.leirt.mpd.weather4.model.Location;
import isel.leirt.mpd.weather4.requests.HttpRequest;
import isel.leirt.mpd.weather4.requests.MockRequest;
import isel.leirt.mpd.weather4.requests.Request;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static isel.leirt.mpd.queries.lazy3.LazyQueries.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class WeatherServiceTests {
	@Test
	public void get_locations_named_lisbon() {
		Request httpReq = new HttpRequest();
		final int[] count = {0};

		Request counterReq0 =
			path -> {
				count[0]++;
				return httpReq.get(path);
			};

		// And with function composition
		Function<String, Reader> httpFunction = httpReq::get;

		Function<String, String> countFunction =
			s -> {
				count[0]++;
				return s;
			};

	 	Request counterReq = httpFunction.compose(countFunction)::apply;
		// or  countFunction.andThen(httpFunction)::apply;
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

	/*
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

		var  tomorrowTemps  =
			flatMap(
				filter(
					flatMap(
						filter(
							serv.search("Lisbon"),
							l -> l.getCountry().equals("PT")
						),
						l -> l.forecast()
					),
					di -> di.getDate().equals(LocalDate.now().plusDays(1))
				),
				di -> di.temperatures()
			);


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


	 */

}
