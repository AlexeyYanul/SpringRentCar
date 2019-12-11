package itacademy.service.impl;

import itacademy.component.LocalizedMessageSource;
import itacademy.model.Address;
import itacademy.model.User;
import itacademy.model.enums.Role;
import itacademy.repository.UserRepository;
import itacademy.service.AddressService;
import itacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private LocalizedMessageSource localizedMessageSource;

    private AddressService addressService;

    @Autowired
    public UserServiceImpl(UserRepository repository, LocalizedMessageSource localizedMessageSource,
                           AddressService addressService) {
        this.userRepository = repository;
        this.localizedMessageSource = localizedMessageSource;
        this.addressService = addressService;
    }

    @Override
    public User getByLoginAndPassword(String login, String password) {
        if (login.isEmpty())
            throw new NullPointerException(localizedMessageSource.getMessage("error.user.loginIsEmpty", new Object[]{}));
        if (password.isEmpty())
            throw new NullPointerException(localizedMessageSource.getMessage("error.user.passwordIsEmpty", new Object[]{}));
        User responseUser = userRepository.findByLoginAndPassword(login, password);
        if (responseUser == null)
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.user.notFound", new Object[]{}));
        return responseUser;
    }

    @Override
    public User getById(Long id) {
        if (id == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.idIsNull", new Object[]{}));
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.user.notFound", new Object[]{}));
        }
        return user.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> getByCity(String city) {
        if (city.isEmpty())
            throw new NullPointerException(localizedMessageSource.getMessage("error.address.cityIsNull", new Object[]{}));
        List<User> userList = userRepository.findByHomeAddressCity(city);
        if (userList.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.user.notFound", new Object[]{}));
        return userList;
    }

    @Override
    public List<User> getByStreet(String street) {
        if (street.isEmpty())
            throw new NullPointerException(localizedMessageSource.getMessage("error.address.streetIsNull", new Object[]{}));
        List<User> userList = userRepository.findByHomeAddressStreet(street);
        if (userList.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.user.notFound", new Object[]{}));
        return userList;
    }

    @Override
    public List<User> getByCityAndStreet(String city, String street) {
        if (city.isEmpty())
            throw new NullPointerException(localizedMessageSource.getMessage("error.address.cityIsNull", new Object[]{}));
        if (street.isEmpty())
            throw new NullPointerException(localizedMessageSource.getMessage("error.address.streetIsNull", new Object[]{}));
        List<User> userList = userRepository.findByHomeAddressCityAndStreet(city, street);
        if (userList.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.user.notFound", new Object[]{}));
        return userList;
    }

    @Override
    public List<User> getByLastName(String lastName) {
        if (lastName.isEmpty())
            throw new NullPointerException(localizedMessageSource.getMessage("error.user.lastNameIsEmpty", new Object[]{}));
        List<User> userList = userRepository.findByLastName(lastName);
        if (userList.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.user.notFound", new Object[]{}));
        return userList;
    }

    @Override
    public User saveUser(User user) {
        if (user.getId() != null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.user.notHaveId", new Object[]{}));
        checkDuplicateLogin(user);
        Address homeAddress = user.getHomeAddress();
        List<Address> duplicateAddress = addressService.getByExample(homeAddress);
        if (!duplicateAddress.isEmpty()) {
            Long duplicateAddressId = duplicateAddress.get(0).getId();
            user.getHomeAddress().setId(duplicateAddressId);
        } else {
            addressService.saveAddress(homeAddress);
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        if (user.getId() == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.user.notHaveId", new Object[]{}));
        checkDuplicateUser(user);
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.user.haveId", new Object[]{}));
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new NullPointerException(localizedMessageSource.getMessage("error.user.notExist", new Object[]{}));
        userRepository.deleteById(id);
    }

    private void checkDuplicateLogin(User user) {
        if (userRepository.existsByLogin(user.getLogin()))
            throw new NullPointerException(localizedMessageSource.getMessage("error.user.loginNotUnique", new Object[]{}));
    }

    private void checkDuplicateUser(User user) {
        List<User> duplicateUser = getByExample(user);
        if (!duplicateUser.isEmpty()) {
            throw new NullPointerException(localizedMessageSource.getMessage("error.user.notUnique", new Object[]{}));
        }
    }

    public List<User> getByExample(User user) {
        Example<User> example = Example.of(user);
        return userRepository.findAll(example);
    }

}
