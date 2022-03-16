package isel.leirt.mpd.mathapp_aula_03_16.expressions;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.function.Consumer;

public interface Expr {
    double eval();
    void writeOn(Writer w);

    /**
     * default method used to avoid use try-catch blocks
     * on writeOn implementations.
     * Note this is a form of Template Method pattern where
     * the IOAction provided as a parameter defines the needed behaviour
     * @param action
     */
    default void doWrite(IOAction action) {
        try {
            action.run();
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    default void visit(Consumer<Expr> action) {
        action.accept(this);
    }

}
