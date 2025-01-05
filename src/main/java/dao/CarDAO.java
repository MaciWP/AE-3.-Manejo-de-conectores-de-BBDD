package dao;

import model.Car;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CarDAO {

    Car insert(Car car) throws SQLException;

    boolean update(Car car) throws SQLException;

    boolean delete(int id) throws SQLException;

    Optional<Car> findById(int id) throws SQLException;

    List<Car> findAll() throws SQLException;

    boolean existsByLicensePlate(String licensePlate) throws SQLException;
}