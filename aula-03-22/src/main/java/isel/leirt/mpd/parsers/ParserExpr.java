package isel.leirt.mpd.parsers;

import isel.leirt.mpd.exceptions.ParserException;

/*
 * the grammar parsed by this parser is:
 *
 * expression:: <term>  { ('+' | '-') <term> }*
 * term :: <factor>  { ('*' | '/') <factor> }*
 * factor :: <number> | '(' <expression> ')'
 *
 */
public class ParserExpr {
    Lex lex;

    Lex.Token token;

    public ParserExpr() {
        this.lex = new Lex();

    }

    private void nextToken()  {
        token =  lex.next();
    }

    private double factor() throws ParserException {
        if (token.isNumber())  {
            double number = token.getNumber();
            nextToken();
            return number;
        }
        else if (token.getType() == Lex.TokType.OPEN_BRACKET) {
            nextToken();
            double val = expression();
            if (token.getType() == Lex.TokType.CLOSE_BRACKET) {
                nextToken();
                return val;
            }
        }
        throw new ParserException("Number or parentheses expected!");
    }

    private double term( ) throws ParserException {
        double val = factor();
        while ((token.getType() == Lex.TokType.OP_MUL ||
                token.getType() == Lex.TokType.OP_DIV)) {
            Lex.TokType type =  token.getType();
            nextToken();
            double right = factor();

            if (type == Lex.TokType.OP_MUL) {
                val  *= right;
            } else {
                val /= right;
            }

        }
        return val;
    }

    public double expression() throws ParserException {
        double val = term();
        while ((token.getType() == Lex.TokType.OP_ADD ||
                token.getType() == Lex.TokType.OP_SUB)) {
            Lex.TokType type =  token.getType();
            nextToken();
            double right = term();

            if (type == Lex.TokType.OP_ADD) {
                val += right;
            } else {
                val -= right;
            }

        }
        return val;
    }

    public double parse(String line) throws ParserException {
        lex.start(line);
        nextToken();
        double result = expression();
        if (token.getType() != Lex.TokType.END)
            throw new ParserException("End of expression expected!");
        return result;
    }

}