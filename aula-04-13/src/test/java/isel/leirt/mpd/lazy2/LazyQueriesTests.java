package isel.leirt.mpd.lazy2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;

import static isel.leirt.mpd.queries.lazy2.LazyQueries.*;

public class LazyQueriesTests {
    private static final Logger log = LoggerFactory.getLogger(LazyQueriesTests.class);

    @Test
    public void get_primes() {
        for(var p : primes()) {

            log.info("{}", p);
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

        var actual = map(
            strings,
            (s) -> s.length()
        );

        List<Integer> actualList = new ArrayList<>();
        for(var v : actual)
            actualList.add(v);

        Assertions.assertEquals(expected, actualList);
    }


    @Test
    public void show_primes_with_least_digit_equals_7() {
        var primesWith7AsLeastDigit =
            takeWhile(
                filter(
                    primes(),
                    p -> {
                        if ((p % 10) == 7) {
                            System.out.println(p);
                            return true;
                        }
                        return false;
                    }
                ),
                p -> p < 1000000);
            out.println("Iterable created: " +
            primesWith7AsLeastDigit);

        var primesIt =
            primesWith7AsLeastDigit.iterator();
        System.out.println("Iterator created: " +
            primesIt);

        if (primesIt.hasNext())
            System.out.println(primesIt.next());

        for(var p : primesWith7AsLeastDigit) {
            System.out.println(p);
        }
    }
}
