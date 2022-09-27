package com.example.blog_api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "User")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id", nullable = false)
    private Long Id;

    @Column(name = "User_Name", length = 36, nullable = false)
    private String userName;

    @Column(name = "encryted_Password", length = 128, nullable = false)
    private String encrytedPassword;

    @OneToMany(mappedBy = "user")
    private Set<RoleUser> roleUsers;
}
