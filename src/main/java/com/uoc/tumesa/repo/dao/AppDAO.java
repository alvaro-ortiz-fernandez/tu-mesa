package com.uoc.tumesa.repo.dao;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.lang.Nullable;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import com.uoc.tumesa.repo.model.RepoEntity;

import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.*;

/**
 * Clase base con la funcionalidad común a las clases encargadas
 * de manejar una colección (tabla) de BBDD.
 * */
public abstract class AppDAO<T extends RepoEntity> {

    protected final MongoCollection<Document> collection;

    public AppDAO(MongoDatabase db, String collection) {
        this.collection = db.getCollection(collection);
    }

    public Optional<T> find(String id) {
        return find(collection
                .find(eq("_id", id))
                .first());
    }

    public List<T> findAll() {
        return toList(collection.find());
    }

    public T save(T entity) {
        return find(entity)
                .map(currentDoc -> update(currentDoc, entity))
                .orElseGet(() -> create(entity));
    }

    public void delete(T entity) {
        if (!Strings.isNullOrEmpty(entity.getId())) {
            collection.deleteOne(eq("_id", new ObjectId(entity.getId())));
        } else {
            find(entity)
                .ifPresent(doc -> collection.deleteOne(eq("_id", doc.getObjectId("_id"))));
        }
    }

    protected T create(T entity) {
        InsertOneResult result = collection.insertOne(toDocument(entity));
        if (result.getInsertedId() != null)
            entity.setId(result.getInsertedId().asObjectId().getValue().toString());

        return entity;
    }

    protected T update(Document currentDoc, T entity) {

        Document query = new Document()
                .append("_id", currentDoc.getObjectId("_id"));

        Document update = new Document()
                .append("$set", toDocument(entity));

        collection.updateOne(query, update);

        return entity;
    }

    protected Optional<T> find(@Nullable Document document) {
        return Optional.ofNullable(document)
                .map(this::fromDocument);
    }

    protected List<T> findByFilter(Bson filter) {
        return toList(collection.find(filter));
    }

    protected List<T> toList(FindIterable<Document> queryResult) {
        List<T> result = Lists.newArrayList();

        try (MongoCursor<Document> cursor = queryResult.cursor()) {
            while (cursor.hasNext())
                result.add(fromDocument(cursor.next()));
        }

        return result;
    }

    protected List<Document> getList(Document document, String field) {
        List<Document> list = document.getList(field, Document.class);
        return (list != null) ? list : Lists.newArrayList();
    }

    protected abstract Optional<Document> find(T entity);

    protected abstract T fromDocument(Document document);

    protected abstract Document toDocument(T entity);
}
