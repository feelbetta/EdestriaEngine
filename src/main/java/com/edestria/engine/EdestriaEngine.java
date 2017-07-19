package com.edestria.engine;

import com.edestria.engine.commands.EFLSEF;
import com.edestria.engine.database.mongo.connection.MongoConnection;
import com.edestria.engine.database.mongo.services.MongoRetrievalService;
import com.edestria.engine.database.mongo.services.MongoUpsertService;
import com.edestria.engine.display.menus.manager.MenuManager;
import com.edestria.engine.eplayers.services.EPlayerTracker;
import com.edestria.engine.files.EngineFiles;
import com.edestria.engine.gson.services.GSONService;
import com.edestria.engine.logging.EngineLogger;
import com.edestria.engine.timers.service.TimerService;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class EdestriaEngine extends JavaPlugin {

    /*
    * Mongo Services
    * */
    @Getter private MongoConnection mongoConnection;
    @Getter private MongoRetrievalService mongoRetrievalService;
    @Getter private MongoUpsertService mongoUpsertService;

    /*
    * Logging
    * */
    @Getter private EngineLogger engineLogger;

    /*
    * Files
    * */
    @Getter private EngineFiles engineFiles;

    /*
    * Object serialization & deserialization
    * */
    @Getter private GSONService gsonService;

    /*
    * Timer Services
    * */
    @Getter private TimerService timerService;

    /*
    * EPlayer Services
    * */
    @Getter private EPlayerTracker ePlayerService;

    /*
    * Menu Manager
    * */
    @Getter private MenuManager menuManager;

    @Override
    public void onEnable() {
        this.engineLogger = new EngineLogger(this);
        this.registerFiles();
        this.reigsterConnections();
        this.registerServices();
        this.registerManagers();
        new EFLSEF(this);
    }

    @Override
    public void onDisable() {
        this.mongoUpsertService.purge();
        this.timerService.purge();
        this.ePlayerService.purge();
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
        this.mongoRetrievalService = new MongoRetrievalService(this);
        this.mongoUpsertService = new MongoUpsertService(this);
        this.timerService = new TimerService(this);
        this.ePlayerService = new EPlayerTracker(this);
        this.gsonService = new GSONService(this);
    }

    private void registerManagers() {
        this.menuManager = new MenuManager(this);
    }

    private void registerFiles() {
        this.engineFiles = new EngineFiles(this);
    }
}
