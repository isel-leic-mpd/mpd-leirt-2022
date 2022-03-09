package isel.leirt.mpd;

import isel.leirt.mpd.mathapp.MathLab;
import isel.leirt.mpd.mathapp.VarRef;
import isel.leirt.mpd.mathapp.expressions.Add;
import isel.leirt.mpd.mathapp.expressions.Expr;
import isel.leirt.mpd.mathapp.expressions.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MathLabTests {
    @Test
    public void simple_expression_with_var() {
        MathLab lab = new MathLab();


        lab.add("a", new Value(5));

        Expr add = new Add(new VarRef("a", lab), new Value(3));

        Assertions.assertEquals(8, add.eval());
    }
}
