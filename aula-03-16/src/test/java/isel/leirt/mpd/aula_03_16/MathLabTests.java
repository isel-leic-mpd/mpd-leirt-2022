package isel.leirt.mpd.aula_03_16;

import isel.leirt.mpd.mathapp_aula_03_16.MathLab;
import isel.leirt.mpd.mathapp_aula_03_16.VarRef;
import isel.leirt.mpd.mathapp_aula_03_16.exceptions.CircularDependencyException;
import isel.leirt.mpd.mathapp_aula_03_16.expressions.Expr;
import isel.leirt.mpd.mathapp_aula_03_16.expressions.Mul;
import isel.leirt.mpd.mathapp_aula_03_16.expressions.Value;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MathLabTests {
    /**
     * This is not really a test since the code
     * simply write the results on the standard output,
     * and so it is not easily testable.
     * The expected output  is:
     * Vars:
     * v1 = 3.0
     * v2 = 3.0
     * v3 = 15.0
     * Changed Vars:
     * v2 = 4.0, due to v1 change!
     * v3 = 20.0, due to v2 change!
     */
    @Test
    public void changing_variables_chain_test() {
        MathLab lab = new MathLab();

        lab.add("v1", new Value(3));
        lab.add("v2", new VarRef("v1", lab));
        lab.add("v3", new Mul(new Value(5), new VarRef("v2", lab)));

        System.out.println("Vars:");
        lab.showVars();
        assertEquals(lab.eval("v2"), 3);

        System.out.println("Changed Vars:");
        lab.setValue("v1", new Value(4));
    }

    /*
    @Test
    public void circular_dependency_test() {
        MathLab lab = new MathLab();

        // this assert (assertThrows) succeeds if the tested code throws
        // a CircularDependencyException
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
    public void old_code_circular_dependency_test_dependency_test() {
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

     */
}
