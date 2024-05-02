package com.uoc.tumesa.app.spring.config.components;

import com.uoc.tumesa.properties.ConfigManager;
import com.uoc.tumesa.repo.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Autowired
    private ConfigManager configManager;

    @Bean(name = "repository")
    public Repository repository() {
        return new Repository(configManager);
    }
}