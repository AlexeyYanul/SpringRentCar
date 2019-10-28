package itacademy.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "users_fines")
public class UserFines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private BigDecimal bill;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserFines() {
    }

    public UserFines(String description, BigDecimal bill, Date date, User user) {
        this.description = description;
        this.bill = bill;
        this.date = date;
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserFines{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", bill=" + bill +
                ", date=" + date +
                ", user=" + user +
                '}';
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
