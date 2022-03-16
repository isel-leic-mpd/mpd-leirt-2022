package isel.leirt.mpd.aula03_15;

import isel.leirt.mpd.mathapp_aula_03_15.MathLab;
import isel.leirt.mpd.mathapp_aula_03_15.VarRef;
import isel.leirt.mpd.mathapp_aula_03_15.exceptions.CircularDependencyException;
import isel.leirt.mpd.mathapp_aula_03_15.expressions.Expr;
import isel.leirt.mpd.mathapp_aula_03_15.expressions.Mul;
import isel.leirt.mpd.mathapp_aula_03_15.expressions.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MathLabTests {



    @Test
    public void circular_dependency_test() {
        MathLab lab = new MathLab();

        assertThrows(
                CircularDependencyException.class,
                () -> {
                    lab.add("a");
                    Expr exp2 = new Mul(new Value(3), new VarRef("a", lab));
                    lab.add("b" , exp2 );
                    System.out.println(lab.eval("a"));
                    lab.setValue("a", new VarRef("b", lab));
                    System.out.println(lab.eval("a"));
                }
        );
    }

    @Test
    public void old_code_test() {
        MathLab lab = new MathLab();

        try {
            Expr exp1 = new VarRef("b", lab);
            Expr exp2 = new Mul(new Value(3), new VarRef("a", lab));
            lab.add("a" , exp1 );
            lab.add("b" , exp2 );

            System.out.println(lab.eval("a"));
        }
        catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
