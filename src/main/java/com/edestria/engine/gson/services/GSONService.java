package com.edestria.engine.gson.services;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.database.mongo.adapters.GuildTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;

public class GSONService {

    @Getter private final Gson gson;

    private final EdestriaEngine edestriaEngine;

    public GSONService(EdestriaEngine edestriaEngine) {
        this.edestriaEngine = edestriaEngine;
        this.gson = new GsonBuilder().registerTypeAdapter(GuildTypeAdapter.class, new GuildTypeAdapter(this.edestriaEngine.getGsonService())).setPrettyPrinting().create();
    }

    public String serialize(Object object) {
        return gson.toJson(object);
    }

    public <T> T deserialize(String jsonString, Class<?> clazz) {
        return (T) gson.fromJson(jsonString, clazz);
    }
}
