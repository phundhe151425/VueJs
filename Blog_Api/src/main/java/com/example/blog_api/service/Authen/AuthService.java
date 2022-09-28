package com.example.blog_api.service.Authen;

import com.example.blog_api.Repository.UserRepository;
import com.example.blog_api.model.Role;
import com.example.blog_api.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class AuthService {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private final String accessTokenSecret;

    private final String refreshTokenSecret;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       @Value("${application.security.access-token-secret}") String accessTokenSecret,
                       @Value("${application.security.refresh-token-secret}") String refreshTokenSecret){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.accessTokenSecret = accessTokenSecret;
        this.refreshTokenSecret = refreshTokenSecret;
    }

    public User register(String userName, String password, String passwordConfirm, Role role){
        if(!password.equals(passwordConfirm))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password do not match");
        return userRepository.save(User.of(userName,passwordEncoder.encode(password), role));
    }
}
