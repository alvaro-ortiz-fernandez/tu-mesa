package com.uoc.tumesa.properties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Clase para manejar la configuración de la aplicación.
 * */
public class ConfigManager {

    private final Properties properties;

    public ConfigManager(Properties properties) {
        this.properties = properties;
    }

    public ConfigManager(URL configUrl) {
        this.properties = new Properties();
        try (InputStream is = configUrl.openStream()) {
            this.properties.load(is);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public String getProperty(String property) {
        return properties.getProperty(property);
    }
}
