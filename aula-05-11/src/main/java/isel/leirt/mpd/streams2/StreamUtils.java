package isel.leirt.mpd.streams2;


import java.util.stream.LongStream;


public class StreamUtils {

    private static boolean isPrime(long n) {
        if (n == 2) return true;
        if (n < 2 || n % 2 == 0) return false;
        for (int i = 3; i <= Math.sqrt(n); i += 2)
            if (n % i == 0) return false;
        return true;
    }

    public static long countPrimesInRange(long start, long end) {
        return LongStream.rangeClosed(start, end)
            .filter(n -> isPrime(n))
            .parallel()
            .count();
    }


}
