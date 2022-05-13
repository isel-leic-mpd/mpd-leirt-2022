package isel.leirt.mpd.weather4;

import isel.leirt.mpd.weather4.dto.ForecastHourlyDto;
import isel.leirt.mpd.weather4.dto.LocationDto;
import isel.leirt.mpd.weather4.dto.ForecastInfoDto;
import isel.leirt.mpd.weather4.model.DayInfo;
import isel.leirt.mpd.weather4.model.Location;
import isel.leirt.mpd.weather4.model.WeatherInfo;
import isel.leirt.mpd.weather4.OpenWeatherWebApi;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static isel.leirt.mpd.queries.lazy3.LazyQueries.*;
import static isel.leirt.mpd.queries.lazy3.LazyQueries.map;


public class OpenWeatherService {
	private  OpenWeatherWebApi api;

	public OpenWeatherService( OpenWeatherWebApi api) {
		this.api = api;
	}

	public Iterable<Location> search(String placeName) {
			return map(
				() -> api.search(placeName).iterator(),
				this::dtoToLocation
			);

	}

	public Iterable<DayInfo> forecastAt(Location loc) {
		// the operations is lazy. Note that the web api
		// is only accessed when the returned iterable will be consumed
		return map(
			() ->api.forecastWeatherAt(
				loc.getLatitude(), loc.getLongitude()).iterator(),
			dto -> dtoToDayInfo(dto, loc)
		);
	}

	public Iterable<WeatherInfo> weatherDetail(Double lat,
											   Double lon,
											   LocalDate day) {
		return
			filter(
				map(
					() -> api.forecastDetailAt(lat, lon).iterator(),
					this::dtoToWeatherInfo
				),
				dto -> dto.getLocalTime().toLocalDate().equals(day)
			);
	}

	public Iterable<WeatherInfo> weatherDetail(Location loc, DayInfo di) {
		return weatherDetail(loc.getLatitude(), loc.getLongitude(), di.getDate());
	}

	private  Location dtoToLocation(LocationDto dto) {
		return new Location(dto.getName(),
			dto.getCountry(),
			dto.getLat(),
			dto.getLon(),
			loc -> forecastAt(loc)
		);
	}

	private  WeatherInfo dtoToWeatherInfo(ForecastHourlyDto dto) {
		return new WeatherInfo(
			dto.observationDateTime(),
			dto.temp(),
			dto.description(),
			dto.humidity(),
			dto.feelsLike());
	}

	public DayInfo dtoToDayInfo(ForecastInfoDto dto, Location loc) {
		return new DayInfo(
			dto.obsDate(),
			dto.maxTemp(),
			dto.minTemp(),
			dto.sunRise(),
			dto.sunSet(),
			dto.moonRise(),
			dto.moonSet(),
			dto.moonPhase(),
			dto.getDescription(),
			// to implement
			dayInfo -> weatherDetail(loc, dayInfo)
		);
	}
}
