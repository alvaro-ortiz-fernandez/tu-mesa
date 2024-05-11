package com.uoc.tumesa.repo.dao;

import com.google.common.base.Strings;
import com.mongodb.client.MongoDatabase;
import com.uoc.tumesa.repo.model.Restaurant;
import com.uoc.tumesa.repo.model.Restaurant.Comment;
import com.uoc.tumesa.repo.model.Restaurant.Images;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

public class RestaurantsDAO extends AppDAO<Restaurant> {

    public RestaurantsDAO(MongoDatabase db) {
        super(db, "restaurants");
    }


    @Override
    protected Optional<Document> find(Restaurant restaurant) {
        return Optional.ofNullable(collection
                .find(eq("name", restaurant.getName()))
                .first());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Restaurant fromDocument(Document document) {
        return new Restaurant(
                document.getObjectId("_id").toString(),
                document.getString("name"),
                document.getString("description"),
                document.getString("address"),
                new Images(
                    document.get("images", Document.class).getString("main"),
                    document.get("images", Document.class).get("photos", java.util.ArrayList.class)
                ),
                document.getList("comments", Document.class).stream()
                        .map(doc -> new Comment(
                            doc.getString("user"),
                            doc.getString("content"),
                            BigDecimal.valueOf(doc.getDouble("rating")),
                            doc.getDate("date").toInstant().atZone(ZoneId.systemDefault())
                        ))
                        .collect(Collectors.toList()));
    }

    @Override
    protected Document toDocument(Restaurant restaurant) {
        Document document =
            new Document("name", restaurant.getName())
                .append("description", restaurant.getDescription())
                .append("address", restaurant.getAddress())
                .append("images",
                        new Document("main", restaurant.getImages().getMain())
                            .append("photos", restaurant.getImages().getPhotos()))
                .append("comments", restaurant.getComments().stream()
                    .map(comment ->
                        new Document("user", comment.getUser())
                            .append("content", comment.getContent())
                            .append("rating", comment.getRating().doubleValue())
                            .append("date", comment.getDate().toInstant()))
                    .collect(Collectors.toList()));

        if (!Strings.isNullOrEmpty(restaurant.getId()))
            document.append("_id", new ObjectId(restaurant.getId()));

        return document;
    }
}
