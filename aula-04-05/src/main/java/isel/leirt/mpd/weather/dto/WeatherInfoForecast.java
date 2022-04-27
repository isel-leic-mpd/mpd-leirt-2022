package isel.leirt.mpd.weather.dto;

import static isel.leirt.mpd.weather.utils.PrintUtils.EOL;

public class WeatherInfoForecast extends WeatherInfoBase {
    private String dt_txt;
    private String form_date_text;

    @Override
    protected String get_form_date_text() {
        if (form_date_text == null) {
            String parts[] = dt_txt.split(" ");
            form_date_text = parts[0] + "T" + parts[1];
        }
        return form_date_text;
    }

    @Override
    public String toString() {
        return "{" + EOL
            + "\tdateTime = " + dateTime() + EOL
            + "\tdescription = " + description() + EOL
            + "\tweather = " + weather() + EOL
            + "\tdt_txt = " + dt_txt + EOL
            + "}";
    }
}
