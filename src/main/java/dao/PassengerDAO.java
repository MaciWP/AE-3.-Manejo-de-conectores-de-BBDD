package dao;

import model.Passenger;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PassengerDAO {

    Passenger insert(Passenger passenger) throws SQLException;

    boolean update(Passenger passenger) throws SQLException;

    boolean delete(int id) throws SQLException;

    Optional<Passenger> findById(int id) throws SQLException;

    List<Passenger> findAll() throws SQLException;

    boolean addToCar(int passengerId, int carId) throws SQLException;

    boolean removeFromCar(int passengerId, int carId) throws SQLException;

    List<Passenger> findByCarId(int carId) throws SQLException;

    boolean isInAnyCar(int passengerId) throws SQLException;

    int getPassengerCountInCar(int carId) throws SQLException;
}