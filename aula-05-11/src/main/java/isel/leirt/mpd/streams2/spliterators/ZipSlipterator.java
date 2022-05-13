package isel.leirt.mpd.streams2.spliterators;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class ZipSlipterator<T,U,V> extends Spliterators.AbstractSpliterator<V> {
    private final BiFunction<T,U,V> combiner;
    private final Spliterator<T> src1;
    private final Spliterator<U> src2;

    public ZipSlipterator(Spliterator<T> src1, Spliterator<U> src2,
                          BiFunction<T,U,V> combiner) {
        super(Math.min(src1.estimateSize(), src2.estimateSize()),0);
        this.combiner = combiner;
        this.src1 = src1;
        this.src2 = src2;
    }

    @Override
    public boolean tryAdvance(Consumer<? super V> action) {
        boolean[] success = {false};

        src1.tryAdvance(t->
            src2.tryAdvance(u -> {
                action.accept(combiner.apply(t, u));
                success[0] = true;
            })
        );
        return success[0];

    }
}
