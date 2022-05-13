package isel.leirt.mpd;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static isel.leirt.mpd.queries2.MoreLazyQueries.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoreQueriesTests {
    @Test
    public void testCache() {
        Random r = new Random();
        Iterable<Integer> nrs = cache(generate(() -> r.nextInt(100)));

        List<Integer> expected = toList(limit(nrs, 10));
        List<Integer> actual = toList(limit(nrs, 10));

        assertEquals(expected, actual);
    }
}
