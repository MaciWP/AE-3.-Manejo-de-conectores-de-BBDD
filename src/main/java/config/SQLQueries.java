package config;

public final class SQLQueries {

    private SQLQueries() {
    }

    public static final class CarQueries {
        public static final String INSERT =
                "INSERT INTO cars (license_plate, brand, model, color) VALUES (?, ?, ?, ?)";

        public static final String UPDATE =
                "UPDATE cars SET license_plate = ?, brand = ?, model = ?, color = ? WHERE id = ?";

        public static final String DELETE =
                "DELETE FROM cars WHERE id = ?";

        public static final String FIND_BY_ID =
                "SELECT id, license_plate, brand, model, color FROM cars WHERE id = ?";

        public static final String FIND_ALL =
                "SELECT id, license_plate, brand, model, color FROM cars";

        public static final String EXISTS_BY_LICENSE_PLATE =
                "SELECT COUNT(*) FROM cars WHERE license_plate = ?";

        private CarQueries() {
        }
    }

    public static final class PassengerQueries {
        public static final String INSERT =
                "INSERT INTO passengers (name, age, weight) VALUES (?, ?, ?)";

        public static final String UPDATE =
                "UPDATE passengers SET name = ?, age = ?, weight = ? WHERE id = ?";

        public static final String DELETE =
                "DELETE FROM passengers WHERE id = ?";

        public static final String FIND_BY_ID =
                "SELECT id, name, age, weight FROM passengers WHERE id = ?";

        public static final String FIND_ALL =
                "SELECT id, name, age, weight FROM passengers";

        private PassengerQueries() {
        }
    }

    public static final class CarPassengerQueries {
        public static final String ADD_PASSENGER_TO_CAR =
                "INSERT INTO car_passengers (car_id, passenger_id) VALUES (?, ?)";

        public static final String REMOVE_PASSENGER_FROM_CAR =
                "DELETE FROM car_passengers WHERE car_id = ? AND passenger_id = ?";

        public static final String FIND_PASSENGERS_BY_CAR =
                "SELECT p.id, p.name, p.age, p.weight FROM passengers p " +
                        "JOIN car_passengers cp ON p.id = cp.passenger_id WHERE cp.car_id = ?";

        public static final String COUNT_PASSENGERS_IN_CAR =
                "SELECT COUNT(*) FROM car_passengers WHERE car_id = ?";

        public static final String CHECK_PASSENGER_IN_CAR =
                "SELECT COUNT(*) FROM car_passengers WHERE passenger_id = ?";

        private CarPassengerQueries() {
        }
    }
}