package itacademy.service;

import itacademy.model.CarInfo;

public interface CarInfoService {
    CarInfo saveCarInfo(CarInfo carInfo);

    CarInfo getById(Long id);

    CarInfo getByCarId(Long id);

    CarInfo updateCarInfo(CarInfo carInfo);

    void deleteById(Long id);
}
