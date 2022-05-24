package isel.leirt.mpd.streams3.spliterators;

import java.io.*;
import java.util.Spliterators;
import java.util.function.Consumer;

import static isel.leirt.mpd.streams3.spliterators.Utils.close;


public class LinesSpliterator
    extends Spliterators.AbstractSpliterator<String> {

    private BufferedReader reader;
    private boolean done;

    public LinesSpliterator(BufferedReader reader) {
        super(Long.MAX_VALUE, 0);
        this.reader = reader;
        done = false;
    }

    @Override
    public boolean tryAdvance(Consumer<? super String> action) {
        if (done) return false;
        try {

            String line = reader.readLine();
            if (line == null) {
                done = true;
                return false;
            }
            action.accept(line);
            return true;
        }
        catch(IOException e) {
            done = true;
            throw new UncheckedIOException(e);
        }
        finally {
            if (done)
                close(reader);
        }
    }
}