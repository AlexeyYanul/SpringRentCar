package itacademy.service.impl;

import itacademy.component.LocalizedMessageSource;
import itacademy.model.Car;
import itacademy.model.CarInfo;
import itacademy.model.RentCar;
import itacademy.model.User;
import itacademy.model.enums.RentCarStatus;
import itacademy.repository.RentCarRepository;
import itacademy.service.CarInfoService;
import itacademy.service.CarService;
import itacademy.service.RentCarService;
import itacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service(value = "rentCarService")
@Transactional
public class RentCarServiceImpl implements RentCarService {

    private final Sort finishDate = Sort.by("finishDate").descending();

    private RentCarRepository rentCarRepository;

    private CarInfoService carInfoService;

    private CarService carService;

    private UserService userService;

    private LocalizedMessageSource localizedMessageSource;

    @Autowired
    public RentCarServiceImpl(RentCarRepository rentCarRepository, CarInfoService carInfoService,
                              CarService carService, UserService userService,
                              LocalizedMessageSource localizedMessageSource) {
        this.rentCarRepository = rentCarRepository;
        this.carInfoService = carInfoService;
        this.carService = carService;
        this.userService = userService;
        this.localizedMessageSource = localizedMessageSource;
    }

    @Override
    public RentCar getById(Long id) {
        validate(id == null, "error.rent.haveId");
        Optional<RentCar> rentCar = rentCarRepository.findById(id);
        if (!rentCar.isPresent()) {
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.rent.notFound", new Object[]{}));
        }
        return rentCar.get();
    }

    @Override
    public RentCar requestRentCar(RentCar rentCar) {
        User renter = userService.getById(rentCar.getUser().getId());
        rentCar.setUser(renter);
        Long carId = rentCar.getCar().getId();
        Car car = carService.getById(carId);

        Boolean carStatus = carInfoService.getByCarId(carId).getStatus();
        validate(!carStatus, "error.rent.notAvailable");

        List<RentCar> rentedCarList = rentCarRepository.findByCarId(carId);
        int rentCountByCar = rentedCarList.size();
        if (rentCountByCar != 0) {
            LocalDateTime orderStartDate = rentCar.getStartDate();
            LocalDateTime orderedFinishDate = rentCar.getFinishDate();
            Integer rentCountByDate = rentCarRepository.countByDateMyPeriod(carId, orderStartDate, orderedFinishDate);
            if (rentCountByCar == rentCountByDate) {
                rentCar.setCar(car);
            } else {
                throw new NullPointerException(localizedMessageSource.getMessage("error.rent.notAvailable", new Object[]{}));
            }
        } else {
            rentCar.setCar(car);
        }
        rentCar.setStatus(RentCarStatus.REQUEST);
        BigDecimal bill = countBill(rentCar);
        rentCar.setBill(bill);
        return rentCarRepository.save(rentCar);
    }

    @Override
    public RentCar acceptRequest(Long id) {
        RentCar rentRequest = getById(id);
        rentRequest.setStatus(RentCarStatus.ACTIVE);
        return rentCarRepository.save(rentRequest);
    }

    @Override
    public RentCar finishRentCar(Long id) {
        RentCar rentRequest = getById(id);
        rentRequest.setStatus(RentCarStatus.END);
        return rentCarRepository.save(rentRequest);
    }

    @Override
    public RentCar cancelRequest(Long id) {
        RentCar rentRequest = getById(id);
        rentRequest.setStatus(RentCarStatus.CANCELED);
        return rentCarRepository.save(rentRequest);
    }

    @Override
    public List<RentCar> getByStatus(String status) {
        List<RentCar> rentCars;
        try {
            RentCarStatus rentCarStatus = RentCarStatus.valueOf(status);
            rentCars = rentCarRepository.findByStatus(rentCarStatus, finishDate);
        } catch (IllegalArgumentException e) {
            throw new NullPointerException(localizedMessageSource.getMessage("error.rent.unexpectedStatus", new Object[]{}));
        }
        if (rentCars.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.rent.notFound", new Object[]{}));
        return rentCars;
    }

    @Override
    public List<RentCar> getByCarId(Long carId) {
        validate(carId == null, "error.rent.unexpectedCarId");
        List<RentCar> rentCars = rentCarRepository.findByCarId(carId, finishDate);
        if (rentCars.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.rent.notFound", new Object[]{}));
        return rentCars;
    }

    @Override
    public List<RentCar> getByUserId(Long userId) {
        validate(userId == null, "error.rent.unexpectedUserId");
        List<RentCar> rentCars = rentCarRepository.findByUserId(userId, finishDate);
        if (rentCars.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.rent.notFound", new Object[]{}));
        return rentCars;
    }

    @Override
    public List<RentCar> getAll() {
        return rentCarRepository.findAll();
    }

    private BigDecimal countBill(RentCar rentCar) {
        LocalDateTime startDate = rentCar.getStartDate();
        LocalDateTime finishDate = rentCar.getFinishDate();
        Duration duration = Duration.between(startDate, finishDate);
        double rentTime = (double) duration.getSeconds() / 3600;

        Long carId = rentCar.getCar().getId();
        CarInfo carInfo = carInfoService.getByCarId(carId);
        BigDecimal hourPrice = carInfo.getHourPrice();

        double r = hourPrice.doubleValue() * rentTime;
        return BigDecimal.valueOf(r);

    }

    private void validate(boolean expression, String messageCode) {
        if (expression) {
            String errorMessage = localizedMessageSource.getMessage(messageCode, new Object[]{});
            throw new NullPointerException(errorMessage);
        }
    }
}
