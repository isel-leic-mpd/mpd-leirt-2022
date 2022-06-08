package isel.leirt.mpd.weatherasync2;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class NioUtils {
    private static Charset charSet = Charset.defaultCharset();
    private static CharsetDecoder decoder = charSet.newDecoder();
    private static CharsetEncoder encoder = charSet.newEncoder();

    private static CompletionHandler<Integer, BiConsumer<Integer,Throwable>>
        rwHandler = new CompletionHandler<Integer,BiConsumer<Integer,Throwable>>() {

        @Override
        public void completed(Integer result, BiConsumer<Integer,Throwable> attachment) {
            attachment.accept(result, null);
        }

        @Override
        public void failed(Throwable exc, BiConsumer<Integer,Throwable> attachment) {

            attachment.accept(null, exc);
        }
    };

    private static CompletionHandler<Void, Consumer<Throwable>> conHandler =
        new CompletionHandler<Void,Consumer<Throwable>>() {

            @Override
            public void completed(Void result, Consumer<Throwable> attachment) {
                attachment.accept(null);
            }

            @Override
            public void failed(Throwable exc, Consumer<Throwable> attachment) {
                attachment.accept(exc);
            }
        };

    public static  void write(AsynchronousSocketChannel channel, String cmd,
                       BiConsumer<Integer, Throwable> onCompleted) {
        CharBuffer buffer = CharBuffer.allocate(4096);
        try {
            buffer.put(cmd);
            buffer.flip();
            ByteBuffer bbuf = encoder.encode(buffer);
            channel.write(bbuf, onCompleted, rwHandler);

        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    public static  void read(AsynchronousSocketChannel channel,
                      BiConsumer<String, Throwable> onCompleted)
    {
        ByteBuffer buffer = ByteBuffer.allocate(4096);

        channel.read(buffer,
            (i, exc) -> {
                try {
                    String res;
                    buffer.flip();
                    if (i <= 0) res = null;
                    else res = decoder.decode(buffer).toString();
                    onCompleted.accept(res, null);
                }
                catch(CharacterCodingException e) {
                    onCompleted.accept(null, e);
                }
            },
            rwHandler);

    }

    public static void connect(AsynchronousSocketChannel channel,
                         SocketAddress sockAddr,
                         Consumer<Throwable> onCompleted)
    {


        channel.connect(sockAddr, onCompleted, conHandler);
    }

    /*
     *  future async operations
     */
    public static Future<Void>
    connect(AsynchronousSocketChannel channel,  SocketAddress sockAddr)
    {

        CompletableFuture<Void> result = new CompletableFuture<>();
        connect(channel, sockAddr, err -> {
            if (err == null)
                result.complete(null);
            else
                result.completeExceptionally(err);
        });
        return result;
    }

    public static Future<Integer> write(AsynchronousSocketChannel channel, String cmd) {
        CharBuffer buffer = CharBuffer.allocate(4096);
        try {
            buffer.put(cmd);
            buffer.flip();
            ByteBuffer bbuf = encoder.encode(buffer);
            return channel.write(bbuf);
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static  Future<String> read(AsynchronousSocketChannel channel)
    {
        CompletableFuture<String> result = new CompletableFuture<>();
        read(channel, (resp, err) -> {
            if (err == null)
                result.complete(resp);
            else
                result.completeExceptionally(err);
        });
        return result;
    }

    /*
     * completable future async operations
     */



    public static  CompletableFuture<String>
                    readCF(AsynchronousSocketChannel channel) {
        CompletableFuture<String> result = new CompletableFuture<>();
        read(channel, (resp, err) -> {
            if (err == null)
                result.complete(resp);
            else
                result.completeExceptionally(err);
        });
        return result;
    }

    public static  CompletableFuture<Integer> writeCF(
        AsynchronousSocketChannel channel,
        String cmd) {
        CompletableFuture<Integer> result = new CompletableFuture<>();

        write(channel,cmd,  (resp, err) -> {
            if (err == null)
                result.complete(resp);
            else
                result.completeExceptionally(err);
        });
        return result;
    }

}
