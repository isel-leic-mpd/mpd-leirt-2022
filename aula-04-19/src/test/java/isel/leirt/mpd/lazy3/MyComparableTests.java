package isel.leirt.mpd.lazy3;

import isel.leirt.mpd.functional.data.Address;
import isel.leirt.mpd.functional.data.Person;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

public class MyComparableTests {
    static List<Person> db = List.of(
        new Person("Carlos",
            LocalDate.of(1980, 3, 2),
            new Address("Coimbra", "3050")),
        new Person("Maria",
            LocalDate.of(2005, 10, 25),
            new Address("Coimbra", "2050")),
        new Person("Alice",
            LocalDate.of(1970, 5, 12),
            new Address("Lisboa", "1800")),
        new Person("Alberto",
            LocalDate.of(1080, 3, 2),
            new Address("Faro", "3150")),
        new Person("Rita",
            LocalDate.of(2000, 3, 2),
            new Address("Lisboa", "1900"))
    );

    private Person greater(Person p1, Person p2, Comparator<Person> comparator) {
        return comparator.compare(p1,p2) > 0 ? p1 : p2;
    }

    @Test
    public void comparePersonsByNameTest() {
        Person p1 = db.get(0);
        Person p2 = db.get(1);


    }

    @Test
    public void comparePersonsByAddressTest() {
        Person p1 = db.get(0);
        Person p2 = db.get(1);



    }
}


