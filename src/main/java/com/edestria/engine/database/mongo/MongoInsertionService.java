package com.edestria.engine.database.mongo;

import com.edestria.engine.EdestriaEngine;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.LinkedList;
import java.util.Queue;

public class MongoInsertionService {

    private final EdestriaEngine edestriaEngine;

    private final Queue<MongoDocumentExecution> executions;

    public MongoInsertionService(EdestriaEngine edestriaEngine) {
        this.edestriaEngine = edestriaEngine;
        this.executions = new LinkedList<>();
    }

    public MongoInsertionService append(MongoCollection mongoCollection, String key, MongoDocumentIdentifier mongoDocumentIdentifier, MongoDocumentEntry mongoDocumentEntry) {
        this.executions.add(() -> mongoCollection.updateOne(Filters.eq(key, mongoDocumentIdentifier.getIdentifier()), new Document("$set", new Document((String) mongoDocumentEntry.getKey(), mongoDocumentEntry.getValue()))));
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
