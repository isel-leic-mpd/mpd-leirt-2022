package isel.leirt.mpd.mathapp_aula_03_15.expressions;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.function.Consumer;

public abstract class BinaryExpr implements Expr {
    protected final Expr left;
    protected final Expr right;

    protected BinaryExpr(Expr left, Expr right) {
        this.right = right;
        this.left = left;
    }

    protected abstract String getOper();

    public void writeOn(Writer w)   {
        try {
            left.writeOn(w);
            w.write(getOper());
            right.writeOn(w);
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public String toString() {
        return  getEvalText();
    }

}
