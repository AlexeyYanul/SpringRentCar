package itacademy.service.impl;

import itacademy.model.User;
import itacademy.model.UserFines;
import itacademy.repository.UserFinesRepository;
import itacademy.service.UserFinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "userFinesService")
@Transactional
public class UserFinesServiceImpl implements UserFinesService {

    private UserFinesRepository userFinesRepository;

    @Autowired
    public UserFinesServiceImpl(UserFinesRepository userFinesRepository) {
        this.userFinesRepository = userFinesRepository;
    }

    public void saveUserFines(UserFines userFines) {
        userFinesRepository.save(userFines);
    }

    public List<UserFines> getFinesByUser(User user) {
        return userFinesRepository.findByUser(user);
    }

    public List<UserFines> getAllFines() {
        Sort sort = Sort.by("user");
        return userFinesRepository.findAll(sort);
    }
}
