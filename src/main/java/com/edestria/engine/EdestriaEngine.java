package com.edestria.engine;

import com.edestria.engine.database.mongo.services.MongoInsertionService;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class EdestriaEngine extends JavaPlugin {

    @Getter private MongoInsertionService mongoInsertionService;

    @Override
    public void onEnable() {
        this.registerServices();
    }

    @Override
    public void onDisable() {
        this.mongoInsertionService.purgeExecutions();
    }

    private void registerServices() {
        this.mongoInsertionService = new MongoInsertionService(this);
    }
}
