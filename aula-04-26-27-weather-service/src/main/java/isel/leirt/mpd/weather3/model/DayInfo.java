package isel.leirt.mpd.weather3.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Function;

public class DayInfo {
	private LocalDate date;
	private double maxTempC;
	private double minTempC ;
	private LocalTime sunrise;
	private LocalTime sunset;
	private LocalTime moonrise;
	private LocalTime moonset;
	private double moon_phase;
	private String description;

	private Function<DayInfo, Iterable<WeatherInfo>> temperatures;
	//private Iterable<WeatherInfo> temperatures;
	//private Supplier<Iterable<WeatherInfo>> temperatures;
	public DayInfo(LocalDate date, double maxTempC, double minTempC,
				   LocalTime sunrise, LocalTime sunset,
				   LocalTime moonrise, LocalTime moonset,
				   double moon_phase, String description,
				   Function<DayInfo, Iterable<WeatherInfo>>  temperatures) {
		this.date = date;
		this.maxTempC = maxTempC;
		this.minTempC = minTempC;
		this.sunrise = sunrise;
		this.sunset = sunset;
		this.moonrise = moonrise;
		this.moonset = moonset;
		this.moon_phase = moon_phase;
		this.description = description;
		this.temperatures = temperatures;
	}

	// accessors
	public LocalDate getDate()      { return date; }
	public double getMaxTemp()         { return maxTempC; }
	public double getMinTemp()         { return minTempC; }
	public LocalTime getSunrise()   { return sunrise; }
	public LocalTime getSunset()    { return sunset; }
	public LocalTime getMoonrise()  { return moonrise; }
	public LocalTime getMoonset()   { return moonset; }
	public double getMoonPhase()    { return moon_phase; }
	public String getDescription()    { return description; }


	public Iterable<WeatherInfo> temperatures() {

		return temperatures.apply(this);
	}

	@Override
	public String toString() {
		return "{"
			+ date
			+ ", "				+ description
			+ ", min="          + minTempC
			+ ", max="          + maxTempC
			+ ", sunrise="      + sunrise
			+ ", sunset="       + sunset
			+ ", moonrise="     + moonrise
			+ ", moonset="      + moonset
			+ ", moon_phase="   + moon_phase
			+ "}";
	}
}
