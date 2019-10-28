package itacademy.repository;

import itacademy.model.Car;
import itacademy.model.CarInfo;
import itacademy.model.CarModel;
import itacademy.model.enums.Body;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{

    List<Car> findByCarModelName(String name);

    List<Car> findByBody(Body body);

    List<Car> findByCarInfoIsFree(Boolean b);

    @Query("SELECT DISTINCT c.body FROM Car c")
    List<Body> getBodyTypes();



}
