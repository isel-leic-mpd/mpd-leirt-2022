package isel.leirt.mpd.reflection_intro.data;

public class C extends B {
    public int iVal;

    public C(int iVal, String name, double dVal) {
        super(iVal*2, name, dVal);
        this.iVal = iVal;
    }

    public C() {
        this(0, "", 0.0);

    }
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        C other = (C) obj;
        return iVal == other.iVal;
    }
}
