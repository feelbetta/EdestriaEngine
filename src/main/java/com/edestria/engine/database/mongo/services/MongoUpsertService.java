package com.edestria.engine.database.mongo.services;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.database.mongo.MongoDocumentEntry;
import com.edestria.engine.database.mongo.MongoDocumentIdentifier;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.LinkedList;
import java.util.Queue;

public class MongoUpsertService {

    public interface MongoDocumentExecution {

        void execute();
    }

    private final EdestriaEngine edestriaEngine;

    private final Queue<MongoDocumentExecution> executions;

    public MongoUpsertService(EdestriaEngine edestriaEngine) {
        this.edestriaEngine = edestriaEngine;
        this.executions = new LinkedList<>();
    }

    public MongoUpsertService append(MongoCollection mongoCollection, MongoDocumentIdentifier mongoDocumentIdentifier, MongoDocumentEntry mongoDocumentEntry) {
        this.executions.add(() -> mongoCollection.updateOne(Filters.eq((String) mongoDocumentIdentifier.getIdentifier(), mongoDocumentIdentifier.getIdentifierValue()), new Document("$set", new Document((String) mongoDocumentEntry.getKey(), mongoDocumentEntry.getValue())), new UpdateOptions().upsert(true)));
        return this;
    }

    public void push() {
        Bukkit.getScheduler().runTaskAsynchronously(this.edestriaEngine, () -> {
            while (!this.executions.isEmpty()) {
                this.executions.poll().execute();
            }
        });
    }

    public void purgeExecutions() {
        this.executions.clear();
    }
}
