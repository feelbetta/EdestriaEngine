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

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoConnection {

    private final EdestriaEngine edestriaEngine;

    private static final String DATABASE_NAME = "edestria";
    private static final int TIME_OUT = 3000;
    private final String host;
    private final int port;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private final List<String> collections;

    @Builder
    public MongoConnection(EdestriaEngine edestriaEngine, String host, int port, List<String> collections) {
        this.edestriaEngine = edestriaEngine;
        this.host = host;
        this.port = port;
        this.collections = collections;
        Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
    }

    public void connect() {
        Bukkit.getScheduler().runTaskAsynchronously(this.edestriaEngine, () -> {
            try {
                this.mongoClient = new MongoClient(new ServerAddress(this.host, this.port), MongoClientOptions.builder().connectTimeout(MongoConnection.TIME_OUT).build());
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
