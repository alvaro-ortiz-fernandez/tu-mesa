package com.uoc.tumesa.app.spring.web;

import com.uoc.tumesa.app.model.*;
import com.uoc.tumesa.app.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador con los endpoints relativos a autenticaciones.
 * */
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;


    /**
     * Endpoint para autenticar las crendenciales indicadas.
     * */
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> login(@RequestBody UserLogin user) {
        return authenticationService.autenticar(user.user(), user.password());
    }

    /**
     * Endpoint para registrar un nuevo usuario con las credenciales indicadas.
     * */
    @PostMapping(value = "/registro", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> registro(@RequestBody UserSignup user) {
        return authenticationService.registrar(user.user(), user.email(), user.password());
    }

    /**
     * Endpoint para obtener el perfil del usuario indicado.
     * */
    @PostMapping(value = "/perfil", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> perfil(@RequestBody UserProfile user) {
        return authenticationService.obtenerPerfil(user.user());
    }
}
