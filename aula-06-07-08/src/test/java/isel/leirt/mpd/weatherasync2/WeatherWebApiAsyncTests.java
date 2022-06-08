package isel.leirt.mpd.weatherasync2;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class WeatherWebApiAsyncTests {
    class Pair<T,U> {
        public final T first;
        public final U second;

        Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }

    public CompletableFuture<String>
    getAllCoordinatesWeather(List<Pair<Double,Double>> places) {
        WeatherWebApiAsync api = new WeatherWebApiAsync();
        return
            places.stream()
            .map(pair -> api.weatherAtCF(pair.first, pair.second))
            .reduce((cf1, cf2) -> cf1.thenCombine(cf2, (s1, s2) -> s1.join(s2)))
            .orElse(CompletableFuture.completedFuture(""));

    }

    public CompletableFuture<String>
    getAllCoordinatesWeather2(List<Pair<Double,Double>> places) {
        WeatherWebApiAsync api = new WeatherWebApiAsync();

        var futures =
            places.stream()
            .map(pair -> api.weatherAtCF(pair.first, pair.second))
            .toArray(sz -> new CompletableFuture[sz]);

        return
            CompletableFuture.allOf(futures)
            .thenApply(__ ->
                Arrays.stream(futures)
                .map(cf -> (String) cf.join())
                //.reduce("", (s1, s2) -> s1.join(s2))
                .collect(Collectors.joining())
            );

    }

    @Test
    public void weatherAtLisboaTest() throws InterruptedException {
        WeatherWebApiAsync api = new WeatherWebApiAsync();

        final double LISBOA_LAT = 38.7071;
        final double LISBOA_LONG = -9.1359;

        Semaphore sem = new Semaphore(0);
        System.out.println("Get lisbon weather using api weatherAt with callbacks");
        long startTime = System.currentTimeMillis();
        api.weatherAt(LISBOA_LAT, LISBOA_LONG, (response, err) -> {
            if (err != null)
                System.out.println("Error: " + err.getMessage());
            else
                System.out.println(response);
            sem.release();
        });

        sem.acquire();
        System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms!");

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

        AtomicReference<String> portoResp = new AtomicReference<>();
        AtomicReference<String> lisboaResp = new AtomicReference<>();
        AtomicReference<Throwable> portoErr = new AtomicReference<>();
        AtomicReference<Throwable>  lisboaErr = new AtomicReference<>();
        AtomicInteger counter = new AtomicInteger();

        Semaphore sem = new Semaphore(0);
        //Lock lock = new ReentrantLock();
        long startTime = System.currentTimeMillis();

        api.weatherAt(LISBOA_LAT,LISBOA_LONG, (resp, err) -> {
            lisboaResp.set(resp);
            lisboaErr.set(err);
            if (counter.incrementAndGet() == 2) {
                showWeathers(resp, err, portoResp.get(), portoErr.get());
                sem.release();
            }

        } );

        api.weatherAt(PORTO_LAT,PORTO_LONG, (resp, err) -> {
            portoResp.set(resp);
            portoErr.set(err);
            if (counter.incrementAndGet() == 2) {
                showWeathers(lisboaResp.get(), lisboaErr.get(), resp, err);
                sem.release();
            }

        } );

        sem.acquire();
        System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms!");

    }

    @Test
    public void weatherAtLisboaWithCFTest() throws InterruptedException {
        WeatherWebApiAsync api = new WeatherWebApiAsync();

        final double LISBOA_LAT = 38.7071;
        final double LISBOA_LONG = -9.1359;

        System.out.println("Get lisbon weather using api weatherAtAsync");
        String lisbonWeather =
            api.weatherAtCF(LISBOA_LAT, LISBOA_LONG)
                   .join();

        System.out.println(lisbonWeather);
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

        var res =
            api.weatherAtCF(LISBOA_LAT, LISBOA_LONG)
           .thenCompose(lisbonResp ->
               api.weatherAtCF(PORTO_LAT, PORTO_LONG)
               .thenApply(portoResp -> new Pair(lisbonResp, portoResp))
           )
           .join();

        
        // TO COMPLETE
        System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms!");

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

        final double PORTO_LAT = 41.15794;
        final double PORTO_LONG = -8.629105;

        System.out.println("Get lisbon and porto weather in parallel using api weatherAtAsync");
        long startTime = System.currentTimeMillis();

        var fut1 = api.weatherAtCF(LISBOA_LAT, LISBOA_LONG);
        var fut2 = api.weatherAtCF(PORTO_LAT, PORTO_LONG);
        var res =
            fut1.thenCombine(fut2, (resp1, resp2) ->  new Pair(resp1, resp2))
                .join();
        System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms!");

        // TO COMPLETE
        System.out.println("Lisbon weather:");
        System.out.println(res.first);
        System.out.println();
        System.out.println("Porto weather:");
        System.out.println(res.second);
    }

}



