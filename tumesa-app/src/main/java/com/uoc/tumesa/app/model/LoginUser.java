package com.uoc.tumesa.app.model;

public class LoginUser {

    private String user;
    private String password;

    public LoginUser() {}

    public LoginUser(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
