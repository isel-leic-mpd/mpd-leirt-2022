package isel.leirt.mpd.mathapp;

import isel.leirt.mpd.mathapp.expressions.Expr;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

public class VarRef implements Expr {
    private final String name;
    private final MathLab lab;

    public VarRef(String name, MathLab lab)  {
        this.name = name;
        this.lab = lab;
    }
    @Override
    public double eval() {
        return lab.eval(name);
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
}
