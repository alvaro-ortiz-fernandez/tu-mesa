package com.uoc.tumesa.app.model;

import javax.annotation.Nullable;

/**
 * Clase DTO para informar al frontend del resultado de autenticación de un usuario.
 * */
public record LoginResult(Result result, @Nullable UserModel user) {

    public enum Result {
        UnregisteredUser("Usuario inexistente"),
        InvalidPassword("Credenciales inválidas"),
        Ok("Sesión iniciada");

        private final String message;

        Result(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}