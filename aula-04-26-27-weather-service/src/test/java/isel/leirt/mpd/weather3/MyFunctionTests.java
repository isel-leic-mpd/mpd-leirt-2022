package isel.leirt.mpd.weather3;

import isel.leirt.mpd.functional.MyFunction;
import org.junit.jupiter.api.Test;

public class MyFunctionTests {

    private static int minc(int i) {
        return i + 1;
    }

    public static int mtriple(int i) {
        return 3 * i;
    }

    @Test
    public void andThenAndComposeTest() {
        MyFunction<Integer, Integer> inc = MyFunctionTests::minc;
        MyFunction<Integer, Integer> triple = MyFunctionTests::mtriple;

    }
}
