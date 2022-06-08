package isel.leirt.mpd.weatherasync2;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CompletableFuturesTests {

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch(Exception e) {

        }
    }

    private CompletableFuture<Integer>
            intProducer(int val) {

        Supplier<Integer> supInt = () -> {
            System.out.println(
                "produce value in thread: " +
                    Thread.currentThread().getName());
            sleep(3000);
            return val;
        };

        return CompletableFuture.supplyAsync(supInt);
    }

    @Test
    public void produceIntegerTest() {
        int expected = 3;

        long startTime = System.currentTimeMillis();

        var future =
            intProducer(expected);

        int res = future.join();
        //res *= 2;

        Assertions.assertEquals(expected, res);
        System.out.println("Done");
        System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms!");

    }

    @Test
    public void produceIntegerAndDoubleItTest() {
        int expected = 6;

        long startTime = System.currentTimeMillis();

        var future =
            intProducer(3)
            .thenApply(i -> i * 2);


        Assertions.assertEquals(expected, future.join());
        System.out.println("Done");
        System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms!");

    }

    @Test
    public void produceIntegerAndAfterProduceAnotherIntegerTest() {
        int expected = 6;

        long startTime = System.currentTimeMillis();

        var future =
            intProducer(3)
            .thenCompose(i -> intProducer(i*2));


        var finalValue = future.join();

        System.out.println("Done");
        System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms!");
        Assertions.assertEquals(expected, finalValue);
    }

    @Test
    public void produceTwoIntegersInParallelTest() {
        int expected = 18;

        long startTime = System.currentTimeMillis();

        var future1 = intProducer(3);
        var future2 = intProducer(6);

        var future = future1.thenCombine(future2,
            (value1, value2) -> value1*value2);

        var finalValue = future.join();

        System.out.println("Done");
        System.out.println("Done in " + (System.currentTimeMillis() - startTime) + "ms!");
        Assertions.assertEquals(expected, finalValue);
    }
}
