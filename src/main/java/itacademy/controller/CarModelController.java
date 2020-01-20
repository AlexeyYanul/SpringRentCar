package itacademy.controller;

import itacademy.component.LocalizedMessageSource;
import itacademy.dto.CarModelDTO;
import itacademy.model.CarModel;
import itacademy.service.CarModelService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Car model controller.
 */
@RestController
@RequestMapping("/carModels")
public class CarModelController {

    private Mapper mapper;

    private CarModelService carModelService;

    private LocalizedMessageSource localizedMessageSource;

    /**
     * Instantiates a new Car model controller.
     *
     * @param mapper                 the mapper
     * @param carModelService        the car model service
     * @param localizedMessageSource the localized message source
     */
    public CarModelController(Mapper mapper, CarModelService carModelService, LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.carModelService = carModelService;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CarModelDTO>> getAll() {
        List<CarModel> carModels = carModelService.getCarModels();
        List<CarModelDTO> carModelDTOs = carModels.stream()
                .map((carModel -> mapper.map(carModel, CarModelDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(carModelDTOs, HttpStatus.OK);
    }

    /**
     * Gets one.
     *
     * @param id the id
     * @return the one
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"id"})
    public ResponseEntity<CarModelDTO> getOne(@RequestParam Long id) {
        CarModel carModel = carModelService.getById(id);
        CarModelDTO carModelDTO = mapper.map(carModel, CarModelDTO.class);
        return new ResponseEntity<>(carModelDTO, HttpStatus.OK);
    }

    /**
     * Save response entity.
     *
     * @param carModelDTO the car model dto
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CarModelDTO> save(@Valid @RequestBody CarModelDTO carModelDTO) {
        carModelDTO.setId(null);
        CarModelDTO responseCarModelDTO = mapper.map(
                carModelService.saveCarModel(mapper.map(carModelDTO, CarModel.class)),
                CarModelDTO.class);
        return new ResponseEntity<>(responseCarModelDTO, HttpStatus.CREATED);
    }

    /**
     * Update response entity.
     *
     * @param carModelDTO the car model dto
     * @param id          the id
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.PUT, params = {"id"})
    public ResponseEntity<CarModelDTO> update(@Valid @RequestBody CarModelDTO carModelDTO, @RequestParam Long id) {
        if (!Objects.equals(carModelDTO.getId(), id))
            throw new NullPointerException(localizedMessageSource.getMessage("error.carModel.unexpectedId", new Object[]{}));
        CarModelDTO responseCarModelDTO = mapper.map(
                carModelService.updateCarModel(mapper.map(carModelDTO, CarModel.class)),
                CarModelDTO.class);
        return new ResponseEntity<>(responseCarModelDTO, HttpStatus.OK);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, params = {"id"})
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Long id) {
        carModelService.delete(id);
    }
}
