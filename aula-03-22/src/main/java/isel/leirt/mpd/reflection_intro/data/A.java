package isel.leirt.mpd.reflection_intro.data;

import java.util.Objects;

public class A {
    public String sVal;

    public A(String name) {
        this.sVal = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) return false;
        A other = (A) obj;
        return Objects.equals(sVal, other.sVal);
    }
}
