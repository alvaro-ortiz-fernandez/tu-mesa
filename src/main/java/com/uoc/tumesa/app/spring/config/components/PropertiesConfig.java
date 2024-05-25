package com.uoc.tumesa.app.spring.config.components;

import com.uoc.tumesa.properties.ConfigManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

/**
 * Clase de configuración de Spring Boot, con los beans que obtienen la configuración en properties del despliegue.
 * */
@Configuration
public class PropertiesConfig {

    @Bean(name = "configManager")
    public ConfigManager configManager() throws IOException {

        URL configUrl = getConfigFromClasspath()
                .orElseGet(() -> getConfigFromClassloader()
                .orElseThrow(() -> new IllegalStateException(
                        "No se encontró el archivo config.properties en el classpath ni en el classloader")));

        return new ConfigManager(configUrl);
    }

    /**
     * Método que busca el fichero 'config.properties' en el classpath.
     * Es la forma en que podemos cargar este fichero cuando ejecutamos la aplicación en un IDE en local.
     * */
    protected Optional<URL> getConfigFromClasspath() {
        return Optional.ofNullable(this.getClass().getResource("/config.properties"));
    }

    /**
     * Método que busca el fichero 'config.properties' en el classloader.
     * Es la forma en que podemos cargar este fichero cuando desplegamos la aplicación en un servidor.
     * */
    protected Optional<URL> getConfigFromClassloader() {
        return Optional.ofNullable(this.getClass().getClassLoader().getResource("config.properties"));
    }
}
