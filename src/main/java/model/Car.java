package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.Constants;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a car entity in the dealership management system.
 * Uses Lombok.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String licensePlate;
    private String brand;
    private String model;
    private String color;

    /**
     * Returns a string representation of the car in Spanish.
     * @return formatted string containing all car details
     */
    @Override
    public String toString() {
        return String.format(Constants.FORMAT_TO_STRING,
                id, licensePlate, brand, model, color);
    }

    /**
     * Formats the car data as a CSV record using semicolons as separators.
     * @return CSV formatted string
     */
    public String toCsvFormat() {
        return String.format(Constants.FORMAT_CSV,
                id, licensePlate, brand, model, color);
    }
}