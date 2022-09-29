package com.example.blog_api.controller;

import com.example.blog_api.Form.UserRequest;
import com.example.blog_api.Repository.RoleRepository;
import com.example.blog_api.Repository.UserRepository;
import com.example.blog_api.model.Role;

import com.example.blog_api.service.Authen.AuthService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AuthController {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthService authService;

    public AuthController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthService authService){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authService = authService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> listRoles(){

        try{
            List<Role> roles = new ArrayList<Role>();
            roles = roleRepository.findAll();

            if (roles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(roles, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    record RegisterRequest( @JsonProperty("user_name") String userName,
                            @JsonProperty("password") String password,
                           @JsonProperty("password_confirm") String passwordConfirm,
                            @JsonProperty("role") Role role){}

    record RegisterResponse(Long id, String userName, String password, Role role){}

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest){
//        Role role = roleRepository.getReferenceById(registerRequest.getRole());
//        var user = authService.register(
//                registerRequest.getUser_name(),
//                registerRequest.getPassword(),
//                registerRequest.getPassword_confirm(),
//                role
//        );
        var user = authService.register(
                registerRequest.userName(),
                registerRequest.password(),
                registerRequest.passwordConfirm(),
                registerRequest.role()
        );
        return new RegisterResponse(user.getId(), user.getUserName(), user.getPassword(), user.getRole());
    }

    record LoginRequest(@JsonProperty("user_name")String userName, String password){}

    record LoginResponse(String token){}
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        var login = authService.login(loginRequest.userName(), loginRequest.password());

        Cookie cookie = new Cookie("refresh_token", login.getRefreshToken().getToken());
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setPath("/api");
        response.addCookie(cookie);
        return new LoginResponse(login.getAccessToken().getToken());


    }
}
