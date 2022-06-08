package isel.leirt.mpd.weather5;

import isel.leirt.mpd.weatherasync3.OpenWeatherService;
import isel.leirt.mpd.weatherasync3.OpenWeatherWebApi;
import isel.leirt.mpd.weatherasync3.model.Location;
import isel.leirt.mpd.weatherasync3.model.WeatherInfo;

import isel.leirt.mpd.weatherasync3.requests.CounterRequest;
import isel.leirt.mpd.weatherasync3.requests.HttpRequest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


public class WeatherServiceTests {

	private static CompletableFuture<Location>
	getPortugalLocation(OpenWeatherService service, String location)  {

		return service.search(location)
				.thenApply(s ->
					s.filter(l -> l.getCountry().equals("PT"))
					.findFirst()
					.get()
				);

	}



	@Test
	public void getForecastForLisbonTest() {

		OpenWeatherService service = new OpenWeatherService(new OpenWeatherWebApi());

        var futLisbonForecast =
	        getPortugalLocation(service, "Lisbon")
			.thenCompose(loc -> loc.forecast())
	        .join()
	        .collect(toList());

		System.out.println("DayInfo list size: " + futLisbonForecast.size());
		futLisbonForecast.forEach(System.out::println);
	}


	@Test
	public void getForecastDetailForLisbonTest() {
		CounterRequest counterReq = new CounterRequest(new HttpRequest());

		OpenWeatherService service = new OpenWeatherService(new OpenWeatherWebApi(counterReq));
		var startTime = System.currentTimeMillis();

		var forecastDetail =
			getPortugalLocation(service,"Lisboa")
			.thenCompose( loc ->
				loc.forecast()
			)
			.thenCompose(sdays -> {
				return sdays
				.map(di -> di.temperatures())
				.reduce((cf1, cf2) ->
					cf1.thenCombine(cf2, (sw1, sw2) -> Stream.concat(sw1, sw2))
				)
				.orElse(CompletableFuture.completedFuture(Stream.empty()));
			})
			.join()
			.collect(Collectors.toList());
		System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms");


		System.out.println("DayInfo list size: " + forecastDetail.size());
		forecastDetail.forEach(System.out::println);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void
	getForecastDetailForLisbonTest2() {
		CounterRequest counterReq = new CounterRequest(new HttpRequest());
		OpenWeatherService service = new OpenWeatherService(new OpenWeatherWebApi(counterReq));

		var startTime = System.currentTimeMillis();
		var forecastDetail =
			getPortugalLocation(service,"Lisboa")
				.thenCompose( loc -> loc.forecast())
			.thenCompose(sDayInfos ->  {
				CompletableFuture<Stream<WeatherInfo>>[] wInfos=
					sDayInfos
						.map( d -> d.temperatures())
						.toArray(sz -> new CompletableFuture[sz]);

				return CompletableFuture.allOf(wInfos)
										.thenApply(__ ->
											 Arrays.stream(wInfos)
												  .flatMap(cf -> cf.join())
										);
			})
			.join()
			.collect(toList());
		System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms");
		System.out.println("DayInfo list size: " + forecastDetail.size());
		forecastDetail.forEach(System.out::println);
	}
}
