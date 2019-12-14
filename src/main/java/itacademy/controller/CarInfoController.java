package itacademy.controller;

import itacademy.component.LocalizedMessageSource;
import itacademy.dto.request.CarInfoRequestDTO;
import itacademy.dto.response.CarInfoResponseDTO;
import itacademy.model.Car;
import itacademy.model.CarInfo;
import itacademy.service.CarInfoService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/carInfo")
public class CarInfoController {

    private CarInfoService carInfoService;

    private Mapper mapper;

    private LocalizedMessageSource localizedMessageSource;

    public CarInfoController(CarInfoService carInfoService, Mapper mapper, LocalizedMessageSource localizedMessageSource) {
        this.carInfoService = carInfoService;
        this.mapper = mapper;
        this.localizedMessageSource = localizedMessageSource;
    }

    @RequestMapping(method = RequestMethod.GET, params = {"id"})
    public ResponseEntity<CarInfoResponseDTO> getOne(@RequestParam Long id) {
        CarInfo carInfo = carInfoService.getById(id);
        CarInfoResponseDTO carInfoResponseDTO = mapper.map(carInfo, CarInfoResponseDTO.class);
        return new ResponseEntity<>(carInfoResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"carId"})
    public ResponseEntity<CarInfoResponseDTO> getByCarId(@RequestParam Long carId) {
        CarInfo carInfo = carInfoService.getByCarId(carId);
        CarInfoResponseDTO carInfoResponseDTO = mapper.map(carInfo, CarInfoResponseDTO.class);
        return new ResponseEntity<>(carInfoResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, params = {"id"})
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Long id) {
        carInfoService.deleteById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CarInfoResponseDTO> save(@Valid @RequestBody CarInfoRequestDTO carInfoRequestDTO) {
        carInfoRequestDTO.setId(null);
        CarInfo saveCarInfo = carInfoService.saveCarInfo(getCarInfo(carInfoRequestDTO));
        CarInfoResponseDTO carInfoResponseDTO = mapper.map(saveCarInfo, CarInfoResponseDTO.class);
        return new ResponseEntity<>(carInfoResponseDTO, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, params = {"id"})
    public ResponseEntity<CarInfoResponseDTO> update(@Valid @RequestBody CarInfoRequestDTO carInfoRequestDTO,
                                                     @RequestParam Long id) {
        if (!Objects.equals(id, carInfoRequestDTO.getId()))
            throw new NullPointerException(localizedMessageSource.getMessage("error.carInfo.unexpectedId", new Object[]{}));
        CarInfo saveCarInfo = carInfoService.updateCarInfo(getCarInfo(carInfoRequestDTO));
        CarInfoResponseDTO carInfoResponseDTO = mapper.map(saveCarInfo, CarInfoResponseDTO.class);
        return new ResponseEntity<>(carInfoResponseDTO, HttpStatus.OK);
    }

    private CarInfo getCarInfo(CarInfoRequestDTO carInfoRequestDTO) {
        CarInfo carInfo = mapper.map(carInfoRequestDTO, CarInfo.class);
        Car car = new Car();
        car.setId(carInfoRequestDTO.getCarId());
        carInfo.setCar(car);
        return carInfo;
    }
}