package model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static utils.Constants.*;

@Getter
public class Passenger {

    @Setter
    private Integer id;
    private String name;
    private int age;
    private double weight;

    public Passenger() {
    }

    public Passenger(String name, int age, double weight) {
        setName(name);
        setAge(age);
        setWeight(weight);
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException(ERROR_NULL_NAME);
        }
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException(ERROR_EMPTY_NAME);
        }
        this.name = name.trim();
    }

    public void setAge(int age) {
        if (age < MIN_AGE) {
            throw new IllegalArgumentException(ERROR_INVALID_AGE);
        }
        this.age = age;
    }

    public void setWeight(double weight) {
        if (weight <= MIN_WEIGHT) {
            throw new IllegalArgumentException(ERROR_INVALID_WEIGHT);
        }
        this.weight = weight;
    }

    @Override
    public String toString() {
        return String.format("Pasajero{id=%d, nombre='%s', edad=%d, peso=%.2f}",
                id, name, age, weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger passenger)) return false;
        return Objects.equals(id, passenger.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}