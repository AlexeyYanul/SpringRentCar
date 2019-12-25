package itacademy.dto.request;

import itacademy.model.enums.RentCarStatus;

import java.math.BigDecimal;

public class RentRequestDTO {

    private Long id;

    private String startDate;

    private String finishDate;

    private RentCarStatus status;

    private Long userId;

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
