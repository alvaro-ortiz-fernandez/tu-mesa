package com.uoc.tumesa.app.model;

import javax.annotation.Nullable;

/**
 * Clase DTO para informar al frontend del resultado de un registro de nuevo usuario.
 * */
public record SignupResult(Result result, @Nullable UserModel user) {

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