package itacademy.service.impl;

import itacademy.model.Car;
import itacademy.model.CarModel;
import itacademy.model.Engine;
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

import java.util.List;
import java.util.Optional;

@Service(value = "carService")
@Transactional
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car getById(Long id) {
        Optional<Car> car = carRepository.findById(id);
        if (!car.isPresent()) {
            return null;
        }
        return car.get();
    }

    public List<Car> getByCarModelName(String name) {
        return carRepository.findByCarModelName(name);
    }

    public List<Car> getByBody(Body body) {
        return carRepository.findByBody(body);
    }

    public List<Car> getAllFreeCars() {
        return carRepository.findByCarInfoStatus(true);
    }

    public List<Car> getByCarModelYear(Integer year) {
        return carRepository.findByCarModelYear(year);
    }

    public List<Car> getByGearbox(Gearbox gearbox) {
        return carRepository.findByGearbox(gearbox);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getByExample(Car car) {
        Example<Car> example = Example.of(car);
        return carRepository.findAll(example);
    }
}
