package com.edestria.engine.logging;

import com.edestria.engine.EdestriaEngine;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class EngineLogger {

    @AllArgsConstructor
    public enum LogType {

        INFO(ChatColor.GREEN),
        WARNING(ChatColor.YELLOW),
        ERROR(ChatColor.RED);

        private ChatColor color;

        @Override
        public String toString() {
            return this.color.toString();
        }
    }

    private final EdestriaEngine edestriaEngine;

    public EngineLogger(EdestriaEngine edestriaEngine) {
        this.edestriaEngine = edestriaEngine;
    }

    public void log(Plugin plugin, LogType logType, String message) {
        Bukkit.getConsoleSender().sendMessage(plugin.getDescription().getPrefix() + " " + logType + message);
    }

    public void log(LogType logType, String message) {
        this.log(this.edestriaEngine, logType, message);
    }
}
