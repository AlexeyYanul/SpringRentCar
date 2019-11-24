package itacademy.repository;

import itacademy.model.Car;
import itacademy.model.enums.Body;
import itacademy.model.enums.Gearbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByCarModelName(String name);

    List<Car> findByCarModelYear(Integer year);

    List<Car> findByBody(Body body);

    List<Car> findByCarInfoStatus(Boolean status);

    List<Car> findByGearbox(Gearbox gearbox);

}
