package itacademy.service.impl;

import itacademy.model.Address;
import itacademy.model.User;
import itacademy.model.enums.Role;
import itacademy.repository.UserRepository;
import itacademy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service(value = "userService")
@Transactional
public class UserServiceImpl {

    private UserRepository userRepository;
    private AddressService addressService;

    @Autowired
    public UserServiceImpl(UserRepository repository, AddressService addressService) {
        this.userRepository = repository;
        this.addressService = addressService;
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

    public void saveUser(User user) {
        Address homeAddress = user.getHomeAddress();
        addressService.saveAddress(homeAddress);
        userRepository.save(user);
    }

}
