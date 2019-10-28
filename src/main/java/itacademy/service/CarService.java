package itacademy.service;

import itacademy.model.Car;
import itacademy.model.CarInfo;
import itacademy.model.CarModel;
import itacademy.model.enums.Body;
import itacademy.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarService {

    private CarRepository carRepository;
    private CarInfoService carInfoService;

    @Autowired
    public CarService(CarRepository carRepository, CarInfoService carInfoService) {
        this.carRepository = carRepository;
    }

    public Car findById(Long id) {
        Optional<Car> car = carRepository.findById(id);
        if (!car.isPresent()) {
            return null;
        }
        return car.get();
    }

    public List<Car> findByCarModelName(String name) {
        return carRepository.findByCarModelName(name);
    }

    public List<Car> findByBody(Body body) {
        return carRepository.findByBody(body);
    }

    public List<Car> findAllFreeCars(){
        return carRepository.findByCarInfoIsFree(true);
    }

    public List<Car> getAllCars(){
        return carRepository.findAll();
    }
    

    public List<Body> getBodyTypes(){
        return carRepository.getBodyTypes();
    }

    public void saveCar(Car car){
        carRepository.save(car);
    }
}
