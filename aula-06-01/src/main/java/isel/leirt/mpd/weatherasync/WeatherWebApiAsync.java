package isel.leirt.mpd.weatherasync;

import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static isel.leirt.mpd.weatherasync.NioUtils.*;

public class WeatherWebApiAsync {


    private static final String API_KEY = getApiKeyFromResources();

    private static final String HEADERS =
        """
        
        Host: api.openweathermap.org
        Accept: */*
        User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64)  
           
        """;


    private static final String WEATHER_AT_TEMPLATE =
        "/data/2.5/weather?lat=%f&lon=%f&units=metric&appid=%s HTTP/1.2";

    /**
     * Retrieve API-KEY from resources
     *
     * @return
     */
    private static String getApiKeyFromResources() {
        try {
            URL keyFile =
                ClassLoader.getSystemResource("openweatherapi-app-key.txt");
            try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(keyFile.openStream()))) {
                return reader.readLine();
            }

        } catch (IOException e) {
            throw new IllegalStateException(
                "YOU MUST GET a KEY from  openweatherapi.com and place it in src/main/resources/openweatherapi-app-key2.txt");
        }
    }


    private void showMsg(String msg) {

        System.out.printf("[%s] : %s\n",
            Thread.currentThread().getName(), msg);
    }

    private void closeChannel(final AsynchronousSocketChannel channel) {
        try {
            channel.close();
        }
        catch(IOException e) {
            System.out.println("Error " + e.getMessage() + " closing socket");
        }
    }

    private void serverConnection(
        BiConsumer<AsynchronousSocketChannel,Throwable> onCompletion) {

        AsynchronousSocketChannel channel = null;
        try {
            channel = AsynchronousSocketChannel.open();
            AsynchronousSocketChannel local = channel;
            InetAddress weatherIp =
                Inet4Address.getByName("api.openweathermap.org");

            SocketAddress sockAddr = new InetSocketAddress(weatherIp, 80);

            connect(channel, sockAddr, err -> {
                if (err == null) {
                    onCompletion.accept(local, null);
                }
                else {
                    closeChannel(local);
                    onCompletion.accept(null, err);
                }
            });
        }
        catch(IOException e) {
            if (channel != null) closeChannel(channel);
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Get WeatherInfo's from a local coordinates given a date interval
     * @param lat
     * @param lon
     * @return
     */
    public void weatherAt(double lat, double lon,
                          BiConsumer<String, Throwable> onCompleted) {

        String request =
            "GET "  + String.format(WEATHER_AT_TEMPLATE, lat, lon, API_KEY) + HEADERS;

        showMsg("before connection");
        serverConnection((channel, exc) -> {
            showMsg("on connection completed");
            if (exc != null) {
                onCompleted.accept(null, exc);
                closeChannel(channel);
            }
            else {
                write(channel, request, (nBytes, exc2) -> {
                    showMsg("on write completed");
                    if (exc2 != null) {
                        onCompleted.accept(null, exc2);
                        closeChannel(channel);
                    }
                    else {
                        read(channel, (response, exc3) -> {
                            showMsg("on read completed");
                            if (exc3 != null) {
                                onCompleted.accept(null, exc3);
                            }
                            else {
                                onCompleted.accept(response, null);
                            }
                            closeChannel(channel);
                        });
                    }
                });
            }
        });

    }


    private CompletableFuture<AsynchronousSocketChannel> serverConnectionAsync() {
        CompletableFuture<AsynchronousSocketChannel> result =
            new CompletableFuture<>();
        serverConnection((channel, err ) -> {
            if (err == null)
                result.complete(channel);
            else
                result.completeExceptionally(err);
        });
        return result;
    }

    /**
     * Get WeatherInfo's from a local coordinates given a date interval
     * @param lat
     * @param lon
     * @return A completable future that represents the result production
     */
    public CompletableFuture<String> weatherAtAsync(double lat, double lon) {
        String request =
            "GET " + String.format(WEATHER_AT_TEMPLATE, lat, lon, API_KEY) + HEADERS;

        //TODO
        return null;

    }

}
