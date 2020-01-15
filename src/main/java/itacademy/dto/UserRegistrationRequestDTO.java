package itacademy.dto;

import itacademy.model.Address;
import itacademy.model.enums.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegistrationRequestDTO {

    @NotNull(message = "{user.login.notNull}")
    @NotEmpty(message = "{user.login.notEmpty}")
    @Size(min = 8, max = 20, message = "{user.login.size}")
    private String login;

    @NotNull(message = "{user.password.notNull}")
    @NotEmpty(message = "{user.password.notEmpty}")
    @Size(min = 8, max = 100, message = "{user.password.size}")
    private String password;

    @NotNull(message = "{user.role.notNull}")
    private Role role;

    @NotNull(message = "{user.firstName.notNull}")
    @NotEmpty(message = "{user.firstName.notEmpty}")
    @Size(min = 2, max = 40, message = "{user.firstName.size}")
    private String firstName;

    @NotNull(message = "{user.lastName.notNull}")
    @NotEmpty(message = "{user.lastName.notEmpty}")
    @Size(min = 2, max = 40, message = "{user.lastName.size}")
    private String lastName;

    @NotNull(message = "{user.phone.notNull}")
    private String phone;

    @Email
    private String email;

    private @NotNull(message = "{user.address.notNull}") Address homeAddress;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
}
