package itacademy.service;

import itacademy.model.Car;
import itacademy.model.RentCar;
import itacademy.model.User;
import itacademy.model.enums.RentCarStatus;

import java.util.List;

public interface RentCarService {
    RentCar getById(Long id);

    RentCar requestRentCar(RentCar rentCar);

    RentCar acceptRequest(RentCar rentCar);

    RentCar finishRentCar(RentCar rentCar);

    RentCar cancelRequest(RentCar rentCar);

    List<RentCar> getByStatus(RentCarStatus status);

    List<RentCar> getByCar(Car car);

    List<RentCar> getByUser(User user);
}
