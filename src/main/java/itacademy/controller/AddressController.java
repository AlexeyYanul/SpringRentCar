package itacademy.controller;

import itacademy.model.Address;
import itacademy.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Address>> getAll(){
        List<Address> addresses = addressService.getAllAddresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Address> getOne(@PathVariable Long id){
        Address address =  addressService.getById(id);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id){
        addressService.deleteById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Address> save(@RequestBody Address address){
        address.setId(null);
        addressService.saveAddress(address);
        Address address1 = addressService.getById(address.getId());
        return new ResponseEntity<>(address1, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Address> update(@RequestBody Address address, @PathVariable Long id){
        if (!Objects.equals(id, address.getId())) {
            throw new RuntimeException();
        }
        addressService.saveAddress(address);
        Address address1 = addressService.getById(address.getId());
        return new ResponseEntity<>(address1, HttpStatus.OK);
    }
}
