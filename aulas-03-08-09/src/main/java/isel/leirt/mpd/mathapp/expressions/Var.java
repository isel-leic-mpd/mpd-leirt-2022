package isel.leirt.mpd.mathapp.expressions;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

public class Var implements Expr {
    private final String name;
    private Expr val;

    public Var(String name, Expr val) {
        this.name = name;
        this.val = val;
    }
    @Override
    public double eval() {
        return val.eval();
    }

    @Override
    public void writeOn(Writer w) {
        try {
            w.write(name);
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void setValue(Expr value) {
        this.val = value;
    }
}
