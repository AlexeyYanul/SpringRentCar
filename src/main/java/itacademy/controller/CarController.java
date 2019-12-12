package itacademy.controller;

import itacademy.component.LocalizedMessageSource;
import itacademy.dto.request.CarRequestDTO;
import itacademy.dto.response.CarResponseDTO;
import itacademy.model.Car;
import itacademy.model.enums.Body;
import itacademy.service.CarService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
}
