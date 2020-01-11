package itacademy.controller;

import itacademy.component.LocalizedMessageSource;
import itacademy.dto.UserDTO;
import itacademy.model.User;
import itacademy.model.enums.Role;
import itacademy.service.UserService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private Mapper mapper;

    private UserService userService;

    private LocalizedMessageSource localizedMessageSource;

    public UserController(Mapper mapper, UserService userService, LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.userService = userService;
        this.localizedMessageSource = localizedMessageSource;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getAll() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOList = users.stream()
                .map((user -> mapper.map(user, UserDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Long id) {
        userService.deleteById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO userDTO) {
        userDTO.setId(null);
        userDTO.getHomeAddress().setId(null);
        UserDTO responseUserDTO = mapper.map(
                userService.saveUser(mapper.map(userDTO, User.class)),
                UserDTO.class);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO userDTO, @RequestParam Long id) {
        if (!Objects.equals(id, userDTO.getId())) {
            throw new NullPointerException(localizedMessageSource.getMessage("error.user.unexpectedId", new Object[]{}));
        }
        UserDTO responseUserDTO = mapper.map(
                userService.updateUser(mapper.map(userDTO, User.class)),
                UserDTO.class);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"id"})
    public ResponseEntity<UserDTO> getOne(@RequestParam Long id) {
        User user = userService.getById(id);
        UserDTO responseUserDTO = mapper.map(user, UserDTO.class);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"login", "password"})
    public ResponseEntity<UserDTO> getByLoginAndPassword(@RequestParam String login,
                                                         @RequestParam String password) {
        User user = userService.getByLoginAndPassword(login, password);
        UserDTO responseUserDTO = mapper.map(user, UserDTO.class);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"lastName"})
    public ResponseEntity<List<UserDTO>> getByLastName(@RequestParam String lastName) {
        List<User> users = userService.getByLastName(lastName);
        List<UserDTO> userDTOList = users.stream()
                .map((user -> mapper.map(user, UserDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"city"})
    public ResponseEntity<List<UserDTO>> getByAddressCity(@RequestParam(name = "city") String cityName) {
        List<User> users = userService.getByCity(cityName);
        List<UserDTO> userDTOList = users.stream()
                .map((user -> mapper.map(user, UserDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = "street")
    public ResponseEntity<List<UserDTO>> getByAddressStreet(@RequestParam(name = "street") String streetName) {
        List<User> users = userService.getByStreet(streetName);
        List<UserDTO> userDTOList = users.stream()
                .map((user -> mapper.map(user, UserDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"city", "street"})
    public ResponseEntity<List<UserDTO>> getByAddressCityAndStreet(@RequestParam String city,
                                                                   @RequestParam String street) {
        List<User> users = userService.getByCityAndStreet(city, street);
        List<UserDTO> userDTOList = users.stream()
                .map((user -> mapper.map(user, UserDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = "role")
    public ResponseEntity<List<UserDTO>> getByRole(@RequestParam Role role) {
        List<User> users = userService.getByRole(role);
        List<UserDTO> userDTOList = users.stream()
                .map((user -> mapper.map(user, UserDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);

    }
}
