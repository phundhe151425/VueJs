package com.example.blog_api.Repository;


import com.example.blog_api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "select r.role_name from role r join Role_User ru on r.Role_Id = ru.Role_Id where ru.User_Id = ?1", nativeQuery = true)
    List<String> findRolesByUser(Long userId);
}
