package com.edestria.engine;

import com.edestria.engine.database.mongo.connection.MongoConnection;
import com.edestria.engine.database.mongo.services.MongoInsertionService;
import com.edestria.engine.logging.EngineLogger;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class EdestriaEngine extends JavaPlugin {

    /*
    * Mongo Services
    * */
    @Getter private MongoConnection mongoConnection;
    @Getter private MongoInsertionService mongoInsertionService;

    /*
    * Logging
    * */
    @Getter private EngineLogger engineLogger;

    @Override
    public void onEnable() {
        this.registerServices();
        this.engineLogger = new EngineLogger(this);
        this.mongoConnection =
                MongoConnection.builder()
                .host("localhost")
                .port(27017)
                .collections("profiles", "holograms", "characters")
                .edestriaEngine(this)
                .build();
        if (this.mongoConnection.connect()) {
            return;
        }
        setEnabled(false);

    }

    @Override
    public void onDisable() {
        this.mongoInsertionService.purgeExecutions();
    }

    private void registerServices() {
        this.mongoInsertionService = new MongoInsertionService(this);
    }
}
