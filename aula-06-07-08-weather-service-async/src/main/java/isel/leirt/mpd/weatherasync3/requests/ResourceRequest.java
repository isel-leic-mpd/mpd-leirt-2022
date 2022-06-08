package isel.leirt.mpd.weatherasync3.requests;

import java.io.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class ResourceRequest implements AsyncRequest {

	@Override
	public CompletableFuture<String> get(String path) {
		try {
			path = path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf('&'))
				.replace('&', '-')
				.replace('=', '-')
				.replace('?', '-')
				.replace(',', '-') + ".txt";

			BufferedReader reader =
				new BufferedReader(
					new InputStreamReader(
						ClassLoader.getSystemResource(path).openStream()
					)
				);


			return CompletableFuture
				   .completedFuture(reader.lines().collect(Collectors.joining()));

		}
		catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
