package isel.leirt.mpd.optionals.data_with_optionals;

import java.util.Optional;

public class Car {
    private Optional<Insurance> insurance;
    private String brand;
    private String licenseNumber;


    public Car(String licenseNumber,  String brand, Insurance insurance) {
        this.licenseNumber = licenseNumber;
        this.brand = brand;
        this.insurance =Optional.ofNullable(insurance);
    }

    public Car(String licenseNumber,  String brand) {
       this(licenseNumber,  brand, null);
    }

    public  Optional<Insurance> getInsurance() { return insurance; }

    public String getLicense() { return licenseNumber; }

    public String getBrand() { return brand; }

}
