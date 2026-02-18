package dev.sasidhar.userauth.Controllers;

import dev.sasidhar.userauth.DTOs.UserDto;
import dev.sasidhar.userauth.DTOs.UserLoginDto;
import dev.sasidhar.userauth.DTOs.UserSignUpDto;
import dev.sasidhar.userauth.Exceptions.InsufficientDetails;
import dev.sasidhar.userauth.Exceptions.UserAlreadyExists;
import dev.sasidhar.userauth.Models.User;
import dev.sasidhar.userauth.Services.IUserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/User")
public class UserAuthController{
    @Autowired
    private IUserAuthService userAuthService;
    /*
    User SignUP functionality

    What it accepts
        - name
        - email
        - password
    (use UserSignUpDto)

    Actions
        - check if the user exists or not
            - Yes Return - exception
            - No (return
                    - name
                    - email
                    - roles)
                (use UserDto)
     Return - ResponseEntity<>(UserDto,200ok)

     */
    @PostMapping("/signup")
    public ResponseEntity<UserDto> userSignUp(@RequestBody UserSignUpDto userSignUpDto){
       // System.out.println(userSignUpDto.getEmail() + " ----- " + userSignUpDto.getName() + " -------- " + userSignUpDto.getPassword());
        if(userSignUpDto.getName()== null||userSignUpDto.getEmail()== null||userSignUpDto.getPassword() == null)
                throw new InsufficientDetails("Please make sure you provide name,email,password");
        User created_user = userAuthService.userSignUp(userSignUpDto.getName(),userSignUpDto.getEmail(),userSignUpDto.getPassword());
        if(created_user == null)
            throw new UserAlreadyExists("User already exists");
        return new ResponseEntity<>(created_user.converttoDto(), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> userLogin(@RequestBody UserLoginDto userLoginDto){
        if(userLoginDto.getEmail()== null||userLoginDto.getPassword() == null)
            throw new InsufficientDetails("Please make sure you provide email & password");
        User user = userAuthService.userLogin(userLoginDto.getEmail(), userLoginDto.getPassword());
        if(user == null)
            throw new InsufficientDetails("Incorrect password / User not available");
        return new ResponseEntity<>(user.converttoDto(),HttpStatus.ACCEPTED);


    }
}
