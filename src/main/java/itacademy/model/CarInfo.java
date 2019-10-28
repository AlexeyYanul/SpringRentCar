package itacademy.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "car_info")
public class CarInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_free")
    private Boolean isFree;

    @Column(name = "hour_price")
    private BigDecimal hourPrice;

    @OneToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    public CarInfo() {
    }

    public CarInfo(Long id, Boolean isFree, BigDecimal hourPrice, Car car) {
        this.id = id;
        this.isFree = isFree;
        this.hourPrice = hourPrice;
        this.car = car;
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "id=" + id +
                ", isFree=" + isFree +
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

    public Boolean getFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
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
