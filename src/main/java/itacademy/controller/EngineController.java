package itacademy.controller;

import itacademy.component.LocalizedMessageSource;
import itacademy.dto.EngineDTO;
import itacademy.model.Engine;
import itacademy.service.EngineService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Engine controller.
 */
@RestController
@RequestMapping(value = "/engines")
public class EngineController {

    private Mapper mapper;

    private EngineService engineService;

    private LocalizedMessageSource localizedMessageSource;

    /**
     * Instantiates a new Engine controller.
     *
     * @param mapper                 the mapper
     * @param engineService          the engine service
     * @param localizedMessageSource the localized message source
     */
    public EngineController(Mapper mapper, EngineService engineService, LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.engineService = engineService;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EngineDTO>> getAll() {
        List<Engine> engines = engineService.getEnginesList();
        List<EngineDTO> engineDTOs = engines.stream()
                .map((engine -> mapper.map(engine, EngineDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(engineDTOs, HttpStatus.OK);
    }

    /**
     * Gets one.
     *
     * @param engineId the engine id
     * @return the one
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"id"})
    public ResponseEntity<EngineDTO> getOne(@RequestParam(name = "id") Long engineId) {
        Engine engine = engineService.getById(engineId);
        EngineDTO engineDTO = mapper.map(engine, EngineDTO.class);
        return new ResponseEntity<>(engineDTO, HttpStatus.OK);
    }

    /**
     * Save response entity.
     *
     * @param engineDTO the engine dto
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<EngineDTO> save(@Valid @RequestBody EngineDTO engineDTO) {
        engineDTO.setId(null);
        EngineDTO responseEngineDTO = mapper.map(
                engineService.saveEngine(mapper.map(engineDTO, Engine.class)),
                EngineDTO.class);
        return new ResponseEntity<>(responseEngineDTO, HttpStatus.CREATED);
    }

    /**
     * Update response entity.
     *
     * @param engineDTO the engine dto
     * @param id        the id
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.PUT, params = {"id"})
    public ResponseEntity<EngineDTO> update(@Valid @RequestBody EngineDTO engineDTO, @RequestParam Long id) {
        if (!Objects.equals(engineDTO.getId(), id)) {
            throw new NullPointerException(localizedMessageSource.getMessage("error.engine.unexpectedId", new Object[]{}));
        }
        EngineDTO responseEngineDTO = mapper.map(
                engineService.updateEngine(mapper.map(engineDTO, Engine.class)),
                EngineDTO.class);
        return new ResponseEntity<>(responseEngineDTO, HttpStatus.OK);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, params = {"id"})
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Long id) {
        engineService.deleteEngien(id);
    }


}
