package com.edestria.engine.database.mongo.adapters;

import com.edestria.engine.gson.services.GSONService;
import com.edestria.engine.guilds.Guild;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.UUID;

public class GuildTypeAdapter implements JsonSerializer<Guild>, JsonDeserializer<Guild> {

    private final GSONService gsonService;

    public GuildTypeAdapter(GSONService gsonService) {
        this.gsonService = gsonService;
    }

    @Override
    public JsonElement serialize(Guild guild, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject element = new JsonObject();
        element.addProperty("created", guild.getCreated());

        element.addProperty("name", guild.getName());

        element.addProperty("leader", guild.getLeader().toString());

        JsonArray members = new JsonArray();
        guild.getMembers().stream().map(UUID::toString).forEach(members::add);
        element.add("members", members);
        return element;
    }

    @Override
    public Guild deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Guild guild = new Guild(jsonElement.getAsJsonObject().get("name").getAsString());
        guild.setCreated(jsonElement.getAsJsonObject().get("created").getAsString());
        guild.setLeader(UUID.fromString(jsonElement.getAsJsonObject().get("leader").getAsString()));
        guild.setMembers(gsonService.getGson().fromJson(jsonElement.getAsJsonObject().get("members").getAsJsonObject().toString(), HashSet.class));
        return guild;
    }
}
