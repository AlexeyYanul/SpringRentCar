package itacademy.repository;

import itacademy.model.RentCar;
import itacademy.model.enums.RentCarStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RentCarRepository extends JpaRepository<RentCar, Long> {

    List<RentCar> findByStatus(RentCarStatus status, Sort sort);

    List<RentCar> findByCarId(Long carId, Sort sort);

    List<RentCar> findByCarId(Long carId);

    List<RentCar> findByUserId(Long userId, Sort sort);

    Integer countByCarId(Long carId);

    @Query("SELECT COUNT(rc) FROM RentCar rc " +
            "WHERE rc.car.id = :carId " +
            "AND " +
            "(rc.finishDate < :start OR rc.startDate > :finish)")
    Integer countByDateMyPeriod(@Param("carId") Long carId, @Param("start") LocalDateTime start,
                                @Param("finish") LocalDateTime finish);
}
