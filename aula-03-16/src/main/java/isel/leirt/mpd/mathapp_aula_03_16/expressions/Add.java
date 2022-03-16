package isel.leirt.mpd.mathapp_aula_03_16.expressions;

public class Add extends BinaryExpr {
   public Add(Expr left, Expr right) {
       super(left,right);
    }

    @Override
    protected String getOper() {
        return "+";
    }

    @Override
    public double eval() {
        return left.eval()+right.eval();
    }


}
