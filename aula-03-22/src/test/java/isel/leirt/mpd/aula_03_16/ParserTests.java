package isel.leirt.mpd.aula_03_16;

import isel.leirt.mpd.exceptions.ParserException;
import isel.leirt.mpd.parsers.Lex;
import isel.leirt.mpd.parsers.ParserExpr;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTests {

    @Test
    public void lex_simple_test() {
        String line = "  (  23 + 45 )";

        Lex l = new Lex();

        l.start(line);
        Lex.Token t = l.next();

        assertEquals( Lex.TokType.OPEN_BRACKET, t.type);
        t = l.next();
        assertEquals(t.type, Lex.TokType.NUMBER);
        assertEquals(t.getNumber(), 23);
        t = l.next();
        assertEquals(t.type, Lex.TokType.OP_ADD);
        t = l.next();
        assertEquals(t.type, Lex.TokType.NUMBER);
        assertEquals(t.getNumber(), 45);
        t = l.next();
        assertEquals(t.type, Lex.TokType.CLOSE_BRACKET);
        t = l.next();
        assertEquals(t.type, Lex.TokType.END);
    }

    @Test
    public void parser_simple_test() {
        String line = "( 23 + 45 ";

        try {
            ParserExpr parser = new ParserExpr();

            double result = parser.parse(line);
            assertEquals(68, result );
        }
        catch(ParserException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void parser_more_complicated_test() {
        String line = "( ((2 + 23) + (55 - 5)) * -1 )";

        try {
            ParserExpr parser = new ParserExpr();

            double result = parser.parse(line);
            assertEquals(-75, result);
        }
        catch(ParserException e) {
            fail(e.getMessage());
        }
    }
}
