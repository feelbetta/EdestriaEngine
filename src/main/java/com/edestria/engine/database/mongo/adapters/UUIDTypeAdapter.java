package com.edestria.engine.database.mongo.adapters;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.UUID;

public class UUIDTypeAdapter implements JsonDeserializer<UUID>, JsonSerializer<UUID> {

    @Override
    public JsonElement serialize(UUID src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    @Override
    public UUID deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return UUID.fromString(json.getAsString());
    }
}
