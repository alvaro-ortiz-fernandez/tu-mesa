package com.uoc.tumesa.app.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase DTO para recibir desde el frontend peticiones de creación de una reserva sobre un restaurante.
 * */
public record NuevaReserva(String restaurante, String usuario, String fecha) {

    public ZonedDateTime getFechaFormateada() {
        return ZonedDateTime.parse(fecha.replaceAll(" ", "T") + ":00Z", DateTimeFormatter.ISO_DATE_TIME);
    }

    @Override
    public String toString() {
        return String.format("Restaurante: %s, Usuario: %s, Fecha: %s", restaurante, usuario, getFechaFormateada());
    }
}