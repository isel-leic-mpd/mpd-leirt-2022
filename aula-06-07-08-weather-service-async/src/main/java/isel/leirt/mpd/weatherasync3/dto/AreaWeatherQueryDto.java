package isel.leirt.mpd.weatherasync3.dto;

public class AreaWeatherQueryDto
{
	public final WeatherInfoDto[] list;

	public AreaWeatherQueryDto(WeatherInfoDto[] list) {
		this.list = list;
	}
}
