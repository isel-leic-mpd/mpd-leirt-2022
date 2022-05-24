package isel.leirt.mpd;

import isel.leirt.mpd.optionals.data_with_optionals.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptionalTests {

    @Test
    public void person_get_car_brand_on_person_without_car() {
        Person person =
            new Person("Armando", "Rua A");

        var carBrand =
            person.getCar()
            .map(Car::getBrand)
            .orElse("NoCar");

        assertEquals("NoCar", carBrand);
    }

    @Test
    public void person_get_car_brand_on_person_with_car() {
        Person person =
            new Person("Armando", "Rua A",
                new Car("XX-YY-ZZ", "Ford"));

        var carBrand =
            person.getCar()
                  .map(Car::getBrand)
                  .orElse("NoCar");

        assertEquals("Ford", carBrand);
    }

    @Test
    public void person_get_insurance_expiration_date() {
        LocalDate expirationDate = LocalDate.of(2022, 8, 15);
        LocalDate onEmptyDate = LocalDate.of(1,1,1);
        Person person =
            new Person("Armando", "Rua A",
                new Car("XX-YY-ZZ", "Ford",
                        new Insurance("Fidelidade", expirationDate)));

        var seenDate  =
            person.getCar()
                  .flatMap(Car::getInsurance)
                  .map(Insurance::getExpirationDate)
                  .orElse(onEmptyDate);



        assertEquals(expirationDate, seenDate);
    }
}
