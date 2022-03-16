package isel.leirt.mpd.mathapp_aula_03_15;

import isel.leirt.mpd.mathapp_aula_03_15.exceptions.CircularDependencyException;
import isel.leirt.mpd.mathapp_aula_03_15.expressions.Expr;
import isel.leirt.mpd.mathapp_aula_03_15.notifications.Publisher;
import isel.leirt.mpd.mathapp_aula_03_15.notifications.PublisherImpl;
import isel.leirt.mpd.mathapp_aula_03_15.notifications.Subscriber;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.function.Consumer;

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

    public void setValue(Expr newVal) {
        this.val = newVal;
    }
}
