package itacademy.service;

import itacademy.model.Car;
import itacademy.model.RentCar;
import itacademy.model.User;
import itacademy.model.enums.RentCarStatus;

import java.util.List;

public interface RentCarService {
    RentCar getById(Long id);

    void requestRentCar(RentCar rentCar);

    void acceptRequest(RentCar rentCar);

    void finishRentCar(RentCar rentCar);

    void cancelRequest(RentCar rentCar);

    List<RentCar> getByStatus(RentCarStatus status);

    List<RentCar> getByCar(Car car);

    List<RentCar> getByUser(User user);
}
