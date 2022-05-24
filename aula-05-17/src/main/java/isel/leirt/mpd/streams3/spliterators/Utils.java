package isel.leirt.mpd.streams3.spliterators;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;

public class Utils {

    public static void close(Reader reader) {
        try {
            reader.close();
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
