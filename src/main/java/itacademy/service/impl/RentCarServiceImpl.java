package itacademy.service.impl;

import itacademy.model.Car;
import itacademy.model.CarInfo;
import itacademy.model.RentCar;
import itacademy.model.User;
import itacademy.model.enums.RentCarStatus;
import itacademy.repository.RentCarRepository;
import itacademy.service.CarInfoService;
import itacademy.service.RentCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service(value = "rentCarService")
@Transactional
public class RentCarServiceImpl implements RentCarService {

    private final Sort finishDate = Sort.by("finishDate").descending();

    private RentCarRepository rentCarRepository;
    private CarInfoService carInfoService;

    @Autowired
    public RentCarServiceImpl(RentCarRepository rentCarRepository, CarInfoService carInfoService) {
        this.rentCarRepository = rentCarRepository;
        this.carInfoService = carInfoService;
    }

    public RentCar getById(Long id) {
        Optional<RentCar> rentCar = rentCarRepository.findById(id);
        if (!rentCar.isPresent()) {
            return null;
        }
        return rentCar.get();
    }

    public void requestRentCar(RentCar rentCar) {
        CarInfo carInfo = rentCar.getCar().getCarInfo();
        if (carInfo.getStatus()) {
            rentCarRepository.save(rentCar);
        } else {
            System.out.println("car already rented");
        }
    }

    public void acceptRequest(RentCar rentCar) {
        CarInfo carInfo = rentCar.getCar().getCarInfo();
        carInfo.setStatus(false);
        carInfoService.saveCarInfo(carInfo);
        rentCar.setStatus(RentCarStatus.ACTIVE);
        rentCarRepository.save(rentCar);
    }

    public void finishRentCar(RentCar rentCar) {
        CarInfo carInfo = rentCar.getCar().getCarInfo();
        carInfo.setStatus(true);
        carInfoService.saveCarInfo(carInfo);
        rentCar.setStatus(RentCarStatus.END);
        rentCarRepository.save(rentCar);
    }

    public void cancelRequest(RentCar rentCar) {
        rentCar.setStatus(RentCarStatus.CANCELED);
        rentCarRepository.save(rentCar);
    }

    public List<RentCar> getByStatus(RentCarStatus status) {
        return rentCarRepository.findByStatus(status, finishDate);
    }

    public List<RentCar> getByCar(Car car) {
        return rentCarRepository.findByCar(car, finishDate);
    }

    public List<RentCar> getByUser(User user) {
        return rentCarRepository.findByUser(user, finishDate);
    }


}
