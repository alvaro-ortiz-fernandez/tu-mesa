package com.uoc.tumesa.repo.dao;

import com.google.common.base.Strings;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.uoc.tumesa.repo.model.Restaurant.Comment;
import com.uoc.tumesa.repo.model.Restaurant.Images;
import com.uoc.tumesa.repo.model.Restaurant.Reservation;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.uoc.tumesa.repo.model.Restaurant;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.*;

public class RestaurantsDAO extends AppDAO<Restaurant> {

    public RestaurantsDAO(MongoDatabase db) {
        super(db, "restaurants");
    }

    public Optional<Restaurant> findByName(String name) {
        return find(collection
                .find(eq("name", name))
                .first());
    }

    public List<Restaurant> findByTerm(@Nullable String term) {
        if (Strings.isNullOrEmpty(term))
            return findAll();
        else
            return findByFilter(or(
                        regex("name", term),
                        regex("description", term)
                    ));
    }

    public List<Restaurant> findByUserWithReservation(@Nullable String user) {
        return findByFilter(
                Filters.elemMatch("reservations", Filters.eq("user", user)));
    }

    public Long countByUserWithComment(String user) {

        Document result = collection.aggregate(Arrays.asList(
                new Document("$match", new Document("comments.user", user)),
                new Document("$unwind", "$comments"),
                new Document("$match", new Document("comments.user", user)),
                new Document("$group", new Document("_id", null).append("totalComments", new Document("$sum", 1)))
            )).first();

        return result != null ? result.getInteger("totalComments") : 0L;
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
                getList(document, "comments").stream()
                        .map(doc -> new Comment(
                            doc.getString("user"),
                            doc.getString("content"),
                            BigDecimal.valueOf(doc.getDouble("rating")),
                            doc.getDate("date").toInstant().atZone(ZoneId.systemDefault())
                        ))
                        .collect(Collectors.toList()),
                getList(document, "reservations").stream()
                        .map(doc -> new Reservation(
                                doc.getString("user"),
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
                    .collect(Collectors.toList()))
                .append("reservations", restaurant.getReservations().stream()
                    .map(reservation ->
                        new Document("user", reservation.getUser())
                            .append("date", reservation.getDate().toInstant()))
                    .collect(Collectors.toList()));

        if (!Strings.isNullOrEmpty(restaurant.getId()))
            document.append("_id", new ObjectId(restaurant.getId()));

        return document;
    }
}
