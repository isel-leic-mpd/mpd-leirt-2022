package isel.leirt.mpd;

import isel.leirt.mpd.mathapp.MathLab;
import isel.leirt.mpd.mathapp.VarRef;
import isel.leirt.mpd.mathapp.expressions.Add;
import isel.leirt.mpd.mathapp.expressions.Expr;
import isel.leirt.mpd.mathapp.expressions.Mul;
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

    @Test
    public void old_code_test() {
        MathLab lab = new MathLab();

        try {
            lab.add("a", new Value(0));
            lab.add("b", new Value(0));
            Expr exp1 = new VarRef("b", lab);
            Expr exp2 = new Mul(new Value(3), new VarRef("a", lab));
            lab.setValue("a" , exp1 );
            lab.setValue("b" , exp2 );

            System.out.println(lab.eval("a"));
        }
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
