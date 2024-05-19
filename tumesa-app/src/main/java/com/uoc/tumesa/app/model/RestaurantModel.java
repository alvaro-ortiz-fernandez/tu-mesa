package com.uoc.tumesa.app.model;

import com.uoc.tumesa.repo.model.Restaurant.Comment;
import com.uoc.tumesa.repo.model.Restaurant.Images;
import com.uoc.tumesa.repo.model.Restaurant.Reservation;

import java.math.BigDecimal;
import java.util.List;

/**
 * Clase DTO para devolver al frontend restaurantes, capando así información sensible de BBDD (como el _id interno).
 * */
public record RestaurantModel(String name, String description, String address, BigDecimal rating,
        Images images, List<Comment> comments, List<Reservation> reservations) {

    public RestaurantModel(com.uoc.tumesa.repo.model.Restaurant restaurant) {
        this(restaurant.getName(), restaurant.getDescription(), restaurant.getAddress(), restaurant.getRating(),
                restaurant.getImages(), restaurant.getComments(), restaurant.getReservations());
    }
}