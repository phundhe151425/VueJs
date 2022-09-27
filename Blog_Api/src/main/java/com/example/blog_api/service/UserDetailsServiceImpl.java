package com.example.blog_api.service;

import com.example.blog_api.Repository.RoleRepository;
import com.example.blog_api.Repository.UserRepository;

import com.example.blog_api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =this.userRepository.findUserByUserName(username);

        if(user == null){
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        List<String> roleNames = this.roleRepository.findRolesByUser(user.getId());

        List<GrantedAuthority> grandList = new ArrayList<>();
        if(roleNames != null){
            for (String role : roleNames){
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grandList.add(authority);
            }
        }

        UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(user.getUserName(), //
                user.getEncrytedPassword(), grandList);

        return userDetails;
    }


}
