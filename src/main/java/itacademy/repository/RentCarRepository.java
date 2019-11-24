package itacademy.repository;

import itacademy.model.Car;
import itacademy.model.RentCar;
import itacademy.model.User;
import itacademy.model.enums.RentCarStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentCarRepository extends JpaRepository<RentCar, Long> {

    List<RentCar> findByStatus(RentCarStatus status, Sort sort);

    List<RentCar> findByCar(Car car, Sort sort);

    List<RentCar> findByUser(User user, Sort sort);
}
