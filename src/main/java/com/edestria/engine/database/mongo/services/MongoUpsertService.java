package com.edestria.engine.database.mongo.services;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.database.mongo.MongoDocumentEntry;
import com.edestria.engine.database.mongo.MongoDocumentIdentifier;
import com.edestria.engine.Purgeable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class MongoUpsertService implements Purgeable {

    public interface MongoDocumentExecution {

        void execute();
    }

    private final EdestriaEngine edestriaEngine;

    private final Queue<MongoDocumentExecution> executions;

    public MongoUpsertService(EdestriaEngine edestriaEngine) {
        this.edestriaEngine = edestriaEngine;
        this.executions = new LinkedList<>();
    }

    public MongoUpsertService append(MongoCollection mongoCollection, MongoDocumentIdentifier mongoDocumentIdentifier, MongoDocumentEntry... mongoDocumentEntries) {
        this.executions.add(() -> {
            System.out.println("Executed on Thread: " + Thread.currentThread().getName() + " (" + Thread.currentThread().getId() + ")");
            Arrays.stream(mongoDocumentEntries).forEach(mongoDocumentEntry -> mongoCollection.updateOne(Filters.eq((String) mongoDocumentIdentifier.getIdentifier(), mongoDocumentIdentifier.getIdentifierValue()), new Document("$set", new Document((String) mongoDocumentEntry.getKey(), mongoDocumentEntry.getValue())), new UpdateOptions().upsert(true)));
        });
        return this;
    }

    public MongoUpsertService append(MongoCollection mongoCollection, MongoDocumentIdentifier mongoDocumentIdentifier, Document document) {
        this.executions.add(() -> {
            System.out.println("Executed on Thread: " + Thread.currentThread().getName() + " (" + Thread.currentThread().getId() + ")");
            mongoCollection.replaceOne(Filters.eq((String) mongoDocumentIdentifier.getIdentifier(), mongoDocumentIdentifier.getIdentifierValue() instanceof UUID ?  mongoDocumentIdentifier.getIdentifierValue().toString() :  mongoDocumentIdentifier.getIdentifierValue()), document, new UpdateOptions().upsert(true));
        });
        return this;
    }


    public void push() {
        Bukkit.getScheduler().runTaskAsynchronously(this.edestriaEngine, () -> {
            while (!this.executions.isEmpty()) {
                this.executions.poll().execute();
            }
        });
    }

    @Override
    public void purge() {
        this.executions.clear();
    }
}
