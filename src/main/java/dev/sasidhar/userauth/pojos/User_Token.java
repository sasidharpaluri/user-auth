package dev.sasidhar.userauth.pojos;

import dev.sasidhar.userauth.Models.BaseModel;
import dev.sasidhar.userauth.Models.User;

public class User_Token extends BaseModel {
    private User user;
    private String token;

    public User_Token(User user,String token){
        this.token = token;
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
