package itacademy.service.impl;

import itacademy.component.LocalizedMessageSource;
import itacademy.model.UserFines;
import itacademy.repository.UserFinesRepository;
import itacademy.service.UserFinesService;
import itacademy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * The type User fines service.
 */
@Service(value = "userFinesService")
@Transactional
public class UserFinesServiceImpl implements UserFinesService {

    private UserFinesRepository userFinesRepository;

    private UserService userService;

    private LocalizedMessageSource localizedMessageSource;

    /**
     * Instantiates a new User fines service.
     *
     * @param userFinesRepository    the user fines repository
     * @param localizedMessageSource the localized message source
     * @param userService            the user service
     */
    @Autowired
    public UserFinesServiceImpl(UserFinesRepository userFinesRepository,
                                LocalizedMessageSource localizedMessageSource,
                                UserService userService) {
        this.userFinesRepository = userFinesRepository;
        this.userService = userService;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets all.
     *
     * @return the users fines list
     */
    @Override
    public List<UserFines> getAllFines() {
        Sort sort = Sort.by("user");
        return userFinesRepository.findAll(sort);
    }

    /**
     * Gets by id.
     *
     * @param id the user fine id
     * @return the user fine
     */
    @Override
    public UserFines getById(Long id) {
        validate(id == null, "error.userFines.haveId");
        Optional<UserFines> finesOptional = userFinesRepository.findById(id);
        validate(!finesOptional.isPresent(), "error.userFines.notFound");
        return finesOptional.get();
    }

    /**
     * Gets by status.
     *
     * @param status the user fine status
     * @return the user fine
     */
    @Override
    public List<UserFines> getFinesByStatus(Boolean status) {
        validate(status == null, "error.userFines.haveStatus");
        List<UserFines> userFinesList = userFinesRepository.findByStatus(status);
        validate(userFinesList.isEmpty(), "error.userFines.notFound");
        return userFinesList;
    }

    /**
     * Gets by user id.
     *
     * @param userId the user id
     * @return the user fines
     */
    public List<UserFines> getFinesByUserId(Long userId) {
        validate(userId == null, "error.userFines.unexpectedId");
        List<UserFines> userFinesList = userFinesRepository.findByUserId(userId);
        validate(userFinesList.isEmpty(), "error.userFines.notFound");
        return userFinesList;
    }

    /**
     * Save user Fines.
     *
     * @param userFines the user fines
     * @return the user fines
     */
    public UserFines saveUserFines(UserFines userFines) {
        validate(userFines.getId() != null, "error.userFines.notHaveId");
        userFines.setUser(userService.getById(userFines.getUser().getId()));
        return userFinesRepository.save(userFines);
    }

    /**
     * Update user Fines.
     *
     * @param userFines the user fines
     * @return the user fines
     */
    public UserFines updateUserFines(UserFines userFines) {
        getById(userFines.getId());
        userFines.setUser(userService.getById(userFines.getUser().getId()));
        return userFinesRepository.save(userFines);
    }


    /**
     * Delete.
     *
     * @param id the user fines id
     */
    @Override
    public void deleteById(Long id) {
        getById(id);
        userFinesRepository.deleteById(id);
    }

    private void validate(boolean expression, String messageCode) {
        if (expression) {
            String errorMessage = localizedMessageSource.getMessage(messageCode, new Object[]{});
            throw new RuntimeException(errorMessage);
        }
    }
}
