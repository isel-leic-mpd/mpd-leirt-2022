package isel.leirt.mpd.weather2;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import static isel.leirt.mpd.weather2.queries.lazy.LazyQueries.primes;

public class LazyQueriesTests {
    private static final Logger log = LoggerFactory.getLogger(LazyQueriesTests.class);

    @Test
    public void get_primes() {
        for(var p : primes()) {
            if (p > 8000000) {
                log.info("{}", p);
            }
        }
    }
}
