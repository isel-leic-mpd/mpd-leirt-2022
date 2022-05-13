package isel.leirt.mpd.sequences;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public interface Sequence<T> {
    boolean tryAdvance(Consumer<T> action);

    // factory operations
    static <T> Sequence<T> empty( ) {
        return action -> false;
    }
    static <T> Sequence<T>
    iterate(T seed, UnaryOperator<T> next) {
        Object[] curr = {seed};
        return action -> {
            T t = (T) curr[0];
            curr[0] = next.apply(t);
            action.accept(t);
            return true;

        };
    }

    static <T> Sequence<T> from( List<T> src) {
        Iterator<T> itSrc = src.iterator();
        return action -> {
            if (!itSrc.hasNext()) return false;
            action.accept(itSrc.next());
            return true;
        };
    }

    // intermediate operations

    default <U> Sequence<U>  map(Function<T,U> mapper) {
        return action ->
            tryAdvance(t ->
                action.accept(mapper.apply(t))
            );
    }
    // terminal operations

    default long count() {
        long[] total = {0};

        while(tryAdvance(__ ->
           total[0]++ ));
        return total[0];
    }
}
