package itacademy.service;

import itacademy.model.Car;
import itacademy.model.enums.Body;
import itacademy.model.enums.Gearbox;

import java.util.List;

public interface CarService {
    Car getById(Long id);

    List<Car> getByCarModelName(String name);

    List<Car> getByBody(Body body);

    List<Car> getAllFreeCars();

    List<Car> getByCarModelYear(Integer year);

    List<Car> getByGearbox(Gearbox gearbox);

    List<Car> getAllCars();

    void saveCar(Car car);

    List<Car> getByExample(Car car);
}
