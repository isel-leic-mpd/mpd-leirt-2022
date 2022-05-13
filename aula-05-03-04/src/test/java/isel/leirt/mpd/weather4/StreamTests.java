package isel.leirt.mpd.weather4;


import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class StreamTests {
    @Test
    public void simple_stream_test() {

        Stream.of("Lisboa", "Porto", "Coimbra")
            .peek( s-> {
                System.out.println("P_" + s);

            })
            .map(c -> c.charAt(0))
            .peek( s-> {
                System.out.println("P_ " + s);
            })
            .forEach(System.out::println);

    }




    @Test
    public void infinite_stream_test() {
        var intNumbers =
            IntStream.iterate(1, i -> i +2);

        var numbers =
            IntStream.iterate(1, i -> i + 2)
            //.peek(System.out::println)
            .mapToObj(n -> "S"+n)
            //.peek(System.out::println)
            .takeWhile(s -> !s.equals("S11"));

        var numberAsList =
            numbers.collect(Collectors.toList());

        System.out.println(numberAsList.size());

        numberAsList.forEach(System.out::println);
        System.out.println();

    }
}
