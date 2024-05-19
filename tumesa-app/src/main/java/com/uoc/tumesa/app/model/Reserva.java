package com.uoc.tumesa.app.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase DTO para recibir desde el frontend peticiones de creación & eliminación de una reserva sobre un restaurante.
 * */
public record Reserva(String restaurante, String usuario, String fecha) {

    public ZonedDateTime getFechaFormateada() {
        return ZonedDateTime.parse(fecha.replaceAll(" ", "T") + ":00Z", DateTimeFormatter.ISO_DATE_TIME);
    }

    public boolean esIgual(ZonedDateTime fecha) {
        ZonedDateTime fechaReserva = getFechaFormateada();

        return fechaReserva.getYear() == fecha.getYear()
                && fechaReserva.getMonthValue() == fecha.getMonthValue()
                && fechaReserva.getDayOfMonth() == fecha.getDayOfMonth()
                && fechaReserva.getHour() == fecha.getHour()
                && fechaReserva.getMinute() == fecha.getMinute();
    }

    @Override
    public String toString() {
        return String.format("Restaurante: %s, Usuario: %s, Fecha: %s", restaurante, usuario, getFechaFormateada());
    }
}