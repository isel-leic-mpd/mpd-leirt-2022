package isel.leirt.mpd.streams2;

import org.junit.jupiter.api.Test;

import java.util.Spliterator;
import java.util.stream.Stream;

public class StreamsTests {
    @Test
    public void count_primes_in_range() {
        long startTime = System.currentTimeMillis();
        System.out.println(StreamUtils.countPrimesInRange(1, 10000000));
        System.out.println("count in " + (System.currentTimeMillis()-startTime) + "ms");
    }


    @Test
    public void use_stream_spliterator_directly() {
        Stream<String> stream =
            Stream.of("Joao", "Carlos", "Maria", "Guilherme");

        Spliterator<String> it = stream.spliterator();

        System.out.println("stream estimated size = " +
            it.estimateSize());

        while(it.tryAdvance(s -> System.out.println(s)));
    }
}
