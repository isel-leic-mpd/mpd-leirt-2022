package isel.leirt.mpd.functional2;

import java.util.function.Function;

public interface MyFunction<T,R> {
    R apply(T t);

    default <V> MyFunction<V,R>
    compose(MyFunction<? super V,? extends T> before) {

        return v ->  apply(before.apply(v));
    }

    default <V> MyFunction<T,V>
    andThen(MyFunction<R,V> after) {
        return t -> after.apply(apply(t));
    }

    static <T> MyFunction<T,T> identity()  {
       return t -> t;
    }

}
