package isel.leirt.mpd.weather4.requests;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

public class MockRequest implements Request{

	@Override
	public Reader get(String path) {

		path = path.substring(path.lastIndexOf('/')+1, path.lastIndexOf('&'))
			.replace('&', '-')
			.replace( '?','-')
			.replace( ',','-')+ ".txt";
		try {
			return new InputStreamReader(ClassLoader.getSystemResource(path).openStream());
		}
		catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
