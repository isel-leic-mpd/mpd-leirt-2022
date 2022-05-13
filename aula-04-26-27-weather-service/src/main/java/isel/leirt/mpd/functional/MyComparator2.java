package isel.leirt.mpd.functional;

import java.util.function.Function;

@FunctionalInterface
public interface MyComparator2<T> {

    int compare(T o1, T o2);

    /**
     * Returns a comparator that imposes the reverse ordering of this comparator.
     */
    default MyComparator2<T> reversed() {

        return (t1, t2) -> compare(t2, t1);
    }

    /**
     * Accepts a function that extracts a {@link Comparable
     * Comparable} sort key from a type {@code T}, and returns a {@code
     * MyComparator<T>} that compares by that sort key.
     * @throws NullPointerException if the argument is null
     * @since 1.8
     */
    public static <T, U extends Comparable<? super U>>
    MyComparator2<T> comparing(Function<T, U> keyExtractor) {

        return (t1, t2) ->
            keyExtractor.apply(t1).compareTo(keyExtractor.apply(t2));
    }

    /**
     * Accepts a function that extracts a sort key from a type {@code T}, and
     * returns a {@code MyComparator<T>} that compares by that sort key using
     * the specified {@link MyComparator2}.
     */
    public static <T, U> MyComparator2<T> comparing(
        Function<? super T, ? extends U> keyExtractor,
        MyComparator2<? super U> keyMyComparator)
    {
        // TO IMPLEMENT
        return null;
    }

    default <U extends Comparable<? super U>> MyComparator2<T>
    thenComparing(Function<? super T, ? extends U> keyExtractor)
    {
        return null;
    }

    /**
     * Returns a lexicographic-order comparator with another comparator.
     * If this {@code MyComparator} considers two elements equal, i.e.
     * {@code compare(a, b) == 0}, {@code other} is used to determine the order.
     * For example, to sort a collection of {@code String} based on the length
     * and then case-insensitive natural ordering, the comparator can be
     * composed using following code,
     *     MyComparator<String> cmp = MyComparator.comparingInt(String::length)
     *             .thenComparing(String.CASE_INSENSITIVE_ORDER);
     */
    default MyComparator2<T>
    thenComparing(MyComparator2<? super T> other) {
        // TO IMPLEMENT
        return null;
    }

    /**
     * Returns a lexicographic-order comparator with a function that
     * extracts a key to be compared with the given {@code MyComparator}.
     */
    default <U> MyComparator2<T> thenComparing(
        Function<? super T, ? extends U> keyExtractor,
        MyComparator2<? super U> keyMyComparator)
    {
        // TO IMPLEMENT
        return null;
    }


    /**
     * Returns a null-friendly comparator that considers {@code null} to be
     * less than non-null. When both are {@code null}, they are considered
     * equal. If both are non-null, the specified {@code MyComparator} is used
     * to determine the order. If the specified comparator is {@code null},
     * then the returned comparator considers all non-null values to be equal.
     */
    public static <T> MyComparator2<T> nullsFirst(MyComparator2<? super T>
                                                     comparator) {
        //TO IMPLEMENT
        return null;
    }

}
