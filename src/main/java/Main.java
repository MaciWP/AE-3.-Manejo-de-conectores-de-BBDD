import config.DatabaseConnection;
import dao.CarDAO;
import dao.PassengerDAO;
import dao.impl.CarDAOImpl;
import dao.impl.PassengerDAOImpl;
import model.Car;
import model.Passenger;
import service.CarService;
import utils.DealershipExceptions.*;
import service.PassengerService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static utils.Constants.*;

public final class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final CarDAO carDAO = new CarDAOImpl();
    private static final PassengerDAO passengerDAO = new PassengerDAOImpl();
    private static final CarService carService = new CarService(carDAO);
    private static final PassengerService passengerService = new PassengerService(passengerDAO, carDAO);

    private Main() {
    }

    public static void main(String[] args) {
        try {
            runMainLoop();
        } catch (Exception e) {
            System.out.println(ERROR_DATABASE);
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    private static void runMainLoop() {
        boolean continueRunning = true;
        while (continueRunning) {
            try {
                displayMainMenu();
                int option = readOption();
                continueRunning = processMainMenuOption(option);
            } catch (SQLException e) {
                System.out.println(ERROR_DATABASE);
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println(String.format(ERROR_UNEXPECTED, e.getMessage()));
                e.printStackTrace();
            }
        }
    }

    private static void displayMainMenu() {
        System.out.println(MENU_TITLE);
        System.out.println(MENU_ADD);
        System.out.println(MENU_DELETE);
        System.out.println(MENU_FIND);
        System.out.println(MENU_UPDATE);
        System.out.println(MENU_LIST);
        System.out.println(MENU_PASSENGER);
        System.out.println(MENU_EXIT);
        System.out.print(MENU_OPTION);
    }

    private static int readOption() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(ERROR_INVALID_OPTION);
            return 0;
        }
    }

    private static boolean processMainMenuOption(int option) throws SQLException {
        return switch (option) {
            case OPTION_ADD -> { addNewCar(); yield true; }
            case OPTION_DELETE -> { deleteCar(); yield true; }
            case OPTION_FIND -> { findCar(); yield true; }
            case OPTION_UPDATE -> { updateCar(); yield true; }
            case OPTION_LIST -> { listAllCars(); yield true; }
            case OPTION_PASSENGER_MENU -> { handlePassengerMenu(); yield true; }
            case OPTION_EXIT -> { handleExit(); yield false; }
            default -> { handleInvalidOption(); yield true; }
        };
    }

    private static void addNewCar() throws SQLException {
        System.out.println(TITLE_ADD_CAR);
        try {
            Car car = readCarDetails();
            carService.add(car);
            System.out.println(SUCCESS_CAR_ADDED);
        } catch (DealershipException e) {
            System.out.println(ERROR_MESSAGE_PREFIX + e.getMessage());
        }
    }

    private static void updateCar() {
        System.out.println(TITLE_UPDATE_CAR);
        try {
            System.out.println(DISPLAY_AVAILABLE_CARS);
            listAllCars();

            int id = readId(PROMPT_UPDATE_ID);
            Optional<Car> existingCar = carService.findById(id);

            if (existingCar.isEmpty()) {
                System.out.println(ERROR_CAR_NOT_FOUND);
                return;
            }
            System.out.println(DISPLAY_CURRENT_CAR_DATA);
            System.out.println(existingCar.get());
            System.out.println(DISPLAY_NEW_CAR_DATA);

            Car updatedCar = readCarDetails();
            updatedCar.setId(id);

            if (carService.update(updatedCar)) {
                System.out.println(SUCCESS_CAR_UPDATED);
            } else {
                System.out.println(ERROR_UPDATE_FAILED);
            }
        } catch (DealershipException e) {
            System.out.println(ERROR_MESSAGE_PREFIX + e.getMessage());
        }
    }

    private static void deleteCar() {
        try {
            System.out.println(DISPLAY_AVAILABLE_CARS);
            listAllCars();

            int id = readId(PROMPT_DELETE_ID);
            if (carService.deleteById(id)) {
                System.out.println(SUCCESS_CAR_DELETED);
            } else {
                System.out.println(ERROR_CAR_NOT_FOUND);
            }
        } catch (DealershipException e) {
            System.out.println(ERROR_MESSAGE_PREFIX + e.getMessage());
        }
    }

    private static void findCar() {
        try {
            int id = readId(PROMPT_FIND_ID);
            carService.findById(id).ifPresentOrElse(
                    System.out::println,
                    () -> System.out.println(ERROR_CAR_NOT_FOUND)
            );
        } catch (DealershipException e) {
            System.out.println(ERROR_MESSAGE_PREFIX + e.getMessage());
        }
    }

    private static void listAllCars() {
        try {
            List<Car> cars = carService.findAll();
            if (cars.isEmpty()) {
                System.out.println(ERROR_NO_CARS);
                return;
            }

            System.out.println(TITLE_LIST_CARS);
            cars.forEach(System.out::println);
        } catch (DealershipException e) {
            System.out.println(ERROR_MESSAGE_PREFIX + e.getMessage());
        }
    }

    private static void handlePassengerMenu() {
        boolean continueSubMenu = true;
        while (continueSubMenu) {
            try {
                displayPassengerMenu();
                int option = readOption();
                continueSubMenu = processPassengerMenuOption(option);
            } catch (Exception e) {
                System.out.println(ERROR_DATABASE);
                e.printStackTrace();
            }
        }
    }

    private static void displayPassengerMenu() {
        System.out.println(PASSENGER_MENU_TITLE);
        System.out.println(PASSENGER_MENU_ADD);
        System.out.println(PASSENGER_MENU_DELETE);
        System.out.println(PASSENGER_MENU_FIND);
        System.out.println(PASSENGER_MENU_LIST);
        System.out.println(PASSENGER_MENU_ADD_TO_CAR);
        System.out.println(PASSENGER_MENU_REMOVE_FROM_CAR);
        System.out.println(PASSENGER_MENU_LIST_BY_CAR);
        System.out.println(PASSENGER_MENU_RETURN);
        System.out.print(MENU_OPTION);
    }

    private static boolean processPassengerMenuOption(int option) throws SQLException {
        switch (option) {
            case PASSENGER_OPTION_ADD:
                addNewPassenger();
                return true;
            case PASSENGER_OPTION_DELETE:
                deletePassenger();
                return true;
            case PASSENGER_OPTION_FIND:
                findPassenger();
                return true;
            case PASSENGER_OPTION_LIST:
                listAllPassengers();
                return true;
            case PASSENGER_OPTION_ADD_TO_CAR:
                addPassengerToCar();
                return true;
            case PASSENGER_OPTION_REMOVE_FROM_CAR:
                removePassengerFromCar();
                return true;
            case PASSENGER_OPTION_LIST_BY_CAR:
                listPassengersByCar();
                return true;
            case PASSENGER_OPTION_RETURN:
                return false;
            default:
                System.out.println(ERROR_INVALID_OPTION);
                return true;
        }
    }

    private static void addNewPassenger() {
        try {
            System.out.println(TITLE_ADD_PASSENGER);
            Passenger passenger = readPassengerDetails();
            passengerService.add(passenger);
            System.out.println(SUCCESS_PASSENGER_ADDED);
        } catch (DealershipException e) {
            System.out.println(ERROR_MESSAGE_PREFIX + e.getMessage());
        }
    }

    private static void deletePassenger() {
        try {
            int id = readId(PROMPT_PASSENGER_ID);
            if (passengerService.deleteById(id)) {
                System.out.println(SUCCESS_PASSENGER_DELETED);
            } else {
                System.out.println(ERROR_PASSENGER_NOT_FOUND);
            }
        } catch (DealershipException e) {
            System.out.println(ERROR_MESSAGE_PREFIX + e.getMessage());
        }
    }

    private static void findPassenger() {
        try {
            int id = readId(PROMPT_PASSENGER_ID);
            passengerService.findById(id).ifPresentOrElse(
                    System.out::println,
                    () -> System.out.println(ERROR_PASSENGER_NOT_FOUND)
            );
        } catch (DealershipException e) {
            System.out.println(ERROR_MESSAGE_PREFIX + e.getMessage());
        }
    }

    private static void listAllPassengers() {
        try {
            List<Passenger> passengers = passengerService.findAll();
            if (passengers.isEmpty()) {
                System.out.println(ERROR_NO_PASSENGERS);
                return;
            }

            System.out.println(TITLE_LIST_PASSENGERS);
            passengers.forEach(System.out::println);
        } catch (DealershipException e) {
            System.out.println(ERROR_MESSAGE_PREFIX + e.getMessage());
        }
    }

    private static void addPassengerToCar() {
        try {
            System.out.println(TITLE_ADD_PASSENGER_TO_CAR);
            displayAvailableCarsAndPassengers();

            int passengerId = readId(PROMPT_PASSENGER_ID);
            int carId = readId(PROMPT_CAR_ID);

            passengerService.addPassengerToCar(passengerId, carId);
            System.out.println(SUCCESS_PASSENGER_ADDED_TO_CAR);
        } catch (DealershipException e) {
            System.out.println(ERROR_MESSAGE_PREFIX + e.getMessage());
        }
    }

    private static void removePassengerFromCar() throws SQLException {
        try {
            System.out.println(TITLE_REMOVE_PASSENGER_FROM_CAR);
            displayCarsWithPassengers();

            int passengerId = readId(PROMPT_PASSENGER_ID);
            int carId = readId(PROMPT_CAR_ID);

            passengerService.removePassengerFromCar(passengerId, carId);
            System.out.println(SUCCESS_PASSENGER_REMOVED_FROM_CAR);
        } catch (DealershipException e) {
            System.out.println(ERROR_MESSAGE_PREFIX + e.getMessage());
        }
    }

    private static void listPassengersByCar() throws SQLException {
        try {
            int carId = readId(PROMPT_CAR_ID);
            List<Passenger> passengers = passengerService.findPassengersByCarId(carId);

            if (passengers.isEmpty()) {
                System.out.println(ERROR_NO_PASSENGERS);
                return;
            }

            System.out.printf(TITLE_LIST_CAR_PASSENGERS, carId);
            passengers.forEach(System.out::println);
        } catch (DealershipException e) {
            System.out.println(ERROR_MESSAGE_PREFIX + e.getMessage());
        }
    }

    private static Car readCarDetails() {
        System.out.print(PROMPT_LICENSE);
        String licensePlate = scanner.nextLine().trim().toUpperCase();
        System.out.print(PROMPT_BRAND);
        String brand = scanner.nextLine().trim();
        System.out.print(PROMPT_MODEL);
        String model = scanner.nextLine().trim();
        System.out.print(PROMPT_COLOR);
        String color = scanner.nextLine().trim();

        return new Car(licensePlate, brand, model, color);
    }

    private static Passenger readPassengerDetails() {
        System.out.print(PROMPT_PASSENGER_NAME);
        String name = scanner.nextLine().trim();

        int age = readNonNegativeInt(PROMPT_PASSENGER_AGE);
        double weight = readPositiveDouble(PROMPT_PASSENGER_WEIGHT);

        return new Passenger(name, age, weight);
    }

    private static int readId(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                String input = scanner.nextLine().trim();
                int id = Integer.parseInt(input);
                if (id <= 0) {
                    System.out.println(PROMPT_ID_POSITIVE);
                    continue;
                }
                return id;
            } catch (NumberFormatException e) {
                System.out.println(ERROR_ID_NUMBER);
            }
        }
    }

    private static int readNonNegativeInt(String prompt) {
        System.out.print(prompt);
        try {
            String input = scanner.nextLine().trim();
            int value = Integer.parseInt(input);
            if (value < 0) {
                System.out.println(PROMPT_NON_NEGATIVE_VALUE);
                return readNonNegativeInt(prompt);
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println(PROMPT_VALID_NUMBER);
            return readNonNegativeInt(prompt);
        }
    }

    private static double readPositiveDouble(String prompt) {
        System.out.print(prompt);
        try {
            String input = scanner.nextLine().trim();
            double value = Double.parseDouble(input);
            if (value <= 0) {
                System.out.println(PROMPT_POSITIVE_VALUE);
                return readPositiveDouble(prompt);
            }
            return value;
        } catch (NumberFormatException e) {
            System.out.println(PROMPT_VALID_NUMBER);
            return readPositiveDouble(prompt);
        }
    }

    private static void displayAvailableCarsAndPassengers() {
        System.out.println(AVAILABLE_CARS_TITLE);
        List<Car> cars = carService.findAll();
        if (cars.isEmpty()) {
            System.out.println(ERROR_NO_CARS);
        } else {
            cars.forEach(System.out::println);
        }

        System.out.println(AVAILABLE_PASSENGERS_TITLE);
        List<Passenger> passengers = passengerService.findAll();
        if (passengers.isEmpty()) {
            System.out.println(ERROR_NO_PASSENGERS);
        } else {
            passengers.forEach(System.out::println);
        }
    }

    private static void displayCarsWithPassengers() throws SQLException {
        List<Car> cars = carService.findAll();

        if (cars.isEmpty()) {
            System.out.println(ERROR_NO_CARS);
            return;
        }

        for (Car car : cars) {
            System.out.printf(DISPLAY_CAR_DETAILS, car);
            System.out.println();

            List<Passenger> passengers = passengerService.findPassengersByCarId(car.getId());

            if (passengers.isEmpty()) {
                System.out.println(NO_PASSENGERS_IN_CAR);
            } else {
                System.out.println(CAR_PASSENGERS_HEADER);
                passengers.forEach(passenger ->
                        System.out.println(String.format(DISPLAY_PASSENGER_INDENT, passenger)));
            }
        }
    }




    private static void handleExit() {
        System.out.println(SUCCESS_EXIT);
    }

    private static void handleInvalidOption() {
        System.out.println(ERROR_INVALID_OPTION);
    }

    private static void closeResources() {
        try {
            scanner.close();
            DatabaseConnection.closeConnection();
            System.out.println(PROMPT_RESOURCES_CLOSED);
        } catch (Exception e) {
            System.out.println(ERROR_RESOURCES_CLOSING);
            e.printStackTrace();
        }
    }
}
