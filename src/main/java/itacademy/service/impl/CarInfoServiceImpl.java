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

@Service(value = "carInfoService")
@Transactional
public class CarInfoServiceImpl implements CarInfoService {

    private CarInfoRepository carInfoRepository;

    private CarService carService;

    private LocalizedMessageSource localizedMessageSource;

    @Autowired
    public CarInfoServiceImpl(CarInfoRepository carInfoRepository, CarService carService,
                              LocalizedMessageSource localizedMessageSource) {
        this.carInfoRepository = carInfoRepository;
        this.carService = carService;
        this.localizedMessageSource = localizedMessageSource;
    }

    public CarInfo saveCarInfo(CarInfo carInfo) {
        if (carInfo.getId() != null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.carInfo.notHaveId", new Object[]{}));
        if (carInfo.getCar() == null || carInfo.getCar().getId() == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.carInfo.carIsNull", new Object[]{}));
        carInfo.setCar(carService.getById(carInfo.getCar().getId()));
        checkDuplicateCarInfo(carInfo.getCar().getId());
        return carInfoRepository.save(carInfo);
    }

    @Override
    public CarInfo getById(Long id) {
        if (id == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.idIsNull", new Object[]{}));
        Optional<CarInfo> carInfoOptional = carInfoRepository.findById(id);
        if (!carInfoOptional.isPresent())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.carInfo.notFound", new Object[]{}));
        return carInfoOptional.get();
    }

    @Override
    public CarInfo getByCarId(Long id) {
        if (id == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.carInfo.carIdIsNull", new Object[]{}));
        CarInfo carInfo = carInfoRepository.findByCarId(id);
        if (carInfo == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.carInfo.notFound", new Object[]{}));
        return carInfo;
    }

    @Override
    public CarInfo updateCarInfo(CarInfo carInfo) {
        if (carInfo.getId() == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.carInfo.haveId", new Object[]{}));
        if (carInfo.getCar() == null || carInfo.getCar().getId() == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.carInfo.carIsNull", new Object[]{}));
        carInfo.setCar(carService.getById(carInfo.getCar().getId()));
        return carInfoRepository.save(carInfo);
    }

    private void checkDuplicateCarInfo(Long carId) {
        CarInfo carInfo = carInfoRepository.findByCarId(carId);
        if (carInfo != null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.carInfo.notUnique", new Object[]{}));
    }


    public void deleteById(Long id) {
        getById(id);
        carInfoRepository.deleteById(id);
    }
}
