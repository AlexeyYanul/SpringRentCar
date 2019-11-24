package itacademy.service.impl;

import itacademy.model.CarModel;
import itacademy.repository.CarModelRepository;
import itacademy.service.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service(value = "carModelService")
@Transactional
public class CarModelServiceImpl implements CarModelService {

    private CarModelRepository carModelRepository;

    @Autowired
    public CarModelServiceImpl(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    public List<CarModel> getCarModels() {
        return carModelRepository.findAll();
    }

    public CarModel getById(Long id) {
        Optional<CarModel> carModel = carModelRepository.findById(id);
        if (!carModel.isPresent()) {
            return null;
        }
        return carModel.get();
    }

    public void saveCarModel(CarModel carModel) {
        carModelRepository.save(carModel);
    }

}
