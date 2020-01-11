package itacademy.service;

import itacademy.model.RentCar;

import java.util.List;

public interface RentCarService {
    RentCar getById(Long id);

    RentCar requestRentCar(RentCar rentCar);

    RentCar acceptRequest(Long id);

    RentCar finishRentCar(Long id);

    RentCar cancelRequest(Long id);

    List<RentCar> getByStatus(String status);

    List<RentCar> getByCarId(Long carId);

    List<RentCar> getByUserId(Long userId);

    List<RentCar> getAll();
}
