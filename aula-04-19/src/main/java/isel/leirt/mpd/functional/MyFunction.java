package isel.leirt.mpd.functional;

public interface MyFunction<T,R> {
    R apply(T t);

    default <V> MyFunction<V,R>
    compose(MyFunction<? super V,? extends T> before) {
        return null;
    }

    default <V> MyFunction<T,V>
    andThen(MyFunction<R,V> after) {
        return null;
    }

}
