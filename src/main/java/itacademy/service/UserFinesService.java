package itacademy.service;

import itacademy.model.User;
import itacademy.model.UserFines;

import java.util.List;

public interface UserFinesService {
    void saveUserFines(UserFines userFines);

    List<UserFines> getFinesByUser(User user);

    List<UserFines> getAllFines();
}
