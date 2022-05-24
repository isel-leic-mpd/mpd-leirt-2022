package isel.leirt.mpd;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static isel.leirt.mpd.queries2.MoreLazyQueries.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CacheTests {

    @Test
    public void cache_test() {
        Random r = new Random();
        Iterable<Integer> nrs = generate(() -> r.nextInt(100));
        nrs = cache(nrs);
        List<Integer> expected = toList(limit(nrs, 10));
        List<Integer> actual = toList(limit(nrs, 10));
        assertEquals(expected, actual);
    }

    @Test
    public void cache_for_deterministic_sequence_interleaved() {
        Iterable<Integer>
            evens = iterate(2, n -> n+ 2);

        Iterable<Integer> evensCache = cache(evens);

        Iterator<Integer> it1 = evensCache.iterator();
        Iterator<Integer> it2 = evensCache.iterator();

        int it1Next = it1.next();
        assertEquals(2, it1Next);

        int it2Next = it2.next();
        assertEquals(2, it2Next);
        it2Next =  it2.next();
        assertEquals( 4,it2Next );
    }
}
