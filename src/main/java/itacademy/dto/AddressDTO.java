package itacademy.dto;

import javax.validation.constraints.Size;

public class AddressDTO {

    private Long id;

    @Size(min = 2, max = 20)
    private String city;

    @Size(min = 2, max = 20)
    private String street;

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
