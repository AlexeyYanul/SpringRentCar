package itacademy.repository;

import itacademy.model.Car;
import itacademy.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarModelRepository extends JpaRepository<CarModel, Long>{

}
