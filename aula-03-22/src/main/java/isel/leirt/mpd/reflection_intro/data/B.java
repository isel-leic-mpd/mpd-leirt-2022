package isel.leirt.mpd.reflection_intro.data;

import java.util.Objects;

public class B extends A {
    protected double dVal;
    private int iVal;

    public B(int ival, String name, double dVal) {
        super(name);
        this.iVal = ival;
        this.dVal = dVal;
    }

    public B() {
        this(0, null, 0.0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
       B other = (B) obj;
        return Double.compare(dVal, other.dVal) == 0 &&
                iVal == other.iVal;
    }

}
