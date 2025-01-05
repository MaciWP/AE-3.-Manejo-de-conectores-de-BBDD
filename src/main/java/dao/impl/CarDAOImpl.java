package dao.impl;

import dao.CarDAO;
import model.Car;
import config.DatabaseConnection;
import config.SQLQueries.CarQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static utils.Constants.*;

public final class CarDAOImpl implements CarDAO {

    @Override
    public Car insert(Car car) throws SQLException {
        validateCar(car);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     CarQueries.INSERT,
                     Statement.RETURN_GENERATED_KEYS)) {

            setCarParameters(pstmt, car);
            executeInsert(pstmt, car);
            return car;

        } catch (SQLException e) {
            System.err.println(ERROR_INSERT + car);
            throw e;
        }
    }

    @Override
    public boolean update(Car car) throws SQLException {
        validateCarWithId(car);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(CarQueries.UPDATE)) {

            setCarParameters(pstmt, car);
            pstmt.setInt(5, car.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(ERROR_UPDATE + car);
            throw e;
        }
    }

    @Override
    public boolean delete(int id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(CarQueries.DELETE)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(ERROR_DELETE + id);
            throw e;
        }
    }

    @Override
    public Optional<Car> findById(int id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(CarQueries.FIND_BY_ID)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            return rs.next() ? Optional.of(extractCarFromResultSet(rs))
                    : Optional.empty();

        } catch (SQLException e) {
            System.err.println(ERROR_FIND + id);
            throw e;
        }
    }

    @Override
    public List<Car> findAll() throws SQLException {
        List<Car> cars = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(CarQueries.FIND_ALL)) {

            while (rs.next()) {
                cars.add(extractCarFromResultSet(rs));
            }
            return cars;

        } catch (SQLException e) {
            System.err.println(ERROR_FIND_ALL);
            throw e;
        }
    }

    @Override
    public boolean existsByLicensePlate(String licensePlate) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(CarQueries.EXISTS_BY_LICENSE_PLATE)) {

            pstmt.setString(1, licensePlate);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;

        } catch (SQLException e) {
            System.err.println(ERROR_LICENSE_PLATE + licensePlate);
            throw e;
        }
    }

    private void executeInsert(PreparedStatement pstmt, Car car) throws SQLException {
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException(ERROR_CREATE_NO_ROWS);
        }

        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                car.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException(ERROR_CREATE_NO_ID);
            }
        }
    }

    private Car extractCarFromResultSet(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setId(rs.getInt(COLUMN_ID));
        car.setLicensePlate(rs.getString(COLUMN_LICENSE_PLATE));
        car.setBrand(rs.getString(COLUMN_BRAND));
        car.setModel(rs.getString(COLUMN_MODEL));
        car.setColor(rs.getString(COLUMN_COLOR));
        return car;
    }

    private void setCarParameters(PreparedStatement pstmt, Car car) throws SQLException {
        pstmt.setString(1, car.getLicensePlate());
        pstmt.setString(2, car.getBrand());
        pstmt.setString(3, car.getModel());
        pstmt.setString(4, car.getColor());
    }

    private void validateCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException(ERROR_NULL_CAR);
        }
    }

    private void validateCarWithId(Car car) {
        validateCar(car);
        if (car.getId() == null) {
            throw new IllegalArgumentException(ERROR_NULL_ID);
        }
    }
}