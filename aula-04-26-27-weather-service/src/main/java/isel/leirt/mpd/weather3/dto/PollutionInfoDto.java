package isel.leirt.mpd.weather3.dto;

import com.google.gson.annotations.SerializedName;
import isel.leirt.mpd.weather3.utils.TimeUtils;

import java.time.LocalDateTime;

import static isel.leirt.mpd.weather3.utils.PrintUtils.EOL;

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
        return "{" + EOL +
                    "\tdate = " + dateTime() + EOL +
                    "\tco= " + getCo() + EOL +
                    "\tno= " + getNo() + EOL +
                    "\tno2= " + getNo() + EOL +
                    "\to3= "  + getO3() + EOL +
                    "\toso2= "  + getSo2() + EOL +
                    "\tfpc= "  + getFPC() + EOL +
                    "\tcpc= "  + getCPC() + EOL +
                    "\tnh3= "  + getNh3() + EOL +
                    "\tquality = " + getQI() + EOL +
                "}";
    }
}
