package com.uoc.tumesa.repo.model;

import com.mongodb.lang.Nullable;

import java.time.ZonedDateTime;

public class User extends RepoEntity {

    private String name;
    private String password;
    private String email;
    private ZonedDateTime signupDate;

    public User(String name, String password, String email) {
        this(null, name, password, email, ZonedDateTime.now());
    }

    public User(@Nullable String id, String name, String password, String email, ZonedDateTime signupDate) {
        super(id);
        this.name = name;
        this.password = password;
        this.email = email;
        this.signupDate = signupDate;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public ZonedDateTime getSignupDate() {
        return signupDate;
    }
    public void setSignupDate(ZonedDateTime signupDate) {
        this.signupDate = signupDate;
    }
}
