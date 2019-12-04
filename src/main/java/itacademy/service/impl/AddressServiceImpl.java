package itacademy.service.impl;

import itacademy.model.Address;
import itacademy.repository.AddressRepository;
import itacademy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service(value = "addressService")
@Transactional
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getByCity(String city) {
        return addressRepository.findByCity(city);
    }

    public List<Address> getByStreet(String street) {
        return addressRepository.findByStreet(street);
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public List<Address> getByExample(Address address) {
        Example<Address> addressExample = Example.of(address);
        return addressRepository.findAll(addressExample);
    }

    public Address getById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        if (!address.isPresent()) {
            return null;
        }
        return address.get();
    }

    public void saveAddress(Address address) {
        addressRepository.save(address);
    }

    @Override
    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }
}
