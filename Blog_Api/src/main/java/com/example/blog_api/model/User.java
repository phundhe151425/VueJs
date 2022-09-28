package com.example.blog_api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "User")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "User_Id", nullable = false)
    private Long id;

    @Column(name = "User_Name", length = 36, nullable = false)
    private String userName;

    @Column(name = "Password", length = 128, nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_Id")
    private Role role;

    public static User of(String userName, String password, Role role){
        return new User(userName, password, role);
    }

    @PersistenceConstructor
    public User(String userName, String password, Role role) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }
}
