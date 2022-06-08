package isel.leirt.mpd.weatherasync3.requests;

import java.util.concurrent.CompletableFuture;

public interface AsyncRequest {
	CompletableFuture<String> get(String path);
}
