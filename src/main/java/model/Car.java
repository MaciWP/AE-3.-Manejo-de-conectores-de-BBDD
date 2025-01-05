package model;

import lombok.Getter;
import java.util.Objects;
import static utils.Constants.*;

@Getter
public class Car {

    private Integer id;
    private String licensePlate;
    private String brand;
    private String model;
    private String color;

    public Car() {
    }

    public Car(String licensePlate, String brand, String model, String color) {
        setLicensePlate(licensePlate);
        setBrand(brand);
        setModel(model);
        setColor(color);
    }

    public void setId(Integer id) {
        this.id = Objects.requireNonNull(id, ERROR_NULL_ID);
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = Objects.requireNonNull(licensePlate, ERROR_NULL_LICENSE)
                .toUpperCase().trim();
    }

    public void setBrand(String brand) {
        this.brand = Objects.requireNonNull(brand, ERROR_NULL_BRAND).trim();
    }

    public void setModel(String model) {
        this.model = Objects.requireNonNull(model, ERROR_NULL_MODEL).trim();
    }

    public void setColor(String color) {
        this.color = Objects.requireNonNull(color, ERROR_NULL_COLOR).trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car car)) return false;
        return Objects.equals(licensePlate, car.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate);
    }

    @Override
    public String toString() {
        return String.format("Coche{id=%d, matrícula='%s', marca='%s', modelo='%s', color='%s'}",
                id, licensePlate, brand, model, color);
    }
}