package itacademy.model;

import itacademy.model.enums.RentCarStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "rent_car")
public class RentCar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "finish_date")
    private Date finishDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentCarStatus status = RentCarStatus.REQUEST;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    public RentCar() {
    }

    public RentCar(Long id, Date startDate, Date finishDate, RentCarStatus status, User user, Car car) {
        this.id = id;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.status = status;
        this.user = user;
        this.car = car;
    }

    @Override
    public String toString() {
        return "RentCar{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", status=" + status +
                ", user=" + user +
                ", car=" + car +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public RentCarStatus getStatus() {
        return status;
    }

    public void setStatus(RentCarStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
