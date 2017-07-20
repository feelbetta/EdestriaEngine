package com.edestria.engine.guilds.services;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.data.DataTracker;
import com.edestria.engine.guilds.Guild;

import java.util.TreeMap;

public class GuildTracker extends DataTracker<Guild, String> {

    private static final String COLLECTION_NAME = "guilds";

    public GuildTracker(EdestriaEngine edestriaEngine) {
        super(edestriaEngine, "name", edestriaEngine.getMongoConnection().getActiveCollection(GuildTracker.COLLECTION_NAME), new TreeMap<>(String.CASE_INSENSITIVE_ORDER), Guild::new);

    }
}