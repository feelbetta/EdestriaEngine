package com.edestria.engine.logging;

import com.edestria.engine.EdestriaEngine;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

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

    public void log(LogType logType, String message) {
        Bukkit.getConsoleSender().sendMessage(this.edestriaEngine.getDescription().getPrefix() + " " + logType + message);
    }
}
