package itacademy.model;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String city;

    @Column(nullable = false, length = 20)
    private String street;

    @Column(nullable = false)
    private Integer home;

    @Column(nullable = true)
    private Integer flat;

    public Address() {
    }

    public Address(String city, String street, Integer home, Integer flat) {
        this.city = city;
        this.street = street;
        this.home = home;
        this.flat = flat;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", home=" + home +
                ", flat=" + flat +
                '}';
    }

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
