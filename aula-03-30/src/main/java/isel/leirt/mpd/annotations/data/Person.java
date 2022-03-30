package isel.leirt.mpd.annotations.data;

import isel.leirt.mpd.annotations.Attribute;

import java.time.LocalDate;


public class Person {
    @Attribute(dbName="Person-Name")
    private final String name;

    @Attribute(dbName="Db-Age")
    private final LocalDate birthDate;

    public Person(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }
}
