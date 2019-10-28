package itacademy.service;

import itacademy.model.Car;
import itacademy.model.CarModel;
import itacademy.model.Engine;
import itacademy.model.enums.Body;
import itacademy.model.enums.Gearbox;
import itacademy.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * The type Car service.
 */
@Service
@Transactional
public class CarService {

    private CarRepository carRepository;
    private CarModelService carModelService;
    private EngineService engineService;

    /**
     * Instantiates a new Car service.
     *
     * @param carRepository   the car repository
     * @param carModelService the car model service
     * @param engineService   the engine service
     */
    @Autowired
    public CarService(CarRepository carRepository, CarModelService carModelService,
                      EngineService engineService) {
        this.carRepository = carRepository;
        this.carModelService = carModelService;
        this.engineService = engineService;
    }

    /**
     * Get car by id.
     *
     * @param id the id
     * @return the by id
     */
    public Car getById(Long id) {
        Optional<Car> car = carRepository.findById(id);
        if (!car.isPresent()) {
            return null;
        }
        return car.get();
    }

    /**
     * Gets car list by model name.
     *
     * @param name the name
     * @return the by car model name
     */
    public List<Car> getByCarModelName(String name) {
        return carRepository.findByCarModelName(name);
    }

    /**
     * Gets car list by body type.
     *
     * @param body the body
     * @return the by body
     */
    public List<Car> getByBody(Body body) {
        return carRepository.findByBody(body);
    }

    /**
     * Gets all free cars.
     *
     * @return the all free cars
     */
    public List<Car> getAllFreeCars() {
        return carRepository.findByCarInfoIsFree(true);
    }

    /**
     * Gets car list by year.
     *
     * @param year the year
     * @return the by car model year
     */
    public List<Car> getByCarModelYear(Integer year) {
        return carRepository.findByCarModelYear(year);
    }

    /**
     * Gets car list by gearbox.
     *
     * @param gearbox the gearbox
     * @return the by gearbox
     */
    public List<Car> getByGearbox(Gearbox gearbox) {
        return carRepository.findByGearbox(gearbox);
    }

    /**
     * Gets all cars.
     *
     * @return the all cars
     */
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    /**
     * Save transient car model, egnine and car or
     * update detached/persistent car
     *
     * @param car the car
     */
    public void saveCar(Car car) {
        CarModel transientCarModel = car.getCarModel();
        carModelService.saveCarModel(transientCarModel);
        Engine transientEngine = car.getEngine();
        engineService.saveEngine(transientEngine);
        carRepository.save(car);
    }

    /**
     * Gets car list using 'query by example'.
     *
     * @param car the car
     * @return the by example
     */
    public List<Car> getByExample(Car car) {
        Example<Car> example = Example.of(car);
        return carRepository.findAll(example);
    }
}
