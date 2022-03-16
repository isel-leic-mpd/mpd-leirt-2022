package isel.leirt.mpd.mathapp_aula_03_16;

import isel.leirt.mpd.mathapp_aula_03_16.exceptions.MathAppException;
import isel.leirt.mpd.mathapp_aula_03_16.expressions.Expr;
import isel.leirt.mpd.mathapp_aula_03_16.expressions.Value;

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

    /**
     * this add overload creates a new var with value 0
     * @param name
     * @return
     */
    public boolean add(String name) {
        if (cells.containsKey(name)) return false;
        cells.put(name, new Var(name, new Value(0)));
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

    /**
     * this new internal method provides a var given its name
     * @param varName
     * @return
     */
    protected Var getVar(String varName) {
        Var var = cells.get(varName);
        if (var == null)
            throw new MathAppException("Undefined variable");
        return var;
    }

    public void showVars() {
        for (Var v : cells.values()) {
            v.show();
        }
    }
}
