package dev.sasidhar.userauth.DTOs;

import dev.sasidhar.userauth.Models.State;
import dev.sasidhar.userauth.Models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserSignUpDto {
    private String name;
    private String email;
    private String password;

    public User convertToUser(){
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);
        return user;
    }
}
