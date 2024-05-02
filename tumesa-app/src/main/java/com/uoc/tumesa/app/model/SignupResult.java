package com.uoc.tumesa.app.model;

import com.uoc.tumesa.app.spring.sec.user.Usuario;

import javax.annotation.Nullable;

public record SignupResult(Result result, @Nullable Usuario user) {

    public enum Result {
        AlreadyRegisteredUser("El usuario ya está registrado"),
        Ok("Usuario registrado");

        private final String message;

        Result(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}