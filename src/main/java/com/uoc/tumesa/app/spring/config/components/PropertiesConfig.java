package com.uoc.tumesa.app.spring.config.components;

import com.google.common.collect.Lists;
import com.uoc.tumesa.properties.ConfigManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * Clase de configuración de Spring Boot, con los beans que obtienen la configuración en properties del despliegue.
 * */
@Configuration
public class PropertiesConfig {

    public static final String FILE = "config.properties";

    @Bean(name = "configManager")
    public ConfigManager configManager() throws IOException {

        URL configUrl = getConfigFromClasspath()
                .orElseGet(() -> getConfigFromClassloader()
                .orElseThrow(() -> new IllegalStateException(String.format(
                        "No se encontró el archivo %s en el classpath ni en el classloader " +
                                "(URLs en el classloader: %s)", FILE, getConfigPathsFromClassloader()))));

        return new ConfigManager(configUrl);
    }

    /**
     * Método que busca el fichero 'config.properties' en el classpath.
     * Es la forma en que podemos cargar este fichero cuando ejecutamos la aplicación en un IDE en local.
     * */
    protected Optional<URL> getConfigFromClasspath() {
        return Optional.ofNullable(this.getClass().getResource(String.format("/%s", FILE)));
    }

    /**
     * Método que busca el fichero 'config.properties' en el classloader.
     * Es la forma en que podemos cargar este fichero cuando desplegamos la aplicación en un servidor.
     * */
    protected Optional<URL> getConfigFromClassloader() {
        return Optional.ofNullable(this.getClass().getClassLoader().getResource(FILE));
    }

    /**
     * Método que obtiene la lista de URLs del fichero 'config.properties' en el classloader.
     * */
    protected List<URL> getConfigPathsFromClassloader() {
        try {
            List<URL> urls = Lists.newArrayList();
            this.getClass().getClassLoader().getResources(FILE).asIterator().forEachRemaining(urls::add);
            this.getClass().getClassLoader().getResources(String.format("/%s", FILE)).asIterator().forEachRemaining(urls::add);
            this.getClass().getClassLoader().resources(FILE).forEach(urls::add);
            this.getClass().getClassLoader().resources(String.format("/%s", FILE)).forEach(urls::add);

            return urls;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
