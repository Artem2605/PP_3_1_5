package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    @Pattern(regexp = "[A-Za-zА-Яа-яЁё]+", message = "Неверно указано имя")
    private String username;

    @Column(name = "lastName")
    @Pattern(regexp = "[A-Za-zА-Яа-яЁё]+", message = "Неверно указана фамилия")
    private String lastName;

    @Column(name = "password")
    @NotEmpty(message = "Неверно указан пароль")
    private String password;

    @Column(name = "year")
    @Min(value = 0, message = "Неверно указан год")
    @Max(value = 2023, message = "Неверно указан год")
    @NotNull(message = "Неверно указан год")
    private Long year;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<@Valid Role> roles;

    public User() {
    }

    public User(String username, String lastName, String password, Long year, List<Role> roles) {
        this.username = username;
        this.lastName = lastName;
        this.password = password;
        this.year = year;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getAuthority())).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id = " + id +
                ", username = " + username + "\\" +
                ", lastName = " + lastName + "\\" +
                ", password = " + password + "\\" +
                ", year = " + year +
                ", roles = " + roles +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            User user = (User) o;
            return Objects.equals(id, user.id) && Objects.equals(username, user.username)
                    && Objects.equals(lastName, user.lastName)
                    && Objects.equals(password, user.password)
                    && Objects.equals(year, user.year)
                    && Objects.equals(roles, user.roles);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, lastName, password, year, roles);
    }
}