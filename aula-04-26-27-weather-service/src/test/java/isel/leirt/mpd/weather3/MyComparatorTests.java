package isel.leirt.mpd.weather3;

import isel.leirt.mpd.functional.data.Address;
import isel.leirt.mpd.functional.data.Person;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class MyComparatorTests {
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
        Person carlos = db.get(0);
        Person maria = db.get(1);

        Comparator<Person> byName =
            (p1,p2) -> p1.getName().compareTo(p2.getName());

        Comparator<Person> byName2 =
            Comparator.comparing(Person::getName);

        Comparator<Person> byName3 =
            Comparator.comparing(p -> p.getName());

        Person res = greater(carlos,maria, byName);

        assertEquals(maria, res);
    }

    @Test
    public void comparePersonsByAddressTest() {
        Person carlos = db.get(0);
        Person alice = db.get(2);

        Comparator<Person> byAddr =
            Comparator
            .comparing(Person::getAddress, Comparator.comparing(Address::getCity)
            .thenComparing(Address::getZipCode));

        Person res = greater(carlos,alice, byAddr);

        assertEquals(carlos, res);
    }
}


