package com.edestria.engine.commands;

import com.edestria.engine.EdestriaEngine;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TestCommand extends ECommand {

    public TestCommand() {
        super(JavaPlugin.getPlugin(EdestriaEngine.class));
    }

    @Override @CommandProperties(name = "TestCommandFUCK")
    public void perform(Player player, String[] args) {
       player.sendMessage("I like to lick fat ass");
    }
}
