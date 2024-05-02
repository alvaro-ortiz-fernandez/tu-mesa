package com.uoc.tumesa.app.spring.web;

import com.uoc.tumesa.app.model.LoginResult;
import com.uoc.tumesa.app.model.LoginUser;
import com.uoc.tumesa.app.model.SignupResult;
import com.uoc.tumesa.app.model.SignupUser;
import com.uoc.tumesa.app.spring.sec.user.UsuarioDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UsuarioDetailsService userDetailsService;


    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> login(@RequestBody LoginUser user) {
        try {
            logger.info("Autenticando al usuario '{}'...", user.user());
            LoginResult result = userDetailsService.login(user.user(), user.password());

            logger.info("Autenticación del usuario '{}' completada con resultado: {}", user.user(), result.result());
            if (result.result() != LoginResult.Result.Ok)
                return new ResponseEntity<>(new Error(result.result().getMessage()), HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>(result.user().jwtToken(), HttpStatus.OK);

        } catch (Exception e) {
            logger.error(String.format("Se produjo un error al autenticar al usuario '%s': ", user.user()), e);
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/registro", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<?> registro(@RequestBody SignupUser user) {
        try {
            logger.info("Registrando al usuario '{}'...", user.user());
            SignupResult result = userDetailsService.signup(user.user(), user.password(), user.email());

            logger.info("Registro del usuario '{}' completado con resultado: {}", user.user(), result.result());
            if (result.result() != SignupResult.Result.Ok)
                return new ResponseEntity<>(new Error(result.result().getMessage()), HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<>(result.user().jwtToken(), HttpStatus.OK);

        } catch (Exception e) {
            logger.error(String.format("Se produjo un error al registrar al usuario '%s': ", user.user()), e);
            return new ResponseEntity<>(new Error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
