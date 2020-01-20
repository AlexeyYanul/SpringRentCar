package itacademy.service.impl;

import itacademy.component.LocalizedMessageSource;
import itacademy.model.Address;
import itacademy.repository.AddressRepository;
import itacademy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * The type Address service.
 */
@Service(value = "addressService")
@Transactional
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    private LocalizedMessageSource localizedMessageSource;

    /**
     * Instantiates a new Address service.
     *
     * @param addressRepository      the address repository
     * @param localizedMessageSource the localized message source
     */
    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, LocalizedMessageSource localizedMessageSource) {
        this.addressRepository = addressRepository;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets address by id.
     *
     * @param id the id
     * @return the address
     */
    public Address getById(Long id) {
        validate(id == null, "error.idIsNull");
        Optional<Address> address = addressRepository.findById(id);
        if (!address.isPresent())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.address.notFound", new Object[]{}));
        return address.get();
    }

    /**
     * Gets address by city.
     *
     * @param city the city name
     * @return the addresses list
     */
    public List<Address> getByCity(String city) {
        validate(city.isEmpty(), "error.address.cityIsNull");
        List<Address> addresses = addressRepository.findByCity(city);
        if (addresses.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.address.notFound", new Object[]{}));
        return addresses;
    }

    /**
     * Gets address by street.
     *
     * @param street the street name
     * @return the addresses list
     */
    public List<Address> getByStreet(String street) {
        validate(street.isEmpty(), "error.address.streetIsNull");
        List<Address> addresses = addressRepository.findByStreet(street);
        if (addresses.isEmpty())
            throw new EntityNotFoundException(localizedMessageSource.getMessage("error.address.notFound", new Object[]{}));
        return addresses;
    }

    /**
     * Gets all addresses.
     *
     * @return the addresses list
     */
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public List<Address> getByExample(Address address) {
        Example<Address> addressExample = Example.of(address);
        return addressRepository.findAll(addressExample);
    }

    /**
     * Save address.
     *
     * @param address the address
     * @return the address
     */
    public Address saveAddress(Address address) {
        validate(address.getId() != null, "error.address.notHaveId");
        checkDuplicate(address);
        return addressRepository.save(address);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @Override
    public void deleteById(Long id) {
        getById(id);
        addressRepository.deleteById(id);
    }

    /**
     * Update response entity.
     *
     * @param address the address
     * @return the address
     */
    @Override
    public Address updateAddress(Address address) {
        validate(address.getId() == null, "error.address.haveId");
        checkDuplicate(address);
        return addressRepository.save(address);
    }

    /**
     * Check duplicate in the database.
     *
     * @param address the address
     */
    private void checkDuplicate(Address address) {
        List<Address> duplicateAddress = getByExample(address);
        validate(!duplicateAddress.isEmpty(), "error.address.notUnique");
    }

    private void validate(boolean expression, String messageCode) {
        if (expression) {
            String errorMessage = localizedMessageSource.getMessage(messageCode, new Object[]{});
            throw new NullPointerException(errorMessage);
        }
    }
}