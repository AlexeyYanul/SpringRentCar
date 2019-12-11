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

@RestController
@RequestMapping(value = "/engines")
public class EngineController {

    private Mapper mapper;

    private EngineService engineService;

    private LocalizedMessageSource localizedMessageSource;

    public EngineController(Mapper mapper, EngineService engineService, LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.engineService = engineService;
        this.localizedMessageSource = localizedMessageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EngineDTO>> getAll(){
        List<Engine> engines = engineService.getEnginesList();
        List<EngineDTO> engineDTOs = engines.stream()
                .map((engine -> mapper.map(engine, EngineDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(engineDTOs, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"id"})
    public ResponseEntity<EngineDTO> getOne(@RequestParam(name = "id") Long engineId){
        Engine engine = engineService.getById(engineId);
        EngineDTO engineDTO = mapper.map(engine, EngineDTO.class);
        return new ResponseEntity<>(engineDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<EngineDTO> save(@Valid @RequestBody EngineDTO engineDTO){
        engineDTO.setId(null);
        EngineDTO responseEngineDTO = mapper.map(
                engineService.saveEngine(mapper.map(engineDTO, Engine.class)),
                EngineDTO.class);
        return new ResponseEntity<>(responseEngineDTO, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, params = {"id"})
    public ResponseEntity<EngineDTO> update(@Valid @RequestBody EngineDTO engineDTO, @RequestParam Long id){
        if (!Objects.equals(engineDTO.getId(), id)){
            throw new NullPointerException(localizedMessageSource.getMessage("error.engine.unexpectedId", new Object[]{}));
        }
        EngineDTO responseEngineDTO = mapper.map(
                engineService.updateEngine(mapper.map(engineDTO, Engine.class)),
                EngineDTO.class);
        return new ResponseEntity<>(responseEngineDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, params = {"id"})
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Long id){
        engineService.deleteEngien(id);
    }


}
