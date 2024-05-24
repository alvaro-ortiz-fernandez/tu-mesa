package com.uoc.tumesa.app.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase de configuración de Spring Boot, para indicar los packages que contienen otras clases de configuración.
 * */
@Configuration
@EnableWebMvc
@ComponentScan({
	// Para permitir la declaración de los beans
	"com.uoc.tumesa.app.spring.config.components"
})
public class AppConfig implements WebMvcConfigurer {
}