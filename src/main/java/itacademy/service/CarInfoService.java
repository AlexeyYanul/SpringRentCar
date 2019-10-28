package itacademy.service;

import itacademy.model.Car;
import itacademy.model.CarInfo;
import itacademy.repository.CarInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CarInfoService {

    private CarInfoRepository carInfoRepository;

    @Autowired
    public CarInfoService(CarInfoRepository carInfoRepository) {
        this.carInfoRepository = carInfoRepository;
    }

    public CarInfo findByCar(Car car){
        return carInfoRepository.findByCar(car);
    }

    public void saveCarInfo(CarInfo carInfo){
        carInfoRepository.save(carInfo);
    }
}
