package com.uoc.tumesa.repo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.uoc.tumesa.properties.ConfigManager;
import com.uoc.tumesa.repo.dao.AppDAO;
import com.uoc.tumesa.repo.dao.RestaurantsDAO;
import com.uoc.tumesa.repo.dao.UsersDAO;

/**
 * Clase encargada de manejar la conexión con la BBDD.
 * */
public class Repository {

    public static String REPOSITORY_URL = "repository.connection";
    public static String REPOSITORY_DB  = "repository.db";

    private static MongoClient client;
    private final MongoDatabase db;

    public Repository(ConfigManager config) {
        // Creamos la conexión MongoDB
        client = MongoClients.create(config.getProperty(REPOSITORY_URL));

        // Y obtenemos la base de datos
        this.db = client.getDatabase(config.getProperty(REPOSITORY_DB));
    }

    @SuppressWarnings("unchecked")
    public <T extends AppDAO<?>> T getDAO(Class<T> daoClass) {

        if (UsersDAO.class.equals(daoClass))
            return (T) new UsersDAO(db);
        else if (RestaurantsDAO.class.equals(daoClass))
            return (T) new RestaurantsDAO(db);
        else
            throw new IllegalStateException(String.format("DAO %s no registrado.", daoClass.getName()));
    }

    public static void shutdown() {
        if (client != null)
            client.close();
    }
}
