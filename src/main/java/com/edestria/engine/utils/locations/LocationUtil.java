package com.edestria.engine.utils.locations;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {

    public static String toString(Location location) {
        return location.getWorld().getName() + ", " +  location.getX() + ", " + location.getY() + ", " + location.getZ() + ", " + location.getPitch() + ", " + location.getYaw();
    }

    public static Location fromString(String location) {
        String[] split = location.split(", ");
        return split.length == 6 ? new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5])) : null;
    }
}
