package service;

import dao.PassengerDAO;
import dao.CarDAO;
import model.Passenger;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import utils.DealershipExceptions.*;
import static utils.Constants.*;

public final class PassengerService {
    private final PassengerDAO passengerDAO;
    private final CarDAO carDAO;

    public PassengerService(PassengerDAO passengerDAO, CarDAO carDAO) {
        this.passengerDAO = Objects.requireNonNull(passengerDAO, ERROR_NULL_PASSENGER_DAO);
        this.carDAO = Objects.requireNonNull(carDAO, ERROR_NULL_CAR_DAO);
    }

    public Passenger add(Passenger passenger) {
        validatePassengerForInsert(passenger);

        try {
            return passengerDAO.insert(passenger);
        } catch (SQLException e) {
            throw new DatabaseException(
                    String.format(ERROR_DATABASE, OPERATION_ADD, ENTITY_PASSENGER), e);
        }
    }

    public boolean update(Passenger passenger) {
        validatePassengerForUpdate(passenger);

        try {
            Optional<Passenger> existing = passengerDAO.findById(passenger.getId());
            if (existing.isEmpty()) {
                throw new EntityNotFoundException(ENTITY_PASSENGER, FIELD_ID, passenger.getId());
            }

            return passengerDAO.update(passenger);
        } catch (SQLException e) {
            throw new DatabaseException(
                    String.format(ERROR_DATABASE, OPERATION_UPDATE, ENTITY_PASSENGER), e);
        }
    }

    public boolean deleteById(Integer id) {
        validateId(id);

        try {
            return passengerDAO.delete(id);
        } catch (SQLException e) {
            throw new DatabaseException(
                    String.format(ERROR_DATABASE, OPERATION_DELETE, ENTITY_PASSENGER), e);
        }
    }

    public Optional<Passenger> findById(Integer id) {
        validateId(id);

        try {
            return passengerDAO.findById(id);
        } catch (SQLException e) {
            throw new DatabaseException(
                    String.format(ERROR_DATABASE, OPERATION_FIND, ENTITY_PASSENGER), e);
        }
    }

    public List<Passenger> findAll() {
        try {
            return passengerDAO.findAll();
        } catch (SQLException e) {
            throw new DatabaseException(
                    String.format(ERROR_DATABASE, OPERATION_LIST, ENTITY_PASSENGER), e);
        }
    }

    public void addPassengerToCar(int passengerId, int carId) {
        try {
            validatePassengerAndCarExist(passengerId, carId);
            validateCarCapacity(carId);

            if (!passengerDAO.addToCar(passengerId, carId)) {
                throw new DatabaseException(
                        String.format(ERROR_ADD_TO_CAR, passengerId, carId), null);
            }
        } catch (SQLException e) {
            throw new DatabaseException(
                    String.format(ERROR_DATABASE, OPERATION_ADD_TO_CAR, ENTITY_PASSENGER), e);
        }
    }

    public void removePassengerFromCar(int passengerId, int carId) {
        try {
            validatePassengerAndCarExist(passengerId, carId);

            if (!passengerDAO.removeFromCar(passengerId, carId)) {
                throw new DatabaseException(
                        String.format(ERROR_REMOVE_FROM_CAR, passengerId, carId), null);
            }
        } catch (SQLException e) {
            throw new DatabaseException(
                    String.format(ERROR_DATABASE, OPERATION_REMOVE_FROM_CAR, ENTITY_PASSENGER), e);
        }
    }

    public List<Passenger> findPassengersByCarId(int carId) throws SQLException {
        validateCarExists(carId);

        try {
            return passengerDAO.findByCarId(carId);
        } catch (SQLException e) {
            throw new DatabaseException(
                    String.format(ERROR_DATABASE, OPERATION_LIST, ENTITY_PASSENGER), e);
        }
    }

    private void validatePassengerForInsert(Passenger passenger) {
        if (passenger == null) {
            throw new ValidationException(ERROR_NULL_PASSENGER);
        }
        validatePassengerFields(passenger);
    }

    private void validatePassengerForUpdate(Passenger passenger) {
        validatePassengerForInsert(passenger);
        validateId(passenger.getId());
    }

    private void validatePassengerFields(Passenger passenger) {
        if (passenger.getName() == null || passenger.getName().trim().isEmpty()) {
            throw new ValidationException(String.format(ERROR_EMPTY_FIELD, FIELD_NAME));
        }
        if (passenger.getAge() < MIN_AGE) {
            throw new ValidationException(ERROR_NEGATIVE_AGE);
        }
        if (passenger.getWeight() <= MIN_WEIGHT) {
            throw new ValidationException(ERROR_INVALID_WEIGHT);
        }
    }

    private void validateId(Integer id) {
        if (id == null) {
            throw new ValidationException(String.format(ERROR_NULL_FIELD, FIELD_ID));
        }
        if (id <= 0) {
            throw new ValidationException(ERROR_INVALID_ID);
        }
    }

    private void validatePassengerAndCarExist(int passengerId, int carId) throws SQLException {
        validatePassengerExists(passengerId);
        validateCarExists(carId);
    }

    private void validatePassengerExists(int passengerId) throws SQLException {
        if (passengerDAO.findById(passengerId).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_PASSENGER, FIELD_ID, passengerId);
        }
    }

    private void validateCarExists(int carId) throws SQLException {
        if (carDAO.findById(carId).isEmpty()) {
            throw new EntityNotFoundException(ENTITY_CAR, FIELD_ID, carId);
        }
    }

    static class BusinessRuleException extends DealershipException {
        public BusinessRuleException(String message) {
            super(message);
        }
    }

    private void validateCarCapacity(int carId) throws SQLException {
        int currentPassengers = passengerDAO.getPassengerCountInCar(carId);
        if (currentPassengers >= MAX_PASSENGERS_PER_CAR) {
            throw new BusinessRuleException(
                    String.format(ERROR_MAX_CAPACITY, carId, MAX_PASSENGERS_PER_CAR));
        }
    }
}