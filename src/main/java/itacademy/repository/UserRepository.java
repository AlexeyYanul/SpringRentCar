package itacademy.repository;

import itacademy.model.User;
import itacademy.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginAndPassword(String login, String password);

    List<User> findByRole(Role role);

    List<User> findByHomeAddressCity(String city);

    List<User> findByHomeAddressStreet(String street);

    @Query("SELECT u FROM User u WHERE u.homeAddress.city = :city " +
            "AND u.homeAddress.street = :street")
    List<User> findByHomeAddressCityAndStreet(@Param("city") String city,
                                              @Param("street") String street);

    List<User> findByLastName(String lastName);

    boolean existsByLogin(String login);
}
