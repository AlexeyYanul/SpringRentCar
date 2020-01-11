package itacademy.service;

import itacademy.model.Car;

import java.util.List;

public interface CarService {
    Car getById(Long id);

    List<Car> getByCarModelName(String name);

    List<Car> getByBody(String body);

    List<Car> getAllFreeCars();

    List<Car> getByCarModelYear(Integer year);

    List<Car> getByGearbox(String gearbox);

    List<Car> getAllCars();

    Car saveCar(Car car);

    Car updateCar(Car car);

    void deleteCar(Long id);

    List<Car> getByExample(Car car);
}
