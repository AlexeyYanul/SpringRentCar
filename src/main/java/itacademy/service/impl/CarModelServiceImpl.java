package itacademy.service.impl;

import itacademy.component.LocalizedMessageSource;
import itacademy.model.CarModel;
import itacademy.repository.CarModelRepository;
import itacademy.service.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * The type Car model service.
 */
@Service(value = "carModelService")
@Transactional
public class CarModelServiceImpl implements CarModelService {

    private CarModelRepository carModelRepository;

    private LocalizedMessageSource localizedMessageSource;

    /**
     * Instantiates a new Car model service.
     *
     * @param carModelRepository     the car model repository
     * @param localizedMessageSource the localized message source
     */
    @Autowired
    public CarModelServiceImpl(CarModelRepository carModelRepository, LocalizedMessageSource localizedMessageSource) {
        this.carModelRepository = carModelRepository;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets car models.
     *
     * @return the car models list
     */
    @Override
    public List<CarModel> getCarModels() {
        return carModelRepository.findAll();
    }

    /**
     * Gets by id.
     *
     * @param id the car model id
     * @return the car model
     */
    @Override
    public CarModel getById(Long id) {
        validate(id == null, "error.idIsNull");
        Optional<CarModel> carModel = carModelRepository.findById(id);
        validate(!carModel.isPresent(), "error.carModel.notFound");
        return carModel.get();
    }

    /**
     * Save car model.
     *
     * @param carModel the car model
     * @return the car model
     */
    @Override
    public CarModel saveCarModel(CarModel carModel) {
        validate(carModel.getId() != null, "error.carModel.notHaveId");
        checkDuplicate(carModel);
        return carModelRepository.save(carModel);
    }

    /**
     * Update car model.
     *
     * @param carModel the car model
     * @return the car model
     */
    @Override
    public CarModel updateCarModel(CarModel carModel) {
        validate(carModel.getId() == null, "error.carModel.haveId");
        checkDuplicate(carModel);
        return carModelRepository.save(carModel);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @Override
    public void delete(Long id) {
        getById(id);
        carModelRepository.deleteById(id);
    }

    /**
     * Gets by example.
     *
     * @param carModel the car model
     * @return the by example
     */
    public List<CarModel> getByExample(CarModel carModel) {
        Example<CarModel> modelExample = Example.of(carModel);
        return carModelRepository.findAll(modelExample);
    }

    /**
     * Check duplicate in the database.
     *
     * @param carModel the car model
     */
    private void checkDuplicate(CarModel carModel) {
        List<CarModel> duplicateCarModel = getByExample(carModel);
        validate(!duplicateCarModel.isEmpty(), "error.carModel.notUnique");
    }

    private void validate(boolean expression, String messageCode) {
        if (expression) {
            String errorMessage = localizedMessageSource.getMessage(messageCode, new Object[]{});
            throw new RuntimeException(errorMessage);
        }
    }
}
