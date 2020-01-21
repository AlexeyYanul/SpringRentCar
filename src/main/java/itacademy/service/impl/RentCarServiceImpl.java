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

/**
 * The type Rent car service.
 */
@Service(value = "rentCarService")
@Transactional
public class RentCarServiceImpl implements RentCarService {

    private final Sort finishDate = Sort.by("finishDate").descending();

    private RentCarRepository rentCarRepository;

    private CarInfoService carInfoService;

    private CarService carService;

    private UserService userService;

    private LocalizedMessageSource localizedMessageSource;

    /**
     * Instantiates a new Rent car service.
     *
     * @param rentCarRepository      the rent car repository
     * @param carInfoService         the car info service
     * @param carService             the car service
     * @param userService            the user service
     * @param localizedMessageSource the localized message source
     */
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

    /**
     * Gets by id.
     *
     * @param id the rent id
     * @return the rent
     */
    @Override
    public RentCar getById(Long id) {
        validate(id == null, "error.rent.haveId");
        Optional<RentCar> rentCar = rentCarRepository.findById(id);
        validate(!rentCar.isPresent(), "error.rent.notFound");
        return rentCar.get();
    }

    /**
     * Save request rent car.
     *
     * @param rentCar the rent request
     * @return the rent
     */
    @Override
    public RentCar requestRentCar(RentCar rentCar) {
        User renter = userService.getById(rentCar.getUser().getId());
        rentCar.setUser(renter);
        Long carId = rentCar.getCar().getId();
        Car car = carService.getById(carId);

        Boolean carStatus = carInfoService.getByCarId(carId).getStatus();
        validate(!carStatus, "error.rent.notAvailable");

        List<RentCar> rentedCarList = rentCarRepository.findByCarIdAndStatus(carId, RentCarStatus.ACTIVE);
        int rentCountByCar = rentedCarList.size();
        if (rentCountByCar != 0) {
            LocalDateTime orderStartDate = rentCar.getStartDate();
            LocalDateTime orderedFinishDate = rentCar.getFinishDate();
            Integer rentCountByDate = rentCarRepository.countByDateMyPeriod(carId, orderStartDate, orderedFinishDate);
            if (rentCountByCar == rentCountByDate) {
                rentCar.setCar(car);
            } else {
                throw new RuntimeException(localizedMessageSource.getMessage("error.rent.notAvailable", new Object[]{}));
            }
        } else {
            rentCar.setCar(car);
        }
        rentCar.setStatus(RentCarStatus.REQUEST);
        BigDecimal bill = countBill(rentCar);
        rentCar.setBill(bill);
        return rentCarRepository.save(rentCar);
    }

    /**
     * Accept rent car.
     *
     * @param id the rent id
     * @return the rent
     */
    @Override
    public RentCar acceptRequest(Long id) {
        RentCar rentRequest = getById(id);
        rentRequest.setStatus(RentCarStatus.ACTIVE);
        return rentCarRepository.save(rentRequest);
    }

    /**
     * Finish rent car.
     *
     * @param id the rent id
     * @return the rent
     */
    @Override
    public RentCar finishRentCar(Long id) {
        RentCar rentRequest = getById(id);
        rentRequest.setStatus(RentCarStatus.END);
        return rentCarRepository.save(rentRequest);
    }

    /**
     * Cancel rent car.
     *
     * @param id the rent id
     * @return the rent
     */
    @Override
    public RentCar cancelRequest(Long id) {
        RentCar rentRequest = getById(id);
        rentRequest.setStatus(RentCarStatus.CANCELED);
        return rentCarRepository.save(rentRequest);
    }

    /**
     * Gets by status.
     *
     * @param status the rent status
     * @return the rents list
     */
    @Override
    public List<RentCar> getByStatus(String status) {
        List<RentCar> rentCars;
        try {
            RentCarStatus rentCarStatus = RentCarStatus.valueOf(status);
            rentCars = rentCarRepository.findByStatus(rentCarStatus, finishDate);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(localizedMessageSource.getMessage("error.rent.unexpectedStatus", new Object[]{}));
        }
        validate(rentCars.isEmpty(), "error.rent.notFound");
        return rentCars;
    }

    /**
     * Gets by car id.
     *
     * @param carId the rented car id
     * @return the rents list
     */
    @Override
    public List<RentCar> getByCarId(Long carId) {
        validate(carId == null, "error.rent.unexpectedCarId");
        List<RentCar> rentCars = rentCarRepository.findByCarId(carId, finishDate);
        validate(rentCars.isEmpty(), "error.rent.notFound");
        return rentCars;
    }

    /**
     * Gets by user id.
     *
     * @param userId the renter id
     * @return the rents list
     */
    @Override
    public List<RentCar> getByUserId(Long userId) {
        validate(userId == null, "error.rent.unexpectedUserId");
        List<RentCar> rentCars = rentCarRepository.findByUserId(userId, finishDate);
        validate(rentCars.isEmpty(), "error.rent.notFound");
        return rentCars;
    }

    /**
     * Gets all.
     *
     * @return the rents list
     */
    @Override
    public List<RentCar> getAll() {
        return rentCarRepository.findAll();
    }

    /**
     * Count bill.
     *
     * @return the rent bill
     */
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
            throw new RuntimeException(errorMessage);
        }
    }
}
