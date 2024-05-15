package com.uoc.tumesa.app.spring.config.components;

import com.uoc.tumesa.properties.ConfigManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Clase de configuración de Spring Boot, con los beans que obtienen la configuración en properties del despliegue.
 * */
@Configuration
public class PropertiesConfig {

    @Bean(name = "configManager")
    public ConfigManager configManager() throws IOException {
        URL configUrl = Objects.requireNonNull(this.getClass().getResource("/config.properties"));
        return new ConfigManager(configUrl);
    }
}
