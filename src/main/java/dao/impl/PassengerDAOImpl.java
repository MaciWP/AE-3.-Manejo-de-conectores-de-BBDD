package dao.impl;

import dao.PassengerDAO;
import model.Passenger;
import config.DatabaseConnection;
import config.SQLQueries.PassengerQueries;
import config.SQLQueries.CarPassengerQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static utils.Constants.*;


public final class PassengerDAOImpl implements PassengerDAO {

    @Override
    public Passenger insert(Passenger passenger) throws SQLException {
        validatePassenger(passenger);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     PassengerQueries.INSERT,
                     Statement.RETURN_GENERATED_KEYS)) {

            setPassengerParameters(pstmt, passenger);
            executeInsert(pstmt, passenger);
            return passenger;

        } catch (SQLException e) {
            System.err.println(ERROR_INSERT + passenger);
            throw e;
        }
    }

    @Override
    public boolean update(Passenger passenger) throws SQLException {
        validatePassengerWithId(passenger);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(PassengerQueries.UPDATE)) {

            setPassengerParameters(pstmt, passenger);
            pstmt.setInt(4, passenger.getId());
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(ERROR_UPDATE + passenger);
            throw e;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(PassengerQueries.DELETE)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(ERROR_DELETE + id);
            throw e;
        }
    }

    @Override
    public Optional<Passenger> findById(int id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(PassengerQueries.FIND_BY_ID)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? Optional.of(extractPassengerFromResultSet(rs))
                    : Optional.empty();

        } catch (SQLException e) {
            System.err.println(ERROR_FIND + id);
            throw e;
        }
    }

    @Override
    public List<Passenger> findAll() throws SQLException {
        List<Passenger> passengers = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(PassengerQueries.FIND_ALL)) {

            while (rs.next()) {
                passengers.add(extractPassengerFromResultSet(rs));
            }
            return passengers;

        } catch (SQLException e) {
            System.err.println(ERROR_FIND_ALL);
            throw e;
        }
    }

    @Override
    public boolean addToCar(int passengerId, int carId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     CarPassengerQueries.ADD_PASSENGER_TO_CAR)) {

            pstmt.setInt(1, carId);
            pstmt.setInt(2, passengerId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.printf((ERROR_ADD_TO_CAR) + "%n", passengerId, carId);
            throw e;
        }
    }

    @Override
    public boolean removeFromCar(int passengerId, int carId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     CarPassengerQueries.REMOVE_PASSENGER_FROM_CAR)) {

            pstmt.setInt(1, carId);
            pstmt.setInt(2, passengerId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.printf((ERROR_REMOVE_FROM_CAR) + "%n", passengerId, carId);
            throw e;
        }
    }

    @Override
    public List<Passenger> findByCarId(int carId) throws SQLException {
        List<Passenger> passengers = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(CarPassengerQueries.FIND_PASSENGERS_BY_CAR)) {

            pstmt.setInt(1, carId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    passengers.add(extractPassengerFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println(ERROR_FIND_BY_CAR + carId);
            throw e;
        }

        return passengers;
    }

    private void executeInsert(PreparedStatement pstmt, Passenger passenger) throws SQLException {
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException(ERROR_CREATE_NO_ROWS);
        }

        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                passenger.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException(ERROR_CREATE_NO_ID);
            }
        }
    }

    private Passenger extractPassengerFromResultSet(ResultSet rs) throws SQLException {
        Passenger passenger = new Passenger();
        passenger.setId(rs.getInt(COLUMN_ID));
        passenger.setName(rs.getString(COLUMN_NAME));
        passenger.setAge(rs.getInt(COLUMN_AGE));
        passenger.setWeight(rs.getDouble(COLUMN_WEIGHT));
        return passenger;
    }

    private void setPassengerParameters(PreparedStatement pstmt, Passenger passenger)
            throws SQLException {
        pstmt.setString(1, passenger.getName());
        pstmt.setInt(2, passenger.getAge());
        pstmt.setDouble(3, passenger.getWeight());
    }

    private void validatePassenger(Passenger passenger) {
        if (passenger == null) {
            throw new IllegalArgumentException(ERROR_NULL_PASSENGER);
        }
        if (passenger.getName() == null || passenger.getName().trim().isEmpty()) {
            throw new IllegalArgumentException(ERROR_EMPTY_NAME);
        }
        if (passenger.getAge() < MIN_AGE) {
            throw new IllegalArgumentException(ERROR_NEGATIVE_AGE);
        }
        if (passenger.getWeight() <= MIN_WEIGHT) {
            throw new IllegalArgumentException(ERROR_INVALID_WEIGHT);
        }
    }

    private void validatePassengerWithId(Passenger passenger) {
        validatePassenger(passenger);
        if (passenger.getId() == null) {
            throw new IllegalArgumentException(ERROR_NULL_ID);
        }
    }

    @Override
    public boolean isInAnyCar(int passengerId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(CarPassengerQueries.CHECK_PASSENGER_IN_CAR)) {

            pstmt.setInt(1, passengerId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println(ERROR_FIND_BY_CAR + passengerId);
            throw e;
        }
    }
    @Override
    public int getPassengerCountInCar(int carId) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(CarPassengerQueries.COUNT_PASSENGERS_IN_CAR)) {

            pstmt.setInt(1, carId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            System.err.println(ERROR_FIND_BY_CAR + carId);
            throw e;
        }
    }
}