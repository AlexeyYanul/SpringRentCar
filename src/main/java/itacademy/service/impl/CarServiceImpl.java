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
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;

@Service(value = "carService")
@Transactional
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;

    private CarModelService carModelService;

    private EngineService engineService;

    private LocalizedMessageSource localizedMessageSource;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, LocalizedMessageSource localizedMessageSource,
                          CarModelService carModelService, EngineService engineService) {
        this.carRepository = carRepository;
        this.carModelService = carModelService;
        this.engineService = engineService;
        this.localizedMessageSource = localizedMessageSource;
    }

    @Override
    public Car getById(Long id) {
        if (id == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.idIsNull", new Object[]{}));
        Optional<Car> car = carRepository.findById(id);
        if (!car.isPresent()) {
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.car.notFound", new Object[]{}));
        }
        return car.get();
    }

    @Override
    public List<Car> getByCarModelName(String name) {
        if (name.isEmpty())
            throw new NullPointerException(localizedMessageSource.getMessage("error.car.modelNameIsNull", new Object[]{}));
        List<Car> carList = carRepository.findByCarModelName(name);
        if (carList.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.car.notFound", new Object[]{}));
        return carList;
    }

    @Override
    public List<Car> getByBody(String bodyType) {
        List<Car> carList;
        try {
            Body body = Body.valueOf(bodyType);
            carList = carRepository.findByBody(body);
        } catch (IllegalArgumentException exception) {
            throw new NullPointerException(localizedMessageSource.getMessage("error.car.unexpectedBodyType", new Object[]{}));
        }
        if (carList.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.car.notFound", new Object[]{}));
        return carList;
    }

    @Override
    public List<Car> getAllFreeCars() {
        return carRepository.findByCarInfoStatus(true);
    }

    @Override
    public List<Car> getByCarModelYear(Integer year) {
        if (year == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.car.modelYearIsNull", new Object[]{}));
        List<Car> carList = carRepository.findByCarModelYear(year);
        if (carList.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.car.notFound", new Object[]{}));
        return carList;
    }

    @Override
    public List<Car> getByGearbox(String gearboxType) {
        List<Car> carList;
        try {
            Gearbox gearbox = Gearbox.valueOf(gearboxType);
            carList = carRepository.findByGearbox(gearbox);
        } catch (IllegalArgumentException exception) {
            throw new NullPointerException(localizedMessageSource.getMessage("error.car.unexpectedGearboxType", new Object[]{}));
        }
        if (carList.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.car.notFound", new Object[]{}));
        return carList;
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car saveCar(Car car) {
        if (car.getId() != null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.car.notHaveId", new Object[]{}));
        if (car.getCarModel() == null || car.getCarModel().getId() == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.car.model.isNull", new Object[]{}));
        if (car.getEngine() == null || car.getEngine().getId() == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.car.engine.isNull", new Object[]{}));
        car.setCarModel(carModelService.getById(car.getCarModel().getId()));
        car.setEngine(engineService.getById(car.getEngine().getId()));
        return carRepository.save(car);
    }

    public Car updateCar(Car car) {
        if (car.getId() == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.car.haveId", new Object[]{}));
        getById(car.getId());
        car.setCarModel(carModelService.getById(car.getCarModel().getId()));
        car.setEngine(engineService.getById(car.getEngine().getId()));
        return carRepository.save(car);
    }

    public void deleteCar(Long id) {
        getById(id);
        carRepository.deleteById(id);
    }

    public List<Car> getByExample(Car car) {
        Example<Car> example = Example.of(car);
        return carRepository.findAll(example);
    }

}
