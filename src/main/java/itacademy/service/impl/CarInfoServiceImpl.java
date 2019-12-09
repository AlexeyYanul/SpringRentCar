package itacademy.service.impl;

import itacademy.model.CarInfo;
import itacademy.repository.CarInfoRepository;
import itacademy.service.CarInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "carInfoService")
@Transactional
public class CarInfoServiceImpl implements CarInfoService {

    private CarInfoRepository carInfoRepository;

    @Autowired
    public CarInfoServiceImpl(CarInfoRepository carInfoRepository) {
        this.carInfoRepository = carInfoRepository;
    }

    public CarInfo saveCarInfo(CarInfo carInfo) {
        return carInfoRepository.save(carInfo);
    }
}
