package itacademy.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class UserFinesRequestDTO {

    private Long id;

    @NotNull(message = "{userFines.description.notNull}")
    @NotEmpty(message = "{userFines.description.notEmpty}")
    private String description;

    @NotNull(message = "{userFines.bill.notNull}")
    @NotEmpty(message = "{userFines.bill.notEmpty}")
    private BigDecimal bill;

    private String date;

    @NotNull(message = "{userFines.status.notNul}")
    private Boolean status;

    @NotNull(message = "{userFines.user.notNull}")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBill() {
        return bill;
    }

    public void setBill(BigDecimal bill) {
        this.bill = bill;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
