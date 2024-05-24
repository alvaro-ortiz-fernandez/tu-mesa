package com.uoc.tumesa.app.model;

import java.math.BigDecimal;

/**
 * Clase DTO para recibir desde el frontend peticiones de búsqueda de restaurantes.
 * */
public record FiltroBusquedaRestaurantes(String termino, String nombre, BigDecimal puntuacion) {

    @Override
    public String toString() {
        return String.format("Término: %s, Nombre: %s, Puntuación: %s", termino, nombre, puntuacion);
    }
}