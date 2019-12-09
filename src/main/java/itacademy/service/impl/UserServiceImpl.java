package itacademy.service.impl;

import itacademy.model.User;
import itacademy.model.enums.Role;
import itacademy.repository.UserRepository;
import itacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.userRepository = repository;
    }

    public User getByLoginAndPassword(String login, String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }

    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return null;
        }
        return user.get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public List<User> getByCity(String city) {
        return userRepository.findByHomeAddressCity(city);
    }

    public List<User> getByStreet(String street) {
        return userRepository.findByHomeAddressStreet(street);
    }

    public List<User> getByCityAndStreet(String city, String street) {
        return userRepository.findByHomeAddressCityAndStreet(city, street);
    }

    public List<User> getByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    public List<User> getByExample(User user) {
        Example<User> example = Example.of(user);
        return userRepository.findAll(example);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
