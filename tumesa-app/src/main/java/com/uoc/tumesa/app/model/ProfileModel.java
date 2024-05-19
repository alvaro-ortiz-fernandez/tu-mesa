package com.uoc.tumesa.app.model;

import com.uoc.tumesa.repo.model.Restaurant;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase DTO para devolver al frontend perfiles de usuario, capando así información sensible de BBDD (como el _id interno).
 * */
public record ProfileModel(Long numComents, List<Reservation> reservations) {

    @Override
    public String toString() {
        return String.format("Nº comentarios: %s, reservas: %s", numComents, reservations);
    }

    public record Reservation(RestaurantModel restaurant, List<ZonedDateTime> dates) {

        public Reservation(RestaurantModel restaurant, String user) {
            this(restaurant, restaurant.reservations().stream()
                    .filter(res -> res.getUser().equals(user))
                    .map(Restaurant.Reservation::getDate)
                    .collect(Collectors.toList()));
        }

        @Override
        public String toString() {
            return String.format("Restaurante: %s, Fechas: %s", restaurant.name(), dates);
        }
    }
}