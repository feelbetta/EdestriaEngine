package com.edestria.engine.display.holograms;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.edestria.engine.Sendable;
import com.edestria.engine.utils.locations.LocationUtil;
import com.edestria.engine.utils.packets.Packets;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Hologram implements Sendable {

    public transient static int GLOBAL_ID = -1;

    @Setter private String name;
    private String location;

    private Map<Integer, String> textLines = new LinkedHashMap<>();

    public Hologram(String name) {
        this.name = name;
    }

    public Hologram() {

    }

    public void setLocation(Location location) {
        this.location = LocationUtil.toString(location);
    }

    public void addTextLines(String... textLines) {
        Arrays.stream(textLines).map(textLine -> ChatColor.translateAlternateColorCodes('$', textLine)).forEach(textLine -> this.textLines.put(GLOBAL_ID--, textLine));
    }

    public void setTextLines(Map<Integer, String> textLines) {
        this.textLines = textLines;
    }

    @Override
    public void to(Player player) {
        Location lineLocation = LocationUtil.fromString(this.location).clone();
        this.textLines.forEach((integer, s) -> {
            PacketContainer spawn = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
            PacketContainer data = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);


            spawn.getUUIDs().write(0, UUID.randomUUID());

            spawn.getIntegers().write(0, integer);
            spawn.getIntegers().write(1, 30);

            spawn.getDoubles().write(0, lineLocation.getX());
            spawn.getDoubles().write(1, lineLocation.subtract(0, .24, 0).getY());
            spawn.getDoubles().write(2, lineLocation.getZ());

            data.getIntegers().write(0, integer);

            WrappedDataWatcher dataWatcher = new WrappedDataWatcher();

            dataWatcher.setObject(2, WrappedDataWatcher.Registry.get(String.class), s);
            dataWatcher.setObject(3, WrappedDataWatcher.Registry.get(Boolean.class), true, false);
            dataWatcher.setObject(5, WrappedDataWatcher.Registry.get(Boolean.class), true, false);
            dataWatcher.setObject(11, WrappedDataWatcher.Registry.get(Byte.class), (byte) 0x09);
            dataWatcher.setObject(0, WrappedDataWatcher.Registry.get(Byte.class), (byte) 0x20);

            data.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());
            Packets.send(player, spawn, data);
        });
    }

    @Override
    public String toString() {
        return this.getTextLines().entrySet().stream().map(integerStringEntry -> integerStringEntry.getKey() + integerStringEntry.getValue()).collect(Collectors.toList()).stream().collect(Collectors.joining(","));
    }
}
