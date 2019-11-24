package itacademy.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "car_info")
public class CarInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean status;

    @Column(name = "hour_price")
    private BigDecimal hourPrice;

    @OneToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    public CarInfo() {
    }

    public CarInfo(Long id, Boolean status, BigDecimal hourPrice, Car car) {
        this.id = id;
        this.status = status;
        this.hourPrice = hourPrice;
        this.car = car;
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "id=" + id +
                ", status=" + status +
                ", hourPrice=" + hourPrice +
                ", car=" + car +
                '}';
    }

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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
