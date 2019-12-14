package itacademy.controller;

import itacademy.component.LocalizedMessageSource;
import itacademy.dto.response.UserFinesResponseDTO;
import itacademy.model.UserFines;
import itacademy.service.UserFinesService;
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
@RequestMapping("/userFines")
public class UserFinesController {

    private UserFinesService userFinesService;

    private Mapper mapper;

    private LocalizedMessageSource localizedMessageSource;

    public UserFinesController(UserFinesService userFinesService, Mapper mapper,
                               LocalizedMessageSource localizedMessageSource) {
        this.userFinesService = userFinesService;
        this.mapper = mapper;
        this.localizedMessageSource = localizedMessageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserFinesResponseDTO>> getAll() {
        List<UserFines> userFinesList = userFinesService.getAllFines();
        List<UserFinesResponseDTO> userFinesResponseDTOs = userFinesList.stream()
                .map(userFines -> mapper.map(userFines, UserFinesResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userFinesResponseDTOs, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"id"})
    public ResponseEntity<UserFinesResponseDTO> getOne(@RequestParam Long id) {
        UserFines userFines = userFinesService.getById(id);
        UserFinesResponseDTO finesResponseDTO = mapper.map(userFines, UserFinesResponseDTO.class);
        return new ResponseEntity<>(finesResponseDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"status"})
    public ResponseEntity<List<UserFinesResponseDTO>> getAllByStatus(@RequestParam Boolean status) {
        List<UserFines> finesByStatus = userFinesService.getFinesByStatus(status);
        List<UserFinesResponseDTO> finesResponseDTOs = finesByStatus.stream()
                .map(userFines -> mapper.map(userFines, UserFinesResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(finesResponseDTOs, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"userId"})
    public ResponseEntity<List<UserFinesResponseDTO>> getByUserId(@RequestParam Long userId) {
        List<UserFines> finesByUserId = userFinesService.getFinesByUserId(userId);
        List<UserFinesResponseDTO> finesResponseDTOs = finesByUserId.stream()
                .map(finesByUser -> mapper.map(finesByUser, UserFinesResponseDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(finesResponseDTOs, HttpStatus.OK);
    }
}
