package com.uoc.tumesa.app.model;

import com.uoc.tumesa.repo.model.User;

import java.time.ZonedDateTime;

/**
 * Clase DTO para devolver al frontend usuarios, capando así información sensible de BBDD (como el _id interno).
 * */
public record UserModel(String name, String email, ZonedDateTime signupDate, String jwtToken) {

    public UserModel(User user, String jwtToken) {
        this(user.getName(), user.getEmail(), user.getSignupDate(), jwtToken);
    }
}