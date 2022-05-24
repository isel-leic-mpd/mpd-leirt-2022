package isel.leirt.mpd.streams3;

import isel.leirt.mpd.streams3.spliterators.LinesSpliterator;
import isel.leirt.mpd.streams3.spliterators.ZipSlipterator;

import java.io.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static isel.leirt.mpd.streams3.spliterators.Utils.close;

public class StreamUtils {


    public static <T,U,V> Stream<V> zip(Stream<T> src1, Stream<U> src2,
                                        BiFunction<T,U,V> combiner) {
        return  StreamSupport.stream(
            new ZipSlipterator<T,U,V>(src1.spliterator(), src2.spliterator(), combiner), false);
    }


    public static  Stream<String> lines(String textFileName) {
        BufferedReader reader;
        try {
            reader =
                new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(textFileName)
                    )
                );
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }

        return
            StreamSupport.stream(new LinesSpliterator(reader), false)
            .onClose( () -> {
                System.out.println("Stream closed!");
                close(reader);
            });

    }
}
