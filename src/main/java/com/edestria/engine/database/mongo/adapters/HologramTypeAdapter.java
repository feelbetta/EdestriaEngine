package com.edestria.engine.database.mongo.adapters;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.display.holograms.Hologram;
import com.edestria.engine.gson.services.GSONService;
import com.edestria.engine.utils.locations.LocationUtil;
import com.google.common.collect.ImmutableSortedMap;
import com.google.gson.*;
import org.bukkit.Bukkit;

import java.lang.reflect.Type;
import java.util.*;

public class HologramTypeAdapter implements JsonSerializer<Hologram>, JsonDeserializer<Hologram> {

    private final GSONService gsonService;

    public HologramTypeAdapter(GSONService gsonService) {
        this.gsonService = gsonService;
    }

    @Override
    public JsonElement serialize(Hologram hologram, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject element = new JsonObject();
        element.addProperty("name", hologram.getName());

        element.addProperty("location", hologram.getLocation());

        element.addProperty("textLines", hologram.toString());
        return element;
    }

    @Override
    public Hologram deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Hologram hologram = new Hologram(jsonElement.getAsJsonObject().get("name").getAsString());
        hologram.setTextLines(new LinkedHashMap<Integer, String>() {{
            jsonElement.getAsJsonObject().get("textLines").getAsJsonArray().forEach(textLine -> {
                String[] data = textLine.getAsString().split(":");
                put(Integer.parseInt(data[0]), data[1]);
            });
        }});
        hologram.setLocation(LocationUtil.fromString(jsonElement.getAsJsonObject().get("location").getAsString()));
        return hologram;
    }
}
