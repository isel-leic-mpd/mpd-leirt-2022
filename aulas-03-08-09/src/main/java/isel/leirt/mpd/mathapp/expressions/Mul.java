package isel.leirt.mpd.mathapp.expressions;

public class Mul extends BinaryExpr {

    public Mul(Expr left, Expr right) {
      super(left, right);
    }

    @Override
    protected String getOper() {
        return "*";
    }

    @Override
    public double eval() {
        return left.eval()*right.eval();
    }


}
