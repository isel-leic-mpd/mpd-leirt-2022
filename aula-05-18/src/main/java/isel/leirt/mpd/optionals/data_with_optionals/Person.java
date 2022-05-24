package isel.leirt.mpd.optionals.data_with_optionals;

import java.util.Optional;

public class Person {
    private Optional<Car> car;
    private final String name;
    private final String address;

    public Person(String name, String address, Car car)  {
        this.name = name;
        this.address = address;
        //this.car = car == null ? Optional.empty() : Optional.of(car);
        this.car = Optional.ofNullable(car);
    }

    public Person(String name, String address )  {
        this(name, address, null);
    }

    public  Optional<Car> getCar() {
        return car;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
}
