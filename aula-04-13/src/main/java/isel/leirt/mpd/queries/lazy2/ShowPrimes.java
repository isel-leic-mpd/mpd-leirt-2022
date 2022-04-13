package isel.leirt.mpd.queries.lazy2;

import static isel.leirt.mpd.weather2.queries.lazy.LazyQueries.primes;

public class ShowPrimes {
    public static void main(String[] args) {

        for(var p : primes()) {
            System.out.println(p);
        }

    }
}
