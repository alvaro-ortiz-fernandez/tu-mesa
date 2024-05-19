package com.uoc.tumesa.app.service;

import com.uoc.tumesa.app.model.FiltroBusquedaRestaurantes;
import com.uoc.tumesa.app.model.Reserva;
import com.uoc.tumesa.app.model.RestaurantModel;
import com.uoc.tumesa.repo.Repository;
import com.uoc.tumesa.repo.dao.RestaurantsDAO;
import com.uoc.tumesa.repo.model.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio encargado de gestionar las funcionalidades relativas a restaurantes.
 * */
public class RestaurantsService {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantsService.class);

    private final Repository repository;

    public RestaurantsService(Repository repository) {
        this.repository = repository;
    }

    /**
     * Método que buscar restaurantes con el filtro indicado.
     * */
    public ResponseEntity<?> buscarRestaurantes(FiltroBusquedaRestaurantes filtro) {
        try {
            logger.info("Obteniendo restaurantes con filtro [{}]...", filtro);
            List<RestaurantModel> restaurants = repository.getDAO(RestaurantsDAO.class)
                    .findByTerm(filtro.termino()).stream()
                    .filter(res -> filtro.puntuacion() == null || res.getRating().compareTo(filtro.puntuacion()) >= 0)
                    .map(RestaurantModel::new)
                    .collect(Collectors.toList());

            logger.info("Nº de restaurantes obtenidos: {}", restaurants.size());
            return new ResponseEntity<>(restaurants, HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Se produjo un error al obtener los restaurantes: ", e);
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método que obtiene el restaurante con el nombre indicado.
     * */
    public ResponseEntity<?> obtenerRestaurante(String nombre) {
        try {
            logger.info("Obteniendo restaurante con nombre [{}]...", nombre);
            Optional<RestaurantModel> restaurant = repository.getDAO(RestaurantsDAO.class)
                    .findByName(nombre)
                    .map(RestaurantModel::new);

            logger.info("Restaurante encontrado -> {}", restaurant.isPresent());

            if (restaurant.isEmpty())
                return new ResponseEntity<>(new Error(String.format(
                        "No se encontró el restaurante con nombre %s", nombre)), HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>(restaurant.get(), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Se produjo un error al obtene el restaurante: ", e);
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método que crea un comentario para el restaurante y usuario indicados.
     * */
    public ResponseEntity<?> crearComentario(String restaurante, String usuario, String comentario, BigDecimal puntuacion) {
        try {
            logger.info("Creando comentario [{}]...", comentario);
            RestaurantsDAO restaurantsDAO = repository.getDAO(RestaurantsDAO.class);
            Optional<Restaurant> restaurant = restaurantsDAO.findByName(restaurante);

            if (restaurant.isEmpty())
                return new ResponseEntity<>(new Error(String.format(
                        "No se encontró el restaurante con nombre %s", restaurante)), HttpStatus.BAD_REQUEST);

            restaurant.get().getComments().add(new Restaurant.Comment(usuario, comentario, puntuacion, ZonedDateTime.now()));
            restaurant = Optional.of(restaurantsDAO.save(restaurant.get()));

            logger.info("Comentario creado correctamente.");
            return new ResponseEntity<>(new RestaurantModel(restaurant.get()), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Se produjo un error guardando el comentario: ", e);
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método que crea la reserva indicada.
     * */
    public ResponseEntity<?> crearReserva(Reserva reserva) {
        try {
            logger.info("Creando reserva [{}]...", reserva);
            RestaurantsDAO restaurantsDAO = repository.getDAO(RestaurantsDAO.class);
            Optional<Restaurant> restaurant = restaurantsDAO.findByName(reserva.restaurante());

            if (restaurant.isEmpty())
                return new ResponseEntity<>(new Error(String.format(
                        "No se encontró el restaurante con nombre %s", reserva.restaurante())), HttpStatus.BAD_REQUEST);

            restaurant.get().getReservations().add(new Restaurant.Reservation(reserva.usuario(), reserva.getFechaFormateada()));
            restaurant = Optional.of(restaurantsDAO.save(restaurant.get()));

            logger.info("Reserva [{}] creada correctamente.", reserva);
            return new ResponseEntity<>(new RestaurantModel(restaurant.get()), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Se produjo un error guardando la reserva: ", e);
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método que elimina la reserva indicada.
     * */
    public ResponseEntity<?> eliminarReserva(Reserva reserva) {
        try {
            logger.info("Eliminando reserva [{}]...", reserva);
            RestaurantsDAO restaurantsDAO = repository.getDAO(RestaurantsDAO.class);
            Optional<Restaurant> restaurant = restaurantsDAO.findByName(reserva.restaurante());

            if (restaurant.isEmpty())
                return new ResponseEntity<>(new Error(String.format(
                        "No se encontró el restaurante con nombre %s", reserva.restaurante())), HttpStatus.BAD_REQUEST);

            boolean eliminada = restaurant.get().getReservations().removeIf(reservation ->
                    reservation.getUser().equals(reserva.usuario()) && reserva.esIgual(reservation.getDate()));

            if (!eliminada)
                return new ResponseEntity<>(new Error(String.format("No se encontró la reserva con fecha %s " +
                        "para el usuario %s", reserva.fecha(), reserva.usuario())), HttpStatus.BAD_REQUEST);

            restaurant = Optional.of(restaurantsDAO.save(restaurant.get()));

            logger.info("Reserva [{}] eliminada correctamente.", reserva);
            return new ResponseEntity<>(new RestaurantModel(restaurant.get()), HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Se produjo un error guardando la reserva: ", e);
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
