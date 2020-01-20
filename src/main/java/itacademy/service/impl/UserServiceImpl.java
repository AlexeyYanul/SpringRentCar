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

/**
 * The type User service.
 */
@Service(value = "userService")
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private LocalizedMessageSource localizedMessageSource;

    private AddressService addressService;

    /**
     * Instantiates a new User service.
     *
     * @param repository             the repository
     * @param localizedMessageSource the localized message source
     * @param addressService         the address service
     */
    @Autowired
    public UserServiceImpl(UserRepository repository, LocalizedMessageSource localizedMessageSource,
                           AddressService addressService) {
        this.userRepository = repository;
        this.localizedMessageSource = localizedMessageSource;
        this.addressService = addressService;
    }

    /**
     * Gets by login and password.
     *
     * @param login    the user login
     * @param password the user password
     * @return the user
     */
    @Override
    public User getByLoginAndPassword(String login, String password) {
        validate(login.isEmpty(), "error.user.loginIsEmpty");
        validate(password.isEmpty(), "error.user.passwordIsEmpty");
        User responseUser = userRepository.findByLoginAndPassword(login, password);
        if (responseUser == null)
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.user.notFound", new Object[]{}));
        return responseUser;
    }

    /**
     * Gets by login.
     *
     * @param login the user login
     * @return the user
     */
    @Override
    public User getByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    /**
     * Gets by id.
     *
     * @param id the user id
     * @return the user
     */
    @Override
    public User getById(Long id) {
        validate(id == null, "error.idIsNull");
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.user.notFound", new Object[]{}));
        }
        return user.get();
    }

    /**
     * Gets all users.
     *
     * @return the users list
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Gets by role.
     *
     * @param role the user role
     * @return the users list
     */
    public List<User> getByRole(Role role) {
        return userRepository.findByRole(role);
    }

    /**
     * Gets by city.
     *
     * @param city the city name
     * @return the users list
     */
    @Override
    public List<User> getByCity(String city) {
        validate(city.isEmpty(), "error.address.cityIsNull");
        List<User> userList = userRepository.findByHomeAddressCity(city);
        if (userList.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.user.notFound", new Object[]{}));
        return userList;
    }

    /**
     * Gets by street.
     *
     * @param street the street name
     * @return the users list
     */
    @Override
    public List<User> getByStreet(String street) {
        validate(street.isEmpty(), "error.address.streetIsNull");
        List<User> userList = userRepository.findByHomeAddressStreet(street);
        if (userList.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.user.notFound", new Object[]{}));
        return userList;
    }

    /**
     * Gets by city and street.
     *
     * @param city    the city name
     * @param street the street name
     * @return the users list
     */
    @Override
    public List<User> getByCityAndStreet(String city, String street) {
        validate(city.isEmpty(), "error.address.cityIsNull");
        validate(street.isEmpty(), "error.address.streetIsNull");
        List<User> userList = userRepository.findByHomeAddressCityAndStreet(city, street);
        if (userList.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.user.notFound", new Object[]{}));
        return userList;
    }

    /**
     * Gets by last name.
     *
     * @param lastName the user last name
     * @return the users list
     */
    @Override
    public List<User> getByLastName(String lastName) {
        validate(lastName.isEmpty(), "error.user.lastNameIsEmpty");
        List<User> userList = userRepository.findByLastName(lastName);
        if (userList.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.user.notFound", new Object[]{}));
        return userList;
    }

    /**
     * Save user.
     *
     * @param user the user
     * @return user
     */
    @Override
    public User saveUser(User user) {
        validate(user.getId() != null, "error.user.notHaveId");
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

    /**
     * Update user.
     *
     * @param user the user
     * @return the user
     */
    @Override
    public User updateUser(User user) {
        validate(user.getId() == null, "error.user.notHaveId");
        checkDuplicateUser(user);
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

    /**
     * Delete.
     *
     * @param id the user id
     */
    @Override
    public void deleteById(Long id) {
        getById(id);
        userRepository.deleteById(id);
    }

    /**
     * Check duplicate in the database by login.
     *
     * @param user the address
     */
    private void checkDuplicateLogin(User user) {
        String userLogin = user.getLogin();
        validate(userRepository.existsByLogin(userLogin), "error.user.loginNotUnique");
    }

    /**
     * Check duplicate in the database by example.
     *
     * @param user the address
     */
    private void checkDuplicateUser(User user) {
        List<User> duplicateUser = getByExample(user);
        validate(!duplicateUser.isEmpty(), "error.user.notUnique");
    }

    /**
     * Check users by example.
     *
     * @return users list
     */
    public List<User> getByExample(User user) {
        Example<User> example = Example.of(user);
        return userRepository.findAll(example);
    }

    private void validate(boolean expression, String messageCode) {
        if (expression) {
            String errorMessage = localizedMessageSource.getMessage(messageCode, new Object[]{});
            throw new NullPointerException(errorMessage);
        }
    }
}
