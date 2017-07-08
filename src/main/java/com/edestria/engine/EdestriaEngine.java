package com.edestria.engine;

import com.edestria.engine.database.mongo.connection.MongoConnection;
import com.edestria.engine.database.mongo.services.MongoUpsertService;
import com.edestria.engine.files.EngineFiles;
import com.edestria.engine.logging.EngineLogger;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class EdestriaEngine extends JavaPlugin {

    /*
    * Mongo Services
    * */
    @Getter private MongoConnection mongoConnection;
    @Getter private MongoUpsertService mongoUpsertService;

    /*
    * Logging
    * */
    @Getter private EngineLogger engineLogger;

    /*
    * Files
    * */
    @Getter private EngineFiles engineFiles;

    @Override
    public void onEnable() {
        this.engineLogger = new EngineLogger(this);
        this.registerFiles();
        this.reigsterConnections();
        this.registerServices();
    }

    @Override
    public void onDisable() {
        this.mongoUpsertService.purgeExecutions();
        this.mongoConnection.disconnect();
    }

    private void reigsterConnections() {
        this.mongoConnection =
                MongoConnection.builder()
                        .host(this.engineFiles.getStringProperty("settings", "host"))
                        .port(this.engineFiles.getIntegerProperty("settings", "port"))
                        .collections(this.engineFiles.getStringListProperty("settings", "collections"))
                        .edestriaEngine(this)
                        .build();
        if (this.mongoConnection.connect()) {
            return;
        }
        Bukkit.getPluginManager().disablePlugin(this);
        Arrays.stream(Bukkit.getPluginManager().getPlugins()).filter(plugin -> plugin.getDescription().getDepend().contains(this.getName())).forEach(Bukkit.getPluginManager()::disablePlugin);
    }

    private void registerServices() {
        this.mongoUpsertService = new MongoUpsertService(this);
    }

    private void registerFiles() {
        this.engineFiles = new EngineFiles(this);
    }
}
