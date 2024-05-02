package com.uoc.tumesa.app.model;

/**
 * Clase DTO para recibir peticiones desde el frontend.
 * */
public record SignupUser(String user, String email, String password) {}