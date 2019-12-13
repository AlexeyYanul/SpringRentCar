package itacademy.controller;

import itacademy.component.LocalizedMessageSource;
import itacademy.dto.request.CarRequestDTO;
import itacademy.dto.response.CarResponseDTO;
import itacademy.model.Car;
import itacademy.model.CarModel;
import itacademy.model.Engine;
import itacademy.service.CarService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
public class CarController {

    private CarService carService;

    private Mapper mapper;

    private LocalizedMessageSource localizedMessageSource;

    public CarController(CarService carService, Mapper mapper, LocalizedMessageSource localizedMessageSource) {
        this.carService = carService;
        this.mapper = mapper;
        this.localizedMessageSource = localizedMessageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CarResponseDTO>> getAll() {
        List<Car> carList = carService.getAllCars();
        List<CarResponseDTO> carResponseDTOs = carList.stream()
                .map((car -> mapper.map(car, CarResponseDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(carResponseDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/free", method = RequestMethod.GET)
    public ResponseEntity<List<CarResponseDTO>> getAllFree() {
        List<Car> carList = carService.getAllFreeCars();
        List<CarResponseDTO> carResponseDTOs = carList.stream()
                .map((car -> mapper.map(car, CarResponseDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(carResponseDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"model"})
    public ResponseEntity<List<CarResponseDTO>> getByModelName(@RequestParam(value = "model") String modelName) {
        List<Car> carList = carService.getByCarModelName(modelName);
        List<CarResponseDTO> carResponseDTOs = carList.stream()
                .map(car -> mapper.map(car, CarResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(carResponseDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"year"})
    public ResponseEntity<List<CarResponseDTO>> getByModelYear(@RequestParam(value = "year") Integer modelYear) {
        List<Car> carList = carService.getByCarModelYear(modelYear);
        List<CarResponseDTO> carResponseDTOs = carList.stream()
                .map(car -> mapper.map(car, CarResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(carResponseDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"id"})
    public ResponseEntity<CarResponseDTO> getOne(@RequestParam Long id) {
        Car car = carService.getById(id);
        CarResponseDTO carResponseDTO = mapper.map(car, CarResponseDTO.class);
        return new ResponseEntity<>(carResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"body"})
    public ResponseEntity<List<CarResponseDTO>> getByBodyType(@RequestParam(value = "body") String bodyType) {
        List<Car> carList = carService.getByBody(bodyType);
        List<CarResponseDTO> carResponseDTOs = carList.stream()
                .map(car -> mapper.map(car, CarResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(carResponseDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"gearbox"})
    public ResponseEntity<List<CarResponseDTO>> getByGearboxType(@RequestParam(value = "gearbox") String gearboxType) {
        List<Car> carList = carService.getByGearbox(gearboxType);
        List<CarResponseDTO> carResponseDTOs = carList.stream()
                .map(car -> mapper.map(car, CarResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(carResponseDTOs, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CarResponseDTO> save(@Valid @RequestBody CarRequestDTO carRequestDTO) {
        carRequestDTO.setId(null);
        Car saveCar = carService.saveCar(getCar(carRequestDTO));
        CarResponseDTO carResponseDTO = mapper.map(saveCar, CarResponseDTO.class);
        return new ResponseEntity<>(carResponseDTO, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, params = {"id"})
    public ResponseEntity<CarResponseDTO> update(@Valid @RequestBody CarRequestDTO carRequestDTO,
                                                 @RequestParam Long id) {
        if (!Objects.equals(id, carRequestDTO.getId()))
            throw new NullPointerException(localizedMessageSource.getMessage("error.car.unexpectedId", new Object[]{}));
        Car updateCar = carService.updateCar(getCar(carRequestDTO));
        CarResponseDTO carResponseDTO = mapper.map(updateCar, CarResponseDTO.class);
        return new ResponseEntity<>(carResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Long id) {
        carService.deleteCar(id);
    }

    private Car getCar(CarRequestDTO carRequestDTO) {
        Car car = mapper.map(carRequestDTO, Car.class);
        CarModel carModel = new CarModel();
        carModel.setId(carRequestDTO.getCarModelId());
        car.setCarModel(carModel);
        Engine engine = new Engine();
        engine.setId(carRequestDTO.getEngineId());
        car.setEngine(engine);
        return car;
    }
}
