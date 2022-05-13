package isel.leirt.mpd.lazy4;


import isel.leirt.mpd.lazy4.iterators.*;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface PipeIterable<T> {

	Iterator<T> iterator();

	// factories or sequence constructors
	static <T> PipeIterable<T> of(Iterable<T> seq) {
		return () -> seq.iterator();
	}

	static PipeIterable<Integer> range(int li, int ls) {

		return () -> new RangeIterator(li,ls);
	}

	// intermediate operations
	default PipeIterable<T> filter(Predicate<T> pred ) {
		return () -> new FilterIterator<>(this, pred);
	}

	default PipeIterable<T> takeWhile(Predicate<T> pred ) {
		return () -> new TakeWhileIterator<>(this, pred);
	}

	default <U> PipeIterable<U> map(Function<T,U> mapper) {
		return () -> new MapIterator<>(this, mapper);
	}

	default <U> PipeIterable<U> flatMap(Function<T, PipeIterable<U>> mapper) {
		return () -> new FlatMapIterator<>(this, mapper);
	}




	// terminal operations


	default void forEach(Consumer<T> action) {
		Iterator<T> it = iterator();
		while(it.hasNext())
			action.accept(it.next());
	}

	default long count() {
		long c = 0;
		Iterator<T> it = iterator();
		while(it.hasNext()) {
			it.next();
			c++;
		}
		return c;
	}

	default Iterable<T> toIterable() {
		return () -> iterator();
	}

	default Optional<T> first() {
		Iterator<T> it = iterator();

		if (it.hasNext()) return Optional.of(it.next());
		else return Optional.empty();
	}
}
