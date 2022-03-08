package isel.leirt.mpd;

import isel.leirt.mpd.expressions.Expr;
import isel.leirt.mpd.expressions.Mul;
import isel.leirt.mpd.expressions.Value;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExprTests {

    @Test
    public void simple_multiplication_test() {
        Expr exp = new Mul(new Value(2.0), new Value(3.0));

        double result = exp.eval();
        assertEquals(result, 6.0);
    }
}
