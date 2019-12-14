package itacademy.service;

import itacademy.model.UserFines;

import java.util.List;

public interface UserFinesService {

    UserFines getById(Long id);

    UserFines updateUserFines(UserFines userFines);

    UserFines saveUserFines(UserFines userFines);

    List<UserFines> getFinesByUserId(Long userId);

    List<UserFines> getFinesByStatus(Boolean status);

    List<UserFines> getAllFines();

    void deleteById(Long id);

}
