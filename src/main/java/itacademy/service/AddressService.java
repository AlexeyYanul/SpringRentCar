package itacademy.service;

import itacademy.model.Address;
import itacademy.repository.AddressRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddressService {

    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getByCity(String city){
        return addressRepository.findByCity(city);
    }

    public List<Address> getByStreet(String street){
        return addressRepository.findByStreet(street);
    }

    public List<Address> getAllAddresses(){
        return addressRepository.findAll();
    }

    public List<Address> getByExample(Address address){
        Example<Address> addressExample = Example.of(address);
        return addressRepository.findAll(addressExample);
    }

    public Address getById(Long id){
        Optional<Address> address = addressRepository.findById(id);
        if (!address.isPresent()){
            return null;
        }
        return address.get();
    }

    public void saveAddress(Address address){
        addressRepository.save(address);
    }
}
