package isel.leirt.mpd.streams3;

import org.junit.jupiter.api.Test;
import java.util.stream.Stream;

import static isel.leirt.mpd.streams3.StreamUtils.lines;
import static isel.leirt.mpd.streams3.StreamUtils.zip;
import static java.util.stream.Stream.iterate;

public class StreamsTests {

    @Test
    public void test_lines_spliterator() {
        String currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);

        try (Stream<String> lines = lines("StreamUtils.java")) {
             lines
             .limit(4)
            .forEach(System.out::println);
        }
    }

    @Test
    public void test_lines_spliterator_with_lines_number() {
        String currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);

        try (Stream<String> lines = lines("StreamUtils.java")) {
            zip(
                iterate(1, i -> i +1 ),
                lines,
                (n, s) -> String.format("%3d: %s", n, s)
            )
            .limit(4)
            .forEach(System.out::println);
        }
    }
}
