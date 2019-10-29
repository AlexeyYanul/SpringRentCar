package itacademy.repository;

import itacademy.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

    List<Address> findByCity(String city);

    List<Address> findByStreet(String street);
}
