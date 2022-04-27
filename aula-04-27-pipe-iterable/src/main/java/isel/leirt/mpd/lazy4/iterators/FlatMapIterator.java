package isel.leirt.mpd.lazy4.iterators;

import isel.leirt.mpd.lazy4.PipeIterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

public class FlatMapIterator<T,U> implements Iterator<U> {
	private Iterator<T> itSrc;
	private Function<T,PipeIterable<U>>  mapper;
	private Iterator<U> itCurr;
	private Optional<U> val;

	public FlatMapIterator(PipeIterable<T> src, Function<T, PipeIterable<U>> mapper) {
		this.itSrc = src.iterator();
		this.mapper = mapper;
		val = Optional.empty();
	}

	@Override
	public boolean hasNext() {
		if (val.isPresent()) return true;
		while(itCurr == null || !itCurr.hasNext()) {
			if (!itSrc.hasNext()) return false;
			itCurr = mapper.apply(itSrc.next()).iterator();
		}
		val = Optional.of(itCurr.next());
		return true;
	}

	@Override
	public U next() {
		if (!hasNext())
			throw new NoSuchElementException();
		U v = val.get();
		val = Optional.empty();
		return v;
	}
}
