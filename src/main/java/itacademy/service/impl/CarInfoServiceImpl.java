package itacademy.service.impl;

import itacademy.component.LocalizedMessageSource;
import itacademy.model.CarInfo;
import itacademy.repository.CarInfoRepository;
import itacademy.service.CarInfoService;
import itacademy.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

/**
 * The type Car info service.
 */
@Service(value = "carInfoService")
@Transactional
public class CarInfoServiceImpl implements CarInfoService {

    private CarInfoRepository carInfoRepository;

    private CarService carService;

    private LocalizedMessageSource localizedMessageSource;

    /**
     * Instantiates a new Car info service.
     *
     * @param carInfoRepository      the car info repository
     * @param carService             the car service
     * @param localizedMessageSource the localized message source
     */
    @Autowired
    public CarInfoServiceImpl(CarInfoRepository carInfoRepository, CarService carService,
                              LocalizedMessageSource localizedMessageSource) {
        this.carInfoRepository = carInfoRepository;
        this.carService = carService;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Save car info.
     *
     * @param carInfo the car info
     * @return car info
     */
    public CarInfo saveCarInfo(CarInfo carInfo) {
        if (carInfo.getId() != null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.carInfo.notHaveId", new Object[]{}));
        if (carInfo.getCar() == null || carInfo.getCar().getId() == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.carInfo.carIsNull", new Object[]{}));
        carInfo.setCar(carService.getById(carInfo.getCar().getId()));
        checkDuplicateCarInfo(carInfo.getCar().getId());
        return carInfoRepository.save(carInfo);
    }

    /**
     * Gets by car info id.
     *
     * @param id the car info id
     * @return the car info
     */
    @Override
    public CarInfo getById(Long id) {
        validate(id == null, "error.idIsNull");
        Optional<CarInfo> carInfoOptional = carInfoRepository.findById(id);
        if (!carInfoOptional.isPresent())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.carInfo.notFound", new Object[]{}));
        return carInfoOptional.get();
    }

    /**
     * Gets by car id.
     *
     * @param id the car id
     * @return the car info
     */
    @Override
    public CarInfo getByCarId(Long id) {
        validate(id == null, "error.carInfo.carIdIsNull");
        CarInfo carInfo = carInfoRepository.findByCarId(id);
        validate(carInfo == null, "error.carInfo.notFound");
        return carInfo;
    }

    /**
     * Update car info.
     *
     * @param carInfo the car info
     * @return the car info
     */
    @Override
    public CarInfo updateCarInfo(CarInfo carInfo) {
        if (carInfo.getId() == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.carInfo.haveId", new Object[]{}));
        if (carInfo.getCar() == null || carInfo.getCar().getId() == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.carInfo.carIsNull", new Object[]{}));
        carInfo.setCar(carService.getById(carInfo.getCar().getId()));
        return carInfoRepository.save(carInfo);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    public void deleteById(Long id) {
        getById(id);
        carInfoRepository.deleteById(id);
    }

    /**
     * Check duplicate in the database.
     *
     * @param carId the car id
     */
    private void checkDuplicateCarInfo(Long carId) {
        CarInfo carInfo = carInfoRepository.findByCarId(carId);
        validate(carInfo != null, "error.carInfo.notUnique");
    }

    private void validate(boolean expression, String messageCode) {
        if (expression) {
            String errorMessage = localizedMessageSource.getMessage(messageCode, new Object[]{});
            throw new NullPointerException(errorMessage);
        }
    }
}
