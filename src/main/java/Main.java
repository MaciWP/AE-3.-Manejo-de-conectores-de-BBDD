import model.Car;
import service.CarService;
import utils.Constants;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;


public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CarService carService = new CarService();

    /**
     * Main entry point of the application.
     * Sets up shutdown hook and runs the main program loop.
     */
    public static void main(String[] args) {
        setupShutdownHook();

        boolean continueRunning = true;
        while (continueRunning) {
            displayMenu();
            int option = readOption();
            continueRunning = processOption(option);
        }
        scanner.close();
    }

    /**
     * Sets up a shutdown hook to save data when the program is terminated.
     */
    private static void setupShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                carService.saveToFile();
                System.out.println(Constants.SUCCESS_SAVE);
            } catch (IOException e) {
                System.err.println(Constants.ERROR_SAVE_DATA + e.getMessage());
            }
        }));
    }

    /**
     * Displays the main menu options to the user.
     */
    private static void displayMenu() {
        System.out.println(Constants.MENU_TITLE);
        System.out.println(Constants.MENU_ADD);
        System.out.println(Constants.MENU_DELETE);
        System.out.println(Constants.MENU_FIND);
        System.out.println(Constants.MENU_LIST);
        System.out.println(Constants.MENU_EXPORT);
        System.out.println(Constants.MENU_EXIT);
        System.out.print(Constants.MENU_OPTION);
    }

    /**
     * Reads and validates user input for menu option.
     *
     * @return The selected menu option as an integer
     */
    private static int readOption() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Processes the selected menu option.
     *
     * @param option The selected menu option
     * @return true if the program should continue, false if it should exit
     */
    private static boolean processOption(int option) {
        return switch (option) {
            case Constants.OPTION_ADD -> { addNewCar(); yield true; }
            case Constants.OPTION_DELETE -> { deleteCar(); yield true; }
            case Constants.OPTION_FIND -> { findCar(); yield true; }
            case Constants.OPTION_LIST -> { listAllCars(); yield true; }
            case Constants.OPTION_EXPORT -> { exportToCsv(); yield true; }
            case Constants.OPTION_EXIT -> { exit(); yield false; }
            default -> { System.out.println(Constants.ERROR_INVALID_OPTION); yield true; }
        };
    }

    /**
     * Handles the addition of a new car to the system.
     * Reads car details from user input and validates them.
     */
    private static void addNewCar() {
        try {
            System.out.println(Constants.TITLE_ADD_CAR);

            System.out.print(Constants.PROMPT_LICENSE);
            String licensePlate = scanner.nextLine();

            System.out.print(Constants.PROMPT_BRAND);
            String brand = scanner.nextLine();

            System.out.print(Constants.PROMPT_MODEL);
            String model = scanner.nextLine();

            System.out.print(Constants.PROMPT_COLOR);
            String color = scanner.nextLine();

            Car car = new Car(null, licensePlate, brand, model, color); // ID será asignado por el servicio
            carService.add(car);
            System.out.println(Constants.SUCCESS_CAR_ADDED);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handles the deletion of a car from the system.
     * Reads car ID from user input and attempts to delete the corresponding car.
     */
    private static void deleteCar() {
        System.out.print(Constants.PROMPT_DELETE_ID);
        try {
            Integer id = Integer.parseInt(scanner.nextLine());
            if (carService.deleteById(id)) {
                System.out.println(Constants.SUCCESS_CAR_DELETED);
            } else {
                System.out.println(Constants.ERROR_CAR_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            System.out.println(Constants.ERROR_ID_NUMBER);
        }
    }

    /**
     * Handles the search for a car in the system.
     * Reads car ID from user input and displays the car if found.
     */
    private static void findCar() {
        System.out.print(Constants.PROMPT_FIND_ID);
        try {
            Integer id = Integer.parseInt(scanner.nextLine());
            Optional<Car> car = carService.findById(id);
            car.ifPresentOrElse(
                    System.out::println,
                    () -> System.out.println(Constants.ERROR_CAR_NOT_FOUND)
            );
        } catch (NumberFormatException e) {
            System.out.println(Constants.ERROR_ID_NUMBER);
        }
    }

    /**
     * Displays a list of all cars in the system.
     */
    private static void listAllCars() {
        System.out.println(Constants.TITLE_LIST_CARS);
        var cars = carService.findAll();
        if (cars.isEmpty()) {
            System.out.println(Constants.ERROR_NO_CARS);
        } else {
            cars.forEach(System.out::println);
        }
    }

    /**
     * Handles the export of all cars to a CSV file.
     */
    private static void exportToCsv() {
        try {
            carService.exportToCsv();
            System.out.println(Constants.SUCCESS_CSV_EXPORT);
        } catch (IOException e) {
            System.out.println(Constants.ERROR_EXPORT_CSV + e.getMessage());
        }
    }

    /**
     * Handles the program termination process.
     * Saves all data before exiting.
     */
    private static void exit() {
        try {
            carService.saveToFile();
            System.out.println(Constants.SUCCESS_EXIT);
        } catch (IOException e) {
            System.out.println(Constants.ERROR_SAVE_DATA + e.getMessage());
        }
    }
}