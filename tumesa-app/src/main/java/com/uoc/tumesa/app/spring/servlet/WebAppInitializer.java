package com.uoc.tumesa.app.spring.servlet;

import com.uoc.tumesa.app.spring.config.AppConfig;
import com.uoc.tumesa.app.spring.config.SpringWebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Clase de configuración de Spring Boot, para indicar las clases de arranque de la aplicación.
 * */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { 
			AppConfig.class
		};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { 
			SpringWebConfig.class
		};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}