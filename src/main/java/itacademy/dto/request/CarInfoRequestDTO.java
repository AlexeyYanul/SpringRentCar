package itacademy.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CarInfoRequestDTO {

    private Long id;

    @NotNull(message = "{carInfo.status.notNull}")
    @NotEmpty(message = "{carInfo.status.notEmpty}")
    private Boolean status;

    @NotNull(message = "{carInfo.hourPrice.notNull}")
    private BigDecimal hourPrice;

    @NotNull(message = "{carInfo.car.notNull}")
    private Long carId;

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

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}
