package com.uoc.tumesa.app.model;

/**
 * Clase DTO para recibir desde el frontend peticiones de autenticar usuario.
 * */
public record LoginUser(String user, String password) {}