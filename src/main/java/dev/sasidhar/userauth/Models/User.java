package dev.sasidhar.userauth.Models;

import dev.sasidhar.userauth.DTOs.UserDto;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
public class User extends BaseModel{
    private String name;
    private String email;
    private String password;
    @ManyToMany
    private List<Role> roles;

    public UserDto converttoDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail(this.email);
        userDto.setName(this.name);
        userDto.setRoles(this.roles);

        return userDto;
    }
}
