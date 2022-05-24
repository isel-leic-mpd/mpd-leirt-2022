package isel.leirt.mpd.streams3.spliterators;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class ZipSlipterator<T,U,V> extends Spliterators.AbstractSpliterator<V> {
    private final BiFunction<T,U,V> combiner;
    private final Spliterator<T> src1;
    private final Spliterator<U> src2;

    private boolean done;

    public ZipSlipterator(Spliterator<T> src1, Spliterator<U> src2,
                          BiFunction<T,U,V> combiner) {
        super(Math.min(src1.estimateSize(), src2.estimateSize()),
            src1.characteristics() & src2.characteristics());
        this.combiner = combiner;
        this.src1 = src1;
        this.src2 = src2;
        done = false;
    }

    @Override
    public boolean tryAdvance(Consumer<? super V> action) {
        boolean[] consumed = {false};

        if (done) return false;
        src1.tryAdvance(t -> {
          src2.tryAdvance(u -> {
              action.accept(combiner.apply(t,u));
              consumed[0] = true;
          }) ;
        });
        if (!consumed[0]) done = true;
        return consumed[0];
    }
}
