package itacademy.model;

import javax.persistence.*;

@Entity
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double volume;

    private String fuel;

    @Column(name = "fuel_economy")
    private String fuelEconomy;

    public Engine() {
    }

    public Engine(Double volume, String fuel, String fuelEconomy) {
        this.volume = volume;
        this.fuel = fuel;
        this.fuelEconomy = fuelEconomy;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "id=" + id +
                ", volume=" + volume +
                ", fuel='" + fuel + '\'' +
                ", fuelEconomy='" + fuelEconomy + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getFuelEconomy() {
        return fuelEconomy;
    }

    public void setFuelEconomy(String fuelEconomy) {
        this.fuelEconomy = fuelEconomy;
    }
}
