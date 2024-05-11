package com.uoc.tumesa.app.model;

/**
 * Clase DTO para recibir desde el frontend peticiones de registrar nuevo usuario.
 * */
public record SignupUser(String user, String email, String password) {}