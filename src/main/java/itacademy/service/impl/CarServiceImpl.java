package itacademy.service.impl;

import itacademy.component.LocalizedMessageSource;
import itacademy.model.Car;
import itacademy.model.enums.Body;
import itacademy.model.enums.Gearbox;
import itacademy.repository.CarRepository;
import itacademy.service.CarModelService;
import itacademy.service.CarService;
import itacademy.service.EngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * The type Car service.
 */
@Service(value = "carService")
@Transactional
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;

    private CarModelService carModelService;

    private EngineService engineService;

    private LocalizedMessageSource localizedMessageSource;

    /**
     * Instantiates a new Car service.
     *
     * @param carRepository          the car repository
     * @param localizedMessageSource the localized message source
     * @param carModelService        the car model service
     * @param engineService          the engine service
     */
    @Autowired
    public CarServiceImpl(CarRepository carRepository, LocalizedMessageSource localizedMessageSource,
                          CarModelService carModelService, EngineService engineService) {
        this.carRepository = carRepository;
        this.carModelService = carModelService;
        this.engineService = engineService;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets by id.
     *
     * @param id the car id
     * @return the car
     */
    @Override
    public Car getById(Long id) {
        validate(id == null, "error.idIsNull");
        Optional<Car> car = carRepository.findById(id);
        validate(!car.isPresent(), "error.car.notFound");
        return car.get();
    }

    /**
     * Gets by car model name.
     *
     * @param name the car model name
     * @return the cars list
     */
    @Override
    public List<Car> getByCarModelName(String name) {
        validate(name.isEmpty(), "error.car.modelNameIsNull");
        List<Car> carList = carRepository.findByCarModelName(name);
        validate(carList.isEmpty(), "error.car.notFound");
        return carList;
    }

    /**
     * Gets by car body type.
     *
     * @param bodyType the car body type
     * @return the cars list
     */
    @Override
    public List<Car> getByBody(String bodyType) {
        List<Car> carList;
        try {
            Body body = Body.valueOf(bodyType);
            carList = carRepository.findByBody(body);
        } catch (IllegalArgumentException exception) {
            throw new RuntimeException(localizedMessageSource.getMessage("error.car.unexpectedBodyType", new Object[]{}));
        }
        validate(carList.isEmpty(), "error.car.notFound");
        return carList;
    }

    /**
     * Gets all free cars.
     *
     * @return the cars list
     */
    @Override
    public List<Car> getAllFreeCars() {
        return carRepository.findByCarInfoStatus(true);
    }

    /**
     * Gets by car year.
     *
     * @param year the car year
     * @return the cars list
     */
    @Override
    public List<Car> getByCarModelYear(Integer year) {
        validate(year == null, "error.car.modelYearIsNull");
        List<Car> carList = carRepository.findByCarModelYear(year);
        validate(carList.isEmpty(), "error.car.notFound");
        return carList;
    }

    /**
     * Gets by car gearbox type.
     *
     * @param gearboxType the car gearbox type
     * @return the cars list
     */
    @Override
    public List<Car> getByGearbox(String gearboxType) {
        List<Car> carList;
        try {
            Gearbox gearbox = Gearbox.valueOf(gearboxType);
            carList = carRepository.findByGearbox(gearbox);
        } catch (IllegalArgumentException exception) {
            throw new NullPointerException(localizedMessageSource.getMessage("error.car.unexpectedGearboxType", new Object[]{}));
        }
        validate(carList.isEmpty(), "error.car.notFound");
        return carList;
    }

    /**
     * Gets all cars.
     *
     * @return the cars list
     */
    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    /**
     * Save car.
     *
     * @param car the car
     * @return the car
     */
    @Override
    public Car saveCar(Car car) {
        if (car.getId() != null)
            throw new RuntimeException(localizedMessageSource.getMessage("error.car.notHaveId", new Object[]{}));
        if (car.getCarModel() == null || car.getCarModel().getId() == null)
            throw new RuntimeException(localizedMessageSource.getMessage("error.car.model.isNull", new Object[]{}));
        if (car.getEngine() == null || car.getEngine().getId() == null)
            throw new RuntimeException(localizedMessageSource.getMessage("error.car.engine.isNull", new Object[]{}));
        car.setCarModel(carModelService.getById(car.getCarModel().getId()));
        car.setEngine(engineService.getById(car.getEngine().getId()));
        return carRepository.save(car);
    }

    /**
     * Update car.
     *
     * @param car the car
     * @return the car
     */
    public Car updateCar(Car car) {
        validate(car.getId() == null, "error.car.haveId");
        getById(car.getId());
        car.setCarModel(carModelService.getById(car.getCarModel().getId()));
        car.setEngine(engineService.getById(car.getEngine().getId()));
        return carRepository.save(car);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    public void deleteCar(Long id) {
        getById(id);
        carRepository.deleteById(id);
    }

    /**
     * Gets by car example.
     *
     * @param car the car example
     * @return the cars list
     */
    public List<Car> getByExample(Car car) {
        Example<Car> example = Example.of(car);
        return carRepository.findAll(example);
    }

    private void validate(boolean expression, String messageCode) {
        if (expression) {
            String errorMessage = localizedMessageSource.getMessage(messageCode, new Object[]{});
            throw new RuntimeException(errorMessage);
        }
    }

}
