package isel.leirt.mpd.weatherasync3.requests;


import org.asynchttpclient.AsyncHttpClient;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;

import static org.asynchttpclient.Dsl.asyncHttpClient;


public class AsyncHttpRequest implements AsyncRequest {
	private static void ahcClose(AsyncHttpClient client) {
		try {
			client.close();
		}
		catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}


	@Override
	public CompletableFuture<String>
	get(String path) {

		AsyncHttpClient client = asyncHttpClient();

		return  client.prepareGet(path)
			.execute()
			.toCompletableFuture()
			.thenApply( response -> response.getResponseBody() )
			.whenComplete((s,e) -> ahcClose(client));
	}
}
