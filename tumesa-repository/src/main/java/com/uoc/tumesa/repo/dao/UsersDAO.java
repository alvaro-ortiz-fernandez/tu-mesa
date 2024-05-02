package com.uoc.tumesa.repo.dao;

import com.google.common.base.Strings;
import com.mongodb.client.MongoDatabase;
import com.uoc.tumesa.repo.model.User;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Optional;

import static com.mongodb.client.model.Filters.*;

public class UsersDAO extends AppDAO<User> {

    public UsersDAO(MongoDatabase db) {
        super(db, "users");
    }


    public Optional<User> findByName(String username) {
        return find(collection
                .find(eq("name", username))
                .first());
    }

    @Override
    protected Optional<Document> find(User user) {
        return Optional.ofNullable(collection
                .find(eq("name", user.getName()))
                .first());
    }

    @Override
    protected User fromDocument(Document document) {
        return new User(
                document.getObjectId("_id").toString(),
                document.getString("name"),
                document.getString("password"),
                document.getString("email"));
    }

    @Override
    protected Document toDocument(User user) {
        Document document =
            new Document("name", user.getName())
                .append("password", user.getPassword())
                .append("email", user.getEmail());

        if (!Strings.isNullOrEmpty(user.getId()))
            document.append("_id", new ObjectId(user.getId()));

        return document;
    }
}
