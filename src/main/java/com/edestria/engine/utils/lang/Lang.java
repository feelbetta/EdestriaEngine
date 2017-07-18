package com.edestria.engine.utils.lang;

import org.bukkit.ChatColor;

public class Lang {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('$', string);
    }
}
