package isel.leirt.mpd.expressions;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

public class Mul implements Expr {

    private final Expr left;
    private final Expr right;

    public Mul(Expr left, Expr right) {
        this.right = right;
        this.left = left;
    }

    @Override
    public double eval() {
        return left.eval()*right.eval();
    }

    @Override
    public void writeOn(Writer w)   {
        try {
            left.writeOn(w);
            w.write("*");
            right.writeOn(w);
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
