package com.uoc.tumesa.app.spring.web;

import com.uoc.tumesa.app.model.*;
import com.uoc.tumesa.app.model.ProfileModel.Reservation;
import com.uoc.tumesa.app.spring.sec.user.UsuarioDetailsService;
import com.uoc.tumesa.repo.Repository;
import com.uoc.tumesa.repo.dao.RestaurantsDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador con los endpoints relativos a autenticaciones.
 * */
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private Repository repository;

    @Autowired
    private UsuarioDetailsService userDetailsService;


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> login(@RequestBody UserLogin user) {
        try {
            logger.info("Autenticando al usuario '{}'...", user.user());
            LoginResult result = userDetailsService.login(user.user(), user.password());

            logger.info("Autenticación del usuario '{}' completada con resultado: {}", user.user(), result.result());
            if (result.result() != LoginResult.Result.Ok)
                return new ResponseEntity<>(new Error(result.result().getMessage()), HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>(result.user(), HttpStatus.OK);

        } catch (Exception e) {
            logger.error(String.format("Se produjo un error al autenticar al usuario '%s': ", user.user()), e);
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/registro", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> registro(@RequestBody UserSignup user) {
        try {
            logger.info("Registrando al usuario '{}'...", user.user());
            SignupResult result = userDetailsService.signup(user.user(), user.password(), user.email());

            logger.info("Registro del usuario '{}' completado con resultado: {}", user.user(), result.result());
            if (result.result() != SignupResult.Result.Ok)
                return new ResponseEntity<>(new Error(result.result().getMessage()), HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>(result.user(), HttpStatus.OK);

        } catch (Exception e) {
            logger.error(String.format("Se produjo un error al registrar al usuario '%s': ", user.user()), e);
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/perfil", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> perfil(@RequestBody UserProfile user) {
        try {
            logger.info("Cargando el perfil del usuario '{}'...", user.user());
            RestaurantsDAO restaurantsDAO = repository.getDAO(RestaurantsDAO.class);

            Long numComents = restaurantsDAO.countByUserWithComment(user.user());
            List<Reservation> reservations = restaurantsDAO.findByUserWithReservation(user.user()).stream()
                    .map(res -> new Reservation(new RestaurantModel(res), user.user()))
                    .collect(Collectors.toList());

            ProfileModel profile = new ProfileModel(numComents, reservations);

            logger.info("Perfil '{}' cargado correctamente.", profile);
            return new ResponseEntity<>(profile, HttpStatus.OK);

        } catch (Exception e) {
            logger.error(String.format("Se produjo un error cargando el perfil del usuario '%s': ", user.user()), e);
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
