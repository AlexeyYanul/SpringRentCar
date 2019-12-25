package itacademy.dto.response;

import itacademy.dto.UserDTO;
import itacademy.model.enums.RentCarStatus;

import java.math.BigDecimal;

public class RentResponseDTO {

    private Long id;

    private String startDate;

    private String finishDate;

    private RentCarStatus status;

    private BigDecimal bill;

    private UserDTO user;

    private CarResponseDTO car;

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

    public BigDecimal getBill() {
        return bill;
    }

    public void setBill(BigDecimal bill) {
        this.bill = bill;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CarResponseDTO getCar() {
        return car;
    }

    public void setCar(CarResponseDTO car) {
        this.car = car;
    }
}
