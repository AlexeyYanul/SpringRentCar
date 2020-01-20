package itacademy.controller;

import itacademy.component.LocalizedMessageSource;
import itacademy.dto.request.RentRequestDTO;
import itacademy.dto.response.RentResponseDTO;
import itacademy.model.Car;
import itacademy.model.RentCar;
import itacademy.model.User;
import itacademy.service.RentCarService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Rent car controller.
 */
@RestController
@RequestMapping("/rentCar")
public class RentCarController {

    private RentCarService rentCarService;

    private Mapper mapper;

    /**
     * Instantiates a new Rent car controller.
     *
     * @param rentCarService         the rent car service
     * @param mapper                 the mapper
     * @param localizedMessageSource the localized message source
     */
    public RentCarController(RentCarService rentCarService, Mapper mapper, LocalizedMessageSource localizedMessageSource) {
        this.rentCarService = rentCarService;
        this.mapper = mapper;
    }

    /**
     * Gets one.
     *
     * @param id the id
     * @return the one
     */
    @RequestMapping(method = RequestMethod.GET, params = {"id"})
    public ResponseEntity<RentResponseDTO> getOne(@RequestParam Long id) {
        RentCar rentCar = rentCarService.getById(id);
        RentResponseDTO rentResponseDTO = mapper.map(rentCar, RentResponseDTO.class);
        return new ResponseEntity<>(rentResponseDTO, HttpStatus.OK);
    }

    /**
     * Gets by status.
     *
     * @param status the status
     * @return the by status
     */
    @RequestMapping(method = RequestMethod.GET, params = {"status"})
    public ResponseEntity<List<RentResponseDTO>> getByStatus(@RequestParam String status) {
        List<RentCar> rentCars = rentCarService.getByStatus(status);
        List<RentResponseDTO> rentResponseDTOs = rentCars.stream()
                .map(rentCar -> mapper.map(rentCar, RentResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(rentResponseDTOs, HttpStatus.OK);
    }

    /**
     * Gets by car id.
     *
     * @param carId the car id
     * @return the by car id
     */
    @RequestMapping(method = RequestMethod.GET, params = {"carId"})
    public ResponseEntity<List<RentResponseDTO>> getByCarId(@RequestParam Long carId) {
        List<RentCar> rentCars = rentCarService.getByCarId(carId);
        List<RentResponseDTO> rentResponseDTOs = rentCars.stream()
                .map(rentCar -> mapper.map(rentCar, RentResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(rentResponseDTOs, HttpStatus.OK);
    }

    /**
     * Gets by user id.
     *
     * @param userId the user id
     * @return the by user id
     */
    @RequestMapping(method = RequestMethod.GET, params = {"userId"})
    public ResponseEntity<List<RentResponseDTO>> getByUserId(@RequestParam Long userId) {
        List<RentCar> rentCars = rentCarService.getByUserId(userId);
        List<RentResponseDTO> rentResponseDTOs = rentCars.stream()
                .map(rentCar -> mapper.map(rentCar, RentResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(rentResponseDTOs, HttpStatus.OK);
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<RentResponseDTO>> getAll() {
        List<RentCar> rentCars = rentCarService.getAll();
        List<RentResponseDTO> rentResponseDTOs = rentCars.stream()
                .map(rentCar -> mapper.map(rentCar, RentResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(rentResponseDTOs, HttpStatus.OK);
    }

    /**
     * Request rent car response entity.
     *
     * @param rentRequestDTO the rent request dto
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RentResponseDTO> requestRentCar(@Valid @RequestBody RentRequestDTO rentRequestDTO) {
        rentRequestDTO.setId(null);
        RentCar rentCar = rentCarService.requestRentCar(getRent(rentRequestDTO));
        RentResponseDTO rentResponseDTO = mapper.map(rentCar, RentResponseDTO.class);
        return new ResponseEntity<>(rentResponseDTO, HttpStatus.CREATED);
    }

    /**
     * Accept rent request response entity.
     *
     * @param rentId the rent id
     * @return the response entity
     */
    @RequestMapping(value = "/accept", method = RequestMethod.PUT, params = {"rentId"})
    public ResponseEntity<RentResponseDTO> acceptRentRequest(@RequestParam Long rentId) {
        RentCar rentCar = rentCarService.acceptRequest(rentId);
        RentResponseDTO rentResponseDTO = mapper.map(rentCar, RentResponseDTO.class);
        return new ResponseEntity<>(rentResponseDTO, HttpStatus.OK);
    }

    /**
     * Finish rent request response entity.
     *
     * @param rentId the rent id
     * @return the response entity
     */
    @RequestMapping(value = "/finish", method = RequestMethod.PUT, params = {"rentId"})
    public ResponseEntity<RentResponseDTO> finishRentRequest(@RequestParam Long rentId) {
        RentCar rentCar = rentCarService.finishRentCar(rentId);
        RentResponseDTO rentResponseDTO = mapper.map(rentCar, RentResponseDTO.class);
        return new ResponseEntity<>(rentResponseDTO, HttpStatus.OK);
    }

    /**
     * Cancel rent request response entity.
     *
     * @param rentId the rent id
     * @return the response entity
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.PUT, params = {"rentId"})
    public ResponseEntity<RentResponseDTO> cancelRentRequest(@RequestParam Long rentId) {
        RentCar rentCar = rentCarService.cancelRequest(rentId);
        RentResponseDTO rentResponseDTO = mapper.map(rentCar, RentResponseDTO.class);
        return new ResponseEntity<>(rentResponseDTO, HttpStatus.OK);
    }

    private RentCar getRent(RentRequestDTO rentRequestDTO) {
        RentCar rentCar = mapper.map(rentRequestDTO, RentCar.class);
        User user = new User();
        user.setId(rentRequestDTO.getUserId());
        rentCar.setUser(user);
        Car car = new Car();
        car.setId(rentRequestDTO.getCarId());
        rentCar.setCar(car);
        return rentCar;
    }
}
