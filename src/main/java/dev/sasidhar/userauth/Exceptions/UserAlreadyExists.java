package dev.sasidhar.userauth.Exceptions;

public class UserAlreadyExists extends RuntimeException{


    public UserAlreadyExists(String userAlreadyExists) {
        super(userAlreadyExists);
    }
}
