package service;

import dao.CarDAO;
import model.Car;
import utils.DealershipExceptions.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static utils.Constants.*;

public final class CarService {

    private final CarDAO carDAO;

    public CarService(CarDAO carDAO) {
        this.carDAO = Objects.requireNonNull(carDAO, ERROR_NULL_DAO);
    }

    public Car add(Car car) throws SQLException {
        validateCarForInsert(car);
        checkLicensePlateUniqueness(car.getLicensePlate());

        try {
            return carDAO.insert(car);
        } catch (SQLException e) {
            throw new DatabaseException(
                    String.format(ERROR_DATABASE, OPERATION_ADD, ENTITY_CAR), e);
        }
    }

    public boolean update(Car car) {
        validateCarForUpdate(car);

        try {
            Optional<Car> existingCar = carDAO.findById(car.getId());
            if (existingCar.isEmpty()) {
                throw new EntityNotFoundException(ENTITY_CAR, FIELD_ID, car.getId());
            }

            if (!existingCar.get().getLicensePlate().equals(car.getLicensePlate())) {
                checkLicensePlateUniqueness(car.getLicensePlate());
            }

            return carDAO.update(car);
        } catch (SQLException e) {
            throw new DatabaseException(
                    String.format(ERROR_DATABASE, OPERATION_UPDATE, ENTITY_CAR), e);
        }
    }

    public boolean deleteById(Integer id) {
        validateId(id);

        try {
            return carDAO.delete(id);
        } catch (SQLException e) {
            throw new DatabaseException(
                    String.format(ERROR_DATABASE, OPERATION_DELETE, ENTITY_CAR), e);
        }
    }

    public Optional<Car> findById(Integer id) {
        validateId(id);

        try {
            return carDAO.findById(id);
        } catch (SQLException e) {
            throw new DatabaseException(
                    String.format(ERROR_DATABASE, OPERATION_FIND, ENTITY_CAR), e);
        }
    }

    public List<Car> findAll() {
        try {
            return carDAO.findAll();
        } catch (SQLException e) {
            throw new DatabaseException(
                    String.format(ERROR_DATABASE, OPERATION_LIST, ENTITY_CAR), e);
        }
    }

    private void validateCarForInsert(Car car) {
        if (car == null) {
            throw new ValidationException(ERROR_NULL_CAR);
        }
        validateCarFields(car);
    }

    private void validateCarForUpdate(Car car) {
        validateCarForInsert(car);
        validateId(car.getId());
    }

    private void validateCarFields(Car car) {
        if (car.getLicensePlate() == null || car.getLicensePlate().trim().isEmpty()) {
            throw new ValidationException(String.format(ERROR_EMPTY_FIELD, FIELD_LICENSE_PLATE));
        }
        if (!car.getLicensePlate().matches(LICENSE_PLATE_REGEX)) {
            throw new ValidationException(ERROR_INVALID_LICENSE);
        }
        if (car.getBrand() == null || car.getBrand().trim().isEmpty()) {
            throw new ValidationException(String.format(ERROR_EMPTY_FIELD, FIELD_BRAND));
        }
        if (car.getModel() == null || car.getModel().trim().isEmpty()) {
            throw new ValidationException(String.format(ERROR_EMPTY_FIELD, FIELD_MODEL));
        }
        if (car.getColor() == null || car.getColor().trim().isEmpty()) {
            throw new ValidationException(String.format(ERROR_EMPTY_FIELD, FIELD_COLOR));
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

    private void checkLicensePlateUniqueness(String licensePlate) throws SQLException {
        if (carDAO.existsByLicensePlate(licensePlate)) {
            throw new DuplicateKeyException(FIELD_LICENSE_PLATE, licensePlate,
                    String.format(ERROR_DUPLICATE_LICENSE, licensePlate));
        }
    }
}