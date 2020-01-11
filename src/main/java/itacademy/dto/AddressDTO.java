package itacademy.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddressDTO {

    private Long id;

    @NotNull(message = "{address.city.notNull}")
    @NotEmpty(message = "{address.city.notEmpty}")
    @Size(min = 2, max = 20, message = "{address.city.size}")
    private String city;

    @NotNull(message = "{address.street.notNull}")
    @NotEmpty(message = "{address.street.notEmpty}")
    @Size(min = 2, max = 20, message = "{address.street.size}")
    private String street;

    @NotNull(message = "{address.home.notNull}")
    @NotEmpty(message = "{address.home.notEmpty}")
    private Integer home;

    private Integer flat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHome() {
        return home;
    }

    public void setHome(Integer home) {
        this.home = home;
    }

    public Integer getFlat() {
        return flat;
    }

    public void setFlat(Integer flat) {
        this.flat = flat;
    }
}
