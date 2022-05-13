package isel.leirt.mpd.streams2.spliterators;

import java.io.*;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SpliteratorReaderLines
    extends Spliterators.AbstractSpliterator<String> {

    private Supplier<InputStream> inputSupplier;
    private BufferedReader reader;
    private boolean done;

    private BufferedReader reader() {
        if (reader == null)
            reader =
                new BufferedReader(new InputStreamReader(inputSupplier.get()));
        return reader;
    }

    private static void closeReader(Reader r) {
        try {
            r.close();
        }
        catch(IOException e) {}
    }

    private String readLine() {
        try {
            return reader().readLine();
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public SpliteratorReaderLines(Supplier<InputStream> inputSupplier) {
        super(Long.MAX_VALUE, 0);
        this.inputSupplier = inputSupplier;
    }

    @Override
    public boolean tryAdvance(Consumer<? super String> action) {
        try {
            if (done) return false;
            String line = reader().readLine();
            if (line == null) {
                done = true;
                reader.close();
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
                closeReader(reader());
        }
    }
}