package itacademy.service;

import itacademy.model.Address;

import java.util.List;

public interface AddressService {
    List<Address> getByCity(String city);

    List<Address> getByStreet(String street);

    List<Address> getAllAddresses();

    List<Address> getByExample(Address address);

    Address getById(Long id);

    Address saveAddress(Address address);

    void deleteById(Long id);
}
