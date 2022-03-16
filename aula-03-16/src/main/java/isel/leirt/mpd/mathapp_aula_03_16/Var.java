package isel.leirt.mpd.mathapp_aula_03_16;

import isel.leirt.mpd.mathapp_aula_03_16.expressions.Expr;
import isel.leirt.mpd.mathapp_aula_03_16.notifications.Publisher;
import isel.leirt.mpd.mathapp_aula_03_16.notifications.PublisherImpl;
import isel.leirt.mpd.mathapp_aula_03_16.notifications.Subscriber;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;

public class Var implements Expr, Subscriber, Publisher {
    private final String name;
    private Expr val;
    private PublisherImpl pub;

    public Var(String name, Expr val) {
        this.name = name;
        this.val = val;
        pub = new PublisherImpl(this);
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

    /**
     * auxiliary method to subscribe on all referenced variables
     * in the "expr" expression
     * @param expr
     */
    private void subscribeDependsOn(Expr expr) {
        expr.visit(exp -> {
            if (exp instanceof Var) {
                ((Var) exp).subscribe(this);
            }
        });
    }

    /**
     * Note this method is not complete...
     * @param newVal
     */
    public void setValue(Expr newVal) {
        // since the variable expression is changing
        // we must subscribe all the referenced variables in the expression
        subscribeDependsOn(newVal);

        this.val = newVal;

        // now we must notify all our listeners that a change ocurred
        notifyChange();
    }

    @Override
    public void subscribe(Subscriber s) {
        pub.subscribe(s);
    }

    @Override
    public void unsubscribe(Subscriber s) {
        pub.unsubscribe(s);
    }

    @Override
    public void notifyChange() {
        pub.notifyChange();
    }

    @Override
    public void valueChanged(Publisher pub) {
        System.out.print(name + " = " + eval());
        if (pub instanceof Var)  {
            System.out.print(", due to " + ((Var) pub).name + " change!");
        }
        System.out.println();
        notifyChange();
    }

    protected void show() {
        System.out.println(name + " = " + eval());
    }
}
