package itacademy.dto.request;

import itacademy.model.enums.RentCarStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RentRequestDTO {

    private Long id;

    @NotNull(message = "{rent.startDate.notNull}")
    @NotEmpty(message = "{rent.startDate.notEmpty}")
    private String startDate;

    @NotNull(message = "{rent.finishDate.notNull}")
    @NotEmpty(message = "{rent.finishDate.notEmpty}")
    private String finishDate;

    private RentCarStatus status;

    @NotNull(message = "{rent.user.notNull}")
    @NotEmpty(message = "{rent.user.notEmpty}")
    private Long userId;

    @NotNull(message = "{rent.car.notNull}")
    @NotEmpty(message = "{rent.car.notEmpty}")
    private Long carId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public RentCarStatus getStatus() {
        return status;
    }

    public void setStatus(RentCarStatus status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}
