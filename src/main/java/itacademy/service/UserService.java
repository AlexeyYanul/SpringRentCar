package itacademy.service;

import itacademy.model.User;
import itacademy.model.enums.Role;

import java.util.List;

public interface UserService {
    User getByLoginAndPassword(String login, String password);
    User getById(Long id);
    List<User> getAllUsers();
    List<User> getByRole(Role role);
    List<User> getByCity(String city);
    List<User> getByStreet(String street);
    List<User> getByCityAndStreet(String city, String street);
    List<User> getByLastName(String lastName);
    List<User> getByExample(User user);
    void saveUser(User user);
    void deleteById(Long id);
}
