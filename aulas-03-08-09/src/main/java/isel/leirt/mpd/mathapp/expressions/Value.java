package isel.leirt.mpd.mathapp.expressions;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

public class Value implements Expr {
    private final double value;

    public Value(double value) {
        this.value = value;
    }
    @Override
    public double eval() {
        return value;
    }

    @Override
    public void writeOn(Writer w) {
        try {
            w.write(Double.toString(value));
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
