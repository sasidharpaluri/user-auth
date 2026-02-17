package dev.sasidhar.userauth.Services;

import dev.sasidhar.userauth.Models.User;

public interface IUserAuthService {
    public User userSignUp(String name,String email,String password);
    public User userLogin(String email,String password);
}
