package itacademy.service;

import itacademy.model.CarModel;
import itacademy.repository.CarModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * The type Car model service.
 */
@Service
@Transactional
public class CarModelService {

    private CarModelRepository carModelRepository;

    /**
     * Instantiates a new Car model service.
     *
     * @param carModelRepository the car model repository
     */
    @Autowired
    public CarModelService(CarModelRepository carModelRepository) {
        this.carModelRepository = carModelRepository;
    }

    /**
     * Gets car models.
     *
     * @return the car models
     */
    public List<CarModel> getCarModels() {
        return carModelRepository.findAll();
    }

    /**
     * Get car model by id.
     *
     * @param id the id
     * @return the by id
     */
    public CarModel getById(Long id) {
        Optional<CarModel> carModel = carModelRepository.findById(id);
        if (!carModel.isPresent()) {
            return null;
        }
        return carModel.get();
    }

    /**
     * Save transient or update detached/persistent car model.
     *
     * @param carModel the car model
     */
    public void saveCarModel(CarModel carModel) {
        carModelRepository.save(carModel);
    }

}
