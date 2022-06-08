package isel.leirt.mpd.weatherasync3.requests;

import java.util.concurrent.CompletableFuture;

public class CounterRequest implements AsyncRequest {

	private final AsyncRequest origReq;
	private int count;

	public CounterRequest(AsyncRequest origReq) {
		this.origReq =origReq;
	}

	@Override
	public CompletableFuture<String> get(String path) {
		count++;
		return origReq.get(path);
	}

	public int getCount() {
		return count;
	}
}
