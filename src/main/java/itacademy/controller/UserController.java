package itacademy.controller;

import itacademy.model.User;
import itacademy.model.enums.Role;
import itacademy.service.UserService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

    private Mapper mapper;

    private UserService userService;

    public UserController(Mapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> save(@RequestBody User user) {
        user.setId(null);
        user.getHomeAddress().setId(null);
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable Long id) {
        if (!Objects.equals(user.getId(), id)) {
            throw new RuntimeException();
        }
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"id"})
    public ResponseEntity<User> getOne(@RequestParam Long id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"login", "password"})
    public ResponseEntity<User> getByLoginAndPassword(@RequestParam String login,
                                                      @RequestParam String password){
        User user = userService.getByLoginAndPassword(login, password);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"lastName"})
    public ResponseEntity<List<User>> getByLastName(@RequestParam String lastName){
        List<User> users = userService.getByLastName(lastName);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"city"})
    public ResponseEntity<List<User>> getByAddressCity(@RequestParam(name = "city") String cityName){
        List<User> users = userService.getByCity(cityName);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = "street")
    public ResponseEntity<List<User>> getByAddressStreet(@RequestParam(name = "street") String streetName){
        List<User> users = userService.getByStreet(streetName);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"city", "street"})
    public ResponseEntity<List<User>> getByAddressCityAndStreet(@RequestParam String city,
                                                                @RequestParam String street){
        List<User> users = userService.getByCityAndStreet(city, street);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET, params = "role")
    public ResponseEntity<List<User>> getByRole(@RequestParam Role role){
        List<User> users = userService.getByRole(role);
        return new ResponseEntity<>(users, HttpStatus.OK);

    }
}
