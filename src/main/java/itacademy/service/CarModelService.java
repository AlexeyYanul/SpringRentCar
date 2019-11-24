package itacademy.service;

import itacademy.model.CarModel;

import java.util.List;

public interface CarModelService {
    List<CarModel> getCarModels();

    CarModel getById(Long id);

    void saveCarModel(CarModel carModel);
}
