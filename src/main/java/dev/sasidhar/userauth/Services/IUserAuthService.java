package dev.sasidhar.userauth.Services;

import dev.sasidhar.userauth.Models.User;
import dev.sasidhar.userauth.pojos.User_Token;

public interface IUserAuthService {
    public User userSignUp(String name,String email,String password);
    public User adminSignUp(String name,String email,String password);
    public User_Token userLogin(String email, String password);

    Boolean validateToken(String token);
    Boolean validateAdminToken(String token);
}
