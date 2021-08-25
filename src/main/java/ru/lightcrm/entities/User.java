package ru.lightcrm.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany
    @JoinTable(name = "users_priorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "priority_id"))
    private Set<Priority> priorities;

    public boolean isEnabled() {
        return enabled;
    }

    public static User createNewUser(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEnabled(true);
        return user;
    }
}
