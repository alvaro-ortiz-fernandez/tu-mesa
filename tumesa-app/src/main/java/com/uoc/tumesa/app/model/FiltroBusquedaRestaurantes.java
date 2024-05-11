package com.uoc.tumesa.app.model;

/**
 * Clase DTO para recibir desde el frontend peticiones de búsqueda de restaurantes.
 * */
public record FiltroBusquedaRestaurantes(String termino, String nombre) {

    @Override
    public String toString() {
        return String.format("Término: %s, Nombre: %s", termino, nombre);
    }
}