package isel.leirt.mpd.mathapp.expressions;

import java.io.Writer;

public interface Expr {
    double eval();
    void writeOn(Writer w);
}
