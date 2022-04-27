package isel.leirt.mpd.lazy4.iterators;

import isel.leirt.mpd.lazy4.PipeIterable;


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class MapIterator<T,U>  implements Iterator<U> {
	private Iterator<T> itSrc;
	private Function<T,U> mapper;

	public MapIterator(PipeIterable<T> src, Function<T,U> mapper) {
		this.itSrc = src.iterator();
		this.mapper = mapper;
	}

	@Override
	public boolean hasNext() {
		return itSrc.hasNext();
	}

	@Override
	public U next() {
		if (!hasNext())  {
			throw new NoSuchElementException();
		}
		return mapper.apply(itSrc.next());
	}
}
