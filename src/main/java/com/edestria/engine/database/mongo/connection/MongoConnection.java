package com.edestria.engine.database.mongo.connection;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.logging.EngineLogger;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Builder;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.List;

public class MongoConnection {

    private final EdestriaEngine edestriaEngine;

    private static final String DATABASE_NAME = "edestria";
    private final String host;
    private final int port;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private final List<String> collections;

    @Builder
    public MongoConnection(EdestriaEngine edestriaEngine, String host, int port, String... collections) {
        this.edestriaEngine = edestriaEngine;
        this.host = host;
        this.port = port;
        this.collections = Arrays.asList(collections);
    }

    public void connect() {
        Bukkit.getScheduler().runTaskAsynchronously(this.edestriaEngine, () -> {
            try {
                this.mongoClient = new MongoClient(new ServerAddress(this.host, this.port), MongoClientOptions.builder().build());
                this.mongoClient.getAddress();
                this.mongoDatabase = this.mongoClient.getDatabase(MongoConnection.DATABASE_NAME);
                this.collections.forEach(this.mongoDatabase::getCollection);
                this.edestriaEngine.getEngineLogger().log(EngineLogger.LogType.INFO, "Successfully established database connection.");
            } catch (Exception exception) {
                this.edestriaEngine.getEngineLogger().log(EngineLogger.LogType.WARNING, "Unable to connect to database services.");
                Bukkit.getPluginManager().disablePlugin(this.edestriaEngine);
            }
        });
    }

    public void disconnect() {
        this.mongoClient.close();
    }

    public MongoCollection getActiveCollection(String collection) {
        if (!this.collections.contains(collection)) {
            this.edestriaEngine.getEngineLogger().log(EngineLogger.LogType.ERROR, "There is no registered collection named '" + collection + "' for usage.");
            return null;
        }
        return this.mongoDatabase.getCollection(collection);
    }
}
