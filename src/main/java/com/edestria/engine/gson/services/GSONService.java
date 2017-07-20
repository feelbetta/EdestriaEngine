package com.edestria.engine.gson.services;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.database.mongo.adapters.GuildTypeAdapter;
import com.edestria.engine.database.mongo.adapters.UUIDTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;

import java.util.UUID;

public class GSONService {

    @Getter private final Gson gson;

    public GSONService(EdestriaEngine edestriaEngine) {
        this.gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).registerTypeAdapter(GuildTypeAdapter.class, new GuildTypeAdapter(edestriaEngine.getGsonService())).setPrettyPrinting().create();
    }

    public String serialize(Object object) {
        return gson.toJson(object);
    }

    public <T> T deserialize(String jsonString, Class<?> clazz) {
        return (T) gson.fromJson(jsonString, clazz);
    }
}
