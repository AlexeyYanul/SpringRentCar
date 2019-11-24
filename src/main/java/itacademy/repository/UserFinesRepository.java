package itacademy.repository;

import itacademy.model.User;
import itacademy.model.UserFines;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFinesRepository extends JpaRepository<UserFines, Long> {

    List<UserFines> findByUser(User user);
}
