package com.edestria.engine.database.mongo.services;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.database.mongo.MongoDocumentIdentifier;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MongoRetrievalService {

    public class MongoObject<T> {

        private final T type;

        public MongoObject(T type) {
            this.type = type;
        }
    }

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

    public Document get(MongoCollection mongoCollection, MongoDocumentIdentifier mongoDocumentIdentifier) {
        try {
            return (Document) CompletableFuture.supplyAsync(() -> mongoCollection.find(Filters.eq((String) mongoDocumentIdentifier.getIdentifier(), mongoDocumentIdentifier.getIdentifierValue())).first()).get();
        } catch (InterruptedException | ExecutionException exception) {
            exception.printStackTrace();
        }
        return new Document();
    }

    public MongoObject<Object> get(MongoCollection mongoCollection, MongoDocumentIdentifier mongoDocumentIdentifier, String key) {
        return new MongoObject<>(this.get(mongoCollection, mongoDocumentIdentifier).get(key));
    }
}
