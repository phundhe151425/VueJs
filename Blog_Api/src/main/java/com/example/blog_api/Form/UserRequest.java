package com.example.blog_api.Form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserRequest {
    private String user_name;
    private String password;
    private String password_confirm;
    private Long role;
}
