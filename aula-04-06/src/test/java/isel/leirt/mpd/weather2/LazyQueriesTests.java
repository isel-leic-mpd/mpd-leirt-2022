package isel.leirt.mpd.weather2;

import isel.leirt.mpd.weather2.queries.lazy.LazyQueries;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void simple_map_from_strings_to_numbers() {
        var strings = List.of(
            "word1",
            "benfica",
            "erro",
            "sim"
        );

        var expected = List.of(
            5,
            7,
            4,
            3
        );

        var actual = LazyQueries.map(
            strings,
            (s) -> s.length()
        );

        List<Integer> actualList = new ArrayList<>();
        for(var v : actual)
            actualList.add(v);

        Assertions.assertEquals(expected, actualList);

    }
}
