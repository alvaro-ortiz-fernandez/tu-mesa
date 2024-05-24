package com.uoc.tumesa.app.spring.web;

import com.uoc.tumesa.app.model.*;
import com.uoc.tumesa.app.service.AuthenticationService;
import com.uoc.tumesa.app.service.RestaurantsService;
import com.uoc.tumesa.app.spring.sec.jwt.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador con los endpoints de la API rest, para que otras plataformas se integren al sistema.
 * */
@RestController
@RequestMapping(value = "/api")
public class ApiController {

    /**
     * Interfaz para definir las ejecuciones realizadas en la API REST
     * */
    @FunctionalInterface
    public interface RequestProcessor<T> {
        ResponseEntity<?> process(T request);
    }

    private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private RestaurantsService restaurantsService;


    /**
     * Endpoint para autenticar las crendenciales indicadas.
     * */
    @PostMapping(value = "/getToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> getToken(@RequestBody UserLogin user) {
        ResponseEntity<?> response = authenticationService.autenticar(user.user(), user.password());

        if (response.getBody() instanceof UserModel)
            return new ResponseEntity<>(ApiResponse.ok(((UserModel) response.getBody()).jwtToken()), response.getStatusCode());
        else
            return proccessResponse(response);
    }

    /**
     * Endpoint para registrar un nuevo usuario con las credenciales indicadas.
     * */
    @PostMapping(value = "/users/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> createUser(@RequestBody UserSignup user) {
        ResponseEntity<?> response = authenticationService.registrar(user.user(), user.email(), user.password());

        if (response.getBody() instanceof UserModel)
            return new ResponseEntity<>(ApiResponse.ok(((UserModel) response.getBody()).jwtToken()), response.getStatusCode());
        else
            return proccessResponse(response);
    }

    /**
     * Endpoint para obtener el perfil del usuario indicado.
     * */
    @PostMapping(value = "/users/getProfile", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> getProfile(@RequestBody ApiRequest<UserProfile> request) {
        return validate(request, request.request().user(), ((user) ->
                authenticationService.obtenerPerfil(user.user())));
    }

    /**
     * Endpoint para obtener los restaurantes con el filtro indicado.
     * */
    @PostMapping(value = "/restaurants/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> getRestaurants(@RequestBody ApiRequest<FiltroBusquedaRestaurantes> request) {
        return validate(request, ((filtro) ->
                restaurantsService.buscarRestaurantes(filtro)));
    }

    /**
     * Endpoint para crear el comentario indicado.
     * */
    @PostMapping(value = "/restaurants/createComment", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> createComment(@RequestBody ApiRequest<NuevoComentario> request) {
        return validate(request, request.request().usuario(), ((comentario) ->
                restaurantsService.crearComentario(comentario.restaurante(), comentario.usuario(), comentario.comentario(), comentario.puntuacion())));
    }

    /**
     * Endpoint para crear la reserva indicada.
     * */
    @PostMapping(value = "/restaurants/createReservation", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> createReservation(@RequestBody ApiRequest<Reserva> request) {
        return validate(request, request.request().usuario(), ((reserva) ->
                restaurantsService.crearReserva(reserva)));
    }

    /**
     * Endpoint para eliminar la reserva indicada.
     * */
    @PostMapping(value = "/restaurants/deleteReservation", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> deleteReservation(@RequestBody ApiRequest<Reserva> request) {
        return validate(request, request.request().usuario(), ((reserva) ->
                restaurantsService.eliminarReserva(reserva)));
    }

    /**
     * Método encargado de validar la autenticación de la petición recibida.
     * No la valida para ningún usuario en específico.
     * */
    protected <T> ResponseEntity<?> validate(ApiRequest<T> request, RequestProcessor<T> requestProcessor) {
        return validate(request, jwtService.extractUsername(request.authToken()), requestProcessor);
    }

    /**
     * Método encargado de validar la autenticación de la petición recibida para el usuario indicado.
     * */
    protected <T> ResponseEntity<?> validate(ApiRequest<T> request, String user, RequestProcessor<T> requestProcessor) {
        boolean valid = jwtService.validateToken(request.authToken(), user);
        if (!valid)
            return new ResponseEntity<>("El token JWT Indicado no es válido.", HttpStatus.UNAUTHORIZED);
        else
            return proccessResponse(requestProcessor.process(request.request()));
    }

    /**
     * Método encargado de devolver una respuesta adecuada.
     * */
    protected ResponseEntity<?> proccessResponse(ResponseEntity<?> response) {
        if (!response.getStatusCode().isError())
            return new ResponseEntity<>(ApiResponse.ok(response.getBody()), response.getStatusCode());

        if (response.getBody() instanceof Error)
            return new ResponseEntity<>(ApiResponse.error(((Error) response.getBody()).getMessage()), response.getStatusCode());
        else
            return new ResponseEntity<>(ApiResponse.error("Se ha producido un error no controlado."), response.getStatusCode());
    }
}
