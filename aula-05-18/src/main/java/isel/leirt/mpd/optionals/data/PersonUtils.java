package isel.leirt.mpd.optionals.data;

import isel.leirt.mpd.optionals.data.Car;
import isel.leirt.mpd.optionals.data.Insurance;
import isel.leirt.mpd.optionals.data.Person;

import java.time.LocalDate;
import java.util.Optional;

public class PersonUtils {

    public static LocalDate getPersonCarInsuranceDate(Person person) {

        Car personCar = person.getCar();
        if (personCar != null) {
            Insurance insurance = personCar.getInsurance();
            if (insurance != null) {
                return insurance.getExpirationDate();
            }
        }

        return null;
    }

    public static Optional<LocalDate> getPersonCarInsuranceDate1(Person person) {
      return null;

    }


}
