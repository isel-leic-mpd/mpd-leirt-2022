package isel.leirt.mpd.weather4.dto;

import com.google.gson.annotations.SerializedName;
import isel.leirt.mpd.weather4.utils.PrintUtils;
import isel.leirt.mpd.weather4.utils.TimeUtils;

import java.time.LocalDateTime;

import static isel.leirt.mpd.weather4.utils.PrintUtils.EOL;

public class PollutionInfoDto {

    public static class Main {
        @SerializedName("aqi")
        int qualityIndex;
    }

    public static class Components {
        double co;
        double no;
        double no2;
        double o3;
        double so2;
        double pm2_5;
        double pm2_10;
        double nh3;
    }

    private Main main;
    private Components components;
    private long dt;

    public double getCo() { return components.co; }

    public double getNo() { return components.no; }

    public double getNo2() { return components.no2; }

    public double getSo2() { return components.so2; }

    public double getO3() { return components.o3; }

    public double getFPC() { return components.pm2_5; }

    public double getCPC() { return components.pm2_10; }

    public double getNh3() { return components.nh3; }

    public double getQI() { return main.qualityIndex; }

    public LocalDateTime dateTime() {
        return TimeUtils.fromUnixTime(dt);
    }

    @Override
    public String toString() {
        return "{" + PrintUtils.EOL +
                    "\tdate = " + dateTime() + PrintUtils.EOL +
                    "\tco= " + getCo() + PrintUtils.EOL +
                    "\tno= " + getNo() + PrintUtils.EOL +
                    "\tno2= " + getNo() + PrintUtils.EOL +
                    "\to3= "  + getO3() + PrintUtils.EOL +
                    "\toso2= "  + getSo2() + PrintUtils.EOL +
                    "\tfpc= "  + getFPC() + PrintUtils.EOL +
                    "\tcpc= "  + getCPC() + PrintUtils.EOL +
                    "\tnh3= "  + getNh3() + PrintUtils.EOL +
                    "\tquality = " + getQI() + PrintUtils.EOL +
                "}";
    }
}
