package itacademy.controller;

import itacademy.component.LocalizedMessageSource;
import itacademy.dto.AddressDTO;
import itacademy.model.Address;
import itacademy.service.AddressService;
import org.dozer.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Address controller.
 */
@RestController
@RequestMapping("/addresses")
public class AddressController {

    private AddressService addressService;

    private Mapper mapper;

    private LocalizedMessageSource localizedMessageSource;

    /**
     * Instantiates a new Address controller.
     *
     * @param addressService         the address service
     * @param mapper                 the mapper
     * @param localizedMessageSource the localized message source
     */
    public AddressController(AddressService addressService, Mapper mapper, LocalizedMessageSource localizedMessageSource) {
        this.addressService = addressService;
        this.mapper = mapper;
        this.localizedMessageSource = localizedMessageSource;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AddressDTO>> getAll() {
        List<Address> addresses = addressService.getAllAddresses();
        List<AddressDTO> addressDTOList = addresses.stream()
                .map((address) -> mapper.map(address, AddressDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(addressDTOList, HttpStatus.OK);
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, params = {"id"})
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@RequestParam Long id) {
        addressService.deleteById(id);
    }

    /**
     * Save response entity.
     *
     * @param addressDto the address dto
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AddressDTO> save(@Valid @RequestBody AddressDTO addressDto) {
        addressDto.setId(null);
        AddressDTO responseAddressDTO = mapper.map(
                addressService.saveAddress(mapper.map(addressDto, Address.class)),
                AddressDTO.class);
        return new ResponseEntity<>(responseAddressDTO, HttpStatus.CREATED);
    }

    /**
     * Update response entity.
     *
     * @param addressDTO the address dto
     * @param id         the id
     * @return the response entity
     */
    @RequestMapping(method = RequestMethod.PUT, params = {"id"})
    public ResponseEntity<AddressDTO> update(@Valid @RequestBody AddressDTO addressDTO, @RequestParam Long id) {
        if (!Objects.equals(id, addressDTO.getId())) {
            throw new RuntimeException(localizedMessageSource.getMessage("error.address.unexpectedId", new Object[]{}));
        }
        AddressDTO responseAddressDTO = mapper.map(addressService.updateAddress(mapper.map(addressDTO, Address.class)), AddressDTO.class);
        return new ResponseEntity<>(responseAddressDTO, HttpStatus.OK);
    }

    /**
     * Gets one.
     *
     * @param id the id
     * @return the one
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, params = {"id"})
    public ResponseEntity<AddressDTO> getOne(@RequestParam Long id) {
        Address responseAddress = addressService.getById(id);
        AddressDTO responseAddressDTO = mapper.map(responseAddress, AddressDTO.class);
        return new ResponseEntity<>(responseAddressDTO, HttpStatus.OK);
    }

    /**
     * Gets by city.
     *
     * @param cityName the city name
     * @return the by city
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, params = "city")
    public ResponseEntity<List<AddressDTO>> getByCity(@RequestParam(name = "city") String cityName) {
        List<Address> responseAddresses = addressService.getByCity(cityName);
        List<AddressDTO> responseAddressesDTO = responseAddresses.stream()
                .map((address -> mapper.map(address, AddressDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseAddressesDTO, HttpStatus.OK);
    }

    /**
     * Gets by street.
     *
     * @param streetName the street name
     * @return the by street
     */
    @RequestMapping(value = "/find", method = RequestMethod.GET, params = "street")
    public ResponseEntity<List<AddressDTO>> getByStreet(@RequestParam(name = "street") String streetName) {
        List<Address> responseAddresses = addressService.getByStreet(streetName);
        List<AddressDTO> responseAddressesDTO = responseAddresses.stream()
                .map((address -> mapper.map(address, AddressDTO.class)))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseAddressesDTO, HttpStatus.OK);
    }
}
