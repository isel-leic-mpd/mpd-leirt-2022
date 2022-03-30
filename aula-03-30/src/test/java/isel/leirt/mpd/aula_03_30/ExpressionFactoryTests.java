package isel.leirt.mpd.aula_03_30;

import isel.leirt.mpd.extensible_apps.ExpressionFactory;
import isel.leirt.mpd.mathapp_aula_03_16.expressions.Expr;
import isel.leirt.mpd.mathapp_aula_03_16.expressions.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

public class ExpressionFactoryTests {

    @Test
    public void use_absolute_oper_with_expression_factory() {
        String expected = "abs(-2.0)=2.0";

        Value v = new Value(-2.0);

        Expr exp =
            ExpressionFactory.createFrom("Abs", v);

        StringWriter sw = new StringWriter();
        exp.writeOn(sw);
        sw.write("=");
        sw.write(Double.toString(exp.eval()));

        System.out.println(sw.toString());
        Assertions.assertEquals(expected, sw.toString());


    }
}
