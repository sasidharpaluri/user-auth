package dev.sasidhar.userauth.Exceptions;

public class UserAlreadyExists extends Exception{


    public UserAlreadyExists(String userAlreadyExists) {
        super(userAlreadyExists);
    }
}
