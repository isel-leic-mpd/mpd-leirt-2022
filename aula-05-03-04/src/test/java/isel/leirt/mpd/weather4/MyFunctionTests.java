package isel.leirt.mpd.weather4;

import isel.leirt.mpd.functional2.MyFunction;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

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

        MyFunction<Integer, Integer> fa =
            inc.compose(triple);

        MyFunction<Integer, Integer> fb =
            inc.andThen(triple);

       assertEquals(10 , fa.apply(3));
        assertEquals(12 , fb.apply(3));
    }
}
