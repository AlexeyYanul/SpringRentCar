package itacademy.service.impl;

import itacademy.component.LocalizedMessageSource;
import itacademy.model.UserFines;
import itacademy.repository.UserFinesRepository;
import itacademy.service.UserFinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service(value = "userFinesService")
@Transactional
public class UserFinesServiceImpl implements UserFinesService {

    private UserFinesRepository userFinesRepository;

    private LocalizedMessageSource localizedMessageSource;

    @Autowired
    public UserFinesServiceImpl(UserFinesRepository userFinesRepository,
                                LocalizedMessageSource localizedMessageSource) {
        this.userFinesRepository = userFinesRepository;
        this.localizedMessageSource = localizedMessageSource;
    }

    @Override
    public List<UserFines> getAllFines() {
        Sort sort = Sort.by("user");
        return userFinesRepository.findAll(sort);
    }

    @Override
    public UserFines getById(Long id) {
        if (id == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.userFines.haveId", new Object[]{}));
        Optional<UserFines> finesOptional = userFinesRepository.findById(id);
        if (!finesOptional.isPresent())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.userFines.notFound", new Object[]{}));
        return finesOptional.get();
    }

    @Override
    public List<UserFines> getFinesByStatus(Boolean status) {
        if (status == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.userFines.haveStatus", new Object[]{}));
        List<UserFines> userFinesList = userFinesRepository.findByStatus(status);
        if (userFinesList.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.userFines.notFound", new Object[]{}));
        return userFinesList;
    }

    public List<UserFines> getFinesByUserId(Long userId) {
        if (userId == null)
            throw new NullPointerException(localizedMessageSource.getMessage("error.userFines.unexpectedCarId", new Object[]{}));
        List<UserFines> userFinesList = userFinesRepository.findByUserId(userId);
        if (userFinesList.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.userFines.notFound", new Object[]{}));
        return userFinesList;
    }

    public UserFines saveUserFines(UserFines userFines) {
        return userFinesRepository.save(userFines);
    }


    public UserFines updateUserFines(UserFines userFines) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
