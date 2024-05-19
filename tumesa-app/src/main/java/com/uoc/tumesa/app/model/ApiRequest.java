package com.uoc.tumesa.app.model;

/**
 * Clase DTO para recibir en la API REST peticiones que requieran autenticación.
 * */
public record ApiRequest<T>(String authToken, T request) {
}
