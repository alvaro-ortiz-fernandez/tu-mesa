package com.uoc.tumesa.repo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.uoc.tumesa.properties.ConfigManager;
import com.uoc.tumesa.repo.dao.UsersDAO;

/**
 * Clase encargada de manejar la conexión con la BBDD.
 * */
public class Repository {

    private static MongoClient client;
    private final MongoDatabase db;

    public Repository(ConfigManager config) {
        // Creamos la conexión MongoDB
        client = MongoClients.create(config.getProperty("repository.connection"));

        // Y obtenemos la base de datos
        this.db = client.getDatabase(config.getProperty("repository.db"));

        System.out.println("OK");
    }

    @SuppressWarnings("unchecked")
    public <T extends UsersDAO> T getDAO(Class<T> daoClass) {

        if (UsersDAO.class.equals(daoClass))
            return (T) new UsersDAO(db);
        else
            throw new IllegalStateException(String.format("DAO %s no registrado.", daoClass.getName()));
    }

    public static void shutdown() {
        if (client != null)
            client.close();
    }
}
