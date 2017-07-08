package com.edestria.engine.database.mongo.services;

import com.edestria.engine.EdestriaEngine;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MongoRetrievalService {

    private final EdestriaEngine edestriaEngine;

    public MongoRetrievalService(EdestriaEngine edestriaEngine) {
        this.edestriaEngine = edestriaEngine;
    }

    public boolean exists(MongoCollection collection, String key, Object value) {
        try {
            return CompletableFuture.supplyAsync(() -> collection.find(new Document(key, value)).first() != null).get();
        } catch (InterruptedException | ExecutionException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public Document get(MongoCollection mongoCollection, String identifier, Object value) {
        try {
            return (Document) CompletableFuture.supplyAsync(() -> mongoCollection.find(Filters.eq(identifier, value)).first()).get();
        } catch (InterruptedException | ExecutionException exception) {
            exception.printStackTrace();
        }
        return new Document();
    }
}
