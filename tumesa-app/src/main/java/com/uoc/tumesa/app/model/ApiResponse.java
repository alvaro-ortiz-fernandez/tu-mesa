package com.uoc.tumesa.app.model;

/**
 * Clase DTO para devolver desde la API REST respuestas.
 * */
public record ApiResponse<T>(Status status, T response) {

    public enum Status {
        OK,
        ERROR
    }

    public static <T> ApiResponse<T> ok(T response) {
        return new ApiResponse<>(Status.OK, response);
    }

    public static <T> ApiResponse<T> error(T error) {
        return new ApiResponse<>(Status.ERROR, error);
    }
}
