package itacademy.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "users_fines")
public class UserFines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private BigDecimal bill;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime date;

    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserFines() {
    }

    public UserFines(String description, BigDecimal bill, LocalDateTime date, User user, Boolean status) {
        this.description = description;
        this.bill = bill;
        this.date = date;
        this.user = user;
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserFines{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", bill=" + bill +
                ", date=" + date +
                ", status=" + status +
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
