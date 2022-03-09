package isel.leirt.mpd.mathapp;

import isel.leirt.mpd.mathapp.exceptions.MathAppException;
import isel.leirt.mpd.mathapp.expressions.Expr;
import isel.leirt.mpd.mathapp.expressions.Var;

import java.util.SortedMap;
import java.util.TreeMap;

public class MathLab {
    private SortedMap<String, Var> cells;

    public MathLab() {
        cells = new TreeMap<String, Var>();
    }

    public boolean add(String name, Expr value) {
        if (cells.containsKey(name)) return false;
        cells.put(name, new Var(name, value));
        return true;
    }

    public void setValue(String name, Expr value) {
        Var var = cells.get(name);
        if (var == null)
            throw new MathAppException("Undefined variable");
        var.setValue(value);
    }

    public double eval(String varName) {
        Var var = cells.get(varName);
        if (var == null)
            throw new MathAppException("Undefined variable");
        return var.eval();
    }
}
