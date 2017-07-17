package com.edestria.engine.display.holograms.services;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.database.mongo.trackers.DataTracker;
import com.edestria.engine.display.holograms.Hologram;
import com.edestria.engine.eplayers.EPlayer;
import lombok.Builder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.*;

public class HologramTracker extends DataTracker<Hologram, String> {

    public HologramTracker(EdestriaEngine edestriaEngine) {
        super(edestriaEngine, "name", edestriaEngine.getMongoConnection().getActiveCollection("holograms"), new HashMap<>(), Hologram::new);
    }

    @Builder
    public Hologram create(String name, Location location, String... textLines) {
        Hologram hologram = new Hologram(name);
        hologram.setLocation(location);
        hologram.setTextLines(new LinkedHashMap<Integer, String>() {{
            Arrays.stream(textLines).map(textLine -> ChatColor.translateAlternateColorCodes('$', textLine)).forEach(textLine -> {
                put(Hologram.GLOBAL_ID--, textLine);
            });
        }});
        this.getData().put(name, hologram);
        Bukkit.broadcastMessage(this.getData().get(name).toString());
        this.update("name", this.retrieve("name", hologram, hologram.getName()));
        return hologram;
    }
}
