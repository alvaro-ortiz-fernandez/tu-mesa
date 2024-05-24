package com.uoc.tumesa.app.service;

import com.uoc.tumesa.app.model.*;
import com.uoc.tumesa.app.spring.sec.user.UsuarioDetailsService;
import com.uoc.tumesa.repo.Repository;
import com.uoc.tumesa.repo.dao.RestaurantsDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio encargado de gestionar las funcionalidades relativas a autenticaciones.
 * */
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final Repository repository;
    private final UsuarioDetailsService userDetailsService;

    public AuthenticationService(Repository repository, UsuarioDetailsService userDetailsService) {
        this.repository = repository;
        this.userDetailsService = userDetailsService;
    }


    /**
     * Método encargado de autenticar las crendenciales indicadas.
     * */
    public ResponseEntity<?> autenticar(String user, String password) {
        try {
            logger.info("Autenticando al usuario '{}'...", user);
            LoginResult result = userDetailsService.login(user, password);

            logger.info("Autenticación del usuario '{}' completada con resultado: {}", user, result.result());
            if (result.result() != LoginResult.Result.Ok)
                return new ResponseEntity<>(new Error(result.result().getMessage()), HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>(result.user(), HttpStatus.OK);

        } catch (Exception e) {
            logger.error(String.format("Se produjo un error al autenticar al usuario '%s': ", user), e);
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método que registra un nuevo usuario con las credenciales indicadas.
     * */
    public ResponseEntity<?> registrar(String user, String email, String password) {
        try {
            logger.info("Registrando al usuario '{}'...", user);
            SignupResult result = userDetailsService.signup(user, password, email);

            logger.info("Registro del usuario '{}' completado con resultado: {}", user, result.result());
            if (result.result() != SignupResult.Result.Ok)
                return new ResponseEntity<>(new Error(result.result().getMessage()), HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>(result.user(), HttpStatus.OK);

        } catch (Exception e) {
            logger.error(String.format("Se produjo un error al registrar al usuario '%s': ", user), e);
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método que obtiene el perfil del usuario indicado.
     * */
    public ResponseEntity<?> obtenerPerfil(String user) {
        try {
            logger.info("Cargando el perfil del usuario '{}'...", user);
            RestaurantsDAO restaurantsDAO = repository.getDAO(RestaurantsDAO.class);

            Long numComents = restaurantsDAO.countByUserWithComment(user);
            List<ProfileModel.Reservation> reservations = restaurantsDAO.findByUserWithReservation(user).stream()
                    .map(res -> new ProfileModel.Reservation(new RestaurantModel(res), user))
                    .collect(Collectors.toList());

            ProfileModel profile = new ProfileModel(numComents, reservations);

            logger.info("Perfil '{}' cargado correctamente.", profile);
            return new ResponseEntity<>(profile, HttpStatus.OK);

        } catch (Exception e) {
            logger.error(String.format("Se produjo un error cargando el perfil del usuario '%s': ", user), e);
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
