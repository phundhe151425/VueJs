package com.example.blog_api.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Role_Id", nullable = false)
    private Long Id;

    @Column(name = "Role_Name", length = 30, nullable = false)
    private String roleName;

    @OneToMany(mappedBy = "role")
    private Set<RoleUser> roleUsers;
}
