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

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private Mapper mapper;

    private UserService userService;

    private LocalizedMessageSource localizedMessageSource;

    /**
     * Instantiates a new User controller.
     *
     * @param mapper                 the mapper
     * @param userService            the user service
     * @param localizedMessageSource the localized message source
     */
    public UserController(Mapper mapper, UserService userService, LocalizedMessageSource localizedMessageSource) {
        this.mapper = mapper;
        this.userService = userService;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getAll() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOList = users.stream()
                .map((user -> mapper.map(user, UserDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam Long id) {
        userService.deleteById(id);
    }

    /**
     * Save response entity.
     *
     * @param userDTO the user dto
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDTO> save(@Valid @RequestBody UserDTO userDTO) {
        userDTO.setId(null);
        userDTO.getHomeAddress().setId(null);
        UserDTO responseUserDTO = mapper.map(
                userService.saveUser(mapper.map(userDTO, User.class)),
                UserDTO.class);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.OK);
    }

    /**
     * Update response entity.
     *
     * @param userDTO the user dto
     * @param id      the id
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO userDTO, @RequestParam Long id) {
        if (!Objects.equals(id, userDTO.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("error.user.unexpectedId", new Object[]{}));
        }
        UserDTO responseUserDTO = mapper.map(
                userService.updateUser(mapper.map(userDTO, User.class)),
                UserDTO.class);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.OK);
    }

    /**
     * Gets one.
     *
     * @param id the id
     * @return the one
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"id"})
    public ResponseEntity<UserDTO> getOne(@RequestParam Long id) {
        User user = userService.getById(id);
        UserDTO responseUserDTO = mapper.map(user, UserDTO.class);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.OK);
    }

    /**
     * Gets by login and password.
     *
     * @param login    the login
     * @param password the password
     * @return the by login and password
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"login", "password"})
    public ResponseEntity<UserDTO> getByLoginAndPassword(@RequestParam String login,
                                                         @RequestParam String password) {
        User user = userService.getByLoginAndPassword(login, password);
        UserDTO responseUserDTO = mapper.map(user, UserDTO.class);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.OK);
    }

    /**
     * Gets by last name.
     *
     * @param lastName the last name
     * @return the by last name
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"lastName"})
    public ResponseEntity<List<UserDTO>> getByLastName(@RequestParam String lastName) {
        List<User> users = userService.getByLastName(lastName);
        List<UserDTO> userDTOList = users.stream()
                .map((user -> mapper.map(user, UserDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    /**
     * Gets by address city.
     *
     * @param cityName the city name
     * @return the by address city
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"city"})
    public ResponseEntity<List<UserDTO>> getByAddressCity(@RequestParam(name = "city") String cityName) {
        List<User> users = userService.getByCity(cityName);
        List<UserDTO> userDTOList = users.stream()
                .map((user -> mapper.map(user, UserDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    /**
     * Gets by address street.
     *
     * @param streetName the street name
     * @return the by address street
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, params = "street")
    public ResponseEntity<List<UserDTO>> getByAddressStreet(@RequestParam(name = "street") String streetName) {
        List<User> users = userService.getByStreet(streetName);
        List<UserDTO> userDTOList = users.stream()
                .map((user -> mapper.map(user, UserDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    /**
     * Gets by address city and street.
     *
     * @param city   the city
     * @param street the street
     * @return the by address city and street
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"city", "street"})
    public ResponseEntity<List<UserDTO>> getByAddressCityAndStreet(@RequestParam String city,
                                                                   @RequestParam String street) {
        List<User> users = userService.getByCityAndStreet(city, street);
        List<UserDTO> userDTOList = users.stream()
                .map((user -> mapper.map(user, UserDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    /**
     * Gets by role.
     *
     * @param role the role
     * @return the by role
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, params = "role")
    public ResponseEntity<List<UserDTO>> getByRole(@RequestParam Role role) {
        List<User> users = userService.getByRole(role);
        List<UserDTO> userDTOList = users.stream()
                .map((user -> mapper.map(user, UserDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);

    }
}
