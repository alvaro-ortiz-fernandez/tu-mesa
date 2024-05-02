package com.uoc.tumesa.properties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ConfigManager {

    private final Properties properties;

    public ConfigManager(URL configUrl) throws IOException {
        this.properties = new Properties();
        try (InputStream is = configUrl.openStream()) {
            this.properties.load(is);
        }
    }

    public String getProperty(String property) {
        return properties.getProperty(property);
    }
}
