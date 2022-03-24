package isel.leirt.mpd.reflection_intro.data;

import java.time.LocalDate;

public class Person {
    public final String name;
    public final int id;
    public final String birthDate;

    public Person(String name, int id, String birthDate) {
        this.name = name;
        this.id = id;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return  "name = " + name +
                ", id = " + id +
                ", birthDate = " + birthDate;
    }


}
