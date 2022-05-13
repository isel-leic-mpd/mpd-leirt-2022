package isel.leirt.mpd.streams2;

import isel.leirt.mpd.streams2.spliterators.SpliteratorReaderLines;
import isel.leirt.mpd.streams2.spliterators.ZipSlipterator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

    private static boolean isPrime(long n) {
        if (n == 2) return true;
        if (n < 2 || n % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(n); i += 2)
            if (n % i == 0) return false;
        return true;
    }

    public static long countPrimesInRange(long start, long end) {
        return LongStream.rangeClosed(start, end)
            .filter(n -> isPrime(n))
            .parallel()
            .count();
    }










    public static <T,U,V> Stream<V> zip(Stream<T> src1, Stream<U> src2,
                                        BiFunction<T,U,V> combiner) {
        return
            StreamSupport
                .stream(new ZipSlipterator<>(
                    src1.spliterator(),
                    src2.spliterator(),
                    combiner),false);

    }

    public static  Stream<String> lines(String textFileName) {
        Supplier<InputStream> input =
            () -> {
                try {
                    return new FileInputStream(textFileName);
                }
                catch(IOException e) {
                    throw new UncheckedIOException(e);
                }
            };
        return
            StreamSupport
                .stream(new SpliteratorReaderLines(input), false);
    }
}
