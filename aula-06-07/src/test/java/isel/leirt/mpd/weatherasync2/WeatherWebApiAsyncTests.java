package isel.leirt.mpd.weatherasync2;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Semaphore;

public class WeatherWebApiAsyncTests {

    @Test
    public void weatherAtLisboaTest() throws InterruptedException {
        WeatherWebApiAsync api = new WeatherWebApiAsync();

        final double LISBOA_LAT = 38.7071;
        final double LISBOA_LONG = -9.1359;

        Semaphore sem = new Semaphore(0);
        System.out.println("Get lisbon weather using api weatherAt with callbacks");

        api.weatherAt(LISBOA_LAT, LISBOA_LONG, (response, err) -> {
            if (err != null)
                System.out.println("Error: " + err.getMessage());
            else
                System.out.println(response);
            sem.release();
        });

        sem.acquire();
        System.out.println("Done");
    }


    @Test
    public void weatherAtLisboaAndPortoInSerialTest() throws InterruptedException {
        WeatherWebApiAsync api = new WeatherWebApiAsync();

        final double LISBOA_LAT = 38.7071;
        final double LISBOA_LONG = -9.1359;

        final double PORTO_LAT = 41.15794;
        final double PORTO_LONG = -8.629105;


        Semaphore sem = new Semaphore(0);
        long startTime = System.currentTimeMillis();
        api.weatherAt(LISBOA_LAT, LISBOA_LONG, (lisbonResp, exc1) -> {
            api.weatherAt(PORTO_LAT, PORTO_LONG, (portoResp, exc2) -> {
                if (exc1 != null) {
                    System.out.println("error get lisbon weather: " + exc1.getMessage());
                } else if (exc2 != null) {
                    System.out.println("error get porto weather: " + exc2.getMessage());
                } else {
                    System.out.println("Lisbon weather:");
                    System.out.println(lisbonResp);
                    System.out.println("Porto weather:");
                    System.out.println(portoResp);
                }
                sem.release();
            });
        });

        sem.acquire();
        System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms!");
    }

    private void showWeathers(String weather1, Throwable err1,
                              String weather2, Throwable err2) {
        if (err1 != null) {
            System.out.println("error get weather1: " + err1.getMessage());
        } else if (err2 != null) {
            System.out.println("error get weather2: " + err2.getMessage());
        } else {
            System.out.println("weather1:");
            System.out.println(weather1);
            System.out.println("weather2:");
            System.out.println(weather2);
        }
    }

    @Test
    public void weatherAtLisboaAndPortoInParallelTest() throws InterruptedException {
        WeatherWebApiAsync api = new WeatherWebApiAsync();

        final double LISBOA_LAT = 38.7071;
        final double LISBOA_LONG = -9.1359;

        final double PORTO_LAT = 41.15794;
        final double PORTO_LONG = -8.629105;

        Semaphore sem = new Semaphore(0);
        long startTime = System.currentTimeMillis();
        // TO COMPLETE

        sem.acquire();
        System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms!");
    }

    @Test
    public void weatherAtLisboaWithCFTest() throws InterruptedException {
        WeatherWebApiAsync api = new WeatherWebApiAsync();

        final double LISBOA_LAT = 38.7071;
        final double LISBOA_LONG = -9.1359;

        System.out.println("Get lisbon weather using api weatherAtAsync");
        String lisbonWeather = null;
        // TO COMPLETE

        System.out.println(lisbonWeather);
    }

    class Pair<T,U> {
        public final T first;
        public final U second;

        Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }

    @Test
    public void weatherAtLisboaAndPortoSerialWithCFTest() throws InterruptedException {
        WeatherWebApiAsync api = new WeatherWebApiAsync();

        final double LISBOA_LAT = 38.7071;
        final double LISBOA_LONG = -9.1359;

        final double PORTO_LAT = 41.15794;
        final double PORTO_LONG = -8.629105;

        System.out.println("Get lisbon and porto weather in serial using api weatherAtAsync");
        long startTime = System.currentTimeMillis();

        var res = new Pair("weather2", "weather2");

        // TO COMPLETE

        System.out.println("Lisbon weather:");
        System.out.println(res.first);
        System.out.println();
        System.out.println("Porto weather:");
        System.out.println(res.second);

    }

    @Test
    public void weatherAtLisboaAndPortoParallelWithCFTest() throws InterruptedException {
        WeatherWebApiAsync api = new WeatherWebApiAsync();

        final double LISBOA_LAT = 38.7071;
        final double LISBOA_LONG = -9.1359;

        System.out.println("Get lisbon and porto weather in parallel using api weatherAtAsync");
        long startTime = System.currentTimeMillis();

        var res = new Pair("weather2", "weather2");

        // TO COMPLETE
        System.out.println("Lisbon weather:");
        System.out.println(res.first);
        System.out.println();
        System.out.println("Porto weather:");
        System.out.println(res.second);
    }

}



