package service;

import model.Car;
import utils.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing car operations including persistence and validation.
 * Handles CRUD operations, file persistence and CSV export functionality.
 */
public class CarService {
    private final List<Car> cars;
    private int lastUsedId = 0;

    /**
     * Initializes the car service and loads existing cars from file.
     * Also initializes the last used ID.
     */
    public CarService() {
        this.cars = new ArrayList<>();
        loadFromFile();
        initializeLastUsedId();
    }

    /**
     * Initializes lastUsedId based on existing cars.
     */
    private void initializeLastUsedId() {
        lastUsedId = cars.stream()
                .mapToInt(Car::getId)
                .max()
                .orElse(0);
    }

    /**
     * Adds a new car to the system with auto-generated ID.
     *
     * @param car The car to be added (ID will be auto-generated)
     * @throws IllegalArgumentException if the car is null or has duplicate license plate
     */
    public void add(Car car) {
        if (car == null) {
            throw new IllegalArgumentException(Constants.ERROR_NULL_CAR);
        }
        car.setId(++lastUsedId);
        validateNewCar(car);
        cars.add(car);
    }

    /**
     * Deletes a car by its ID.
     *
     * @param id The ID of the car to delete
     * @return true if car was deleted, false if not found
     * @throws IllegalArgumentException if the ID is null
     */
    public boolean deleteById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException(Constants.ERROR_NULL_ID);
        }
        return cars.removeIf(car -> car.getId().equals(id));
    }

    /**
     * Finds a car by its ID.
     *
     * @param id The ID to search for
     * @return Optional containing the car if found
     * @throws IllegalArgumentException if the ID is null
     */
    public Optional<Car> findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException(Constants.ERROR_NULL_ID);
        }
        return cars.stream()
                .filter(car -> car.getId().equals(id))
                .findFirst();
    }

    /**
     * Returns a list of all cars in the system.
     *
     * @return List of all cars (copy of internal list)
     */
    public List<Car> findAll() {
        return new ArrayList<>(cars);
    }

    /**
     * Exports all cars to a CSV file.
     *
     * @throws IOException if there's an error writing to the file
     */
    public void exportToCsv() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(Constants.CSV_FILE))) {
            writer.println(Constants.CSV_HEADER);
            for (Car car : cars) {
                writer.println(car.toCsvFormat());
            }
        } catch (IOException e) {
            System.err.println(Constants.ERROR_EXPORT_CSV + e.getMessage());
            throw e;
        }
    }

    /**
     * Saves all cars to a binary file.
     *
     * @throws IOException if there's an error writing to the file
     */
    public void saveToFile() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(Constants.DATA_FILE))) {
            oos.writeObject(cars);
        } catch (IOException e) {
            System.err.println(Constants.ERROR_SAVE_DATA + e.getMessage());
            throw e;
        }
    }

    /**
     * Loads cars from the binary file if it exists.
     * If the file doesn't exist or there's an error, starts with an empty list.
     */
    private void loadFromFile() {
        File file = new File(Constants.DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(file))) {
                @SuppressWarnings("unchecked")
                List<Car> loadedCars = (List<Car>) ois.readObject();
                cars.addAll(loadedCars);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println(Constants.ERROR_LOAD_DATA + e.getMessage());
            }
        }
    }

    /**
     * Validates a new car for duplicate ID and license plate.
     *
     * @param car The car to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateNewCar(Car car) {
        if (car.getLicensePlate() == null || car.getLicensePlate().trim().isEmpty()) {
            throw new IllegalArgumentException(Constants.ERROR_NULL_LICENSE);
        }
        if (isIdDuplicated(car.getId())) {
            throw new IllegalArgumentException(Constants.ERROR_ID_DUPLICATE);
        }
        if (isLicensePlateDuplicated(car.getLicensePlate())) {
            throw new IllegalArgumentException(Constants.ERROR_LICENSE_DUPLICATE);
        }
    }

    /**
     * Checks if an ID already exists in the system.
     *
     * @param id The ID to check
     * @return true if ID exists, false otherwise
     */
    private boolean isIdDuplicated(Integer id) {
        return cars.stream().anyMatch(c -> c.getId().equals(id));
    }

    /**
     * Checks if a license plate already exists in the system.
     *
     * @param licensePlate The license plate to check
     * @return true if license plate exists, false otherwise
     */
    private boolean isLicensePlateDuplicated(String licensePlate) {
        return cars.stream()
                .anyMatch(c -> c.getLicensePlate().equalsIgnoreCase(licensePlate));
    }
}