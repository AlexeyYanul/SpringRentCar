package itacademy.repository;

import itacademy.model.Car;
import itacademy.model.CarInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarInfoRepository extends JpaRepository<CarInfo, Long>{

    CarInfo findByCar(Car car);

}
