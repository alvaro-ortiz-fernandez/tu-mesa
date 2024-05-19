package com.uoc.tumesa.app.spring.config.components;

import com.uoc.tumesa.app.service.AuthenticationService;
import com.uoc.tumesa.app.service.RestaurantsService;
import com.uoc.tumesa.app.spring.sec.user.UsuarioDetailsService;
import com.uoc.tumesa.repo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración de Spring Boot, con los beans de los servicios de la aplicación.
 * */
@Configuration
public class ServiceConfig {

    @Autowired
    private Repository repository;

    @Autowired
    private UsuarioDetailsService userDetailsService;


    @Bean(name = "authenticationService")
    public AuthenticationService authenticationService() {
        return new AuthenticationService(repository, userDetailsService);
    }

    @Bean(name = "restaurantsService")
    public RestaurantsService restaurantsService() {
        return new RestaurantsService(repository);
    }
}
