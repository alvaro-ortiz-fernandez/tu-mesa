package com.uoc.tumesa.app.spring.config.components;

import com.uoc.tumesa.repo.Repository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean(name = "repository")
    public Repository repository() {
        return new Repository();
    }
}