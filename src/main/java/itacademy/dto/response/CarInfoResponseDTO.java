package itacademy.dto.response;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CarInfoResponseDTO {

    private Long id;

    private Boolean status;

    private BigDecimal hourPrice;

    @NotNull
    private CarResponseDTO car;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public BigDecimal getHourPrice() {
        return hourPrice;
    }

    public void setHourPrice(BigDecimal hourPrice) {
        this.hourPrice = hourPrice;
    }

    public CarResponseDTO getCar() {
        return car;
    }

    public void setCar(CarResponseDTO car) {
        this.car = car;
    }
}
