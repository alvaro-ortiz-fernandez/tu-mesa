package com.uoc.tumesa.app.spring.config.components;

import com.uoc.tumesa.properties.ConfigManager;
import com.uoc.tumesa.repo.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Properties;

/**
 * Clase de configuración de Spring Boot, con los beans que obtienen la configuración en properties del despliegue.
 * */
@Configuration
public class PropertiesConfig {

    @Bean(name = "configManager")
    public ConfigManager configManager() throws IOException {

        Properties properties = new Properties();
        properties.setProperty(Repository.REPOSITORY_URL, System.getProperty("DB_URL"));
        properties.setProperty(Repository.REPOSITORY_DB, System.getProperty("DB_NAME"));

        return new ConfigManager(properties);
    }
}
