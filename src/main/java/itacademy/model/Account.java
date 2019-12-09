package itacademy.model;

import itacademy.model.enums.Role;
import javax.persistence.*;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String login;

    @Column(nullable = false, length = 20)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Account() {
    }

    public Account(String login, String password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
