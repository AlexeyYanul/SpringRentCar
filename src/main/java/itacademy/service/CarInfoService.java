package itacademy.service;

import itacademy.model.CarInfo;
import itacademy.repository.CarInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Car info service.
 */
@Service
@Transactional
public class CarInfoService {

    private CarInfoRepository carInfoRepository;

    /**
     * Instantiates a new Car info service.
     *
     * @param carInfoRepository the car info repository
     */
    @Autowired
    public CarInfoService(CarInfoRepository carInfoRepository) {
        this.carInfoRepository = carInfoRepository;
    }

    /**
     * Save transient or update detached/persistent car info.
     *
     * @param carInfo the car info
     */
    public void saveCarInfo(CarInfo carInfo) {
        carInfoRepository.save(carInfo);
    }
}
