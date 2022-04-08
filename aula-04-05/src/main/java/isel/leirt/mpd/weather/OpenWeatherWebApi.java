package isel.leirt.mpd.weather;

import com.google.gson.Gson;
import isel.leirt.mpd.weather.dto.*;
import isel.leirt.mpd.weather.exceptions.WeatherApiException;
import isel.leirt.mpd.weather.utils.TimeUtils;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class OpenWeatherWebApi {
    private static final String API_KEY;

    private static final String WEATHER_HOST =
            "http://api.openweathermap.org/";

    private static final String GEO_SERVICE =
        "geo/1.0/";

    private static final String WEATHER_SERVICE =
            "http://api.openweathermap.org/data/2.5/";

    private static final String WEATHER_AT_TEMPLATE =
            "weather?lat=%f&lon=%f&units=metric&appid=%s";

    private static final String AREA_WEATHER_TEMPLATE =
            // longl, latb, longr, latt
            "box/city?bbox=%f,%f,%f,%f,50&units=metric&appid=%s";

    private static final String FORECAST_WEATHER_TEMPLATE =
            "forecast?lat=%f&lon=%f&units=metric&appid=%s";

    private static final String AIR_POLLUTION_AT_TEMPLATE =
            "air_pollution?lat=%f&lon=%f&appid=%s";

    private static final String AIR_POLLUTION_HISTORY_TEMPLATE =
            "air_pollution/history?lat=%f&lon=%f&start=%d&end=%d&appid=%s";

    private static final String LOCATION_SEARCH_TEMPLATE =
        WEATHER_HOST +
        GEO_SERVICE +
        "direct?q=%s&limit=10&lang=pt&appid=%s";

    protected final Gson gson;

    /**
     * Retrieve API-KEY from resources
     * @return
     */
    private static String getApiKeyFromResources() {
        try {
            URL keyFile =
                    ClassLoader.getSystemResource("openweatherapi-app-key.txt");
            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(keyFile.openStream()))) {
                return reader.readLine();
            }

        }
        catch(IOException e) {
            throw new IllegalStateException(
                    "YOU MUST GET a KEY from  openweatherapi.com and place it in src/main/resources/openweatherapi-app-key2.txt");
        }
    }

    /**
     * Static Constructor
     */
    static {
        API_KEY = getApiKeyFromResources();
    }

    /**
     * Get WeatherInfo's from a local coordinates given a date interval
     * @param lat
     * @param lon
     * @return
     */
    public WeatherInfo weatherAt(double lat, double lon) {
        String path =  WEATHER_SERVICE +
            String.format(WEATHER_AT_TEMPLATE, lat, lon, API_KEY);
        try {
            URL url = new URL(path);
            try (Reader reader = new InputStreamReader(url.openStream())) {
                WeatherInfo winfo =
                        gson.fromJson(reader, WeatherInfo.class);
                return winfo;
            }
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Get current air pollution metrics from a local coordinates
     * @param lat
     * @param lon
     * @return
     */
    public PollutionInfo airPollutionAt(double lat, double lon) {
        String path =   WEATHER_SERVICE +
            String.format(AIR_POLLUTION_AT_TEMPLATE, lat, lon, API_KEY);

        try {
            URL url = new URL(path);
            try(Reader reader = new InputStreamReader(url.openStream())) {
                PollutionInfoQuery pi =
                    gson.fromJson(reader, PollutionInfoQuery.class);
                if (pi.list == null || pi.list.length != 1)
                    throw new WeatherApiException("response list must have one element");
                return pi.list[0];
            }
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    /**
     * Get WeatherInfo's forecast for a local coordinates
     * @param lat
     * @param lon
     * @return
     */
    public ForecastInfo forecastWeatherAt(double lat, double lon) {
        String path =  WEATHER_SERVICE + String.format(FORECAST_WEATHER_TEMPLATE, lat, lon, API_KEY);
        try {
            URL url = new URL(path);
            try (Reader reader = new InputStreamReader(url.openStream())) {
                ForecastInfo finfo =
                        gson.fromJson(reader, ForecastInfo.class);
                return finfo;
            }
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Get WeatherInfo's from locals inside a rectangular coordinates area
     * @param lati
     * @param longi
     * @param latf
     * @param longf
     * @return
     */
    public List<WeatherInfo> weatherAtArea(double lati, double longi, double latf, double longf) {
        String path =  WEATHER_SERVICE +
             String.format(AREA_WEATHER_TEMPLATE, lati, longi,latf,longf, API_KEY);
        try {
            URL url = new URL(path);
            try (Reader reader = new InputStreamReader(url.openStream())) {
                AreaWeatherQuery winfo =
                        gson.fromJson(reader, AreaWeatherQuery.class);
                return Arrays.asList(winfo.list);
            }
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Get local info given the name of the local
     * @param location
     * @return
     */
    public Location[] search(String location) {
        try {
            String path =  String.format(LOCATION_SEARCH_TEMPLATE, location, API_KEY);
            URL url = new URL(path);

            try (Reader reader =  new InputStreamReader(url.openStream())) {
                Location[] search = gson.fromJson(reader, Location[].class);
                return search;
            }
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public List<PollutionInfo> pollutionHistoryAt(
            double lati, double longi, LocalDate start, LocalDate end) {

        String path =  WEATHER_SERVICE +
            String.format(AIR_POLLUTION_HISTORY_TEMPLATE,
                            lati, longi,
                    TimeUtils.toUnixTime(start), TimeUtils.toUnixTime(end), API_KEY);
        try {
            URL url = new URL(path);
            try (Reader reader = new InputStreamReader(url.openStream())) {
                PollutionInfoQuery winfo =
                        gson.fromJson(reader, PollutionInfoQuery.class);
                return Arrays.asList(winfo.list);
            }
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    public OpenWeatherWebApi() {
        gson = new Gson();
    }
}
