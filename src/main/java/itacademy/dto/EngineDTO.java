package itacademy.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EngineDTO {

    private Long id;

    @NotNull(message = "{engine.volume.notNull}")
    private Double volume;

    @NotNull(message = "{engine.fuel.notNull}")
    @NotEmpty(message = "{engine.fuel.notEmpty}")
    private String fuel;

    @NotNull(message = "{engine.fuelEconomy.notNull}")
    @NotEmpty(message = "{engine.fuelEconomy.notEmpty}")
    private String fuelEconomy;

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
