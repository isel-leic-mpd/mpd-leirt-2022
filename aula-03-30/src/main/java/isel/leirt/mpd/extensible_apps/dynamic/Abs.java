package isel.leirt.mpd.extensible_apps.dynamic;

import isel.leirt.mpd.mathapp_aula_03_16.expressions.Expr;

import java.io.Writer;

public class Abs implements Expr {
    private final Expr expr;

    public Abs(Expr expr)  {
        this.expr = expr;
    }

    @Override
    public double eval() {
        double res = expr.eval();
        if (res< 0) return -res;
        return res;
    }

    @Override
    public void writeOn(Writer w) {
        doWrite(() -> {
            w.write("abs(");
            expr.writeOn(w);
            w.write(")");
        });
    }
}
