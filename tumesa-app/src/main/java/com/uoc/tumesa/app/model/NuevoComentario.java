package com.uoc.tumesa.app.model;

import java.math.BigDecimal;

/**
 * Clase DTO para recibir desde el frontend peticiones de creación de un comentario sobre un restaurante.
 * */
public record NuevoComentario(String restaurante, String usuario, String comentario, BigDecimal puntuacion) {

    @Override
    public String toString() {
        return String.format("Restaurante: %s, Usuario: %s, Comentario: %s, Puntuación: %s",
                restaurante, usuario, comentario, puntuacion);
    }
}